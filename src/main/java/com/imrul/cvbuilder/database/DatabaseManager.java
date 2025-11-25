package com.imrul.cvbuilder.database;

import com.imrul.cvbuilder.models.CV;
import com.imrul.cvbuilder.utils.GsonHelper;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Consumer;

/**
 * DatabaseManager handles all database operations for CV storage
 * Uses SQLite for persistence, Gson for JSON serialization, and ExecutorService for concurrency
 */
public class DatabaseManager {
    private static DatabaseManager instance;
    private static final String DB_URL = "jdbc:sqlite:cvbuilder.db";
    private final ExecutorService executorService;
    
    private DatabaseManager() {
        // Create thread pool for concurrent database operations
        this.executorService = Executors.newFixedThreadPool(3);
        initializeDatabase();
    }
    
    public static synchronized DatabaseManager getInstance() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }
    
    /**
     * Initialize the database and create tables if they don't exist
     */
    private void initializeDatabase() {
        String createTableSQL = """
            CREATE TABLE IF NOT EXISTS cvs (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                full_name TEXT NOT NULL,
                email TEXT NOT NULL,
                phone_number TEXT,
                address TEXT,
                cv_data TEXT NOT NULL,
                created_at TEXT NOT NULL,
                updated_at TEXT NOT NULL
            )
            """;
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(createTableSQL);
            System.out.println("Database initialized successfully");
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Get database connection
     */
    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL);
    }
    
    /**
     * Save a CV to the database (INSERT or UPDATE) - Synchronous version
     */
    public boolean saveCV(CV cv) throws SQLException {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME);
        String cvJson = GsonHelper.getGson().toJson(cv);
        
        if (cv.getId() == null || cv.getId() <= 0) {
            // Insert new CV
            String insertSQL = """
                INSERT INTO cvs (full_name, email, phone_number, address, cv_data, created_at, updated_at)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;
            
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(insertSQL, Statement.RETURN_GENERATED_KEYS)) {
                
                pstmt.setString(1, cv.getFullName());
                pstmt.setString(2, cv.getEmail());
                pstmt.setString(3, cv.getPhoneNumber());
                pstmt.setString(4, cv.getAddress());
                pstmt.setString(5, cvJson);
                pstmt.setString(6, timestamp);
                pstmt.setString(7, timestamp);
                
                int affectedRows = pstmt.executeUpdate();
                
                if (affectedRows > 0) {
                    try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                        if (generatedKeys.next()) {
                            cv.setId(generatedKeys.getInt(1));
                            return true;
                        }
                    }
                }
            }
        } else {
            // Update existing CV
            String updateSQL = """
                UPDATE cvs SET full_name = ?, email = ?, phone_number = ?, address = ?, 
                               cv_data = ?, updated_at = ?
                WHERE id = ?
                """;
            
            try (Connection conn = getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(updateSQL)) {
                
                pstmt.setString(1, cv.getFullName());
                pstmt.setString(2, cv.getEmail());
                pstmt.setString(3, cv.getPhoneNumber());
                pstmt.setString(4, cv.getAddress());
                pstmt.setString(5, cvJson);
                pstmt.setString(6, timestamp);
                pstmt.setInt(7, cv.getId());
                
                return pstmt.executeUpdate() > 0;
            }
        }
        
        return false;
    }
    
    /**
     * Save a CV to the database asynchronously - Uses concurrency
     */
    public CompletableFuture<Boolean> saveCVAsync(CV cv, Consumer<Exception> errorHandler) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return saveCV(cv);
            } catch (SQLException e) {
                if (errorHandler != null) {
                    errorHandler.accept(e);
                }
                throw new CompletionException(e);
            }
        }, executorService);
    }
    
    /**
     * Get all CVs from the database - Synchronous version
     */
    public List<CV> getAllCVs() throws SQLException {
        List<CV> cvList = new ArrayList<>();
        String selectSQL = "SELECT * FROM cvs ORDER BY updated_at DESC";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(selectSQL)) {
            
            while (rs.next()) {
                CV cv = GsonHelper.getGson().fromJson(rs.getString("cv_data"), CV.class);
                cv.setId(rs.getInt("id"));
                cvList.add(cv);
            }
        }
        
        return cvList;
    }
    
    /**
     * Get all CVs from the database asynchronously - Uses concurrency
     */
    public CompletableFuture<List<CV>> getAllCVsAsync(Consumer<Exception> errorHandler) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getAllCVs();
            } catch (SQLException e) {
                if (errorHandler != null) {
                    errorHandler.accept(e);
                }
                throw new CompletionException(e);
            }
        }, executorService);
    }
    
    /**
     * Get a specific CV by ID - Synchronous version
     */
    public CV getCVById(int id) throws SQLException {
        String selectSQL = "SELECT * FROM cvs WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setInt(1, id);
            
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    CV cv = GsonHelper.getGson().fromJson(rs.getString("cv_data"), CV.class);
                    cv.setId(rs.getInt("id"));
                    return cv;
                }
            }
        }
        
        return null;
    }
    
    /**
     * Get a CV by ID asynchronously - Uses concurrency
     */
    public CompletableFuture<CV> getCVByIdAsync(int id, Consumer<Exception> errorHandler) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return getCVById(id);
            } catch (SQLException e) {
                if (errorHandler != null) {
                    errorHandler.accept(e);
                }
                throw new CompletionException(e);
            }
        }, executorService);
    }
    
    /**
     * Delete a CV from the database - Synchronous version
     */
    public boolean deleteCV(int id) throws SQLException {
        String deleteSQL = "DELETE FROM cvs WHERE id = ?";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(deleteSQL)) {
            
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
    
    /**
     * Delete a CV asynchronously - Uses concurrency
     */
    public CompletableFuture<Boolean> deleteCVAsync(int id, Consumer<Exception> errorHandler) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                return deleteCV(id);
            } catch (SQLException e) {
                if (errorHandler != null) {
                    errorHandler.accept(e);
                }
                throw new CompletionException(e);
            }
        }, executorService);
    }
    
    /**
     * Search CVs by name - Synchronous version
     */
    public List<CV> searchCVsByName(String searchTerm) throws SQLException {
        List<CV> cvList = new ArrayList<>();
        String selectSQL = "SELECT * FROM cvs WHERE full_name LIKE ? ORDER BY updated_at DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(selectSQL)) {
            
            pstmt.setString(1, "%" + searchTerm + "%");
            
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    CV cv = GsonHelper.getGson().fromJson(rs.getString("cv_data"), CV.class);
                    cv.setId(rs.getInt("id"));
                    cvList.add(cv);
                }
            }
        }
        
        return cvList;
    }
    
    /**
     * Get count of all CVs
     */
    public int getCVCount() throws SQLException {
        String countSQL = "SELECT COUNT(*) FROM cvs";
        
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(countSQL)) {
            
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        
        return 0;
    }
    
    /**
     * Shutdown the executor service gracefully
     */
    public void shutdown() {
        executorService.shutdown();
        try {
            if (!executorService.awaitTermination(5, TimeUnit.SECONDS)) {
                executorService.shutdownNow();
            }
        } catch (InterruptedException e) {
            executorService.shutdownNow();
            Thread.currentThread().interrupt();
        }
    }
}
