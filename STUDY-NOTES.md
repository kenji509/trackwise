# Trackwise Study Notes

## Request flow

```
Client (Postman)
   -> Controller   (receives HTTP, handles errors with @ExceptionHandler)
   -> Service      (business rules, finds related records)
   -> Repository   (talks to the database)
   -> Database     (MySQL, database "trackwise")
```

Example: `POST /quiz/api/postNewQuiz`
1. `QuizController` receives a `QuizEntity` as the JSON body.
2. `QuizService` looks up the teacher by id and sets it on the quiz.
3. `QuizRepository.save` writes it to the `quiz` table.
4. The saved `QuizEntity` goes back through the controller as JSON.

## The four packages

| Package | Real classes | Job |
|---------|--------------|-----|
| `controller` | `SectionController`, `TopicController`, `UserController`, `QuizController`, `QuestionController`, `ChoiceController` | Defines the URLs (`/quiz/api/...`), accepts requests, returns responses, and handles errors |
| `service` | `SectionService`, `TopicService`, `UserService`, `QuizService`, `QuestionService`, `ChoiceService`, `NameErrorResponse` | Business logic: looks up foreign keys, hashes passwords. `NameErrorResponse` is the error body (status, message, timestamp) |
| `repository` | `SectionRepository`, `TopicRepository`, `UserRepository`, `QuizRepository`, `QuestionRepository`, `ChoiceRepository` | Extends `JpaRepository`, gives save, find and delete without writing SQL |
| `entity` | `SectionEntity`, `TopicEntity`, `UserEntity`, `QuizEntity`, `QuestionEntity`, `ChoiceEntity`, `Role` | Java classes mapped to database tables |

There are no `dto` or `exception` packages. Entities are sent and received directly.

## Foreign keys

A quiz belongs to a teacher. In `QuizEntity`, this is a `@ManyToOne` link to `UserEntity`, stored as a `teacher_id` column.

The client sends the teacher as a nested object holding only the id. The service then:
1. Reads the id from the nested object.
2. Looks it up with `findById`. If missing, it throws `NoSuchElementException`.
3. Sets the real, loaded entity on the quiz before saving.

Same pattern elsewhere: `QuestionEntity` points to `QuizEntity` and `TopicEntity`, `ChoiceEntity` points to `QuestionEntity`, `UserEntity` points to `SectionEntity` (optional).

## Passwords

`UserService` hashes the incoming password with `BCryptPasswordEncoder` before saving. In `UserEntity`, `passwordHash` is marked `@JsonIgnore` (with a `@JsonProperty` setter in write-only mode), so a client can send a password but responses never include it.

## Error handling

Each controller has its own `@ExceptionHandler` methods and builds a `NameErrorResponse`:

| Status | Handler | Cause |
|--------|---------|-------|
| 404 Not Found | `handleException(NoSuchElementException)` | An id does not exist, for the record itself or a foreign key |
| 400 Bad Request | `handleException(Exception)` | Anything else: duplicate email, missing password, malformed JSON, database constraint errors |

Spring picks the most specific handler, so `NoSuchElementException` goes to the 404 one and everything else falls to the general one. A handler only covers its own controller, which is why the same two methods are repeated in each controller.

`NameErrorResponse` sits in the `service` package so both the services and controllers can use it.
