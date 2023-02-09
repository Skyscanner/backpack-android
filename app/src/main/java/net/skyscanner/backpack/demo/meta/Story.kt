package net.skyscanner.backpack.demo.meta

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable

@Immutable
data class Story(
  val component: Component,
  val name: String,
  val isCompose: Boolean,
  val isScreenshot: Boolean,
  val content: @Composable () -> Unit,
) {

  // used for code-generated extensions
  companion object
}
