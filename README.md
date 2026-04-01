# OrderManagementCake

<p align="center">
  <a href="https://visitorbadge.io/status?path=Noro18%2FCakeOrder">
    <img src="https://api.visitorbadge.io/api/combined?path=Noro18%2FCakeOrder&labelColor=%23697689&countColor=%23263759" />
  </a>
</p>

*(Tetum iha kraik)*

A basic Android application for managing cake orders, built with modern Android development practices.

## Description

OrderManagementCake is an Android application designed to help users (likely bakery owners or staff) manage cake orders efficiently. The app provides a streamlined interface to track orders, manage customer details, and keep track of delivery schedules.

## Features (Planned)

*   **Order Tracking:** Create, edit, and view cake orders.
*   **Customer Management:** Maintain a list of customers and their contact information.
*   **Cake Customization Details:** Record specific requirements for each cake (flavor, size, decorations, etc.).
*   **Status Management:** Track the progress of orders (e.g., Pending, Baking, Ready for Delivery, Delivered).
*   **Delivery Schedule:** View upcoming deliveries in a clear list or calendar view.

## Tech Stack

*   **Language:** [Kotlin](https://kotlinlang.org/)
*   **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
*   **Architecture:** Follows modern Android Architecture Components (planned).
*   **Build System:** Gradle (Kotlin DSL)
*   **Minimum SDK:** 24
*   **Target SDK:** 36

## Project Structure

The project follows a feature-based structure where each feature is separated into its own folder named after its main functionality, and each contains its own ViewModel.

```text
app/src/main/java/com/example/ordermanagementcake/
├── data/                   # Data layer
│   ├── local/              # Local data source (Room)
│   │   ├── dao/            # Data Access Objects (Queries)
│   │   ├── entities/       # Database entities (tables)
│   │   └── OrderDatabase.kt # Room database configuration
│   └── repository/         # Data source implementations
├── repository/             # Domain repositories (Business logic)
├── ui/                     # UI Layer (Jetpack Compose)
│   ├── clients/            # Client-related screens and ViewModel
│   ├── components/         # Reusable UI components (buttons, cards, etc.)
│   ├── navigation/         # App navigation logic and routes
│   ├── orders/             # Order-related screens and ViewModel
│   └── theme/              # App styling and theme (Colors, Type, etc.)
└── MainActivity.kt         # Main entry point of the application
```

## Architecture
checkout [Archictecture and File structure](docs/ARCHITECTURE.md) for a more detailed explanation about the architecture 

## Getting Started

### Prerequisites

*   Android Studio Ladybug or newer.
*   JDK 11 or higher.

### Installation

1.  Clone the repository:
    ```bash
    git clone https://github.com/yourusername/OrderManagementCake.git
    ```
2.  Open the project in Android Studio.
3.  Sync the project with Gradle files.
4.  Run the app on an emulator or a physical device.


### CONTRIBUTION

For contribution guidance checkout out [Contribution guidance](docs/CONTRIBUTION.md)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details (if applicable).

---

# OrderManagementCake (Tetum)

Aplicasaun Android báziku ida atu jere pedidu bolu (cake orders), harii ho prátika dezenvolvimentu Android modernu.

## Deskrisaun

OrderManagementCake mak aplicasaun Android ida ne'ebé dezenvolve atu ajuda uza-na'in (hanesan na'in ba padaria ka funsionáriu) atu jere pedidu bolu sira ho efisiente. Aplicasaun ne'e fornese interface ida ne'ebé simples atu kontrola pedidu, jere detallu kliente sira, no kontrola oráriu entrega sira.

## Funsaun sira (Planeadu)

*   **Kontrola Pedidu:** Kria, edita, no haree pedidu bolu sira.
*   **Jere Kliente:** Mantein lista kliente sira no sira-nia informasaun kontaktu.
*   **Detallu Personalizasaun Bolu:** Rejista rekerimentu espesífiku ba bolu ida-idak (sabor, medida, dekorasaun, nst).
*   **Jere Status:** Kontrola progresu pedidu sira (ezemplu: Hein hela, Te'in hela, Prontu atu Entrega, Entrega ona).
*   **Oráriu Entrega:** Haree entrega sira ne'ebé sei mai iha lista ne'ebé klaru ka vizaun kalendáriu.

## Teknolojia ne'ebé Uza

*   **Lian:** [Kotlin](https://kotlinlang.org/)
*   **UI Framework:** [Jetpack Compose](https://developer.android.com/jetpack/compose)
*   **Arkitetura:** Tuir Komponente Arkitetura Android modernu (planeadu).
*   **Sistema Build:** Gradle (Kotlin DSL)
*   **SDK Mínimu:** 24
*   **SDK Alvu:** 36

## Estrutura Projetu

Projetu ne'e tuir estrutura bazeia ba funsaun (feature-based) ne'ebé kada funsaun haketak ba nia pasta rasik ne'ebé hanaran tuir nia funsaun prinsipál, no ida-idak iha nia ViewModel rasik.

```text
app/src/main/java/com/example/ordermanagementcake/
├── data/                   # Kamada dadus
│   ├── local/              # Fonte dadus lokál (Room)
│   │   ├── dao/            # Data Access Objects (DAO: Query sira)
│   │   ├── entities/       # Entidade baze dadus (tabela sira)
│   │   └── OrderDatabase.kt # Konfigurasaun baze dadus Room
│   └── repository/         # Implementasaun fonte dadus sira
├── repository/             # Repozitóriu domíniu (Lójika negósiu)
├── ui/                     # Kamada UI (Jetpack Compose)
│   ├── clients/            # Tela sira ne'ebé relasiona ho kliente no ViewModel
│   ├── components/         # Komponente UI ne'ebé uza fali (butaun, kardaun, nst)
│   ├── navigation/         # Lójika navigasaun no rota sira
│   ├── orders/             # Tela sira ne'ebé relasiona ho pedidu no ViewModel
│   └── theme/              # Estilu no tema aplicasaun (Kór, Tipografia, nst)
└── MainActivity.kt # Pontu entrada prinsipál ba aplicasaun
```

## Arkitetura
haree [Arkitetura no Estrutura File](docs/ARCHITECTURE.md) ba esplikasaun detalladu liu kona-ba arkitetura.

## Hahú Prosesu

### Pre-rekizitu sira

*   Android Studio Ladybug ka foun liu.
*   JDK 11 ka aas liu.

### Instalasaun

1.  Clone repozitóriu:
    ```bash
    git clone https://github.com/yourusername/OrderManagementCake.git
    ```
2.  Loke projetu iha Android Studio.
3.  Sincroniza projetu ho file Gradle sira.
4.  Hala'o aplicasaun iha emulador ka dispozitivu fíziku.


### KONTRIBUISAUN

Ba guia kontribuisaun, haree [Guia Kontribuisaun](docs/CONTRIBUTION.md).

## Lisensa

Projetu ne'e lisensiadu okos Lisensa MIT - haree file [LICENSE](LICENSE) ba detallu sira (se iha).
