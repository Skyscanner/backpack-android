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

package net.skyscanner.backpack.dialog

import android.view.View
import android.view.ViewGroup
import android.widget.ScrollView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.RootMatchers.isDialog
import androidx.test.espresso.matcher.ViewMatchers.withId
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.button.BpkButton
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkDialogTest : BpkSnapshotTest() {

    @Test
    fun default() {
        val dialog = BpkDialog(composeTestRule.activity, BpkDialog.Type.Success).apply {
            title = "You are going to Tokyo!"
            description = "Your flight is all booked. Why not check out some hotels now?"
            icon = BpkDialog.Icon(R.drawable.bpk_tick)

            addActionButton(
                BpkDialog.Button("Continue") { },
            )

            addActionButton(
                BpkDialog.Button("Skip") { },
            )
        }

        record(dialog)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun fullscreen() {
        val dialog = BpkDialog(composeTestRule.activity, BpkDialog.Type.Success).apply {
            title = "You are going to Tokyo!"
            description = Array(30) {
                "Your flight is all booked. Why not check out some hotels now?"
            }.joinToString(separator = "\n")

            icon = BpkDialog.Icon(R.drawable.bpk_tick)

            addActionButton(
                BpkDialog.Button("Continue") { },
            )

            addActionButton(
                BpkDialog.Button("Skip") { },
            )
        }

        record(dialog)
    }

    @Test
    fun destructive() {
        val dialog = BpkDialog(composeTestRule.activity, BpkDialog.Type.Destructive).apply {
            title = "Delete?"
            description = "Delete your profile?"
            icon = BpkDialog.Icon(R.drawable.bpk_trash)

            addActionButton(
                BpkDialog.Button("Delete") { },
            )

            addActionButton(
                BpkDialog.Button("Cancel") { },
            )
        }

        record(dialog)
    }

    @Suppress("DEPRECATION")
    @Test
    fun deprecated() {
        val dialog = BpkDialog(composeTestRule.activity, BpkDialog.Style.ALERT).apply {
            title = "Delete?"
            description = "Delete your profile?"
            icon = BpkDialog.Icon(R.drawable.bpk_trash, composeTestRule.activity.getColor(R.color.bpkTextSecondary))

            addActionButton(
                BpkButton(composeTestRule.activity).apply {
                    type = BpkButton.Type.Secondary
                    text = "Secondary"
                },
            )
            addActionButton(
                BpkButton(composeTestRule.activity).apply {
                    type = BpkButton.Type.Destructive
                    text = "Destructive"
                },
            )
            addActionButton(
                BpkButton(composeTestRule.activity).apply {
                    type = BpkButton.Type.Featured
                    text = "Featured"
                },
            )
        }

        record(dialog)
    }

    @Test
    fun warning() {
        val dialog = BpkDialog(composeTestRule.activity, BpkDialog.Type.Warning)
        dialog.apply {
            title = "Want to know when prices change?"
            description = "Create a price alert and we'll let you know changes for this route"
            icon = BpkDialog.Icon(R.drawable.bpk_trash)

            addActionButton(
                BpkDialog.Button("Create") { },
            )

            addActionButton(
                BpkDialog.Button("No, Thanks!") { },
            )
        }

        record(dialog)
    }

    @Test
    fun flare() {
        val dialog = BpkDialog(composeTestRule.activity, BpkDialog.Type.Flare).apply {
            title = "You are going to Tokyo!"
            description = "Your flight is all booked."
            icon = BpkDialog.Icon(R.drawable.bpk_tick)

            image!!.setImageResource(R.drawable.dialog_sample)

            addActionButton(
                BpkDialog.Button("Continue") { },
            )

            addActionButton(
                BpkDialog.Button("Skip") { },
            )
        }

        record(dialog)
    }

    private fun record(dialog: BpkDialog) {
        // not ideal, but the scrollbar disappears too early when running on CI causing test failures if visible
        dialog.window?.decorView?.findScrollView()?.scrollBarDefaultDelayBeforeFade = 5000

        dialog.show()

        var view: View? = null
        onView(withId(R.id.dialog_buttons_root))
            .inRoot(isDialog())
            .check { _, _ ->
                val rootView = dialog.window!!.decorView
                composeTestRule.activity.windowManager.removeView(rootView)

                view = rootView
            }
        snap(view!!, background = R.color.bpkTextSecondary, padding = 0, width = 420, height = 600)
    }

    private fun View.findScrollView(): ScrollView? {
        if (this !is ViewGroup) return null
        for (i in 0..childCount) {
            val child = getChildAt(i)
            if (child is ScrollView) {
                return child
            } else if (child is ViewGroup) {
                val view = child.findScrollView()
                if (view != null) {
                    return view
                }
            }
        }
        return null
    }
}
