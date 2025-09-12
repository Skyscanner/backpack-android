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

package net.skyscanner.backpack.snackbar

import android.widget.FrameLayout
import net.skyscanner.backpack.BpkSnapshotTest
import net.skyscanner.backpack.BpkTestVariant
import net.skyscanner.backpack.Variants
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.util.unsafeLazy
import org.junit.Test

class BpkSnackbarTests : BpkSnapshotTest() {

    private val root by unsafeLazy {
        FrameLayout(testContext)
    }

    @Test
    fun default() {
        capture {
            BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun withAction() {
        capture {
            BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
                .setAction("Action") {}
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun withTitle() {
        capture {
            BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
                .setTitle("Title")
                .setAction("Action") {}
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun withIcon() {
        capture {
            BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
                .setIcon(net.skyscanner.backpack.internal.icons.R.drawable.bpk_tick_circle)
                .setAction("Action") {}
        }
    }

    @Test
    fun withTitleAndIcon() {
        capture {
            BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
                .setTitle("Title")
                .setIcon(net.skyscanner.backpack.internal.icons.R.drawable.bpk_tick_circle)
                .setAction("Action") {}
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun iconOnly() {
        capture {
            BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
                .setAction(net.skyscanner.backpack.internal.icons.R.drawable.bpk_close, "Close") {}
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun titleWithAction() {
        capture {
            BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
                .setTitle("Title")
                .setAction(net.skyscanner.backpack.internal.icons.R.drawable.bpk_close, "Close") {}
        }
    }

    @Test
    @Variants(BpkTestVariant.Default)
    fun iconWithAction() {
        capture {
            BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
                .setIcon(net.skyscanner.backpack.internal.icons.R.drawable.bpk_tick_circle)
                .setAction(net.skyscanner.backpack.internal.icons.R.drawable.bpk_close, "Close") {}
        }
    }

    @Test
    fun titleWithIconAndAction() {
        capture {
            BpkSnackbar.make(root, "Test", BpkSnackbar.LENGTH_INDEFINITE)
                .setTitle("Title")
                .setIcon(net.skyscanner.backpack.internal.icons.R.drawable.bpk_tick_circle)
                .setAction(net.skyscanner.backpack.internal.icons.R.drawable.bpk_close, "Close") {}
        }
    }

    private inline fun capture(crossinline what: () -> BpkSnackbar) {
        val snackbar: BpkSnackbar = what()
        snackbar.show()

        snap(snackbar.rawSnackbar.view, padding = 0, width = 350)
    }
}
