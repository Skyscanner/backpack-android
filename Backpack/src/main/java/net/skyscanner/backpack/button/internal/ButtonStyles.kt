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

package net.skyscanner.backpack.button.internal

import android.content.Context
import net.skyscanner.backpack.R

internal sealed class ButtonStyles : (Context) -> ButtonStyle {

    data object Primary : ButtonStyles() {
        override fun invoke(context: Context) = ButtonStyle.fromTheme(
            context = context,
            style = R.attr.bpkButtonPrimaryStyle,
            bgColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryNormalBackground,
            bgPressedColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryPressedBackground,
            contentColorRes = net.skyscanner.backpack.common.R.color.bpkTextOnDark,
        )
    }

    data object Secondary : ButtonStyles() {
        override fun invoke(context: Context) = ButtonStyle.fromTheme(
            context = context,
            style = R.attr.bpkButtonSecondaryStyle,
            bgColorRes = net.skyscanner.backpack.common.R.color.__privateButtonSecondaryNormalBackground,
            bgPressedColorRes = net.skyscanner.backpack.common.R.color.__privateButtonSecondaryPressedBackground,
            contentColorRes = net.skyscanner.backpack.common.R.color.bpkTextPrimary,
        )
    }

    data object Featured : ButtonStyles() {
        override fun invoke(context: Context) = ButtonStyle.fromTheme(
            context = context,
            style = R.attr.bpkButtonFeaturedStyle,
            bgColorRes = net.skyscanner.backpack.common.R.color.__privateButtonFeaturedNormalBackground,
            bgPressedColorRes = net.skyscanner.backpack.common.R.color.__privateButtonFeaturedPressedBackground,
            contentColorRes = net.skyscanner.backpack.common.R.color.bpkTextPrimaryInverse,
        )
    }

    data object Destructive : ButtonStyles() {
        override fun invoke(context: Context) = ButtonStyle.fromTheme(
            context = context,
            style = R.attr.bpkButtonDestructiveStyle,
            bgColorRes = net.skyscanner.backpack.common.R.color.__privateButtonDestructiveNormalBackground,
            bgPressedColorRes = net.skyscanner.backpack.common.R.color.__privateButtonDestructivePressedBackground,
            contentColorRes = net.skyscanner.backpack.common.R.color.__privateButtonDestructiveNormalForeground,
            contentPressedColorRes = net.skyscanner.backpack.common.R.color.bpkTextPrimaryInverse,
        )
    }

    data object PrimaryOnDark : ButtonStyles() {
        override fun invoke(context: Context) = ButtonStyle.fromTheme(
            context = context,
            style = R.attr.bpkButtonPrimaryOnDarkStyle,
            bgColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryOnDarkNormalBackground,
            bgPressedColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryOnDarkPressedBackground,
            bgDisabledColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryOnDarkDisabledBackground,
            contentColorRes = net.skyscanner.backpack.common.R.color.bpkTextOnLight,
            contentDisabledColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryOnDarkDisabledForeground,
            rippleColorRes = R.color.__buttonOnDarkRipple,
        )
    }

    data object PrimaryOnLight : ButtonStyles() {
        override fun invoke(context: Context) = ButtonStyle.fromTheme(
            context = context,
            style = R.attr.bpkButtonPrimaryOnLightStyle,
            bgColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryOnLightNormalBackground,
            bgPressedColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryOnLightPressedBackground,
            bgDisabledColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryOnLightDisabledBackground,
            contentColorRes = net.skyscanner.backpack.common.R.color.bpkTextOnDark,
            contentDisabledColorRes = net.skyscanner.backpack.common.R.color.__privateButtonPrimaryOnLightDisabledForeground,
            rippleColorRes = R.color.__buttonOnLightRipple,
        )
    }

    data object SecondaryOnDark : ButtonStyles() {
        override fun invoke(context: Context) = ButtonStyle.fromTheme(
            context = context,
            style = R.attr.bpkButtonSecondaryOnDarkStyle,
            bgColorRes = net.skyscanner.backpack.common.R.color.__privateButtonSecondaryOnDarkNormalBackground,
            bgPressedColorRes = net.skyscanner.backpack.common.R.color.__privateButtonSecondaryOnDarkPressedBackground,
            bgDisabledColorRes = net.skyscanner.backpack.common.R.color.__privateButtonSecondaryOnDarkDisabledBackground,
            contentColorRes = net.skyscanner.backpack.common.R.color.bpkTextOnDark,
            contentDisabledColorRes = net.skyscanner.backpack.common.R.color.__privateButtonSecondaryOnDarkDisabledForeground,
            rippleColorRes = R.color.__buttonOnDarkRipple,
        )
    }

    data object Link : ButtonStyles() {
        override fun invoke(context: Context) = ButtonStyle.fromTheme(
            context = context,
            style = R.attr.bpkButtonLinkStyle,
            bgColorRes = android.R.color.transparent,
            bgDisabledColorRes = android.R.color.transparent,
            contentColorRes = net.skyscanner.backpack.common.R.color.bpkTextPrimary,
            contentPressedColorRes = net.skyscanner.backpack.common.R.color.bpkTextPrimary,
            rippleColorRes = R.color.__buttonLinkRipple,
        )
    }

    data object LinkOnDark : ButtonStyles() {
        override fun invoke(context: Context) = ButtonStyle.fromTheme(
            context = context,
            style = R.attr.bpkButtonLinkOnDarkStyle,
            bgColorRes = android.R.color.transparent,
            bgDisabledColorRes = android.R.color.transparent,
            contentColorRes = net.skyscanner.backpack.common.R.color.bpkTextOnDark,
            contentPressedColorRes = net.skyscanner.backpack.common.R.color.__privateButtonLinkOnDarkPressedForeground,
            contentDisabledColorRes = net.skyscanner.backpack.common.R.color.__privateButtonLinkOnDarkDisabledForeground,
            rippleColorRes = R.color.__buttonLinkOnDarkRipple,
        )
    }
}
