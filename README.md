# Hotel Modulith

A modular hotel management REST API built with **Java 21, Spring Boot and Spring Modulith**.

The project models several hotel-related business capabilities — guests, rooms, reservations and notifications — as independent application modules inside a single deployable application.

The main goal is to explore how a modular monolith can keep clear business boundaries while remaining simpler to run than a distributed microservices architecture.

## What this project demonstrates

The application provides:

* Guest, room and reservation management
* Filtering reservations by date and guest
* A combined room/reservation view
* PostgreSQL persistence with Spring Data JPA / Hibernate
* Explicit application module boundaries with Spring Modulith
* Architecture verification and generated PlantUML documentation
* Application events with `ReservationCreatedEvent`
* Retry support for incomplete event publications
* Spring Boot Actuator for application monitoring

## Tech stack

| Area                 | Technologies                                   |
| -------------------- | ---------------------------------------------- |
| Language             | Java 21                                        |
| Backend              | Spring Boot 4.1.0, Spring Web MVC              |
| Modular architecture | Spring Modulith 2.1.0                          |
| Persistence          | Spring Data JPA, Hibernate                     |
| Database             | PostgreSQL                                     |
| Build                | Maven Wrapper                                  |
| Testing              | JUnit 5, AssertJ, Spring Modulith test support |
| Utilities            | Lombok, Spring Boot Actuator                   |

## Architecture

The application is a **Spring Modulith monolith** rather than a collection of independently deployed services.

Each business capability is isolated as an application module with its own internal implementation.

```text
hotelmodulith
├── guests
├── rooms
├── reservations
├── roomreservations
└── notifications
```

A typical module follows this structure:

```text
module/
├── public contracts / DTOs
└── internal/
    ├── controller
    ├── application service
    ├── mapper
    └── persistence
```

The idea is to keep the public contract of a module small while keeping implementation details inside the module.

### Main modules

**`guests`**
Handles guest management and optional email-based filtering.

**`rooms`**
Handles room management.

**`reservations`**
Handles reservation creation, filtering, update and deletion. It also publishes a `ReservationCreatedEvent`.

**`roomreservations`**
Provides combined views involving rooms, reservations and guests.

**`notifications`**
Consumes reservation events and currently logs a confirmation message. It also exposes an administrative endpoint for retrying incomplete event publications.

## Application events

When a reservation is created, the reservation module publishes:

```java
ReservationCreatedEvent
```

The notification module consumes the event through:

```java
@ApplicationModuleListener
```

The main flow is:

```text
ReservationController
        │
        ▼
ReservationService
        │
        ├── Save reservation
        │
        └── Publish ReservationCreatedEvent
                    │
                    ▼
        ReservationNotificationListener
```

The listener handles the event after the transaction commits.

The project also includes support for retrying incomplete Spring Modulith event publications:

```text
POST /api/v2/admin/retry/events
```

The current notification implementation only writes a confirmation message to standard output. An email, messaging or external notification provider could be added later.

## Architecture verification

The project includes an `ArchitectureTest` that verifies the application's module boundaries.

This is important because the architecture should not depend only on package conventions or developer discipline.

The test also generates PlantUML documentation snippets describing the module relationships.

Run:

```bash
./mvnw test
```

The generated diagrams can then be used to inspect the module structure.

## Project structure

```text
.
├── docker-compose.yaml
├── pom.xml
├── mvnw
├── mvnw.cmd
└── src
    ├── main
    │   ├── java/dev/emma/hotelmodulith
    │   │   ├── HotelModulithApplication.java
    │   │   ├── guests/
    │   │   ├── rooms/
    │   │   ├── reservations/
    │   │   ├── roomreservations/
    │   │   └── notifications/
    │   └── resources
    │       ├── application.yaml
    │       ├── schema.sql
    │       └── data.sql
    └── test
        └── java/dev/emma/hotelmodulith
            ├── HotelModulithApplicationTests.java
            └── ArchitectureTest.java
```

## Running locally

### Prerequisites

* JDK 21
* Docker and Docker Compose
* A shell capable of running the Maven Wrapper

### 1. Start PostgreSQL

```bash
docker compose up -d
```

The provided Compose configuration starts:

