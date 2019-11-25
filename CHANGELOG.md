# Backpack Android changelog

[Unreleased changes](./UNRELEASED.md).

# 19.0.0
  
- **Breaking:**
  - `BpkPageTitle` was removed.
  
- **Added:**
  - `BpkNavBar`
    * Menu and nav icon support.
  - `BpkCalendar`
    * Disabled dates customisation

# 18.0.1

**Fixed:**

- Updated `bpk_cars`, `bpk_flights` and `bpk_hotels` icons.

# 18.0.0

- **Breaking:**
  - `Checkbox` text color cannot be set now via `android:textColor` attribute.
  - `Switch` text color cannot be set now via `android:textColor` attribute.
  
- **Added:**
  - Dark Mode support for `Checkbox`
  - Dark Mode support for `Switch`
  - Dark Mode support for `StarRating`
  - Dark Mode support for `InteractiveStarRating`
  - Dark Mode support for `HorizontalNav`
  - Disabled state support for `Checkbox`
  - Semantic color support for `bpkPrimaryColor`
  
# 17.0.0

**Breaking:**
  - The library reacts to the Dark Mode now

- **Added:**
  - New semantic colours and the palette for the Dark Mode support
  - Dark Mode support for icons
  - Dark Mode support for `Badge`
  - Dark Mode support for `Button`
  - Dark Mode support for `ButtonLink`
  - Dark Mode support for `Card`
  - Dark Mode support for `Calendar`
  - Dark Mode support for `Chip`
  - Dark Mode support for `Dialog`
  - Dark Mode support for `Flare`
  - Dark Mode support for `PageTitle`
  - Dark Mode support for `Panel`
  - Dark Mode support for `Rating`
  - Dark Mode support for `Snackbar`
  - Dark Mode support for `Text`
  - Dark Mode support for `TextField`
  - Dark Mode support for `Toast`
  

# 16.0.1

**Fixed:**

- BpkSpinner
  - Fixed inline `style` support.

# 16.0.0

**Added:**

- BpkButton:
  - Added `loading` (`app:buttonLoading` in XML) property and loading style.
  
- BpkIcon:
  - Added file and printer icons

**Breaking:**

- Removed old brand colours.

# 15.0.0

**Breaking:**

- Rename `bpkSegano` colour to `bpkSagano`.

# 14.0.1

**Fixed:**

- BpkButton
  - Fixed default border radius.

# 14.0.0

**Added:**

- `compileSdk` and `targetSdk` updated to API 29
- NEW BRAND COLOURS 🎉

**Breaking:**

- Updated all components to use new colours.
- Removed unused `Bpk.chip` style.

# 13.4.0

**Added:**
- `BpkCalendar`:
  - `calendarDateSelectedSameDayBackgroundColor` attribute

- `BpkIcon`:
  - Added `bpk_single_booking`
  
- `BpkChip`:
  - `chipBackgroundColor` attribute
  
**Fixed:**
- `BpkCalendar`:
  - Height calculation of range selection
  - Increased `rowheight`
  - Default font weight is `emphasized`
  - Removed today marking circle
  - Changed the day's colour appearance

- `BpkChip`:
  - Default font weight is `emphasized`
  - Default font color is `bpkTextPrimaryLight`
  - Default background color is `bpkGray50`
  - Removed elevation

# 13.3.0

**Added:**

- `BpkBadge`
  - Added theme support
  - Added `android:includeFontPadding` theme property
  
# 13.2.1

**Fixed:**

- Switch
  - Updated selected track colour to match platform standards.

# 13.2.0

**Fixed:**

- `BpkTextEdit`
  - Changed text size from SM to BASE.

- `BpkPageTitle`
  - Fixed wrong title alignment on RTL.
  - Fixed wrong scrolling behaviour of `CoordinatorLayout` in some cases.

**Added:**

- New colours added:
  - `bpkTextPrimaryLight`
  - `bpkTextSecondaryLight`

# 13.1.0

**Added:**

- `BpkPageTitle`

# 13.0.0

**Fixed:**

- Updated gray colours `gray50`, `gray100`, `gray200`, `gray300`, `gray400`, `gray500`, `gray700`, `gray900`
  - Deprecated `gray600` and `gray800` please update as these will be removed in the future.
  - For these tokens please use either one token above or below your current value to suit your needs.

