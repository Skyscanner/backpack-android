# Testing & Publishing

## Testing Commands

```bash
# Compile check
./gradlew :backpack-compose:compileDebugKotlin

# Parse test
npx @figma/code-connect parse --verbose

# List components
npx @figma/code-connect list

# Test publishing
npx @figma/code-connect publish --dry-run
```

## Publishing

### Dry Run (Recommended)
```bash
npx @figma/code-connect publish --dry-run
```

### Publish to Figma
```bash
npx @figma/code-connect publish
```

## Common Issues

| Error | Solution |
|-------|----------|
| "Property not found" | Check Figma Dev Mode for exact property name |
| "Invalid enum value" | Verify case-sensitive spelling |
| "Parse failed" | Simplify property mapping, use separate variant classes |
| "Compilation error" | Check imports and Kotlin syntax |
| "Authentication failed" | Verify `FIGMA_ACCESS_TOKEN` |

## Verification

After publishing:
1. Open Figma component in Dev Mode
2. Check Code tab for generated snippets
3. Verify code matches your templates
