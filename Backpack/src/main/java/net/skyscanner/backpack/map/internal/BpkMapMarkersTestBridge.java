/**
 * Backpack for Android - Skyscanner's Design System
 * <p>
 * Copyright 2018-2021 Skyscanner Ltd
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package net.skyscanner.backpack.map.internal;

import android.content.Context;
import android.view.View;

// This class is used for screenshots testing only.
// It's not a part of public API and may be changed at any moment without being considered as a breaking change.
import org.jetbrains.annotations.NotNull;

/*package*/ enum BpkMapMarkersTestBridge {
  Instance;

  @NotNull
  /*package*/ View create(@NotNull Context context, @NotNull String title, int icon, boolean showPointer) {
    return BpkMapViewsKt.createBpkMarkerView(context, title, icon, showPointer);
  }
}
