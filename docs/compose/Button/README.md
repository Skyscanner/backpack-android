# Button

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.button)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/button)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Button/screenshots/default.png" alt="Button component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Button/screenshots/default_dm.png" alt="Button component - dark mode" width="375" /> |

## Large

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Button/screenshots/large.png" alt="Large Button component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Button/screenshots/large_dm.png" alt="Large Button component - dark mode" width="375" /> |

## Link

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Button/screenshots/link.png" alt="Link Button component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Button/screenshots/link_dm.png" alt="Link Button component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a text Button with default (small) size and primary type:

```Kotlin
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType

BpkButton(
  text = stringResource(R.string.my_button_text),
  size = BpkButtonSize.Default,
  type = BpkButtonType.Primary,
) {
    // onClick
}
```

Example of a disabled button:

```Kotlin
import net.skyscanner.backpack.compose.button.BpkButton

BpkButton(
  text = stringResource(R.string.my_button_text),
  enabled = false,
) {
  // onClick
}
```

Example of a loading button:

```Kotlin
import net.skyscanner.backpack.compose.button.BpkButton

BpkButton(
  text = stringResource(R.string.my_button_text),
  loading = true,
) {
  // onClick
}
```

Example of a large button:

```Kotlin
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonSize

BpkButton(
  text = stringResource(R.string.my_button_text),
  size = BpkButtonSize.Large,
) {
  // onClick
}
```

Example of a secondary button:

```Kotlin
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonType

BpkButton(
  text = stringResource(R.string.my_button_text),
  type = BpkButtonType.Secondary,
) {
  // onClick
}
```

Example of a icon only button:

```Kotlin
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.icons.BpkIcons
import net.skyscanner.backpack.compose.icons.sm.LongArrowRight

BpkButton(
  icon = BpkIcons.Sm.LongArrowRight,
  contentDescription = stringResource(R.string.my_button_content_description),
) {
  // onClick
}
```

Example of a button with leading icon and text:

```Kotlin
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonIconPosition
import net.skyscanner.backpack.compose.icons.BpkIcons
import net.skyscanner.backpack.compose.icons.sm.LongArrowRight

BpkButton(
  text = stringResource(R.string.my_button_text),
  icon = BpkIcons.Sm.LongArrowRight,
  position = BpkButtonIconPosition.Start,
) {
  // onClick
}
```
