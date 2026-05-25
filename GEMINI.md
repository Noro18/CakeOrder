# Project Instructions (GEMINI.md)

## UI Architecture
- **Single Shell Architecture**: The application uses a single root `Scaffold` in the `NavGraph`. Avoid nesting `Scaffold` components within individual screens to prevent double padding and layout issues.
- **TopBar Management**: We are transitioning to a unified TopBar system. Refer to [docs/UNIFIED_TOPBAR_PLAN.md](docs/UNIFIED_TOPBAR_PLAN.md) for the architecture plan when updating or refactoring TopBar logic.
