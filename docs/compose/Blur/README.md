# Blur

[![Github Packages](https://img.shields.io/maven-central/v/net.skyscanner.backpack/backpack-compose)](https://github.com/orgs/Skyscanner/packages?repo_name=backpack-android)
[![Class reference](https://img.shields.io/badge/Class%20reference-Android-blue)](https://backpack.github.io/android/backpack-compose/net.skyscanner.backpack.compose.blur)
[![Source code](https://img.shields.io/badge/Source%20code-GitHub-lightgrey)](https://github.com/Skyscanner/backpack-android/tree/main/backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/blur)

Backpack Blur provides two different blur effects to apply to composables:

- **Uniform Blur**: Applies a consistent blur effect across the entire composable
- **Progressive Blur**: Applies a graduated blur effect that starts at the midpoint and intensifies toward the bottom of the
  composable

| Day                                                                                                                                                                        | Night                                                                                                                                                                                     |
|----------------------------------------------------------------------------------------------------------------------------------------------------------------------------|-------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Blur/screenshots/uniform.png" alt="Uniform blur modifier" width="375" />         | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Blur/screenshots/uniform_dm.png" alt="Uniform blur modifier - dark mode" width="375" />         |
| <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Blur/screenshots/progressive.png" alt="Progressive blur modifier" width="375" /> | <img src="https://raw.githubusercontent.com/Skyscanner/backpack-android/main/docs/compose/Blur/screenshots/progressive_dm.png" alt="Progressive blur modifier - dark mode" width="375" /> |

## Installation

Backpack Compose is available
through [Github Packages](https://github.com/orgs/Skyscanner/packages?repo_name=backpack-android). Check the
main [Readme](https://github.com/skyscanner/backpack-android#installation) for a complete installation guide.

## Usage

Example of a uniform blur applied to an image:

```Kotlin
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.tokens.bpkUniformBlur

Image(
    painter = painterResource(id = R.drawable.my_image),
    contentDescription = null,
    modifier = Modifier
        .fillMaxSize()
        .bpkUniformBlur()
)
```

Example of a progressive blur applied to an image:

```Kotlin
import androidx.compose.foundation.Image
import androidx.compose.ui.Modifier
import net.skyscanner.backpack.compose.tokens.bpkProgressiveBlur

Image(
    painter = painterResource(id = R.drawable.my_image),
    contentDescription = null,
    modifier = Modifier
        .fillMaxSize()
        .bpkProgressiveBlur()
)
```

## Progressive Blur Details

The progressive blur effect:

- Starts at the vertical midpoint of the composable
- Gradually increases in strength toward the bottom edge
- Uses advanced shaders on Android 13+ (API 33+) for optimal performance
- Falls back to uniform blur on older Android versions

This effect is particularly useful for creating visually appealing transitions or for enhancing the readability of text
overlaid on images.
