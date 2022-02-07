repositories {
  mavenCentral()
  google()
}

plugins {
  `kotlin-dsl`
}

dependencies {
  implementation(gradleApi())
  implementation("com.android.tools.build:gradle:7.1.1")
  implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.5.31")
  implementation("com.squareup:kotlinpoet:1.10.2")
  implementation("com.google.guava:guava:31.0.1-jre")
  implementation("com.google.code.gson:gson:2.8.9")
}
