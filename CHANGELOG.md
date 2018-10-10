# Backpack Android changelog

[Unreleased changes](./UNRELEASED.md).

# 0.4.0
- Bump kotlin version to 1.2.71
- New component BpkCardView

# 0.3.1
Fixes a bug to set button state from code 

# 0.3.0
Change button API to accept `icon` and `iconPosition`.

# 0.2.4
Adds backpack icons to resources

## 0.2.3

Fix artifacts publishing

## 0.2.3

Changes the dependency signature from `com.github.Skyscanner:backpack-android` to `com.github.skyscanner:backpack-android`.

Fixes a bug which prevented release artifacts from being uploaded.

## 0.2.1

Introducing BpkBadge and BpkPanel component.

## 0.2.0

Introducing BpkGradients. See usage in [README.md](README.md#gradient).

## 0.1.0

Adds Backpack text styles. See the full list in the [README.md](README.md#text-styles).

Usage:

```XML
<TextView
  style="@style/bpkTextSm" />
```

Adds Backpack radii tokens. See the full list in the [README.md](README.md#radii).

Usage:

```XML
<corners android:radius="@dimen/bpkBorderRadiusPill" />
```

Adds Backpack elevation tokens. See th full list in the [README.md](README.md#elevation).

Usage:

```XML
android:elevation="@dimen/bpkElevationXs"
```

## 0.0.3

Update project id to "net.skyscanner.backpack"

## 0.0.2

Initial release of Backpack for Android featuring color tokens. Usage

```XML
<style name="AppTheme.Backpack" color="@color/bpkBlue500" />
```

## 0.0.1

Dummy release to test, release automation. (not available for download)
