package net.skyscanner.backpack.demo.meta

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameterProvider

data class Meta(
  val componentInfo: ComponentInfo,
  val storyInfo: StoryInfo?,
  val snapshotInfo: SnapshotInfo?,
  val parameters: ParametersInfo?,
  val sampleInfo: SampleInfo?,
  val content: @Composable () -> Unit,
) {

  data class SnapshotInfo(
    val variants: List<Variants>,
  )

  data class ComponentInfo(
    val name: String,
    val link: String,
    val kind: Kind,
  )

  data class StoryInfo(
    val name: String,
    val screenshot: Boolean = true,
  )

  data class ParametersInfo(
    val name: String,
    val provider: PreviewParameterProvider<*>,
  )

  data class SampleInfo(
    val kDocs: String,
    val body: String,
  )
}
