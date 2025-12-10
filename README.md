<div align="center">

# NetUsers Pro — Explorador de Usuarios REST

[![Android](https://img.shields.io/badge/Android-13+-3DDC84?logo=android)](#)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.x-7F52FF?logo=kotlin)](#)
[![Compose](https://img.shields.io/badge/Jetpack_Compose-1.9.x-4285F4?logo=jetpack-compose)](#)
[![Retrofit](https://img.shields.io/badge/Retrofit-2.9-1D3557)](#)
[![License](https://img.shields.io/badge/License-MIT-lightgray)](#)

</div>

Aplicación Android en Jetpack Compose + MVVM que consume un endpoint REST (JSONPlaceholder) para listar usuarios con estados de carga, error, búsqueda y navegación a detalle.

## ✨ Funcionalidades
- Lista de usuarios con LazyColumn y tarjetas limpias.
- Búsqueda local por nombre o email.
- Estados de carga, error con retry y pull-to-refresh.
- Navegación list → detail.
- Tema Material 3.

## 🧱 Arquitectura
- **Presentación:** Compose (screens, components), `UserViewModel` (StateFlow).
- **Dominio:** modelo `User`.
- **Datos:** `RetrofitClient`, `ApiService`, `UserRepository` (Result).

Estructura:
```
app/src/main/java/dev/dongeeo/netuserspro/
  data/remote/ (ApiService, RetrofitClient)
  data/repository/ (UserRepository)
  domain/model/ (User)
  presentation/ (ViewModel, screens, components, navigation, theme)
```

## 🔗 API
- Base URL: `https://jsonplaceholder.typicode.com/`
- Endpoint: `GET /users`

## ▶️ Ejecución rápida
Requisitos: Android Studio Giraffe+ / AGP 8.1+, Java 11, dispositivo/emulador con Android 13+ recomendado.

```bash
./gradlew assembleDebug
adb install -r app/build/outputs/apk/debug/app-debug.apk
```

## 📦 Dependencias clave
- Compose BOM 2024.12.01, Material3, Navigation Compose.
- Lifecycle ViewModel / LiveData (StateFlow en ViewModel).
- Retrofit + Gson.
- Accompanist SwipeRefresh.

## ⚙️ Permisos
- `INTERNET`

## 🧪 QA mínimo
- `./gradlew lint`
- `./gradlew assembleDebug`

## 🛣️ Roadmap corto
- Migrar SwipeRefresh a `pullRefresh` (Compose Foundation).
- Añadir cache local (Room).
- Tests de repositorio y ViewModel con coroutines.

## 🧑‍💻 Autor
Giorgio Interdonato — NetUsers Pro (Bootcamp Android)

