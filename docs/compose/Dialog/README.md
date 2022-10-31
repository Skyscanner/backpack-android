# Dialog

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.dialog)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/dialog)

## Success

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Dialog/screenshots/success.png" alt="Success Dialog component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Dialog/screenshots/success_dm.png" alt="Success Dialog component - dark mode" width="375" /> |

## Warning

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Dialog/screenshots/warning.png" alt="Warning Dialog component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Dialog/screenshots/warning_dm.png" alt="Warning Dialog component - dark mode" width="375" /> |

## Destructive

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Dialog/screenshots/destructive.png" alt="Destructive Dialog component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Dialog/screenshots/destructive_dm.png" alt="Destructive Dialog component - dark mode" width="375" /> |

## Flare

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Dialog/screenshots/flare.png" alt="Flare Dialog component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Dialog/screenshots/flare_dm.png" alt="Flare Dialog component - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a success dialog with three buttons

```Kotlin
import net.skyscanner.backpack.compose.dialog.BpkDialog
import net.skyscanner.backpack.compose.icons.BpkIcons

BpkSuccessDialog(
  icon = BpkIcons.Lg.Tick,
  title = stringResource(id = R.string.dialog_title),
  text = stringResource(id = R.string.dialog_text),
  confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation)) { /** onClick **/ },
  secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip)) { /** onClick **/ },
  linkButton = DialogButton(stringResource(id = R.string.dialog_link_optional)) { /** onClick **/ },
) {
  // onDismiss
}
```

Example of a warning dialog with two buttons

```Kotlin
import net.skyscanner.backpack.compose.dialog.BpkDialog
import net.skyscanner.backpack.compose.icons.BpkIcons

BpkWarningDialog(
  icon = BpkIcons.Lg.AlertAdd,
  title = stringResource(id = R.string.dialog_title),
  text = stringResource(id = R.string.dialog_text),
  confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation)) { /** onClick **/ },
  secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip)) { /** onClick **/ },
) {
  // onDismiss
}
```

Example of a destructive dialog with two buttons

```Kotlin
import net.skyscanner.backpack.compose.dialog.BpkDialog
import net.skyscanner.backpack.compose.icons.BpkIcons

BpkDestructiveDialog(
      icon = BpkIcons.Lg.Trash,
      title = stringResource(id = R.string.dialog_title),
      text = stringResource(id = R.string.dialog_text),
      confirmButton = DialogButton(stringResource(id = R.string.dialog_delete)) { /** onClick **/ },
      linkButton = DialogButton(stringResource(id = R.string.dialog_cancel)) { /** onClick **/ },
      onDismissRequest = onDismiss,
) {
  // onDismiss
}
```

Example of a flare dialog

```Kotlin
import androidx.compose.foundation.Image
import net.skyscanner.backpack.compose.dialog.BpkDialog
import net.skyscanner.backpack.compose.icons.BpkIcons

BpkFlareDialog(
  title = stringResource(id = R.string.dialog_title),
  text = stringResource(id = R.string.dialog_text),
  confirmButton = DialogButton(stringResource(id = R.string.dialog_confirmation)) { /** onClick **/ },
  secondaryButton = DialogButton(stringResource(id = R.string.dialog_skip)) { /** onClick **/ },
  onDismissRequest = { /** onDismiss **/ },
) {
  Image(
    painter = painterResource(R.drawable.flare_image),
    contentDescription = stringResource(R.string.flare_image_content_description),
    contentScale = ContentScale.Crop,
  )
}
```
