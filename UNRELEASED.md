# Unreleased

**Breaking:**

- BpkChip now does not add a click listener by default anymore, and now is up to users to add it.
  - click listener was removed to avoid initialization problems if the chip is subclassed and the
    ònClickeListener` method is overridden.
  - added `toggle` function to toggle the chip's state

- class BpkDismissableChip was removed as is not supported at the moment.