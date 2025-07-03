# Backpack Component Standards

Below are the standards and best practices for developing components in the Backpack Android library. These standards ensure that all components are accessible, well-designed, thoroughly tested, and properly documented. Wherever appropriate, these standards draw inspiration from established best practices such as Material 3 guidelines to ensure modern, accessible, and robust components.

## Accessibility Standards

Accessibility is a fundamental requirement for all Backpack components. Components must be usable by everyone.

### Requirements

1. **Screen Reader Support**
   - All components must work properly with TalkBack
   - Interactive elements must have appropriate content descriptions
   - State changes must be announced to screen readers
   - Custom components should implement appropriate accessibility semantics

2. **Keyboard Navigation**
   - All interactive components must be navigable and operable via keyboard
   - Focus states must be clearly visible
   - Tab order must be logical and follow the visual layout

3. **Touch Target Size**
   - Interactive elements must have a minimum touch target size of 48dp × 48dp
   - Sufficient spacing between interactive elements (at least 8dp)

4. **Color Contrast**
   - Text must have a contrast ratio of at least 4.5:1 against its background
   - UI controls and graphical elements must have a contrast ratio of at least 3:1
   - Components must adhere to Backpack's theming system, ensuring proper appearance in all supported themes (e.g., light and dark).

5. **Text Scaling**
   - Components must support text scaling up to 200%
   - Layouts must adapt appropriately to larger text sizes
   - No text should be cut off or overlap when font size is increased

6. **State Communication**
   - Error states must be communicated both visually and programmatically
   - Loading states must be properly indicated and announced
   - Selection states must be clearly visible and announced


## API Design Principles

A well-designed API is intuitive, consistent, and easy to use correctly. The following principles should guide API design for all Backpack components.

### Requirements

1. **Consistency**
   - Parameter naming should be consistent across components
   - Parameter ordering should follow a consistent pattern
   - Similar components should have similar APIs
   - Follow platform conventions where appropriate

2. **Simplicity**
   - APIs should be as simple as possible, but no simpler
   - Common use cases should be easy to implement
   - Default values should be sensible and cover the most common use cases
   - Complex functionality should be encapsulated behind simple interfaces

3. **Flexibility**
   - Components should be customizable for different use cases
   - Customization should be done through explicit parameters, not global configuration
   - Avoid overly restrictive APIs that limit legitimate use cases

4. **Type Safety**
   - Use enums or sealed classes for finite sets of options
   - Leverage the type system to prevent invalid states
   - Provide type-safe builders where appropriate

5. **Nullability**
   - Parameters should be non-nullable by default
   - Nullable parameters should be clearly documented
   - Provide default values for optional parameters when possible

6. **Compose-Specific Principles**
   - Follow the Compose API design guidelines
   - Use modifiers for layout and appearance customization.
   - Modifiers should be used to apply styles and behaviors to the container (parent component) of the components.
   - Modifier should never be used by children components.
   - Provide content lambdas for customizable content.
   - Use remember and derivedStateOf appropriately.
   - Ensure components are recomposition-friendly.

7. **Design Implementation**
   - Components should be implemented to be pixel-perfect according to Backpack design specifications.
   - Components must fully support Backpack's theming system, including light, dark, and any other defined themes. Ensure proper token usage for colors, typography, and spacing to allow for theme adaptability.
   - Components must be designed and implemented to fully support Right-to-Left (RTL) layouts, ensuring correct visual presentation and interaction.
   - Components should be designed to adapt gracefully to different screen sizes, orientations, and user accessibility settings (e.g., font scaling, display scaling).

## UI Tokens and Strings Principles
UI tokens and strings are essential for maintaining consistency and adaptability across components. They should be used to define visual styles, spacing, and text content.
### Requirements
1. **General Token Usage**
   - Use tokens for all design elements (colors, typography, spacing, etc.)
   - Avoid hardcoded values in components
   - Never modify auto generated tokens or create new ones.
   - Tokens are defined in `backpack-compose` module under tokens package for compose and in values resources in `backpack` module for views.
   - Never define new themes or modify existing ones in the `backpack-compose` or in `backpack` modules.
2. **String Resources**
   - Consumer should provide text related values via API parameters, not via string resources. e.g content description, label, placeholder text, etc.
   - Avoid hardcoded strings in components or in stories
   - Use descriptive keys for string resources
   - Stories can use string resources as they are for demonstration purposes.

## Testing Requirements

Thorough testing ensures that components work as expected and continue to work as the codebase evolves.

### Requirements

1. **Snapshot Tests**
   - Create snapshot tests for all visual components
   - Include snapshots for different states (default, disabled, error, etc.)
   - Include snapshots for different configurations (size, type, etc.)
   - Test in both light and dark themes
   - Test RTL layouts thoroughly.

2. **Semantic Tests**
   - Test accessibility features
   - Test performance for complex components

3. **Test Coverage**
   - Aim for high test coverage (at least 80%)
   - Focus on testing behaviour, not implementation details
   - Ensure all code paths are tested

## Documentation Standards

Good documentation is essential for component adoption and correct usage.

### Requirements

1. **README Files**
   - Each component should have a README file
   - README should include a description of the component
   - Include examples of common use cases
   - Document all public APIs
   - Include screenshots showing different states and configurations

2. **API Documentation**
   - All public APIs should have KDoc comments
   - Document parameters, return values, and exceptions
   - Include code examples for complex APIs
   - Document any limitations or performance considerations
   - User-facing text within components (e.g., default content descriptions, labels, placeholder text) should be clear, concise, and helpful.

3. **Demo App**
   - Include the component in the demo app using feature story.
   - Show different configurations and states
   - Provide interactive examples where appropriate

4. **Screenshots**
   - Include screenshots in both light and dark themes
   - Show different states and configurations
   - Update screenshots when the component changes visually

## Compose vs View Components
* Compose is our standard for new component development in Backpack Android.
* New components should be developed using Jetpack Compose and located in backpack-compose.
* View-based components should be maintained for backward compatibility, but new development should prioritize Compose implementations.
* Do not update View-based components unless absolutely necessary. ask users to migrate to Compose.

## Component Checklist

Run throw this checklist before submitting a new component or significant changes to an existing component:

- [ ] Component meets all accessibility requirements
- [ ] API follows the design principles
- [ ] Unit tests cover all functionality
- [ ] Snapshot tests cover all visual states
- [ ] Instrumentation tests for complex interactions
- [ ] README file is complete and up-to-date
- [ ] API documentation is complete
- [ ] Component is included in the demo app
- [ ] Screenshots are up-to-date


## Contribution Guidelines
Ensure to include /CONTRIBUTING.md for additional context on contributing to Backpack Android components.
