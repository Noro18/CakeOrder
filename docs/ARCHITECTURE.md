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
│   │   ├── OrderDatabase.kt          # Room Database — singleton with version 4 schema
│   │   ├── SeedData.kt               # Pre-populates DB with sample clients, orders, cakes, and tiers
│   │   ├── dao/
│   │   │   ├── ClientDao.kt          # CRUD + search operations for clients
│   │   │   ├── OrderDao.kt           # CRUD + filter by status for orders
│   │   │   ├── CakeDao.kt            # CRUD operations for cakes
│   │   │   └── TierDao.kt            # CRUD operations for tiers
│   │   └── entities/
│   │       ├── ClientEntity.kt       # Clients table
│   │       ├── OrderEntity.kt        # Orders table (delivery date, status, notes)
│   │       ├── CakeEntity.kt         # Cakes table (tracks title, image_uri, baking_date)
│   │       └── TierEntity.kt         # Tiers table (shape, size, level, color, price)
│   │
│   ├── local/relations/
│   │   ├── ClientWithOrders.kt       # Relation joining ClientEntity → List<OrderEntity>
│   │   ├── OrderWithCakes.kt         # Relation joining OrderEntity → List<CakeEntity>
│   │   └── OrderWithCakeAndTiers.kt  # Nested relation (Order -> Cakes -> Tiers)
│   │
│   └── repository/
│       ├── ClientRepository.kt       # Wraps ClientDao — used by ClientViewModel
│       └── OrderRepository.kt        # Wraps OrderDao — used by OrderViewModel
│
└── ui/                               # View layer (UI + ViewModel)
    ├── clients/
    │   ├── ClientsListScreen.kt      # Reads from ClientViewModel, renders client list
    │   ├── ClientViewModel.kt        # Exposes ClientUiState; loads clients, handles search
    │   ├── ClientViewModelFactory.kt # Creates ClientViewModel with ClientRepository injected
    │   └── ClientUiState.kt          # UI state: clients list, isLoading, errorMessage, searchQuery
    │
    ├── orders/
    │   ├── OrderListScreen.kt        # Reads from OrderViewModel, renders order list with filters
    │   ├── OrderViewModel.kt         # Exposes OrderUiState; loads by status, handles updates
    │   ├── OrderViewModelFactory.kt  # Creates OrderViewModel with OrderRepository injected
    │   └── OrderUiState.kt           # UI state: orders list, isLoading, errorMessage, selectedStatus
    │
    ├── dashboard/
    │   └── DashboardScreen.kt        # Static dashboard with summary cards and upcoming pickups
    │
    ├── components/                   # Reusable UI components
    │   ├── AppTopBar.kt              # Common TopAppBar for the app
    │   └── Components.kt             # Shared UI elements like BottomNavigationBar
    │
    ├── navigation/
    │   └── NavGraph.kt               # Routes and NavHost configuration
    │
    └── theme/                        # App styling and theme
        ├── Color.kt
        ├── Theme.kt
        └── Type.kt

MainActivity.kt                       # Entry point, hosts the AppNavHost
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

### Data Flow

The full MVVM data flow is now connected end-to-end. Here is how data moves from the database to the screen:

```
Room DB  →  DAO  →  Repository  →  ViewModel  →  UiState  →  Screen (Composable)
```

**Concrete example for the Clients screen:**

1. `OrderDatabase.getInstance(context)` returns the singleton Room database.
2. `ClientRepository` is created with `clientDao()` from the database.
3. `ClientViewModelFactory` receives the repository and produces a `ClientViewModel`.
4. `ClientViewModel.loadClients()` calls `repository.getAllClients()`, which returns a `Flow<List<ClientEntity>>` from Room.
5. The flow is collected inside a `viewModelScope` coroutine and updates `_uiState` (a `MutableStateFlow<ClientUiState>`).
6. `ClientsListScreen` observes `uiState` with `collectAsStateWithLifecycle()` and re-renders whenever the data changes.

The Orders screen follows the same pattern, with the added behaviour of filtering by status: `OrderViewModel.loadOrders(status)` fetches only orders matching the given `status` string (e.g. `"PENDING"`, `"READY"`).

---
### UiState Classes

Each feature has a dedicated `UiState` data class that represents everything the screen needs to render itself:

#### `ClientUiState`

| Field | Type | Purpose |
| :--- | :--- | :--- |
| `clients` | `List<ClientEntity>` | All clients loaded from DB |
| `isLoading` | `Boolean` | Show loading spinner while fetching |
| `errorMessage` | `String?` | Non-null if the DB flow threw an exception |
| `searchQuery` | `String` | Current value of the search bar; filtering happens in the screen |

#### `OrderUiState`

| Field | Type | Purpose |
| :--- | :--- | :--- |
| `orders` | `List<OrderEntity>` | Orders for the currently selected status |
| `isLoading` | `Boolean` | Show loading spinner while fetching |
| `errorMessage` | `String?` | Non-null if the DB flow threw an exception |
| `selectedStatus` | `String` | Active filter button (defaults to `"PENDING"`) |

