# 📜 Changelog

## v1.0.2 — 2025-12-10
### ✅ Added
- 🧪 Tests de unidad para autenticación: `AuthRepository`, `LoginViewModel` y validador de credenciales (Mockito + coroutines).
- 📱 Tests de instrumentación Compose para flujo de login: éxito, credenciales inválidas y validaciones en UI.
- 🛡️ Pantalla de Login con validaciones, snackbar de éxito/error y flujo simple de autenticación.
### 🧰 Changed
- 🔢 Versión de app a 1.0.2 (versionCode 3).
### ✅ QA
- 🧪 `./gradlew test`
- 📱 `./gradlew connectedAndroidTest`

## v1.0.1 — 2025-12-10
### ✅ Added
- 🧪 Tests de unidad para `UserRepository` y `UserViewModel` (coroutines + flujos).
- 📱 Tests de instrumentación Compose para la lista: carga, búsqueda y estado de error.
### 🧰 Changed
- 🔢 Versión de app a 1.0.1 (versionCode 2) con dependencias de coroutines.
### ✅ QA
- 🧪 `./gradlew test`
- 📱 `./gradlew connectedAndroidTest`

## v0.1.0 — 2025-12-10
### ✨ Added
- 🚀 MVP Android con Jetpack Compose + MVVM.
- 🌐 Fetch REST `GET /users` (JSONPlaceholder) con Retrofit + Gson.
- 📋 Listado con tarjetas, búsqueda local (nombre/email), estados de carga/error y pull-to-refresh.
- 👤 Pantalla de detalle de usuario.
- 🧭 Navegación Compose (list → detail).
- 🎨 Theming Material 3 y componentes de estado (loading/error).
- 📖 README con setup, dependencias y roadmap corto.

### 🐛 Fixed
- 🛡️ Crash inicial por falta de permiso `INTERNET` en el manifest.

### ✅ QA
- 🧹 `./gradlew lint`
- 🏗️ `./gradlew assembleDebug`

