# CLAUDE.md - BijouK Project Guide

## Project Overview

BijouK is a **Kotlin Multiplatform (KMP)** music player remote control application for Android and iOS. It connects to the [Cider](https://cider.sh/) music player backend via HTTP RPC and WebSocket APIs to provide real-time playback control, library browsing, and queue management.

- **Package:** `com.snowdango.bijouk`
- **Min SDK:** 30 (Android 11) | **Target SDK:** 36 (Android 15)
- **Kotlin:** 2.3.0 | **AGP:** 8.13.2 | **Java:** 17
- **Version:** 0.0.2

## Quick Reference - Build Commands

```bash
# Build debug APK
./gradlew assembleDebug

# Run detekt with auto-correct
./gradlew detekt --auto-correct

# Run all checks (build + lint)
./gradlew assembleDebug detekt --auto-correct

# Generate API client from OpenAPI spec
./gradlew openApiGenerate

# Deploy debug build to DeployGate
./gradlew uploadDeployGateDebug
```

**Note:** A `local.properties` file is required for builds with signing. It must contain `debug.keypass`, `debug.storepass`, `deploygate.user`, and `deploygate.token`.

## Architecture

The project follows **Clean Architecture** with a **MVVM** pattern and **unidirectional data flow** using Kotlin StateFlow.

```
Presentation Layer (Compose UI)
  app / presenter / features/*
         ↓
State Management Layer (ViewModels + Models)
  model
         ↓
Data Layer (Repositories)
  repository
         ↓
Domain Layer (Database, Preferences, HTTP setup)
  domain
         ↓
External Sources (Generated API client, Room DB, WebSocket)
  api
```

### Module Dependency Graph

```
app
├── presenter
│   ├── features:device, features:setting, features:search,
│   │   features:playlist, features:album, features:artist, features:queue
│   ├── ui
│   └── model
├── repository → api, domain
├── domain (Room DB, DataStore, Ktor HTTP client)
├── model → domain, repository, api, infla, analytics
├── ui (shared Compose components, theme)
├── infla (SharedEventStore, platform utilities)
└── analytics (logging, Firebase)
```

## Module Descriptions

| Module | Type | Purpose |
|--------|------|---------|
| `app` | Android Application | Entry point, Koin initialization, Activities |
| `presenter` | Android Library | Screen orchestration, navigation, main ViewModel |
| `features/*` | Android Libraries | Self-contained feature screens (device, search, queue, album, artist, playlist, setting) |
| `model` | KMP Library | Business logic, state containers, data mappers, paging sources |
| `repository` | KMP Library | Data access layer, API/DB bridging |
| `domain` | KMP Library | Room database, DataStore preferences, HTTP/WebSocket client setup |
| `api` | KMP Library | Auto-generated OpenAPI client from `cider.json` |
| `ui` | Android Library | Shared Compose components, theme (`BijouKTheme`), responsive layout utilities |
| `infla` | KMP Library | `SharedEventStore` (cross-module event bus), platform utilities |
| `analytics` | KMP Library | Platform-specific logging (KMP Xlog), Firebase Analytics |

## Tech Stack

- **UI:** Jetpack Compose with Material 3
- **Navigation:** Compose Navigation with type-safe serializable routes
- **Networking:** Ktor Client 3.x (HTTP + WebSocket), KMP Socket.IO
- **Database:** Room 2.8.x with Bundled SQLite
- **Preferences:** AndroidX DataStore
- **DI:** Koin 4.x (multiplatform)
- **Async:** Kotlin Coroutines + StateFlow/SharedFlow
- **Image Loading:** Coil 3 (Compose)
- **Serialization:** kotlinx.serialization
- **Paging:** AndroidX Paging 3
- **Analytics:** Firebase Analytics + Crashlytics
- **Code Quality:** Detekt (with Compose rules), ktlint, Android Lint

## Code Conventions

### ViewModel Pattern

ViewModels inherit `ViewModel()` and implement `KoinComponent`. Dependencies are injected via `by inject()` or constructor parameters.

```kotlin
class FeatureViewModel(
    private val baseUrl: String,
    private val token: String,
) : ViewModel(), KoinComponent {
    private val someModel: SomeModel by inject { parametersOf(baseUrl, token) }

    // Private MutableStateFlow with underscore prefix
    private val _uiState: MutableStateFlow<UiState> = MutableStateFlow(UiState.Loading)
    // Public StateFlow exposed with .stateIn()
    val uiState: StateFlow<UiState> = _uiState.stateIn(
        viewModelScope,
        SharingStarted.WhileSubscribed(5_000),
        UiState.Loading,
    )

    sealed class UiState {
        data object Loading : UiState()
        data class Success(val data: SomeData) : UiState()
        data object Error : UiState()
    }
}
```

- Expose state via `StateFlow` with `SharingStarted.WhileSubscribed(5_000)`
- Use sealed classes for UI state (`Loading`, `Success`, `Error`)
- Use `MutableSharedFlow` for one-off events (toasts, action results)
- Action methods use imperative verbs: `load()`, `refresh()`, `play()`, `delete()`

### Error Handling

```kotlin
viewModelScope.launch(Dispatchers.IO) {
    try {
        // operation
    } catch (ce: CancellationException) {
        throw ce  // Always rethrow CancellationException
    } catch (th: Throwable) {
        Log.e("Tag", th.toString())
    }
}
```

Always rethrow `CancellationException`. Log errors with `Log.e()`. For UI-facing errors, emit an `Error` UI state or a toast message.

### Compose Conventions

```kotlin
@Composable
fun FeatureScreen(
    modifier: Modifier = Modifier,           // Always first optional param
    onNavigateBack: () -> Unit,              // Callbacks before ViewModel
    viewModel: FeatureViewModel = koinViewModel(), // ViewModel last with default
) {
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    // ...
}
```

- `modifier: Modifier = Modifier` always present as optional parameter
- Collect state with `collectAsStateWithLifecycle()`
- Use `Scaffold` for screen layouts
- Wrap content with `BijouKTheme` in `@Preview` functions
- Private preview functions: `@Preview @Composable private fun PreviewContent()`
- Responsive layouts use `ScreenType.SINGLE` vs `ScreenType.DUAL`

### Navigation

Routes are defined as `@Serializable` objects/data classes inside sealed classes:

```kotlin
sealed class SecondRoute {
    @Serializable data object QUEUE : SecondRoute()
    @Serializable data class ALBUM(val albumId: String, val isLibrary: Boolean) : SecondRoute()
}
```

Navigation uses `NavHost` with `composable<Route>` and `navController.navigate(route)` with state preservation (`saveState = true`, `restoreState = true`).

### Dependency Injection (Koin)

Each module exports a Koin module:

```kotlin
// Simple ViewModels
val featureModule = module {
    viewModelOf(::FeatureViewModel)
}

// Parameterized ViewModels
val featureModule = module {
    viewModel { param -> FeatureViewModel(param.get(), param.get()) }
}

// Repositories use factory (new instance each time)
val repositoryModule = module {
    factory { param -> SomeRepository(get { parametersOf(param.get()) }) }
}

// Singletons for app-wide services
val infraModule = module {
    single { SharedEventStore(get()) }
}
```

### Cross-Module Events

Use `SharedEventStore` for cross-module communication:

```kotlin
// Emit
sharedEventStore.setEvent(SharedEvent.QueueUpdated)

// Collect
sharedEventStore.events.collect { event ->
    when (event) {
        is SharedEvent.QueueUpdated -> refresh()
        // ...
        else -> {}
    }
}
```

Events are defined as sealed classes: `QueueUpdated`, `QueueDelayUpdated`, `ShuffleModeUpdated`, `DeviceListUpdated`, `ChangeNowPlayingSong`.

### Feature Module Structure

Every feature module follows this pattern:

```
features/{name}/
├── build.gradle.kts
├── src/main/java/com/snowdango/bijouk/features/{name}/
│   ├── {Name}Module.kt          # Koin DI module
│   ├── {name}/
│   │   ├── {Name}ViewModel.kt   # State management
│   │   └── {Name}Screen.kt      # Main composable
│   └── component/                # Shared sub-components
```

Feature modules depend on `:ui`, `:model`, and `:infla`.

## API Integration

The app communicates with Cider via:

- **HTTP RPC:** OpenAPI-generated client from `cider.json` (base URL: `http://{host}:8080/api/v1`)
- **WebSocket:** Real-time events (`NowPlayingItemDidChange`, `PlayBackStateDidChange`, `PlayBackTimeDidChange`, `ShuffleModeDidChange`)

Key endpoints: `/playback/now-playing`, `/playback/playpause`, `/playback/next`, `/playback/previous`, `/playback/seek`, `/playback/queue`, `/amapi/run-v3` (Apple Music API bridge).

## Code Quality

### Detekt

- Config: `config/detekt/detekt.yml`
- Auto-correct enabled
- Max line length: 120 characters
- Cyclomatic complexity threshold: 15
- Max nested block depth: 4
- Large class threshold: 600 lines
- Compose-specific rules enabled (30+ rules)

### Ktlint

- Version 14.0.1 via Gradle plugin
- Enforces official Kotlin code style (`kotlin.code.style=official`)

### Android Lint

- Abort on error enabled
- Check dependencies enabled
- Text report output

## CI/CD

| Workflow | Trigger | Action |
|----------|---------|--------|
| `build-test.yml` | Pull request | `assembleDebug` + `detekt --auto-correct` |
| `deploygate-debug.yml` | Push to `develop` | Upload debug APK to DeployGate |
| `deploygate-release.yml` | Manual dispatch | Build release APK/AAB, create GitHub Release |

**Dependabot** is configured for weekly Gradle dependency updates targeting the `develop` branch.

## Branching

- **`develop`** - Main development branch; debug builds auto-deploy on push
- Feature branches merge into `develop` via PRs
- Releases are triggered manually from `deploygate-release.yml`

## Key Files

| File | Purpose |
|------|---------|
| `app/src/main/java/.../BijouKApplication.kt` | App entry point, Koin initialization |
| `presenter/src/main/java/.../second/SecondViewModel.kt` | Main player ViewModel (playback state, socket) |
| `model/src/commonMain/.../cider/CiderRPCModel.kt` | RPC command execution |
| `model/src/commonMain/.../cider/CiderSocketModel.kt` | WebSocket connection management |
| `model/src/commonMain/.../cider/CiderBridgeModel.kt` | High-level playback operations |
| `infla/src/commonMain/.../SharedEventStore.kt` | Cross-module event bus |
| `ui/src/main/java/.../BijouKTheme.kt` | Compose theme definition |
| `cider.json` | OpenAPI specification for the Cider API |
| `gradle/libs.versions.toml` | Centralized dependency versions |
| `config/detekt/detekt.yml` | Detekt static analysis rules |

## Guidelines for AI Assistants

1. **Read before editing.** Always read files before modifying them. Understand existing patterns.
2. **Follow existing conventions.** Match the ViewModel, Compose, DI, and navigation patterns described above.
3. **KMP awareness.** Modules `domain`, `repository`, `model`, `api`, `infla`, `analytics` are Kotlin Multiplatform. Use `commonMain` for shared code, `androidMain`/`iosMain` for platform-specific implementations.
4. **Feature modules are self-contained.** New features should be created as new modules under `features/` with their own Koin module, ViewModel, and Screen composable.
5. **Use the version catalog.** All dependency versions live in `gradle/libs.versions.toml`. Never hardcode versions in `build.gradle.kts` files.
6. **Run detekt.** After code changes, run `./gradlew detekt --auto-correct` to catch style issues. Respect the 120-character line limit.
7. **Rethrow CancellationException.** In all try-catch blocks around coroutine code, always rethrow `CancellationException`.
8. **State management.** Use `MutableStateFlow`/`StateFlow` pairs with `SharingStarted.WhileSubscribed(5_000)`. Use sealed classes for UI state.
9. **No hardcoded strings in UI.** Use string resources (`R.string.*`) for user-facing text.
10. **API changes.** If modifying API integration, update `cider.json` and regenerate with `./gradlew openApiGenerate`.
