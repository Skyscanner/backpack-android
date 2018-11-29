package net.skyscanner.backpack.demo

import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDexApplication
import com.facebook.stetho.Stetho
import io.github.inflationx.viewpump.ViewPump
import net.skyscanner.backpack.util.BpkInterceptor

/**
 * Application class registered in AndroidManifest.xml
 */
open class BackpackDemoApplication : MultiDexApplication() {
  override fun onCreate() {
    super.onCreate()
    Stetho.initializeWithDefaults(this)
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    ViewPump.init(ViewPump.builder()
      .addInterceptor(BpkInterceptor(true))
      .build())
  }
}
