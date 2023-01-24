package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.symbol.KSAnnotation
import java.io.File

operator fun <T> KSAnnotation.get(param: AnnotationParam<T>): T =
  arguments.first { it.name!!.getShortName() == param.name }.value.toString().let(param::parse)

fun Sequence<KSAnnotation>.find(definition: AnnotationDefinition): KSAnnotation? =
  find { it.shortName.getShortName() == definition.simpleName }

fun fileLog(name: String = "ksp", message: String) {
  val file = File(System.getProperty("user.home"), "$name.txt")
  file.delete()
  file.createNewFile()
  file.writeText(message)
}
