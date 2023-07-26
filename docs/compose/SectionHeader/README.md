# SectionHeader

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.sectionheader)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/sectionheader)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SectionHeader/screenshots/default.png" alt="SectionHeader component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SectionHeader/screenshots/default_dm.png" alt="SectionHeader component - dark mode" width="375" /> |

## On Dark

| Day | Night |
| --- | --- |


## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

### Basic section header with a title.
If you don't specify a `style` parameter it will use the `.default` type

### Basic section header with a title.

```Kotlin
    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
    )
```

### Section header with a title and description.

```Kotlin
    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        description = stringResource(R.string.section_header_description),
    )
```

### Section header with a title, description and trailing button.

```Kotlin
    BpkSectionHeader(
        title = stringResource(R.string.section_header_title),
        description = stringResource(R.string.section_header_description),
        button = BpkSectionHeaderButton(
            text = stringResource(R.string.section_header_button_text),
            onClick = {},
        ),
    )
```

### Section header with a title, description, trailing button and onDark style.

```Kotlin
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
