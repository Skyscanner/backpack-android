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

package net.skyscanner.backpack.dialog.internal

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Build
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewGroup.LayoutParams
import android.view.Window
import android.view.WindowManager
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.LayoutRes
import net.skyscanner.backpack.R
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.dialog.BpkDialog
import kotlin.math.min

class BpkDialogImpl(
    @LayoutRes layout: Int,
    dialog: Dialog,
    private val type: BpkDialog.Type?,
) {
    private val root: View = LayoutInflater.from(dialog.context).inflate(layout, null)
    private val titleView = root.findViewById<TextView?>(R.id.dialog_title)
    private val descriptionView = root.findViewById<TextView?>(R.id.dialog_description)
    private val iconView = root.findViewById<BpkDialogIcon?>(R.id.dialog_icon)
    private val buttonsRoot = root.findViewById<ViewGroup?>(R.id.dialog_buttons_root)
    val image: ImageView? = root.findViewById(R.id.dialog_image)

    init {
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(root)
        iconView?.type = type

        dialog.window?.let {
            val maxWidth = dialog.context.resources.getDimensionPixelSize(R.dimen.bpk_dialog_max_width)
            val displayWidth = getScreenWidth(dialog)
            it.setLayout(min(displayWidth, maxWidth), LayoutParams.WRAP_CONTENT)

            val background = ColorDrawable(Color.TRANSPARENT)
            val margin = dialog.context.resources.getDimensionPixelSize(R.dimen.bpkSpacingLg)
            it.setBackgroundDrawable(InsetDrawable(background, margin))
        }
    }

    var title: String = ""
        set(value) {
            field = value
            titleView?.text = value
        }

    var description: String = ""
        set(value) {
            field = value
            descriptionView?.text = value
        }

    var icon: BpkDialog.Icon? = null
        set(value) {
            field = value
            iconView?.icon = icon
        }

    var isCanceledOnTouchOutside: Boolean = true

    fun addActionButton(view: View) {
        buttonsRoot?.addView(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
    }

    fun addActionButton(button: BpkDialog.Button) {
        val buttonsRoot = buttonsRoot
        if (buttonsRoot != null) {
            val dangerButton = type == BpkDialog.Type.Destructive
            when (buttonsRoot.childCount) {
                0 -> addActionButton(
                    createButton(if (dangerButton) BpkButton.Type.Destructive else BpkButton.Type.Featured, button),
                )
                1 -> addActionButton(
                    createButton(if (dangerButton) BpkButton.Type.Link else BpkButton.Type.Secondary, button),
                )
                else -> addActionButton(createButton(BpkButton.Type.Link, button))
            }
        }
    }

    private fun createButton(type: BpkButton.Type, button: BpkDialog.Button): View =
        BpkButton(root.context).apply {
            this.type = type
            this.text = button.text
            this.size = BpkButton.Size.Large
            this.setOnClickListener {
                button.onClick()
            }
        }

    private fun getScreenWidth(dialog: Dialog): Int {
        val windowManager = dialog.context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            val metrics = windowManager.currentWindowMetrics
            metrics.bounds.width()
        } else {
            val metrics = DisplayMetrics()
            @Suppress("DEPRECATION")
            windowManager.defaultDisplay.getMetrics(metrics)
            metrics.widthPixels
        }
    }
}
