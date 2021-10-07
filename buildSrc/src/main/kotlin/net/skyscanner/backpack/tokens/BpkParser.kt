package net.skyscanner.backpack.tokens

interface BpkParser<T> : (Map<String, Any>) -> T

fun <T> Pipeline<Map<String, Any>>.parseAs(parser: BpkParser<T>): Pipeline<T> =
  pipeTo(parser)
