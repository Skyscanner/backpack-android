# Button

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
