# Trackwise

Spring Boot 4.1.2 / Java 17 / Maven REST API.

## Project rules

- Group: `com.csit321g2.bainabai`
- Artifact: `trackwise`
- Base package: `com.csit321g2.bainabai.trackwise`
- Four packages only: `entity`, `repository`, `service`, `controller`
- Database: XAMPP MySQL, database `trackwise`, user `root`, no password
- Do not write any code comments anywhere
- Entity classes are named `XEntity` (`UserEntity`, `QuizEntity`, ...) and are used directly in controllers and services; there are no DTOs
- Build one entity at a time
- Foreign keys use `@ManyToOne` and are resolved by id in the service, throwing `NoSuchElementException` when the id does not exist
- Error handling: `NameErrorResponse` lives in the `service` package; each controller has its own `@ExceptionHandler` methods (`NoSuchElementException` returns 404, any other `Exception` returns 400)
- Passwords are hashed with BCrypt (`spring-security-crypto` only, no full Spring Security); `UserEntity.passwordHash` is `@JsonIgnore` on read so it is never returned

## Endpoints

Each controller is mounted at `/<entity>/api` and has the same method names:

| Entity   | Base path        | Methods |
|----------|------------------|---------|
| Section  | `/section/api`   | `GET /printAuthor`, `POST /postNewSection`, `GET /getAllSections`, `PUT /putSection`, `DELETE /deleteSection/{id}` |
| Topic    | `/topic/api`     | same pattern with `Topic` / `Topics` |
| User     | `/user/api`      | same pattern with `User` / `Users` |
| Quiz     | `/quiz/api`      | same pattern with `Quiz` / `Quizzes` |
| Question | `/question/api`  | same pattern with `Question` / `Questions` |
| Choice   | `/choice/api`    | same pattern with `Choice` / `Choices` |

All six are built; the refactor to the four-package structure is done. Not yet tested in Postman.

## What's left

- Test every endpoint in Postman
- Push to GitHub
- Attempt and Answer entities in a later increment
