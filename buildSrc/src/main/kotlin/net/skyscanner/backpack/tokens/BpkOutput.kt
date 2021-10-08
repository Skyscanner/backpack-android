package net.skyscanner.backpack.tokens

import com.google.common.io.Resources
import com.squareup.kotlinpoet.AnnotationSpec
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.TypeSpec
import java.io.File
import java.nio.charset.StandardCharsets
import java.util.*

fun <T> Pipeline<T>.saveTo(output: BpkOutput<T>): Pipeline<Boolean> =
  pipeTo(output)

sealed class BpkOutput<Input> : (Input) -> Boolean {

  data class KotlinFile(
    val srcDir: String,
    val pkg: String,
  ) : BpkOutput<TypeSpec>() {

    override fun invoke(typeSpec: TypeSpec): Boolean {
      val currentYear = Calendar.getInstance().get(Calendar.YEAR)
      val copyright = Resources.toString(Resources.getResource("copyright.txt"), StandardCharsets.UTF_8)
      FileSpec.builder(pkg, typeSpec.name!!)
        .addComment(copyright, currentYear)
        .suppressWarningTypes("RedundantVisibilityModifier", "unused")
        .addComment("Auto-generated: do not edit")
        .addType(typeSpec)
        .build()
        .writeTo(File(srcDir))
      return true
    }
  }
}

private fun FileSpec.Builder.suppressWarningTypes(vararg types: String) : FileSpec.Builder {
  if (types.isEmpty()) {
    return this
  }

  val format = "%S,".repeat(types.count()).trimEnd(',')
  return addAnnotation(
    AnnotationSpec.builder(ClassName("", "Suppress"))
      .addMember(format, *types)
      .build()
  )
}
