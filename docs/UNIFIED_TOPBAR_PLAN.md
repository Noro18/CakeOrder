# Unified TopBar Architecture Plan

## Goal
Replace screen-specific TopBar components (`AppTopBar`, `AppTopBarDelete`, etc.) with a single, highly configurable `BakeryTopBar` to ensure visual consistency and a "Single Shell" architecture.

## Design
The `BakeryTopBar` will use slot-based parameters:
- `title: String`
- `navIcon: @Composable () -> Unit` (defaults to Menu icon)
- `actions: @Composable RowScope.() -> Unit` (defaults to Profile Picture)
- `colors: TopAppBarColors` (standardized to app theme)

## Implementation Strategy
1. **Unified Component**: Create `ui/components/BakeryTopBar.kt`.
2. **State Hoisting**: The `NavGraph` will determine the `title`, `navIcon`, and `actions` based on the current `Routes`.
3. **Single Shell**: The root `Scaffold` in `NavHost` will exclusively use `BakeryTopBar`.

## Benefits
- **Zero Double-Padding**: One Scaffold, one TopBar, consistent insets.
- **Visual Continuity**: Smooth transitions as the content changes but the "Shell" remains stable.
- **Maintainability**: Global changes to the header (elevation, color, font) are done in one place.
