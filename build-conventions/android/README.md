# android

---
A set of android-related plugins for `com.android.library` | `com.android.application` configuration.

### `convention.android-application.gradle.kts`

Apply this plugin, if you want to configure an Android application.
This plugin will configure build types, build number and all application-related things for you.

Example usage:

```kotlin
plugins {
    id("convention.android-application")
}

dependencies {
    
}
```

### `convention.android-library.gradle.kts`

Apply this plugin, if you want to configure an Android library.
This plugin will configure build types, build number and all application-related things for you.

Example usage:

```kotlin
plugins {
    id("convention.android-library")
}

dependencies {
    
}
```