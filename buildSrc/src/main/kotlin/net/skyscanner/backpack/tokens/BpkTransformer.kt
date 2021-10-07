package net.skyscanner.backpack.tokens

interface BpkTransformer<In, Out> : (In) -> Out

fun <In, Out> Pipeline<In>.transformTo(transformer: BpkTransformer<In, Out>): Pipeline<Out> =
  pipeTo(transformer)
