# VideoPlayer

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.videoplayer)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/videoplayer)

## Default controls

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/VideoPlayer/screenshots/default.png" alt="VideoPlayer component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/VideoPlayer/screenshots/default_dm.png" alt="VideoPlayer component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

A video player with no enforced aspect ratio. Control playback via `BpkVideoPlayerController`, obtained with `rememberBpkVideoPlayerController`. Compose overlays directly on top of `BpkVideoPlayer` — use the built-in `BpkVideoPlayerDefaultControls` or wire your own UI to the controller.

### Obtaining the controller

```kotlin
val controller = rememberBpkVideoPlayerController(
    config = BpkVideoPlayerConfig(
        videoUrl = BpkVideoUrl("https://example.com/video.mp4"),
        loop = true,
        startsMuted = true,
        accessibilityLabel = stringResource(R.string.video_accessibility_label),
    ),
)
```

`rememberBpkVideoPlayerController` creates a `BpkVideoPlayerController` scoped to the composition. The player is automatically released when the composable leaves the tree.

### Simple — built-in controls

`BpkVideoPlayerDefaultControls` handles play/pause taps internally. Provide localized `playContentDescription` and `pauseContentDescription` — the component owns no user-facing copy.

```kotlin
Box {
    BpkVideoPlayer(
        controller = controller,
        modifier = Modifier.matchParentSize(),
    )
    BpkVideoPlayerDefaultControls(
        controller = controller,
        playContentDescription = stringResource(R.string.video_play_label),
        pauseContentDescription = stringResource(R.string.video_pause_label),
        modifier = Modifier.align(Alignment.TopEnd),
    )
}
```

### Custom controls

`BpkVideoPlayerDefaultControls` is optional. Use `controller` directly to build any UI — observe `controller.playbackState` and `controller.isMuted` as Compose `State` to react to changes.

```kotlin
val playbackState by controller.playbackState
val isMuted by controller.isMuted

Box {
    BpkVideoPlayer(
        controller = controller,
        modifier = Modifier.fillMaxSize(),
    )
    Row(modifier = Modifier.align(Alignment.BottomCenter)) {
        Button(onClick = { controller.toggle() }) {
            Text(if (playbackState.isPlaying) "Pause" else "Play")
        }
        Button(onClick = { controller.setMuted(!isMuted) }) {
            Text(if (isMuted) "Unmute" else "Mute")
        }
    }
}
```

Available controller actions: `play()`, `pause()`, `toggle()`, `setMuted(Boolean)`, `resetToStart()`.

### Surface type

By default the video is drawn into a `SurfaceView`, which renders in its own hardware layer. That is the cheapest option and its last frame is held by the system compositor, so it survives the app being backgrounded — but the layer is composited outside the view hierarchy, so Compose `alpha`, z-order and clipping do not apply to it. A video that is faded or animated can therefore stay fully opaque while everything around it fades.

Pass `surfaceType = BpkVideoPlayerSurfaceType.TextureView` when the video takes part in normal Compose compositing. Alpha, z-order, clipping and transforms all behave as expected. It costs an extra GPU copy and cannot display DRM-protected content.

```kotlin
BpkVideoPlayer(
    controller = controller,
    modifier = Modifier.fillMaxSize(),
    surfaceType = BpkVideoPlayerSurfaceType.TextureView,
)
```

### Shared controller — continuous playback across transitions

Create a `BpkVideoPlayerController` and pass it to multiple `BpkVideoPlayer` calls. Playback continues uninterrupted when the view changes (e.g. card → fullscreen).

```kotlin
val controller = rememberBpkVideoPlayerController(
    config = BpkVideoPlayerConfig(
        videoUrl = BpkVideoUrl("https://example.com/video.mp4"),
        loop = true,
        startsMuted = true,
        accessibilityLabel = stringResource(R.string.video_accessibility_label),
    ),
)

// Card view — scale to fit
BpkVideoPlayer(controller = controller)

// Fullscreen — same controller, playback never resets, scale to fill
BpkVideoPlayer(controller = controller, scaleToFill = true)
```

### Scale to fill (crop)

Pass `scaleToFill = true` to crop the video to fill the container without letterboxing — equivalent to iOS `resizeAspectFill`.

```kotlin
BpkVideoPlayer(
    controller = controller,
    modifier = Modifier.fillMaxSize(),
    scaleToFill = true,
)
```

### Observing playback state

