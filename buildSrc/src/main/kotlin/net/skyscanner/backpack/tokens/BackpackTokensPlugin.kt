package net.skyscanner.backpack.tokens

import org.gradle.api.Plugin
import org.gradle.api.Project

class BackpackTokensPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.task("hello") {
      doLast {
        println("Hello from the BackpackTokensPlugin")
      }
    }
  }
}

