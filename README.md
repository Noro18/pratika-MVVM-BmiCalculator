# BMI Calculator - Android (Jetpack Compose)

A modern, clean, and intuitive Body Mass Index (BMI) calculator for Android. Built with **Kotlin** and **Jetpack Compose**, this application follows the **MVVM (Model-View-ViewModel)** architectural pattern to ensure a clean separation of concerns and a responsive user experience.

## ✨ Features

- **Instant Calculation:** Get your BMI results immediately by entering your weight (kg) and height (cm).
- **Dynamic Status Feedback:** Automatically categorizes your BMI into *Underweight*, *Normal*, *Overweight*, or *Obese*.
- **Visual BMI Scale:** A custom-drawn gradient scale with a marker that visually represents where your BMI falls within the standard ranges.
- **Modern UI/UX:**
    - Built with **Material 3** components.
    - Smooth enter/exit animations for result cards using `AnimatedVisibility`.
    - Support for **Dark and Light themes** with a professional color palette.
- **Smart Input:** Uses appropriate keyboard types and manages the software keyboard automatically.
- **One-Tap Reset:** Quickly clear all inputs and results to start over.

## 🛠 Tech Stack & Architecture

- **Language:** [Kotlin](https://kotlinlang.org/)
- **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose) (Material 3)
- **Architecture:** MVVM (Model-View-ViewModel)
- **State Management:** Compose State & Android ViewModel
- **Animation:** Compose Animation API
- **Theme:** Custom Material3 Theme with Dynamic Color support disabled for consistent branding.

## 📂 Project Structure

```text
com.example.bmicalculator/
├── model/           # Data models and BMI calculation logic
│   └── BmiData.kt   # BmiData class and utility functions (calculateBMI, getStatus)
├── viewmodel/       # UI Logic and State Management
│   └── BmiViewModel.kt
├── ui/              # Composable UI Components
│   ├── BmiScreen.kt # Main screen layout
│   ├── BmiResultCard.kt # Specialized components for results and scale
│   └── theme/       # Design system (Color, Type, Theme)
└── MainActivity.kt  # App entry point and Scaffold setup
```

## 🚀 Getting Started

1. **Clone the repository:**
   ```bash
   git clone https://github.com/yourusername/BMIcalculator.git
   ```
2. **Open in Android Studio:**
   - Use Android Studio Hedgehog or newer.
3. **Build and Run:**
   - Connect an Android device or start an emulator.
   - Click the **Run** button in Android Studio.

## 📸 Screenshots

| Light Mode | Dark Mode | Result Animation |
|:---:|:---:|:---:|
| *[Add Screenshot]* | *[Add Screenshot]* | *[Add Screenshot]* |

---
*Created with ❤️ using Jetpack Compose.*
