package net.skyscanner.backpack.text

import android.content.Context
import android.graphics.Typeface
import androidx.core.content.res.ResourcesCompat
import java.util.Hashtable

object FontCache {

  private val fontCache = Hashtable<Int, Typeface>()

  operator fun get(res: Int, context: Context): Typeface? {
    var tf = fontCache[res]
    if (tf == null) {
      tf = ResourcesCompat.getFont(context, res)
      fontCache[res] = tf
    }
    return tf
  }
}
