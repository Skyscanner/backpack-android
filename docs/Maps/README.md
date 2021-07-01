# Map

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Backpack provides custom markers for Google Maps SDK on Android.

Due to the nature of Google Maps SDK it's not possible to use Views directly on a map.
Instead we rasterize each marker to bitmap and render it.

In order to add map maker, you need to call `GoogleMap.addBpkMarker` method.

Here's an example of it in Kotlin.

```Kotlin
  import net.skyscanner.backpack.map.addBpkMarker

  googleMaps.addBpkMarker(
    context = context,
    position = LatLng(0.0, 0.0),
    title = "Badge title",
    icon = iconToBeDrawnOnBadge,
    pointerOnly = false,
  )
```

When `pointerOnly` set to true, only the pointer (circle) will be drawn initially,
and the badge appears only when user clicks on it.

The `icon` may be set to 0 if there's no icon to render on badge.

Please be aware that this method rasterize View to Bitmap and therefore too many markers created may lead to memory overflow.

In order to display selected markers correctly, you also need to retrieve `GoogleMap` using Backpack wrapper method:

```Kotlin
import net.skyscanner.backpack.map.addBpkMarker
import net.skyscanner.backpack.map.getBpkMapAsync

mapView.getBpkMapAsync { map ->
    map.addBpkMarker(
    // ...
    )
}
```

or

```Kotlin
import net.skyscanner.backpack.map.addBpkMarker
import net.skyscanner.backpack.map.getBpkMapAsync

supportMapFragment.getBpkMapAsync { map ->
    map.addBpkMarker(
    // ...
    )
}
```
