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
  val screenshot: String,
  val component: ComponentDefinition,
  val location: FileLocation,
)
