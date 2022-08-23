# Skeleton

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage
Skeleton components is built for an composition with shimmer animation. It is often used for a loading screen or loading cards.
There is a shimmer overlay which is to wrap the whole composition.
There are 4 base elements with pre-selected size or custom size support: image skeleton, circle skeleton, headline skeleton and body text skeleton,
they represent the real block as their name in a composition.
You should put the whole composition into a shimmer overlay to add shimmer animation.

The Skeleton components can be used in both XML and Kotlin/Java

Shimmer overlay support to add a child with component tree, or to include another layout XML file.

Example of a shimmer overlay with  in XML

```xml
<net.skyscanner.backpack.skeleton.BpkShimmerOverlay
  android:layout_width="200dp"
  android:layout_height="wrap_content">
    <net.skyscanner.backpack.skeleton.BpkBodyTextSkeleton
        android:layout_width="match_parent"
        android:layout_height="wrap_content" />
</net.skyscanner.backpack.skeleton.BpkShimmerOverlay>
```

```xml
<net.skyscanner.backpack.skeleton.BpkShimmerOverlay
  android:layout_width="200dp"
  android:layout_height="wrap_content">
    <include layout="@layout/skeleton_overlay_sample" />
</net.skyscanner.backpack.skeleton.BpkShimmerOverlay>
```

Circle skeleton have pre-selected size. 
`skeletonCircleSize`: `small(32 dp), large(48 dp), custom`.
You could also specify a custom size by `skeletonDiameter`.

Example of a circle Skeleton with large size in XML

```xml
<net.skyscanner.backpack.skeleton.BpkCircleSkeleton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:skeletonCircleSize="large" />
```

Example of a circle skeleton with custom size in XML

```xml
<net.skyscanner.backpack.skeleton.BpkCircleSkeleton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:skeletonCircleSize="custom"
  app:skeletonDiameter="48dp" />
```

Image skeleton requires width and height dimension, you should specify them to get an expected size.

Example of a Image Skeleton with rounded corner in XML

```xml
<net.skyscanner.backpack.skeleton.BpkImageSkeleton
  android:layout_width="80dp"
  android:layout_height="80dp" 
  app:skeletonCornerType="rounded" />
```

Headline skeleton have pre-selected height. 
`skeletonHeadlineHeightSize`: `small(8 dp), medium(16 dp), large(32 dp), custom`.
Please set height to `wrap_content` if you want to use pre-selected height otherwise you could specify a custom height.
If you want to set the height to a custom size, please set `skeletonHeadlineHeightSize` to custom and then set height to a dimension value.
You should specify the width to any size to keep consistent with your design.

Example of a headline skeleton with medium size in XML

```xml
<net.skyscanner.backpack.skeleton.BpkHeadlineSkeleton
  android:layout_width="80dp"
  android:layout_height="wrap_content"
  app:skeletonHeadlineHeightSize="medium" />
```

Example of a headline skeleton with custom size in XML

```xml
<net.skyscanner.backpack.skeleton.BpkHeadlineSkeleton
  android:layout_width="80dp"
  android:layout_height="48dp" 
  app:skeletonHeadlineHeightSize="custom" />
```

Body text skeleton have its own height(`40 dp`), you should never to reset it. You should only specify width for it, and set height to `wrap_content`.

Example of a body text skeleton in XML

```xml
<net.skyscanner.backpack.skeleton.BpkBodyTextSkeleton
  android:layout_width="200dp"
  android:layout_height="wrap_content" />
```

Example of a shimmer overlay in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkShimmerOverlay
import net.skyscanner.backpack.skeleton.BpkImageSkeleton

val skeleton = BpkShimmerOverlay(context)
val image = BpkImageSkeleton(context)
skeleton.addView(image, context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl),
  context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl))
```


Example of a circle skeleton with large size in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkCircleSkeleton

BpkCircleSkeleton(context).apply {
  size = BpkCircleSkeleton.CircleSize.Large
}
```

Example of a circle skeleton with custom size in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkCircleSkeleton

BpkCircleSkeleton(context).apply {
  diameter = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl)
}
```

Example of a image skeleton with rounded corner in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkImageSkeleton

BpkImageSkeleton(context).apply {
  cornerType = BpkImageSkeleton.CornerType.Rounded
}
```

Example of a headline skeleton with medium size in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkHeadlineSkeleton

BpkHeadlineSkeleton(context).apply {
  heightSize = BpkHeadlineSkeleton.SkeletonHeightSizeType.Medium
}
```

Example of a body text skeleton with large corner in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkBodyTextSkeleton

BpkBodyTextSkeleton(context)
```
