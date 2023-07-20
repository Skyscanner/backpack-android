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
package net.skyscanner.backpack.screenshots

import dadb.Dadb
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.get
import io.ktor.http.HttpStatusCode
import io.ktor.server.application.ApplicationCall
import io.ktor.server.application.call
import io.ktor.server.engine.embeddedServer
import io.ktor.server.netty.Netty
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.routing
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import java.io.File

class ScreenshotTestsServer @JvmOverloads constructor(
    private val outDir: File,
    private val serverPort: Int = 8888,
    private val tmpRemoteFile: String = "/data/local/tmp/screenshot.png",
) : AutoCloseable {

    private val scope = CoroutineScope(Dispatchers.IO)

    fun start() {
        require(Dadb.list().isEmpty()) { "No device or emulator should be running before the tests" }

        try {
            startServer()
        } catch (t: Throwable) {
            runBlocking {
                // port is already taken: gradle didn't finalise the previous run properly
                // if we just send an empty ping request, the server will close itself due the request being invalid
                HttpClient(CIO).get("http://localhost:$serverPort")
                // sleeping for 3 seconds to make sure the server is closed
                delay(3000L)
            }
            // another attempt
            startServer()
        }
    }

    private var server = scope.embeddedServer(Netty, serverPort) {
        routing {
            get("/") {
                app(call)
            }
        }
    }

    private fun startServer() {
        server.start()
    }

    override fun close() {
        scope.cancel("The server was stopped")
    }

    private val adb by lazy { Dadb.discover()!!.apply { setup() } }

    private suspend fun app(call: ApplicationCall): Unit =
        try {
            val request = call.request
            val component = request.queryParameters["component"]
            val type = request.queryParameters["type"]
            val file = request.queryParameters["file"]

            val folder = outDir.resolve("$type/$component/screenshots")
            if (!folder.exists()) {
                folder.mkdirs()
            }

            val outFile = File(folder, "$file.png")
            if (!outFile.exists()) {
                outFile.createNewFile()
            }

            adb.requireShell("screencap -p $tmpRemoteFile")
            adb.pull(outFile, tmpRemoteFile)

            call.respond(HttpStatusCode.OK)
        } catch (t: Throwable) {
            call.respond(HttpStatusCode.InternalServerError)
            close()
            throw t
        }

    private fun Dadb.setup() {
        requireShell("settings put global sysui_demo_allowed 1")
        requireShell("settings put global window_animation_scale 0.0")
        requireShell("settings put global transition_animation_scale 0")
        requireShell("settings put global animator_duration_scale 0")
        requireShell("am broadcast -a com.android.systemui.demo -e command clock -e hhmm 1000")
        requireShell("am broadcast -a com.android.systemui.demo -e command battery -e plugged false -e level 100")
        requireShell("am broadcast -a com.android.systemui.demo -e command network -e wifi hide -e level 4 -e mobile show -e datatype none")
        requireShell("am broadcast -a com.android.systemui.demo -e command notifications -e visible false")
        Thread.sleep(200L) // sleeping for 200 millis to make sure these settings actually applied
    }

    private fun Dadb.requireShell(command: String) {
        val result = shell(command)
        require(result.exitCode == 0) { "'$command' failed Output: \n ${result.allOutput}" }
    }
}
