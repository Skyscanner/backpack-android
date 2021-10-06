package net.skyscanner.backpack.tokens

import org.gradle.internal.impldep.com.google.common.base.CaseFormat

fun String.changeCase(from: CaseFormat, to: CaseFormat): String =
  from.to(to, this)
