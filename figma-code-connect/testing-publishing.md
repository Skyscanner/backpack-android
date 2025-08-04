# Testing & Publishing Guide

## Overview

This guide covers the complete workflow for testing your Figma Code Connect integrations locally and publishing them to Figma for your design team to use.

## 🧪 Testing Workflow

### Step 1: Syntax Validation

First, ensure your Kotlin code compiles correctly:

```bash
./gradlew :backpack-compose:compileDebugKotlin
```

**Expected output**: `BUILD SUCCESSFUL` with no compilation errors

**Common issues**:
- Missing imports
- Incorrect property types
- Syntax errors in annotations

### Step 2: Parse Testing

Test if Figma CLI can parse your Code Connect files:

```bash
figma connect parse --verbose
```

**What to look for**:
- Your component appears in the output
- No parsing errors
- Generated templates look correct

### Step 3: Component-Specific Testing

Test a specific component file:

```bash
figma connect parse backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/button/BpkButtonCodeConnect.kt --verbose
```

**Expected output**:
```json
{
  "component": "BpkButton",
  "template": "BpkButton(\n    text = \"${text}\",\n    type = BpkButtonType.Primary\n)",
  "templateData": {
    "text": {
      "type": "string"
    }
  }
}
```

### Step 4: List All Components

See all detected Code Connect components:

```bash
figma connect list
```

**Expected output**:
```
Found 3 code connect files:
  - BpkButton (4 variants)
  - BpkText (18 variants)
  - BpkCard (1 variant)
```

## 🔍 Validation Commands

### Essential Testing Commands

```bash
# Quick syntax check
./gradlew :backpack-compose:compileDebugKotlin

# Full parse test with detailed output
figma connect parse --verbose

# Test specific component
figma connect parse path/to/ComponentCodeConnect.kt --verbose

# List all components
figma connect list

# Test publishing (without actually publishing)
figma connect publish --dry-run

# Check CLI status
figma connect status

# Validate configuration
figma connect --help
```

### Advanced Testing

```bash
# Parse with output to file for detailed inspection
figma connect parse --verbose > parse-results.json

# Test specific directory
figma connect parse backpack-compose/src/main/kotlin/net/skyscanner/backpack/compose/ --verbose

# Check for any parsing errors
figma connect parse 2>&1 | grep -i "error"
```

## ✅ Pre-Publish Checklist

Before publishing, verify:

### Code Quality
- [ ] All Code Connect files compile without errors
- [ ] No parsing errors from `figma connect parse`
- [ ] Component templates generate clean, readable code
- [ ] All variants are detected and listed correctly

### Property Validation
- [ ] Property names match Figma Dev Mode exactly
- [ ] Enum values are case-sensitive correct
- [ ] Default values are meaningful and realistic
- [ ] All component states/variants are covered

### Documentation
- [ ] Component has clear, descriptive class names
- [ ] Default property values are self-explanatory
- [ ] Generated code follows Backpack conventions

### Testing Results
- [ ] `figma connect parse --verbose` succeeds
- [ ] `figma connect list` shows all expected components
- [ ] `figma connect publish --dry-run` passes validation
- [ ] Generated templates produce valid Kotlin code
- [ ] No warnings or errors in parse output

## 📤 Publishing Process

### Step 1: Final Validation

Run a complete test before publishing:

```bash
# Clean build
./gradlew clean

# Compile everything
./gradlew :backpack-compose:compileDebugKotlin

# Parse all components
figma connect parse --verbose

# List components to verify count
figma connect list

# Test publishing without actually uploading
figma connect publish --dry-run
```

### Step 2: Dry Run Test (Recommended)

Before actual publishing, test the publish process:

```bash
figma connect publish --dry-run
```

**What happens**:
- Validates all Code Connect files
- Checks for publishing errors without actually uploading
- Shows what would be published
- Confirms authentication and permissions

**Expected output**:
```
✅ Dry run successful - 23 files ready to publish
   - BpkButton: 4 variants
   - BpkText: 18 variants  
   - BpkCard: 1 variant

Run 'figma connect publish' to upload to Figma
```

### Step 3: Publish to Figma

