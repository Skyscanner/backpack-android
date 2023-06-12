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

package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import net.skyscanner.backpack.ksp.visitor.StoriesVisitor
import net.skyscanner.backpack.ksp.visitor.ComponentsVisitor
import net.skyscanner.backpack.ksp.visitor.StoryAnnotationsVisitor
import net.skyscanner.backpack.ksp.writer.writeListOfStories
import net.skyscanner.backpack.meta.ComponentMarker
import net.skyscanner.backpack.meta.StoryMarker

class BackpackSymbolProcessor(
    private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

    var invoked = false

    override fun process(resolver: Resolver): List<KSAnnotated> {
        if (invoked) return emptyList()

        val components = resolver
            .getSymbolsWithAnnotation(ComponentMarker::class.qualifiedName!!)
            .filter { it.validate() }
            .mapNotNull { it.accept(ComponentsVisitor, Unit) }
            .associateBy { it.id }

        val storyAnnotations =
            resolver
                .getSymbolsWithAnnotation(StoryMarker::class.qualifiedName!!)
                .filter { it.validate() }
                .mapNotNull { it.accept(StoryAnnotationsVisitor, Unit) }

        val stories = storyAnnotations
            .flatMap { annotation ->
                resolver
                    .getSymbolsWithAnnotation(annotation.annotation.qualifiedName)
                    .filter { it.validate() }
                    .mapNotNull { it.accept(StoriesVisitor(annotation), components) }
            }

        writeListOfStories(stories.toList(), environment.codeGenerator)

        invoked = true
        return emptyList()
    }
}

class BackpackSymbolProcessorProvider : SymbolProcessorProvider {

    override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
        BackpackSymbolProcessor(environment)
}
