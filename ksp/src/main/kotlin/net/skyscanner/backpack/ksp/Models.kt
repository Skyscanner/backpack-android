package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.symbol.FileLocation

data class ComponentDefinition(
  val name: String,
  val link: String,
  val kind: EnumValue,
  val location: FileLocation,
)

data class StoryDefinition(
  val name: String,
  val screenshot: Boolean,
  val component: ComponentDefinition,
  val previewProvider: PreviewProviderDefinition?,
  val location: FileLocation,
  val reference: String,
)

data class SampleDefinition(
  val kDocs: String?,
  val sourceCode: String,
  val component: ComponentDefinition,
  val location: FileLocation,
)

data class SnapshotDefinition(
  val variants: List<EnumValue>,
  val component: ComponentDefinition,
  val previewProvider: PreviewProviderDefinition?,
  val location: FileLocation,
  val reference: String,
)

data class PreviewProviderDefinition(
  val name: String,
  val limit: Int,
  val type: String,
)

data class EnumValue(
  val value: String,
  val type: String,
)
