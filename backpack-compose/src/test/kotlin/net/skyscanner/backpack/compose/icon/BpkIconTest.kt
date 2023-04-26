/*
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

package net.skyscanner.backpack.compose.icon

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertNull
import org.junit.Test

class BpkIconTest {

    @Test
    fun `finds account--add icon`() {
        val icon = BpkIcon.findByName("account--add")
        assertNotNull(icon)
        assertEquals("account--add", icon?.name)
    }

    @Test
    fun `finds baby-carriage icon`() {
        val icon = BpkIcon.findByName("baby-carriage")
        assertNotNull(icon)
        assertEquals("baby-carriage", icon?.name)
    }

    @Test
    fun `returns null for unknown icon name`() {
        val icon = BpkIcon.findByName("unexisting-icon-name")
        assertNull(icon)
    }
}
