package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.symbol.KSAnnotation

operator fun <T> KSAnnotation.get(param: AnnotationParam<T>): T =
  arguments.first { it.name!!.getShortName() == param.name }.value!!.let(param::parse)

fun Sequence<KSAnnotation>.find(definition: AnnotationDefinition): KSAnnotation? =
  find { it.shortName.getShortName() == definition.simpleName }
