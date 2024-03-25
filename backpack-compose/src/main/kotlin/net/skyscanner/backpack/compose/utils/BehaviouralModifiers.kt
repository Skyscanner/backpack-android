package net.skyscanner.backpack.compose.utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.composed
import androidx.compose.ui.draw.drawBehind

fun Modifier.registerForBehaviouralEvents(
    item: Any,
    behaviouralCallback: BehaviouralCallback?,
    onClick: () -> Unit,
): Modifier = composed {

    if (behaviouralCallback == null) {
        this
    } else {
        var wasDrawn by rememberSaveable { mutableStateOf(false) }
        drawBehind {
            if (!wasDrawn) {
                wasDrawn = true
                behaviouralCallback.onDrawn(item)
            }
        }
    }
}.clickable {
    behaviouralCallback?.onClick(item)
    onClick.invoke()
}
