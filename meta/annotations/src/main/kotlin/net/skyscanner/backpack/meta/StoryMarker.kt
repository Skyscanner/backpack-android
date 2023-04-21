package net.skyscanner.backpack.meta

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class StoryMarker(
    val isCompose: Boolean,
)
