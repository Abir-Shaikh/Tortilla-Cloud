# Tortilla Cloud

Tortilla Cloud is a Spring Boot 3 web app for building custom tortillas, placing orders, and viewing order history.  
It uses server-side rendering with Thymeleaf, form-based authentication with Spring Security, and H2 in-memory storage with JPA.

## Tech Stack and Versions

| Technology | Version | Where defined |
|---|---|---|
| Java | 21 | `pom.xml` (`<java.version>21</java.version>`) |
| Spring Boot | 3.2.5 | `pom.xml` parent `spring-boot-starter-parent` |
| Maven Wrapper | Project-managed | `mvnw`, `mvnw.cmd` |
| Thymeleaf | Managed by Spring Boot 3.2.5 | `spring-boot-starter-thymeleaf` |
| Spring Security | Managed by Spring Boot 3.2.5 | `spring-boot-starter-security` |
| Spring Data JPA | Managed by Spring Boot 3.2.5 | `spring-boot-starter-data-jpa` |
| H2 Database | Managed by Spring Boot 3.2.5 | `com.h2database:h2` |
| Lombok | Managed by Spring Boot 3.2.5 | `org.projectlombok:lombok` |
| Validation | Managed by Spring Boot 3.2.5 | `spring-boot-starter-validation` |

## What This App Currently Does

1. Public pages: `/`, `/login`, `/register`
2. Auth-only pages: `/design`, `/orders/**`
3. Register users and store credentials with BCrypt hashing
4. Build tortilla designs using ingredient categories
5. Place orders and view user-specific order history
6. Seed ingredients from `src/main/resources/data.sql`

## Project Structure

```text
.
├── src/main/java/com/Tortilla_cloud
│   ├── configuration/        # Config properties (OrderProps)
│   ├── controller/           # MVC controllers
│   ├── model/                # JPA entities + enum
│   ├── repository/           # Spring Data repositories
│   ├── security/             # Security config + registration form model
│   └── service/              # UserDetailsService implementation
├── src/main/resources
│   ├── templates/            # Thymeleaf pages (home, login, design, orders...)
│   ├── application.properties
│   └── data.sql              # Startup seed data
├── src/test/java             # Tests
├── diagram.puml
└── TortillaCloud_Current_Request_Flow.puml
```

## Quick Start

### Prerequisites

1. JDK 21
2. Maven (optional if using wrapper scripts)

### Run

```bash
# Windows
.\mvnw.cmd spring-boot:run

# macOS/Linux
./mvnw spring-boot:run
```

App URL: `http://localhost:8080`

H2 Console: `http://localhost:8080/h2-console`  
JDBC URL: `jdbc:h2:mem:tortilladb`  
User: `sa`  
Password: *(empty)*

### Test

```bash
# Windows
.\mvnw.cmd test

# macOS/Linux
./mvnw test
```

## Configuration Notes

Configured in `src/main/resources/application.properties`:

- `server.port=8080`
- Thymeleaf cache disabled for development
- H2 in-memory datasource enabled
- `spring.jpa.hibernate.ddl-auto=create-drop`
- SQL logging enabled (`spring.jpa.show-sql=true`)
- Order page size: `tortilla.orders.page-size=10`

## IDE Plugins and Tools (based on this codebase)

### Recommended for IntelliJ IDEA

1. Lombok plugin: https://plugins.jetbrains.com/plugin/6317-lombok
2. PlantUML Integration plugin: https://plugins.jetbrains.com/plugin/7017-plantuml-integration

### Recommended for VS Code

1. Extension Pack for Java: https://marketplace.visualstudio.com/items?itemName=vscjava.vscode-java-pack
2. Spring Boot Extension Pack: https://marketplace.visualstudio.com/items?itemName=vmware.vscode-boot-dev-pack
3. Lombok Annotations Support for VS Code: https://marketplace.visualstudio.com/items?itemName=GabrielBB.vscode-lombok
4. PlantUML extension: https://marketplace.visualstudio.com/items?itemName=jebbs.plantuml

### Build Plugin in this project

- Spring Boot Maven Plugin (`spring-boot-maven-plugin`) is configured in `pom.xml`.

## Main Endpoints

| Method | Path | Access |
|---|---|---|
| GET | `/` | Public |
| GET | `/login` | Public |
| GET/POST | `/register` | Public |
| GET/POST | `/design` | Authenticated |
| GET | `/orders` | Authenticated |
| GET/POST | `/orders/current` | Authenticated |
| POST | `/logout` | Authenticated |

## Diagrams

- `TortillaCloud_Current_Request_Flow.puml` -> request/response sequence flow
- `diagram.puml` -> architecture overview

Render with any PlantUML plugin or with the PlantUML CLI.

## Future Improvements

1. Add cart + multi-step checkout before final order submission
2. Add payment integration (e.g., Stripe) with webhook handling
3. Add role-based authorization (admin/user) and admin dashboard
4. Add profile management and password reset flows
5. Add API layer (`/api/...`) for mobile/frontend clients
6. Improve validation/error UX and localized messages
7. Add persistent database profiles (PostgreSQL/MySQL) for production
8. Add Flyway/Liquibase migrations
9. Expand automated test coverage (controller, repository, security flows)
10. Add CI pipeline (build, test, security checks, diagram export)

