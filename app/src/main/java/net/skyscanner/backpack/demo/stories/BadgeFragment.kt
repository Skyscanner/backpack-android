package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.View
import net.skyscanner.backpack.demo.ComponentDetailFragment
import net.skyscanner.backpack.demo.R

class BadgeFragment : ComponentDetailFragment() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.merge(R.layout.fragment_badge)
  }

}

