package net.skyscanner.backpack.ksp

import androidx.room.compiler.processing.ExperimentalProcessingApi
import androidx.room.compiler.processing.XProcessingEnv
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.processing.SymbolProcessorEnvironment
import com.google.devtools.ksp.processing.SymbolProcessorProvider
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import net.skyscanner.backpack.ksp.visitor.ComponentsVisitor
import net.skyscanner.backpack.ksp.visitor.ComposeStoriesVisitor
import net.skyscanner.backpack.ksp.visitor.ViewStoriesVisitor
import net.skyscanner.backpack.ksp.writer.writeListOfStories

@OptIn(ExperimentalProcessingApi::class)
class BackpackSymbolProcessor(
  private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

  var invoked = false

  override fun process(resolver: Resolver): List<KSAnnotated> {
    if (invoked) return emptyList()
    // if we run the ksp for test sources we will generate empty stories list
    if (!resolver.getAllFiles().any { "main" in it.filePath }) return emptyList()

    val filer = XProcessingEnv.create(environment.options, resolver, environment.codeGenerator, environment.logger).filer

    val components = resolver
      .getSymbolsWithAnnotation(ComponentAnnotation.qualifiedName)
      .filter { it.validate() }
      .mapNotNull { it.accept(ComponentsVisitor, Unit) }
      .associateBy { it.id }

    val composeStories = resolver
      .getSymbolsWithAnnotation(ComposeStoryAnnotation.qualifiedName)
      .filter { it.validate() }
      .mapNotNull { it.accept(ComposeStoriesVisitor, components) }

    val viewStories = resolver
      .getSymbolsWithAnnotation(ViewStoryAnnotation.qualifiedName)
      .filter { it.validate() }
      .mapNotNull { it.accept(ViewStoriesVisitor, components) }

    writeListOfStories((composeStories + viewStories).toList(), filer)

    fileLog("ksp", composeStories.joinToString(separator = "\n"))

    invoked = true
    return emptyList()
  }
}

class BackpackSymbolProcessorProvider : SymbolProcessorProvider {

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
    BackpackSymbolProcessor(environment)
}
