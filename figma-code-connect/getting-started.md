# Getting Started with Figma Code Connect

## Prerequisites

Before you begin, ensure you have:

- ✅ **Android Studio** with Kotlin support
- ✅ **Node.js** installed (for Figma CLI)
- ✅ Access to **Backpack Figma workspace**
- ✅ **Figma personal access token**

## 1. Installation & Setup

### Step 1: Install Figma CLI

```bash
npm install -g @figma/code-connect
```

Verify installation:
```bash
figma connect --version
# Should show: 1.3.4 or later
```

### Step 2: Get Your Figma Access Token

1. Go to [Figma Account Settings](https://www.figma.com/settings)
2. Scroll to **Personal Access Tokens**
3. Click **Create new token**
4. Name it "Code Connect - Backpack Android"
5. Copy the generated token

### Step 3: Configure Access Token

**Environment Variable**
```bash
export FIGMA_ACCESS_TOKEN="your_token_here"
```

Add to your shell profile (`.zshrc`, `.bashrc`, etc.):
```bash
echo 'export FIGMA_ACCESS_TOKEN="your_token_here"' >> ~/.zshrc
source ~/.zshrc
```

## 2. Verify Setup

### Test 1: Check Token Access
```bash
figma connect status
```
✅ Should show: "Connected to Figma"

### Test 2: Parse Existing Components
```bash
figma connect parse --verbose
```
✅ Should find and parse existing `BpkButton` and `BpkText` Code Connect files

## 3. Development Workflow

### Quick Test Command
```bash
# Parse and verify your Code Connect annotations
figma connect parse --verbose

# Test a specific file
figma connect parse backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/button/BpkButtonCodeConnect.kt --verbose
```

### Essential Commands
```bash
# Check what's detected
figma connect list

# Test publishing (without actually publishing)
figma connect publish --dry-run

# Publish to Figma (when ready)
figma connect publish

# Help
figma connect --help
```

## 4. Project Structure

Your Code Connect files should follow this pattern:
```
backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/
├── button/
│   ├── BpkButton.kt                    # Main component
│   └── BpkButtonCodeConnect.kt         # Code Connect integration
├── text/
│   ├── BpkText.kt                      # Main component
│   └── BpkTextCodeConnect.kt           # Code Connect integration
└── [component]/
    ├── Bpk[Component].kt               # Main component
    └── Bpk[Component]CodeConnect.kt    # Code Connect integration
```

## 🚨 Important Notes

### File Naming Convention
- Code Connect files must end with `CodeConnect.kt`
- Place them alongside the main component file
- Use descriptive class names: `BpkTextCodeConnect`, `BpkButtonCodeConnect`

### Parser Requirements
- Keep Code Connect classes simple
- Avoid complex `@Composable` property mappings
- Use individual `@FigmaVariant` classes for multiple variants

## ✅ Verification Checklist

Before proceeding to component integration:

- [ ] Figma CLI installed and working
- [ ] Access token configured and tested
- [ ] Project builds without errors
- [ ] `figma connect parse` runs successfully
- [ ] Can see existing components in parse output

## 🔧 Troubleshooting

### "Command not found: figma"
```bash
npm install -g @figma/code-connect
```

### "Invalid access token"
1. Regenerate token in Figma settings
2. Update environment variable or config file
3. Test with `figma connect status`


### "No Code Connect files found"
- Verify files end with `CodeConnect.kt`
- Check `figma.config.json` include patterns
- Ensure annotations are correctly applied

---

**Next Step**: Ready to integrate components? Go to [Component Integration](component-integration.md)
