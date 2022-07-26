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

package net.skyscanner.backpack.compose.textfield

import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.Accessibility
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkTextFieldTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    setDimensions(height = 50, width = 200)
  }

  @Test
  fun default() = composed {
    BpkTextField(
      value = "Value",
      onValueChange = {},
      placeholder = "Placeholder",
      status = BpkFieldStatus.Default,
    )
  }

  @Test
  fun readOnly() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkTextField(
        value = "Value",
        onValueChange = {},
        placeholder = "Placeholder",
        status = BpkFieldStatus.Default,
        readOnly = true,
      )
    }
  }

  @Test
  fun placeholder() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkTextField(
        value = "",
        onValueChange = {},
        placeholder = "Placeholder",
        status = BpkFieldStatus.Default,
      )
    }
  }

  @Test
  fun withLeadingIcon() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.Rtl, BpkTestVariant.DarkMode)
    composed {
      BpkTextField(
        value = "Value",
        onValueChange = {},
        placeholder = "Placeholder",
        status = BpkFieldStatus.Default,
        icon = BpkIcon.Accessibility,
      )
    }
  }

  @Test
  fun singleLine() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkTextField(
        value = "Value ".repeat(20),
        onValueChange = {},
        placeholder = "Placeholder ".repeat(20),
        status = BpkFieldStatus.Default,
        maxLines = 1,
      )
    }
  }

  @Test
  fun singleLinePlaceholder() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkTextField(
        value = "",
        onValueChange = {},
        placeholder = "Placeholder ".repeat(20),
        status = BpkFieldStatus.Default,
        maxLines = 1,
      )
    }
  }

  @Test
  fun multiline() {
    assumeVariant(BpkTestVariant.Default)
    composed {
      BpkTextField(
        value = "Value ".repeat(20),
        onValueChange = {},
        placeholder = "Placeholder ".repeat(20),
        status = BpkFieldStatus.Default,
        maxLines = 3,
      )
    }
  }

  @Test
  fun multilinePlaceholder() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    composed {
      BpkTextField(
        value = "",
        onValueChange = {},
        placeholder = "Placeholder ".repeat(20),
        status = BpkFieldStatus.Default,
        maxLines = 3,
      )
    }
  }

  @Test
  fun disabled() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkTextField(
        value = "Value",
        onValueChange = {},
        placeholder = "Placeholder",
        status = BpkFieldStatus.Disabled,
      )
    }
  }

  @Test
  fun disabledPlaceholder() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    composed {
      BpkTextField(
        value = "",
        onValueChange = {},
        placeholder = "Placeholder",
        status = BpkFieldStatus.Disabled,
      )
    }
  }

  @Test
  fun validated() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    composed {
      BpkTextField(
        value = "Value",
        onValueChange = {},
        placeholder = "Placeholder",
        status = BpkFieldStatus.Validated,
      )
    }
  }

  @Test
  fun error() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    composed {
      BpkTextField(
        value = "Value",
        onValueChange = {},
        placeholder = "Placeholder",
        status = BpkFieldStatus.Error("Error text"),
      )
    }
  }
}
