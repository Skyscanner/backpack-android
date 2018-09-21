package net.skyscanner.backpack.demo

import android.support.multidex.MultiDexApplication
import android.support.v7.app.AppCompatDelegate
import com.facebook.stetho.Stetho

/**
 * Application class registered in AndroidManifest.xml
 */
class BackpackDemoApplication : MultiDexApplication() {
  override fun onCreate() {
    super.onCreate()
    Stetho.initializeWithDefaults(this);
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
  }
}
