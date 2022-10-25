# Dialog

[![Maven Central](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-android)](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/Backpack/net.skyscanner.backpack.dialog)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/Backpack/src/main/java/net/skyscanner/backpack/dialog)

## Delete confirmation

| Day | Night |
| --- | --- |
| ![Delete confirmation Dialog component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Dialog/screenshots/delete-confirmation.png) |![Delete confirmation Dialog component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Dialog/screenshots/delete-confirmation_dm.png) |

## With CTA

| Day | Night |
| --- | --- |
| ![With cta Dialog component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Dialog/screenshots/with-cta.png) |![With cta Dialog component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Dialog/screenshots/with-cta_dm.png) |

## With flare

| Day | Night |
| --- | --- |
| ![With flare Dialog component](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Dialog/screenshots/with-flare.png) |![With flare Dialog component - dark mode](https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/view/Dialog/screenshots/with-flare_dm.png) |

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Dialog component can only be used in Kotlin/Java

Example of dialog in Kotlin

```Kotlin
import net.skyscanner.backpack.dialog.BpkDialog
import net.skyscanner.backpack.dialog.BpkDialog.Type
import net.skyscanner.backpack.dialog.BpkDialog.Button

val dialog = BpkDialog(context, BpkDialog.Type.Success)
dialog.apply {
    title = "You are going to Tokyo!"
    description = "Your flight is all booked. Why not check out some hotels now?"
    icon = BpkDialog.Icon(R.drawable.bpk_tick)

    addActionButton(
      BpkDialog.Button("Continue") {
          println("confirmed")
          dialog.dismiss()
      }
    )

    addActionButton(
      BpkDialog.Button("Skip") {
        println("skipped")
        dialog.dismiss()
      }
    )
}
```

Example of flare dialog in Kotlin

```Kotlin
import net.skyscanner.backpack.dialog.BpkDialog
import net.skyscanner.backpack.dialog.BpkDialog.Type
import net.skyscanner.backpack.dialog.BpkDialog.Button

val dialog = BpkDialog(context, BpkDialog.Type.Flare)
dialog.apply {
    title = "You are going to Tokyo!"
    description = "Your flight is all booked. Why not check out some hotels now?"

    Picasso.get().load(url).into(image)

    addActionButton(
      BpkDialog.Button("Continue") {
        println("confirmed")
        dialog.dismiss()
      }
    )

    addActionButton(
      BpkDialog.Button("Skip") {
        println("skipped")
        dialog.dismiss()
      }
    )
}
```

Please use `DialogInterface.OnDismissListener` as the `DialogInterface.OnCancelListener` is not supported.
