# Bottom Sheet

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.bottomsheet)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/bottomsheet)

## Default

| Day | Night |
| --- | --- |
| ![BottomSheet component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/BottomSheet/screenshots/default.png) |![BottomSheet component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/BottomSheet/screenshots/default_dm.png) |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Bottom Sheet component can be used in both XML and Kotlin/Java.
In order to work correctly, it needs to be placed inside [CoordinatorLayout](https://developer.android.com/reference/androidx/coordinatorlayout/widget/CoordinatorLayout).

Example of a Bottom Sheet in XML

```xml
<androidx.coordinatorlayout.widget.CoordinatorLayout
  xmlns:android="http://schemas.android.com/apk/res/android"
  xmlns:app="http://schemas.android.com/apk/res-auto"
  android:layout_width="match_parent"
  android:layout_height="match_parent">

  <FrameLayout
    android:layout_width="match_parent"
    android:layout_height="match_parent" >

    <!-- Non-bottom sheet content -->

  </FrameLayout>

  <net.skyscanner.backpack.bottomsheet.BpkBottomSheet
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    app:behavior_peekHeight="180dp"
    app:layout_behavior="net.skyscanner.backpack.bottomsheet.BpkBottomSheetBehaviour">

    <!-- Bottom sheet content -->

  </net.skyscanner.backpack.bottomsheet.BpkBottomSheet>
```

Example of a Bottom Sheet in Kotlin

```Kotlin
import android.view.View
import android.widget.FrameLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout.*

  val root = CoordinatorLayout(context)

  val content = FrameLayout(context)
  content.layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
  root.addView(content)

  val bottomSheet = BpkBottomSheet(context)
  val bottomSheetParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT)
  val bottomSheetBehaviour = BpkBottomSheetBehaviour<BpkBottomSheet>()
  bottomSheetBehaviour.peekHeight = bottomSheet.resources.getDimensionPixelSize(R.dimen.bpkSpacingXxl)
  bottomSheetParams.behavior = bottomSheetBehaviour
  bottomSheet.layoutParams = bottomSheetParams
  root.addView(bottomSheet)

  val bottomSheetContent: View = //... init the bottom sheet content
  bottomSheet.addView(bottomSheetContent)
```
