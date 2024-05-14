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

package net.skyscanner.backpack.compose.button

import androidx.compose.ui.res.painterResource
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.LongArrowRight
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.compose.rowBackground
import org.junit.Assume.assumeTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner

@RunWith(ParameterizedRobolectricTestRunner::class)
class BpkButtonTest(flavour: Flavor) : BpkSnapshotTest(listOf(flavour.first, flavour.second)) {

    private val type: BpkButtonType = flavour.first
    private val size: BpkButtonSize = flavour.second
    private val icon = BpkIcon.LongArrowRight
    private val iconDrawableRes = R.drawable.sample_icon

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun text() {
        snap(background = { type.rowBackground() }) {
            BpkButton("Button", type = type, size = size, onClick = {})
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun disabled() {
        assumeTrue(size == BpkButtonSize.Default) // colors will be the same on large size

        snap(background = { type.rowBackground() }) {
            BpkButton("Button", type = type, size = size, enabled = false, onClick = {})
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun loading() {
        snap(background = { type.rowBackground() }) {
            BpkButton("Button", type = type, size = size, loading = true, onClick = {})
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun iconAtStart() {
        assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
        // icon is bigger on large size, so we need to test this

        snap {
            BpkButton("Button", icon, BpkButtonIconPosition.Start, type = type, size = size, onClick = {})
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun iconAtEnd() {
        assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
        // icon is bigger on large size, so we need to test this

        snap {
            BpkButton("Button", icon, BpkButtonIconPosition.End, type = type, size = size, onClick = {})
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun longTextWithIconAtEnd() {
        assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
        // icon is bigger on large size, so we need to test this

        snap {
            BpkButton(
                "From: Toronto Pearson Internation Airport",
                icon,
                BpkButtonIconPosition.End,
                type = type,
                size = size,
                onClick = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun longTextWithIconAtStart() {
        assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
        // icon is bigger on large size, so we need to test this

        snap {
            BpkButton(
                "From: Toronto Pearson Internation Airport",
                icon,
                BpkButtonIconPosition.Start,
                type = type,
                size = size,
                onClick = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun iconOnly() {
        assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
        // icon is bigger on large size, so we need to test this

        snap {
            BpkButton(icon, "contentDescription", type = type, size = size, onClick = {})
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun drawableAtStart() {
        assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
        // icon is bigger on large size, so we need to test this

        snap {
            BpkButton(
                text = "Button",
                icon = painterResource(id = iconDrawableRes),
                position = BpkButtonIconPosition.Start,
                type = type,
                size = size,
                onClick = {},
            )
        }
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.Rtl)
    fun drawableAtEnd() {
        assumeTrue(type == BpkButtonType.Primary) // the layout the same across different button types
        // icon is bigger on large size, so we need to test this

        snap {
            BpkButton(
                text = "Button",
                icon = painterResource(id = iconDrawableRes),
                position = BpkButtonIconPosition.End,
                type = type,
                size = size,
                onClick = {},
            )
        }
    }

    companion object {

        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "{0} Screenshot")
        fun flavours(): List<Flavor> = BpkButtonType.entries.flatMap { type ->
            BpkButtonSize.entries.mapNotNull { size ->
                if (type == BpkButtonType.Primary || size == BpkButtonSize.Default) {
                    Flavor(type, size)
                } else {
                    null
                }
            }
        }
    }
}

private typealias Flavor = Pair<BpkButtonType, BpkButtonSize>
