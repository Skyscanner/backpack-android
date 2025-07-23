/**
 * Backpack for Android - Skyscanner's Design System
 *
 * Copyright 2025 Skyscanner Ltd
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

package net.skyscanner.backpack.compose.blur

import android.graphics.RenderEffect
import android.graphics.RuntimeShader
import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clipToBounds
import androidx.compose.ui.graphics.asComposeRenderEffect
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import org.intellij.lang.annotations.Language

fun Modifier.bpkUniformBlur() = this.blur(radius = 4.dp)

/**
 * Applies a progressive blur effect to the bottom half of the composable.
 *
 * The blur starts at the vertical midpoint and increases towards the bottom edge,
 * creating a visually appealing transition. This modifier uses a custom AGSL shader
 * for optimal performance and appearance on supported Android versions (API 33+).
 *
 * - The effect is clipped to the composable bounds.
 * - The blur strength and direction are handled via uniforms for two-pass chaining (X then Y).
 * - Falls back to a uniform blur on unsupported platforms.
 *
 * This modifier is designed to be accessible and themable, following Backpack's
 * component standards. It does not interfere with touch targets, semantics, or
 * theming, and is compatible with both light and dark modes.
 */
@Composable
fun Modifier.bpkProgressiveBlur(): Modifier =
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
        val shader = remember { RuntimeShader(BLUR_SHADER) }

        this
            .clipToBounds()
            .graphicsLayer {
                val imageHeight = size.height
                val maxSigma = 4.dp.toPx()

                shader.setFloatUniform("uMaxSigma", maxSigma)
                shader.setFloatUniform("uImageHeight", imageHeight)
                // X pass
                shader.setFloatUniform("uDirection", 0f)
                val blurEffectX = RenderEffect
                    .createRuntimeShaderEffect(shader, "uContent")

                // Y pass
                shader.setFloatUniform("uDirection", 1f)
                val blurEffectY = RenderEffect
                    .createRuntimeShaderEffect(shader, "uContent")

                // Chain Y after X
                val combinedEffect = RenderEffect.createChainEffect(blurEffectY, blurEffectX)

                this.renderEffect = combinedEffect.asComposeRenderEffect()
            }
    } else {
        bpkUniformBlur()
    }

@Language("AGSL")
val BLUR_SHADER = """
// Backpack progressive blur shader
uniform shader uContent;           // The input image/content to blur
uniform float uMaxSigma;           // Maximum blur sigma (strength)
uniform float uImageHeight;        // Height of the image/composable
uniform float uDirection;          // 0 = X direction, 1 = Y direction

const int MAX_RADIUS = 24;         // Maximum blur radius

half4 main(float2 fragCoord) {
    float2 pixel = fragCoord;

    // Compute normalized vertical position (0 at halfway down, 1 at bottom)
    float verticalProgress = clamp((pixel.y - 0.5 * uImageHeight) / (0.5 * uImageHeight), 0.0, 1.0);

    // Interpolate blurSigma: 0 at halfway, uMaxSigma at bottom
    float blurSigma = mix(0.0, uMaxSigma, verticalProgress);

    // If blurSigma is very small, just return the original pixel (no blur)
    if (blurSigma <= 0.01) return uContent.eval(pixel);

    // Compute blurRadius based on blurSigma, clamp to allowed range
    int blurRadius = int(clamp(ceil(3.0 * blurSigma), 1.0, float(MAX_RADIUS)));
    float twoSigmaSq = 2.0 * blurSigma * blurSigma;

    half4 accumulatedColor = half4(0.0); // Accumulated color
    float weightSum = 0.0;               // Normalization factor

    // Blur loop: sum weighted pixels along the chosen direction
    for (int d = -MAX_RADIUS; d <= MAX_RADIUS; ++d) {
        if (float(abs(float(d))) > float(blurRadius)) continue;

        // sampleOffset in X or Y depending on uDirection
        float2 sampleOffset = uDirection == 0.0 ? float2(d, 0.0) : float2(0.0, d);

        // Gaussian sampleWeight for this offset
        float sampleWeight = exp(-dot(sampleOffset, sampleOffset) / twoSigmaSq);

        // Accumulate weighted color
        accumulatedColor += sampleWeight * uContent.eval(pixel + sampleOffset);
        weightSum += sampleWeight;
    }

    // Normalize and return blurred color
    return accumulatedColor / weightSum;
}
""".trimIndent()
