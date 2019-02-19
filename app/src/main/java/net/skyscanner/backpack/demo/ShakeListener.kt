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
