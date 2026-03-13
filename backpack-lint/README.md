# Backpack Lint Rules

This module contains custom lint rules for enforcing Backpack design system usage in Android projects.

## Lint Rules

### 1. HardcodedComposeColor
Detects hardcoded Compose `Color` values and requires using `BpkTheme.colors.*` tokens instead.

**Examples:**
- ❌ `Color(0xFF0062E3)`
- ❌ `Color.Blue`
- ✅ `BpkTheme.colors.corePrimary`

### 2. HardcodedSpacing
Detects hardcoded `.dp` spacing values and requires using `BpkSpacing.*` tokens instead.

**Examples:**
- ❌ `padding(16.dp)`
- ❌ `size(100.dp)`
- ✅ `padding(BpkSpacing.Base)`
- ✅ Variables are allowed: `IMAGE_HEIGHT.dp`

**Auto-fix:** For exact token matches (0, 4, 8, 16, 24, 32, 40, 64, 96), the IDE can automatically replace with the correct token.

### 3. TokensCopy
Detects `.copy()` usage on design tokens which bypasses the design system.

**Examples:**
- ❌ `BpkTheme.colors.coreAccent.copy(alpha = 0.5f)`
- ❌ `colors.textPrimary.copy(alpha = 0.5f)` (imported reference)
- ❌ `BpkTheme.typography.heading1.copy(fontWeight = FontWeight.Bold)`
- ✅ Request a new semantic token from the design team

## Token Generation

The token mappings used by lint detectors are **automatically generated** from the same source as Backpack tokens.

### How It Works

1. **Source:** `@skyscanner/bpk-foundations-android` npm package (`tokens/base.raw.android.json`)
2. **Generation:** `./gradlew :backpack-lint:generateTokens`
3. **Output:**
   - `GeneratedSpacingTokenMap.kt` - spacing token map
   - `GeneratedBorderRadiusTokenMap.kt` - border radius token map
   - `GeneratedColorTokenMap.kt` - color token map
   - `BpkDeprecatedTokens.kt` - deprecated token list

### Updating Token Mappings

When Backpack tokens change:

```bash
# In backpack-android root directory
npm install  # Update foundation tokens if needed
./gradlew :backpack-lint:generateTokens  # Regenerate all mappings
```

### Generation Setup

The generation is configured in:
- `backpack-lint/tokens.gradle.kts` - Gradle task definitions
- `buildSrc/.../BpkDimensionLintRules.kt` - Spacing and border radius transformation logic
- `buildSrc/.../BpkColorLintRules.kt` - Color transformation logic

## Support

Need help with these lint rules? Share your message in [#backpack Slack channel](https://skyscanner.slack.com/archives/C0JHPDSSU)
