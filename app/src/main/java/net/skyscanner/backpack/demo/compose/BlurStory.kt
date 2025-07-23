/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.demo.compose

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.compose.blur.bpkProgressiveBlur
import net.skyscanner.backpack.compose.blur.bpkUniformBlur
import net.skyscanner.backpack.compose.card.BpkCard
import net.skyscanner.backpack.compose.card.BpkCardPadding
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.BlurComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/**
 * Enum to define the type of blur effect to apply
 */
enum class BlurType {
    UNIFORM,
    PROGRESSIVE,
}

/**
 * Reusable composable for displaying an image with blur effect in a card
 *
 * @param imageRes Resource ID of the image to display
 * @param contentScale Content scale to apply to the image
 * @param blurType Type of blur effect to apply (UNIFORM or PROGRESSIVE)
 * @param modifier Optional modifier for the card
 * @param useWeight Whether to use weight(1f) instead of fillMaxSize() for the image modifier
 */
@Composable
fun BlurCard(
    @DrawableRes imageRes: Int,
    blurType: BlurType,
    modifier: Modifier = Modifier,
) {
    BpkCard(
        padding = BpkCardPadding.None,
        modifier = modifier,
    ) {
        val imageModifier = Modifier
            .aspectRatio(1f)

        val blurModifier = when (blurType) {
            BlurType.UNIFORM -> imageModifier.bpkUniformBlur()
            BlurType.PROGRESSIVE -> imageModifier.bpkProgressiveBlur()
        }

        Image(
            painter = painterResource(imageRes),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = blurModifier,
        )
    }
}

@Composable
@BlurComponent
@ComposeStory(name = "Blur", StoryKind.DemoOnly)
fun BlurStory(modifier: Modifier = Modifier) {
    Column(
        modifier
            .verticalScroll(rememberScrollState())
            .padding(BpkSpacing.Md),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        UniformBlurExample()
        Spacer(modifier = Modifier.height(BpkSpacing.Lg))
        ProgressiveBlurExample()
    }
}

@Composable
@BlurComponent
@ComposeStory(name = "Uniform", StoryKind.ScreenshotOnly)
fun UniformBlurExample(modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(BpkSpacing.Md),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BpkText(text = "Uniform Blur")
        Spacer(modifier = Modifier.height(BpkSpacing.Md))
        BlurCard(
            imageRes = R.drawable.city,
            blurType = BlurType.UNIFORM,
        )
        Spacer(modifier = Modifier.height(BpkSpacing.Md))
        BlurCard(
            imageRes = R.drawable.checker,
            blurType = BlurType.UNIFORM,
        )
    }
}

@Composable
@BlurComponent
@ComposeStory(name = "Progressive", StoryKind.ScreenshotOnly)
fun ProgressiveBlurExample(modifier: Modifier = Modifier) {
    Column(
        modifier
            .padding(BpkSpacing.Md),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BpkText(text = "Progressive Blur")
        Spacer(modifier = Modifier.height(BpkSpacing.Md))
        BlurCard(
            imageRes = R.drawable.miami,
            blurType = BlurType.PROGRESSIVE,
        )
        Spacer(modifier = Modifier.height(BpkSpacing.Md))
        BlurCard(
            imageRes = R.drawable.checker,
            blurType = BlurType.PROGRESSIVE,
        )
    }
}
