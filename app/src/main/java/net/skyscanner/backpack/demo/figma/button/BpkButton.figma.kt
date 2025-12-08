import androidx.compose.runtime.Composable
import com.figma.code.connect.Figma
import com.figma.code.connect.FigmaConnect
import com.figma.code.connect.FigmaProperty
import com.figma.code.connect.FigmaType
import com.figma.code.connect.FigmaVariant
import net.skyscanner.backpack.compose.button.BpkButton
import net.skyscanner.backpack.compose.button.BpkButtonIconPosition
import net.skyscanner.backpack.compose.button.BpkButtonSize
import net.skyscanner.backpack.compose.button.BpkButtonType
import net.skyscanner.backpack.compose.icon.BpkIcon
import net.skyscanner.backpack.compose.tokens.ArrowRight

@FigmaConnect("FIGMA_BUTTON")
@FigmaVariant(key = "Icon", value = "None")
class BpkButtonCodeConnect {

    @FigmaProperty(FigmaType.Enum, "Style")
    val type: BpkButtonType = Figma.mapping(
        "Primary" to BpkButtonType.Primary,
        "Secondary" to BpkButtonType.Secondary,
        "Featured" to BpkButtonType.Featured,
        "Destructive" to BpkButtonType.Destructive,
        "Primary on Dark" to BpkButtonType.PrimaryOnDark,
        "Primary on Light" to BpkButtonType.PrimaryOnLight,
        "Secondary on Dark" to BpkButtonType.SecondaryOnDark,
        "Link" to BpkButtonType.Link,
        "Link on Dark" to BpkButtonType.LinkOnDark,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    val enabled: Boolean = Figma.mapping(
        "Normal" to true,
        "Disabled" to false,
        "Loading" to true,
        "Pressed" to true,
        "Focused" to true,
        "Hover" to true,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    val loading: Boolean = Figma.mapping(
        "Normal" to false,
        "Disabled" to false,
        "Loading" to true,
        "Pressed" to false,
        "Focused" to false,
        "Hover" to false,
    )

    @FigmaProperty(FigmaType.Enum, "Size")
    val size: BpkButtonSize = Figma.mapping(
        "Default" to BpkButtonSize.Default,
        "Large" to BpkButtonSize.Large,
    )

    @Composable
    fun ButtonExample() {
        BpkButton(
            text = "Label",
            type = type,
            size = size,
            enabled = enabled,
            loading = loading,
            onClick = { },
        )
    }
}

@FigmaConnect("FIGMA_BUTTON")
@FigmaVariant(key = "Icon", value = "Left")
class BpkButtonLeftIconCodeConnect {

    @FigmaProperty(FigmaType.Enum, "Style")
    val type: BpkButtonType = Figma.mapping(
        "Primary" to BpkButtonType.Primary,
        "Secondary" to BpkButtonType.Secondary,
        "Featured" to BpkButtonType.Featured,
        "Destructive" to BpkButtonType.Destructive,
        "Primary on Dark" to BpkButtonType.PrimaryOnDark,
        "Primary on Light" to BpkButtonType.PrimaryOnLight,
        "Secondary on Dark" to BpkButtonType.SecondaryOnDark,
        "Link" to BpkButtonType.Link,
        "Link on Dark" to BpkButtonType.LinkOnDark,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    val enabled: Boolean = Figma.mapping(
        "Normal" to true,
        "Disabled" to false,
        "Loading" to true,
        "Pressed" to true,
        "Focused" to true,
        "Hover" to true,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    val loading: Boolean = Figma.mapping(
        "Normal" to false,
        "Disabled" to false,
        "Loading" to true,
        "Pressed" to false,
        "Focused" to false,
        "Hover" to false,
    )

    @FigmaProperty(FigmaType.Enum, "Size")
    val size: BpkButtonSize = Figma.mapping(
        "Default" to BpkButtonSize.Default,
        "Large" to BpkButtonSize.Large,
    )

    @Composable
    fun ButtonExample() {
        BpkButton(
            text = "Label",
            type = type,
            size = size,
            enabled = enabled,
            loading = loading,
            icon = BpkIcon.ArrowRight,
            position = BpkButtonIconPosition.Start,
            onClick = { },
        )
    }
}

@FigmaConnect("FIGMA_BUTTON")
@FigmaVariant(key = "Icon", value = "Right")
class BpkButtonRightIconCodeConnect {

    @FigmaProperty(FigmaType.Enum, "Style")
    val type: BpkButtonType = Figma.mapping(
        "Primary" to BpkButtonType.Primary,
        "Secondary" to BpkButtonType.Secondary,
        "Featured" to BpkButtonType.Featured,
        "Destructive" to BpkButtonType.Destructive,
        "Primary on Dark" to BpkButtonType.PrimaryOnDark,
        "Primary on Light" to BpkButtonType.PrimaryOnLight,
        "Secondary on Dark" to BpkButtonType.SecondaryOnDark,
        "Link" to BpkButtonType.Link,
        "Link on Dark" to BpkButtonType.LinkOnDark,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    val enabled: Boolean = Figma.mapping(
        "Normal" to true,
        "Disabled" to false,
        "Loading" to true,
        "Pressed" to true,
        "Focused" to true,
        "Hover" to true,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    val loading: Boolean = Figma.mapping(
        "Normal" to false,
        "Disabled" to false,
        "Loading" to true,
        "Pressed" to false,
        "Focused" to false,
        "Hover" to false,
    )

    @FigmaProperty(FigmaType.Enum, "Size")
    val size: BpkButtonSize = Figma.mapping(
        "Default" to BpkButtonSize.Default,
        "Large" to BpkButtonSize.Large,
    )

    @Composable
    fun ButtonExample() {
        BpkButton(
            text = "Label",
            type = type,
            size = size,
            enabled = enabled,
            loading = loading,
            icon = BpkIcon.ArrowRight,
            position = BpkButtonIconPosition.End,
            onClick = { },
        )
    }
}

@FigmaConnect("FIGMA_BUTTON")
@FigmaVariant(key = "Icon", value = "Icon only")
class BpkButtonIconOnlyCodeConnect {

    @FigmaProperty(FigmaType.Enum, "Style")
    val type: BpkButtonType = Figma.mapping(
        "Primary" to BpkButtonType.Primary,
        "Secondary" to BpkButtonType.Secondary,
        "Featured" to BpkButtonType.Featured,
        "Destructive" to BpkButtonType.Destructive,
        "Primary on Dark" to BpkButtonType.PrimaryOnDark,
        "Primary on Light" to BpkButtonType.PrimaryOnLight,
        "Secondary on Dark" to BpkButtonType.SecondaryOnDark,
        "Link" to BpkButtonType.Link,
        "Link on Dark" to BpkButtonType.LinkOnDark,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    val enabled: Boolean = Figma.mapping(
        "Normal" to true,
        "Disabled" to false,
        "Loading" to true,
        "Pressed" to true,
        "Focused" to true,
        "Hover" to true,
    )

    @FigmaProperty(FigmaType.Enum, "State")
    val loading: Boolean = Figma.mapping(
        "Normal" to false,
        "Disabled" to false,
        "Loading" to true,
        "Pressed" to false,
        "Focused" to false,
        "Hover" to false,
    )

    @FigmaProperty(FigmaType.Enum, "Size")
    val size: BpkButtonSize = Figma.mapping(
        "Default" to BpkButtonSize.Default,
        "Large" to BpkButtonSize.Large,
    )

    @Composable
    fun ButtonExample() {
        BpkButton(
            icon = BpkIcon.ArrowRight,
            contentDescription = "Label",
            type = type,
            size = size,
            enabled = enabled,
            loading = loading,
            onClick = { },
        )
    }
}