```kotlin
val playbackState by controller.playbackState

when (playbackState) {
    is BpkVideoPlaybackState.Playing   -> { /* video is playing */ }
    is BpkVideoPlaybackState.Paused    -> { /* video is paused */ }
    is BpkVideoPlaybackState.Ended     -> { /* video finished */ }
    is BpkVideoPlaybackState.Failed    -> { /* handle error */ }
    else                               -> { /* loading, buffering, etc. */ }
}
```
### Tracking playback progress

`controller.progressState` exposes the current playback position as a `BpkVideoPlayerProgress?` Compose `State`. It is `null` when the duration is not yet known (e.g. before the asset is ready). Once playback starts it updates roughly every 200 ms, and a guaranteed final value at 100% is emitted at the end of each play-through — including loop boundaries.

```kotlin
val progress by controller.progressState

progress?.let {
    println("${it.positionMs} / ${it.durationMs} ms (${(it.percentage * 100).toInt()}%)")
}
```

A common pattern is quartile tracking for analytics:

```kotlin
LaunchedEffect(Unit) {
    val fired = mutableSetOf<Int>()
    snapshotFlow { controller.progressState.value }
        .filterNotNull()
        .collect { p ->
            listOf(25, 50, 75, 100).forEach { q ->
                if (q !in fired && p.percentage >= q / 100f) {
                    fired.add(q)
                    trackEvent("video_quartile_$q")
                }
            }
        }
}
```

BpkVideoPlayerProgress fields:


| Field  | Type | Description |
| --- | --- | --- |
| positionMs | Long | Current playback position in milliseconds |
| durationMs | Long | Total video duration in milliseconds |
| percentage | Float | positionMs / durationMs, 0f when duration is 0 |

### Monitoring data transfer

`controller.bytesTransferred` exposes the cumulative number of bytes the player has pulled over the network for its media item, as a `State<Long>`. It is intended for operational monitoring — CDN cost attribution and spotting an oversized or repeatedly re-fetched asset.

Semantics worth knowing before you report it:

- **Network bytes only.** A bundled resource or local file reports `0`.
- **Updated as data arrives**, not when a load finishes — so the value is meaningful mid-playback, not just at the end. It is published at most every 200 ms.
- **Cumulative per impression.** A new controller is created whenever `BpkVideoPlayerConfig` changes, so the total covers exactly one media item. Nothing resets it: `resetToStart()` and `loop` replays add to the total when the player re-fetches, which is the correct reading for a cost metric but a misleading one if you read it as "bytes for this play-through".
- **Includes transfers that failed or were cancelled**, because those were still served.
- **Client bytes are not billed bytes.** The device counts what it received; the CDN bills what it sent. Treat this as a proportional signal, not as billing reconciliation.

Report it once per impression, when transfers have stopped — on lifecycle pause or scroll-away:

```kotlin
val lifecycleOwner = LocalLifecycleOwner.current

LaunchedEffect(controller) {
    lifecycleOwner.repeatOnLifecycle(Lifecycle.State.RESUMED) {
        try {
            awaitCancellation()
        } finally {
            // Reached on pause / scroll-away, while the controller is still alive.
            trackEvent("video_bytes_transferred", controller.bytesTransferred.value)
        }
    }
}
```

Reading the total from your own `onDispose` is **not** the recommended pattern: disposal ordering between your `DisposableEffect` and the controller's own is not guaranteed, so such a read can miss up to one 200 ms publishing interval — always in the undercounting direction.

`BpkVideoPlaybackState.Ended` is not a usable trigger either when `loop = true`: a repeating player never reaches the end of its playlist, so `Ended` never arrives. Lifecycle pause is the trigger that works for every configuration.

### State reference

| State | Meaning |
| --- | --- |
| `Loading` | Asset is being fetched or decoded |
| `ReadyToPlay` | Asset ready — `autoPlay` will call `play()` if enabled |
| `Playing` | Playback active |
| `Paused` | Playback paused |
| `Buffering` | Rebuffering mid-playback |
| `Ended` | Playback reached the end |
| `Failed(cause)` | Load failed or timed out |

Convenience helpers on `BpkVideoPlaybackState`:

```kotlin
controller.playbackState.value.isPlaying  // true only when Playing
controller.playbackState.value.isLoading  // true for Loading and Buffering
```

Observable state on the controller:

