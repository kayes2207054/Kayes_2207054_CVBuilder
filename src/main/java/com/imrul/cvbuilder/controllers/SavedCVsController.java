package com.imrul.cvbuilder.controllers;

import com.imrul.cvbuilder.CVBuilderApplication;
import com.imrul.cvbuilder.database.DatabaseManager;
import com.imrul.cvbuilder.models.CV;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Controller for the Saved CVs View
 * Uses ObservableList to manage CV data and concurrency for database operations
 */
public class SavedCVsController {

    @FXML private TextField searchField;
    @FXML private VBox cvListContainer;
    @FXML private Label noCVsLabel;

    // ObservableList to manage CVs with automatic UI updates
    private ObservableList<CV> cvObservableList;

    @FXML
    public void initialize() {
        cvObservableList = FXCollections.observableArrayList();
        loadAllCVs();
    }

    /**
     * Load all CVs from database using concurrency
     */
    private void loadAllCVs() {
        cvListContainer.getChildren().clear();
        showLoadingIndicator();

        DatabaseManager.getInstance().getAllCVsAsync((exception) -> {
            Platform.runLater(() -> {
                hideLoadingIndicator();
                showAlert("Error", "Failed to load CVs: " + exception.getMessage(), Alert.AlertType.ERROR);
            });
        }).thenAccept(cvList -> {
            Platform.runLater(() -> {
                hideLoadingIndicator();
                cvObservableList.clear();
                cvObservableList.addAll(cvList);
                displayCVs();
            });
        }).exceptionally(throwable -> {
            Platform.runLater(() -> {
                hideLoadingIndicator();
                showAlert("Error", "An error occurred while loading CVs: " + throwable.getMessage(), Alert.AlertType.ERROR);
            });
            return null;
        });
    }

    /**
     * Display all CVs from the ObservableList
     */
    private void displayCVs() {
        cvListContainer.getChildren().clear();

        if (cvObservableList.isEmpty()) {
            noCVsLabel.setVisible(true);
            noCVsLabel.setManaged(true);
            cvListContainer.getChildren().add(noCVsLabel);
            return;
        }

        noCVsLabel.setVisible(false);
        noCVsLabel.setManaged(false);

        for (CV cv : cvObservableList) {
            VBox cvCard = createCVCard(cv);
            cvListContainer.getChildren().add(cvCard);
        }
    }

    /**
     * Create a card UI component for a CV
     */
    private VBox createCVCard(CV cv) {
        VBox card = new VBox(10);
        card.setStyle("-fx-background-color: white; -fx-padding: 20; -fx-background-radius: 8; " +
                     "-fx-border-color: #e0e0e0; -fx-border-width: 1; -fx-border-radius: 8; " +
                     "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.1), 5, 0, 0, 2);");

        // Header with name
        Label nameLabel = new Label(cv.getFullName());
        nameLabel.setFont(Font.font("System", FontWeight.BOLD, 20));
        nameLabel.setStyle("-fx-text-fill: #2c3e50;");

        // Contact information
        HBox contactBox = new HBox(20);
        contactBox.setAlignment(Pos.CENTER_LEFT);

        Label emailLabel = new Label("✉ " + cv.getEmail());
        emailLabel.setFont(Font.font(14));
        emailLabel.setStyle("-fx-text-fill: #555555;");

        Label phoneLabel = new Label("☎ " + cv.getPhoneNumber());
        phoneLabel.setFont(Font.font(14));
        phoneLabel.setStyle("-fx-text-fill: #555555;");

        contactBox.getChildren().addAll(emailLabel, phoneLabel);

        // Buttons
        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);

        Button viewButton = new Button("View");
        viewButton.setStyle("-fx-background-color: #3498db; -fx-text-fill: white; " +
                           "-fx-padding: 8 20; -fx-background-radius: 5; -fx-cursor: hand;");
        viewButton.setOnAction(e -> handleViewCV(cv));

