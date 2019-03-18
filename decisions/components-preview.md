# Component Preview (AKA stories)

## Decision
- All previews should respond to a shortcut to hide the navigation bar (CMD+D).
- The component being previewed should fit in the screen in portrait mode *without* the navigation bar.
- The Android preview should be as close as possible as the iOS version (Always respecting any platform specific behaviour).
- Different configurations (e.g. normal text and emphasised text) should have different previews. In this case clicking the main story should show a new list view with all configs.

## Thinking
We need a consistent way of "visually documenting" components in order to add them to our docs site (alongside with React Native and Web). Furthermore, having consistency between
both the Android and iOS app will make it much easier for us to navigate between them.

Regarding the shortcut to hide the navigation bar, this is only so we hide all visual noise from the preview and create more space to show the actual component.