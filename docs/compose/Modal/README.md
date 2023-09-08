# Modal

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.net.skyscanner.backpack.compose.dialog)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/net.skyscanner.backpack.compose.dialog)

## Default

| Day                                                                                                                                                        | Night                                                                                                                                                                     | Right to Left |
|------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/default.png" alt="DefaultModal" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/default_dm.png" alt="DefaultModal - dark mode" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-title-action_rtl.png" alt="DefaultModal - rtl mode" width="375" /> |

## Modal without action

| Day | Night                                                                                                                                                                                  | Right to Left |
| --- |----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/without-action.png" alt="ModalWithoutAction" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/without-action_dm.png" alt="ModalWithoutAction - dark mode" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-title-no-action_rtl.png" alt="ModalWithoutAction - rtl mode" width="375" /> |

## Modal without action and title

| Day                                                                                                                                                                                       | Night                                                                                                                                                                                                    | Right to Left |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/without-action-and-title.png" alt="ModalWithoutActionAndTitle" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/without-action-and-title_dm.png" alt="ModalWithoutActionAndTitle - dark mode" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-no-title-no-action_rtl.png" alt="ModalWithoutActionAndTitle - rtl mode" width="375" /> |

## Modal with back icon

| Day                                                                                                                                                               | Night                                                                                                                                                                            | Right to Left |
|-------------------------------------------------------------------------------------------------------------------------------------------------------------------|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/with-back.png" alt="ModalWithBackIcon" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/with-back_dm.png" alt="ModalWithBackIcon - dark mode" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Modal/screenshots/modal-back-icon_rtl.png" alt="ModalWithBackIcon - rtl mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Modal:

```Kotlin
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import net.skyscanner.backpack.compose.modal.BpkModal
import net.skyscanner.backpack.compose.modal.rememberBpkModalState

val showModal = rememberSaveable { mutableStateOf(true) }

if (showModal.value) {
    val modalState = rememberBpkModalState()
    val coroutineScope = rememberCoroutineScope()
    BpkModal(
        state = modalState,
        title = title,
        navIcon = NavIcon.Close(
            contentDescription = "Navigate back",
            onClick = { coroutineScope.launch { modalState.hide() } },
        ),
        action = TextAction(
            text = "Action",
            onClick = {
                coroutineScope.launch { modalState.hide() }
            },
        ),
        onDismiss = { showModal.value = false },
    ) {
        // modal content
    }
}
```

Note: to correctly show/hide the modal with animation use `rememberBpkModalState` and `modalState.hide()` as shown in the example above. The `onDismiss` callback will be called when the modal is fully dismissed.
