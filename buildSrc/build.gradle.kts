repositories {
  mavenCentral()
  google()
}

plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(gradleApi())
  implementation("org.jetbrains.dokka:dokka-core:1.7.10") // this can be removed when updating to build tools 7.3
  implementation("com.android.tools.build:gradle:7.3.1")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
  implementation("com.squareup:kotlinpoet:1.12.0")
  implementation("com.google.guava:guava:31.1-jre")
  implementation("com.google.code.gson:gson:2.9.1")
}
