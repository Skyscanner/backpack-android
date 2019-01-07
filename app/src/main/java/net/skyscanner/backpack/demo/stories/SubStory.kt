package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.StoriesRecyclerViewAdapter

open class SubStory : Story() {

  override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
    val stories = arguments?.getStringArray(STORIES) ?: savedInstanceState?.getStringArray(STORIES)
    if (stories != null) {
      val view = inflater.inflate(R.layout.component_list, container, false)
      val componentsList = view.findViewById<View>(R.id.componentsList) as RecyclerView
      val allItems = mutableListOf<StoriesRecyclerViewAdapter.ListItem>()
      allItems.addAll(stories.map { StoriesRecyclerViewAdapter.StoryItem(it) })

      componentsList.adapter = StoriesRecyclerViewAdapter(allItems)
      return view
    } else {
      throw IllegalStateException("Story has not been property initialized")
    }
  }

  companion object {
    const val STORIES = "stories"

    infix fun of(stories: Array<String>) = SubStory().apply {
      arguments = Bundle()
      arguments?.putStringArray(STORIES, stories)
    }
  }
}
