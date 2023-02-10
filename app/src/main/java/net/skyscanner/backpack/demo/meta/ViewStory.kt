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
