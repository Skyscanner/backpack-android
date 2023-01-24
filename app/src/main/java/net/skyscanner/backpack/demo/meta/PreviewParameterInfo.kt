package net.skyscanner.backpack.demo.meta

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

data class PreviewParameterInfo(
  val name: String,
  val provider: PreviewParameterProvider<*>,
)
