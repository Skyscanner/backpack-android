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

package net.skyscanner.backpack.demo.stories

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ButtonComponent
import net.skyscanner.backpack.meta.StoryKind
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import kotlin.math.max
import kotlin.time.Duration.Companion.seconds

@Composable
@ButtonComponent
@ViewStory("Standard")
fun LoadingButtonStoryStandard(modifier: Modifier = Modifier) =
    LoadingButtonDemo(R.layout.fragment_button_standard, modifier)

@Composable
@ButtonComponent
@ViewStory("Large")
fun LoadingButtonStoryLarge(modifier: Modifier = Modifier) =
    LoadingButtonDemo(R.layout.fragment_button_large, modifier)

@Composable
@ButtonComponent
@ViewStory("Link")
fun LoadingButtonStoryLink(modifier: Modifier = Modifier) =
    LoadingButtonDemo(R.layout.fragment_button_link, modifier)

@Composable
@ButtonComponent
@ViewStory("Changeable", StoryKind.DemoOnly)
fun ChangeableButtonsStory(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_buttons_changeable, modifier.fillMaxSize()) {

        findViewById<TextView>(R.id.button_increase).setOnClickListener {
            it as TextView
            it.text = context.getString(R.string.button_increased, it.text.toString())
        }

        findViewById<TextView>(R.id.button_decrease).setOnClickListener {
            it as TextView
            it.text = it.text.substring(0, max(0, it.length() - 1))
        }
    }

@Composable
@ButtonComponent
@ViewStory("Styleable", StoryKind.DemoOnly)
fun StyleableButtonStory(modifier: Modifier = Modifier) =
    AndroidLayout(R.layout.fragment_buttons_styleable, modifier.fillMaxSize()) {
        findViewById<View>(R.id.primary).setOnClickListener { setButtonType(this, BpkButton.Type.Primary) }
        findViewById<View>(R.id.secondary).setOnClickListener { setButtonType(this, BpkButton.Type.Secondary) }
        findViewById<View>(R.id.destructive).setOnClickListener { setButtonType(this, BpkButton.Type.Destructive) }
        findViewById<View>(R.id.featured).setOnClickListener { setButtonType(this, BpkButton.Type.Featured) }
        findViewById<View>(R.id.primaryOnDark).setOnClickListener { setButtonType(this, BpkButton.Type.PrimaryOnDark) }
        findViewById<View>(R.id.primaryOnLight).setOnClickListener { setButtonType(this, BpkButton.Type.PrimaryOnLight) }
    }

@Composable
private fun LoadingButtonDemo(
    @LayoutRes layoutId: Int,
    modifier: Modifier = Modifier,
) {
    val scope = rememberCoroutineScope()
    AndroidLayout(layoutId, modifier.fillMaxSize()) {
        makeButtonsLoadeable(this as ViewGroup, scope)
    }
}

private fun makeButtonsLoadeable(parent: ViewGroup, scope: CoroutineScope) {
    for (i in 0..<parent.childCount) {
        val child = parent.getChildAt(i)
        when (child) {
            is ViewGroup -> makeButtonsLoadeable(child, scope)
            is BpkButton -> child.setOnClickListener {
                scope.launch {
                    child.loading = true
                    delay(2.5.seconds)
                    child.loading = false
                }
            }
        }
    }
}

private fun setButtonType(view: View, type: BpkButton.Type) {
    view.findViewById<ViewGroup>(R.id.buttonsContainer).run {
        for (i in 0..<childCount) {
            (getChildAt(i) as? BpkButton?)?.type = type
        }
    }
}
