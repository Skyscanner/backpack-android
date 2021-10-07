package net.skyscanner.backpack.tokens

import org.gradle.api.Plugin
import org.gradle.api.Project

class BackpackTokensPlugin : Plugin<Project> {
  override fun apply(project: Project) {
    project.task("hello") {
      doLast {

        project.nodeFileOf("@skyscanner/bpk-foundations-android", "tokens/base.raw.android.json")
          .readAs(BpkFormat.Json)
          .parseAs(BpkDimension.Category.Elevation)
          .transformTo(BpkDimension.Format.Compose(namespace = "BpkElevation"))
          .saveTo(BpkOutput.KotlinFile(srcDir = "/Users/vitaliibabichev/Projects/backpack-android/backpack-compose/src/main/kotlin", "net.skyscanner.backpack.compose"))
          .execute()
      }
    }
  }
}

