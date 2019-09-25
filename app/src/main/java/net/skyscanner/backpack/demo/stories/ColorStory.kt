package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.R
import java.lang.reflect.Field

class ColorStory : Story() {
  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    return inflater.inflate(R.layout.fragment_all_colors, container, false)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    val colorGridView: RecyclerView = view.findViewById(R.id.lst_colors)

    val colorResources = ArrayList<Field>()

    for (field in R.color::class.java.fields) {
      if (field.name.startsWith("bpk") && !field.name.contains("_")) {
        colorResources.add(field)
      }
    }
    colorGridView.layoutManager = GridLayoutManager(context, 5)
    colorGridView.adapter = ColorAdapter(colorResources)
  }
}
