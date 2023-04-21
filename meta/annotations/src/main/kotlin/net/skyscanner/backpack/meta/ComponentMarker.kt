package net.skyscanner.backpack.meta

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class ComponentMarker(
    val name: String,
    val isToken: Boolean = false,
)
