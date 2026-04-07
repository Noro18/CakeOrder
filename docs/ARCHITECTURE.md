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
│   │   ├── OrderDatabase.kt          # Central database instance (single source of truth)
│   │   ├── dao/                      # Defines database operations
│   │   └── entities/                 # Database entities (tables)
│   │
│   └── repository/                    # Handles data logic and abstracts data access
│
├── ui/                               # View layer (UI + ViewModels)
│   ├── clients/
│   │   ├── ClientsListScreen.kt      # Displays list of clients
│   │   └── ClientsViewModel.kt       # Manages client-related UI state
│   │
│   ├── orders/
│   │   ├── OrderListScreen.kt        # Displays list of orders (summary view)
│   │   └── OrderViewModel.kt         # Manages order UI state
│   │
│   ├── components/                    # Reusable UI components
│   │   └── Components.kt
│   │
│   ├── navigation/                   # App navigation logic and routes
│   │
│   ├── theme/                        # App styling and theme
│       ├── Color.kt
│       ├── Theme.kt
│       └── Type.kt
│
└── MainActivity.kt                    # Main entry point of the application
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

### Database Schema

This project uses **Room** as the local database. The schema follows a simple relational structure with 3 tables.

---

#### Tables

**`CLIENT`**
Stores the basic information of each customer.

| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER` PK | Auto-generated unique identifier |
| `name` | `TEXT` | Full name of the client |
| `whatsapp_number` | `TEXT` | WhatsApp number, used as a unique identifier to avoid duplicate clients |
| `address` | `TEXT` | Delivery or contact address |

---

**`ORDER`**
Represents a single order placed by a client. One client can have many orders, each with its own pickup date.

| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER` PK | Auto-generated unique identifier |
| `client_id` | `INTEGER` FK | References `CLIENT.id` |
| `order_date` | `DATE` | The date the order was placed |
| `pickup_date` | `DATE` | The date the client will collect the order |
| `status` | `TEXT` | Current status: `PENDING`, `IN_PROGRESS`, `READY`, `COMPLETED` |
| `notes` | `TEXT` | Any special instructions for the entire order |

---

**`ORDER_ITEM`**
Represents an individual cake within an order. One order can contain many items — for example, 1 chocolate cake and 20 cupcakes in the same order.

| Column | Type | Description |
| :--- | :--- | :--- |
| `id` | `INTEGER` PK | Auto-generated unique identifier |
| `order_id` | `INTEGER` FK | References `ORDER.id` |
| `title` | `TEXT` | Name of the cake (e.g. "Double Chocolate Fudge") |
| `description` | `TEXT` | Custom details such as flavor, size, decoration, or messages |
| `image_ref` | `TEXT` | Filename or path reference to the cake image |
| `quantity` | `INTEGER` | Number of units ordered for this item |
| `notes` | `TEXT` | Item-specific instructions |

---

#### Relationships

| Relationship | Type | Description |
| :--- | :--- | :--- |
| `CLIENT` → `ORDER` | One to many (optional) | A client can have zero or many orders. Each order belongs to exactly one client. |
| `ORDER` → `ORDER_ITEM` | One to many (mandatory) | An order must have at least one item. Each item belongs to exactly one order. |

---

#### Design Decisions

- **No separate CAKE catalog table** — since this is a custom cake business, cake details are stored directly inside `ORDER_ITEM`. This avoids the overhead of maintaining a catalog for items that are rarely repeated identically.
- **WhatsApp number as unique identifier** — when registering a new order, the app checks if a client with that WhatsApp number already exists. If yes, it reuses the existing client record. If no, it creates a new one automatically. This enables a one-step order registration flow.
- **Multiple orders per client for different dates** — if a client orders cakes for two different events, two separate `ORDER` rows are created, each with its own `pickup_date`. The client record is shared between both orders.

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

