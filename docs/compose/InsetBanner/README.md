# InsetBanner

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.insetbanner)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/insetbanner)

## OnLight

| Day                                                                                                                                                                               | Night                                                                                                                                                                                            |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/onlight.png" alt="InsetBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/onlight_dm.png" alt="InsetBanner component - dark mode" width="375" /> |

## OnDark

| Day                                                                                                                                                                              | Night                                                                                                                                                                                           |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/ondark.png" alt="InsetBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/ondark_dm.png" alt="InsetBanner component - dark mode" width="375" /> |

## Without Title and SubHeadline

| Day                                                                                                                                                                                                     | Night                                                                                                                                                                                                                  |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/without-title-and-subheadline.png" alt="InsetBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/without-title-and-subheadline_dm.png" alt="InsetBanner component - dark mode" width="375" /> |

## Without Logo

| Day                                                                                                                                                                                    | Night                                                                                                                                                                                                 |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/without-logo.png" alt="InsetBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/without-logo_dm.png" alt="InsetBanner component - dark mode" width="375" /> |

## Without Call To Action (CTA)

| Day                                                                                                                                                                                   | Night                                                                                                                                                                                                |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/without-cta.png" alt="InsetBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/InsetBanner/screenshots/without-cta_dm.png" alt="InsetBanner component - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a InsetBanner:

```Kotlin

import net.skyscanner.backpack.compose.insetbanner.BpkInsetBanner
import net.skyscanner.backpack.compose.insetbanner.BpkInsetBannerCTA
import net.skyscanner.backpack.compose.insetbanner.BpkInsetBannerVariant

{
    BpkInsetBanner(
        variant = BpkInsetBannerVariant.OnLight,
        title = title,
        subHeadline = subHeadline,
        callToAction = BpkInsetBannerCTA(
            ctaText,
            ctaAccessibilityLabel,
        ),
        body = body,
    ) {
        Image(
            painter = painterResource(R.drawable.inset_banner_skyland_white),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
        )
    }
}
```

Note: The Inset Banner is clickable/expandable if a body text is provided.
