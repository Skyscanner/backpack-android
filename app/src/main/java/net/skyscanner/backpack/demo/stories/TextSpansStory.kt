/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.demo.stories

import android.os.Bundle
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.view.View
import android.widget.TextView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.TextSpansComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkLinkSpan
import net.skyscanner.backpack.text.BpkPrimaryColorSpan
import net.skyscanner.backpack.text.BpkText
import net.skyscanner.backpack.toast.BpkToast

@Composable
@TextSpansComponent
@ViewStory
fun TextSpansStory(modifier: Modifier = Modifier) =
  AndroidLayout<TextView>(R.layout.fragment_text_spans, R.id.text, modifier) {
    setupTextSpans(this)
  }

class TextSpansFragment : Story() {

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)
    setupTextSpans(view.findViewById(R.id.text))
  }

  companion object {
    private const val LAYOUT_ID = "fragment_id"

    infix fun of(fragmentLayout: Int) = TextSpansFragment().apply {
      arguments = Bundle()
      arguments?.putInt(LAYOUT_ID, fragmentLayout)
    }
  }
}

private fun setupTextSpans(textView: TextView) {

  val linksHandler = { link: String ->
    BpkToast.makeText(textView.context, link, BpkToast.LENGTH_SHORT).show()
  }

  textView.movementMethod = LinkMovementMethod.getInstance()
  textView.text = SpannableStringBuilder().apply {
    append("This is an example of \n")
    append("primary color span \n", BpkPrimaryColorSpan(textView.context), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

    append("This is an example of \n")
    append(
      "Backpack font span \n",
      BpkFontSpan(textView.context, BpkText.TextStyle.Heading4),
      Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
    )

    append("This is an example of \n")
    append(
      "Backpack link span \n",
      BpkLinkSpan(textView.context, "Link clicked!", linksHandler),
      Spannable.SPAN_EXCLUSIVE_EXCLUSIVE,
    )
  }
}
