package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.symbol.KSType

interface AnnotationDefinition {
  val pkg: String
  val simpleName: String
  val qualifiedName: String
    get() = "$pkg.$simpleName"
}

abstract class AnnotationParam<Type>(val name: String) {
  abstract fun parse(value: Any): Type
}

fun AnnotationDefinition.stringParamOf(name: String): AnnotationParam<String> =
  object : AnnotationParam<String>(name) {
    override fun parse(value: Any): String =
      value as String
  }

fun AnnotationDefinition.booleanParamOf(name: String): AnnotationParam<Boolean> =
  object : AnnotationParam<Boolean>(name) {
    override fun parse(value: Any): Boolean =
      value as Boolean
  }

fun AnnotationDefinition.intParamOf(name: String): AnnotationParam<Int> =
  object : AnnotationParam<Int>(name) {
    override fun parse(value: Any): Int =
      value as Int
  }

fun AnnotationDefinition.enumParamOf(name: String): AnnotationParam<EnumValue> =
  object : AnnotationParam<EnumValue>(name) {
    override fun parse(value: Any): EnumValue =
      EnumValue(
        value = (value as KSType).declaration.simpleName.getShortName(),
        type = value.declaration.qualifiedName!!.getQualifier(),
      )
  }

fun AnnotationDefinition.typeParamOf(name: String): AnnotationParam<String> =
  object : AnnotationParam<String>(name) {
    override fun parse(value: Any): String =
      (value as KSType).declaration.qualifiedName!!.asString()
  }

fun AnnotationDefinition.enumParamsOf(name: String): AnnotationParam<List<EnumValue>> =
  object : AnnotationParam<List<EnumValue>>(name) {
    override fun parse(value: Any): List<EnumValue> =
      (value as List<KSType>)
        .map {
          EnumValue(
            value = it.declaration.simpleName.getShortName(),
            type = it.declaration.qualifiedName!!.getQualifier(),
          )
        }
  }
