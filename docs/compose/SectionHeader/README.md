# SectionHeader

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.sectionheader)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/sectionheader)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SectionHeader/screenshots/default.png" alt="SectionHeader component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SectionHeader/screenshots/default_dm.png" alt="SectionHeader component - dark mode" width="375" /> |

## On Dark

| Day                                                                                                                                                                          | Night                                                                                                                                                                                       |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SectionHeader/screenshots/ondark.png" alt="SectionHeader component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SectionHeader/screenshots/ondark_dm.png" alt="SectionHeader component - dark mode" width="375" /> |



## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

### Parameters

| Parameter | Type | Description |
| --- | --- | --- |
| `title` | `String` | Required text displayed as the section heading. |
| `modifier` | `Modifier` | Optional Compose modifier applied to the section header. |
| `description` | `String?` | Optional supporting text displayed below the title. Blank descriptions are not displayed. |
| `button` | `BpkSectionHeaderButton?` | Optional trailing action. Tablets display a text button, while smaller screens display an arrow icon using the button text as its content description. |
| `accessibilityHeaderTagEnabled` | `Boolean?` | Controls whether heading semantics are applied to the title. Defaults to `true`; `false` or `null` disables them. |
| `type` | `BpkSectionHeaderType` | Controls the component appearance. Defaults to `BpkSectionHeaderType.Default`. |

`BpkSectionHeaderButton` accepts:

SectionHeader adapts its trailing action by screen size. On tablets, it renders
a text button. On phones, it renders an arrow-only button and uses the
configured text as its accessibility content description.

| Parameter | Type | Description |
| --- | --- | --- |
| `text` | `String` | Label for the trailing action. Displayed as button text on tablets and used as the arrow-only button's content description on phones. |
| `onClick` | `() -> Unit` | Action invoked when the trailing button is selected. |

### Types

- `BpkSectionHeaderType.Default` uses the standard text colours and primary button style.
- `BpkSectionHeaderType.OnDark` uses on-dark text colours and the primary-on-dark button style.

### Basic section header with a title.

If you don't specify a `type` parameter it will use `BpkSectionHeaderType.Default`.

```kotlin
    import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader

    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
    )
```

### Section header with a title and description.

```kotlin
    import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader

    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        description = stringResource(R.string.section_header_description),
    )
```

### Section header with a title, description and trailing button.

```kotlin
    import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader
    import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton

    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        description = stringResource(R.string.section_header_description),
        button = BpkSectionHeaderButton(
            text = stringResource(R.string.section_header_button_text),
            onClick = {},
        ),
    )
```

### Accessibility heading semantics

The title is exposed as an accessibility heading by default. Set
`accessibilityHeaderTagEnabled` to `false` when another element already
provides the appropriate heading semantics.

```kotlin
    import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader

    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        accessibilityHeaderTagEnabled = false,
    )
```

### Section header with a title, description, trailing button and OnDark type.

```kotlin
    import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeader
    import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderButton
    import net.skyscanner.backpack.compose.sectionheader.BpkSectionHeaderType.OnDark

    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        description = stringResource(R.string.section_header_description),
        button = BpkSectionHeaderButton(
            text = stringResource(R.string.section_header_button_text),
            onClick = {},
        ),
        type = OnDark,
    )
```

## Cross-platform naming

The appearance parameter is named `type` on Android and `style` on iOS.
Both parameters provide equivalent default and on-dark appearance options.
The platforms retain their existing names to follow their established API
conventions and avoid source-breaking changes for existing consumers.
