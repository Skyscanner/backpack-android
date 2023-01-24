package net.skyscanner.backpack.demo.meta

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.ui.tooling.preview.Preview

@Suppress("Detekt.PreviewNaming")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Default", uiMode = UI_MODE_NIGHT_NO, locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(name = "Night", uiMode = UI_MODE_NIGHT_YES, locale = "en", showBackground = true, backgroundColor = 0xFF000000)
@Preview(name = "Rtl", uiMode = UI_MODE_NIGHT_NO, locale = "ar", showBackground = true, backgroundColor = 0xFFFFFFFF)
annotation class Story(
  val name: String,
  val screenshot: Boolean = true,
)

@Target(AnnotationTarget.ANNOTATION_CLASS)
@Retention(AnnotationRetention.BINARY)
annotation class Component(
  val name: String,
  val link: String,
  val kind: Kind = Kind.ViewAndCompose,
)

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Sample

@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
annotation class Snapshot(
  vararg val variants: Variants = [Variants.Default, Variants.DarkMode, Variants.Rtl, Variants.Themed],
)
