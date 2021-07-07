# Dialog

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

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
        context.getColor(R.color.bpkMonteverde),
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

Example of flare dialog in Kotlin

```Kotlin
import net.skyscanner.backpack.dialog.BpkDialog

val dialog = BpkDialog(context, BpkDialog.Style.FLARE)
dialog.apply {
    title = "You are going to Tokyo!"
    description = "Your flight is all booked. Why not check out some hotels now?"

    Picasso.get().load(url).into(image)

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

Please use `DialogInterface.OnDismissListener` as the `DialogInterface.OnCancelListener` is not supported.
