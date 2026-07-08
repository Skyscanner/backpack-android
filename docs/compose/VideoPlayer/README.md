# VideoPlayer

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.videoplayer)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/videoplayer)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/VideoPlayer/screenshots/default.png" alt="VideoPlayer component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/VideoPlayer/screenshots/default_dm.png" alt="VideoPlayer component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

### Basic usage

```Kotlin
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayer
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerConfig
import net.skyscanner.backpack.compose.videoplayer.rememberBpkVideoPlayerController

val controller = rememberBpkVideoPlayerController(
  config = BpkVideoPlayerConfig(
    videoUrl = "https://example.com/video.mp4",
    accessibilityLabel = stringResource(R.string.video_accessibility_label),
  ),
)

BpkVideoPlayer(controller = controller)
```

### With default controls overlay

```Kotlin
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayer
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerConfig
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlayerDefaultControls
import net.skyscanner.backpack.compose.videoplayer.rememberBpkVideoPlayerController

val controller = rememberBpkVideoPlayerController(
  config = BpkVideoPlayerConfig(
    videoUrl = "https://example.com/video.mp4",
    loop = true,
    startsMuted = true,
    accessibilityLabel = stringResource(R.string.video_accessibility_label),
  ),
)

Box {
  BpkVideoPlayer(
    controller = controller,
    modifier = Modifier
      .matchParentSize()
      .clickable { controller.toggle() },
  )
  BpkVideoPlayerDefaultControls(
    controller = controller,
    modifier = Modifier.align(Alignment.TopEnd),
  )
}
```

### Observing playback state

```Kotlin
import net.skyscanner.backpack.compose.videoplayer.BpkVideoPlaybackState

val playbackState by controller.playbackState

when (playbackState) {
  is BpkVideoPlaybackState.Playing -> { /* video is playing */ }
  is BpkVideoPlaybackState.Paused -> { /* video is paused */ }
  is BpkVideoPlaybackState.Failed -> { /* handle error */ }
  else -> { /* loading, buffering, etc. */ }
}
```
