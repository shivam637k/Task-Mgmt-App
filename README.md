DESCRIPTION:
  To create a task management application where users can register and log in. The authenticated user will be allowed to access the JWT-based API routes. This will enable them to access and modify their tasks.


OBJECTIVE:
Build a secure Task Management REST API where:
  - Users can register and log in
  - Authenticated users can access, manage, and modify their own tasks only
  - A particular user can't access or modify tasks of other users


TECHNOLOGIES USED:
 - Java
 - Spring Boot
 - MySQL
 - JWT-based authentication
 - JPA (Hibernate)
 - Postman


SECURITY REQUIREMENTS (auth):
  - All task-related APIs should be protected with JWT-based authentication
  - To use BCrypt to store the passwords safely
  - JWT must validate every protected request
  - Users must not be able to access other users' tasks
