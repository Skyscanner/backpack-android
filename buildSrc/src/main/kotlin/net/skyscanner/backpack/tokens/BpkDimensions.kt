package net.skyscanner.backpack.tokens

import com.google.common.base.CaseFormat
import com.squareup.kotlinpoet.*
import javax.script.ScriptEngineManager

interface BpkDimensions : Map<String, Int>

object BpkDimension {

  sealed class Category : BpkParser<BpkDimensions> {

    object Spacing : Category() {
      override fun invoke(source: Map<String, Any>): BpkDimensions =
        parseDimensions(source, "spacings")
    }

    object Radii : Category() {
      override fun invoke(source: Map<String, Any>): BpkDimensions =
        parseDimensions(source, "radii")
    }

    object Elevation : Category() {
      override fun invoke(source: Map<String, Any>): BpkDimensions =
        parseDimensions(source, "elevation")
    }

  }

  sealed class Format : BpkTransformer<BpkDimensions, TypeSpec> {

    data class Compose(val namespace: String) : Format() {
      override fun invoke(source: BpkDimensions): TypeSpec =
        toCompose(source, namespace)
    }

  }

}

private val JsEvaluator = ScriptEngineManager().getEngineByName("js")

@Suppress("UNCHECKED_CAST")
private fun parseDimensions(
  source: Map<String, Any>,
  category: String,
  evaluate: Boolean = false,
): BpkDimensions {

  val props = source.getValue("props") as Map<String, Map<String, String>>
  val data = props.filter { (_, value) -> value["type"] == "size" && value["category"] == category }

  val map = data
    .mapValues {
      val value = it.value.getValue("value")
      if (evaluate) {
        JsEvaluator.eval(value).toString()
      } else {
        value
      }
    }
    .mapValues { it.value.toIntOrNull() }
    .mapKeys { it.key.removePrefix(category.toUpperCase() + "_") }
    .filterValues { it != null }
    .let { it as Map<String, Int> }

  return object : BpkDimensions, Map<String, Int> by map {
    override fun toString(): String =
      map.toString()
  }
}

private val DpClass = ClassName("androidx.compose.ui.unit", "Dp")
private val StableAnnotation = ClassName("androidx.compose.runtime", "Stable")
private val dpExtension = MemberName("androidx.compose.ui.unit", "dp", isExtension = true)

private fun toCompose(
  source: BpkDimensions,
  namespace: String,
): TypeSpec =
  TypeSpec.objectBuilder(namespace)
    .addProperties(
      source.map { (name, value) ->
        PropertySpec
          .builder(name.changeCase(CaseFormat.UPPER_UNDERSCORE, CaseFormat.UPPER_CAMEL), DpClass)
          .addAnnotation(StableAnnotation)
          .initializer(buildCodeBlock { add("%L.%M", value, dpExtension) })
          .build()
      }
    )
    .build()
