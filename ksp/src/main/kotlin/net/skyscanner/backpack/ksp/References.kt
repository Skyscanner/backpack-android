package net.skyscanner.backpack.ksp

private const val demoMetaPkg = "net.skyscanner.backpack.demo.meta"

object StoryAnnotation : AnnotationDefinition {
  override val simpleName = "Story"
  override val pkg = demoMetaPkg
  val paramName = stringParamOf("name")
  val paramScreenshot = booleanParamOf("screenshot")
}

object ComponentAnnotation : AnnotationDefinition {
  override val simpleName = "Component"
  override val pkg = demoMetaPkg
  val paramName = stringParamOf("name")
  val paramLink = stringParamOf("link")
  val paramKind = enumParamOf("kind")
}

object SampleAnnotation : AnnotationDefinition {
  override val simpleName = "Sample"
  override val pkg = demoMetaPkg
}

object SnapshotAnnotation : AnnotationDefinition {
  override val simpleName = "Snapshot"
  override val pkg = demoMetaPkg
  val paramVariants = enumParamsOf("variants")
}

object PreviewParameter : AnnotationDefinition {
  override val simpleName = "PreviewParameter"
  override val pkg = "androidx.compose.ui.tooling.preview"
  val paramLimit = intParamOf("limit")
  val paramsProvider = typeParamOf("provider")
}
