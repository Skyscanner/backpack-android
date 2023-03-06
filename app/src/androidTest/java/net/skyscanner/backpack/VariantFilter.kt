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

package net.skyscanner.backpack

import androidx.test.filters.AbstractFilter
import org.junit.runner.Description

@Suppress("unused")
internal class VariantFilter : AbstractFilter() {
  override fun evaluateTest(description: Description): Boolean {
    return when {
      description.annotations.filterIsInstance<Variants>().isNotEmpty() -> {
        val condition = description.getAnnotation(Variants::class.java)!!
        condition.variants.contains(BpkTestVariant.current)
      }
      description.testClass.annotations.filterIsInstance<Variants>().isNotEmpty() -> {
        val condition = description.testClass.getAnnotation(Variants::class.java)!!
        condition.variants.contains(BpkTestVariant.current)
      }
      else -> true
    }
  }

  override fun describe(): String {
    return "Only run tests annotated with the supplied variants"
  }
}
@Target(AnnotationTarget.FUNCTION, AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class Variants(vararg val variants: BpkTestVariant)
