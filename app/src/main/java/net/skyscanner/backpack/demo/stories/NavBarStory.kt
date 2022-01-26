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

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.navbar.BpkNavBar
import net.skyscanner.backpack.toast.BpkToast

class NavBarStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    val navBar = view.findViewById<BpkNavBar>(R.id.appBar)
    navBar.title = if (view.layoutDirection == View.LAYOUT_DIRECTION_RTL) {
      "عنوان الصفحة"
    } else {
      "Nav Bar"
    }
    navBar.navAction = {
      BpkToast.makeText(requireContext(), "Nav is clicked!", BpkToast.LENGTH_SHORT).show()
    }
    navBar.menuAction = {
      BpkToast.makeText(
        requireContext(),
        "${it.itemId.let(resources::getResourceEntryName)} is clicked!",
        BpkToast.LENGTH_SHORT
      ).show()
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = NavBarStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
      arguments?.putBoolean(SCROLLABLE, false)
    }
  }
}
