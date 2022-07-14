package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun ColorsComposeStory() {
  Column {
    Text(text = "Any text")
    Text(text = "More text")
  }
}
