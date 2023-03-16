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

package net.skyscanner.backpack.badge

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkBadgeTest : BpkSnapshotTest() {

    @Test
    fun default() {
        val badge = BpkBadge(testContext)
        badge.text = "Message"
        snap(badge)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun success() {
        val badge = BpkBadge(testContext)
        badge.type = BpkBadge.Type.Success
        badge.text = "Message"
        snap(badge)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun destructive() {
        val badge = BpkBadge(testContext)
        badge.type = BpkBadge.Type.Destructive
        badge.text = "Message"
        snap(badge)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun inverse() {
        val badge = BpkBadge(testContext)
        badge.type = BpkBadge.Type.Inverse
        badge.text = "Message"
        snap(badge, background = R.color.bpkCorePrimary)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun normal() {
        val badge = BpkBadge(testContext)
        badge.type = BpkBadge.Type.Normal
        badge.text = "Message"
        snap(badge)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun strong() {
        val badge = BpkBadge(testContext)
        badge.type = BpkBadge.Type.Strong
        badge.text = "Message"
        snap(badge)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun warning() {
        val badge = BpkBadge(testContext)
        badge.type = BpkBadge.Type.Warning
        badge.text = "Message"
        snap(badge)
    }

    @Test
    @Variants(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    fun outline() {
        val badge = BpkBadge(testContext)
        badge.type = BpkBadge.Type.Outline
        badge.text = "Message"
        snap(badge, background = R.color.bpkCorePrimary)
    }
}
