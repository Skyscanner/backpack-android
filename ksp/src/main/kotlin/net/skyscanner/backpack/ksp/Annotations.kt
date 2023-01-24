package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.symbol.KSType

private const val demoPkg = "net.skyscanner.backpack.demo"

interface AnnotationDefinition {
  val simpleName: String
  val qualifiedName: String
}

abstract class AnnotationParam<Type>(val name: String) {
  abstract fun parse(value: Any): Type
}

private fun stringParamOf(name: String): AnnotationParam<String> =
  object : AnnotationParam<String>(name) {
    override fun parse(value: Any): String =
      value as String
  }

private fun booleanParamOf(name: String): AnnotationParam<Boolean> =
  object : AnnotationParam<Boolean>(name) {
    override fun parse(value: Any): Boolean =
      value as Boolean
  }

private fun intParamOf(name: String): AnnotationParam<Int> =
  object : AnnotationParam<Int>(name) {
    override fun parse(value: Any): Int =
      value as Int
  }

private fun enumParamOf(name: String): AnnotationParam<EnumValue> =
  object : AnnotationParam<EnumValue>(name) {
    override fun parse(value: Any): EnumValue =
      EnumValue(
        value = (value as KSType).declaration.simpleName.getShortName(),
        type = value.declaration.qualifiedName!!.getQualifier(),
      )
  }

private fun typeParamOf(name: String): AnnotationParam<String> =
  object : AnnotationParam<String>(name) {
    override fun parse(value: Any): String =
      (value as KSType).declaration.qualifiedName!!.asString()
  }

private fun enumParamsOf(name: String): AnnotationParam<List<EnumValue>> =
  object : AnnotationParam<List<EnumValue>>(name) {
    override fun parse(value: Any): List<EnumValue> =
      (value as List<KSType>)
        .map {
          EnumValue(
            value = it.declaration.simpleName.getShortName(),
            type = it.declaration.qualifiedName!!.getQualifier(),
          )
        }
  }

object StoryAnnotation : AnnotationDefinition {
  override val simpleName = "Story"
  override val qualifiedName = "$demoPkg.meta.$simpleName"
  val paramName = stringParamOf("name")
  val paramScreenshot = booleanParamOf("screenshot")
}

object ComponentAnnotation : AnnotationDefinition {
  override val simpleName = "Component"
  override val qualifiedName = "$demoPkg.meta.$simpleName"
  val paramName = stringParamOf("name")
  val paramLink = stringParamOf("link")
  val paramKind = enumParamOf("kind")
}

object SampleAnnotation : AnnotationDefinition {
  override val simpleName = "Sample"
  override val qualifiedName = "$demoPkg.meta.$simpleName"
}

object SnapshotAnnotation : AnnotationDefinition {
  override val simpleName = "Snapshot"
  override val qualifiedName = "$demoPkg.meta.$simpleName"
  val paramVariants = enumParamsOf("variants")
}

object PreviewParameter : AnnotationDefinition {
  override val simpleName = "PreviewParameter"
  override val qualifiedName = "androidx.compose.ui.tooling.preview.$simpleName"
  val paramLimit = intParamOf("limit")
  val paramsProvider = typeParamOf("provider")
}