        Button deleteButton = new Button("Delete");
        deleteButton.setStyle("-fx-background-color: #e74c3c; -fx-text-fill: white; " +
                             "-fx-padding: 8 20; -fx-background-radius: 5; -fx-cursor: hand;");
        deleteButton.setOnAction(e -> handleDeleteCV(cv));

        buttonBox.getChildren().addAll(spacer, viewButton, deleteButton);

        card.getChildren().addAll(nameLabel, contactBox, new Separator(), buttonBox);

        return card;
    }

    /**
     * Handle viewing a CV
     */
    private void handleViewCV(CV cv) {
        CVBuilderApplication.getInstance().showPreviewCVView(cv);
    }

    /**
     * Handle deleting a CV using concurrency
     */
    private void handleDeleteCV(CV cv) {
        Alert confirmAlert = new Alert(Alert.AlertType.CONFIRMATION);
        confirmAlert.setTitle("Confirm Delete");
        confirmAlert.setHeaderText("Delete CV");
        confirmAlert.setContentText("Are you sure you want to delete the CV for " + cv.getFullName() + "?");

        confirmAlert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                DatabaseManager.getInstance().deleteCVAsync(cv.getId(), (exception) -> {
                    Platform.runLater(() -> {
                        showAlert("Error", "Failed to delete CV: " + exception.getMessage(), Alert.AlertType.ERROR);
                    });
                }).thenAccept(success -> {
                    Platform.runLater(() -> {
                        if (success) {
                            cvObservableList.remove(cv);
                            displayCVs();
                            showAlert("Success", "CV deleted successfully!", Alert.AlertType.INFORMATION);
                        } else {
                            showAlert("Error", "Failed to delete CV from database", Alert.AlertType.ERROR);
                        }
                    });
                }).exceptionally(throwable -> {
                    Platform.runLater(() -> {
                        showAlert("Error", "An error occurred while deleting: " + throwable.getMessage(), Alert.AlertType.ERROR);
                    });
                    return null;
                });
            }
        });
    }

    @FXML
    private void handleSearch() {
        String searchTerm = searchField.getText().trim();
        
        if (searchTerm.isEmpty()) {
            loadAllCVs();
            return;
        }

        cvListContainer.getChildren().clear();
        showLoadingIndicator();

        // Using synchronous call wrapped in async for search
        DatabaseManager db = DatabaseManager.getInstance();
        new Thread(() -> {
            try {
                var results = db.searchCVsByName(searchTerm);
                Platform.runLater(() -> {
                    hideLoadingIndicator();
                    cvObservableList.clear();
                    cvObservableList.addAll(results);
                    displayCVs();
                });
            } catch (Exception e) {
                Platform.runLater(() -> {
                    hideLoadingIndicator();
                    showAlert("Error", "Search failed: " + e.getMessage(), Alert.AlertType.ERROR);
                });
            }
        }).start();
    }

    @FXML
    private void handleRefresh() {
        searchField.clear();
        loadAllCVs();
    }

    @FXML
    private void handleBackToHome() {
        CVBuilderApplication.getInstance().showHomeView();
    }

    @FXML
    private void handleCreateNew() {
        CVBuilderApplication.getInstance().showCreateCVView();
    }

    private void showLoadingIndicator() {
        Label loadingLabel = new Label("Loading CVs...");
        loadingLabel.setFont(Font.font(16));
        loadingLabel.setStyle("-fx-text-fill: #555555;");
        ProgressIndicator progressIndicator = new ProgressIndicator();
        progressIndicator.setMaxSize(50, 50);
        
        VBox loadingBox = new VBox(10, progressIndicator, loadingLabel);
        loadingBox.setAlignment(Pos.CENTER);
        loadingBox.setPadding(new Insets(50));
        
        cvListContainer.getChildren().clear();
        cvListContainer.getChildren().add(loadingBox);
    }

    private void hideLoadingIndicator() {
        // Loading indicator will be cleared when displayCVs is called
    }

    private void showAlert(String title, String content, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
