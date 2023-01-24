package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.symbol.FileLocation

data class ComponentDefinition(
  val name: String,
  val link: String,
  val kind: String,
  val location: FileLocation,
)

data class StoryDefinition(
  val name: String,
  val screenshot: Boolean,
  val component: ComponentDefinition,
  val location: FileLocation,
)

data class SampleDefinition(
  val kDocs: String?,
  val body: String,
  val component: ComponentDefinition,
  val location: FileLocation,
)
data class SnapshotDefinition(
  val variants: List<String>,
  val component: ComponentDefinition,
  val location: FileLocation,
)
