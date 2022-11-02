# Floating Notification

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.floatingnotification)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/floatingnotification)

## Default

| Day | Night                                                                                                                                                                                                      |
| --- |------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FloatingNotification/screenshots/default.png" alt="FloatingNotification component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/FloatingNotification/screenshots/default_dm.png" alt="FloatingNotification component - dark mode" width="375" /> |

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
        onClick = { println("action performed") },
        onExit = { println("notification dismissed") },
      )
    }
  }

  BpkFloatingNotification(state = state)

}
```
