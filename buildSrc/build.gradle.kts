repositories {
  mavenCentral()
  google()
}

plugins {
  `kotlin-dsl`
}

dependencies {
  implementation("com.android.tools.build:gradle:7.0.2")
  implementation(kotlin("gradle-plugin"))
  implementation(gradleApi())
  implementation("com.squareup:kotlinpoet:1.10.1")
  implementation("com.google.guava:guava:31.0.1-jre")
}
