# Text Spans

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.text)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/text)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/TextSpans/screenshots/default.png" alt="TextSpans component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/TextSpans/screenshots/default_dm.png" alt="TextSpans component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Backpack provides custom `Spans` for text customisation whenever you need it.


`BpkFontSpan` is designed to set `BpkText` font on a part of Spanned.
Here's an example:

```Kotlin
import net.skyscanner.backpack.text.BpkFontSpan

val span = BpkFontSpan(context, BpkText.TextStyle.Heading2)
textView.text = SpannableStringBuilder("Foo").append("bar", span, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
```

`BpkPrimaryColorSpan` is designed to set `bpkPrimaryColor` on a part of Spanned.
Here's an example:

```Kotlin
import net.skyscanner.backpack.text.BpkPrimaryColorSpan

val span = BpkPrimaryColorSpan(context)
textView.text = SpannableStringBuilder("Foo").append("bar", span, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
```

`BpkLinkSpan` is designed to set a clickable action on a part of Spanned.
Here's an example:

```Kotlin
import net.skyscanner.backpack.text.BpkLinkSpan

val span = BpkLinkSpan(context, "https://skyscanner.net") { openInBrowser(it) }
textView.text = SpannableStringBuilder("Foo").append("bar", span, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
```

## Theme Props

- `bpkPrimaryColor`
- [Font theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/Text/README.md)

Styles can be changed globally through `bpkTextStyle`. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/view/THEMING.md) for more information.
