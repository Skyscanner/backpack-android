/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

package net.skyscanner.backpack.docs

import okhttp3.HttpUrl
import okhttp3.OkHttpClient
import okhttp3.Request

object RemoteScreenGrab {

  private val serverIp: String = "10.0.2.2"
  private val serverPort = 8888
  private val okHttp = OkHttpClient()

  fun takeScreenshot(component: String, type: String, file: String) {
    val response = okHttp.newCall(
      Request.Builder().url(
        HttpUrl.Builder()
          .scheme("http")
          .host(serverIp)
          .port(serverPort)
          .setQueryParameter("component", component)
          .setQueryParameter("type", type)
          .setQueryParameter("file", file)
          .build(),
      ).build(),
    ).execute()

    require(response.code() == 200) { "Unable to take screenshot for $component. Error code: ${response.code()}" }
  }
}
