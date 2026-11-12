# Trackwise

Spring Boot 4.1.2 / Java 17 / Maven REST API.

## Project rules

- Group: `com.csit321g2.bainabai`
- Artifact: `trackwise`
- Base package: `com.csit321g2.bainabai.trackwise`
- Layers: `entity`, `repository`, `service`, `controller`, `dto`, `exception`
- Database: XAMPP MySQL, database `trackwise`, user `root`, no password
- Do not write any code comments anywhere
- Controllers never return entities; use DTO records named `XRequest` / `XResponse`
- Build one entity at a time
- Foreign keys use `@ManyToOne(fetch = LAZY)` and are resolved by id in the service, throwing `ResourceNotFoundException` when the id does not exist
- Errors go through `GlobalExceptionHandler`: 404 not found, 400 validation, 409 duplicate or data integrity
- Passwords are hashed with BCrypt (`spring-security-crypto` only, no full Spring Security); `UserResponse` never includes the password

## Entity status

| Entity   | Endpoint          | Status                          |
|----------|-------------------|---------------------------------|
| Section  | `/api/sections`   | Built, not yet tested in Postman |
| Topic    | `/api/topics`     | Built, not yet tested in Postman |
| User     | `/api/users`      | Built, not yet tested in Postman |
| Quiz     | `/api/quizzes`    | Built, not yet tested in Postman |
| Question | `/api/questions`  | Built, not yet tested in Postman |
| Choice   | `/api/choices`    | Built, not yet tested in Postman |

Each entity has POST (201), GET all, GET by id, PUT and DELETE (204).

## What's left

- Test every endpoint in Postman
- Push to GitHub
- Attempt and Answer entities in a later increment
