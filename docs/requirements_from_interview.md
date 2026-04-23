# PRODUCT REQUIREMENTS

# 🎂 Mama's Cake Order App — Product Requirements Document

> A personal mobile app to replace the handwritten order book, helping Mom manage cake orders, client details, and baking schedules — all in one place.

---

## 1. Overview

**App Name:** _(TBD — e.g. "Key Book" or "Mom's Cake Orders")_ **Platform:** Mobile (Android preferred — background notifications required) **Primary User:** Mom (single user, no login needed unless desired) **Goal:** Digitize cake orders, automate price suggestions, and send timely baking reminders.

---

## 2. Core Features

### 2.1 Order Management

Create, view, edit, and delete cake orders. Each order captures all client and cake details.

### 2.2 Notification System

Automatic reminders to start baking and a follow-up reminder the day before pickup — based on cake type.

### 2.3 Cake Size & Price Database

A manageable list of cake sizes and their base prices, used to auto-calculate order totals.

---

## 3. Data Model

### 3.1 Client Information

|Field|Type|Notes|
|---|---|---|
|Name|Text|Client's full name|
|WhatsApp Number|Phone number|For contact & future WhatsApp integration|
|Address|Text (multiline)|Delivery or reference address|

---

### 3.2 Cake Information

| Field                  | Type                             | Notes                                                                            |
| ---------------------- | -------------------------------- | -------------------------------------------------------------------------------- |
| Cake Type              | Dropdown/Select                  | e.g. "Kue Maran", "Wedding Cake", "Birthday Cake", etc. Mom can manage this list |
| Cake Color             | Color Picker                     | Visual color selector — no manual text input                                     |
| Decoration Category    | Select (single choice)           | **Flowers** or **Karakter** (toys, cars, figurines)                              |
| Decoration Description | Text (multiline)                 | Short note describing the specific decoration                                    |
| Cake Theme             | Text                             | e.g. "Minecraft", "Princess", "Floral Garden"                                    |
| Theme Description      | Text (multiline)                 | Additional details about the theme                                               |
| Writing on Cake        | Text (optional)                  | Name, message, or phrase to be written on the cake. Leave blank if none.         |
| Cake Levels            | Dynamic list (see Section 3.2.1) | Each level has its own pan size selection                                        |
| Auto-calculated Price  | Currency                         | Sum of all level prices from the database — **fully editable**                   |
| Final Price            | Currency (editable)              | Mom can override the auto-calculated total at any time                           |

#### 3.2.1 Cake Levels UI

Each cake can have one or more levels. In the order form, Mom can:

1. **Add a level** — tapping "+ Add Level" appends a new level row
2. **Per level, select size parity first** — a toggle between **Odd sizes** (e.g. 15cm, 17cm, 19cm) or **Even sizes** (e.g. 16cm, 18cm, 20cm). This filters the size dropdown to the relevant options, making it faster to pick.
3. **Then select the pan size** from the filtered dropdown (sourced from the Size & Price database)
4. **Remove a level** — each level row has a delete/remove button
5. The **auto-calculated price updates live** as levels are added, removed, or changed

**Example:**

| Level            | Size Parity | Pan Size        | Price          |
| ---------------- | ----------- | --------------- | -------------- |
| Level 1 (bottom) | Even        | 20cm            | Rp 150,000     |
| Level 2          | Odd         | 17cm            | Rp 120,000     |
| Level 3 (top)    | Even        | 14cm            | Rp 90,000      |
|                  |             | **Auto Total**  | **Rp 360,000** |
|                  |             | **Final Price** | _(editable)_   |

---

### 3.3 Order Logistics

| Field                  | Type            | Notes                                                                  |
| ---------------------- | --------------- | ---------------------------------------------------------------------- |
| Order Date             | Date            | When the order was placed                                              |
| Pickup / Delivery Date | Date + Time     | When the client will take the cake — **this drives all notifications** |
| Order Status           | Status badge    | e.g. Pending → Baking → Ready → Completed                              |
| Notes                  | Text (optional) | Any extra instructions or reminders                                    |

---

## 4. Notification System

> ⚠️ This requires the app to run as a **background service** on the device. On Android this means using a scheduled notification or alarm manager. Keep this in mind when choosing the tech stack.

### 4.1 Notification Rules (by Cake Type)

