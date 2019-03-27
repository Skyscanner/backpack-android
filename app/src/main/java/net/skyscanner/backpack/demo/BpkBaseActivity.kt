package net.skyscanner.backpack.demo

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PorterDuff
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import io.github.inflationx.viewpump.ViewPumpContextWrapper
import net.skyscanner.backpack.demo.data.SharedPreferences
import net.skyscanner.backpack.util.ThemeOverlayEnforcement

@SuppressLint("Registered")
open class BpkBaseActivity : AppCompatActivity() {

  private val sensorManager by lazy {
    getSystemService(SENSOR_SERVICE) as SensorManager
  }

  private val accelerometer by lazy {
    sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
  }

  private val shakeListener by lazy {
    ShakeListener(this::onShaked)
  }

  override fun attachBaseContext(newBase: Context) {
    super.attachBaseContext(ThemeOverlayEnforcement(ViewPumpContextWrapper.wrap(newBase)))
  }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTheme(SharedPreferences.getTheme(this))

    getSystemService(SENSOR_SERVICE)?.let {
      it as SensorManager
      val sensor = it.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)
      it.registerListener(shakeListener, sensor, SensorManager.SENSOR_DELAY_NORMAL)
    }
  }

  override fun onCreateOptionsMenu(menu: Menu): Boolean {
    menuInflater.inflate(R.menu.settings, menu)
    val drawable = menu.findItem(R.id.settings_button).icon
    if (drawable != null) {
      drawable.mutate()
      val bpkWhite = ResourcesCompat.getColor(resources, R.color.bpkWhite, null)
      drawable.setColorFilter(bpkWhite, PorterDuff.Mode.SRC_ATOP)
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
