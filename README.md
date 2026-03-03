# Ocean View Resort Reservation System

A web-based hotel reservation management system built with Java EE, JSP, Servlets, and PostgreSQL.

## Features

- User Authentication (Login/Logout)
- Room Management
- Reservation Management (Add, View, Cancel)
- Billing System
- Dashboard Overview
- Staff Management

## Technology Stack

- **Backend**: Java 11, Servlets, JSP
- **Database**: PostgreSQL
- **Build Tool**: Apache Maven 3.9+
- **Application Server**: Apache Tomcat 9.0+
- **Testing**: JUnit 4, Mockito

## Prerequisites

Before running this application, ensure you have the following installed:

1. **Java Development Kit (JDK) 11 or higher**
   - Download: https://adoptium.net/

2. **Apache Maven 3.9+**
   - Download: https://maven.apache.org/download.cgi

3. **PostgreSQL Database**
   - Download: https://www.postgresql.org/download/

4. **Apache Tomcat 9.0+**
   - Download: https://tomcat.apache.org/download-90.cgi

## Installation & Setup

### 1. Clone or Download the Repository

```bash
git clone https://github.com/medawatthe/OceanViewResort.git
cd OceanViewResort
```

Or download the ZIP file and extract it.

### 2. Database Setup

**Step 1:** Start PostgreSQL and create the database

```bash
psql -U postgres
```

**Step 2:** Create the database

```sql
CREATE DATABASE ocean_view_resort;
\q
```

**Step 3:** Run the schema file

```bash
psql -U postgres -d ocean_view_resort -f database/postgresql_schema.sql
```

**Step 4:** Update database credentials

Edit the `.env` file in the project root:

```properties
DB_HOST=localhost
DB_PORT=5432
DB_NAME=ocean_view_resort
DB_USER=postgres
DB_PASSWORD=your_password_here
DB_SSL=false
```

### 3. Build the Project

Navigate to the project directory and build using Maven:

```bash
mvn clean package
```

This will create a WAR file in the `target` directory: `OceanViewResort.war`

### 4. Deploy to Tomcat

**Option A: Automatic Deployment**

Copy the WAR file to Tomcat's webapps directory:

```bash
cp target/OceanViewResort.war /path/to/tomcat/webapps/
```

**Option B: Using Maven Tomcat Plugin**

```bash
mvn tomcat7:run
```

### 5. Start Tomcat Server

**Windows:**
```bash
cd C:\path\to\tomcat\bin
startup.bat
```

**Linux/Mac:**
```bash
cd /path/to/tomcat/bin
./startup.sh
```

### 6. Access the Application

Open your web browser and navigate to:

```
http://localhost:8080/OceanViewResort
```

## Project Structure

```
OceanViewResort/
├── database/                    # Database schemas
│   ├── postgresql_schema.sql
│   └── schema.sql
├── src/
│   ├── main/
│   │   ├── java/com/oceanview/
│   │   │   ├── controller/      # Servlets (Request handlers)
│   │   │   ├── dao/             # Data Access Objects
│   │   │   ├── model/           # Domain models/entities
│   │   │   ├── service/         # Business logic
│   │   │   └── util/            # Utilities
│   │   └── webapp/              # Web resources (JSP, CSS, JS)
│   │       ├── css/
│   │       ├── js/
│   │       ├── WEB-INF/
│   │       └── *.jsp
│   └── test/                    # Unit tests
├── target/                      # Build output (generated)
├── .env                         # Environment variables
├── pom.xml                      # Maven configuration
└── README.md
```

## Configuration

### Database Configuration

The application uses the following database configuration from `.env`:

- **Host**: localhost
- **Port**: 5432
- **Database**: ocean_view_resort
- **Username**: postgres
- **Password**: (set in .env file)

You can modify these settings in:
- `.env` file (preferred)
- `src/main/java/com/oceanview/util/DatabaseManager.java`

### Tomcat Configuration

Default Tomcat settings:
- **Port**: 8080
- **Context Path**: /OceanViewResort

To change the port, edit `TOMCAT_HOME/conf/server.xml`

## Running Tests

To run the unit tests:

```bash
mvn test
```

To skip tests during build:

```bash
mvn clean package -DskipTests
```

## Troubleshooting

### Issue: "Connection refused" error

**Solution**: Make sure Tomcat is running and accessible on port 8080

### Issue: Database connection error

**Solution**:
- Verify PostgreSQL is running
- Check database credentials in `.env`
- Ensure the database `ocean_view_resort` exists

### Issue: "mvn command not found"

**Solution**: Add Maven to your system PATH or use the full path to mvn

### Issue: "JAVA_HOME not set"

**Solution**: Set JAVA_HOME environment variable:

**Windows:**
```bash
set JAVA_HOME=C:\Program Files\Java\jdk-11
```

**Linux/Mac:**
```bash
export JAVA_HOME=/usr/lib/jvm/java-11-openjdk
```

## Default Login Credentials

(Update this section based on your database seed data)

```
Username: admin
Password: admin123
```

## API Endpoints

- `/login` - User login
- `/logout` - User logout
- `/dashboard` - Dashboard view
- `/addReservation` - Add new reservation
- `/viewReservation` - View reservations
- `/cancelReservation` - Cancel reservation
- `/bill` - Generate bill

## Contributing

1. Fork the repository
2. Create your feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## License

This project is licensed under the Apache License 2.0 - see the LICENSE file for details.

## Author

**Ocean View Resort Team**

## Support

For issues and questions, please open an issue on GitHub:
https://github.com/medawatthe/OceanViewResort/issues

## Acknowledgments

- Apache Tomcat
- PostgreSQL
- Maven
- Bootstrap (if used in frontend)
