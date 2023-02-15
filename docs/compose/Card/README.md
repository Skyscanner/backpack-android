# Card

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.card)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/card)

## Default

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Card/screenshots/default.png" alt="Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Card/screenshots/default_dm.png" alt="Card component - dark mode" width="375" /> |

## Divided Card

| Day                                                                                                                                                              | Night |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Card/screenshots/divided-card.png" alt="Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Card/screenshots/divided-card_dm.png" alt="Card component - dark mode" width="375" /> |

## Card Wrapper

| Day                                                                                                                                                              | Night |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------| --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Card/screenshots/card-wrapper.png" alt="Card component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Card/screenshots/card-wrapper_dm.png" alt="Card component - dark mode" width="375" /> |


## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Card with default (small) corner:

```Kotlin
import net.skyscanner.backpack.compose.card.BpkCard

BpkCard {
    // content
}
```

Example of a Card with large corner:

```Kotlin
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardCorner

BpkCard(corner = BpkCardCorner.Large) {
    // content
}
```

Example of a Card with no padding:

```Kotlin
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardPadding

BpkCard(padding = BpkCardPadding.None) {
    // content
}
```

Example of a clickable Card:

```Kotlin
import net.skyscanner.backpack.compose.card.BpkCard

BpkCard(
  onClick = {},
  onClickLabel = "Semantic and accessibility label",
) {
    // content
}
```

Example of a focused Card:

```Kotlin
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardElevation

BpkCard(elevation = BpkCardElevation.Focus) {
    // content
}
```

Example of a Divided Card:

```Kotlin
import net.skyscanner.backpack.compose.dividedcard.BpkDividedCard
import net.skyscanner.backpack.compose.dividedcard.dividedCardWidth

BpkDividedCard(
  modifier = Modifier.fillMaxWidth(),
  primaryContent = {
    Image(
      modifier = Modifier
        .height(BpkSpacing.Xxl * 2)
        .dividedCardWidth(BpkSpacing.Xxl),
      painter = painterResource(id = R.drawable.image),
      contentDescription = "content description",
      contentScale = ContentScale.Crop
    )
  },
  secondaryContent = {
    BpkText(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource("BpkDividedCard sample"),
    )
  },
  onClick = {}
)
```

Example of a Card Wrapper:

```Kotlin
import net.skyscanner.backpack.compose.cardwrapper.BpkCardWrapper
import net.skyscanner.backpack.compose.dividedcard.dividedCardWidth

BpkCardWrapper(
  modifier = Modifier.fillMaxWidth(),
  backgroundColor = BpkTheme.colors.coreEco,
  headerContent = {
    BpkText(
      modifier = Modifier
        .fillMaxWidth()
        .height(BpkSpacing.Xxl),
      text = stringResource("BpkCardWrapper header sample"),
    )
  },
  cardContent = {
    Image(
      modifier = Modifier
        .height(BpkSpacing.Xxl * 2)
        .fillMaxWidth(),
      painter = painterResource(id = R.drawable.canadian_rockies_canada),
      contentDescription = "",
      contentScale = ContentScale.Crop
    )
    BpkText(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource("BpkCardWrapper card sample"),
      style = BpkTheme.typography.bodyDefault,
      textAlign = TextAlign.Center
    )
  },
  onClick = {}
)
```