| State | Type | Description |
| --- | --- | --- |
| `playbackState` | `State<BpkVideoPlaybackState>` | Current playback state, per the table above |
| `isMuted` | `State<Boolean>` | Whether the player is muted |
| `progressState` | `State<BpkVideoPlayerProgress?>` | Playback position, `null` until the duration is known |
| `bytesTransferred` | `State<Long>` | Cumulative network bytes for this media item — see [Monitoring data transfer](#monitoring-data-transfer) |

### Handling errors

`BpkVideoPlaybackState.Failed` carries a `BpkVideoPlayerError` describing what went wrong:

| Error | Meaning |
| --- | --- |
| `LoadTimeout` | The asset didn't finish loading within `loadTimeoutMs` |
| `PlaybackFailed(cause: Exception, code: BpkVideoPlayerErrorCode)` | The player reported a playback error — inspect `cause` for details |

Every error also carries a `code: BpkVideoPlayerErrorCode`, a platform-neutral classification whose `wireName` matches the vocabulary Backpack Web emits, so Android, iOS and Web failures can be grouped in a single dashboard.

```kotlin
val state = controller.playbackState.value
if (state is BpkVideoPlaybackState.Failed) {
    reportError(state.cause.code.wireName) // e.g. "MEDIA_ERR_NETWORK"

    when (val error = state.cause) {
        is BpkVideoPlayerError.LoadTimeout -> { /* show a retry prompt */ }
        is BpkVideoPlayerError.PlaybackFailed -> { /* inspect error.cause */ }
    }
}
```

Report `code.wireName` rather than the constant name: constant names follow Kotlin conventions and may be refactored, whereas `wireName` is a stable cross-platform contract.

| Code | `wireName` | Reported for |
| --- | --- | --- |
| `MediaErrAborted` | `MEDIA_ERR_ABORTED` | The decoder lost its resources to a higher-priority app |
| `MediaErrNetwork` | `MEDIA_ERR_NETWORK` | The video could not be fetched — connection failure, bad HTTP status, timeout |
| `MediaErrDecode` | `MEDIA_ERR_DECODE` | The video was fetched but could not be read or decoded |
| `MediaErrSrcNotSupported` | `MEDIA_ERR_SRC_NOT_SUPPORTED` | The container, codec or DRM scheme is unsupported |
| `UnknownError` | `UNKNOWN_ERROR` | A failure fitting no other code |
| `LoadTimeout` | `LOAD_TIMEOUT` | The video did not become playable within `loadTimeoutMs`. Has no Web counterpart, and is kept distinct from `MEDIA_ERR_NETWORK` so timeouts do not inflate the network-error count |

The code describes what failed, not how the video was delivered: an HLS stream and a progressive MP4 that both fail to download are reported as `MEDIA_ERR_NETWORK`. Use a dimension you already send — the video URL, or the creative identifier — if you need to split a dashboard by format.

Web additionally emits an `HLS_*` family sourced from `hls.js` error types (`HLS_NETWORK_ERROR`, `HLS_MEDIA_ERROR`, `HLS_MUX_ERROR`, `HLS_NOT_SUPPORTED`, `HLS_UNKNOWN_ERROR`, `HLS_OTHER_ERROR`, `HLS_CHUNK_LOAD_FAILED`). Android emits none of them: Media3 plays HLS natively and reports the same `PlaybackException` codes it reports for any other format, so an `Hls*` constant here would be a guess dressed up as a classification. A cross-platform dashboard should therefore union Android's `MEDIA_ERR_*` codes with Web's `HLS_*` equivalents rather than expecting the two platforms to emit the same string.

### Audio behaviour

The player defaults to `startsMuted = true`. Volume can be toggled at any time:

```kotlin
controller.setMuted(false) // unmute
controller.setMuted(true)  // mute
```

### Accessibility

- Reduced motion: autoplay is blocked and playback pauses when `ANIMATOR_DURATION_SCALE` or `TRANSITION_ANIMATION_SCALE` is set to 0.
- `accessibilityLabel` in `BpkVideoPlayerConfig` is applied as a `contentDescription` on the player container.
- `videoUrl` in `BpkVideoPlayerConfig` is a `BpkVideoUrl` value class. It supports HTTP(S) URLs, absolute local file paths, and `file:///` URIs, for example `BpkVideoUrl("https://example.com/video.mp4")`, `BpkVideoUrl("/data/user/0/example/cache/video.mp4")`, or `BpkVideoUrl("file:///data/user/0/example/cache/video.mp4")`.
