package net.skyscanner.backpack.util

/**
 * The API marked by this annotation should never be used
 * outside of Backpack implementation, because it may be changed
 * at any time in the future without considering this as a
 * breaking change, providing any warnings or migration aids.
 */
@Retention(value = AnnotationRetention.BINARY)
@Target(
  AnnotationTarget.CLASS,
  AnnotationTarget.FUNCTION,
  AnnotationTarget.TYPEALIAS,
  AnnotationTarget.PROPERTY
)
@RequiresOptIn(
  level = RequiresOptIn.Level.ERROR,
  message = "" +
    "The API marked by this annotation should never be used " +
    "outside of Backpack implementation, because it may be changed " +
    "at any time in the future without considering this as a " +
    "breaking change, providing any warnings or migration aids."
)
annotation class InternalBackpackApi
