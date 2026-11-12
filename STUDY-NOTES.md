# Trackwise Study Notes

## Request flow

```
Client (Postman)
   -> Controller   (receives HTTP, validates the DTO)
   -> Service      (business rules, finds related records)
   -> Repository   (talks to the database)
   -> Database     (MySQL, database "trackwise")
```

The response travels back the same way, as a DTO, never as an entity.

Example: `POST /api/quizzes`
1. `QuizController.create` receives a `QuizRequest`.
2. `QuizService.create` checks the teacher and builds a `Quiz`.
3. `QuizRepository.saveAndFlush` writes it to the `quiz` table.
4. `QuizService` turns the saved `Quiz` into a `QuizResponse`.
5. `QuizController` returns it with status 201.

## What each layer does

| Layer | Package | Real classes | Job |
|-------|---------|--------------|-----|
| Controller | `controller` | `SectionController`, `TopicController`, `UserController`, `QuizController`, `QuestionController`, `ChoiceController` | Defines the URLs (`/api/quizzes`, ...), accepts requests, returns responses and status codes |
| Service | `service` | `SectionService`, `TopicService`, `UserService`, `QuizService`, `QuestionService`, `ChoiceService` | Business logic: looks up foreign keys, hashes passwords, converts entity to DTO |
| Repository | `repository` | `SectionRepository`, `TopicRepository`, `UserRepository`, `QuizRepository`, `QuestionRepository`, `ChoiceRepository` | Extends `JpaRepository`, gives save, find and delete without writing SQL |
| Entity | `entity` | `Section`, `Topic`, `User`, `Quiz`, `Question`, `Choice`, `Role` | Java classes mapped to database tables |
| DTO | `dto` | `QuizRequest`, `QuizResponse`, and the same pair for each entity, plus `ErrorResponse` | Shape of data going in and out of the API |
| Exception | `exception` | `ResourceNotFoundException`, `BadRequestException`, `DuplicateResourceException`, `GlobalExceptionHandler` | Turns errors into proper HTTP responses |

## How Quiz uses a foreign key

A quiz belongs to a teacher. In `Quiz`, this is a `@ManyToOne` link to `User`:

- The `quiz` table has a `teacher_id` column pointing to `users.user_id`.
- It is `LAZY`, so the teacher is only loaded from the database when needed.
- The client never sends a `User` object, only a number: `QuizRequest.teacherId`.

What `QuizService.apply` does with that number:
1. Looks up the user with `UserRepository.findById`. If missing, it throws `ResourceNotFoundException` (404).
2. Checks the role is `TEACHER`. If not, it throws `BadRequestException` (400).
3. Sets that `User` on the `Quiz`.

`QuizResponse` sends back only `teacherId`, not the whole user.

Same pattern elsewhere: `Question` points to `Quiz` and `Topic`, `Choice` points to `Question`, `User` points to `Section` (optional).

Cascade: `Quiz` has `cascade = REMOVE` on its questions, and `Question` has it on its choices. Deleting a quiz deletes its questions, which deletes their choices.

## DTOs and validation

An entity is the database row. A DTO is what the API accepts or returns. Both are Java `record` types.

- Why DTOs: the API never exposes entities. For example, `UserResponse` has no password, while `User` has `passwordHash`.
- `XRequest` is the input, for example `QuizRequest(teacherId, title, description, timeLimitMin)`.
- `XResponse` is the output, for example `QuizResponse(quizId, teacherId, title, description, timeLimitMin, createdAt)`.

Validation uses Jakarta Validation annotations on the request fields:

| Annotation | Meaning | Used in |
|------------|---------|---------|
| `@NotBlank` | Not null, not empty | `title`, `name`, `email` |
| `@NotNull` | Must be present | `teacherId`, `role`, `timeLimitMin` |
| `@Size` | Length limit | `name` (max 100), `password` (8 to 100) |
| `@Email` | Valid email format | `UserRequest.email` |
| `@Positive` | Greater than zero | `timeLimitMin` |

These only run because the controller parameter has `@Valid`: `@Valid @RequestBody QuizRequest request`. If a rule fails, Spring throws `MethodArgumentNotValidException` before the service is called.

## Where 404, 400 and 409 come from

All are turned into an `ErrorResponse` by `GlobalExceptionHandler` (`@RestControllerAdvice`).

| Status | Cause | Thrown by |
|--------|-------|-----------|
| 404 Not Found | Id does not exist, for the record itself or a foreign key | `ResourceNotFoundException` in the services (`getOrThrow`, `findById(...).orElseThrow`) |
| 400 Bad Request | Failed `@Valid` rules, malformed JSON, or the quiz owner is not a TEACHER | `MethodArgumentNotValidException`, `HttpMessageNotReadableException`, `BadRequestException` |
| 409 Conflict | Duplicate unique value (section name, topic name, email) or deleting a record that other rows still reference | `DuplicateResourceException` (email check in `UserService`), `DataIntegrityViolationException` from the database |

Why `saveAndFlush` and `repository.flush()` in the services: they force the database to run the statement right away, so any constraint error is thrown inside the service call and gets handled as a 409, instead of surfacing later.