```text
Database:   hmodulith_db
User:       hmodulith
Password:   hmodulithapp
Port:       5434
```

The default `application.yaml` uses another datasource configuration, so either update it or provide the datasource values through environment variables:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5434/hmodulith_db
export SPRING_DATASOURCE_USERNAME=hmodulith
export SPRING_DATASOURCE_PASSWORD=hmodulithapp
```

The application uses `schema.sql` and `data.sql` during startup.

### 2. Start the application

Linux / macOS:

```bash
./mvnw spring-boot:run
```

Windows:

```bat
mvnw.cmd spring-boot:run
```

The API starts on:

```text
http://localhost:8080
```

### 3. Stop PostgreSQL

```bash
docker compose down
```

## Testing and packaging

Run the tests:

```bash
./mvnw test
```

Build the application:

```bash
./mvnw clean package
```

Run the generated JAR:

```bash
java -jar target/hotel-modulith-0.0.1-SNAPSHOT.jar
```

## REST API

All endpoints use the `/api/v2` prefix.

### Guests

| Method | Endpoint              | Description    |
| ------ | --------------------- | -------------- |
| GET    | `/api/v2/guests`      | List guests    |
| GET    | `/api/v2/guests/{id}` | Get a guest    |
| POST   | `/api/v2/guests`      | Create a guest |
| PUT    | `/api/v2/guests/{id}` | Update a guest |
| DELETE | `/api/v2/guests/{id}` | Delete a guest |

Optional filtering:

```text
GET /api/v2/guests?emailAddress=ada@example.com
```

### Rooms

| Method | Endpoint             | Description   |
| ------ | -------------------- | ------------- |
| GET    | `/api/v2/rooms`      | List rooms    |
| GET    | `/api/v2/rooms/{id}` | Get a room    |
| POST   | `/api/v2/rooms`      | Create a room |
| PUT    | `/api/v2/rooms/{id}` | Update a room |
| DELETE | `/api/v2/rooms/{id}` | Delete a room |

### Reservations

| Method | Endpoint                               | Description          |
| ------ | -------------------------------------- | -------------------- |
| GET    | `/api/v2/reservations`                 | List reservations    |
| GET    | `/api/v2/reservations?date=2022-08-01` | Filter by date       |
| GET    | `/api/v2/reservations?guestId=1`       | Filter by guest      |
| POST   | `/api/v2/reservations`                 | Create a reservation |
| PUT    | `/api/v2/reservations/{id}`            | Update a reservation |
| DELETE | `/api/v2/reservations/{id}`            | Delete a reservation |

### Combined room/reservation view

```text
GET /api/v2/roomReservations?date=2022-08-01
```

This module combines room, reservation and guest information into a single view.

It also exposes additional endpoints for working with rooms, guests and reservations.

## Example request

Create a guest:

```bash
curl -X POST http://localhost:8080/api/v2/guests \
  -H 'Content-Type: application/json' \
  -d '{
    "firstName": "Ada",
    "lastName": "Lovelace",
    "emailAddress": "ada@example.com",
    "address": "1 Analytical Engine Way",
    "country": "United Kingdom",
    "state": "",
    "phoneNumber": "+44-123-456-789"
  }'
```

List reservations for a date:

```bash
curl 'http://localhost:8080/api/v2/reservations?date=2022-08-01'
```

## Development notes

The project includes development/sample data in:

```text
src/main/resources/data.sql
```

This data should not be used in a production environment.

For deployed environments, datasource credentials should be provided through external configuration or a secret management solution rather than committed to the repository.

## Possible next steps

Some natural extensions for the project would be:

* Replace the console notification with an email or messaging provider
* Add more complete business validation
* Expand integration and API tests
* Add authentication and authorization
* Improve observability around event processing
* Add more explicit module-level architecture tests
* Document architectural decisions as the project evolves

---

## Why this project?

The project is mainly an exploration of **modular architecture with Spring Modulith**:

* keeping business capabilities isolated;
* making module boundaries explicit and testable;
* using application events where loose coupling is useful;
* keeping the operational simplicity of a single application and database.

The goal is not to claim that a modular monolith is always better than microservices, but to understand the trade-offs and when each approach makes sense.
