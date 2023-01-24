package net.skyscanner.backpack.ksp

private const val demoPkg = "net.skyscanner.backpack.demo"

interface AnnotationDefinition {
  val simpleName: String
  val qualifiedName: String
}

abstract class AnnotationParam<Type>(val name: String) {
  abstract fun parse(value: String): Type
}

private fun stringParamOf(name: String): AnnotationParam<String> =
  object : AnnotationParam<String>(name) {
    override fun parse(value: String): String =
      value
  }

private fun booleanParamOf(name: String): AnnotationParam<Boolean> =
  object : AnnotationParam<Boolean>(name) {
    override fun parse(value: String): Boolean =
      java.lang.Boolean.parseBoolean(value)
  }

private fun intParamOf(name: String): AnnotationParam<Int> =
  object : AnnotationParam<Int>(name) {
    override fun parse(value: String): Int =
      Integer.parseInt(value)
  }

private fun stringArrayParamOf(name: String): AnnotationParam<List<String>> =
  object : AnnotationParam<List<String>>(name) {
    override fun parse(value: String): List<String> =
      value.removePrefix("[")
        .removeSuffix("]")
        .split(",")
        .map { it.trim() }
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
  val paramKind = stringParamOf("kind")
}

object SampleAnnotation : AnnotationDefinition {
  override val simpleName = "Sample"
  override val qualifiedName = "$demoPkg.meta.$simpleName"
}

object SnapshotAnnotation : AnnotationDefinition {
  override val simpleName = "Snapshot"
  override val qualifiedName = "$demoPkg.meta.$simpleName"
  val paramVariants = stringArrayParamOf("variants")
}
