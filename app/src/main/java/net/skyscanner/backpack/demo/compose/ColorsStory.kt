package net.skyscanner.backpack.demo.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import net.skyscanner.backpack.compose.text.BpkText
import net.skyscanner.backpack.compose.tokens.BpkColor
import net.skyscanner.backpack.compose.tokens.BpkSpacing
import net.skyscanner.backpack.demo.data.Token
import net.skyscanner.backpack.demo.data.values

@Composable
@Preview
fun ColorsComposeStory() {
  Column {
    val data: List<Token<Color>> = BpkColor.values
    for (item: Token<Color> in data) {
      ColorSampleRow(token = item)
    }
  }
}

@Composable
private fun ColorSampleRow(token: Token<Color>) {
  Row(
    modifier = Modifier
      .height(56.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    BpkText(
      text = token.name,
      modifier = Modifier
        .weight(1f)
        .padding(BpkSpacing.Base)
    )
    Box(
      modifier = Modifier
        .size(56.dp)
        .padding(1.dp)
        .background(token.value)
    )
  }
}