- Updated icon borders to utilise updated gray color

- `BpkText` now uses `bpkGray900` as default text colour.

**Breaking:**

- Removed themeable grey colours.
  - `bpkGray50Color`
  - `bpkGray100Color`
  - `bpkGray300Color`
  - `bpkGray500Color`
  - `bpkGray700Color`
  - `bpkGray900Color`

- Removed function `BpkTheme.getColor`

- Changed API of `BpkSnackbar` to be consistent with material `Snackbar`.

# 12.3.0

**Added:**

- `BpkButton`
  - Stroke width now increases when pressed for Secondary, Destructive and Outlined buttons
  - Added `buttonStrokeColourPressed` theme property.

# 12.2.0

**Added**:
  - `BpkSnackbar`
  - `BpkDialog` now expands to full screen when it takes more than 75% of available height.

**Fixed**:
  - `BpkDialog` content is now scrollable.
  
# 12.0.0

**Breaking**:

- `BpkIcon`:
  - Renamed `bpk_swap` to `bpk_swap__horizontal`:

**Added**:

- Added `BpkFlare` component
- `BpkIcon`:
  - Added `bpk_swap__vertical`

# 11.0.3

**Fixed:**

- Bumped `androidx.appcompat:appcompat` to `1.0.2`

# 11.0.2

**Fixed**:

- Icons:
  - Added `autoMirrored` props to the following icons:
    - `arrow-left`
    - `arrow-right`
    - `long-arrow-left`
    - `long-arrow-right`
    - `chevron-left`
    - `chevron-right`
    - `fast-track`
    - `list`
    - `media`
    - `native-android--back`
    - `native-android--forward`
    - `policy`
    - `share`
    - `star-half`
    - `swap`
    - `trend`
    - `trend--down`
    - `trend--steady`
    - `trend--will-rise`

# 11.0.1

**Fixed**:

- `BpkInteractiveStarRating` resets its value when receiving cancel touch event.

# 11.0.0

**Breaking**:

- `BpkText`:
  - Removed deprecated `emphasized` prop. You should use `weight="emphasized"` now.
  - Introduced theme variables for all text styles. Those can be used to theme native components with Backpack's text style via code or `xml`.
  - Previous text styles (e.g `@style/bpkTextBase`) should not be directly used any more.
  - Text colour is now `gray700` by default.

**Added**:

- `BpkRating` component.

# 10.0.1

**Fixed**:

- `BpkButton`, `BpkButtonLink`
  - fixed icons alignment when text is too long.
- `BpkTextField`
  - open for extension
  
# 10.0.0

**Added**:

- `BpkTextField`

**Breaking:**

- `BpkFab`:
  - Changed base class to `FloatingActionButton`

# 9.11.0

**Added:**

- New `flight-landing`, `flight-takeoff` and `aircraft` icons.

**Fixed**:

- `BpkInteractiveStarRating`
  - Listener is being invoked when rating set programmatically or when user finishes the interaction.

- `ButtonLink`
  - setting `buttonTextColor` to white now works.

# 9.10.1

**Fixed**:

- `BpkButton`
  - Fixed icon only buttons to be always round.

# 9.10.0

**Added**:

- `BpkHorizontalNav`

# 9.9.0

**Fixed**:

- `BpkInteractiveStarRating`
  - Fixed multiple listener invocations with the same values.

**Added:**

- `BpkDialog`:
  - `addActionButton` now accepts any view as argument.

# 9.8.0

**Added**:

- Added `BpkCheckbox` and `BpkButtonLink` components.
- Added `BpkFontSpan` and `BpkPrimaryColorSpan` font spans.

# 9.7.0

**Added**:

- `BpkToast`

# 9.6.0

**Added:**

- BpkButton
  - Primary and Featured buttons now have elevation by default
  - `buttonAddElevation` attribute has no effect any more.

- Added new component `BpkInteractiveStarRating`

- Icons:
  - New `paid`, `star-outline` icons
  - Updated `meal`, `bar`, `star`, `star-half` `media` icons

# 9.5.3

**Fixed**:

- `BpkButton`:
  - Elevation is now disabled for API 21 as it causes native crashes in some edge cases.

# 9.5.2

**Fixed**:

