import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.VisualTransformation
import net.skyscanner.backpack.compose.fieldset.BpkFieldStatus
import net.skyscanner.backpack.compose.fieldset.LocalFieldStatus
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.searchinputsummary.Prefix
import net.skyscanner.backpack.compose.textfield.BpkClearAction
import net.skyscanner.backpack.compose.textfield.internal.BpkTextFieldImpl
import net.skyscanner.backpack.compose.textfield.internal.BpkTextFieldType
import net.skyscanner.backpack.compose.tokens.Calendar

sealed class SearchTextFieldShape(
    val topLeft: Boolean = false,
    val topRight: Boolean = false,
    val bottomLeft: Boolean = false,
    val bottomRight: Boolean = false,
) {
    data object TopLeft : SearchTextFieldShape(topLeft = true)

    data object Middle : SearchTextFieldShape()

    data object TopRight : SearchTextFieldShape(topRight = true)

    data object Left : SearchTextFieldShape(topLeft = true, bottomLeft = true)

    data object Right : SearchTextFieldShape(topRight = true, bottomRight = true)

    data object BottomLeft : SearchTextFieldShape(bottomLeft = true)

    data object BottomRight : SearchTextFieldShape(bottomRight = true)

    data object Float : SearchTextFieldShape(topLeft = true, topRight = true, bottomLeft = true, bottomRight = true)

    data object Top : SearchTextFieldShape(topLeft = true, topRight = true)

    data object Bottom : SearchTextFieldShape(bottomLeft = true, bottomRight = true)
}

@Composable
fun SearchField(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    readOnly: Boolean = false,
    placeholder: String? = null,
    icon: BpkIcon? = BpkIcon.Calendar,
    status: BpkFieldStatus = LocalFieldStatus.current,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    maxLines: Int = 1,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    clearAction: BpkClearAction? = null,
    shape: SearchTextFieldShape = SearchTextFieldShape.Float,
    onContrast: Boolean = false,
) {
    BpkTextFieldImpl(
        value,
        onValueChange,
        modifier,
        readOnly,
        placeholder,
        prefix = icon?.let { Prefix.Icon(it) },
        status,
        visualTransformation,
        keyboardOptions,
        keyboardActions,
        minLines = 1,
        maxLines,
        interactionSource,
        trailingIcon = null,
        clearAction,
        type = if (onContrast) BpkTextFieldType.Search else BpkTextFieldType.SearchContrast,
        shape,
    )
}
