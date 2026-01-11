# 🌟 MoodLens – Android

MoodLens is a **modern Android application** built using **Kotlin** and **Jetpack Compose**, focused on helping users **track their daily mood and habits** through a clean, minimal, and Gen-Z–friendly user interface.

This project is developed as part of a **mobile application ideation and development task**, with emphasis on **UI design, architecture understanding, and clean project structure**.

---

## 🚀 Project Objective

The goal of MoodLens is to demonstrate:
- Strong understanding of **Android app structure**
- Usage of **Jetpack Compose** for modern UI
- Proper **Gradle configuration** using Kotlin DSL
- Clean, scalable, **MVVM-ready architecture**
- Professional GitHub project organization

---

## ✨ Features Implemented

- 🔐 **Login Screen**
  - App branding
  - Email input
  - Simple login flow (UI-only)

- 😊 **Mood Check-In Screen**
  - Friendly greeting
  - Mood selection using emojis
  - Optional text note for feelings

- ✅ **Habit Tracker**
  - Daily habit checklist
  - Simple, easy-to-use UI

- 📜 **Mood History**
  - List of previous mood entries
  - Date, emoji, and short note display

- 🧭 **Bottom Navigation**
  - Home
  - Habits
  - History

---

## 🧠 Architecture Overview

The project follows a **clean, modular structure** and is **MVVM-ready**.

- **UI Layer**: Jetpack Compose screens and reusable components  
- **Navigation Layer**: Centralized navigation using Compose Navigation  
- **Model Layer**: Data classes representing mood entries and habits  
- **ViewModel Layer**: Prepared for future business logic and state handling  

> Note: Backend, Firebase, and AI integrations are intentionally excluded at this stage to keep the project focused on UI and architecture.

---

## 🛠 Tech Stack

- **Language**: Kotlin  
- **UI**: Jetpack Compose + Material 3  
- **Architecture**: MVVM-ready structure  
- **Build System**: Gradle (Kotlin DSL)  
- **Dependency Management**: Version Catalog (`libs.versions.toml`)  

---

## 📁 Project Structure

app/src/main/java/com/mcn/moodlens/
│
├── MainActivity.kt
│
├── navigation/
│ └── NavGraph.kt
│
├── ui/
│ ├── screens/
│ │ ├── LoginScreen.kt
│ │ ├── HomeScreen.kt
│ │ ├── HabitScreen.kt
│ │ └── HistoryScreen.kt
│ │
│ └── components/
│ ├── MoodEmojiButton.kt
│ └── HabitItem.kt
│
├── model/
│ ├── MoodEntry.kt
│ └── Habit.kt
│
├── viewmodel/
│ └── MoodViewModel.kt
│
└── utils/
└── FakeData.kt

---

## ▶️ How to Run the Project

1. Open the project in **Android Studio**
2. Let **Gradle sync** complete
3. Select an emulator or physical device
4. Click **Run ▶**

> VS Code is used for code editing, while Android Studio is used for building and running the app.

---

---

## 📊 Architecture Diagrams (PDF)

This project includes complete architecture documentation in a single PDF file containing:

- ✅ ERD (Entity Relationship Diagram)
- ✅ DRD (Firestore Data Relationship Diagram)
- ✅ User Journey / Flow Diagram

⬇️ **Direct Download:**  
[Download MoodLens_Architecture_v2.pdf](https://github.com/Subodh-1370/MoodLens-Android/raw/main/diagrams/MoodLens_Architecture_v2.pdf)

---