- `BpkButton`:
  - Fixed a native crash on Galaxy S4 in some places


# 9.5.1

**Fixed**:

- `BpkCalendar`:
  - Fixed rendering of passed weeks before current week in month.
  - Fixed invalid days of the week on some rare circumstances.

# 9.5.0

**Added:**
- `BpkFab` 

# 9.4.1

**Fixed**:

- `BpkSpinner`:
  - Disable spinner animation when `Global.ANIMATOR_DURATION_SCALE` is 0. This ensures it doesn't
    cause timeouts in Espresso tests.

# 9.4.0

**Added:**

- Added new smiley icons face--blank, face--happy, face--sad.

**Updated:**

- Updated world and heart icons.

# 9.3.1

**Updated:**

- Updated icons `bpk_heart__outline`

# 9.3.0

**Added:**

- Added new icons `bpk_filter`, `bpk_heart__outline`, `bpk_trend__down`, `bpk_trend__steady`, `bpk_trend__will_rise`, `bpk_world__amer`, `bpk_world__apac`, `bpk_world__emea`
- `BpkText`:
  - Added method `applyTo` to `BpkFontDefinition` and `JvmStatic` annotation to `getFont`.

**Updated:**

- Updated icons `bpk_family`, `bpk_heart`

# 9.2.0

**Added:**
- `BpkStarRating`

# 9.1.0

**Added:**

- Added the following new tokens/dimensions:
  - `bpkTextBaseSize`
  - `bpkTextCapsSize`
  - `bpkTextLgSize`
  - `bpkTextSmSize`
  - `bpkTextXlSize`
  - `bpkTextXsSize`
  - `bpkTextXxlSize`
  - `bpkTextXxxlSize`

# 9.0.2

**Fixed:**
- `BpkPanel`
  - Added missing rounding corners

# 9.0.1

**Changed:**

- `BpkSwitch`
  - Changed theming approach to not rely on constructor's default arguments.

**Fixed:**

- `BpkButton`:
  - Fixed theming for stroke color.

# 9.0.0

**Added:**

- Added the following themeable props:
  - `bpkGray50Color`
  - `bpkGray100Color`
  - `bpkGray300Color`
  - `bpkGray500Color`
  - `bpkGray700Color`
  - `bpkGray900Color`

**Breaking:**

- Renamed `ThemesUtil` to `BpkTheme`

# 8.0.0

**Breaking:**

- Removed `ThemeOverlayEnforcement` class and `bpkThemeOverlay` attributes.

**Added:**

- `BpkGradients`:
  - Added `bpkPrimaryGradientColorStart` and `bpkPrimaryGradientColorEnd` theme props for the `getPrimary` function.

# 7.5.0

**Added:**

- BpkButton
  - Added `buttonCornerRadius` and `buttonAddElevation` theme props

# 7.4.1

**Fixed:**

- BpkCalendar
  - Changed changed background color to transparent.
  - Removed synthetic imports.

# 7.4.0

**Fixed**:

- BpkButton
  - Fixed icon alignment and size.
  - Adjusted style to match current RN implementation.

**Added:**

- Added `BpkViewPumpContextWrapper` to wrap `ViewPump` functionality and disabled it for Android Q.

# 7.3.1

**Changed:**

- Redesigned icons `bpk_alert__active`, `bpk_alert__add`, `bpk_alert__expired`, `bpk_alert__remove`, `bpk_price_alerts`.

# 7.3.0

**Fixed:**

- Fixed text theming for buttons, calendar and badge
- Fixed `BpkCalendar` to use the correct font definitions

**Added:**

- Added function `BpkText.getFont` to retrieve the current font definition programmatically

# 7.2.0

**Added:**

- BpkChip
  - Added theming support to BpkChip

**Fixed:**

- BpkChip
  - Fixed text theming for BpkChip

# 7.1.0

**Added:**

- Replaced `org.threeten:threetenbp` with `com.jakewharton.threetenabp:threetenabp` for date handling
- BpkText
  - Added theming support

# 7.0.0

**Breaking:**

- BpkCalendar
  - Replaced `java.util.Date` and `java.util.Calendar` with `JSR-310` date constructs (using `org.threeten.bp.*`)
  - Added theming support
  - Fixed `updateSelection` method for single selection

