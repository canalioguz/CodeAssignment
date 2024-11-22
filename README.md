
## Prerequisites

- **Java 17** or higher
- **Gradle**

## Getting Started

### 1. Build the Project
Run the following command to build the project:
```bash
gradle clean build
```

### 2. Run the Application
Start the application with:
```bash
gradle bootRun
```

### 3. Access the Application
- **APIs**: Use tools like Postman or `curl`.
- **H2 Console**: Visit `http://localhost:8080/h2-console`.
  - **JDBC URL**: `jdbc:h2:mem:testdb`
  - **Username**: `sa`
  - **Password**: (empty)