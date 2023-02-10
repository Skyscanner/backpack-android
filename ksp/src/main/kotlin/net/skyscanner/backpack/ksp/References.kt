package net.skyscanner.backpack.ksp

private const val metaPkg = "net.skyscanner.backpack.demo.meta"

object ComposeStoryAnnotation : AnnotationDefinition {
  override val simpleName = "ComposeStory"
  override val pkg = metaPkg
  val paramName = stringParamOf("name")
  val paramScreenshot = booleanParamOf("screenshot")
}

object ViewStoryAnnotation : AnnotationDefinition {
  override val simpleName = "ViewStory"
  override val pkg = metaPkg
  val paramName = stringParamOf("name")
  val paramScreenshot = booleanParamOf("screenshot")
  val paramLayoutId = intParamOf("layoutId")
}

object ComponentAnnotation : AnnotationDefinition {
  override val simpleName = "Component"
  override val pkg = metaPkg
  val paramName = stringParamOf("name")
  val paramLink = stringParamOf("link")
}
