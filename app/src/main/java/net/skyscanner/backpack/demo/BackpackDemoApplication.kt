package net.skyscanner.backpack.demo

import android.app.Application
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatDelegate
import io.github.inflationx.viewpump.ViewPump
import net.skyscanner.backpack.demo.data.SharedPreferences
import net.skyscanner.backpack.util.BpkInterceptor
import com.facebook.stetho.Stetho

/**
 * Application class registered in AndroidManifest.xml
 */

class BackpackDemoApplication : Application() {

  companion object {

    private lateinit var instance: BackpackDemoApplication

    var highlight = false
      set(value) {
        field = value
        SharedPreferences.saveHighlightState(instance, value)
      }

    fun triggerRebirth(context: Context) {
      val packageManager = context.packageManager
      val intent = packageManager.getLaunchIntentForPackage(context.packageName)
      val componentName = intent!!.component
      val mainIntent = Intent.makeRestartActivityTask(componentName)
      context.startActivity(mainIntent)
      Runtime.getRuntime().exit(0)
    }
  }

  override fun onCreate() {
    super.onCreate()
    instance = applicationContext!! as BackpackDemoApplication
    Stetho.initializeWithDefaults(this)
    AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
    highlight = SharedPreferences.shouldHighlight(this)

    if (highlight) {
      ViewPump.init(ViewPump.builder()
        .addInterceptor(BpkInterceptor())
        .build())
    }
  }
}
