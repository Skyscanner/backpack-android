# Figma Code Connect Integration for Backpack Android

## Overview

Figma Code Connect bridges the gap between design and development by automatically generating accurate code snippets from Figma designs. This guide walks you through integrating and using Figma Code Connect with Backpack Android components.

## 📚 Documentation Structure

This guide is split into focused documents for easier navigation:

1. **[Getting Started](./getting-started.md)** - Initial setup and installation
2. **[Component Integration](./component-integration.md)** - How to add Code Connect to components
3. **[Annotations Guide](./annotations-guide.md)** - Complete reference for all annotations
4. **[Testing & Publishing](./testing-publishing.md)** - Verification and deployment workflow
5. **[Examples](./examples.md)** - Real-world implementation examples
6. **[Quick Reference](./quick-reference.md)** - Essential commands and patterns

## 🚀 Quick Start

If you're already familiar with the setup, here's the essential workflow:

1. **Add annotations** to your component
2. **Test locally**: `figma connect parse --verbose`
3. **Test publishing**: `figma connect publish --dry-run`
4. **Publish**: `figma connect publish`

## 🎯 What You'll Learn

- ✅ How to set up Figma Code Connect in Backpack Android
- ✅ When and how to use different annotations (`@FigmaConnect`, `@FigmaVariant`, `@FigmaProperty`)
- ✅ Best practices for component integration
- ✅ Testing and publishing workflow
- ✅ Real examples from `BpkButton` and `BpkText`

## 🔧 Prerequisites

- Android Studio with Kotlin support
- Node.js (for Figma CLI)
- Access to Backpack Figma workspace
- Figma personal access token

## 📋 Current Integration Status

### ✅ Integrated Components
- **BpkButton** - 4 icon variants (None, Left, Right, Icon only)
- **BpkText** - 18 typography variants (Hero1-5, Heading1-5, etc.)
- **BpkCard** - Basic example

### 🔄 Integration Pattern
Each component variant uses individual `@FigmaVariant` classes for clean, parser-friendly code generation.

## 🆘 Need Help?

- **Quick questions**: Check the [Annotations Guide](annotations-guide.md)
- **Setup issues**: See [Getting Started](getting-started.md)
- **Integration patterns**: Review [Examples](examples.md)
- **Testing problems**: Visit [Testing & Publishing](testing-publishing.md)

---

**Next Step**: Start with [Getting Started](getting-started.md) to set up your development environment.
