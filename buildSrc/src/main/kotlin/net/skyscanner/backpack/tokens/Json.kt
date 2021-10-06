package net.skyscanner.backpack.tokens

import com.google.gson.Gson
import java.io.File

fun File.asJsonMap(): Map<String, Any> {
  if (!exists()) error("File $this does not exist!")

  val content = readText()
  val map = Gson().fromJson(content, Map::class.java)
  return map.toStringKeyMap()
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
