# Nudger

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.nudger)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/nudger)

## Default

| Day                                                                                                                                                         | Night                                                                                                                                                                      |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Nudger/screenshots/default.png" alt="Nudger component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Nudger/screenshots/default_dm.png" alt="Nudger component - dark mode" width="375" /> |

## Accessibility

The nudger is exposed to screen readers as a single adjustable control, so TalkBack announces the
current value and offers the volume keys to change it rather than reading the `+` and `-` buttons
separately.

For external keyboards the two buttons are separate focus stops. When a button becomes disabled
because the value reached `min` or `max`, keyboard focus moves to the opposite button so it is never
lost. Note that Compose only makes buttons focusable outside of touch input mode, so this has no
effect on touch interaction.

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Nudger:

```Kotlin
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.nudger.BpkNudger
import net.skyscanner.backpack.compose.tokens.Account

BpkNudger(
  value = currentValue,
  onValueChange = { /* update the value */ },
  min = minValue,
  max = maxValue,
)
```

Example of a Nudger with title, subtitle and icon:

```Kotlin
import net.skyscanner.backpack.compose.nudger.BpkNudger

BpkNudger(
  value = currentValue,
  onValueChange = { /* update the value */ },
  min = minValue,
  max = maxValue,
  title = "Title",
  subtitle = "Subtitle",
  icon = BpkIcon.Account,
)
```
