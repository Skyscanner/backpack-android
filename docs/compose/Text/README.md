# Text

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a text composable with `label1` style and secondary text color

```Kotlin
import androidx.compose.runtime.Composable
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

@Composable
fun FlightText() {
  BpkText(
    text = "Flights to Edinburgh",
    style = BpkTheme.typography.label1,
    color = BpkTheme.colors.textSecondary,
  )
}
```

Example of a text composable with annotated text

```Kotlin
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.theme.BpkTheme

BpkText(
  text = buildAnnotatedString {
    append("Sample ")
    withStyle(style = SpanStyle(color = BpkTheme.colors.textSecondary)) {
      append("Text")
    }
  }
)
```
