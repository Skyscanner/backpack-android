package net.skyscanner.backpack.demo.meta

import androidx.compose.runtime.Composable

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Snapshot(
  vararg val variants: Variants = [Variants.Default, Variants.DarkMode, Variants.Rtl, Variants.Themed],
)

object Snapshots

data class SnapshotEntry(
  val name: String,
  val component: ComponentEntry,
  val variants: List<Variants>,
  val previewParameter: PreviewParameterInfo?,
  val content: @Composable () -> Unit,
)

enum class Variants {
  Default,
  DarkMode,
  Rtl,
  Themed,
}
