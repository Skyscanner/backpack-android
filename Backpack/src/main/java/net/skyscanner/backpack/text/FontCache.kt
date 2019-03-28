package net.skyscanner.backpack.text

import android.content.Context
import android.graphics.Typeface
import java.util.Hashtable

object FontCache {

  private val fontCache = Hashtable<String, Typeface>()

  operator fun get(res: String?, context: Context): Typeface? {
    var tf = fontCache[res]
    if (tf == null) {
      try {
        tf = Typeface.createFromAsset(context.assets, "$res.ttf")
      } catch (e: Exception) {
        return null
      }
      fontCache[res] = tf
    }
    return tf
  }
}
