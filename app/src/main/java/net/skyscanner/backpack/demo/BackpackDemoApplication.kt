package net.skyscanner.backpack.demo

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.facebook.stetho.Stetho

/**
 * Application class registered in AndroidManifest.xml
 */
class BackpackDemoApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    Stetho.initializeWithDefaults(this)
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
  }
}
