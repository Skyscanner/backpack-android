package net.skyscanner.backpack.compose.imagegallery

import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.compose.ImageGalleryPreviewDefaultStory
import net.skyscanner.backpack.demo.compose.ImageGalleryPreviewHeroStory
import org.junit.Test

class BpkImageGalleryPreviewTest : BpkSnapshotTest() {

    @Test
    fun previewDefault() {
        snap { ImageGalleryPreviewDefaultStory() }
    }

    @Test
    fun previewHero() {
        snap { ImageGalleryPreviewHeroStory() }
    }
}
