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
import net.skyscanner.backpack.bottomnav.BpkBottomNav
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.toast.BpkToast

class BottomNavStory : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    view.findViewById<BpkBottomNav>(R.id.bottom_nav).apply {
      addItem(1, R.string.bottom_nav_home, R.drawable.bpk_hotels)
      addItem(2, R.string.bottom_nav_explore, R.drawable.bpk_navigation)
      addItem(3, R.string.bottom_nav_trips, R.drawable.bpk_trips)
      addItem(4, R.string.bottom_nav_profile, R.drawable.bpk_account_circle)
      addOnNavigationItemReselectedListener { item, index ->
        BpkToast.makeText(requireContext(), "${item.title} #$index is reselected!", BpkToast.LENGTH_SHORT).show()
      }
      addOnNavigationItemSelectedListener { item, index ->
        BpkToast.makeText(requireContext(), "${item.title} #$index is selected!", BpkToast.LENGTH_SHORT).show()
      }
    }
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = BottomNavStory().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}
