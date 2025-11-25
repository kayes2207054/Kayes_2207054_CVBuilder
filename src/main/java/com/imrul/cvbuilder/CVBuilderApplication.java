package com.imrul.cvbuilder;

import com.imrul.cvbuilder.controllers.PreviewCVController;
import com.imrul.cvbuilder.database.DatabaseManager;
import com.imrul.cvbuilder.models.CV;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CVBuilderApplication extends Application {
    private static CVBuilderApplication instance;
    private Stage primaryStage;
    private Scene homeScene;
    private Scene createCVScene;

    public static CVBuilderApplication getInstance() {
        return instance;
    }

    @Override
    public void start(Stage primaryStage) {
        instance = this;
        this.primaryStage = primaryStage;

        // Initialize database on startup
        DatabaseManager.getInstance();

        primaryStage.setTitle("CV Builder Application");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(700);
        primaryStage.setMinWidth(800);
        primaryStage.setMinHeight(600);

        showHomeView();
        primaryStage.show();
    }

    @Override
    public void stop() {
        // Shutdown database manager gracefully
        DatabaseManager.getInstance().shutdown();
    }

    public void showHomeView() {
        try {
            if (homeScene == null) {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/HomeView.fxml"));
                Parent root = loader.load();
                homeScene = new Scene(root);
            }
            primaryStage.setScene(homeScene);
            primaryStage.setTitle("CV Builder - Home");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load Home View: " + e.getMessage());
        }
    }

    public void showCreateCVView() {
        try {
            // Always create a new instance to reset the form
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/CreateCVView.fxml"));
            Parent root = loader.load();
            createCVScene = new Scene(root);
            primaryStage.setScene(createCVScene);
            primaryStage.setTitle("CV Builder - Create CV");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load Create CV View: " + e.getMessage());
        }
    }

    public void showPreviewView(CV cv) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/PreviewCVView.fxml"));
            Parent root = loader.load();

            PreviewCVController controller = loader.getController();
            controller.setCV(cv);

            Scene previewScene = new Scene(root);
            primaryStage.setScene(previewScene);
            primaryStage.setTitle("CV Builder - Preview");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load Preview View: " + e.getMessage());
        }
    }

    public void showPreviewCVView(CV cv) {
        showPreviewView(cv);
    }

    public void showSavedCVsView() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("fxml/SavedCVsView.fxml"));
            Parent root = loader.load();
            Scene savedCVsScene = new Scene(root);
            primaryStage.setScene(savedCVsScene);
            primaryStage.setTitle("CV Builder - Saved CVs");
        } catch (IOException e) {
            e.printStackTrace();
            showError("Failed to load Saved CVs View: " + e.getMessage());
        }
    }

    private void showError(String message) {
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText("Application Error");
        alert.setContentText(message);
        alert.showAndWait();
    }
}
