### Architecture: MVVM

This project follows the **MVVM (Model–View–ViewModel)** pattern.

* **Model (`data/`)** → Defines data structures and handles persistence (Room, DAO, Repository)
* **View (`ui/`)** → Screens that render UI and handle user interaction
* **ViewModel (`ui/...ViewModel.kt`)** → Holds UI state and coordinates between View and Model

The folder structure directly reflects this separation:

* `data/` = Model
* `ui/` = View + ViewModel

---

### Project Structure with Reasoning

```
OrderManagementCake/app/src/main/java/com/example/ordermanagementcake/
├── data/                              # Model layer (data + business logic)
│   ├── local/
│   │   ├── OrderDatabase.kt          # Room Database definition (Placeholder)
│   │   ├── dao/                      # Database operations (Empty)
│   │   └── entities/                 # Database entities/tables (Empty)
│   │
│   └── repository/                    # Data logic and abstraction (Empty)
│
├── repository/                        # Duplicate/Placeholder folder (Empty)
│
└── ui/                               # View layer (UI)
    ├── clients/
    │   └── ClientsListScreen.kt      # Displays list of clients
│
    ├── orders/
    │   └── OrderListScreen.kt        # Displays list of orders
│
    ├── components/                    # Reusable UI components
    │   ├── AppTopBar.kt              # Common TopAppBar for the app
    │   └── Components.kt             # Shared UI elements like BottomNavigationBar
│
    ├── navigation/                   # App navigation logic (Navigation Compose)
    │   └── NavGraph.kt               # Routes and NavHost configuration
│
    ├── theme/                        # App styling and theme
    │   ├── Color.kt
    │   ├── Theme.kt
    │   └── Type.kt
│
└── MainActivity.kt                    # Entry point, hosts the AppNavHost
```

---

### Key Structural Decisions

* **Feature-based grouping (`orders/`, `clients/`)**
  Keeps related UI and logic together, improving maintainability and scalability.

* **Single ViewModel per feature**
  Each feature (`orders`, `clients`) has one ViewModel to centralize state and logic.

* **Repository layer between UI and DAO**
  Prevents direct database access from UI, making the app easier to modify and test.

* **Theme and Components centralization**
  The `theme/` and `components/` folders ensure a consistent look and feel across the entire application.

--- 

## Navigation

Aplikasaiun agora uza ona Navigation Compose ba scren navigatyion nian

`AppNavHost` iha `ui/navigation/NavGraph.kt` mak resonsoavble ba:

- Scaffold ba **TopBar** no **BottomBar** haketak husi main content
- DefineROute no mapeia ba iha screen

```bash

# visualmente nia hnsa ne
┌─────────────────────┐
│       TopBar        │  ← lives in the main Scaffold(AppNavHost), never changes
├─────────────────────┤
│                     │
│   content changes   │  ← only this part swaps when you navigate
│   here              │
│                     │
├─────────────────────┤
│     BottomBar       │  ← lives in the main Scaffold(AppNavHost), never changes
└─────────────────────┘
```

