/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
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

import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotification
import net.skyscanner.backpack.compose.floatingnotification.BpkFloatingNotificationState
import net.skyscanner.backpack.compose.floatingnotification.rememberBpkFloatingNotificationState
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.TextSpansComponent
import net.skyscanner.backpack.demo.meta.ViewStory
import net.skyscanner.backpack.demo.ui.AndroidLayout
import net.skyscanner.backpack.text.BpkFontSpan
import net.skyscanner.backpack.text.BpkLinkSpan
import net.skyscanner.backpack.text.BpkPrimaryColorSpan
import net.skyscanner.backpack.text.BpkText

@Composable
@TextSpansComponent
@ViewStory
fun TextSpansStory(modifier: Modifier = Modifier) {
    val notificationState = rememberBpkFloatingNotificationState()
    val scope = rememberCoroutineScope()

    Box(modifier) {
        AndroidLayout<TextView>(R.layout.fragment_text_spans, R.id.text) {
            setupTextSpans(this, notificationState, scope)
        }
        BpkFloatingNotification(notificationState)
    }
}

private fun setupTextSpans(textView: TextView, notificationState: BpkFloatingNotificationState, scope: CoroutineScope) {

    val linksHandler = { link: String ->
        scope.launch {
            notificationState.show(text = link)
        }
        Unit
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
