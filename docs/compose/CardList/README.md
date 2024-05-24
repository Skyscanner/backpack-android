# CardList

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.cardlist)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/cardlist)

## Rail

| Day                                                                                                                                                              | Night                                                                                                                                                                           |
|------------------------------------------------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardList/screenshots/rail.png" alt="CardList component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardList/screenshots/rail_dm.png" alt="CardList component - dark mode" width="375" /> |

## Rail with section header button

| Day                                                                                                                                                                              | Night                                                                                                                                                                                           |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardList/screenshots/rail-with-headerbutton.png" alt="CardList component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/CardList/screenshots/rail-with-headerbutton_dm.png" alt="CardList component - dark mode" width="375" /> |

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a CardList:

## Rail

```Kotlin
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import net.skyscanner.backpack.compose.cardlist.BpkCardList
import net.skyscanner.backpack.compose.snippet.BpkSnippet

private val dataList = cardListSamples()
private const val CARD_LAYOUT_WIDTH = 281

BpkCardList(
    title = stringResource(R.string.card_list_title),
    description = stringResource(R.string.card_list_description),
    layout = BpkCardListLayout.Rail(),
    modifier = Modifier,
    dataList = dataList,
) { position ->
    CardLayout(dataList[position])
}

@Composable
private fun CardLayout(data: CardListSample) {
    BpkSnippet(
        modifier = Modifier.width(CARD_LAYOUT_WIDTH.dp),
        imageOrientation = ImageOrientation.Landscape,
        headline = stringResource(data.headline),
        bodyText = stringResource(data.bodyText),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(data.image),
            contentDescription = stringResource(R.string.snippet_image_content_description),
            contentScale = ContentScale.Crop,
        )
    }
}

data class CardListSample(
    @DrawableRes val image: Int,
    @StringRes val headline: Int,
    @StringRes val bodyText: Int,
)
```

## Rail with section header button

```Kotlin
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.width
import net.skyscanner.backpack.compose.cardlist.BpkCardList
import net.skyscanner.backpack.compose.snippet.BpkSnippet

private val dataList = cardListSamples()
private const val CARD_LAYOUT_WIDTH = 281

BpkCardList(
    title = stringResource(R.string.card_list_title),
    description = stringResource(R.string.card_list_description),
    layout = BpkCardListLayout.Rail(
        headerButton = BpkCardListButtonAccessory.SectionHeaderButton(
            text = stringResource(R.string.card_list_header_button_text),
            onClick = {},
        )),
    modifier = Modifier,
    dataList = dataList,
) { position ->
    CardLayout(dataList[position])
}

@Composable
private fun CardLayout(data: CardListSample) {
    BpkSnippet(
        modifier = Modifier.width(CARD_LAYOUT_WIDTH.dp),
        imageOrientation = ImageOrientation.Landscape,
        headline = stringResource(data.headline),
        bodyText = stringResource(data.bodyText),
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(data.image),
            contentDescription = stringResource(R.string.snippet_image_content_description),
            contentScale = ContentScale.Crop,
        )
    }
}

data class CardListSample(
    @DrawableRes val image: Int,
    @StringRes val headline: Int,
    @StringRes val bodyText: Int,
)
```
