package net.skyscanner.backpack

import android.app.Activity
import android.content.Context
import android.content.res.Configuration
import android.os.Looper
import android.view.ContextThemeWrapper
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.app.AppCompatDialog
import androidx.test.platform.app.InstrumentationRegistry
import java.util.Locale
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.ThemeApplier
import net.skyscanner.backpack.util.BpkTheme

sealed class BpkTestVariant(val id: String) {

  open fun applyToActivity(activity: Activity): Activity =
    activity

  open fun newActivity(activity: Activity): Activity =
    activity

  open fun newContext(context: Context): Context =
    BpkTheme.wrapContextWithDefaults(
      androidx.appcompat.view.ContextThemeWrapper(
        context,
        R.style.AppTheme,
      )
    )

  object Default : BpkTestVariant("default")

  object Themed : BpkTestVariant("themed") {

    override fun applyToActivity(activity: Activity): Activity =
      activity.apply {
        ThemeApplier(R.style.LondonTheme).onActivityCreated(this, null)
      }

    override fun newContext(context: Context) =
      ContextThemeWrapper(super.newContext(context), R.style.LondonTheme)
  }

  object DarkMode : BpkTestVariant("dm") {

    override fun newActivity(activity: Activity): Activity {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      val config = Configuration().apply {
        uiMode = uiMode or Configuration.UI_MODE_NIGHT_YES
      }
      activity.applyOverrideConfiguration(config)
      return activity
    }

    override fun newContext(context: Context): Context {
      AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
      if (Looper.myLooper() == null) {
        Looper.prepare()
      }
      val dialog = AppCompatDialog(super.newContext(context))
      return dialog.context
    }
  }

  object Rtl : BpkTestVariant("rtl") {

    private val rtlLocale = Locale("ar")

    override fun newActivity(activity: Activity): Activity {
      Locale.setDefault(rtlLocale)
      val config = Configuration().apply {
        setLayoutDirection(rtlLocale)
        setLocale(rtlLocale)
      }
      activity.applyOverrideConfiguration(config)
      return activity
    }

    override fun newContext(context: Context): Context {
      Locale.setDefault(rtlLocale)
      val config = Configuration(context.resources.configuration).apply {
        setLayoutDirection(rtlLocale)
        setLocale(rtlLocale)
      }
      return super.newContext(context.createConfigurationContext(config))
    }
  }

  companion object {

    val current: BpkTestVariant
      get() = when (InstrumentationRegistry.getArguments().getString("suite")) {
        Default.id -> Default
        Themed.id -> Themed
        DarkMode.id -> DarkMode
        Rtl.id -> Rtl
        else -> Default
      }
  }
}
