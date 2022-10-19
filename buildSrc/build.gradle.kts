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
  implementation("com.google.code.gson:gson:2.9.1")
}
