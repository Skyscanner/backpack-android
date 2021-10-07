package net.skyscanner.backpack.tokens

import com.google.gson.Gson
import java.io.File

fun <T> Pipeline<File>.readAs(format: BpkFormat<T>) : Pipeline<T> =
  pipeTo(format)

sealed class BpkFormat<T>: (File) -> T {

  object Json : BpkFormat<Map<String, Any>>() {
    override fun invoke(file: File): Map<String, Any> {
      val content = file.readText()
      val map = Gson().fromJson(content, Map::class.java)
      return map.toStringKeyMap()
    }
  }
}

private fun Map<*, *>.toStringKeyMap(): Map<String, Any> {
  val result = mutableMapOf<String, Any>()
  for ((key, value) in entries) {
    if (value is Map<*, *>) {
      result[key.toString()] = value.toStringKeyMap()
    } else if (value != null) {
      result[key.toString()] = value
    }
  }
  return result
}