| Cake Type       | Reminder 1: Start Baking | Reminder 2: Day Before Pickup                |
| --------------- | ------------------------ | -------------------------------------------- |
| Birthday Cake   | 1 day before pickup      | 1 day before pickup (same day, earlier time) |
| Wedding Cake    | 6 days before pickup     | 1 day before pickup                          |
| _(Other types)_ | Configurable by Mom      | Always 1 day before pickup                   |

> 💡 **Design suggestion:** Allow Mom to set a custom "days before" value per cake type in the settings, so it's flexible as the business grows.

### 4.2 Notification Content

**Notification 1 — Start Baking:**

> 🎂 Time to start the [Cake Type] for [Client Name]! Pickup is on [Date].

**Notification 2 — Day Before Pickup:**

> 📦 Reminder: [Client Name]'s cake is being picked up TOMORROW at [Time]. Is it ready?

---

## 5. Cake Size & Price Database

A lookup table where each pan size has a price. This is what powers the per-level price calculation.

### 5.1 Data Structure

| Field       | Type     | Notes                                                               |
| ----------- | -------- | ------------------------------------------------------------------- |
| Size Label  | Text     | e.g. "14cm", "17cm", "20cm"                                         |
| Size Parity | Enum     | **Odd** or **Even** — used to filter the dropdown in the order form |
| Price       | Currency | Price for one level of this size                                    |

### 5.2 Management

- Mom can **add, edit, and delete** sizes directly in the app via a "Manage Sizes" screen.
- When building an order, selecting a size for a level **immediately adds that level's price** to the running total.
- The final price field remains **manually editable** at all times — the auto-total is just a starting point.

### 5.3 Price Calculation Logic

```
Auto Price = price(level_1_size) + price(level_2_size) + ... + price(level_n_size)
```

This recalculates live every time a level is added, removed, or its size is changed.

---

## 6. Screens / App Structure

```
App
├── 📋 Orders List          — All orders, sortable by date / status
│   └── Order Card          — Shows client name, pickup date, status badge
│
├── ➕ New Order             — Form to create a new order (all fields from Section 3)
│
├── 🔍 Order Detail          — View full order info
│   ├── Edit Order
│   └── Delete Order
│
├── 🔔 Notifications         — Log of sent/upcoming reminders (optional but helpful)
│
└── ⚙️  Settings
    ├── Manage Cake Types    — Add/edit/delete cake types
    ├── Manage Sizes & Prices — Add/edit/delete the price database (Section 5)
    └── Notification Rules   — Set "days before" per cake type
```

---

## 7. Tech Stack Recommendations

| Option          | Stack                                      | Notes                                                              |
| --------------- | ------------------------------------------ | ------------------------------------------------------------------ |
| **Recommended** | React Native + Expo                        | Cross-platform, good notification support via `expo-notifications` |
| Alternative     | Flutter                                    | Strong Android support, great for offline-first apps               |
| Database        | SQLite (via Expo SQLite or Drift)          | Local, offline, no internet needed                                 |
| Notifications   | `expo-notifications` / `expo-task-manager` | Required for background scheduling                                 |

> 💡 Since this is a **personal, offline app** for one user, no backend server is needed. Everything lives on the phone.

---

## 8. Nice-to-Have Features (Future)

- [x] **Photo attachment** — Attach a reference photo or inspiration image to an order
- [ ] **WhatsApp quick-send** — One tap to message the client via WhatsApp
- [ ] **Order summary export** — Generate a simple PDF or image summary to share
- [ ] **Monthly revenue summary** — Total earnings per month based on completed orders
- [x] **Dark mode** — Easier on the eyes for evening use

---

## 9. Open Questions to Resolve Before Development

1. **Cake types list** — What are all the current cake types Mom offers? Need the full list to pre-populate the database.
2. **Size & price list** — What are all current sizes and their prices, and which are odd vs even? These will seed the initial database.
3. **Platform priority** — Android only, or iOS too?
4. **Notification timing** — What time of day should the "start baking" reminder fire? (e.g. 7:00 AM)
5. **Multiple orders per day** — Is it possible to have more than one order with the same pickup date? The app should handle this gracefully.
6. **Maximum levels** — Is there a practical maximum number of levels a cake can have? (Helps with UI design — e.g. cap at 5 or 6 levels.)

---

_Document version: v1.0 — Based on initial interview with Mom._ _Next step: Resolve open questions (Section 9), then begin with the database schema and Orders List screen._
