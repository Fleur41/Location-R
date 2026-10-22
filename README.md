# 📍 Location-R

[![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)](https://developer.android.com)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.2.10-7F52FF?style=flat-square&logo=kotlin&logoColor=white)](https://kotlinlang.org)
[![Jetpack Compose](https://img.shields.io/badge/Jetpack%2520Compose-Material%25203-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)](https://developer.android.com/jetpack/compose)
[![Dagger Hilt](https://img.shields.io/badge/Dagger-Hilt-2.60.1-232F3E?style=flat-square&logo=dagger&logoColor=white)](https://dagger.dev/hilt)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg?style=flat-square)](https://opensource.org/licenses/MIT)

**Location-R** is a modern Android application built to demonstrate precise location tracking, live GPS updates, reverse geocoding, and runtime permission handling using **Jetpack Compose**, **Material 3**, and **Google Play Services**.

---

## ✨ Features

- **🎯 Approximate & Precise Location**: Retrieve coarse location updates using Android's native `LocationManager` or high-accuracy GPS coordinates via `FusedLocationProviderClient`.
- **📡 Live Location Updates**: Real-time location streaming with automatic provider fallback (`GPS_PROVIDER` & `NETWORK_PROVIDER`).
- **🏠 Reverse Geocoding**: Automatically convert raw latitude and longitude coordinates into human-readable street addresses using Android's built-in `Geocoder`.
- **🛡️ Robust Runtime Permissions**: Seamlessly handle Android runtime permissions (`ACCESS_COARSE_LOCATION` & `ACCESS_FINE_LOCATION`) using Accompanist Permissions.
- **🎨 Modern Material 3 UI**: Clean, reactive UI built entirely with Jetpack Compose, Scaffold, AnimatedVisibility, and custom design components.
- **💉 Dependency Injection**: Powered by **Dagger Hilt** for scalable architecture and dependency management.

---

## 🛠️ Tech Stack & Libraries

- **UI Framework**: [Jetpack Compose](https://developer.android.com/jetpack/compose) & [Material 3](https://m3.material.io)
- **Dependency Injection**: [Dagger Hilt](https://dagger.dev/hilt)
- **Location Services**: [Google Play Services Location (`FusedLocationProviderClient`)](https://developers.google.com/android/reference/com/google/android/gms/location/FusedLocationProviderClient) & Android `LocationManager`
- **Permissions**: [Accompanist Permissions](https://github.com/google/accompanist)
- **Asynchronous & Lifecycle**: Kotlin Coroutines, Flow, & Lifecycle Runtime KTX
- **Background Tasks**: Android WorkManager with Hilt integration

---

## 📂 Project Architecture

```tree
app/src/main/java/com/sam/location_r/
├── MainActivity.kt                # Entry point with Hilt @AndroidEntryPoint & FusedLocationClient injection
├── MyApplication.kt               # Application class with @HiltAndroidApp
├── Components.kt                  # Reusable Compose UI components (e.g., VerticalSpacer)
├── LoggingUtility.kt              # Centralized Log.d debugging utilities
└── location/
    ├── LocationScreen.kt          # Main reactive UI screen with permission handlers & action buttons
    └── LocationUtility.kt         # Core utility for LocationManager, Live Updates, & Geocoder
```

---

## 🚀 Getting Started

### Prerequisites

- **Android Studio** (Koala or newer recommended)
- **JDK 11** or higher
- **Android SDK Platform 37** (or compatible emulator/device running API 31+)

### Cloning the Repository

Clone the repository from GitHub:

```bash
git clone https://github.com/Fleur41/Location-R.git
cd Location-R
```

### Building & Running

1. Open **Android Studio**.
2. Select **File > Open** and choose the cloned `Location-R` folder.
3. Allow Gradle to sync dependencies.
4. Connect an Android device or start an emulator (API 31+).
5. Click the **Run** ▶️ button in Android Studio to build and deploy the app.

---

## 💡 Usage Walkthrough

1. **Get Approximate Location**: Taps into Android's native `LocationManager` to retrieve coarse location data and resolves it to an address.
2. **Get Live Location**: Subscribes to real-time location changes with automatic provider fallback.
3. **Get Precise Live Location**: Leverages `FusedLocationProviderClient` for high-accuracy GPS coordinates (requires fine location permission).

---

## 📄 License

Distributed under the MIT License. See `LICENSE` for more information.
