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
        videoUrl = "https://example.com/video.mp4",
        loop = true,
        startsMuted = true,
        accessibilityLabel = stringResource(R.string.video_accessibility_label),
    ),
)
```

`rememberBpkVideoPlayerController` creates a `BpkVideoPlayerController` scoped to the composition. The player is automatically released when the composable leaves the tree.

### Simple — built-in controls

`BpkVideoPlayerDefaultControls` handles play/pause taps internally.

```kotlin
Box {
    BpkVideoPlayer(
        controller = controller,
        modifier = Modifier.matchParentSize(),
    )
    BpkVideoPlayerDefaultControls(
        controller = controller,
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

### Shared controller — continuous playback across transitions

Create a `BpkVideoPlayerController` and pass it to multiple `BpkVideoPlayer` calls. Playback continues uninterrupted when the view changes (e.g. card → fullscreen).

```kotlin
val controller = rememberBpkVideoPlayerController(
    config = BpkVideoPlayerConfig(
        videoUrl = "https://example.com/video.mp4",
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

### State reference

| State | Meaning |
| --- | --- |
| `Idle` | Player not yet prepared |
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

### Handling errors

`BpkVideoPlaybackState.Failed` carries a `BpkVideoPlayerError` describing what went wrong:

| Error | Meaning |
| --- | --- |
| `LoadTimeout` | The asset didn't finish loading within `loadTimeoutMs` |
| `PlaybackFailed(cause: PlaybackException)` | ExoPlayer reported a playback error — inspect `cause` (a Media3 `PlaybackException`) for details |

```kotlin
val state = controller.playbackState.value
if (state is BpkVideoPlaybackState.Failed) {
    when (val error = state.cause) {
        is BpkVideoPlayerError.LoadTimeout -> { /* show a retry prompt */ }
        is BpkVideoPlayerError.PlaybackFailed -> { /* inspect error.cause */ }
    }
}
```

### Poster image and loading placeholder

`BpkVideoPlayer` does not manage poster images — the component renders video only. Use `playbackState` to show your own placeholder while loading or on failure:

```kotlin
val state by controller.playbackState

Box {
    // Show a poster/placeholder until the first frame is ready, and on error
    if (state.isLoading || state is BpkVideoPlaybackState.Failed) {
        AsyncImage(
            model = posterUrl,
            contentDescription = null,
            contentScale = if (scaleToFill) ContentScale.Crop else ContentScale.Fit,
            modifier = Modifier.fillMaxSize(),
        )
    }
    BpkVideoPlayer(controller = controller, modifier = Modifier.matchParentSize())
    BpkVideoPlayerDefaultControls(controller = controller)
}
```

`ReadyToPlay` is the transition point when the first frame is decoded and ready to display — hide the poster at that point.

### Audio behaviour

The player defaults to `startsMuted = true`. Volume can be toggled at any time:

```kotlin
controller.setMuted(false) // unmute
controller.setMuted(true)  // mute
```

### Accessibility

- Reduced motion: autoplay is blocked and playback pauses when `ANIMATOR_DURATION_SCALE` is set to 0 or `AccessibilityManager.isAnimationsEnabled` returns false (API 33+).
- `accessibilityLabel` in `BpkVideoPlayerConfig` is applied as a `contentDescription` on the player container.
