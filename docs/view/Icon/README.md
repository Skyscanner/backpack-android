# Icon

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)

## All

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Icon/screenshots/all.png" alt="Icon component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Icon/screenshots/all_dm.png" alt="Icon component - dark mode" width="375" /> |

## Small

| Day | Night |
| --- | --- |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Icon/screenshots/small.png" alt="Sm Icon component" width="375" /> |<img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Icon/screenshots/small_dm.png" alt="Sm Icon component - dark mode" width="375" /> |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main Readme for a complete installation guide.

## Usage

Icons are converted to image vectors and are available as drawables.

They can be used in code and XML.

Example usage in XML

```
<ImageView
  android:layout_width="16dp"
  android:tint="@color/bpkSkyGray"
  android:layout_margin="@dimen/bpkSpacingSm"
  app:srcCompat="@drawable/bpk_flight"
  android:layout_height="16dp" />
```

Example usage in Kotlin

```
ImageView(context).apply {
  setImageDrawable(AppCompatResources.getDrawable.getDrawable(context, R.drawable.bpk_flag))
}
```
