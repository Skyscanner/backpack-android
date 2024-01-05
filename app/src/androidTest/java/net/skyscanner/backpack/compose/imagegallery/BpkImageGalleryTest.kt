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

package net.skyscanner.backpack.compose.imagegallery

import androidx.compose.runtime.Composable
import androidx.compose.ui.test.isDialog
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.uiautomator.UiDevice
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.ImageGalleryCarouselStory
import net.skyscanner.backpack.demo.compose.ImageGallerySlideshowStory
import org.junit.Test

class BpkImageGalleryTest : BpkSnapshotTest() {

    @Test
    fun carousel() {
        snap { ImageGalleryCarouselStory() }
    }

    @Test
    fun slideshow() {
        recordModal { ImageGallerySlideshowStory() }
    }

    @Test
    fun slideshow_landscape() {
        val device = UiDevice.getInstance(InstrumentationRegistry.getInstrumentation())
        device.setOrientationLeft()
        recordModal { ImageGallerySlideshowStory() }
        device.setOrientationNatural()
    }

    @Test
    fun slideshow_second_page() {
        recordModal { ImageGallerySlideshowStory(initialPage = 2) }
    }

    private fun recordModal(content: @Composable () -> Unit) {
        snap(comparison = { name ->
            compareScreenshot(onNode(isDialog()), name)
        }) {
            content()
        }
    }
}
