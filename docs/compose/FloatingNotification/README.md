# Floating Notification

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Floating Notification:

```Kotlin
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import androidx.compose.runtime.rememberCoroutineScope

val scope = rememberCoroutineScope()
val state = rememberBpkFloatingNotificationState()
BpkFloatingNotification(hostState = state)
scope.launch {
  state.show(
    text = "Lorem ipsum dolor sit amet."
  )
}
```

Example of a Floating Notification with icon and CTA:

```Kotlin

val scope = rememberCoroutineScope()
val state = rememberBpkFloatingNotificationState()
BpkFloatingNotification(hostState = state)
scope.launch {
  state.show(
    text = "Lorem ipsum dolor sit amet.",
    icon = BpkIcon.Heart,
    cta = CTA("View", onClick = {}),
  )
}
```
