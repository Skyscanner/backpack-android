package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.symbol.FileLocation
import com.google.devtools.ksp.symbol.KSName

data class ComponentDefinition(
  val id: KSName,
  val name: String,
  val link: String,
  val location: FileLocation,
)

data class StoryDefinition(
  val component: ComponentDefinition,
  val name: String,
  val isCompose: Boolean,
  val isScreenshot: Boolean,
  val layoutId: Int,
  val reference: String,
  val location: FileLocation,
)

data class EnumValue(
  val value: String,
  val type: String,
)
