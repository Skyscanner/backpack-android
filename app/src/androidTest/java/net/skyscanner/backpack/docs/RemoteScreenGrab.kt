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

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode
import io.ktor.http.URLProtocol
import kotlinx.coroutines.runBlocking

object RemoteScreenGrab {

    private const val serverIp: String = "10.0.2.2"
    private const val serverPort = 8888
    private val client = HttpClient(CIO)

    fun takeScreenshot(component: String, type: String, file: String) = runBlocking {
        val response = client.get {
            url {
                protocol = URLProtocol.HTTP
                host = serverIp
                port = serverPort
                parameter("component", component)
                parameter("type", type)
                parameter("file", file)
            }
        }

        require(response.status == HttpStatusCode.OK) { "Unable to take screenshot for $component. Error code: ${response.status}" }
    }
}
