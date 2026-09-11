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
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.bottomnav.BpkBottomNav
import net.skyscanner.backpack.compose.bottomnav.BpkBottomNavItem
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.theme.BpkTheme
import net.skyscanner.backpack.compose.tokens.Explore
import net.skyscanner.backpack.compose.tokens.Hotels
import net.skyscanner.backpack.compose.tokens.Trips
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.components.NativeCompareComponent
import net.skyscanner.backpack.demo.meta.ComposeStory
import net.skyscanner.backpack.meta.StoryKind

/** BpkBottomNav next to Material 3's NavigationBar, themed with Backpack's semantic tokens only. */
@Composable
@NativeCompareComponent
@ComposeStory("Bottom navigation", kind = StoryKind.DemoOnly)
fun NativeBottomNavigationStory(modifier: Modifier = Modifier) {
    ComparisonStory(modifier = modifier) {
        BottomNavigationComparison.entries.forEach { pair ->
            NativeBottomNavigationComparison(pair)
        }
    }
}

@Composable
internal fun NativeBottomNavigationComparison(pair: BottomNavigationComparison, modifier: Modifier = Modifier) {
    ComparisonPair(
        label = pair.label,
        difference = pair.difference,
        rows = listOf(
            ComparisonRow(
                backpack = {
                    val items = bottomNavItems()
                    BpkBottomNav(
                        onTabClicked = {},
                        selectedItemId = 0,
                        items = items.mapIndexed { index, item ->
                            BpkBottomNavItem(title = item.title, id = index, icon = item.icon, showBadge = pair.badge && index == 1)
                        },
                    )
                },
                native = {
                    NavigationBar {
                        bottomNavItems().forEachIndexed { index, item ->
                            NavigationBarItem(
                                selected = index == 0,
                                onClick = {},
                                icon = {
                                    if (pair.badge && index == 1) {
                                        BadgedBox(badge = { Badge() }) { MaterialIcon(item.icon) }
                                    } else {
                                        MaterialIcon(item.icon)
                                    }
                                },
                                label = { Text(item.title, style = MaterialTheme.typography.labelSmall) },
                            )
                        }
                    }
                },
            ),
        ),
        backpack = pair.bpkSwatches,
        native = pair.nativeSwatches,
        modifier = modifier,
    )
}

private class NavItem(val title: String, val icon: BpkIcon)

@Composable
private fun bottomNavItems(): List<NavItem> = listOf(
    NavItem(stringResource(R.string.native_compare_bottom_nav_explore), BpkIcon.Explore),
    NavItem(stringResource(R.string.native_compare_bottom_nav_trips), BpkIcon.Trips),
    NavItem(stringResource(R.string.native_compare_bottom_nav_hotels), BpkIcon.Hotels),
)

internal enum class BottomNavigationComparison(
    @StringRes val label: Int,
    @StringRes val difference: Int,
    val badge: Boolean,
    val bpkSwatches: List<Swatch>,
    val nativeSwatches: List<Swatch>,
) {
    Default(
        label = R.string.native_compare_default,
        difference = R.string.native_compare_bottom_nav_default_difference,
        badge = false,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.none(R.string.native_compare_state_indicator),
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.token(R.string.native_compare_state_pressed, R.string.native_compare_token_core_accent_ripple) { BpkTheme.colors.coreAccent },
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_fill, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
            Swatch.token(R.string.native_compare_state_selected, R.string.native_compare_token_text_primary) { BpkTheme.colors.textPrimary },
            Swatch.token(R.string.native_compare_state_indicator, R.string.native_compare_token_surface_highlight) { BpkTheme.colors.surfaceHighlight },
            Swatch.token(R.string.native_compare_state_text, R.string.native_compare_token_text_secondary) { BpkTheme.colors.textSecondary },
            Swatch.platform(R.string.native_compare_state_pressed),
        ),
    ),
    WithBadge(
        label = R.string.native_compare_bottom_nav_badge,
        difference = R.string.native_compare_bottom_nav_badge_difference,
        badge = true,
        bpkSwatches = listOf(
            Swatch.token(R.string.native_compare_state_badge, R.string.native_compare_token_core_accent) { BpkTheme.colors.coreAccent },
            Swatch.token(R.string.native_compare_state_badge_border, R.string.native_compare_token_surface_default) { BpkTheme.colors.surfaceDefault },
        ),
        nativeSwatches = listOf(
            Swatch.token(R.string.native_compare_state_badge, R.string.native_compare_token_status_danger_spot) { BpkTheme.colors.statusDangerSpot },
            Swatch.none(R.string.native_compare_state_badge_border),
        ),
    ),
}
