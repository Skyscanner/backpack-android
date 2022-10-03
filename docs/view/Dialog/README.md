# Dialog

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
