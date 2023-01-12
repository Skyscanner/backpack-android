repositories {
  mavenCentral()
  google()
}

plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(gradleApi())
  implementation("com.android.tools.build:gradle:7.3.1")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.20")
  implementation("com.squareup:kotlinpoet:1.12.0")
  implementation("com.google.guava:guava:31.1-jre")
  implementation("com.google.code.gson:gson:2.10.1")

  implementation(platform("org.http4k:http4k-bom:4.35.3.0"))
  implementation("org.http4k:http4k-core")
  implementation("org.http4k:http4k-server-undertow")
  implementation("org.http4k:http4k-client-apache")
  implementation("dev.mobile:dadb:1.2.6")
}
