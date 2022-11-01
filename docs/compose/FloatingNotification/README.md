# Floating Notification

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Floating Notification:

```Kotlin
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.Box
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
val scope = rememberCoroutineScope()
val state = rememberBpkFloatingNotificationState()

Box {

    BpkButton(text = "Show notification") {
        scope.launch {
            state.show(
                text = "Lorem ipsum dolor sit amet."
            )
        }
    }

    BpkFloatingNotification(state = state)

}
```

Example of a Floating Notification with icon and action:

```Kotlin
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.foundation.layout.Box
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
val scope = rememberCoroutineScope()
val state = rememberBpkFloatingNotificationState()

Box {

  BpkButton(text = "Show notification") {
    scope.launch {
      state.show(
        text = "Lorem ipsum dolor sit amet.",
        icon = BpkIcon.Heart,
        action = "View",
        onClick = {},
      )
    }
  }

  BpkFloatingNotification(state = state)

}
```
