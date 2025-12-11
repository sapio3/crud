Student Management System (Spring Boot)

How to run:

1. Ensure you have Java 17 and Maven installed.
2. Create a MySQL database named `student_db` and set the DB username/password in
   src/main/resources/application.properties (currently set to root / YOUR_DB_PASSWORD_HERE).
3. From the project root run:
   mvn clean install
   mvn spring-boot:run

Endpoints:
POST   /api/students
GET    /api/students
GET    /api/students/{id}
PUT    /api/students/{id}
DELETE /api/students/{id}
