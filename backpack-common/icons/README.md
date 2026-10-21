# Icon artwork overrides

These SVGs override matching icons from `@skyscanner/bpk-svgs` during
`:backpack-common:generateIcons`. They contain the updated artwork supplied in
[BACKPACK-198](https://skyscanner.atlassian.net/browse/BACKPACK-198), using the
existing Android icon names. `sm` contains 16px artwork and `lg` contains 24px
artwork. Keep generated drawable XML in sync by running `generateTokens`.

The supplied large checked-included SVG was an exact copy of the 16px SVG.
Its override centers the original path on a 24×24 canvas with a `(4, 4)`
translation, preserving its stroke weight and matching the optical size of the
other large baggage icons. Multiplying the path coordinates by 1.5 would retain
the oversized appearance. The cabin and checked uncertain SVGs are identical
in the supplied artwork and are preserved as supplied.

Remove these overrides once the SVG package supplies the same updated artwork
and normalization; verify regeneration does not change the drawable output.
