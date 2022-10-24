# Floating Notification

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Floating Notification:

```Kotlin
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification

BpkFloatingNotification(
  text = "Lorem ipsum dolor sit amet",
  show = true // set to true when you want to show the notification
)
```

Example of a Floating Notification with icon and CTA:

```Kotlin
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification

BpkFloatingNotification(
  text = "Lorem ipsum dolor sit amet",
  icon = BpkIcon.Heart,
  cta = CTA("View", onClick = {}),
  show = true
)
```

Example of a Floating Notification with custom animation:

```Kotlin
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification

BpkFloatingNotification(
  text = "Lorem ipsum dolor sit amet",
  animation = Animation(
    enabled = true,
    showDuration = 2000.milliseconds,
    transitionDuration = 200.milliseconds,
    onFinished = {}
  ),
  show = true
)
```

Example of a Floating Notification with real usage:

```Kotlin
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.button.BpkButton

val (show, setShow) = remember { mutableStateOf(false) }
BpkFloatingNotification(
  text = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.",
  icon = BpkIcon.Heart,
  show = show
)
BpkButton(text = "Show Notification") {
  setShow(true)
}
```
