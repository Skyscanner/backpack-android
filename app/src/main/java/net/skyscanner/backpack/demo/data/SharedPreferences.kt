package net.skyscanner.backpack.demo.data

import android.annotation.SuppressLint
import android.content.Context
import net.skyscanner.backpack.demo.R

class SharedPreferences {

  companion object {
    val SHOULD_HIGHLIGHT = "highlight"


    fun shouldHighlight(context: Context): Boolean {
      return context
        .getSharedPreferences(context.getString(R.string.preference_file_key),
          Context.MODE_PRIVATE)
        .getBoolean(SHOULD_HIGHLIGHT, false)
    }

    @SuppressLint("ApplySharedPref")
    fun saveHighlightState(context: Context, state: Boolean) {
      val sharedPref = context
        .getSharedPreferences(context.getString(R.string.preference_file_key),
          Context.MODE_PRIVATE)
      with(sharedPref.edit()) {
        putBoolean(SHOULD_HIGHLIGHT, state)
        commit()
      }
    }
  }
}
