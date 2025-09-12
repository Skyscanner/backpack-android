/**
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

package net.skyscanner.backpack.navbar

import android.view.View
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.matcher.ViewMatchers
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkNavBarTest : BpkSnapshotTest() {

    @Test
    fun default() {
        init()
        var view: View? = null
        onView(ViewMatchers.withId(R.id.appBar))
            .check { v, _ -> view = v }
        snap(view!!)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun collapsed() {
        init(expanded = false)
        var view: View? = null
        onView(ViewMatchers.withId(R.id.appBar))
            .check { v, _ -> view = v }
        snap(view!!)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun collapsed_icon() {
        init(icon = true, expanded = false)
        var view: View? = null
        onView(ViewMatchers.withId(R.id.appBar))
            .check { v, _ -> view = v }
        snap(view!!)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun expanded_icon() {
        init(icon = true)
        var view: View? = null
        onView(ViewMatchers.withId(R.id.appBar))
            .check { v, _ -> view = v }
        snap(view!!)
    }

    @Test
    fun collapsed_menu() {
        init(menu = true, expanded = false)
        var view: View? = null
        onView(ViewMatchers.withId(R.id.appBar))
            .check { v, _ -> view = v }
        snap(view!!)
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun expanded_menu() {
        init(menu = true)
        var view: View? = null
        onView(ViewMatchers.withId(R.id.appBar))
            .check { v, _ -> view = v }
        snap(view!!)
    }

    @Test
    fun collapsed_iconAndMenu() {
        init(icon = true, menu = true, expanded = false)
        var view: View? = null
        onView(ViewMatchers.withId(R.id.appBar))
            .check { v, _ -> view = v }
        snap(view!!)
    }

    @Test
    fun expanded_iconAndMenu() {
        init(icon = true, menu = true)
        var view: View? = null
        onView(ViewMatchers.withId(R.id.appBar))
            .check { v, _ -> view = v }
        snap(view!!)
    }

    private fun init(
        icon: Boolean = false,
        menu: Boolean = false,
        expanded: Boolean = true,
    ) {
        composeTestRule.activity.setContentView(R.layout.fragment_nav_bar)
        val navBar = composeTestRule.activity.findViewById<BpkNavBar>(R.id.appBar)
        navBar.title = "Nav Bar"

        if (icon) {
            navBar.icon = composeTestRule.activity.getDrawable(net.skyscanner.backpack.internal.icons.R.drawable.bpk_native_android__back)
        } else {
            navBar.icon = null
        }
        if (menu) {
            navBar.menu = R.menu.settings
        } else {
            navBar.menu = 0
        }
        navBar.setExpanded(expanded, false)
    }
}
