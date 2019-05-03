package net.skyscanner.backpack.demo

import android.app.Activity
import android.app.Application
import android.os.Bundle

class ThemeApplier(private val themeRes: Int) : Application.ActivityLifecycleCallbacks {
  override fun onActivityPaused(activity: Activity?) {}
  override fun onActivityResumed(activity: Activity?) {}
  override fun onActivityStarted(activity: Activity?) {}
  override fun onActivityDestroyed(activity: Activity?) {}
  override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {}
  override fun onActivityStopped(activity: Activity?) {}
  override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
    activity?.theme?.applyStyle(themeRes, true)
  }
}
