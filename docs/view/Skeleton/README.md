# Skeleton

## Installation

Backpack Android is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-android). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

The Skeleton components can be used in both XML and Kotlin/Java

Example of a Circle Skeleton with large size in XML

```xml
<net.skyscanner.backpack.skeleton.BpkCircleSkeleton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:circleSize="Large" />
```

Example of a Circle Skeleton with custom size in XML

```xml
<net.skyscanner.backpack.skeleton.BpkCircleSkeleton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:diameter="48dp" />
```

Example of a Image Skeleton with rounded corner in XML

```xml
<net.skyscanner.backpack.skeleton.BpkImageSkeleton
  android:layout_width="80dp"
  android:layout_height="80dp" 
  app:cornerType="Rounded" />
```

Example of a Headline Skeleton with medium size in XML

```xml
<net.skyscanner.backpack.skeleton.BpkHeadlineSkeleton
  android:layout_width="80dp"
  android:layout_height="wrap_content"
  app:skeletonSize="Medium" />
```

Example of a Headline Skeleton with custom size in XML

```xml
<net.skyscanner.backpack.skeleton.BpkHeadlineSkeleton
  android:layout_width="80dp"
  android:layout_height="48dp" />
```

Example of a Body Text Skeleton in XML

```xml
<net.skyscanner.backpack.skeleton.BpkBodyTextSkeleton
  android:layout_width="200dp"
  android:layout_height="wrap_content" />
```

Example of a Shimmer Skeleton with  in XML

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

Example of a Circle Skeleton with large size in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkCircleSkeleton

BpkCircleSkeleton(context).apply {
    size = BpkCircleSkeleton.CircleSize.Large
}
```

Example of a Circle Skeleton with custom size in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkCircleSkeleton

BpkCircleSkeleton(context).apply {
    diameter = context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl)
}
```

Example of a Image Skeleton with rounded corner in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkImageSkeleton

BpkImageSkeleton(context).apply {
    cornerType = BpkImageSkeleton.CornerType.Rounded
}
```

Example of a Headline Skeleton with medium size in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkHeadlineSkeleton

BpkHeadlineSkeleton(context).apply {
    size = BpkHeadlineSkeleton.SkeletonSize.Medium
}
```

Example of a Body Text Skeleton with large corner in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkBodyTextSkeleton

BpkBodyTextSkeleton(context)
```
Example of a Shimmer Overlay in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkShimmerOverlay
import net.skyscanner.backpack.skeleton.BpkImageSkeleton

val skeleton = BpkShimmerOverlay(context)
val image = BpkImageSkeleton(context)
skeleton.addView(image, context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl),
    context.resources.getDimensionPixelSize(R.dimen.bpkSpacingXl))
```