# 6.3.0

**Fixed:**

- BpkCalendar now works with `height=wrap_content`

**Added:**

- BpkSpinner
  - Added theming support

# 6.2.0

**Added:**

- BpkCalendar
  - Added ability to set the selected colour when using a `ColorBucket`
  - The week header now respects the locale to pick the first day of the week

- Added `bpkThemeOverlay` to the list of attributes and `ThemeOverlayEnforcement` to allow controlling themes during runtime.

# 6.1.0

**Added:**

- Added animation tokens.
- Removed all logic related to the RN bridge from BpkCalendar

# 6.0.1

**Fixed:**

- Fix calendar year pill to show when the start date is in the past/future and to work in the RN bridge.

# 6.0.0

**Breaking:**

- Added theme support to `BpkButton`.

# 5.0.0

**Breaking:**

- Added single selection to `BpkCalendar`.

# 4.0.0

**Breaking:**

- BpkChip now does not add a click listener by default any more, and now is up to users to add it.
  - click listener was removed to avoid initialization problems if the chip is subclassed and the
    `onClickListener` chip's state

- class `BpkDismissableChip` was removed as it's not supported at the moment.

# 3.2.0

**Added:**

- Added function `updateContent` to `BpkCalendarController`.

# 3.1.0

**Added:**

- Added `bpkPrimaryColor` theme property and `ThemeUtils.getPrimaryColor` function.
  - These should be used in place of `bpkBlue500`.

# 3.0.0

**Breaking:**

- Added support for color markers in BpkCalendar

# 2.8.0

**Added:**

- New icons `hide` and `eco-leaf`.

# 2.7.1

- BpkCalendar
  - Selecting a new date after the end date has been selected now resets the range selection.

# 2.7.0

**Added:**

- New icons `grid-layout` and `social-like`.

- `BpkSwitch`
  - New XML property `switchPrimaryColor` added.
  - `BpkSwitch` styles can now be changed globally by setting the `bpkSwitchStyle` property in your current theme/style.

# 2.6.0

**Added:**

- New `bpk_social_like` icon.
- New `dark` variant of `BpkBadge`
- Added year indicator badge on `BpkCalendar`
- Support for distribution via HockeyApp

# 2.5.0

**Added:**

- New `bpk_speaker` icon.

- BpkCalendar
  - Added ability to update the selected dates programmatically using `updateSelection` in `BpkCalendarController`.
  - BpkCalendar can now be extended.
  - Internal changes to allow usage from React Native.

# 2.4.0

**Added:**

- Support for highlighting backpack components


# 2.3.1

**Fixed:**

- Darken text and icon on press along with the background on `BpkButton`
- Support `drawableTint` on Android API < v23

# 2.3.0
**Added:**

- Added new component `BPKCalendar`

# 2.2.0
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
  XXL: from `36sp` to `30sp`
  XXXL: from `42sp` to `36sp`

| Font size  | Old value  | New value  |
|---|---|---|
| XXL  | `36pt` | `30pt`  |
| XXXL  | `42pt`  | `36pt`  |

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

- Introducing `BpkDialog` component. See usage in [README.md](docs/Dialog/README.md).

# 0.9.0

**Added:**

- Added property `weight` to `BpkText` and support for new `heavy` weight.
  - `emphasize` is now deprecated. Use `weight` instead.

# 0.8.0

- Type ramp changes for letter-spacing
- Fix RTL bug for `BPKButton`

# 0.7.0

- Introducing the `BpkSwitch` component. See usage in [README.md](docs/Switch/README.md).

# 0.6.0

Added new icons:

- `bpk_call_back`
- `bpk_end_call`
- `bpk_keypad`
- `bpk_mute`
- `bpk_phone_call`
- `bpk_send_message`
- `bpk_unmute`

# 0.5.0

Introducing BpkSpinner component. See usage in [README.md](docs/Spinner/README.md).

Update build tools to 28.0.3.

# 0.4.0

Bump Kotlin version to 1.2.71

New component `BpkCardView`

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

Introducing `BpkBadge` and BpkPanel component.

## 0.2.0

Introducing `BpkGradients`. See usage in [README.md](README.md#gradient).

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

Adds Backpack elevation tokens. See the full list in the [README.md](README.md#elevation).

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
