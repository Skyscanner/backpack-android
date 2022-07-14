package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.demo.data.Token
import net.skyscanner.backpack.demo.data.values

@Composable
@Preview
fun ColorsComposeStory() {
  Column {
    val data: List<Token<Color>> = BpkColor.values
    for (item: Token<Color> in data) {
      Text(text = item.name)
    }
  }
}
