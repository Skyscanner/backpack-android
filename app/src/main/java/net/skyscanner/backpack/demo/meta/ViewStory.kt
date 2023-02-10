package net.skyscanner.backpack.demo.meta

import android.content.res.Configuration
import android.graphics.Color
import androidx.annotation.LayoutRes
import androidx.compose.ui.tooling.preview.Preview
import javax.annotation.concurrent.Immutable

@Immutable
@Suppress("Detekt.PreviewNaming")
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.BINARY)

@Preview(name = "Default",
  uiMode = Configuration.UI_MODE_NIGHT_NO,
  locale = "en",
  showBackground = true,
  backgroundColor = Color.WHITE.toLong(),
)

@Preview(name = "Night",
  uiMode = Configuration.UI_MODE_NIGHT_YES,
  locale = "en",
  showBackground = true,
  backgroundColor = Color.BLACK.toLong(),
)

@Preview(name = "Rtl",
  uiMode = Configuration.UI_MODE_NIGHT_NO,
  locale = "ar",
  showBackground = true,
  backgroundColor = Color.BLACK.toLong(),
)

annotation class ViewStory(
  val name: String = "Default",
  val screenshot: Boolean = true,
  @LayoutRes val layoutId: Int = 0,
)
