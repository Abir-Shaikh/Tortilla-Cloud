# 🌯 Tortilla Cloud

> A Spring Boot 3 web application for designing custom tortillas, placing orders, and managing order history with user authentication and real-time tracking.

**Tortilla Cloud** is a feature-rich e-commerce platform that enables users to build custom tortillas by selecting ingredients, place orders, and track their order history. Built with modern Spring Boot technologies, it provides a secure, scalable foundation for a tortilla ordering service.

---

## 📋 Table of Contents

- [Features](#-features)
- [Tech Stack](#-tech-stack)
- [Project Structure](#-project-structure)
- [Quick Start](#-quick-start)
- [Configuration](#-configuration)
- [API Endpoints](#-api-endpoints)
- [Database](#-database)
- [Architecture](#-architecture)
- [IDE Setup](#-ide-setup)
- [Future Roadmap](#-future-roadmap)
- [Contributing](#-contributing)
- [License](#-license)
- [Support](#-support)

---

## ✨ Features

### Core Functionality
- **User Authentication**: Secure registration & login with BCrypt password hashing
- **Custom Tortilla Designer**: Build tortillas by selecting from ingredient categories
- **Order Management**: Place orders, view order history, and track user-specific orders
- **Ingredient System**: Pre-configured ingredient categories loaded at startup
- **Session Management**: Secure user sessions with Spring Security

### Current Capabilities
1. Public pages: Homepage (`/`), Login (`/login`), Registration (`/register`)
2. Authenticated pages: Tortilla Design (`/design`), Order Management (`/orders/**`)
3. User credential management with BCrypt hashing
4. Ingredient seeding from `src/main/resources/data.sql`
5. Pagination support for order history (configurable page size)

---

## 🛠️ Tech Stack

| Component | Technology | Version | Details |
|---|---|---|---|
| **Language** | Java | 21 | Modern JVM with latest features |
| **Framework** | Spring Boot | 3.2.5 | Production-ready framework |
| **Build Tool** | Maven | Wrapper included | `mvnw` / `mvnw.cmd` |
| **View Engine** | Thymeleaf | Spring managed | Server-side templating |
| **Security** | Spring Security | Spring managed | Form-based authentication |
| **Database** | Spring Data JPA | Spring managed | ORM & data access |
| **Data Store** | H2 Database | Spring managed | In-memory (dev), configurable for prod |
| **Utilities** | Lombok | Spring managed | Boilerplate reduction |
| **Validation** | Spring Validation | Spring managed | Bean validation & constraints |

---

## 📁 Project Structure

```
Tortilla-cloud/
├── .mvn/                              # Maven wrapper configuration
├── src/
│   ├── main/
│   │   ├── java/com/Tortilla_cloud/
│   │   │   ├── configuration/         # App properties & configuration (OrderProps)
│   │   │   ├── controller/            # MVC controllers (routes & handlers)
│   │   │   ├── model/                 # JPA entities & enums
│   │   │   ├── repository/            # Spring Data repositories
│   │   │   ├── security/              # Security config & registration forms
│   │   │   ├── service/               # Business logic (UserDetailsService)
│   │   │   └── TortillaCloudApplication.java
│   │   └── resources/
│   │       ├── templates/             # Thymeleaf HTML pages
│   │       │   ├── home.html
│   │       │   ├── login.html
│   │       │   ├── register.html
│   │       │   ├── design.html
│   │       │   └── orders.html
│   │       ├── application.properties # Server & framework configuration
│   │       └── data.sql               # Initial ingredient seed data
│   └── test/
│       └── java/                      # Unit & integration tests
├── target/                            # Build output
├── pom.xml                            # Maven configuration
├── mvnw / mvnw.cmd                    # Maven wrapper scripts
├── diagram.puml                       # Architecture diagram
├── TortillaCloud_Current_Request_Flow.puml  # Request flow sequence
├── HELP.md                            # Spring Boot help
└── README.md                          # This file
```

---

## 🚀 Quick Start

### Prerequisites

- **JDK 21** or later ([download](https://www.oracle.com/java/technologies/downloads/#java21))
- **Maven 3.8+** (optional — included wrapper scripts work standalone)

### 1️⃣ Clone & Navigate

```bash
cd /path/to/Tortilla-cloud
```

### 2️⃣ Run the Application

```bash
# Windows
.\mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

The app starts at **`http://localhost:8080`**

### 3️⃣ Access the Application

- **Homepage**: [http://localhost:8080](http://localhost:8080)
- **H2 Database Console**: [http://localhost:8080/h2-console](http://localhost:8080/h2-console)
  - **JDBC URL**: `jdbc:h2:mem:tortilladb`
  - **User**: `sa`
  - **Password**: *(leave empty)*

### 4️⃣ Run Tests

```bash
# Windows
.\mvnw.cmd test

# macOS/Linux
./mvnw test
```

### 5️⃣ Build for Production

```bash
# Windows
.\mvnw.cmd clean package

# macOS/Linux
./mvnw clean package
```

Artifact: `target/tortilla-cloud-0.0.1-SNAPSHOT.jar`

---

## ⚙️ Configuration

### Application Properties

Located in `src/main/resources/application.properties`:

```properties
# Server
server.port=8080

# Thymeleaf (Template Engine)
spring.thymeleaf.cache=false              # Disable caching for development
spring.thymeleaf.mode=HTML

# H2 Database
spring.datasource.url=jdbc:h2:mem:tortilladb
spring.datasource.driverClassName=org.h2.Driver
spring.datasource.username=sa
spring.datasource.password=

# JPA & Hibernate
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.jpa.hibernate.ddl-auto=create-drop # Auto-create schema on startup
spring.jpa.show-sql=true                  # Log SQL statements
spring.jpa.properties.hibernate.format_sql=true

# Custom Application Properties
tortilla.orders.page-size=10              # Orders per page
```

### Environment Variables (Future)

For production deployments:
- `DB_URL` – Database connection URL
- `DB_USER` – Database username
- `DB_PASSWORD` – Database password

---

## 🔌 API Endpoints

### Web Pages (Server-Side Rendering)

| Method | Path | Access | Description |
|--------|------|--------|-------------|
| **GET** | `/` | Public | Homepage |
| **GET** | `/login` | Public | Login page |
| **POST** | `/login` | Public | Authenticate user |
| **GET** | `/register` | Public | Registration page |
| **POST** | `/register` | Public | Create new user account |
| **POST** | `/logout` | Authenticated | End user session |
| **GET** | `/design` | Authenticated | Tortilla designer page |
| **POST** | `/design` | Authenticated | Submit tortilla design |
| **GET** | `/orders` | Authenticated | View order history |
| **GET** | `/orders/current` | Authenticated | View/confirm current order |
| **POST** | `/orders/current` | Authenticated | Place/finalize order |

### REST API Endpoints (`/api/`)

#### Ingredients API
| Method | Path | Access | Description |
|--------|------|--------|-------------|
| **GET** | `/api/ingredients` | Public | Get all ingredients |
| **GET** | `/api/ingredients/{id}` | Public | Get ingredient by ID |
| **POST** | `/api/ingredients` | Public | Create new ingredient |

#### Orders API
| Method | Path | Access | Description |
|--------|------|--------|-------------|
| **GET** | `/api/orders` | Authenticated | Get user's orders (paginated) |
| **GET** | `/api/orders/{id}` | Public | Get order by ID |
| **POST** | `/api/orders` | Authenticated | Create new order |

#### Tortillas API
| Method | Path | Access | Description |
|--------|------|--------|-------------|
| **GET** | `/api/tortillas` | Public | Get all tortillas |
| **GET** | `/api/tortillas/{id}` | Public | Get tortilla by ID |
| **POST** | `/api/tortillas` | Public | Create new tortilla |
| **PUT** | `/api/tortillas/{id}` | Public | Update existing tortilla |
| **DELETE** | `/api/tortillas/{id}` | Public | Delete tortilla |

### CORS Configuration
All REST API endpoints have **CORS enabled** (`@CrossOrigin(origins = "*")`) for cross-origin requests.

---

## 💾 Database

### Technology: H2 (In-Memory)
- **Development**: Fast, zero-config, auto-cleanup on restart
- **Production**: Swappable to PostgreSQL/MySQL via `pom.xml`

### Schema
**Auto-generated** by Hibernate (`ddl-auto=create-drop`):
- `USER` – User accounts with BCrypt-hashed passwords
- `INGREDIENT` – Available ingredients (seeded from `data.sql`)
- `TORTILLA_ORDER` – Order records linked to users
- `ORDER_ITEM` – Line items within orders

### Seed Data
`src/main/resources/data.sql` pre-loads ingredient categories (proteins, vegetables, sauces, etc.)

---

## 🏗️ Architecture

### Layered Architecture

```
┌─────────────────────────────────────┐
│         Thymeleaf Templates         │ ← UI Layer
├─────────────────────────────────────┤
│          Spring Controllers         │ ← Routing & Request Handling
├─────────────────────────────────────┤
│         Service Layer               │ ← Business Logic
├─────────────────────────────────────┤
│      Spring Data Repositories       │ ← Data Access Layer
├─────────────────────────────────────┤
│      H2 Database / ORM (JPA)        │ ← Persistence Layer
└─────────────────────────────────────┘
```

### Security Flow
1. User submits login/register form
2. Spring Security validates credentials
3. BCrypt hashing for password comparison
4. Session token created for authenticated requests
5. Spring Security filters enforce authorization on protected endpoints

### Diagrams
- **`TortillaCloud_Current_Request_Flow.puml`** – Sequence diagram (user interactions)
- **`diagram.puml`** – Component/architecture diagram

Render with PlantUML plugins or CLI:
```bash
plantuml TortillaCloud_Current_Request_Flow.puml -o diagrams/
```

---

## 🛠️ IDE Setup

### IntelliJ IDEA

**Recommended Plugins:**
1. [Lombok](https://plugins.jetbrains.com/plugin/6317-lombok) – Annotation processing
2. [PlantUML Integration](https://plugins.jetbrains.com/plugin/7017-plantuml-integration) – Diagram editing
3. [Spring Assistant](https://plugins.jetbrains.com/plugin/16862-spring-assistant) – Spring configuration hints

**Setup:**
- File → Project Structure → SDK → Select JDK 21
- Enable Annotation Processing: Settings → Build → Compiler → Annotation Processors → Enable

### VS Code

**Recommended Extensions:**
1. [Extension Pack for Java](https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack) – Full Java support
2. [Spring Boot Extension Pack](https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack) – Spring Boot tools
3. [Lombok Annotations Support](https://marketplace.visualstudio.com/items?itemName=GabrielBB.vscode-lombok) – Lombok recognition
4. [PlantUML](https://marketplace.visualstudio.com/items?itemName=jebbs.plantuml) – Diagram support

**Settings:**
```json
{
  "java.configuration.runtimes": [
    {
      "name": "JavaSE-21",
      "path": "/path/to/jdk-21"
    }
  ]
}
```

### Eclipse

**Plugins:**
- [Lombok](https://projectlombok.org/setup/eclipse)
- [Spring Tools 4](https://spring.io/tools)
- [PlantUML](https://plantuml.com/eclipse)

---

## 🗓️ Future Roadmap

### Short-term
- [ ] Add shopping cart with multi-step checkout
- [ ] Implement payment processing (Stripe/PayPal integration)
- [ ] Add order status tracking (pending, processing, ready, completed)

### Mid-term
- [ ] Role-based authorization (Admin/User dashboards)
- [ ] User profile management & password reset
- [ ] REST API layer (`/api/...`) for mobile apps
- [ ] Enhanced validation & localized error messages
- [ ] Email notifications for order status

### Long-term
- [ ] Production database profiles (PostgreSQL/MySQL)
- [ ] Database migrations (Flyway/Liquibase)
- [ ] Comprehensive test coverage (unit, integration, E2E)
- [ ] CI/CD pipeline (GitHub Actions, automated security checks)
- [ ] Analytics dashboard & reporting
- [ ] Microservices architecture (Order Service, Payment Service, Notification Service)
- [ ] Real-time order updates (WebSocket)

---

## 📝 Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature`
3. Commit changes: `git commit -m "feat: describe your change"`
4. Push to branch: `git push origin feature/your-feature`
5. Open a Pull Request

---

## 📄 License

See [LICENSE](LICENSE) file for details.

---

## 📞 Support

For issues, questions, or suggestions:
- Open an [Issue](../../issues)
- Check [HELP.md](HELP.md) for Spring Boot documentation

---

**Happy Tortilla Building! 🌯**

