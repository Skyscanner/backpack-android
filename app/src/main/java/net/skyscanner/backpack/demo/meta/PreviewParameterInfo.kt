package net.skyscanner.backpack.demo.meta

import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.tooling.preview.datasource.CollectionPreviewParameterProvider
import kotlin.reflect.KClass

data class PreviewParameterInfo(
  val name: String,
  val provider: PreviewParameterProvider<*>,
)

abstract class EnumProvider<E : Enum<E>>(clazz: KClass<E>) : CollectionPreviewParameterProvider<E>(clazz.java.enumConstants.toList())
