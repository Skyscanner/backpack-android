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

import android.annotation.SuppressLint
import android.content.Intent
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import net.skyscanner.backpack.demo.data.SharedPreferences
import net.skyscanner.backpack.util.unsafeLazy

@SuppressLint("Registered")
open class BpkBaseActivity : AppCompatActivity() {

  private val sensorManager by unsafeLazy {
    getSystemService(SENSOR_SERVICE) as SensorManager
  }

  private val accelerometer by unsafeLazy {
    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
  }

  private val shakeListener by unsafeLazy {
    ShakeListener(this::onShaked)
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTheme(SharedPreferences.getTheme(this))

    val sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
    sensorManager.registerListener(shakeListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.settings, menu)
    val drawable = menu.findItem(R.id.settings_button).icon
    if (drawable != null) {
      drawable.mutate()
      val bpkWhite = getColor(R.color.bpkTextOnDark)
      drawable.colorFilter =
        BlendModeColorFilterCompat.createBlendModeColorFilterCompat(bpkWhite, BlendModeCompat.SRC_ATOP)
    }
    return super.onCreateOptionsMenu(menu)
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    when (item.itemId) {
      R.id.settings_button -> {
        val intent = Intent(this, SettingsActivity::class.java)
        startActivity(intent)
      }
    }
    return super.onOptionsItemSelected(item)
  }

  override fun onResume() {
    super.onResume()
    accelerometer?.let {
      sensorManager.registerListener(shakeListener, it, SensorManager.SENSOR_DELAY_NORMAL)
    }
  }

  override fun onPause() {
    super.onPause()
    sensorManager.unregisterListener(shakeListener)
  }

  private fun onShaked() {
    val intent = Intent(this, SettingsActivity::class.java)
    startActivity(intent)
  }
}
