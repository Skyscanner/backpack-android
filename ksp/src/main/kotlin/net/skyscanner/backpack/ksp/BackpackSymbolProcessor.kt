package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import net.skyscanner.backpack.ksp.visitor.ComponentsVisitor
import net.skyscanner.backpack.ksp.visitor.StoriesVisitor

class BackpackSymbolProcessor : SymbolProcessor {

  override fun process(resolver: Resolver): List<KSAnnotated> {

    val components = mutableMapOf<String, ComponentDefinition>()
    resolver
      .getSymbolsWithAnnotation(References.ComponentAnnotation)
      .filter { it.validate() }
      .forEach { it.accept(ComponentsVisitor(components), Unit) }

    val stories = mutableListOf<StoryDefinition>()
    resolver
      .getSymbolsWithAnnotation(References.StoryAnnotation)
      .filter { it.validate() }
      .forEach { it.accept(StoriesVisitor(components, stories), Unit) }

//    fileLog("ksp", stories.joinToString(separator = "\n"))

    return emptyList()
  }
}

class BackpackSymbolProcessorProvider : SymbolProcessorProvider {

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
    BackpackSymbolProcessor()
}
