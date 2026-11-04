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

package net.skyscanner.backpack.util

import android.view.View
import android.widget.FrameLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

/**
 * Hosts View tests with content clear of the system bars.
 */
class TestActivity : AppCompatActivity() {
    override fun setContentView(view: View) {
        val content = FrameLayout(this)
        ViewCompat.setOnApplyWindowInsetsListener(content) { container, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            container.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        content.addView(view)
        super.setContentView(content)
        ViewCompat.requestApplyInsets(content)
    }
}
