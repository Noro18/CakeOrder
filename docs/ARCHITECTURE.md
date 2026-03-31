### Architecture: MVVM

This project follows the **MVVM (Model–View–ViewModel)** pattern.

* **Model (`data/`)** → Defines data structures and handles persistence (Room, DAO, Repository)
* **View (`ui/`)** → Fragments that render UI and handle user interaction
* **ViewModel (`ui/...ViewModel.kt`)** → Holds UI state and coordinates between View and Model

The folder structure directly reflects this separation:

* `data/` = Model
* `ui/` = View + ViewModel

---

### Project Structure with Reasoning

```
CakeOrderApp/
├── data/                              # Model layer (data + business logic)
│   ├── model/
│   │   ├── Customer.kt               # Represents customer entity (core app data)
│   │   ├── Order.kt                  # Represents order entity (main business object)
│   │   └── OrderStatus.kt            # Shared enum for order state across app
│   │
│   ├── local/
│   │   ├── AppDatabase.kt            # Central database instance (single source of truth)
│   │   └── dao/
│   │       ├── CustomerDao.kt        # Defines database operations for customers
│   │       └── OrderDao.kt           # Defines database operations for orders
│   │
│   └── repository/
│       ├── CustomerRepository.kt     # Abstracts customer data access (decouples UI from DB)
│       └── OrderRepository.kt        # Handles all order-related data logic
│
└── ui/                               # View layer (UI + ViewModels)
    ├── orders/
    │   ├── OrderListFragment.kt      # Displays list of orders (summary view)
    │   ├── OrderDetailFragment.kt    # Displays full details of a selected order
    │   ├── AddEditOrderFragment.kt   # Handles creating and editing orders (form reuse)
    │   └── OrderViewModel.kt         # Manages order UI state shared across fragments
│
    ├── customers/
    │   ├── CustomerListFragment.kt   # Displays list of customers
    │   ├── AddEditCustomerFragment.kt# Handles creating/editing customers
    │   └── CustomerViewModel.kt      # Manages customer-related UI state
│
    └── delivery/
        ├── DeliveryScheduleFragment.kt # Displays upcoming deliveries
        └── DeliveryViewModel.kt       # Handles delivery-related UI logic
```

---

### Key Structural Decisions

* **Feature-based grouping (`orders/`, `customers/`, `delivery/`)**
  Keeps related UI and logic together, improving maintainability and scalability.

* **Single ViewModel per feature**
  Each feature (`orders`, `customers`, `delivery`) has one ViewModel to centralize state and logic.

* **Repository layer between UI and DAO**
  Prevents direct database access from UI, making the app easier to modify and test.

* **Shared models in `data/model/`**
  Ensures consistency across all features using the same data definitions.
