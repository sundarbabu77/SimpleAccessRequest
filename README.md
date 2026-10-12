# Simple Access Request

A small full-stack app to create and list access requests.

| Layer    | Tech                                   |
|----------|----------------------------------------|
| Frontend | Angular 18 (standalone components)     |
| Backend  | Java 17, Spring Boot 3.3, Spring Data JPA / Hibernate |
| Database | H2 (in-memory)                         |

```
.
├── backend/    Spring Boot API
└── frontend/   Angular UI
```

## Prerequisites

- JDK 17+
- Maven 3.9+
- Node.js 18.19+ (20 LTS recommended) and npm

## Run the backend

```bash
cd backend
mvn spring-boot:run
```

The API starts on <http://localhost:8080>. The H2 console is available at
<http://localhost:8080/h2-console> (JDBC URL `jdbc:h2:mem:accessdb`, user `sa`, empty password).

Run the tests with `mvn test`.

## Run the frontend

```bash
cd frontend
npm install
npm start        # same as: ng serve
```

Open <http://localhost:4200>. During development `ng serve` proxies `/api/*` to
`http://localhost:8080` (see `frontend/proxy.conf.json`), so no CORS setup is needed in the browser.
The backend also allows `http://localhost:4200` via CORS as a fallback
(`app.cors.allowed-origins` in `application.properties`).

## API

### `POST /api/request`

```json
{
  "account": "max_mustermann",
  "entitlement": "auditor",
  "justification": "Need access for audit"
}
```

`201 Created`:

```json
{ "id": 1, "status": "created", "createdAt": "2026-05-08T10:00:00" }
```

`account` and `entitlement` are required (blank values are rejected); `justification` is optional.
Validation failure → `400`:

```json
{
  "status": 400,
  "message": "Validation failed",
  "fieldErrors": { "account": "account is required" },
  "timestamp": "2026-05-08T10:00:00.123"
}
```

### `GET /api/requests`

Returns all requests, newest first, each with `id`, `account`, `entitlement`, `justification`, `status`, `createdAt`.

### Quick check with curl

```bash
curl -X POST localhost:8080/api/request -H 'Content-Type: application/json' \
  -d '{"account":"max_mustermann","entitlement":"auditor","justification":"Need access for audit"}'
curl localhost:8080/api/requests
```

## Design notes

**Backend** (layered, one responsibility per class)

- `controller` – HTTP only: routing, `@Valid`, status codes.
- `service` – use cases and transaction boundaries.
- `repository` – Spring Data JPA.
- `mapper` – converts between entity and DTOs.
- `dto` – request/response records, so the persistence model is never exposed directly.
- `model` – JPA entity + status enum. `id` is auto-generated, `createdAt` is set in `@PrePersist`,
  `status` is always `CREATED` on creation (it is not accepted from the client).
- `exception` – `@RestControllerAdvice` returning one consistent error shape for validation errors,
  malformed JSON, and unexpected failures (details are logged, not leaked to the client).

**Frontend**

- `AccessRequestService` is the only place that talks to the API and converts HTTP errors
  into readable messages (server unreachable, field validation errors, generic failures).
- `AccessRequestFormComponent` – reactive form with client-side validation, loading state,
  and success/error feedback. Emits `created` on success.
- `AccessRequestListComponent` – purely presentational (inputs only).
- `AppComponent` – owns the list state and refreshes it when a request is created.

## Status

**Working**

- Create a request from the form and see it persisted in H2.
- List of all requests under the form, refreshed after each submit.
- Server-side validation (blank/missing `account` and `entitlement`, length limits) and client-side validation.
- API/network errors shown in the UI instead of failing silently.
- Backend integration tests (MockMvc) for create, validation failure, optional justification, and list.

**Not done / what I would improve with more time**

- Frontend unit tests (service with `HttpTestingController`, form component).
- Pagination, sorting and filtering for the list; a "refresh" action.
- `entitlement` as a dropdown fed by a backend endpoint instead of free text.
- Request lifecycle beyond `created` (approve / reject / cancel), with authentication and per-user visibility.
- Persistent database (PostgreSQL) with Flyway migrations instead of `ddl-auto=create-drop`.
- OpenAPI documentation (springdoc), a Dockerfile / docker-compose, and a CI pipeline.
- Maven wrapper committed so no local Maven install is needed.
