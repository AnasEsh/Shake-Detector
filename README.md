# 📳 Shake Detector

A lightweight and easy-to-use Android library for detecting device shake gestures using the accelerometer sensor.

## ✨ Features

- 🎯 **Simple Integration** - Easy to set up with just a few lines of code
- ⚙️ **Configurable Sensitivity** - Choose from preset sensitivity levels or create custom thresholds
- 🔄 **Lifecycle Aware** - Designed to work seamlessly with Android lifecycle
- 📱 **Lightweight** - Minimal footprint with no external dependencies
- 📚 **Well Documented** - Comprehensive KDoc documentation included

## 🚀 Installation

Add the dependency to your app's `build.gradle.kts` file:

```kotlin
dependencies {
    implementation("com.github.AnasEsh:shake_detector:<version>")
}
```

### Option 1: Using JitPack Repository (Recommended)

Add JitPack to your project-level `build.gradle.kts` or `settings.gradle.kts`:

```kotlin
repositories {
    maven { url = uri("https://jitpack.io") }
    // ... other repositories
}
```

### Option 2: Using Local Build

Make sure you have `mavenLocal()` in your repositories if using a local build:

```kotlin
repositories {
    mavenLocal()
    // ... other repositories
}
```

## 📖 Usage

### Basic Usage

```kotlin
class MainActivity : AppCompatActivity() {
    
    private lateinit var shakeDetector: ShakeDetector
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        // Initialize shake detector
        shakeDetector = ShakeDetector(this, object : ShakeListener {
            override fun onShaked() {
                // Handle shake event
                Toast.makeText(this@MainActivity, "Device shaken!", Toast.LENGTH_SHORT).show()
            }
        })
    }
    
    override fun onStart() {
        super.onStart()
        shakeDetector.start() // Start listening for shakes
    }
    
    override fun onStop() {
        super.onStop()
        shakeDetector.stop() // Stop listening to save battery
    }
}
```

### Lambda Syntax (Kotlin)

```kotlin
// Using lambda for cleaner code
val shakeDetector = ShakeDetector(this) { 
    Log.d("Shake", "Device was shaken!")
    // Handle shake event here
}
```

### Custom Sensitivity

```kotlin
// High sensitivity (less force needed)
val shakeDetector = ShakeDetector(
    context = this,
    shakeListener =  /* ... handle shake */ ,
    sensitivity = ShakeSensitivity.High
)

// Low sensitivity (more force needed)
val shakeDetector = ShakeDetector(
    context = this,
    shakeListener =  /* ... handle shake */ ,
    sensitivity = ShakeSensitivity.Low
)

// Custom threshold
val shakeDetector = ShakeDetector(
    context = this,
    shakeListener =  /* ... handle shake */ ,
    sensitivity = ShakeSensitivity.Custom(threshold = 800)
)

// Change sensitivity at runtime
shakeDetector.changeSensitivity(ShakeSensitivity.High)
```

## 🎛️ Sensitivity Levels

| Level | Description | Use Case |
|-------|-------------|----------|
| `High` | Very sensitive, triggers easily | Games, interactive apps |
| `Normal` | Balanced sensitivity (default) | General purpose apps |
| `Low` | Less sensitive, requires more force | Prevents accidental triggers |
| `Custom(threshold)` | User-defined threshold | Fine-tuned control |

## 🔧 API Reference

### ShakeDetector

```kotlin
class ShakeDetector(
    context: Context,
    shakeListener: ShakeListener,
    sensitivity: ShakeSensitivity = ShakeSensitivity.Normal
)
```

#### Methods

- `start()` - Start listening for shake events
- `stop()` - Stop listening for shake events  
- `changeSensitivity(sensitivity: ShakeSensitivity)` - Update sensitivity at runtime

#### Exceptions

- `UnsupportedOperationException` - Thrown when accelerometer is not available

### ShakeListener

```kotlin
interface ShakeListener {
    fun onShaked()
}
```

### ShakeSensitivity

```kotlin
sealed class ShakeSensitivity {
    object High : ShakeSensitivity()
    object Normal : ShakeSensitivity()
    object Low : ShakeSensitivity()
    data class Custom(val threshold: Int = 650) : ShakeSensitivity()
}
```

## 📋 Requirements

- **Android API Level**: 24+ (Android 7.0)
- **Permissions**: None required
- **Hardware**: Device with accelerometer sensor

## 🏗️ Architecture

The library uses Android's `SensorManager` to monitor accelerometer data and applies a sophisticated algorithm to detect shake patterns:

1. **Data Collection**: Monitors X, Y, Z acceleration values
2. **Pattern Recognition**: Analyzes movement patterns over time
3. **Threshold Filtering**: Applies configurable sensitivity thresholds
4. **Event Triggering**: Requires multiple consecutive shakes within a time window

## 🔄 Lifecycle Integration

**Recommended lifecycle integration:**

```kotlin
class YourActivity : AppCompatActivity() {
    
    override fun onStart() {
        super.onStart()
        shakeDetector.start() // Start when visible
    }
    
    override fun onStop() {
        super.onStop()
        shakeDetector.stop() // Stop to save battery
    }
}
```

## ⚠️ Important Notes

- Always call `stop()` in your activity's `onStop()` or `onPause()` to prevent battery drain
- The library automatically handles sensor availability checks
- Shake detection requires at least 3 consecutive shakes within 1 second
- Custom thresholds: lower values = higher sensitivity

## 🐛 Troubleshooting

**Shake not detected:**
- Ensure you called `start()` method
- Try increasing sensitivity with `ShakeSensitivity.High`
- Check if device has a working accelerometer

**Too sensitive:**
- Use `ShakeSensitivity.Low` or custom threshold
- Increase the threshold value in `Custom(threshold)`

**Battery concerns:**
- Always call `stop()` when not needed
- Consider using lifecycle-aware components

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

## 📧 Support

For questions or support, please open an issue on GitHub.

---

Made with ❤️ by [AnasEsh](https://github.com/AnasEsh)
