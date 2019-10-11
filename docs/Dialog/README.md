# Dialog

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Dialog component can only be used in Kotlin/Java

Example of dialog in Kotlin

```Kotlin
import net.skyscanner.backpack.dialog.BpkDialog

val dialog = BpkDialog(context, BpkDialog.Style.ALERT)
dialog.apply {
    title = "You are going to Tokyo!"
    description = "Your flight is all booked. Why not check out some hotels now?"
    icon = BpkDialog.Icon(
        R.drawable.bpk_tick,
        R.color.bpkMonteverde
    )

    addActionButton(BpkButton(context).apply {
        text = "Continue"
        setOnClickListener({
            println("confirmed")
            dialog.dismiss()
        })
    })

    addActionButton(BpkButton(context).apply {
        text = "Skip"
        type = BpkButton.Type.Secondary
        setOnClickListener({
            println("skipped")
            dialog.dismiss()
        })
    })
}
```
