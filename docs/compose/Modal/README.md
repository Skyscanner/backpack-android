# Modal

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.net.skyscanner.backpack.compose.dialog)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/net.skyscanner.backpack.compose.dialog)

## DefaultModal

| Day | Night | Right to Left |
| --- | --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-title-action.png" alt="DefaultModal" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-title-action_dm.png" alt="DefaultModal - dark mode" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-title-action_rtl.png" alt="DefaultModal - rtl mode" width="375" /> |

## ModalWithoutAction

| Day | Night | Right to Left |
| --- | --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-title-no-action.png" alt="ModalWithoutAction" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-title-no-action_dm.png" alt="ModalWithoutAction - dark mode" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-title-no-action_rtl.png" alt="ModalWithoutAction - rtl mode" width="375" /> |

## ModalWithoutActionAndTitle

| Day | Night | Right to Left |
| --- | --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-no-title-no-action.png" alt="ModalWithoutActionAndTitle" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-no-title-no-action_dm.png" alt="ModalWithoutActionAndTitle - dark mode" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-no-title-no-action_rtl.png" alt="ModalWithoutActionAndTitle - rtl mode" width="375" /> |

## ModalWithBackIcon

| Day | Night | Right to Left |
| --- | --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-back-icon.png" alt="ModalWithBackIcon" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-back-icon_dm.png" alt="ModalWithBackIcon - dark mode" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-back-icon_rtl.png" alt="ModalWithBackIcon - rtl mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Modal with title, text action and text content:

```Kotlin
import net.skyscanner.backpack.compose.net.skyscanner.backpack.compose.dialog.BpkModal

BpkModal(
    title = stringResource(id = R.string.dialog_title),
    closeButtonAccessibilityLabel = stringResource(id = R.string.navigation_accessibility),
    action = TextAction(
        text = stringResource(R.string.navigation_text_action),
        onClick = { /** onActionClick **/ },
    ),
    onDismiss = { /** onDismiss **/ },
) {
    BpkText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(BpkSpacing.Base),
        text = stringResource(R.string.dialog_text),
        style = BpkTheme.typography.bodyDefault,
        color = BpkTheme.colors.textPrimary,
    )
}
```

Example of a Modal with title and text content:

```Kotlin
import net.skyscanner.backpack.compose.net.skyscanner.backpack.compose.dialog.BpkModal

BpkModal(
    title = stringResource(id = R.string.dialog_title),
    closeButtonAccessibilityLabel = stringResource(id = R.string.navigation_accessibility),
    action = null,
    onDismiss = { /** onDismiss **/ },
) {
    BpkText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(BpkSpacing.Base),
        text = stringResource(R.string.dialog_text),
        style = BpkTheme.typography.bodyDefault,
        color = BpkTheme.colors.textPrimary,
    )
}
```

Example of a Modal with text content only:

```Kotlin
import net.skyscanner.backpack.compose.net.skyscanner.backpack.compose.dialog.BpkModal

BpkModal(
    title = null,
    closeButtonAccessibilityLabel = stringResource(id = R.string.navigation_accessibility),
    action = null,
    onDismiss = { /** onDismiss **/ },
) {
    BpkText(
        modifier = Modifier
            .fillMaxWidth()
            .padding(BpkSpacing.Base),
        text = stringResource(R.string.dialog_text),
        style = BpkTheme.typography.bodyDefault,
        color = BpkTheme.colors.textPrimary,
    )
}
```
