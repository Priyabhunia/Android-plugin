# WhatsApp Status Guard (Android)

This project is a simple Android app that helps you avoid opening **WhatsApp Status/Updates** unnecessarily.

## What it does
- Lets you set a fixed daily time window.
- During the blocked period, it detects WhatsApp Status/Updates screens and immediately exits them.
- It does **not** block chats or contacts.

---

## Quick answer to your questions

### Do I need Android Studio?
**No, not strictly required.**
You can compile using only:
- JDK (recommended: **JDK 17**)
- Android SDK **Command-line Tools**
- Gradle (or Gradle Wrapper)

Android Studio is easier, but if you don’t want heavy software, command-line build works.

### Should I compile on Windows, then install on Android?
**Yes.** Typical flow:
1. Build APK on Windows.
2. Transfer APK to phone and install, or install directly using `adb`.

---

## Windows setup (without Android Studio)

> Recommended versions:
> - JDK 17
> - Android SDK Platform 34 + Build-Tools 34.x

### 1) Install required tools

1. Install **JDK 17** (Temurin/Microsoft/OpenJDK are fine).
2. Download Android **Command-line Tools** zip from official Android developer site.
3. Extract to (example):
   - `C:\Android\cmdline-tools\latest\...`
4. Create SDK folders if needed:
   - `C:\Android\platform-tools`
   - `C:\Android\platforms`
   - `C:\Android\build-tools`

### 2) Set environment variables (Windows)

Set:
- `ANDROID_SDK_ROOT=C:\Android`
- Add to `Path`:
  - `C:\Android\cmdline-tools\latest\bin`
  - `C:\Android\platform-tools`

### 3) Install SDK packages

Run in PowerShell/CMD:

```bash
sdkmanager --licenses
sdkmanager "platform-tools" "platforms;android-34" "build-tools;34.0.0"
```

### 4) Point project to SDK

Create `local.properties` in project root:

```properties
sdk.dir=C:\\Android
```

### 5) Build debug APK

If Gradle wrapper exists:

```bash
.\gradlew.bat assembleDebug
```

If wrapper is missing, use system Gradle:

```bash
gradle assembleDebug
```

APK output path:

```text
app\build\outputs\apk\debug\app-debug.apk
```

---

## Install on your Android phone

### Option A: USB + ADB (recommended)
1. Enable Developer Options + USB debugging on phone.
2. Connect phone via USB.
3. Run:

```bash
adb devices
adb install -r app\build\outputs\apk\debug\app-debug.apk
```

### Option B: Manual file transfer
1. Copy APK to phone storage.
2. Open APK from file manager.
3. Allow install from unknown sources when prompted.

---

## Runtime setup (important)
1. Open **Status Guard** app.
2. Set block start/end time.
3. Tap **Save daily window**.
4. Tap **Enable blocker service**.
5. In Accessibility settings, enable **Status Guard** service.

Without Accessibility permission, blocking will not work.

---

## Notes / limitations
- This works through Android Accessibility APIs because third-party apps cannot directly modify WhatsApp UI.
- Detection relies on text like "Status" / "Updates" and may need tuning for language or WhatsApp UI changes.
- If WhatsApp redesigns screens, detection logic may require updates.
