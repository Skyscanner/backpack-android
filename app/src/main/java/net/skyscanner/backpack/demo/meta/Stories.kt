package net.skyscanner.backpack.demo.meta

import android.content.res.Configuration
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Suppress("Detekt.PreviewNaming")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Default", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "en", showBackground = true, backgroundColor = 0xFF000000)
@Preview(name = "Rtl", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ar", showBackground = true, backgroundColor = 0xFFFFFFFF)
annotation class Story(
  val name: String,
  val screenshot: Boolean = true,
)

data class StoryEntry(
  val name: String,
  val component: ComponentEntry,
  val screenshot: Boolean = true,
  val previewParameter: PreviewParameterInfo?,
  val content: @Composable () -> Unit,
)
