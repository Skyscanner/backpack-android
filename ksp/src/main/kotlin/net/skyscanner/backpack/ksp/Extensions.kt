package net.skyscanner.backpack.ksp

import com.google.devtools.ksp.symbol.KSAnnotation
import java.io.File

operator fun KSAnnotation.get(name: String): String =
  arguments.first { it.name!!.getShortName() == name }.value.toString()

fun fileLog(name: String = "ksp", message: String) {
  val file = File("/Users/vitaliibabichev/$name.txt")
  file.delete()
  file.createNewFile()
  file.writeText(message)
}
