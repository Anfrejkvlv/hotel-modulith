# Hotel Modulith

A modular hotel management REST API built with Spring Boot and Spring Modulith. The application manages guests, rooms, and reservations while keeping each business capability isolated as an application module. It also demonstrates domain events: creating a reservation publishes a `ReservationCreatedEvent`, which is handled by the notifications module after the transaction commits.

## Features

- Guest CRUD operations with optional email filtering
- Room CRUD operations
- Reservation creation, filtering, updating, and deletion
- Combined room-reservation view for a selected date
- PostgreSQL persistence through Spring Data JPA
- SQL schema and sample data initialization
- Spring Modulith architecture verification and generated PlantUML module documentation
- Event publication and retry support for incomplete event publications
- Spring Boot Actuator support

## Technology stack

- Java 21
- Spring Boot 4.1.0
- Spring Modulith 2.1.0
- Spring Web MVC
- Spring Data JPA / Hibernate
- PostgreSQL
- Maven Wrapper
- JUnit 5, AssertJ, and Spring Modulith test support
- Lombok

## Project structure

```text
.
├── docker-compose.yaml                 # Local PostgreSQL container
├── pom.xml                             # Maven build and dependencies
├── mvnw / mvnw.cmd                      # Maven Wrapper launchers
└── src
    ├── main
    │   ├── java/dev/emma/hotelmodulith
    │   │   ├── HotelModulithApplication.java
    │   │   ├── guests/                  # Guest module: API, application, persistence
    │   │   ├── rooms/                   # Room module: API, application, persistence
    │   │   ├── reservations/            # Reservation module and domain event
    │   │   ├── roomreservations/         # Combined room availability/reservation view
    │   │   └── notifications/            # Event listener and failed-event retry API
    │   └── resources
    │       ├── application.yaml         # Runtime and datasource configuration
    │       ├── schema.sql                # Database schema
    │       └── data.sql                  # Development/sample data
    └── test
        └── java/dev/emma/hotelmodulith
            ├── HotelModulithApplicationTests.java
            └── ArchitectureTest.java   # Verifies Modulith boundaries and writes diagrams
```

Each main business module follows a similar structure:

- Public module contracts and DTOs are kept at the module root.
- HTTP controllers, mappers, application services, and repositories are under `internal`.
- Persistence is implemented with JPA entities and Spring Data repositories.

## Prerequisites

- JDK 21
- Docker and Docker Compose, or an accessible PostgreSQL instance
- A shell capable of running the Maven Wrapper (`mvnw` on Unix-like systems or `mvnw.cmd` on Windows)

## Database configuration

The included `docker-compose.yaml` starts PostgreSQL with:

```text
Database: hmodulith_db
User:     hmodulith
Password: hmodulithapp
Host port: 5434
```

The checked-in `src/main/resources/application.yaml` currently points to `localhost:5432` with a different username and password. Before starting the application with the supplied Compose database, update the datasource settings or provide equivalent Spring datasource overrides, for example:

```bash
export SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5434/hmodulith_db
export SPRING_DATASOURCE_USERNAME=hmodulith
export SPRING_DATASOURCE_PASSWORD=hmodulithapp
```

The application uses `schema.sql` and `data.sql` on startup. Hibernate schema generation is disabled (`ddl-auto: none`) and SQL initialization is enabled.

## Run locally

Start PostgreSQL:

```bash
docker compose up -d
```

Start the application from the repository root:

```bash
./mvnw spring-boot:run
```

On Windows:

```bat
mvnw.cmd spring-boot:run
```

The API is available at:

```text
http://localhost:8080
```

To stop the database:

```bash
docker compose down
```

## Build and test

Run the test suite:

```bash
./mvnw test
```

Create an executable Spring Boot jar:

```bash
./mvnw clean package
```

Run the packaged application:

```bash
java -jar target/hotel-modulith-0.0.1-SNAPSHOT.jar
```

`ArchitectureTest` verifies the application module boundaries and generates PlantUML documentation snippets during the test run.

## REST API

All endpoints use the `/api/v2` prefix.

### Guests

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v2/guests` | List guests; optionally filter with `?emailAddress=` |
| `GET` | `/api/v2/guests/{id}` | Get a guest by ID |
| `POST` | `/api/v2/guests` | Create a guest |
| `PUT` | `/api/v2/guests/{id}` | Update a guest |
| `DELETE` | `/api/v2/guests/{id}` | Delete a guest |

### Rooms

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v2/rooms` | List rooms |
| `GET` | `/api/v2/rooms/{id}` | Get a room by ID |
| `POST` | `/api/v2/rooms` | Create a room |
| `PUT` | `/api/v2/rooms/{id}` | Update a room |
| `DELETE` | `/api/v2/rooms/{id}` | Delete a room |

### Reservations

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v2/reservations` | List reservations |
| `GET` | `/api/v2/reservations?date=2022-08-01` | Filter by date |
| `GET` | `/api/v2/reservations?guestId=1` | Filter by guest |
| `GET` | `/api/v2/reservations?date=2022-08-01&guestId=1` | Filter by date and guest |
| `POST` | `/api/v2/reservations` | Create a reservation |
| `PUT` | `/api/v2/reservations/{id}` | Update a reservation |
| `DELETE` | `/api/v2/reservations/{id}` | Delete a reservation |

Creating a reservation publishes `ReservationCreatedEvent`. `ReservationNotificationListener` consumes the event with `@ApplicationModuleListener` and currently logs a confirmation message.

### Combined room-reservation view

The `roomreservations` module exposes a view that combines room, reservation, and guest information:

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/api/v2/roomReservations?date=2022-08-01` | Get rooms with reservation and guest details |
| `GET` | `/api/v2/roomReservations/rooms` | List rooms |
| `GET` | `/api/v2/roomReservations/reservations` | List/filter reservations |
| `GET` | `/api/v2/roomReservations/guests` | List guests |
| `POST` | `/api/v2/roomReservations/rooms` | Create a room |
| `POST` | `/api/v2/roomReservations/guests` | Create a guest |
| `POST` | `/api/v2/roomReservations/reservations` | Create a reservation |

The combined controller also provides nested get, update, and delete operations for rooms, guests, and reservations.

### Event administration

```text
POST /api/v2/admin/retry/events
```

Resubmits incomplete Spring Modulith event publications.

## Example requests

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

## Architecture

The application is a Spring Modulith monolith rather than a set of independently deployed services. Modules communicate through explicit services and application events while sharing one runtime and database.

The main event flow is:

1. `ReservationController` accepts a reservation request.
2. `ReservationServiceImpl` persists the reservation through `ReservationRepository`.
3. `ReservationServiceImpl` publishes `ReservationCreatedEvent`.
4. `ReservationNotificationListener` handles the event after the transaction commits.
5. Spring Modulith event externalization and incomplete-publication support provide the foundation for durable event handling and retry operations.

## Notes

- The sample data in `src/main/resources/data.sql` is development data and should not be used as production data.
- Replace the datasource credentials in configuration with secrets managed outside source control for deployed environments.
- The current notification listener writes a confirmation to standard output; integrating an email, messaging, or external notification provider would be the next step for production use.
