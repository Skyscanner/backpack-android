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

package net.skyscanner.backpack.compose.blur

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.demo.R
import org.junit.Test

class BpkBlurTest : BpkSnapshotTest() {

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun uniformBlur() {
        snap {
            BlurTestContent(modifier = Modifier.bpkUniformBlur())
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun progressiveBlur() {
        snap {
            BlurTestContent(modifier = Modifier.bpkProgressiveBlur())
        }
    }

    @Test
    fun uniformBlurWithRoundedCorners() {
        snap {
            BlurTestContent(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .bpkUniformBlur(),
            )
        }
    }

    @Test
    fun progressiveBlurWithRoundedCorners() {
        snap {
            BlurTestContent(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .bpkProgressiveBlur(),
            )
        }
    }

    @Test
    fun noBlur() {
        snap {
            BlurTestContent(modifier = Modifier)
        }
    }

    @Composable
    private fun BlurTestContent(modifier: Modifier = Modifier) {
        Box(
            modifier = modifier
                .size(200.dp)
                .background(Color.White),
        ) {
            Image(
                painter = painterResource(id = R.drawable.checker),
                contentDescription = "Test image",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize(),
            )
        }
    }
}
