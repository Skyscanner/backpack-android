package net.skyscanner.backpack.demo.meta

import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView

@Suppress("Detekt.PreviewNaming")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)
@Preview(name = "Default", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "en", showBackground = true, backgroundColor = 0xFFFFFFFF)
@Preview(name = "Night", uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "en", showBackground = true, backgroundColor = 0xFF000000)
@Preview(name = "Rtl", uiMode = Configuration.UI_MODE_NIGHT_NO, locale = "ar", showBackground = true, backgroundColor = 0xFFFFFFFF)
annotation class Story(
  val name: String = "Default",
  val screenshot: Boolean = true,
)

object Stories

data class StoryEntry(
  val name: String,
  val component: ComponentEntry,
  val screenshot: Boolean = true,
  val content: @Composable () -> Unit,
)

@Composable
fun <T : View> AndroidLayout(
  @LayoutRes id: Int,
  modifier: Modifier = Modifier,
  update: T.() -> Unit,
) {
  AndroidView(
    modifier = modifier,
    factory = { LayoutInflater.from(it).inflate(id, null) as T },
    update = { update(it) },
  )
}

@Composable
fun AndroidLayout(
  @LayoutRes id: Int,
  modifier: Modifier = Modifier,
) =
  AndroidLayout<View>(id, modifier) {}

@Composable
inline fun <reified T : View> AndroidView(
  modifier: Modifier = Modifier,
  crossinline update: T.() -> Unit,
) {
  AndroidView(
    modifier = modifier,
    factory = { T::class.java.constructors.first { it.parameters.size == 1 }.newInstance(it) as T },
    update = { update(it) },
  )
}
