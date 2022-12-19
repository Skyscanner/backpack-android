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

package net.skyscanner.backpack.compose.fieldset

import androidx.compose.ui.unit.IntSize
import androidx.test.ext.junit.runners.AndroidJUnit4
import net.skyscanner.backpack.compose.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.SnapshotUtil.assumeVariant
import net.skyscanner.backpack.compose.textfield.BpkTextField
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class BpkFieldSetTest : BpkSnapshotTest() {

  @Before
  fun setup() {
    snapshotSize = IntSize(200, 140)
  }

  @Test
  fun default() = snap {
    BpkFieldSet(
      label = "Title",
      description = "Description",
      status = BpkFieldStatus.Default,
    ) {
      BpkTextField(
        value = "",
        onValueChange = { },
        placeholder = "Placeholder",
      )
    }
  }

  @Test
  fun noTitle() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkFieldSet(
        description = "Description",
        status = BpkFieldStatus.Default,
      ) {
        BpkTextField(
          value = "",
          onValueChange = { },
          placeholder = "Placeholder",
        )
      }
    }
  }

  @Test
  fun noDescription() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkFieldSet(
        description = "Description",
        status = BpkFieldStatus.Default,
      ) {
        BpkTextField(
          value = "",
          onValueChange = { },
          placeholder = "Placeholder",
        )
      }
    }
  }

  @Test
  fun noTitleAndDescription() {
    assumeVariant(BpkTestVariant.Default)
    snap {
      BpkFieldSet(
        status = BpkFieldStatus.Default,
      ) {
        BpkTextField(
          value = "",
          onValueChange = { },
          placeholder = "Placeholder",
        )
      }
    }
  }

  @Test
  fun disabled() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    snap {
      BpkFieldSet(
        label = "Title",
        description = "Description",
        status = BpkFieldStatus.Disabled,
      ) {
        BpkTextField(
          value = "",
          onValueChange = { },
          placeholder = "Placeholder",
        )
      }
    }
  }

  @Test
  fun validated() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode)
    snap {
      BpkFieldSet(
        label = "Title",
        description = "Description",
        status = BpkFieldStatus.Validated,
      ) {
        BpkTextField(
          value = "",
          onValueChange = { },
          placeholder = "Placeholder",
        )
      }
    }
  }

  @Test
  fun error() {
    assumeVariant(BpkTestVariant.Default, BpkTestVariant.DarkMode, BpkTestVariant.Rtl)
    snap {
      BpkFieldSet(
        label = "Title",
        description = "Description",
        status = BpkFieldStatus.Error("Error text"),
      ) {
        BpkTextField(
          value = "",
          onValueChange = { },
          placeholder = "Placeholder",
        )
      }
    }
  }
}
