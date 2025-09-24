# Figma Code Connect

Generates code snippets from Figma designs for Backpack Android components.

## Quick Start

1. Get [Figma access token](https://www.figma.com/settings)
2. `export FIGMA_ACCESS_TOKEN="your_token"`
3. Create `Component.figma.kt` with annotations
4. Test: `npx @figma/code-connect parse --verbose`
5. Publish: `npx @figma/code-connect publish`

## Documentation

- **[Getting Started](./getting-started.md)** - Setup and installation
- **[Annotations Guide](./annotations-guide.md)** - Annotation reference
- **[Component Integration](./component-integration.md)** - Adding to components
- **[Examples](./examples.md)** - Real implementations
- **[Testing & Publishing](./testing-publishing.md)** - Verification workflow
- **[CI Integration](./ci-integration.md)** - CI/CD setup
- **[Quick Reference](./quick-reference.md)** - Commands and patterns

## Current Components

- **BpkButton** - 4 icon variants
- **BpkText** - 18 typography variants
- **BpkCard** - Basic example
