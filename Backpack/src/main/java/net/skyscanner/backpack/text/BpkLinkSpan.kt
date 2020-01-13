package net.skyscanner.backpack.text

import android.content.Context
import android.text.TextPaint
import android.text.style.ClickableSpan
import android.view.View
import net.skyscanner.backpack.util.BpkTheme

class BpkLinkSpan<T>(
  context: Context,
  private val link: T,
  private val linkHandler: (T) -> Unit
) : ClickableSpan() {

  private val color = BpkTheme.getPrimaryColor(context)

  override fun updateDrawState(tp: TextPaint) {
    tp.color = color
    tp.isUnderlineText = true
  }

  override fun onClick(widget: View) {
    linkHandler(link)
  }
}
