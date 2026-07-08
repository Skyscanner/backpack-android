# BpkVideoPlayer

## Decision

Add `BpkVideoPlayer` as a new Backpack Compose component for video playback, built on Media3/ExoPlayer using the `PlayerSurface` composable from `media3-ui-compose`.

## Thinking

**Why Media3/ExoPlayer?**
Media3 is Google's supported successor to the legacy `ExoPlayer` and `MediaPlayer` APIs. It ships a native `PlayerSurface` composable in `androidx.media3:media3-ui-compose` (available from 1.6.0), eliminating the need for `AndroidView` interop. The `PlayerSurface` reassignment model enables seamless handoff of the same player instance between multiple composables without teardown or restart.

**Why pure Compose (`PlayerSurface`) over `AndroidView { VideoView }`?**
Per Skyscanner Production Standards, new Android UI must be Compose-first. `PlayerSurface` provides a native Compose API with no interop overhead. `AndroidView` would require a `SurfaceView` bridge and makes player reassignment between composables significantly harder.

**Gap-free looping**
`REPEAT_MODE_ONE` on a single-item playlist loops the asset indefinitely with no gap between iterations — this is a Media3 guarantee and the correct mode for a single-clip looping player.

**Bundle cost**
Adding `media3-exoplayer`, `media3-exoplayer-hls`, and `media3-ui-compose` increases the `backpack-compose` AAR by approximately 2–4 MB. Consuming teams should factor this into their APK size budgets.

**HLS support**
HLS playback requires `media3-exoplayer-hls` as a separate dependency — `DefaultMediaSourceFactory` loads `HlsMediaSource$Factory` via reflection at runtime and crashes if the artifact is absent. It is declared as `implementation` alongside `media3-exoplayer`.

**`@UnstableApi` opt-in**
`DefaultMediaSourceFactory` and `PlayerSurface` are annotated `@UnstableApi` by Media3. Both usages carry `@androidx.annotation.OptIn(UnstableApi::class)`, consistent with how other AndroidX experimental APIs are handled in this codebase.

**Media3 minimum API level**
Media3 supports API 21+. Backpack's existing minimum API floor is above 21, so no `minSdk` bump is required.

## Anything else

iOS counterpart: `BPKVideoPlayer` in backpack-ios.
