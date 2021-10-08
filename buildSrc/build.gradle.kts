repositories {
  mavenCentral()
  google()
}

plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(gradleApi())
  implementation("com.android.tools.build:gradle:7.0.2")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.30")
  implementation("com.squareup:kotlinpoet:1.10.1")
  implementation("com.google.guava:guava:31.0.1-jre")
  implementation("com.google.code.gson:gson:2.8.8")
}