Once dry run succeeds, publish for real:

```bash
figma connect publish
```

**What happens**:
1. CLI uploads all Code Connect definitions to Figma
2. Design team can now see code snippets in Figma Dev Mode
3. Code snippets are automatically generated based on component usage

**Expected output**:
```
✅ Successfully published 23 code connect files
   - BpkButton: 4 variants
   - BpkText: 18 variants
   - BpkCard: 1 variant

🔗 View in Figma: https://www.figma.com/...
```

### Step 4: Verify in Figma

1. Open your Figma component in Dev Mode
2. Select a component instance
3. Look for the **Code** tab
4. Verify that:
   - Code snippets appear for your components
   - Code is accurate and follows your templates
   - Different component configurations generate different code

## 🔄 Update Workflow

When you modify existing Code Connect integrations:

### For Property Changes
```bash
# Test changes
figma connect parse --verbose

# Verify specific component
figma connect parse path/to/UpdatedCodeConnect.kt --verbose

# Test publishing
figma connect publish --dry-run

# Re-publish
figma connect publish
```

### For New Variants
```bash
# Add new variant class
# Test parsing
figma connect parse --verbose

# Verify new variant appears
figma connect list

# Test publishing
figma connect publish --dry-run

# Publish updates
figma connect publish
```

### For New Components
```bash
# Create new Code Connect file
# Test new component
figma connect parse path/to/NewCodeConnect.kt --verbose

# Test all components still work
figma connect parse --verbose

# Test publishing
figma connect publish --dry-run

# Publish all
figma connect publish
```

## 🚨 Troubleshooting

### Parse Errors

**Error**: `Property 'Style' not found in component`
```bash
# Solution: Check Figma Dev Mode for exact property name
# Verify case sensitivity
```

**Error**: `Invalid enum value 'primary'`
```bash
# Solution: Check Figma Dev Mode for exact enum values
# Usually a case sensitivity issue (should be 'Primary')
```

**Error**: `Parse failed: Complex property mapping`
```bash
# Solution: Simplify property mapping
# Use individual @FigmaVariant classes instead of complex logic
```

### Compilation Errors

**Error**: `Unresolved reference: Figma`
```kotlin
// Solution: Add missing import
import com.figma.code.connect.Figma
```

**Error**: `@Composable invocations can only happen from the context of a @Composable function`
```kotlin
// Solution: Ensure all @Composable calls are within @Composable functions
// Or use suppress annotation if intentional
@Suppress("ComposableNaming")
```

### Publishing Issues

**Error**: `Authentication failed`
```bash
# Solution: Check access token
figma connect status

# Re-configure token
export FIGMA_ACCESS_TOKEN="your_token_here"
```

**Error**: `No Code Connect files found`
```bash
# Solution: Check file naming convention
# Files must end with 'CodeConnect.kt'
# Verify figma.config.json include patterns
```

## 📊 Monitoring & Maintenance

### Regular Checks

```bash
# Weekly: Verify all components still parse
figma connect parse --verbose

# After dependency updates: Test compilation
./gradlew :backpack-compose:compileDebugKotlin

# After Figma component changes: Re-test and republish
figma connect publish
```

### Performance Monitoring

```bash
# Check parse time for large projects
time figma connect parse

# Monitor file count
figma connect list | wc -l
```

### Team Adoption

After publishing, check with design team:
- Are code snippets appearing in Figma Dev Mode?
- Is the generated code accurate and helpful?
- Do they need additional component variants covered?

## 🎯 Success Metrics

Your Code Connect integration is successful when:

### Technical Metrics
- ✅ All components compile without errors
- ✅ Parse command runs in <10 seconds
- ✅ All variants are detected and listed
- ✅ Generated code is valid Kotlin

### Design Team Metrics
- ✅ Code snippets appear in Figma Dev Mode
- ✅ Generated code matches actual component usage
- ✅ Design team reports improved developer handoff
- ✅ Reduced back-and-forth on component implementation

### Maintenance Metrics
- ✅ Easy to add new component variants
- ✅ Updates can be published quickly
- ✅ No regression in existing components

---

**Next Step**: See real implementation examples in [Examples](examples.md)
