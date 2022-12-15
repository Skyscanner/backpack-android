# Inventory Divided Card

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.inventorydividedcard)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/inventorydividedcard)

## Default

| Day                                                                                                                                                                                         | Night                                                                                                                                                                                                      |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InventoryDividedCard/screenshots/default.png" alt="InventoryDividedCard component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InventoryDividedCard/screenshots/default_dm.png" alt="InventoryDividedCard component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of an Inventory Divided Card:

```Kotlin
import net.skyscanner.backpack.compose.inventorydividedcard.BpkInventoryDividedCard
import net.skyscanner.backpack.compose.inventorydividedcard.inventoryDividedCardWidth

BpkInventoryDividedCard(
  modifier = Modifier.fillMaxWidth(),
  primaryContent = {
    Image(
      modifier = Modifier
        .height(BpkSpacing.Xxl * 2)
        .inventoryDividedCardWidth(BpkSpacing.Xxl),
      painter = painterResource(id = R.drawable.image),
      contentDescription = "",
      contentScale = ContentScale.Crop
    )
  },
  secondaryContent = {
    BpkText(
      modifier = Modifier.fillMaxWidth(),
      text = stringResource("BpkInventoryDividedCard sample"),
    )
  },
  onClick = {}
)
```
