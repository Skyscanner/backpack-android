package net.skyscanner.backpack.demo.stories

import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.gradient.BpkGradients

class GradientStoryCustom : Story() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_gradient, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<View>(R.id.gradient).background = BpkGradients(
      requireContext(),
      GradientDrawable.Orientation.TL_BR,
      intArrayOf(
        ContextCompat.getColor(requireContext(), R.color.bpkMonteverde),
        ContextCompat.getColor(requireContext(), R.color.bpkSagano)))
  }
}

class GradientStoryPrimary : Story() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_gradient, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<View>(R.id.gradient).background = BpkGradients.getPrimary(requireContext())
  }
}

class GradientStoryWithDirection : Story() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_gradient, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    view.findViewById<View>(R.id.gradient).background = BpkGradients.getPrimary(requireContext(), GradientDrawable.Orientation.TR_BL)
  }
}
