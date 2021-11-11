/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018-2021 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.skyscanner.backpack.tokens

import com.google.gson.Gson
import java.io.File


sealed class BpkFormat<T>: (File) -> T {

  object Json : BpkFormat<Map<String, Any>>() {
    override fun invoke(file: File): Map<String, Any> {
      val content = file.readText()
      val map = Gson().fromJson(content, Map::class.java)
      return map.toStringKeyMap()
    }
  }

  object Folder : BpkFormat<List<File>>() {

    override fun invoke(file: File): List<File> =
      file.listFiles()?.toList().orEmpty()

  }

}

private fun Map<*, *>.toStringKeyMap(): Map<String, Any> {
  val result = mutableMapOf<String, Any>()
  for ((key, value) in entries) {
    if (value is Map<*, *>) {
      result[key.toString()] = value.toStringKeyMap()
    } else if (value != null) {
      result[key.toString()] = value.toString()
    }
  }
  return result
}
