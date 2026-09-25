# Video player observability API

## TL;DR

`BpkVideoPlayerController` gains `bytesTransferred: State<Long>`, and `BpkVideoPlayerError` gains a `code: BpkVideoPlayerErrorCode` normalising Media3 failures into the part of Backpack Web's vocabulary Android can actually produce. Both are additive and read-only. The error vocabulary follows Web; the data-transfer metric has no Web precedent, so this change defines it cross-platform.

## Decision

Two additions to the public surface, released together as `minor`.

### 1. `bytesTransferred`

```kotlin
class BpkVideoPlayerController {
    val bytesTransferred: State<Long>
}
```

Cumulative bytes pulled over the network for the controller's media item. Four semantics, pinned here because they become the cross-platform definition:

| | |
| --- | --- |
| **unit** | bytes, as a `Long`. Not KB — let the dashboard scale it |
| **scope** | network bytes only; local and bundled reads report `0` |
| **window** | cumulative per impression — one media item, one controller |
| **includes** | transfers later cancelled or failed, and re-fetches caused by `loop` or `resetToStart()`. The question is spend, not playback outcome |

Implemented by attaching a `TransferListener` to the existing `DefaultDataSource.Factory` and accumulating `onBytesTransferred` when `isNetwork` is true. Accumulation happens into an `AtomicLong`, and that atomicity is load-bearing rather than defensive: Media3 gives every `ChunkSampleStream` its own single-thread `Loader`, and HLS commonly splits video, audio and subtitles into separate rendition groups, each with its own `ChunkSampleStream` — so this one counter can genuinely receive concurrent writes from several loading threads at once. A coroutine on the controller's scope publishes it into Compose state every 200 ms, reusing the progress-polling cadence. `dispose()` cancels publishing, releases the player, then publishes a final value — in that order, so bytes received part-way through a final chunk are not lost.

No reset API. `rememberBpkVideoPlayerController` uses `remember(config)`, so controller lifetime and media-item lifetime are identical and per-impression already coincides with per-controller.

### 2. `BpkVideoPlayerErrorCode`

```kotlin
sealed class BpkVideoPlayerError {
    abstract val code: BpkVideoPlayerErrorCode

    data object LoadTimeout : BpkVideoPlayerError() { /* code = LoadTimeout */ }

    data class PlaybackFailed(
        val cause: Exception,
        override val code: BpkVideoPlayerErrorCode = BpkVideoPlayerErrorCode.UnknownError,
    ) : BpkVideoPlayerError()
}

enum class BpkVideoPlayerErrorCode(val wireName: String) { /* 6 constants */ }
```

Six constants: the five Web codes Android can actually produce, plus `LOAD_TIMEOUT`. Each carries Web's exact wire string as `wireName`, which is what consumers report.

**The `HLS_*` family is deliberately not implemented.** Web's other seven codes come from `hls.js` error *types*, which exist because `hls.js` is a JavaScript HLS implementation layered over `HTMLMediaElement` and reports its own failures separately from the element's. Media3 has no such layer — it plays HLS natively and reports the same `PlaybackException` codes it reports for progressive media. Emitting `HLS_MUX_ERROR` for, say, `ERROR_CODE_PARSING_CONTAINER_MALFORMED` on an `.m3u8` URL would be inferring an `hls.js` concept from an unrelated signal: a guess dressed up as a classification, and one that would only look right until a manifest served MP4 segments or a progressive URL served HLS.

So the code answers *what failed* and not *how the video was delivered*. A dashboard that needs the format split has the video URL as a dimension already; a cross-platform dashboard unions Android's `MEDIA_ERR_NETWORK` with Web's `HLS_NETWORK_ERROR` rather than expecting one string from both.

The full Media3-to-Backpack mapping table lives in `BpkVideoPlayerErrorCodeMapper.kt`, covered per-row by `BpkVideoPlayerErrorCodeMapperTest`. Two rows are judgement calls rather than translations:

- `ERROR_CODE_DECODING_RESOURCES_RECLAIMED` reports `MEDIA_ERR_ABORTED`. Media3 has no user-initiated-abort error, so without this row that constant would be unreachable — and a decoder taken away by a higher-priority app is a genuine abort rather than a decode failure.
- `ERROR_CODE_IO_INVALID_HTTP_CONTENT_TYPE` reports `MEDIA_ERR_SRC_NOT_SUPPORTED` rather than a network error. The fetch succeeded; what arrived was not playable media.

## Thinking

### Why normalise at all

`BpkVideoPlaybackReducer` currently passes the raw `PlaybackException` through as `PlaybackFailed(cause)`. A consumer *can* read `PlaybackException.errorCode` — but then every consumer owns its own mapping, and iOS, Android and Web drift. Web already ships this taxonomy; cross-platform dashboards will not join unless the app shares the vocabulary. Normalising once, in the component, keeps the Media3 vocabulary internal and gives every consumer the same classification for free.

### Why a property and not a new error case

`BpkVideoPlayerError` cannot gain a subclass: consumer code has an exhaustive `when` over the two existing cases, and a third would break its compile. A property on the sealed parent is additive for every consumer.

