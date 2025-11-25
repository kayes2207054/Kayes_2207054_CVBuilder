# CV Builder - Database Integration Documentation

## Overview
This CV Builder application has been enhanced with SQLite database integration, Observable collections, JSON serialization, and concurrent operations for better performance and data persistence.

## Features Implemented

### 1. Database Integration (SQLite)
- **DatabaseManager Class**: Singleton pattern for managing all database operations
- **Database File**: `cvbuilder.db` (created automatically in the project root)
- **Table Structure**:
  ```sql
  CREATE TABLE cvs (
      id INTEGER PRIMARY KEY AUTOINCREMENT,
      full_name TEXT NOT NULL,
      email TEXT NOT NULL,
      phone_number TEXT,
      address TEXT,
      cv_data TEXT NOT NULL,
      created_at TEXT NOT NULL,
      updated_at TEXT NOT NULL
  )
  ```

### 2. CRUD Operations
All operations support both synchronous and asynchronous execution:

#### Create/Update
- `saveCV(CV cv)` - Synchronous save
- `saveCVAsync(CV cv, errorHandler)` - Asynchronous save with error handling

#### Read
- `getAllCVs()` - Get all CVs (synchronous)
- `getAllCVsAsync(errorHandler)` - Get all CVs (asynchronous)
- `getCVById(int id)` - Get specific CV (synchronous)
- `getCVByIdAsync(int id, errorHandler)` - Get specific CV (asynchronous)
- `searchCVsByName(String searchTerm)` - Search CVs by name

#### Delete
- `deleteCV(int id)` - Synchronous delete
- `deleteCVAsync(int id, errorHandler)` - Asynchronous delete with error handling

### 3. JSON Integration
- **Gson Library**: Used for JSON serialization/deserialization
- **CV Model**: Added `toJSON()` and `fromJSON()` methods
- **Storage**: CV data stored as JSON in the database for flexibility

### 4. Observable Collections
- **ObservableList in CV Model**: 
  - `ObservableList<Education> educationList`
  - `ObservableList<String> skills`
  - `ObservableList<WorkExperience> workExperienceList`
  - `ObservableList<Project> projects`
  
- **ObservableList in SavedCVsController**: 
  - `ObservableList<CV> cvObservableList` for automatic UI updates

### 5. Concurrency Implementation
- **ExecutorService**: Thread pool with 3 threads for concurrent database operations
- **CompletableFuture**: All async operations return CompletableFuture for composable asynchronous programming
- **Platform.runLater()**: Ensures UI updates happen on JavaFX Application Thread
- **Graceful Shutdown**: DatabaseManager properly shuts down thread pool on application exit

### 6. New UI Features

#### Preview Window - Save CV Button
- Location: Bottom button bar in PreviewCVView
- Functionality:
  - Saves new CV to database (INSERT)
  - Updates existing CV if already saved (UPDATE)
  - Shows success/error alerts
  - Uses asynchronous database operations

#### Home Page - View Saved CVs Button
- Location: Home screen below "Create New CV"
- Functionality:
  - Navigates to Saved CVs view
  - Displays all previously saved CVs

#### Saved CVs View (New Screen)
Features:
- **Search Bar**: Search CVs by name
- **Refresh Button**: Reload all CVs from database
- **CV Cards**: Each CV displayed as a card with:
  - Full Name (large, bold)
  - Email and Phone (icon-prefixed)
  - View Button (opens CV in preview)
  - Delete Button (removes CV with confirmation)
- **Loading Indicator**: Shows while fetching data
- **Empty State**: Message when no CVs exist
- **Navigation**: Back to Home, Create New CV

## Dependencies Added

### Maven Dependencies (pom.xml)
```xml
<!-- SQLite JDBC Driver -->
<dependency>
    <groupId>org.xerial</groupId>
    <artifactId>sqlite-jdbc</artifactId>
    <version>3.46.1.0</version>
</dependency>

<!-- Gson for JSON -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.11.0</version>
</dependency>
```

