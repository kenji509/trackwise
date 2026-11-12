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
- Error handling: `NameErrorResponse` lives in the `service` package; each controller has its own `@ExceptionHandler` methods (a missing id in `getX(int id)` or a foreign key throws `NoSuchElementException`, which returns 404, any other `Exception` returns 400)
- Passwords are hashed with BCrypt (`spring-security-crypto` only, no full Spring Security); `UserEntity.passwordHash` is `@JsonProperty(access = WRITE_ONLY)` so it is accepted on input but never returned

## Endpoints

Each controller is mounted at `/<entity>/api` and has the same method names:

| Entity   | Base path        | Methods |
|----------|------------------|---------|
| Section  | `/section/api`   | `GET /printAuthor`, `POST /postNewSection`, `GET /getAllSections`, `GET /getSection/{id}`, `PUT /putSection`, `DELETE /deleteSection/{id}` |
| Topic    | `/topic/api`     | same pattern with `Topic` / `Topics`, including `GET /getTopic/{id}` |
| User     | `/user/api`      | same pattern with `User` / `Users`, including `GET /getUser/{id}` |
| Quiz     | `/quiz/api`      | same pattern with `Quiz` / `Quizzes`, including `GET /getQuiz/{id}` |
| Question | `/question/api`  | same pattern with `Question` / `Questions`, including `GET /getQuestion/{id}` |
| Choice   | `/choice/api`    | same pattern with `Choice` / `Choices`, including `GET /getChoice/{id}` |

All six are built; the refactor to the four-package structure is done. Not yet tested in Postman.

## What's left

- Test every endpoint in Postman
- Push to GitHub
- Attempt and Answer entities in a later increment
