# Getting Started

## Prerequisites

- Android Studio with Kotlin support
- Node.js (for Figma CLI)
- Access to Backpack Figma workspace
- Figma personal access token

## Setup

### 1. Get Figma Access Token

1. Go to [Figma Account Settings](https://www.figma.com/settings)
2. Create a new **Personal Access Token**
3. Copy the token

### 2. Configure Token

Add to your shell profile (`.zshrc`, `.bashrc`):
```bash
export FIGMA_ACCESS_TOKEN="your_token_here"
```

### 3. Test Installation

```bash
# Test validation (should work with existing .figma.kt files)
npx figma connect publish --dry-run
```

## Project Structure

Code Connect files follow this pattern:
```
backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/
├── button/
│   ├── BpkButton.kt
│   └── BpkButton.figma.kt
├── text/
│   ├── BpkText.kt
│   └── BpkText.figma.kt
└── component/
    ├── BpkComponent.kt
    └── BpkComponent.figma.kt
```

## Configuration

The project uses `figma.config.json`:
```json
{
  "codeConnect": {
    "parser": "compose",
    "include": [
      "backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/**/*.figma.kt"
    ]
  }
}
```

## Next Steps

1. **[Annotations Guide](annotations-guide.md)** - Learn the annotations
2. **[Component Integration](component-integration.md)** - Add Code Connect to components
3. **[CI Integration](ci-integration.md)** - Automated testing and publishing