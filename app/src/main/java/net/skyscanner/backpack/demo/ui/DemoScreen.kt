package net.skyscanner.backpack.demo.ui

import android.content.Intent
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.navigationbar.BpkTopNavBar
import net.skyscanner.backpack.compose.navigationbar.IconAction
import net.skyscanner.backpack.compose.navigationbar.NavIcon
import net.skyscanner.backpack.compose.tokens.Settings
import net.skyscanner.backpack.demo.R
import net.skyscanner.backpack.demo.SettingsActivity
import net.skyscanner.backpack.demo.meta.Story

@Composable
fun DemoScreen(
  case: Story,
  modifier: Modifier = Modifier,
  onBack: () -> Unit,
) {
  Column(modifier = modifier) {
    val context = LocalContext.current
    BpkTopNavBar(
      navIcon = NavIcon.Back(
        contentDescription = stringResource(R.string.navigation_back),
        onClick = { onBack() },
      ),
      title = case.component.name + " - " + case.name,
      actions = listOf(
        IconAction(
          icon = BpkIcon.Settings,
          contentDescription = stringResource(R.string.settings_title),
          onClick = {
            val intent = Intent(context, SettingsActivity::class.java)
            context.startActivity(intent)
          },
        ),
      ),
    )
    case.content()
  }
}
