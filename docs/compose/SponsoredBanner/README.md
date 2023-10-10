# SponsoredBanner

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.sponsoredbanner)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/sponsoredbanner)

## OnLight

| Day                                                                                                                                                                               | Night                                                                                                                                                                                            |
|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/onlight.png" alt="SponsoredBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/onlight_dm.png" alt="SponsoredBanner component - dark mode" width="375" /> |

## OnDark

| Day                                                                                                                                                                              | Night                                                                                                                                                                                           |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/ondark.png" alt="SponsoredBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/ondark_dm.png" alt="SponsoredBanner component - dark mode" width="375" /> |

## Without Title and SubHeadline

| Day                                                                                                                                                                                                     | Night                                                                                                                                                                                                                  |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/without-title-and-subheadline.png" alt="SponsoredBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/without-title-and-subheadline_dm.png" alt="SponsoredBanner component - dark mode" width="375" /> |

## Without Logo

| Day                                                                                                                                                                                    | Night                                                                                                                                                                                                 |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/without-logo.png" alt="SponsoredBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/without-logo_dm.png" alt="SponsoredBanner component - dark mode" width="375" /> |

## Without Call To Action (CTA)

| Day                                                                                                                                                                                   | Night                                                                                                                                                                                                |
|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/without-cta.png" alt="SponsoredBanner component" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/SponsoredBanner/screenshots/without-cta_dm.png" alt="SponsoredBanner component - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a SponsoredBanner:

```Kotlin

import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBanner
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBannerCTA
import net.skyscanner.backpack.compose.sponsoredbanner.BpkSponsoredBannerVariant

{
    BpkSponsoredBanner(
        variant = BpkSponsoredBannerVariant.OnLight,
        title = title,
        subHeadline = subHeadline,
        callToAction = BpkSponsoredBannerCTA(
            ctaText,
            ctaAccessibilityLabel,
        ),
        body = body,
    ) {
        Image(
            painter = painterResource(R.drawable.sponsored_banner_skyland_white),
            contentDescription = contentDescription,
            contentScale = ContentScale.Fit,
        )
    }
}
```

Note: The Sponsored Banner is clickable/expandable if a body text is provided.
