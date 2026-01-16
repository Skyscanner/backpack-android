import com.android.build.gradle.LibraryExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.create
import org.gradle.kotlin.dsl.get

plugins {
    `maven-publish`
}

// Extension to receive artifactId from modules
interface BackpackPublishingExtension {
    var artifactId: String
}

val publishingExt = extensions.create<BackpackPublishingExtension>("backpackPublishing")

android {
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

afterEvaluate {
    publishing {
        repositories {
            maven {
                name = "GitHubPackages"
                url = uri("https://maven.pkg.github.com/skyscanner/backpack-android")
                credentials {
                    username = (rootProject.extra["githubUsername"] as String).ifEmpty {
                        System.getenv("GITHUB_ACTOR")
                    }
                    password = (rootProject.extra["githubToken"] as String).ifEmpty {
                        System.getenv("GITHUB_TOKEN")
                    }
                }
            }
        }

        publications {
            create<MavenPublication>("maven") {
                groupId = rootProject.extra["group"] as String
                artifactId = publishingExt.artifactId
                version = project.version.toString()

                from(components["release"])

                pom {
                    name.set(publishingExt.artifactId)
                    description.set("Backpack is a collection of design resources, reusable components and guidelines for creating Skyscanner's products.")
                    url.set("https://github.com/Skyscanner/backpack-android")

                    licenses {
                        license {
                            name.set("The Apache License, Version 2.0")
                            url.set("https://github.com/Skyscanner/backpack-android/blob/main/LICENSE.txt")
                        }
                    }
                    developers {
                        developer {
                            id.set("skyscanner")
                            name.set("Skyscanner Open Source")
                            email.set("koalasquad@skyscanner.net")
                        }
                    }
                    scm {
                        connection.set("scm:git:git://github.com/Skyscanner/backpack-android.git")
                        developerConnection.set("scm:git:ssh://github.com/Skyscanner/backpack-android.git")
                        url.set("http://github.com/Skyscanner/backpack-android/tree/main")
                    }
                }
            }
        }
    }
}

fun Project.android(configure: LibraryExtension.() -> Unit) {
    extensions.configure<LibraryExtension>(configure)
}

fun Project.publishing(configure: PublishingExtension.() -> Unit) {
    extensions.configure<PublishingExtension>(configure)
}
