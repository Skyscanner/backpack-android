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

import com.github.takahirom.roborazzi.RoborazziRule
import org.junit.Assume
import java.io.File

object SnapshotUtil {
    fun roborazziOptions(variant: String, tags: List<Any>): RoborazziRule.Options =
        RoborazziRule.Options(
            outputDirectoryPath = "screenshots/oss/debug/$variant",
            outputFileProvider = { description, outputDirectory, fileExtension ->
                File(
                    outputDirectory,
                    if (tags.isEmpty()) {
                        "${description.testClass.name}_${description.methodName}.$fileExtension"
                    } else {
                        tags.joinToString(
                            separator = "_",
                            prefix = "${description.testClass.name}_${description.methodName.substringBefore("[")}.",
                            postfix = ".$fileExtension",
                        ) { it.toString() }
                    },
                )
            },
        )

    fun filterTest(variant: BpkTestVariant) {
        // when we've got tags that means it's a parameterized test, so we need to do the filtering manually
        val callingFunctionTrace = Thread.currentThread().stackTrace[4]
        val callerClass = Class.forName(callingFunctionTrace.className).getMethod(callingFunctionTrace.methodName)
        callerClass.isAnnotationPresent(Variants::class.java).let { hasVariantsAnnotation ->
            if (hasVariantsAnnotation) {
                val variants = callerClass.getAnnotation(Variants::class.java)!!
                if (!variants.variants.contains(variant)) {
                    Assume.assumeFalse("Skipping test for variant $variant", true)
                }
            }
        }
    }
}
