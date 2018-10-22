package net.skyscanner.backpack.demo

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho

/**
 * Application class registered in AndroidManifest.xml
 */
class BackpackDemoApplication : MultiDexApplication() {
  override fun onCreate() {
    super.onCreate()
    Stetho.initializeWithDefaults(this)
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
  }
}
