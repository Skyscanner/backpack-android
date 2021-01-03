/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
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

package net.skyscanner.backpack.demo

import android.os.Bundle
import androidx.fragment.app.Fragment

/**
 * A fragment representing a single Component detail screen.
 * This fragment is contained in a [.ComponentDetailActivity]
 * on handsets.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
open class ComponentDetailFragment : Fragment() {

  override fun onCreate(savedInstanceState: Bundle?) {
    if (arguments?.containsKey(ARG_ITEM_ID) == true) {
      val activity = this.activity as ComponentDetailActivity
      activity.title = arguments!!.getString(ARG_ITEM_ID)
    }

    super.onCreate(savedInstanceState)
  }

  companion object {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    val ARG_ITEM_ID = "item_id"

    /*
     * Tells if the fragment running in automation mode.
     * Use this to avoid showing toast and things designed only for
     * user feedback
     */
    val AUTOMATION_MODE = "automation_mode"
  }
}
