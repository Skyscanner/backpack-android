package net.skyscanner.backpack.screenshots

import dadb.Dadb
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.server.Undertow
import org.http4k.server.asServer
import java.io.File

class ScreenshotTestsServer @JvmOverloads constructor(
  private val outDir: File,
  private val serverPort: Int = 8888,
  private val tmpRemoteFile: String = "/data/local/tmp/screenshot.png",
) : AutoCloseable {

  private val server = ::app.asServer(Undertow(serverPort))
  private val adb by lazy { Dadb.discover()!! }

  fun start() {
    require(Dadb.list().isEmpty()) { "No device or emulator should be running before the tests" }
    server.start()
  }

  override fun close() {
    server.stop()
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

      val screencap = adb.shell("screencap -p $tmpRemoteFile")
      require(screencap.exitCode == 0) { "Unable to screencap the screenshot, ${screencap.allOutput}" }

      adb.pull(outFile, tmpRemoteFile)

      Response(Status.OK)
    } catch (t: Throwable) {
      close()
      throw t
    }

}
