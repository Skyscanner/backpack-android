/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.stories

import android.view.ViewGroup
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.StarRatingInteractiveComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.starrating.BpkInteractiveStarRating

@Composable
@StarRatingInteractiveComponent
@ViewStory
fun InteractiveStarRatingStory(modifier: Modifier = Modifier) {
    val notificationState = rememberBpkFloatingNotificationState()
    val scope = rememberCoroutineScope()

    Box(modifier) {
        AndroidLayout(R.layout.fragment_star_rating_interactive) {
            this as ViewGroup
            (0..<childCount).forEach { idx ->
                val child = getChildAt(idx)
                if (child is BpkInteractiveStarRating) {
                    child.onRatingChangedListener = { current, max ->
                        scope.launch {
                            notificationState.show(text = "$current/$max")
                        }
                    }
                }
            }
        }
        BpkFloatingNotification(notificationState)
    }
}
