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

package net.skyscanner.backpack.demo.compose.nativecompare

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavBarStyle
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.NativeAndroidBack
import net.skyscanner.backpack.compose.tokens.Settings
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.NativeCompareComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/** BpkTopNavBar next to Material 3's TopAppBar, themed with Backpack's semantic tokens only. */
@Composable
@NativeCompareComponent
@ComposeStory("Navigation bar", kind = StoryKind.DemoOnly)
fun NativeNavigationBarStory(modifier: Modifier = Modifier) {
    ComparisonStory(modifier = modifier) {
        NavigationBarComparison.entries.forEach { pair ->
            NativeNavigationBarComparison(pair)
        }
    }
}

@Composable
internal fun NativeNavigationBarComparison(pair: NavigationBarComparison, modifier: Modifier = Modifier) {
    ComparisonPair(
        label = pair.label,
        difference = pair.difference,
        rows = listOf(
            ComparisonRow(
                backpack = {
                    BpkTopNavBar(
                        navIcon = NavIcon.Back(contentDescription = stringResource(R.string.navigation_back)) {},
                        title = stringResource(R.string.native_compare_nav_bar_title),
                        insets = null,
                        actions = listOf(
                            IconAction(icon = BpkIcon.Settings, contentDescription = stringResource(R.string.native_compare_nav_bar_action)) {},
                        ),
                        style = pair.style,
                        modifier = Modifier.fillMaxWidth(),
                    )
                },
                native = { MaterialTopBar(contrast = pair.contrast) },
            ),
        ),
        backpack = pair.bpkSwatches,
        native = pair.nativeSwatches,
        modifier = modifier,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MaterialTopBar(contrast: Boolean) {
    TopAppBar(
        title = { Text(stringResource(R.string.native_compare_nav_bar_title)) },
        navigationIcon = { IconButton(onClick = {}) { MaterialIcon(BpkIcon.NativeAndroidBack) } },
        actions = { IconButton(onClick = {}) { MaterialIcon(BpkIcon.Settings) } },
        windowInsets = WindowInsets(0, 0, 0, 0),
        colors = if (contrast) contrastTopBarColors() else TopAppBarDefaults.topAppBarColors(),
    )
}

/** The stock bar moved onto a contrast surface: inverse surface roles only. */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun contrastTopBarColors(): TopAppBarColors = TopAppBarDefaults.topAppBarColors(
    containerColor = MaterialTheme.colorScheme.inverseSurface,
    titleContentColor = MaterialTheme.colorScheme.inverseOnSurface,
    navigationIconContentColor = MaterialTheme.colorScheme.inverseOnSurface,
    actionIconContentColor = MaterialTheme.colorScheme.inverseOnSurface,
)

internal enum class NavigationBarComparison(
    @StringRes val label: Int,
    @StringRes val difference: Int,
    val style: NavBarStyle,
    val contrast: Boolean,
    val bpkSwatches: List<Swatch>,
    val nativeSwatches: List<Swatch>,
) {
    Default(
        label = R.string.native_compare_default,
        difference = R.string.native_compare_nav_bar_default_difference,
        style = NavBarStyle.Default,
        contrast = false,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_title, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_nav_icon, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_actions, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_title, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_nav_icon, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_actions, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
        ),
    ),
    SurfaceContrast(
        label = R.string.native_compare_surface_contrast,
        difference = R.string.native_compare_nav_bar_surface_contrast_difference,
        style = NavBarStyle.SurfaceContrast,
        contrast = true,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_contrast) { BpkTheme.colors.surfaceContrast },
            Swatch.token(R.string.native_compare_state_title, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_nav_icon, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_actions, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_contrast) { BpkTheme.colors.surfaceContrast },
            Swatch.token(R.string.native_compare_state_title, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_nav_icon, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
            Swatch.token(R.string.native_compare_state_actions, R.string.native_compare_token_text_on_dark) { BpkTheme.colors.textOnDark },
        ),
    ),
}
