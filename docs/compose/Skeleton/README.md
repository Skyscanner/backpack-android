# Skeleton

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Image Skeleton:

```Kotlin
import net.skyscanner.backpack.compose.sleketon.BpkImageSkeleton

BpkImageSkeleton(
    cornerType = BpkSkeletonCornerType.Rounded,
)
```

Example of a Circle Skeleton:

```Kotlin
import net.skyscanner.backpack.compose.sleketon.BpkCircleSkeleton

BpkCircleSkeleton(
    circleSize = BpkCircleSkeletonSizeType.Large,
)
```

Example of a Headline Skeleton:

```Kotlin
import net.skyscanner.backpack.compose.sleketon.BpkHeadlineSkeleton

BpkHeadlineSkeleton(
    skeletonSize = BpkSkeletonSizeType.Small,
)
```

Example of a Body Text Skeleton:

```Kotlin
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton

BpkBodyTextSkeleton(
    skeletonSize = BpkSkeletonSizeType.Medium,
)
```
