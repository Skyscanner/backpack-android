package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import net.skyscanner.backpack.demo.ComponentDetailFragment
import net.skyscanner.backpack.demo.R

class PanelFragment : ComponentDetailFragment() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.merge(R.layout.fragment_panel)
  }

}
