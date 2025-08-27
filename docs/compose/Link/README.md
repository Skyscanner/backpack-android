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

The Link component is designed to display clickable text links within text content. It supports both simple links and mixed text with multiple links.

### Simple Link with Mixed Text

For a single clickable link, you can use the mixed text approach:

```Kotlin
BpkLink(
    text = listOf(
        TextType.LinkText("Terms and Conditions", "https://www.skyscanner.net/terms")
    ),
    onLinkClicked = { url ->
        // Handle link click
        webView.loadUrl(url)
    }
)
```

### Simple Link (without URL)

```Kotlin
BpkLink(
    text = "Click here",
    onLinkClicked = {
        // Handle click action without URL
        navigateToScreen()
    }
)
```

### Mixed Text with Links

For paragraphs containing both regular text and clickable links:

```Kotlin
BpkLink(
    text = listOf(
        TextType.RawText("By signing up, you agree to our "),
        TextType.LinkText("Terms of Service", "https://www.skyscanner.net/terms"),
        TextType.RawText(" and "),
        TextType.LinkText("Privacy Policy", "https://www.skyscanner.net/privacy"),
    ),
    onLinkClicked = { url ->
        // Handle link clicks
        webView.loadUrl(url)
    }
)
```

### Link Styles

The component supports two visual styles:

```Kotlin
// Default style (for light backgrounds)
BpkLink(
    text = listOf(
        TextType.LinkText("Default Link", "https://www.skyscanner.net")
    ),
    style = BpkLinkStyle.Default,
    onLinkClicked = { url -> /* Handle click */ }
)

// On contrast style (for dark backgrounds)
BpkLink(
    text = "Contrast Link",
    style = BpkLinkStyle.OnContrast,
    onLinkClicked = { /* Handle click without URL */ }
)
```

## Parameters

### BpkLink (with list of texts)

| Property | PropType | Required | Default Value |
| -------- | -------- | -------- | ------------- |
| text | List<TextType> | ✓ | - |
| onLinkClicked | (Int) -> Unit | ✓ | - |
| modifier | Modifier | - | Modifier |
| textStyle | TextStyle | - | LocalTextStyle.current |
| style | BpkLinkStyle | - | BpkLinkStyle.Default |

### BpkLink (simple text)

| Property | PropType | Required | Default Value |
| -------- | -------- | -------- | ------------- |
| text | String | ✓ | - |
| onLinkClicked | () -> Unit | ✓ | - |
| modifier | Modifier | - | Modifier |
| textStyle | TextStyle | - | LocalTextStyle.current |
| style | BpkLinkStyle | - | BpkLinkStyle.Default |

## TextType Sealed Class

The `TextType` sealed class is used to define different types of text content:

```Kotlin
sealed class TextType {
    abstract val value: String

    data class RawText(override val value: String) : TextType()
    data class LinkText(
        override val value: String,
        val url: String,
    ) : TextType()
}
```

- **RawText**: Regular, non-clickable text
- **LinkText**: Clickable text with an associated URL

## BpkLinkStyle Enum

```Kotlin
enum class BpkLinkStyle {
    Default,    // For light backgrounds
    OnContrast  // For dark backgrounds
}
```

## Accessibility

The component automatically handles accessibility by:

- Setting the appropriate semantic role as `Role.Button`
- Providing content descriptions for screen readers
- Supporting proper click actions
- Following platform accessibility guidelines
