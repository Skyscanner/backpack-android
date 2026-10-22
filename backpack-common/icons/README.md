# Icon artwork overrides

These SVGs override matching icons from `@skyscanner/bpk-svgs` during
`:backpack-common:generateIcons`. They contain the updated artwork supplied in
[BACKPACK-198](https://skyscanner.atlassian.net/browse/BACKPACK-198), using the
existing Android icon names. `sm` contains 16px artwork and `lg` contains 24px
artwork. Keep generated drawable XML in sync by running `generateTokens`.

The supplied large artwork has inconsistent internal padding: five drawings
are approximately as tall as their small versions, and the large checked-included
file repeats the 16px source. Each large override uniformly scales the supplied
large path so its longest visible dimension is 1.5 times that of the matching
small drawing, then centers it on a 24×24 canvas. This preserves the large
artwork's proportions and gives every pair a visible size difference without
clipping. Keep these transforms when regenerating the drawables.

The small artwork is unchanged. The cabin and checked uncertain drawings are
identical in the supplied artwork and remain identical after normalization.

Remove these overrides once the SVG package supplies the same updated artwork
and normalization; verify regeneration does not change the drawable output.
