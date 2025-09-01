# Link

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.link)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/link)

## Link

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Link/screenshots/default.png" alt="Link component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Link/screenshots/default_dm.png" alt="Link component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Link component supports two approaches for creating clickable text:

### Markdown Strings

Use familiar `[text](url)` syntax for simple cases:

```Kotlin
BpkLink(
    text = "Visit [Skyscanner](https://www.skyscanner.net) for great deals",
    onLinkClicked = { url ->
        webView.loadUrl(url)
    }
)
```

### Type-Safe Builder

Use `buildTextSegments` for programmatic construction:

```Kotlin
BpkLink(
    segments = buildTextSegments {
        text("Explore ")
        link("flights", "https://www.skyscanner.net/flights")
        text(" and ")
        link("hotels", "https://www.skyscanner.net/hotels")
    },
    onLinkClicked = { url ->
        webView.loadUrl(url)
    }
)
```


### Link Styles

```Kotlin
// Default style (light backgrounds)
BpkLink(
    text = "[Link](https://example.com)",
    style = BpkLinkStyle.Default,
    onLinkClicked = { url -> webView.loadUrl(url) }
)

// OnContrast style (dark backgrounds)
BpkLink(
    text = "[Link](https://example.com)",
    style = BpkLinkStyle.OnContrast,
    onLinkClicked = { url -> webView.loadUrl(url) }
)
```

## Parameters

| Property | PropType | Required | Default Value |
| -------- | -------- | -------- | ------------- |
| text | String | ✓ | - |
| segments | List<TextSegment> | ✓ | - |
| onLinkClicked | (String) -> Unit | ✓ | - |
| modifier | Modifier | - | Modifier |
| textStyle | TextStyle | - | LocalTextStyle.current |
| style | BpkLinkStyle | - | BpkLinkStyle.Default |

*Note: Use either `text` for markdown or `segments` for type-safe builder approach.*

## buildTextSegments

The `buildTextSegments` function provides a type-safe DSL for creating text with links:

```Kotlin
buildTextSegments {
    text("Visit ")           // Plain text
    link("Skyscanner", url)  // Clickable link  
    text(" for deals")
}
```

By default, `autoSpace` is `true`, which automatically adds spaces between segments. To disable automatic spacing:

```Kotlin
buildTextSegments(autoSpace = false) {
    text("Visit")
    link("Skyscanner", url)
    text("for deals")
}
```

## BpkLinkStyle Enum

```Kotlin
enum class BpkLinkStyle {
    Default,    // For light backgrounds
    OnContrast  // For dark backgrounds
}
```

## Accessibility

The component automatically handles accessibility by:

- Setting the appropriate semantic role for clickable text
- Providing content descriptions for screen readers
- Supporting proper click actions and focus handling
- Following platform accessibility guidelines
- Maintaining text contrast ratios for both link styles