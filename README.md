# Smart Pantry Manager

A Java Android app that helps reduce food waste. The user records the ingredients
they already have (their "pantry"), and the app suggests only the recipes they can
cook right now, with no shopping trip needed.

## Features
- Add, edit and delete pantry items (name, quantity, unit, optional expiry date)
- 20 recipes pre-loaded on first run
- Strict matching: a recipe is suggested only if every ingredient is in the pantry
  in at least the required quantity (handles "tomato" vs "Tomatoes" and unit
  differences such as kg and g)
- Recipe detail screen with ingredients and method
- Settings screen: highlight ingredients expiring within 3 days
- Bottom navigation, input validation and an empty-state message

## Database choice: SQLite
I chose SQLite (SQLiteOpenHelper) because the data is personal and small, the app
must work offline in a kitchen, and it needs no server or account. The data
persists after the app is closed. Three tables: `pantry_items`, `recipes`,
`recipe_ingredients` (linked by a foreign key with ON DELETE CASCADE).

## Setup and run
1. Install Android Studio and clone this repository.
2. Open the project folder in Android Studio and wait for Gradle to sync.
3. Create an emulator (for example Pixel 6, API 34) or connect a phone.
4. Press Run. The recipes are seeded automatically on first launch.

## Requirements
Java, minSdk 24. No Google Maps, GPS or location features are used.
