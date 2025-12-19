/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 - 2025 Skyscanner Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

import com.android.build.gradle.LibraryExtension
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication

apply(plugin = "maven-publish")

configure<LibraryExtension> {
    publishing {
        singleVariant("release") {
            withSourcesJar()
        }
    }
}

afterEvaluate {
    configure<PublishingExtension> {
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
                artifactId = project.extra["artifactId"] as String
                version = project.version.toString()

                from(components["release"])

                pom {
                    name.set(project.extra["artifactId"] as String)
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

