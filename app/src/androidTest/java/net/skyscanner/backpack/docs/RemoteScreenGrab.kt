package net.skyscanner.backpack.docs

import java.io.BufferedInputStream
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.HttpURLConnection
import java.net.URL

class RemoteScreenGrab(
  private val serverIp: String
) {
  fun takeScreenshot(name: String) {
    val url = URL("http://$serverIp:8888?name=$name")
    val con = url.openConnection() as HttpURLConnection
    con.requestMethod = "POST"
    try {
      val inStream = BufferedInputStream(con.inputStream)
      // Output should to be read
      BufferedReader(InputStreamReader(inStream)).readText()
      if (con.responseCode != 200) {
        throw Exception("Unable to take screenshot for $name. Error code: ${con.responseCode}")
      }
    } finally {
      con.disconnect()
    }
  }
}
