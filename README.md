# TodoApp Project Documentation

## Overview
The TodoApp is a cross-platform task management application built using Jetpack Compose Multiplatform (CMP), targeting Android, iOS, and potentially other platforms supported by Kotlin Multiplatform. This project demonstrates a modern, scalable, and maintainable mobile application architecture with a focus on reactive UI updates. Key features include creating, editing, deleting, and filtering tasks, with support for marking tasks as favorite or completed. The app also includes a custom Material Theme with light and dark mode support, adapting dynamically to system settings or user preferences.

## Architecture
The project adheres to the Model-View-ViewModel (MVVM) architectural pattern, which separates concerns into three distinct layers:

- **Model**: Represents the data layer, managed by a repository that interacts with a local database.
- **ViewModel**: Acts as the intermediary between the UI and the data layer, exposing state and handling business logic.
- **View**: Built using Jetpack Compose, reacting to state changes from the ViewModel via StateFlow.

### Alternative: MVI Consideration
While MVVM was selected for its simplicity and rapid development benefits, an alternative approach like Model-View-Intent (MVI) was considered. MVI uses intents (user actions) to drive state changes, centralizing events into a single intent stream, which can simplify callback management in screens.

For example, instead of passing multiple lambdas to handle events (e.g., `onTitleChange`, `onSave`), MVI would consolidate these into a single intent (e.g., `Intent.UpdateTitle`, `Intent.SaveTask`).

However, to prioritize development speed and reduce complexity, MVVM was chosen with a single source of truth (SSOT) for each screen's state. This ensures a predictable state flow while avoiding the overhead of intent dispatching typical in MVI.

## Technologies and Libraries

### Dependency Injection with Koin
- Koin is used for dependency injection, providing a lightweight and pragmatic solution for managing dependencies across the app.
- The `koinViewModel()` function injects ViewModels into composables, ensuring scoped instances tied to the composition lifecycle.


## Data Layer with SQLDelight and Flows

SQLDelight serves as the local database solution, generating type-safe Kotlin code from SQL queries defined in `.sq` files (e.g., `TodoAppDatabase.sq`).

The `sqldelight-coroutines-extensions` library enables the use of `asFlow()` to convert SQLDelight queries into reactive Flows, facilitating real-time UI updates.

The `TaskRepositoryImpl` uses `asFlow()` with `mapToList()` and custom mapping to transform database rows into domain models (`TaskModel`).

## Domain Layer in `:lib:todoTask`

In this architecture, the module `:lib:todoTask` contains the **domain** logic for the feature `:feat:todoTask`. The primary goal of this separation is to **decouple** the domain layer from any external dependencies, ensuring that it remains **pure**, **independent**, and functions strictly as a **consumable API**.

### Key Principles

- **Decoupling**: The domain layer does not depend on any specific platform (Android/iOS) or framework (Compose, SQLDelight, etc.). It only defines **core business rules**, **entities**, **use cases**, and **interfaces**.

- **Reusability**: By keeping the domain independent, it can be easily reused across different features or platforms without modification.

- **Testability**: The domain layer can be tested in isolation, as it doesn't rely on any external implementations or UI frameworks.

- **API-First Approach**: The domain acts as an **API** that exposes contracts (interfaces) and models. Implementations of those contracts (repositories, data sources) are injected from outer layers (typically in the `data` or `presentation` modules).

### Structure Example in `:lib:todoTask`

:lib:todoTask └── domain ├── model │ └── TaskModel.kt ├── repository │ └── TaskRepository.kt

### Why This Matters

By isolating the domain in `:lib:todoTask`, we:
- Maintain **Clean Architecture** principles.
- Ensure the **domain layer** remains **stable** and **unaffected** by changes in data sources or UI layers.
- Promote **scalability** and **maintainability** across the entire codebase.