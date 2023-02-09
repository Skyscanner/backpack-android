package net.skyscanner.backpack.ksp.visitor

import com.google.devtools.ksp.symbol.KSValueParameter
import net.skyscanner.backpack.ksp.PreviewParameter
import net.skyscanner.backpack.ksp.PreviewProviderDefinition
import net.skyscanner.backpack.ksp.find
import net.skyscanner.backpack.ksp.get

object PreviewProviderFinder {

  fun find(parameters: List<KSValueParameter>): PreviewProviderDefinition? {
    val parameter = parameters.find { it.annotations.find(PreviewParameter) != null }
    if (parameter != null) {
      val annotation = parameter.annotations.find(PreviewParameter)!!
      return PreviewProviderDefinition(
        name = parameter.name!!.getShortName(),
        limit = annotation[PreviewParameter.paramLimit],
        type = annotation[PreviewParameter.paramsProvider],
      )
    }
    return null
  }
}
