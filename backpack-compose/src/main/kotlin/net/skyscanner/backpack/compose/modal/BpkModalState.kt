package net.skyscanner.backpack.compose.modal

import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember

@Composable
fun rememberBpkModalState() = remember {
    BpkModalState()
}

class BpkModalState(
    val isVisible: MutableTransitionState<Boolean> =
        MutableTransitionState(false).apply { targetState = true },
) {
    suspend fun hide() {
        isVisible.targetState = false
    }
}
