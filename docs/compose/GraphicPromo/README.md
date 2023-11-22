# GraphicPromo

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.graphicpromotion)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/graphicpromotion)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/default.png" alt="GraphicPromo component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/default_dm.png" alt="GraphicPromo component - dark mode" width="375" /> |

## Top aligned with kicker and sub headline

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/top-align-with-text.png" alt="GraphicPromo component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/top-align-with-text_dm.png" alt="GraphicPromo component - dark mode" width="375" /> |


## Top aligned Sponsored

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/top-align-sponsored.png" alt="GraphicPromo component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/top-align-sponsored_dm.png" alt="GraphicPromo component - dark mode" width="375" /> |

## Bottom aligned with kicker and sub headline

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/bottom-align-with-text.png" alt="GraphicPromo component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/bottom-align-with-text_dm.png" alt="GraphicPromo component - dark mode" width="375" /> |


## Bottom aligned Sponsored

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/bottom-align-sponsored.png" alt="GraphicPromo component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/GraphicPromo/screenshots/bottom-align-sponsored_dm.png" alt="GraphicPromo component - dark mode" width="375" /> |


## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a GraphicPromo:

```Kotlin
import android.util.Log
import net.skyscanner.backpack.compose.graphicpromotion.BpkGraphicPromo
import net.skyscanner.backpack.compose.graphicpromotion.Sponsor
import net.skyscanner.backpack.compose.graphicpromotion.VerticalAlignment
import net.skyscanner.backpack.compose.overlay.BpkOverlayType


BpkGraphicPromo(
    kicker = "Travel tips",
    headline = "Three Parks Challenge",
    subHeadline = "How to complete the climb in 3 days",
    verticalAlignment = VerticalAlignment.Bottom,
    overlayType = BpkOverlayType.SolidHigh,
    sponsor = BpkGraphicsPromoSponsor(
        accessibilityLabel = "Sponsored",
        logo = "https://images.kiwi.com/airlines/64/FR.png",
        title = "Sponsored",
    ),
    image = {
        Image(
            modifier = Modifier.matchParentSize(),
            painter = painterResource(id = R.drawable.graphic_promo),
            contentDescription = "Image",
            contentScale = ContentScale.Crop,
        )
    }, sponsorLogo = {
        Image(
            painter = painterResource(id = R.drawable.skyland,),
            contentDescription = "Image",
            contentScale = ContentScale.Fit,
        )
    },
    tapAction = {
        Log.d("TAG", "Tap on graphic promo")
    },
)
```
