/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2026 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.videoplayer

import android.os.ParcelFileDescriptor.AutoCloseInputStream
import androidx.test.platform.app.InstrumentationRegistry
import org.junit.rules.ExternalResource

// BpkVideoPlayerController defaults to respectsReducedMotion = true and treats animator/transition
// scale 0 (a common test-emulator setting, used to speed up Espresso) as a reduced-motion signal,
// which suppresses autoplay and leaves the player stuck at ReadyToPlay.
// Call disableReducedMotionSignal() in tests that need real playback; the rule restores original
// values automatically after each test.
class VideoPlayerTestRule : ExternalResource() {

    private var originalAnimationScales: Pair<String?, String?>? = null

    fun disableReducedMotionSignal() {
        originalAnimationScales =
            getGlobalSetting(ANIMATOR_DURATION_SCALE_KEY) to getGlobalSetting(TRANSITION_ANIMATION_SCALE_KEY)
        putGlobalSetting(ANIMATOR_DURATION_SCALE_KEY, "1")
        putGlobalSetting(TRANSITION_ANIMATION_SCALE_KEY, "1")
    }

    override fun after() {
        originalAnimationScales?.let { (animator, transition) ->
            putGlobalSetting(ANIMATOR_DURATION_SCALE_KEY, animator ?: "1")
            putGlobalSetting(TRANSITION_ANIMATION_SCALE_KEY, transition ?: "1")
            originalAnimationScales = null
        }
    }

    fun bundledVideoUrl(): String {
        val targetPackage = InstrumentationRegistry.getInstrumentation().targetContext.packageName
        return "android.resource://$targetPackage/raw/bpk_video_player_test"
    }

    // android.provider.Settings.Global requires WRITE_SECURE_SETTINGS, which the app under test
    // does not hold; UiAutomation shell commands run with shell identity, which does.
    private fun getGlobalSetting(key: String): String? =
        runShellCommand("settings get global $key")
            .trim()
            .takeUnless { it == "null" || it.isEmpty() }

    private fun putGlobalSetting(key: String, value: String) {
        runShellCommand("settings put global $key $value")
    }

    private fun runShellCommand(command: String): String =
        AutoCloseInputStream(InstrumentationRegistry.getInstrumentation().uiAutomation.executeShellCommand(command))
            .use { it.readBytes().decodeToString() }

    companion object {
        private const val ANIMATOR_DURATION_SCALE_KEY = "animator_duration_scale"
        private const val TRANSITION_ANIMATION_SCALE_KEY = "transition_animation_scale"
        const val READY_STATE_TIMEOUT_MS = 7_000L
        const val PLAYING_STATE_TIMEOUT_MS = 5_000L
        const val ENDED_STATE_TIMEOUT_MS = 8_000L
    }
}
