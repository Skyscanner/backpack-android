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
) {

  override fun equals(other: Any?): Boolean {
    if (this === other) return true
    if (javaClass != other?.javaClass) return false

    other as ComponentEntry

    if (name != other.name) return false

    return true
  }

  override fun hashCode(): Int {
    return name.hashCode()
  }
}

enum class Kind {
  View,
  Compose,
}
