package net.skyscanner.backpack.tokens

import com.google.common.base.CaseFormat

fun String.changeCase(from: CaseFormat, to: CaseFormat): String =
  from.to(to, this)
