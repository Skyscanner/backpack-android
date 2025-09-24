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

// Auto-generated: do not edit
@file:Suppress("RedundantVisibilityModifier", "unused")

package net.skyscanner.backpack.compose.tokens

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle

public data class BpkTypography internal constructor(
    public val baseLarken: TextStyle,
    public val bodyDefault: TextStyle,
    public val bodyLongform: TextStyle,
    public val caption: TextStyle,
    public val footnote: TextStyle,
    public val heading1: TextStyle,
    public val heading2: TextStyle,
    public val heading3: TextStyle,
    public val heading4: TextStyle,
    public val heading5: TextStyle,
    public val hero1: TextStyle,
    public val hero2: TextStyle,
    public val hero3: TextStyle,
    public val hero4: TextStyle,
    public val hero5: TextStyle,
    public val label1: TextStyle,
    public val label2: TextStyle,
    public val label3: TextStyle,
    public val smLarken: TextStyle,
    public val subheading: TextStyle,
    public val xsLarken: TextStyle,
) {
    internal constructor(defaultFontFamily: FontFamily = FontFamily.SansSerif) : this(
      baseLarken = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Base,
        lineHeight = BpkLineHeight.BaseTight,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      bodyDefault = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = BpkFontSize.Base,
        lineHeight = BpkLineHeight.Base,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      bodyLongform = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = BpkFontSize.Lg,
        lineHeight = BpkLineHeight.Lg,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      caption = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = BpkFontSize.Xs,
        lineHeight = BpkLineHeight.Xs,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      footnote = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = BpkFontSize.Sm,
        lineHeight = BpkLineHeight.Sm,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      heading1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Xxxl,
        lineHeight = BpkLineHeight.Xxxl,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      heading2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Xxl,
        lineHeight = BpkLineHeight.Xxl,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      heading3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Xl,
        lineHeight = BpkLineHeight.XlTight,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      heading4 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Lg,
        lineHeight = BpkLineHeight.LgTight,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      heading5 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Base,
        lineHeight = BpkLineHeight.BaseTight,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      hero1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.`8xl`,
        lineHeight = BpkLineHeight.`8xl`,
        letterSpacing = BpkLetterSpacing.Tight,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      hero2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.`7xl`,
        lineHeight = BpkLineHeight.`7xl`,
        letterSpacing = BpkLetterSpacing.Tight,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      hero3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.`6xl`,
        lineHeight = BpkLineHeight.`6xl`,
        letterSpacing = BpkLetterSpacing.Tight,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      hero4 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.`5xl`,
        lineHeight = BpkLineHeight.`5xl`,
        letterSpacing = BpkLetterSpacing.Tight,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      hero5 = TextStyle(
        fontWeight = FontWeight.Black,
        fontSize = BpkFontSize.Xxxxl,
        lineHeight = BpkLineHeight.Xxxxl,
        letterSpacing = BpkLetterSpacing.Tight,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      label1 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Base,
        lineHeight = BpkLineHeight.Base,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      label2 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Sm,
        lineHeight = BpkLineHeight.Sm,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      label3 = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Xs,
        lineHeight = BpkLineHeight.Xs,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      smLarken = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Sm,
        lineHeight = BpkLineHeight.Sm,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      subheading = TextStyle(
        fontWeight = FontWeight.Normal,
        fontSize = BpkFontSize.Xl,
        lineHeight = BpkLineHeight.Xl,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
      xsLarken = TextStyle(
        fontWeight = FontWeight.Bold,
        fontSize = BpkFontSize.Xs,
        lineHeight = BpkLineHeight.Xs,
        fontFamily = defaultFontFamily,
        lineHeightStyle = LineHeightStyle(
            alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
            trim = LineHeightStyle.Trim.None,
        ),
      ),
    )

    public companion object {
        public fun VDL2(defaultFontFamily: FontFamily = FontFamily.SansSerif): BpkTypography {
                                    /**
                                     * VDL2 typography with enhanced letter spacing and optimized styles.
                                     * Updated styles: Hero 4-5, Heading 1-5 with enhanced letter spacing and Black font weight
                                     */
                                    return BpkTypography(
                                        defaultFontFamily
                                    ).copy(
                                        hero4 = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = BpkFontSize.`5xl`,
                lineHeight = BpkLineHeight.`5xl`,
                letterSpacing = BpkLetterSpacing.VdlHero,
                fontFamily = defaultFontFamily,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
                        hero5 = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = BpkFontSize.Xxxxl,
                lineHeight = BpkLineHeight.Xxxxl,
                letterSpacing = BpkLetterSpacing.VdlHero,
                fontFamily = defaultFontFamily,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
                        heading1 = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = BpkFontSize.Xxxl,
                lineHeight = BpkLineHeight.Xxxl,
                letterSpacing = BpkLetterSpacing.VdlHeading1,
                fontFamily = defaultFontFamily,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
                        heading2 = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = BpkFontSize.Xxl,
                lineHeight = BpkLineHeight.Xxl,
                letterSpacing = BpkLetterSpacing.VdlHeading2,
                fontFamily = defaultFontFamily,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
                        heading3 = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = BpkFontSize.Xl,
                lineHeight = BpkLineHeight.XlTight,
                letterSpacing = BpkLetterSpacing.VdlHeading3,
                fontFamily = defaultFontFamily,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
                        heading4 = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = BpkFontSize.Lg,
                lineHeight = BpkLineHeight.LgTight,
                letterSpacing = BpkLetterSpacing.VdlHeading3,
                fontFamily = defaultFontFamily,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
                    trim = LineHeightStyle.Trim.None,
                ),
            ),
                        heading5 = TextStyle(
                fontWeight = FontWeight.Black,
                fontSize = BpkFontSize.Base,
                lineHeight = BpkLineHeight.BaseTight,
                letterSpacing = BpkLetterSpacing.VdlHeading3,
                fontFamily = defaultFontFamily,
                lineHeightStyle = LineHeightStyle(
                    alignment = LineHeightStyle.Alignment(topRatio = 0.2f),
                    trim = LineHeightStyle.Trim.None,
                ),
            )
                                    )
        }
    }
}
