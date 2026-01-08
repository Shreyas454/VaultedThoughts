# VaultedThoughts

A secure, feature-rich journaling application built with Spring Boot that combines personal reflection with intelligent insights. VaultedThoughts provides end-to-end encrypted journal entries, automated sentiment analysis, and personalized weekly reports.

## Features

- **Secure Authentication**: JWT-based authentication with BCrypt password encryption
- **Role-Based Access Control**: Admin and User roles with granular permissions
- **Journal Management**: Full CRUD operations for journal entries with MongoDB persistence
- **Sentiment Analysis**: Automated weekly analysis of journal entries
- **Email Notifications**: Scheduled sentiment reports delivered to opted-in users
- **Weather Integration**: Context-aware weather data with Redis caching
- **Performance Optimization**: Redis caching layer for frequently accessed data
- **Transaction Management**: ACID-compliant operations for data consistency
- **Security Filters**: Custom JWT filter for request authentication

## Tech Stack

- **Backend**: Spring Boot 3.4.1
- **Language**: Java 21
- **Database**: MongoDB
- **Caching**: Redis
- **Authentication**: JWT (JSON Web Tokens)
- **Security**: Spring Security with BCrypt
- **Build Tool**: Maven
- **Additional Libraries**: Lombok, Jackson

## Prerequisites

Before running this application, ensure you have:

- Java 21 or higher
- Maven 3.6+
- MongoDB (running locally or remote instance)
- Redis server
- SMTP server credentials (for email notifications)

## Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/Shreyas454/VaultedThoughts.git
cd journalApp
```

### 2. Configure Application Properties

Create `src/main/resources/application.properties` with the following:

```properties
# MongoDB Configuration
spring.data.mongodb.uri=mongodb://localhost:27017/journalDB
spring.data.mongodb.database=journalDB

# Redis Configuration
spring.data.redis.host=localhost
spring.data.redis.port=6379

# Weather API Configuration
weather.api.key=YOUR_WEATHER_API_KEY

# Email Configuration
spring.mail.host=smtp.gmail.com
spring.mail.port=587
spring.mail.username=your-email@gmail.com
spring.mail.password=your-app-password
spring.mail.properties.mail.smtp.auth=true
spring.mail.properties.mail.smtp.starttls.enable=true

# JWT Configuration (Optional - defaults will be used if not specified)
jwt.secret=your-secret-key
jwt.expiration=86400000

# Server Configuration
server.port=8080
```

### 3. Build the Project

```bash
mvn clean install
```

### 4. Run the Application

```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## API Endpoints

### Public Endpoints

| Method | Endpoint | Description |
|--------|----------|-------------|
| POST | `/public/signup` | Register a new user |
| POST | `/public/login` | Authenticate and receive JWT token |

### User Endpoints (Authenticated)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/journalV2` | Get all journal entries for authenticated user |
| POST | `/journalV2` | Create a new journal entry |
| GET | `/journalV2/{id}` | Get specific journal entry by ID |
| PUT | `/journalV2/{id}` | Update journal entry |
| DELETE | `/journalV2/{id}` | Delete journal entry |
| GET | `/user/profile` | Get user profile |
| PUT | `/user/profile` | Update user profile |
| DELETE | `/user/profile` | Delete user account |

### Admin Endpoints (Admin Role Required)

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/admin/users` | Get all users |
| POST | `/admin/create-admin` | Create admin user |

## Authentication Flow

1. **Sign Up**: Create an account via `/public/signup`
2. **Login**: Authenticate via `/public/login` to receive a JWT token
3. **Use Token**: Include the JWT in the `Authorization` header for subsequent requests:
   ```
   Authorization: Bearer <your-jwt-token>
   ```

## Project Structure

```
journalApp/
├── src/
│   ├── main/
│   │   ├── java/com/edigestjournal/journalApp/
│   │   │   ├── config/          # Security and Redis configuration
│   │   │   ├── controller/      # REST API controllers
│   │   │   ├── entity/          # MongoDB entities
│   │   │   ├── filters/         # JWT authentication filter
│   │   │   ├── repository/      # Data access layer
│   │   │   ├── service/         # Business logic
│   │   │   ├── utils/           # Utility classes (JWT)
│   │   │   ├── cache/           # Application caching
│   │   │   ├── Scheduller/      # Scheduled tasks
│   │   │   └── api/response/    # API response models
│   │   └── resources/
│   │       └── application.properties
│   └── test/                    # Unit and integration tests
├── pom.xml
└── README.md
```

## Scheduled Tasks

The application includes automated scheduled jobs:

- **Weekly Sentiment Analysis**: Runs every Thursday at 6:05 PM
  - Analyzes journal entries from the past 7 days
  - Generates sentiment report
  - Emails report to users who have opted in

## Configuration

### MongoDB Collections

- `users`: User accounts and authentication data
- `journal_entries`: User journal entries
- `config_journal_app`: Application configuration

### Redis Cache

- Weather API responses (TTL: 5 minutes)
- Application configuration cache

## Testing

Run the test suite:

```bash
mvn test
```

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License.

## Author

**Shreyas**
- GitHub: [@Shreyas454](https://github.com/Shreyas454)

## Acknowledgments

- Spring Boot team for the excellent framework
- MongoDB and Redis communities
- Weather API providers

---

**Note**: This is a learning project demonstrating Spring Boot best practices, security implementations, and microservices patterns.
