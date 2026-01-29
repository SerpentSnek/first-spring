# Setup and Installation Guide

## Table of Contents

1. [System Requirements](#system-requirements)
2. [Backend Setup (Spring Boot)](#backend-setup-spring-boot)
3. [Database Setup (PostgreSQL)](#database-setup-postgresql)
4. [Frontend Setup (Flutter)](#frontend-setup-flutter)
5. [Running the Application](#running-the-application)
6. [Troubleshooting](#troubleshooting)

---

## System Requirements

### General Requirements
- Git (for version control)
- A terminal/command prompt
- Text editor or IDE (VS Code, IntelliJ IDEA recommended)

### Backend Requirements
- **Java Development Kit (JDK) 21** or later
  - Download from [java.com](https://www.java.com) or use `winget install Oracle.JDK.21` on Windows
  - Verify: `java -version`
- **Maven 3.6.0** or later
  - Download from [maven.apache.org](https://maven.apache.org/download.cgi)
  - Verify: `mvn -version`
  - Or use the included Maven wrapper (mvnw/mvnw.cmd)

### Database Requirements
- **PostgreSQL 12+**
  - Download from [postgresql.org](https://www.postgresql.org/download/)
  - Verify: `psql --version`
  - For Windows: Use [PostgreSQL installer](https://www.postgresql.org/download/windows/) or `winget install PostgreSQL.PostgreSQL`

### Frontend Requirements
- **Flutter SDK 3.10.0+**
  - Download from [flutter.dev](https://flutter.dev/docs/get-started/install)
  - Verify: `flutter --version`
- **Dart SDK** (included with Flutter)
  - Verify: `dart --version`

---

## Backend Setup (Spring Boot)

### 1. Clone the Repository

```bash
cd c:\Users\serpentwyvern\Documents\GitHub
git clone https://github.com/yourusername/first-spring.git
cd first-spring/firstspringdemo
```

### 2. Verify Java Installation

```bash
java -version
```

Expected output should show Java 21.

### 3. Verify Maven Installation

```bash
mvn -version
```

Or use the Maven wrapper:

```bash
./mvnw -version
```

### 4. Build the Project

```bash
# Option 1: Using Maven wrapper (recommended)
./mvnw clean install

# Option 2: Using installed Maven
mvn clean install
```

This will:
- Download all dependencies
- Compile the source code
- Run tests
- Create a JAR file in `target/`

### 5. Verify Build Success

Look for `BUILD SUCCESS` in the console output. The build should complete without errors.

---

## Database Setup (PostgreSQL)

### 1. Install PostgreSQL

**On Windows:**
```bash
winget install PostgreSQL.PostgreSQL
```

Or download the installer from [postgresql.org](https://www.postgresql.org/download/windows/).

**On macOS:**
```bash
brew install postgresql
```

**On Linux (Ubuntu/Debian):**
```bash
sudo apt-get install postgresql postgresql-contrib
```

### 2. Start PostgreSQL Service

**Windows:**
- PostgreSQL automatically installs as a Windows service and starts on boot
- Or manually start: `pg_ctl start -D "C:\Program Files\PostgreSQL\15\data"`

**macOS/Linux:**
```bash
# macOS (using Homebrew)
brew services start postgresql

# Linux
sudo service postgresql start
# or
sudo systemctl start postgresql
```

### 3. Create the Database and User

```bash
# Connect to PostgreSQL as the default postgres user
psql -U postgres

# In the psql prompt, create the database
CREATE DATABASE firstspringdemodb;

# Create a user (if not already created)
CREATE USER serpentwyvern WITH PASSWORD 'HelionTokamak6891!!!';

# Grant privileges
ALTER ROLE serpentwyvern SET client_encoding TO 'utf8';
ALTER ROLE serpentwyvern SET default_transaction_isolation TO 'read committed';
ALTER ROLE serpentwyvern SET default_transaction_deferrable TO on;
ALTER ROLE serpentwyvern SET default_transaction_read_committed TO off;
GRANT ALL PRIVILEGES ON DATABASE firstspringdemodb TO serpentwyvern;

# Exit psql
\q
```

### 4. Verify Database Connection

```bash
psql -U serpentwyvern -d firstspringdemodb -h localhost
```

If successful, you should see the `firstspringdemodb=#` prompt.

### 5. Update application.properties

Edit `src/main/resources/application.properties`:

```properties
spring.application.name=firstspringdemo
spring.datasource.url=jdbc:postgresql://localhost:5432/firstspringdemodb?currentSchema=inventory_management,public
spring.datasource.username=serpentwyvern
spring.datasource.password=HelionTokamak6891!!!
spring.datasource.driver-class-name=org.postgresql.Driver
```

**Important:** Update the password if you used a different one during database creation.

---

## Frontend Setup (Flutter)

### 1. Install Flutter SDK

**Windows:**
```bash
# Using winget
winget install Google.Flutter

# Or manually:
# 1. Download from https://flutter.dev/docs/get-started/install/windows
# 2. Extract to a folder (e.g., C:\flutter)
# 3. Add C:\flutter\bin to PATH environment variable
```

**macOS:**
```bash
# Using Homebrew
brew install flutter
```

**Linux:**
```bash
# Download and extract
cd ~/development
tar xf ~/Downloads/flutter_linux_*.tar.xz

# Add to PATH
export PATH="$PATH:`pwd`/flutter/bin"
# Add to ~/.bashrc or ~/.zshrc permanently
```

### 2. Verify Flutter Installation

```bash
flutter doctor
```

This will check for missing dependencies. Fix any warnings before proceeding.

### 3. Navigate to Flutter Project

```bash
cd first-spring/firstspringdemo/flutter
```

### 4. Get Dependencies

```bash
flutter pub get
```

This downloads all dependencies listed in `pubspec.yaml`.

### 5. Check Flutter Setup

```bash
flutter devices
```

This lists available devices/emulators where you can run the app.

### 6. Update API Endpoint

Edit `lib/services/api_client.dart`:

```dart
const String baseUrl = 'http://localhost:8080';  // Update if backend is on different host
```

If testing on a physical device or different network, use your machine's actual IP address:

```dart
const String baseUrl = 'http://192.168.1.100:8080';  // Replace with your IP
```

---

## Running the Application

### Step 1: Start PostgreSQL

**Windows:**
```bash
# PostgreSQL should be running as a service
# Verify it's running by connecting:
psql -U serpentwyvern -d firstspringdemodb
\q
```

**macOS/Linux:**
```bash
brew services start postgresql
# or
sudo systemctl start postgresql
```

### Step 2: Start the Spring Boot Backend

```bash
cd first-spring/firstspringdemo

# Option 1: Using Maven wrapper
./mvnw spring-boot:run

# Option 2: Using Maven
mvn spring-boot:run

# Option 3: Running the JAR directly
java -jar target/firstspringdemo-0.0.1-SNAPSHOT.jar
```

**Expected output:**
```
...
Started FirstspringdemoApplication in X.XXX seconds
```

The backend is now running on `http://localhost:8080`.

### Step 3: Verify Backend is Running

Open a new terminal and test the health endpoint:

```bash
curl http://localhost:8080/actuator/health
```

**Expected response:**
```json
{"status":"UP"}
```

### Step 4: Start the Flutter Frontend

```bash
cd first-spring/firstspringdemo/flutter

# For Android emulator
flutter run

# For iOS simulator (macOS only)
flutter run -d ios

# For a specific device
flutter run -d <device-id>

# For web (if enabled)
flutter run -d web-javascript
```

The app will compile and launch on your device/emulator.

---

## Verify Everything Works

1. **Backend running:** `http://localhost:8080/actuator/health` returns `{"status":"UP"}`
2. **Database connected:** `psql -U serpentwyvern -d firstspringdemodb` connects successfully
3. **Flutter app running:** App displays and connects to backend

You're all set! 🎉

---

## Troubleshooting

### Java/Maven Issues

**Problem:** `java: command not found` or `mvn: command not found`

**Solution:**
1. Verify installation: `java -version`, `mvn -version`
2. Add to PATH if needed
3. On Windows, restart terminal after installing Java

---

### PostgreSQL Connection Issues

**Problem:** `FATAL: Ident authentication failed` or cannot connect to PostgreSQL

**Solution:**
1. Verify PostgreSQL is running
2. Check credentials in `application.properties`
3. Verify database exists: `psql -U postgres -c "SELECT datname FROM pg_database;"`
4. Create database if needed (see Database Setup section)

**Problem:** Port already in use (usually 5432)

**Solution:**
```bash
# Kill the process using the port
# Windows
netstat -ano | findstr :5432
taskkill /PID <PID> /F

# macOS/Linux
lsof -i :5432
kill -9 <PID>
```

---

### Spring Boot Build/Run Issues

**Problem:** `[ERROR] COMPILATION ERROR` or build fails

**Solution:**
1. Clear cache: `./mvnw clean`
2. Verify Java version: `java -version` (must be 21+)
3. Check internet connection (downloading dependencies)
4. Try: `./mvnw clean install -X` for detailed error messages

---

### Flutter Issues

**Problem:** `flutter: command not found`

**Solution:**
1. Verify installation: `flutter --version`
2. Add Flutter to PATH (see Flutter Installation)
3. Run: `flutter doctor` to diagnose

**Problem:** `Connection refused` or app can't reach backend

**Solution:**
1. Verify backend is running: `curl http://localhost:8080/actuator/health`
2. Check API endpoint in `api_client.dart` matches backend URL
3. On emulator, use your machine's IP instead of `localhost`
4. Check firewall settings

---

### Port Already in Use

**Problem:** Port 8080 (backend) or 5432 (database) already in use

**Solution:**
```bash
# Windows - find and kill process
netstat -ano | findstr :8080
taskkill /PID <PID> /F

# macOS/Linux
lsof -i :8080
kill -9 <PID>
```

Or change the port in `application.properties`:
```properties
server.port=8081
```

---

## Getting Help

1. Check [DOCUMENTATION.md](DOCUMENTATION.md) for project overview
2. Check [API_REFERENCE.md](API_REFERENCE.md) for API details
3. Review logs in terminal output for error messages
4. Run `flutter doctor` for Flutter-specific issues
5. Check PostgreSQL logs: `tail -f /var/log/postgresql/postgresql.log`

---

**Last Updated:** January 2026
