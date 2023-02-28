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

package net.skyscanner.backpack.demo

import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.scaleIn
import androidx.compose.animation.scaleOut
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import net.skyscanner.backpack.demo.ui.DemoScaffold
import net.skyscanner.backpack.demo.ui.NavGraphs

/**
 * An activity representing a list of Components. This activity
 * has different presentations for handset and tablet-size devices. On
 * handsets, the activity presents a list of items, which when touched,
 * lead to a [ComponentDetailActivity] representing
 * item details. On tablets, the activity presents the list of items and
 * item details side-by-side using two vertical panes.
 */
class MainActivity : BpkBaseActivity() {

  @OptIn(ExperimentalAnimationApi::class, ExperimentalMaterialNavigationApi::class)
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContent {
      DemoScaffold {
        DestinationsNavHost(
          navGraph = NavGraphs.root,
          engine = rememberAnimatedNavHostEngine(
            rootDefaultAnimations = RootNavGraphDefaultAnimations(
              enterTransition = { slideIntoContainer(AnimatedContentScope.SlideDirection.Left) },
              exitTransition = { scaleOut(targetScale = 0.95f) },
              popEnterTransition = { scaleIn(initialScale = 0.95f) },
              popExitTransition = { slideOutOfContainer(AnimatedContentScope.SlideDirection.Right) },
            ),
          ),
        )
      }
    }
  }
}
