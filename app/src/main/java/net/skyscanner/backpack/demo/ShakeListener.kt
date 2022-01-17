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

package net.skyscanner.backpack.demo

import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener

class ShakeListener(val onShaked: () -> Unit) : SensorEventListener {

  private var lastUpdate: Long = 0
  private var lastX: Float = 0f
  private var lastY: Float = 0f
  private var lastZ: Float = 0f

  override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {}

  override fun onSensorChanged(event: SensorEvent) {
    val curTime = System.currentTimeMillis()
    // only allow one update every 100ms.
    if (curTime - lastUpdate > 100) {
      val diffTime = curTime - lastUpdate
      lastUpdate = curTime

      val x = event.values[0]
      val y = event.values[1]
      val z = event.values[2]

      val speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000

      if (speed > 800) {
        onShaked()
      }

      lastX = x
      lastY = y
      lastZ = z
    }
  }
}
