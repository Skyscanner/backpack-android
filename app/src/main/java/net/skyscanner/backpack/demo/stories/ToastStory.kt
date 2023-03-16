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

import android.widget.TextView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.ToastComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.demo.ui.LocalAutomationMode
import net.skyscanner.backpack.toast.BpkToast

@Composable
@ToastComponent
@ViewStory
fun ToastStory(modifier: Modifier = Modifier) {
    val automationMode = LocalAutomationMode.current

    AndroidLayout(R.layout.fragment_toasts, modifier.fillMaxSize()) {
        findViewById<TextView>(R.id.toast_short).setOnClickListener {
            it as TextView
            BpkToast.makeText(context, it.text, BpkToast.LENGTH_SHORT).show()
        }

        findViewById<TextView>(R.id.toast_long).setOnClickListener {
            it as TextView
            BpkToast.makeText(context, it.text, BpkToast.LENGTH_LONG).show()
        }

        if (automationMode) {
            findViewById<TextView>(R.id.toast_short).performClick()
        }
    }
}
