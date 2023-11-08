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

package net.skyscanner.backpack.compose.annotation

import android.content.res.Configuration
import androidx.compose.ui.tooling.preview.Preview

@Target(
    AnnotationTarget.ANNOTATION_CLASS,
    AnnotationTarget.FUNCTION,
)

@Retention(AnnotationRetention.BINARY)

@Preview(name = "Default",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "en",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF, // BpkTheme.colors.canvas, LM
)

@Preview(name = "DM",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    locale = "en",
    showBackground = true,
    backgroundColor = 0xFF010913, // BpkTheme.colors.canvas, DM
)

@Preview(name = "RTL",
    uiMode = Configuration.UI_MODE_NIGHT_NO,
    locale = "ar",
    showBackground = true,
    backgroundColor = 0xFFFFFFFF, // BpkTheme.colors.canvas, LM
)
annotation class BpkPreviews