### Module Configuration (module-info.java)
```java
requires java.sql;
requires com.google.gson;

opens com.imrul.cvbuilder.models to javafx.fxml, com.google.gson;
opens com.imrul.cvbuilder.database to com.google.gson;
```

## File Structure

### New Files Created
```
src/main/java/com/imrul/cvbuilder/
├── database/
│   └── DatabaseManager.java         # Database operations manager
└── controllers/
    └── SavedCVsController.java      # Controller for saved CVs view

src/main/resources/com/imrul/cvbuilder/
└── fxml/
    └── SavedCVsView.fxml            # FXML for saved CVs screen
```

### Modified Files
```
src/main/java/com/imrul/cvbuilder/
├── models/
│   └── CV.java                       # Added ID, toJSON(), fromJSON()
├── controllers/
│   ├── PreviewCVController.java     # Added handleSaveCV()
│   └── HomeController.java          # Added handleViewSavedCVs()
└── CVBuilderApplication.java        # Added navigation, DB init

src/main/resources/com/imrul/cvbuilder/
├── fxml/
│   ├── PreviewCVView.fxml           # Added Save CV button
│   └── HomeView.fxml                # Added View Saved CVs button
└── css/
    └── styles.css                    # Added styles for new views

pom.xml                               # Added dependencies
module-info.java                      # Added requires/opens
```

## Usage Guide

### Saving a CV
1. Create or edit a CV
2. Click "Generate Preview"
3. In preview window, click "Save CV"
4. Success message confirms save
5. CV is now stored in database

### Viewing Saved CVs
1. From home screen, click "View Saved CVs"
2. All saved CVs appear as cards
3. Use search bar to filter by name
4. Click "Refresh" to reload from database

### Managing Saved CVs
**View a CV:**
- Click "View" button on any CV card
- Opens CV in preview mode

**Delete a CV:**
- Click "Delete" button on any CV card
- Confirm deletion in dialog
- CV is permanently removed from database

**Search CVs:**
- Type name in search field
- Click "Search" button
- Results update automatically

## Technical Details

### Thread Safety
- All database operations are thread-safe
- UI updates always use Platform.runLater()
- ExecutorService manages concurrent operations
- No race conditions in CRUD operations

### Error Handling
- Comprehensive error handlers for all async operations
- User-friendly error messages via Alerts
- Database exceptions caught and logged
- Graceful degradation on failures

### Performance
- Asynchronous operations prevent UI freezing
- Thread pool reuses threads for efficiency
- JSON serialization is fast and compact
- SQLite provides excellent read/write performance

### Data Persistence
- All CV data persists between sessions
- Database file created automatically
- No manual setup required
- Works offline (no network needed)

## Building the Project

### Prerequisites
- Java 21 or higher
- Maven (or use included mvnw wrapper)
- JavaFX 21.0.6

### Build Commands
```bash
# Using Maven wrapper (Windows)
.\mvnw.cmd clean install

# Using Maven wrapper (Unix/Mac)
./mvnw clean install

# Using system Maven
mvn clean install
```

### Running the Application
```bash
# Using Maven
mvn javafx:run

# Or run the compiled JAR
java -jar target/CVBuilder-1.0-SNAPSHOT.jar
```

## Troubleshooting

### JAVA_HOME Not Set
Set JAVA_HOME environment variable:
```bash
# Windows
set JAVA_HOME=C:\Path\To\JDK

# Unix/Mac
export JAVA_HOME=/path/to/jdk
```

### Module Errors
If you see module-related errors, ensure:
1. Java 21+ is installed
2. Dependencies are downloaded (run `mvn clean install`)
3. module-info.java includes all required modules

### Database Locked
If database is locked:
1. Close all application instances
2. Delete cvbuilder.db file (data will be lost)
3. Restart application (new database created)

## Future Enhancements
- Export CV to PDF
- Import CV from JSON file
- Share CV via email
- CV templates
- Advanced search filters
- Backup/restore database
- Cloud synchronization

## License
This project is part of the CV Builder application by Kayes (ID: 2207054).

## Contact
For issues or questions, please contact the development team.