Likewise `code` is a **defaulted constructor parameter** on `PlaybackFailed` rather than a computed getter: the code is derived from `PlaybackException.errorCode`, and `cause` is typed as `Exception` so that Media3 stays out of the public signature — the classification has to be computed where the `PlaybackException` is still visible, which is inside the controller. The default keeps existing `PlaybackFailed(someException)` call sites source-compatible and is only ever reached by external callers — the component always passes a classified code.

### Why `TransferListener` and not the alternatives

- **`AnalyticsListener.onLoadCompleted`/`Canceled`/`Error`** fire only when a load *terminates*. For a progressive MP4 that is typically one load for the whole file, so the counter would sit at zero for the entire download, and a mid-download stop would depend on a cancellation callback racing listener removal. Too coarse, and wrong in the case that matters most.
- **`BandwidthMeter.EventListener.onBandwidthSample`** is worth recording because it is the choice most likely to be re-litigated: ExoPlayer's default bandwidth meter is a process-wide singleton with no getter, so scoping it to one player means installing our own instance — which stops HLS adaptive bitrate from sharing bitrate estimates across players. Trading playback quality for a metric is the wrong call, and its samples are throttled estimation deltas rather than an exact ledger.

`TransferListener.onBytesTransferred` fires per chunk as bytes arrive, which is what makes the value correct at any instant rather than only at the end, and `isNetwork` is what makes it network-only rather than counting local reads.

### Consistency with `event-listeners-and-state.md`

That decision warns against a component registering listeners on the consumer's behalf and mutating visual state. Both additions here are read-only, pull-based `State<T>`; no listener enters the public API, and the Media3 listeners involved are internal details a consumer can neither see nor override. Its "expose a function instead of changing state internally" guidance is about *interaction-driven* state — `bytesTransferred` is observation-driven, reporting something that happened rather than reacting to a gesture, so the `toggle()` pattern does not apply.

### Why the load timeout gets its own code

Web has no counterpart: it tracks load timeouts as a separate `Fallback_Shown` signal. Folding them into `MEDIA_ERR_NETWORK` would inflate the network-error count on a shared dashboard, so `LOAD_TIMEOUT` is additive vocabulary rather than a conflicting mapping.

## Anything else

Open items for the API review, rather than settled parts of the decision above.

1. **The data-transfer metric has no Web precedent, so this change defines it.** Confirm the four semantics, and confirm that the iOS mechanism agrees: `AVPlayerItemAccessLogEvent.numberOfBytesTransferred` summed across `AVPlayerItem.accessLog` events is also cumulative network bytes for the item, so the two platforms should agree without either bending — but it is worth checking before the numbers reach a shared dashboard.
2. **Client bytes are not billed bytes.** The device counts what it received; the CDN bills what it sent, including abandoned requests, protocol overhead, and clients that never report. This is a proportional early-warning and attribution signal, not billing reconciliation. Worth stating wherever the metric is first presented.
3. **Nothing is cached, so `loop` replays are not free.** Backpack wires no `CacheDataSource`, and ExoPlayer has no disk cache by default; its in-memory buffer discards played-past samples, so seeking back to zero on a repeat generally re-reads from the network. Consumers set `loop = true`, so a hero video parked on screen plausibly costs its asset size *per loop iteration*. `bytesTransferred ÷ known asset size` measures that multiple, and is the most valuable thing this metric will report in its first week. If loops do re-fetch, whether caching belongs in Backpack, in CDN headers, or neither is a separate ticket.
4. **The whole `HLS_*` family is absent**, not two stragglers from it. Media3 plays HLS natively and reports the same `PlaybackException` codes for every format, so there is no signal to derive an `hls.js` error type from — see the Decision above. The practical consequence for the review: a cross-platform dashboard must union Android's `MEDIA_ERR_NETWORK` with Web's `HLS_NETWORK_ERROR` (and the other `MEDIA_ERR_*`/`HLS_*` pairs) rather than grouping on the wire string alone. Confirm Quark can express that union, and that nobody is relying on the `HLS_*` strings arriving from app clients.
5. **`bytesTransferred` is monotonic across `loop` replays and `resetToStart()`.** Correct for cost accounting, surprising if read as "bytes for this play-through" — the semantics most likely to be misread by a consumer, and the reason the README documents reporting at lifecycle pause rather than from a consumer's `onDispose`.
6. **`PlaybackFailed`'s generated `copy` and constructor signatures change**, and `code` now participates in `equals`. Acceptable for a `minor` release in a repo with no binary-compatibility validator, but a decision rather than an accident.
7. **`wireName` versus SCREAMING_SNAKE enum constants.** Constants follow the repo's PascalCase enum convention (cf. `BpkVideoPlayerSurfaceType`), so `.name` is not the wire string. Naming the constants `MEDIA_ERR_NETWORK` would make `.name` the contract and remove the footgun, at the cost of breaking convention. Low stakes, but a permanent public name.
8. **`mutableLongStateOf` for `bytesTransferred`** diverges from the siblings' `mutableStateOf`, avoiding autoboxing on a value that updates every 200 ms. The public type stays `State<Long>`.
