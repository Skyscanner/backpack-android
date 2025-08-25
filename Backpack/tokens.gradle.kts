/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2018 Skyscanner Ltd
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

// Token generation has been migrated to backpack-common module.
// This file is kept for backwards compatibility but all token generation
// is now handled by the backpack-common module's tokens.gradle.kts file.

tasks {
    val group = "tokens"

    val generateTokens by creating {
        this.group = group
        // Depend on backpack-common token generation
        dependsOn(":backpack-common:generateTokens")
    }
}
