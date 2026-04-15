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
│   │   ├── OrderDatabase.kt          # Room Database — singleton with seed data on first create
│   │   ├── SeedData.kt               # Pre-populates DB with sample clients, orders, and items
│   │   ├── dao/
│   │   │   ├── ClientDao.kt          # CRUD + search operations for clients
│   │   │   ├── OrderDao.kt           # CRUD + filter by status for orders
│   │   │   └── OrderItemDao.kt       # CRUD operations for order items
│   │   └── entities/
│   │       ├── ClientEntity.kt       # Clients table (id, name, phoneNumber, address, createdAt)
│   │       ├── OrderEntity.kt        # Orders table (id, clientId, dates, status, notes)
│   │       └── OrderItemEntity.kt    # Order items table (id, orderId, title, qty, notes, etc.)
│   │
│   ├── local/relations/
│   │   ├── ClientWithOrder.kt        # @Relation joining ClientEntity → List<OrderEntity>
│   │   └── OrderWithItems.kt         # @Relation joining OrderEntity → List<OrderItemEntity>
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

**`ClientUiState`**
| Field | Type | Purpose |
| :--- | :--- | :--- |
| `clients` | `List<ClientEntity>` | All clients loaded from DB |
| `isLoading` | `Boolean` | Show loading spinner while fetching |
| `errorMessage` | `String?` | Non-null if the DB flow threw an exception |
| `searchQuery` | `String` | Current value of the search bar; filtering happens in the screen |

**`OrderUiState`**
| Field | Type | Purpose |
| :--- | :--- | :--- |
| `orders` | `List<OrderEntity>` | Orders for the currently selected status |
| `isLoading` | `Boolean` | Show loading spinner while fetching |
| `errorMessage` | `String?` | Non-null if the DB flow threw an exception |
| `selectedStatus` | `String` | Active filter button (defaults to `"PENDING"`) |

---

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

This project uses **Room** as the local database. The schema follows a simple relational structure with 3 tables.

#### Tables

**`CLIENT`**

| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER` PK | Auto-generated unique identifier |
| `name` | `TEXT` | Full name of the client |
| `phoneNumber` | `TEXT` | WhatsApp number, used as a unique identifier to avoid duplicates |
| `address` | `TEXT` | Delivery or contact address |
| `createdAt` | `TEXT` | Date the client record was created |

---

**`ORDER`**

| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER` PK | Auto-generated unique identifier |
| `clientId` | `INTEGER` FK | References `CLIENT.id` |
| `orderDate` | `TEXT` | The date the order was placed |
| `pickupDate` | `TEXT` | The date the client will collect the order |
| `status` | `TEXT` | Current status: `PENDING`, `IN_PROGRESS`, `READY`, `COMPLETED` |
| `notes` | `TEXT` | Special instructions for the entire order |
| `createdAt` | `TEXT` | Date the order record was created |

---

**`ORDER_ITEM`**

| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER` PK | Auto-generated unique identifier |
| `orderId` | `INTEGER` FK | References `ORDER.id` |
| `title` | `TEXT` | Name of the cake (e.g. "Double Chocolate Fudge") |
| `description` | `TEXT` | Custom details: flavor, size, decoration, messages |
| `imageRef` | `TEXT` | Filename reference to the cake image drawable |
| `quantity` | `INTEGER` | Number of units ordered for this item |
| `notes` | `TEXT` | Item-specific instructions |

---

#### Relationships

| Relationship | Type | Description |
| :--- | :--- | :--- |
| `CLIENT` → `ORDER` | One to many (optional) | A client can have zero or many orders. Each order belongs to exactly one client. |
| `ORDER` → `ORDER_ITEM` | One to many (mandatory) | An order must have at least one item. Each item belongs to exactly one order. |

These are expressed in code via `@Relation` data classes (`ClientWithOrder`, `OrderWithItems`) and fetched with `@Transaction` queries in the DAOs.

---

#### Seed Data

On first database creation, `SeedData.kt` is called automatically via `OrderDatabase.PrepopulateCallback`. It inserts 6 sample clients, 6 orders across different statuses, and 7 order items so the app is immediately usable without manual data entry. This runs on `Dispatchers.IO` to avoid blocking the main thread.

---

#### Design Decisions

- **No separate CAKE catalog table** — since this is a custom cake business, cake details are stored directly inside `ORDER_ITEM`. This avoids the overhead of maintaining a catalog for items that are rarely repeated identically.
- **WhatsApp number as unique identifier** — when registering a new order, the app checks if a client with that WhatsApp number already exists. If yes, it reuses the existing client record. If no, it creates a new one automatically. This enables a one-step order registration flow.
- **Multiple orders per client for different dates** — if a client orders cakes for two different events, two separate `ORDER` rows are created, each with its own `pickupDate`. The client record is shared between both orders.

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