### ViewModelFactory Pattern

Because the ViewModels require a `Repository` constructor argument, they cannot be created by the default `ViewModelProvider` without a factory. Each feature has its own factory:

```kotlin
// Example: ClientViewModelFactory
class ClientViewModelFactory(private val repository: ClientRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ClientViewModel::class.java)) {
            return ClientViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
```

The factory is passed directly at the call site in the Composable:

```kotlin
val viewModel: ClientViewModel = viewModel(
    factory = ClientViewModelFactory(
        ClientRepository(
            OrderDatabase.getInstance(LocalContext.current).clientDao()
        )
    )
)
```

> **Note:** This manual wiring works fine for the current scale. If the app grows, consider adopting a dependency injection framework like **Hilt** to manage this automatically.

---

### Database

This project uses **Room** as the local database. The schema follows a relational structure with 7 core entities: Client, Order, Cake, Tier, Shape, Size, and Price Table.

#### Core Tables

- **`ClientEntity`**: Stores customer details (name, phone, address).
- **`OrderEntity`**: Represents a single customer agreement / delivery event. Tracks `delivery_date`, `status` (PENDING, IN_PROGRESS, READY, COMPLETED, CANCELLED), and `total_price`.
- **`CakeEntity`**: Represents a cake within an order. Tracks its own independent `baking_date` and `image_uri`.
- **`TierEntity`**: Represents a tier of a cake. Holds references to shape and size, level, color hex, and snapshot price.
- **`ShapeEntity`** & **`SizeEntity`**: Configuration tables for available shapes and sizes.
- **`PriceTableEntity`**: Maps Shape + Size combinations to base prices.

#### Relationships

- `ClientEntity` → `OrderEntity`: One-to-Many
- `OrderEntity` → `CakeEntity`: One-to-Many
- `CakeEntity` → `TierEntity`: One-to-Many
- `TierEntity` → `ShapeEntity` & `SizeEntity`: Many-to-One

#### Date, Status & Notification Placement Design

- **Delivery Date (`delivery_date` on `OrderEntity`)**: Represents the delivery target. If a customer needs cakes delivered on different dates, they must place separate orders (**"1 Order = 1 Delivery" rule**).
- **Baking Date (`baking_date` on `CakeEntity`)**: Stored per-cake so that multiple cakes in a single order can have different baking schedules and lead times.
- **Order Status (`status` on `OrderEntity`)**: Managed at the order level to simplify state updates, listing, and filtering. The entire order moves together through `PENDING → IN_PROGRESS → READY → COMPLETED`.
- **Notifications**: Reminders are dynamically calculated behaviors. Baking reminders are scheduled based on `cake.baking_date`, and delivery reminders are scheduled based on `order.delivery_date`.

#### Seed Data

On first database creation, `SeedData.kt` is called automatically via `OrderDatabase.PrepopulateCallback`. It inserts shapes, sizes, pricing rules, clients, orders, cakes, and tiers so the app is immediately usable without manual data entry. This runs on `Dispatchers.IO` to avoid blocking the main thread.

#### Design Decisions

- **No separate COLOR table** — color is stored directly on TIER as a hex string (`color_hex`). A color picker in the UI enforces valid hex format.
- **Price is snapshotted** — `TIER.price` is copied from `PRICE_TABLE` at order time so future price changes don't affect old orders.
- **CAKE is its own entity** — because one order can contain multiple cakes.
- **Shape lives on TIER not CAKE** — because mixed-shape cakes exist (e.g. square bottom tier, circle top tier).
- **Notifications are not an entity** — delivery and baking reminders are behaviors triggered by target dates, not stored rows in the database.
- **Multiple orders per client for different dates** — if a client orders cakes for two different events, two separate `ORDER` rows are created, each with its own `deliveryDate`. The client record is shared between both orders.

---

### Navigation

The app uses Navigation Compose for screen navigation.

`AppNavHost` in `ui/navigation/NavGraph.kt` is responsible for:

- A `Scaffold` that holds **TopBar** and **BottomBar**, which never change between screens
- Defining routes and mapping them to their screens

```
┌─────────────────────┐
│       TopBar        │  ← lives in the main Scaffold (AppNavHost), never changes
├─────────────────────┤
│                     │
│   content changes   │  ← only this part swaps when you navigate
│   here              │
│                     │
├─────────────────────┤
│     BottomBar       │  ← lives in the main Scaffold (AppNavHost), never changes
└─────────────────────┘
```

**Defined routes:**

| Route constant | Screen | Bottom nav index |
| :--- | :--- | :--- |
| `Routes.DASHBOARD` | `DashboardScreen` | 0 |
| `Routes.ORDERS` | `OrderListScreen` | 1 |
| `Routes.CLIENTS` | `ClientsListScreen` | 2 |