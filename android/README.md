# Danish↔English Offline Dictionary (Android)

Fully offline Danish to English and English to Danish dictionary app.

## Highlights
- 100% offline. No network permission, telemetry, or external calls.
- Fast local search using Room + FTS.
- Toggle direction (DA→EN / EN→DA).
- Each result shows headword, phonetic notation, part of speech, synonyms (destination language), and example sentence with translation.
- Jetpack Compose UI with Material 3.

## Build
Open the `android` folder in Android Studio (Giraffe+). Android Studio will create the Gradle wrapper if missing.
## Build
Open the `android` folder in Android Studio (Giraffe+) OR use the CLI from the root directory.

### Quickstart from CLI
```powershell
# From c:\daneng (project root)
.\build.bat assembleDebug
```

The APK will be at: `c:\daneng\android\app\build\outputs\apk\debug\app-debug.apk`

### Troubleshooting
If the IDE highlights many `Unresolved reference: androidx` or Kotlin standard library errors:
1. In Android Studio: "Sync Project with Gradle Files".
2. Accept prompt to generate missing wrapper (we've added wrapper scripts; Studio will fetch the JAR).
3. After sync, reopen affected files to clear stale diagnostics.

### Configuration Details
- Min SDK 24; Target SDK 35.
- First launch seeds a small sample dataset from assets.

Launcher icon
- The app icon uses `daneng_icon.png` bundled at `app/src/main/res/drawable/daneng_icon.png`.
- Adaptive icons reference this image via `@drawable/ic_launcher_foreground`.
- To replace the icon, swap the PNG at that path; recommended size is 432×432px for adaptive foreground.

## Data model
	- `DictionaryRepositoryTest` validates prefix + diacritics normalization search.
	- `TextUtilsTest` validates normalization logic.

Offline verification:
```powershell
# Ensure no INTERNET permission
Select-String -Path app\src\main\AndroidManifest.xml -Pattern "INTERNET" # (should return nothing)
- `Sense(sourceWordId, targetLanguage, partOfSpeech, synonyms[List], examples[List<ExamplePair>])`.

## Seeding and extending data
- Sample CSV assets live in `app/src/main/assets/seed/`.
- To extend the vocabulary, add more rows to the CSVs or add new CSVs and update the importer accordingly. Keep offline-only.
- For production scale, consider prebuilding an SQLite DB and packaging it as an asset; add a Room migration when schema changes.

## Testing
- Unit tests live under `app/src/test`. Run tests from Android Studio or `./gradlew test`.

## License
- Project excludes proprietary dictionary content; provide your own datasets that you have rights to use.
