package net.skyscanner.backpack.tokens

import org.gradle.api.Project
import java.io.File

interface NodeModule {

  fun resolve(path: String) : File

}

fun Project.nodeModuleOf(name: String): NodeModule {
  val nodeModules = project.rootDir.resolve("node_modules/")
  if (!nodeModules.exists()) error("Run npm install first")

  val module = nodeModules.resolve(name)
  if (!module.exists()) error("Module $name is not found!")

  return object: NodeModule {
    override fun resolve(path: String): File  =
      module.resolve(path)
  }
}
