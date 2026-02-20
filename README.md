# WhatsApp Status Guard (Android)

This project is a simple Android app that helps you avoid opening **WhatsApp Status/Updates** unnecessarily.

## What it does
- Lets you set a fixed daily time window.
- During the blocked period, it detects WhatsApp Status/Updates screens and immediately exits them.
- It does **not** block chats or contacts.

## How to use
1. Build and install the app.
2. Open the app and select block start/end times.
3. Tap **Save daily window**.
4. Tap **Enable blocker service** and turn on **Status Guard** in Accessibility settings.

## Notes
- This works through Android Accessibility APIs because third-party apps cannot directly modify WhatsApp UI.
- Detection relies on text like "Status" / "Updates" and may need tuning for language or WhatsApp UI changes.
