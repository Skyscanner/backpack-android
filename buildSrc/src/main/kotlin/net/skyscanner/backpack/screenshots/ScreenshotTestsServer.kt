package net.skyscanner.backpack.screenshots

import dadb.Dadb
import org.http4k.client.JavaHttpClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Undertow
import org.http4k.server.asServer
import java.io.File
import java.net.BindException

class ScreenshotTestsServer @JvmOverloads constructor(
  private val outDir: File,
  private val serverPort: Int = 8888,
  private val tmpRemoteFile: String = "/data/local/tmp/screenshot.png",
) : AutoCloseable {

  private val server = ::app.asServer(Undertow(serverPort))

  private val adb by lazy { Dadb.discover()!!.apply { setup() } }

  fun start() {
    require(Dadb.list().isEmpty()) { "No device or emulator should be running before the tests" }
    try {
      server.start()
    } catch (t: BindException) {
      // port is already taken: gradle didn't finalise the previous run properly
      // if we just send an empty ping request, the server will close itself due the request being invalid
      JavaHttpClient().invoke(Request(Method.GET, "http://localhost:$serverPort")).close()
      // slleping for 3 seconds to make sure the server is closed
      Thread.sleep(3000L)
      // another attempt
      require(Dadb.list().isEmpty()) { "No device or emulator should be running before the tests" }
      server.start()
    }
  }

  override fun close() {
    server.stop()
    try {
      adb.close()
    } catch (t: Throwable) {
      // do nothing
    }
  }

  private fun app(request: Request) : Response =
    try {
      val component = request.query("component")!!
      val type = request.query("type")!!
      val file = request.query("file")!!

      val folder = outDir.resolve("$type/$component/screenshots")
      if (folder.exists()) {
        folder.mkdirs()
      }

      val outFile = File(folder, "$file.png")
      if (!outFile.exists()) {
        outFile.createNewFile()
      }

      // 20ms delay to make sure the buttons are unpressed and states is updated
      Thread.sleep(20L)

      adb.requireShell("screencap -p $tmpRemoteFile")
      adb.pull(outFile, tmpRemoteFile)

      Response(Status.OK)
    } catch (t: Throwable) {
      close()
      t.printStackTrace()
      Response(Status.INTERNAL_SERVER_ERROR)
    }

  private fun Dadb.setup(): Unit {
    requireShell("settings put global sysui_demo_allowed 1")
    requireShell("settings put global window_animation_scale 0.0")
    requireShell("settings put global transition_animation_scale 0")
    requireShell("settings put global animator_duration_scale 0")
    requireShell("am broadcast -a com.android.systemui.demo -e command clock -e hhmm 1000")
    requireShell("am broadcast -a com.android.systemui.demo -e command battery -e plugged false -e level 100")
    requireShell("am broadcast -a com.android.systemui.demo -e command network -e wifi hide -e level 4 -e mobile show -e datatype none")
    requireShell("am broadcast -a com.android.systemui.demo -e command notifications -e visible false")
    Thread.sleep(50L) // sleeping for 50 msec to make sure these settings actually applied
  }

  private fun Dadb.requireShell(command: String) {
    val result = shell(command)
    require (result.exitCode == 0) { "'$command' failed Output: \n ${result.allOutput}"}
  }

}
