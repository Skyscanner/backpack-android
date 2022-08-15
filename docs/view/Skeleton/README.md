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

Example of a Image Skeleton with rounded corner in XML

```xml
<net.skyscanner.backpack.skeleton.BpkImageSkeleton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content" 
  app:cornerType="Rounded" />
```

Example of a Headline Skeleton with medium size in XML

```xml
<net.skyscanner.backpack.skeleton.BpkHeadlineSkeleton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:skeletonSize="Medium" />
```

Example of a Body Text Skeleton in XML

```xml
<net.skyscanner.backpack.skeleton.BpkBodyTextSkeleton
  android:layout_width="wrap_content"
  android:layout_height="wrap_content"
  app:skeletonSize="Large" />
```

Example of a Circle Skeleton with large size in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkCircleSkeleton

BpkCircleSkeleton(context).apply {
    size = BpkCircleSkeleton.CircleSize.Large
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
import net.skyscanner.backpack.skeleton.BpkSkeletonBase

BpkHeadlineSkeleton(context).apply {
    size = BpkSkeletonBase.SkeletonSize.Medium
}
```

Example of a Body Text Skeleton with large corner in Kotlin

```Kotlin
import net.skyscanner.backpack.skeleton.BpkBodyTextSkeleton
import net.skyscanner.backpack.skeleton.BpkSkeletonBase

BpkBodyTextSkeleton(context).apply {
    size = BpkSkeletonBase.SkeletonSize.Large
}
```
