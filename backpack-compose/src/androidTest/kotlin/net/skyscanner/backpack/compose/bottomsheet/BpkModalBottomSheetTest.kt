package net.skyscanner.backpack.compose.bottomsheet

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import net.skyscanner.backpack.compose.theme.BpkTheme
import org.junit.Rule
import org.junit.Test

class BpkModalBottomSheetTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val title = "Title"
    private val customTitleContentDesc = "Custom content desc"

    @Test
    fun givenTitleContentDescriptionNotSet_whenBpkModalBottomSheet_thenSemanticsSet() {
        composeTestRule.setContent {
            BpkTheme {
                BpkModalBottomSheet(
                    onDismissRequest = { },
                    title = title,
                ) {
                }
            }
        }

        composeTestRule
            .onNodeWithContentDescription(title)
            .assertExists()
    }

    @Test
    fun givenTitleContentDescriptionSet_whenBpkModalBottomSheet_thenSemanticsSet() {
        composeTestRule.setContent {
            BpkTheme {
                BpkModalBottomSheet(
                    onDismissRequest = { },
                    title = title,
                    titleContentDescription = customTitleContentDesc,
                ) {
                }
            }
        }

        composeTestRule
            .onNodeWithContentDescription(customTitleContentDesc)
            .assertExists()
    }
}
