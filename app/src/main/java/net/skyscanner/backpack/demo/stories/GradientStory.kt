package net.skyscanner.backpack.demo.stories

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.fragment_gradient.*
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.gradient.BpkGradients

class GradientStory : Story() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_gradient, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    gradient_primary.background = BpkGradients(context!!)
    gradient_direction.background = BpkGradients(context!!, GradientDrawable.Orientation.TR_BL)
  }
}
