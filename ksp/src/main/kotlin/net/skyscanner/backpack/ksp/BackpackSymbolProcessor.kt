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
import net.skyscanner.backpack.ksp.visitor.SamplesVisitor
import net.skyscanner.backpack.ksp.visitor.SnapshotsVisitor
import net.skyscanner.backpack.ksp.visitor.StoriesVisitor
import net.skyscanner.backpack.ksp.writer.writeListOfStories

@OptIn(ExperimentalProcessingApi::class)
class BackpackSymbolProcessor(
  private val environment: SymbolProcessorEnvironment,
) : SymbolProcessor {

  var invoked = false

  override fun process(resolver: Resolver): List<KSAnnotated> {
    if (invoked) return emptyList()

    val filer = XProcessingEnv.create(environment.options, resolver, environment.codeGenerator, environment.logger).filer

    val components = resolver
      .getSymbolsWithAnnotation(ComponentAnnotation.qualifiedName)
      .filter { it.validate() }
      .mapNotNull { it.accept(ComponentsVisitor, Unit) }
      .associateBy { it.location.filePath }

    val stories = resolver
      .getSymbolsWithAnnotation(StoryAnnotation.qualifiedName)
      .filter { it.validate() }
      .mapNotNull { it.accept(StoriesVisitor, components) }
      .also { writeListOfStories(it.toList(), filer) }

    val samples = resolver
      .getSymbolsWithAnnotation(SampleAnnotation.qualifiedName)
      .filter { it.validate() }
      .mapNotNull { it.accept(SamplesVisitor, components) }

    val snapshots = resolver
      .getSymbolsWithAnnotation(SnapshotAnnotation.qualifiedName)
      .filter { it.validate() }
      .mapNotNull { it.accept(SnapshotsVisitor, components) }

    fileLog("ksp", stories.joinToString(separator = "\n"))

    invoked = true
    return emptyList()
  }
}

class BackpackSymbolProcessorProvider : SymbolProcessorProvider {

  override fun create(environment: SymbolProcessorEnvironment): SymbolProcessor =
    BackpackSymbolProcessor(environment)
}
