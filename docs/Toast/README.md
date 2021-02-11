# Toast

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Toast component can be used in Kotlin/Java

```Kotlin
import net.skyscanner.backpack.toast.BpkToast

BpkToast.makeText(context, "Message", BpkToast.LENGTH_SHORT).show()
```

The fonts will be applied globally from the current theme. Check [theming](https://github.com/Skyscanner/backpack-android/blob/main/docs/THEMING.md) for more information.
