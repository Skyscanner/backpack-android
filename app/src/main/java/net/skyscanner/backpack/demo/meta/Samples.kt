package net.skyscanner.backpack.demo.meta

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Sample

data class SampleEntry(
  val component: ComponentEntry,
  val kDocs: String,
  val body: String,
)
