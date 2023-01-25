package net.skyscanner.backpack.demo.meta

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Component(
  val name: String,
  val link: String,
  val kind: Kind = Kind.Compose,
)

object Components

data class ComponentEntry(
  val name: String,
  val link: String,
  val kind: Kind,
)

enum class Kind {
  View,
  Compose,
}
