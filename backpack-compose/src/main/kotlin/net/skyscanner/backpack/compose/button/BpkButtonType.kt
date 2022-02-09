package net.skyscanner.backpack.compose.button

sealed class BpkButtonType {

  object Primary : BpkButtonType()

  object Secondary : BpkButtonType()

  object PrimaryOnDark : BpkButtonType()

  object PrimaryOnLight : BpkButtonType()

  object Featured : BpkButtonType()

  object Destructive : BpkButtonType()

  object Link : BpkButtonType()

}
