package net.skyscanner.backpack.demo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.LayoutRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

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
    factory = { T::class.java.getConstructor(Context::class.java).newInstance(it) as T },
    update = { update(it) },
  )
}
