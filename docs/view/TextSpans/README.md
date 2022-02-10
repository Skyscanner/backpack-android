# Text Spans

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
