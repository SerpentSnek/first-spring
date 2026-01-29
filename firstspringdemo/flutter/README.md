# Flutter demo — First Spring

Quick steps to run the demo UI (Products & Inventory) locally.

## Prerequisites
- Install Flutter (https://flutter.dev/docs/get-started/install) and add it to PATH.
- Recommended: run `flutter doctor` and ensure SDK and basic tools are healthy.

## One-click launch scripts (Windows)
- `scripts/run_chrome.ps1` — runs the demo on Chrome (web).
- `scripts/run_windows.ps1` — runs the demo on Windows (desktop).
- There are also `.bat` wrappers (`run_chrome.bat`, `run_windows.bat`) to launch the PowerShell scripts by double-click.

## Run steps (manual)
1. Open terminal in `firstspringdemo/flutter`
2. Ensure web/desktop is enabled if necessary:
   - `flutter config --enable-web`
   - `flutter config --enable-windows-desktop`
3. If you see a message about missing platform files, run:
   - `flutter create .`
4. Fetch packages:
   - `flutter pub get`
5. Run the app:
   - Web: `flutter run -d chrome`
   - Windows: `flutter run -d windows`

## About the demo
- The app uses in-memory demo services (`lib/services/demo_services.dart`).
- It intentionally does not require a backend — FAB performs in-memory adds so you can explore UI quickly.

## Troubleshooting
- If `flutter run` fails, run `flutter doctor` and inspect the suggested fixes.
- If Chrome/Windows do not run cleanly, use `flutter create .` then try again.

