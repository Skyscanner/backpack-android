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
import androidx.annotation.DrawableRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.HorizontalNavComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.horisontalnav.BpkHorizontalNav

@Composable
@HorizontalNavComponent
@ViewStory
fun HorizontalNavStory(modifier: Modifier = Modifier) =
  AndroidLayout(R.layout.fragment_horizontal_nav_default, modifier) {
    init(findViewById(R.id.horizontal_nav))
    init(findViewById(R.id.horizontal_nav_scrollable))
    init(findViewById(R.id.horizontal_nav_small))
    init(findViewById(R.id.horizontal_nav_alternate))
    init(findViewById(R.id.horizontal_nav_rtl))
    init(findViewById(R.id.horizontal_nav_icons))
  }

class HorizontalNavFragment : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    init(view.findViewById(R.id.horizontal_nav))
    init(view.findViewById(R.id.horizontal_nav_scrollable))
    init(view.findViewById(R.id.horizontal_nav_small))
    init(view.findViewById(R.id.horizontal_nav_alternate))
    init(view.findViewById(R.id.horizontal_nav_rtl))
    init(view.findViewById(R.id.horizontal_nav_icons))
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = HorizontalNavFragment().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}

private fun init(horizontalNav: BpkHorizontalNav) {
  horizontalNav.addTab("Flights", R.drawable.bpk_flight_sm)
  horizontalNav.addTab("Hotels", R.drawable.bpk_hotels_sm)
  horizontalNav.addTab("Car Hire", R.drawable.bpk_cars_sm)
}

private fun BpkHorizontalNav.addTab(tabText: String, @DrawableRes icon: Int) {
  val tab = newTab().apply {
    text = tabText
  }
  addTab(tab)
  if (id == R.id.horizontal_nav_icons) {
    tab.setIcon(icon)
  }
}
