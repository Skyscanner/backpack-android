/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.demo.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView

@Composable
fun AndroidLayout(
  @LayoutRes layoutId: Int,
  modifier: Modifier = Modifier,
  update: View.() -> Unit = {},
  init: View.() -> Unit,
) {
  AndroidView(
    modifier = modifier,
    factory = { LayoutInflater.from(it).inflate(layoutId, null).also(init) },
    update = { update(it) },
  )
}

@Composable
fun <T : View> AndroidLayout(
  @LayoutRes layoutId: Int,
  @IdRes viewId: Int,
  modifier: Modifier = Modifier,
  update: T.() -> Unit = {},
  init: T.() -> Unit,
) {
  AndroidLayout(
    layoutId = layoutId,
    modifier = modifier,
    init = { findViewById<T>(viewId).also(init) },
    update = { findViewById<T>(viewId).also(update) },
  )
}

@Composable
fun AndroidLayout(
  @LayoutRes layoutId: Int,
  modifier: Modifier = Modifier,
) =
  AndroidLayout(layoutId, modifier, init = {})

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
