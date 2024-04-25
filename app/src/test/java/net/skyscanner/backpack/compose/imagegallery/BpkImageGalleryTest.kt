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

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.ImageGalleryCarouselStory
import net.skyscanner.backpack.demo.compose.ImageGalleryChipGridStory
import net.skyscanner.backpack.demo.compose.ImageGalleryImageGridStory
import net.skyscanner.backpack.demo.compose.ImageGallerySlideshowStory
import org.junit.Test
import org.robolectric.RuntimeEnvironment

class BpkImageGalleryTest : BpkSnapshotTest() {

    @Test
    fun carousel() {
        snap { ImageGalleryCarouselStory() }
    }

    @Test
    fun slideshow() {
        snap(captureFullScreen = true) { ImageGallerySlideshowStory() }
    }

    @Test
    fun slideshow_landscape() {
        RuntimeEnvironment.setQualifiers("+land")
        snap(captureFullScreen = true) { ImageGallerySlideshowStory() }
    }

    @Test
    fun slideshow_short_text() {
        snap(captureFullScreen = true) { ImageGallerySlideshowStory(initialPage = 1) }
    }

    @Test
    fun image_grid() {
        snap(captureFullScreen = true) { ImageGalleryImageGridStory() }
    }

    @Test
    fun chip_grid() {
        snap(captureFullScreen = true) { ImageGalleryChipGridStory() }
    }
}
