package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.sleketon.BpkBodyTextSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkCircleSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkCircleSkeletonSizeType
import net.skyscanner.backpack.compose.sleketon.BpkHeadlineSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkImageSkeleton
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonCornerType
import net.skyscanner.backpack.compose.sleketon.BpkSkeletonSizeType
import net.skyscanner.backpack.compose.tokens.BpkSpacing

@Composable
fun SkeletonStory() {
  Column(
    modifier = Modifier.padding(BpkSpacing.Base),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Xl)
  ) {
    CircleSkeletonExample()
    ImageSkeletonExample()
    HeadlineSkeletonExample()
    BodyTextSkeletonExample()
  }
}

@Composable
@Preview
fun ImageSkeletonExample() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    BpkImageSkeleton(cornerType = BpkSkeletonCornerType.Square)
    BpkImageSkeleton(cornerType = BpkSkeletonCornerType.Rounded)
  }
}

@Composable
@Preview
fun CircleSkeletonExample() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceAround
  ) {
    BpkCircleSkeleton()
    BpkCircleSkeleton(circleSize = BpkCircleSkeletonSizeType.Large)
  }
}

@Composable
@Preview
fun BodyTextSkeletonExample() {
  Column(
    modifier = Modifier.fillMaxWidth(),
    verticalArrangement = Arrangement.spacedBy(BpkSpacing.Base)
  ) {
    BpkBodyTextSkeleton()
    BpkBodyTextSkeleton(skeletonSize = BpkSkeletonSizeType.Medium)
    BpkBodyTextSkeleton(skeletonSize = BpkSkeletonSizeType.Large)
  }
}

@Composable
@Preview
fun HeadlineSkeletonExample() {
  Row(
    modifier = Modifier.fillMaxWidth(),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.SpaceEvenly
  ) {
    BpkHeadlineSkeleton()
    BpkHeadlineSkeleton(skeletonSize = BpkSkeletonSizeType.Medium)
    BpkHeadlineSkeleton(skeletonSize = BpkSkeletonSizeType.Large)
  }
}
