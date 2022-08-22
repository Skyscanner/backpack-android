# Skeleton

## Installation

Backpack Compose is available through [Maven Central](https://search.maven.org/artifact/net.skyscanner.backpack/backpack-compose). Check the main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a Image Skeleton:

```Kotlin
import net.skyscanner.backpack.compose.sleketon.BpkImageSkeleton

BpkImageSkeleton(modifier = Modifier.size(BpkSpacing.Xxl.times(2), BpkSpacing.Xxl.times(2)),
  cornerType = BpkSkeletonCornerType.Rounded)
```

Example of a Circle Skeleton:

```Kotlin
import net.skyscanner.backpack.compose.sleketon.BpkCircleSkeleton

Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceAround
) {
  BpkCircleSkeleton(circleSize = BpkCircleSizeType.Custom(BpkSpacing.Xl))
  BpkCircleSkeleton(circleSize = BpkCircleSizeType.Large)
}
```

Example of a Headline Skeleton:

```Kotlin
import net.skyscanner.backpack.compose.sleketon.BpkHeadlineSkeleton

Row(
  modifier = Modifier.fillMaxWidth(),
  verticalAlignment = Alignment.CenterVertically,
  horizontalArrangement = Arrangement.SpaceEvenly
) {
  BpkHeadlineSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)), BpkSkeletonSizeType.Small)
  BpkHeadlineSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)), BpkSkeletonSizeType.Medium)
  BpkHeadlineSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(2)).height(50.dp))
}
```

Example of a Body Text Skeleton:

```Kotlin
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton

BpkBodyTextSkeleton(modifier = Modifier.width(BpkSpacing.Xxl.times(5)))
```

Example of a Shimmer Overlay:

```Kotlin
import net.skyscanner.backpack.compose.sleketon.BpkImageSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkShimmerOverlay

BpkShimmerOverlay(modifier = Modifier.width(200.dp).wrapContentHeight()){
  Row() {
    BpkImageSkeleton(modifier = Modifier.size(BpkSpacing.Xxl, BpkSpacing.Xxl))
    BpkBodyTextSkeleton(modifier = Modifier.padding(start=BpkSpacing.Md).width(152.dp))
  }
}
```
