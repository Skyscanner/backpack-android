# Gradient

## Installation

Backpack Android is available through [Jitpack](https://jitpack.io/#Skyscanner/backpack-android). Check the main [Readme](../../README.md#installation) for a complete installation guide.

## Usage

The Gradient drawable can only be used in Kotlin/Java:


```Kotlin
import net.skyscanner.backpack.gradient.BpkGradients

view.background = BpkGradients(
  context, 
  GradientDrawable.Orientation.BL_TR,
  intArrayOf(
    ContextCompat.getColor(testContext, R.color.bpkGreen500),
    ContextCompat.getColor(testContext, R.color.bpkGreen300))
  )
```

The default direction is `GradientDrawable.Orientation.TL_BR` and the default colours are Backpack's primary colours.

### Primary gradient

For primary gradients use the `getPrimary` function:

```Kotlin
import net.skyscanner.backpack.gradient.BpkGradients

view.background = BpkGradients.getPrimary(context)
```

The gradient returned by this function can be themed through the following theme props:

- `bpkPrimaryGradientColorStart`
- `bpkPrimaryGradientColorEnd`