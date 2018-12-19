# Backpack Android changelog

[Unreleased changes](./UNRELEASED.md).

# v2.2.0
- Drop support for Android API level 19 and 20
- Fixes a bug in `BpkButton` where button size was different when icon was present
- Updates API for `BPKGradient`
- Updates to Kotlin 1.3.10

# 2.1.0

**Added**

* Added new `outline` type to `BpkButton`, which has white text and border on a transparent background. Designed to be used on coloured backgrounds.

# 2.0.0

**Breaking:**

- Text style changes:
  XXL: from 36sp to 30sp
  XXXL: from 42sp to 36sp

| Font size  | Old value  | New value  |
|---|---|---|
| XXL  | 36pt | 30pt  |
| XXXL  | 42pt  | 36pt  |

# 1.3.1

**Fixed:**

- Revert elevation tokens changes released in 1.3.0.

# 1.3.0

**Added:**

- Added new property `cornerStyle` to `BpkCardView`. See usage in [README.md](docs/Card/README.md).

# 1.2.0

**Added:**

- Introducing BpkChip component. See usage in [README.md](docs/Chip/README.md).

# 1.1.0

**Added:**

* Added tokens for border sizes.
* Added `bpkElevationXxl` token.

**Changed:**

* Changed elevation token values. See https://backpack.github.io/tokens/shadows.

# 1.0.1

**Fixed:**

- Updated `bpk_send_message` icon.

# 1.0.0

**Added:**

- Introducing BpkDialog component. See usage in [README.md](docs/Dialog/README.md).

# 0.9.0

**Added:**

- Added property `weight` to `BpkText` and support for new `heavy` weight.
  - `emphasize` is now deprecated. Use `weight` instead.

# 0.8.0

- Type ramp changes for letter-spacing
- Fix RTL bug for `BPKButton`

# 0.7.0

- Indroducing the BpkSwitch component. See usage in [README.md](docs/Switch/README.md).

# 0.6.0

Added new icons:

- bpk_call_back
- bpk_end_call
- bpk_keypad
- bpk_mute
- bpk_phone_call
- bpk_send_message
- bpk_unmute

# 0.5.0

Introducing BpkSpinner component. See usage in [README.md](docs/Spinner/README.md).

Update build tools to 28.0.3.

# 0.4.0

Bump kotlin version to 1.2.71

New component BpkCardView

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
