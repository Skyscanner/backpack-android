package net.skyscanner.backpack.demo

import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate

/**
 * Application class registered in AndroidManifest.xml
 */
class BackpackDemoApplication : MultiDexApplication() {
  override fun onCreate() {
    super.onCreate()
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
  }
}
