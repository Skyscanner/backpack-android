package net.skyscanner.backpack.demo.ui
/*
* Backpack for Android - Skyscanner's Design System
*
* Copyright 2018 - 2025 Skyscanner Ltd
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

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.RadioButton
import androidx.constraintlayout.widget.ConstraintLayout
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.text.BpkText
import androidx.core.content.withStyledAttributes

class SettingsTypographyOption @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0,
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val titleView: BpkText
    private val radioButton: RadioButton

    var text: CharSequence?
        get() = titleView.text
        set(value) {
            titleView.text = value
        }

    var isCurrent: Boolean
        get() = radioButton.isChecked
        set(value) {
            radioButton.isChecked = value
        }

    init {
        LayoutInflater.from(context).inflate(R.layout.settings_typography_option, this, true)

        titleView = findViewById(R.id.typography_title)
        radioButton = findViewById(R.id.typography_radio)

        context.withStyledAttributes(attrs, R.styleable.SettingsTypographyOption) {
            text = getString(R.styleable.SettingsTypographyOption_typographyName)
        }
    }
}
