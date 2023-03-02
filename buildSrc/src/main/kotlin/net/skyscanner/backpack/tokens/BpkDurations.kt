/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.skyscanner.backpack.tokens

import com.google.common.base.CaseFormat

interface BpkDurations : Map<String, Int>

object BpkDuration {

  sealed class Category : BpkParser<Map<String, Any>, BpkDurations> {

    object Animation : Category() {
      override fun invoke(source: Map<String, Any>): BpkDurations =
        parseIntegers(source, "animations", "ANIMATION_DURATION_")
    }
  }

  sealed class Format<Output> : BpkTransformer<BpkDurations, Output> {

    data class Xml(val namespace: String) : Format<String>() {
      override fun invoke(source: BpkDurations): String =
        toXml(source, namespace)
    }
  }
}

@Suppress("UNCHECKED_CAST")
private fun parseIntegers(
  source: Map<String, Any>,
  category: String,
  prefixToRemove: String,
  filter: (Map.Entry<String, Int>) -> Boolean = { true },
): BpkDurations {

  val props = source.getValue("props") as Map<String, Map<String, String>>
  val data = props.filter { (_, value) -> value["type"] == "duration" && value["category"] == category }

  val map = data
    .mapValues { it.value.getValue("value").removeSuffix("ms").toIntOrNull() }
    .mapKeys { it.key.removePrefix(prefixToRemove) }
    .filterValues { it != null }
    .let { it as Map<String, Int> }
    .filter(filter)

  return object : BpkDurations, Map<String, Int> by map {
    override fun toString(): String =
      map.toString()
  }
}

private fun toXml(source: BpkDurations, type: String): String =
  source.map { (name, value) ->
    "  <integer name=\"$type${CaseFormat.UPPER_UNDERSCORE.to(CaseFormat.UPPER_CAMEL, name)}\">$value</integer>"
  }.joinToString("\n")
