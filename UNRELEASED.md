# Unreleased

> Place your changes below this line.

**Added:**
  - `BpkIcon`
    - New icon dimension – `sm`.
    - New icons:
        - `bpk_account__female`
        - `bpk_beach`
        - `bpk_car_wash`
        - `bpk_cars_flexible`
        - `bpk_clean`
        - `bpk_clean_policy`
        - `bpk_cleaning_medical`
        - `bpk_collapse`
        - `bpk_education`
        - `bpk_electric`
        - `bpk_exclamation`
        - `bpk_explore`
        - `bpk_face_id`
        - `bpk_face_mask`
        - `bpk_fingerprint`
        - `bpk_flight_flexible`
        - `bpk_logout`
        - `bpk_pause`
        - `bpk_play`
        - `bpk_ppe`
        - `bpk_social_distancing`
        - `bpk_sort_down`
        - `bpk_sort_up`
        - `bpk_swap`
        - `bpk_virus`
        - `bpk_weather__clear`
        - `bpk_weather__cloudy`
        - `bpk_weather__fog`
        - `bpk_weather__partly_cloudy`
        - `bpk_weather__rain`
        - `bpk_weather__snow`
        - `bpk_weather__thunderstorm`
        - `bpk_weather__tornado`
        - `bpk_weather__wind`

**Removed:**
  - `BpkIcon`
    - `bpk_close_small`

## How to write a good changelog entry

1. Add 'Breaking', 'Added' or 'Fixed' in bold depending on if the change will be major, minor or patch according to [semver](semver.org).
2. Add the package name.
3. Detail the changes. Write with the consumer in mind, what do they need to know. If it's patch, tell them what's changed. If it's minor, tell them what you've added and what it does for them. If it's breaking, tell them what they need to change. Link to examples on the [Backpack docs site](backpack.github.io) where possible.

Don't worry about adding the specific version number or the date. This will be done by a Backpack squad member as part of the release process.

## Example of a good changelog entry

See [`CHANGELOG.md`](CHANGELOG.md) for real-world examples of good changelog entries.

**Breaking:**

- `bpk-svgs`:
  - Replaced `charmeleon` icon with new `charizard` icon. To upgrade, replace your references to `charmeleon` with `charizard`.
  - Upgraded `fire` dependency to `3.0.0`.

**Added:**

- `bpk-component-infinity-gauntlet`:
  - New `timeStone` prop for controlling time. See &lt;link to docs site&gt;.

**Fixed:**

- `bpk-component-horcrux`:
  - Fixed issue where `BpkHorcrux` would occasionally possess the living.

