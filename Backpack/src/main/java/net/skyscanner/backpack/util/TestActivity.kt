package net.skyscanner.backpack.util

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.skyscanner.backpack.R

/**
 * Activity only used for test purposes
 */
internal class TestActivity : AppCompatActivity() {
  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setTheme(R.style.Theme_AppCompat)
  }
}
