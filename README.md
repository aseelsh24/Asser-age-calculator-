# Asser – آسر (Advanced Age Calculator)

Asser is a modern, elegant, and robust Android application that calculates a person's precise age down to the minute. It is built with the latest Android development standards, featuring a clean architecture and a user-friendly interface.

## Features

*   **Dual Date Picker:** Intuitive UI for selecting both a birth date and a target end date.
*   **Real-time & Custom Calculation:**
    *   Calculate age from the birth date to the current moment.
    *   Calculate age between a birth date and a user-selected future or past date.
*   **Detailed Result Display:** The calculated age is presented in a detailed breakdown:
    *   Years, Months, Days
    *   Total Days
    *   Total Hours
    *   Total Minutes
*   **Sharing:** Easily share the detailed result via any installed application (WhatsApp, SMS, Email, etc.).
*   **About Screen:** Displays the app name, a short description, version name, and version code.
*   **Dark Theme:** Full support for both light and dark modes, automatically following the system settings.
*   **Multi-language Support:**
    *   Full support for English (default) and Arabic.
    *   The app automatically switches the language based on the device's locale, with proper Right-to-Left (RTL) layout mirroring for Arabic.

## Screenshots

*(Placeholder for app screenshots in both light and dark mode)*

## Tech Stack & Architecture

*   **Language:** 100% [Kotlin](https://kotlinlang.org/)
*   **Architecture:** Modern [MVVM (Model-View-ViewModel)](https://developer.android.com/topic/architecture)
*   **UI:** XML layouts with [Material Components 3 (MD3)](https://m3.material.io/)
*   **ViewBinding:** Used for safe interaction with views.
*   **Date/Time Handling:** Modern `java.time` API for all calculations.
*   **Dependency Management:** [Gradle with Kotlin DSL](https://docs.gradle.org/current/userguide/kotlin_dsl.html)
*   **Testing:** [JUnit](https://junit.org/junit4/) for unit testing the age calculation logic.
*   **Asynchronous Programming:** [Kotlin Coroutines](https://kotlinlang.org/docs/coroutines-overview.html) and `StateFlow` for managing state.

## Building from Source

To build the project from the source, follow these steps:

1.  **Clone the repository:**
    ```sh
    git clone https://github.com/[your-repository-url].git
    cd asser-age-calculator
    ```

2.  **Generate Gradle Wrapper:**
    The `gradle-wrapper.jar` binary is not included in the repository. The `gradlew` script will automatically download the correct Gradle distribution the first time you run a command.

3.  **Build the project:**
    Use the included Gradle wrapper to build the project.

    *   On macOS/Linux:
        ```sh
        ./gradlew build
        ```
    *   On Windows:
        ```sh
        ./gradlew.bat build
        ```

4.  **Generate a Release APK:**
    You can build a release APK using the following command. The APK will be signed with the debug key included in the repository.

    *   On macOS/Linux:
        ```sh
        ./gradlew assembleRelease
        ```
    *   On Windows:
        ```sh
        ./gradlew.bat assembleRelease
        ```
    The generated APK will be located at `app/build/outputs/apk/release/app-release.apk`.

## CI/CD

This project uses [GitHub Actions](.github/workflows/android.yml) for Continuous Integration and Continuous Deployment. The workflow is configured to:
-   **Build & Test:** Trigger on every push and pull request to the `main` branch.
-   **Upload Artifact:** Upload the generated APK as a build artifact after a successful build.
-   **Create Release:** Automatically create a new GitHub Release with the APK attached whenever a new version tag (e.g., `v1.0.1`) is pushed to the repository.
