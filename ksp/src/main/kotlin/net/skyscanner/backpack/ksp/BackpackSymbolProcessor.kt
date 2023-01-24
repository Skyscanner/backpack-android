package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import net.skyscanner.backpack.ksp.visitor.ComponentsVisitor
import net.skyscanner.backpack.ksp.visitor.SamplesVisitor
import net.skyscanner.backpack.ksp.visitor.StoriesVisitor

class BackpackSymbolProcessor : SymbolProcessor {

  override fun process(resolver: Resolver): List<KSAnnotated> {

    val components = resolver
      .getSymbolsWithAnnotation(References.ComponentAnnotation)
      .filter { it.validate() }
      .mapNotNull { it.accept(ComponentsVisitor, Unit) }
      .associateBy { it.location.filePath }

    val stories = resolver
      .getSymbolsWithAnnotation(References.StoryAnnotation)
      .filter { it.validate() }
      .mapNotNull { it.accept(StoriesVisitor, components) }

    val samples = resolver
      .getSymbolsWithAnnotation(References.SampleAnnotation)
      .filter { it.validate() }
      .mapNotNull { it.accept(SamplesVisitor, components) }

    fileLog("ksp", samples.joinToString(separator = "\n"))

    return emptyList()
  }
}

class BackpackSymbolProcessorProvider : SymbolProcessorProvider {

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
    BackpackSymbolProcessor()
}
