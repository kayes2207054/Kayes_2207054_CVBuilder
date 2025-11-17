package com.imrul.cvbuilder.controllers;

import com.imrul.cvbuilder.CVBuilderApplication;
import com.imrul.cvbuilder.models.*;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;

/**
 * Controller for the Create CV View
 */
public class CreateCVController {

    @FXML private TextField fullNameField;
    @FXML private TextField emailField;
    @FXML private TextField phoneField;
    @FXML private TextField addressField;
    @FXML private TextArea skillsArea;
    @FXML private VBox educationContainer;
    @FXML private VBox workExperienceContainer;
    @FXML private VBox projectsContainer;

    private CV currentCV;

    @FXML
    private void initialize() {
        currentCV = new CV();
        // Add at least one education entry by default
        addEducationEntry();
    }

    @FXML
    private void handleAddEducation() {
        addEducationEntry();
    }

    private void addEducationEntry() {
        VBox entryBox = new VBox(15);
        entryBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-background-radius: 5;");

        // Department field
        VBox departmentBox = new VBox(5);
        Label departmentLabel = new Label("Department:");
        departmentLabel.setFont(Font.font("System", 13));
        departmentLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField departmentField = new TextField();
        departmentField.setPromptText("e.g., Computer Science and Engineering");
        departmentField.setStyle("-fx-pref-width: 100%;");
        departmentBox.getChildren().addAll(departmentLabel, departmentField);

        // Institution field
        VBox institutionBox = new VBox(5);
        Label institutionLabel = new Label("University/College:");
        institutionLabel.setFont(Font.font("System", 13));
        institutionLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField institutionField = new TextField();
        institutionField.setPromptText("e.g., University Name");
        institutionField.setStyle("-fx-pref-width: 100%;");
        institutionBox.getChildren().addAll(institutionLabel, institutionField);

        // Year and CGPA in HBox
        HBox detailsBox = new HBox(15);

        VBox yearBox = new VBox(5);
        Label yearLabel = new Label("Passing Year:");
        yearLabel.setFont(Font.font("System", 13));
        yearLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField yearField = new TextField();
        yearField.setPromptText("e.g., 2024");
        yearField.setPrefWidth(200);
        yearBox.getChildren().addAll(yearLabel, yearField);

        VBox gradeBox = new VBox(5);
        Label gradeLabel = new Label("CGPA:");
        gradeLabel.setFont(Font.font("System", 13));
        gradeLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField gradeField = new TextField();
        gradeField.setPromptText("e.g., 3.80/4.00");
        gradeField.setPrefWidth(200);
        gradeBox.getChildren().addAll(gradeLabel, gradeField);

        detailsBox.getChildren().addAll(yearBox, gradeBox);

        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-padding: 8 20;");
        removeButton.setOnAction(e -> educationContainer.getChildren().remove(entryBox));

        HBox buttonBox = new HBox(removeButton);
        buttonBox.setStyle("-fx-alignment: center-right;");
        VBox.setMargin(buttonBox, new Insets(10, 0, 0, 0));

        entryBox.getChildren().addAll(departmentBox, institutionBox, detailsBox, buttonBox);
        educationContainer.getChildren().add(entryBox);
    }

    @FXML
    private void handleAddWorkExperience() {
        addWorkExperienceEntry();
    }

    private void addWorkExperienceEntry() {
        VBox entryBox = new VBox(15);
        entryBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-background-radius: 5;");

        // Job Title field
        VBox jobTitleBox = new VBox(5);
        Label jobTitleLabel = new Label("Job Title:");
        jobTitleLabel.setFont(Font.font("System", 13));
        jobTitleLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField jobTitleField = new TextField();
        jobTitleField.setPromptText("e.g., Software Developer");
        jobTitleField.setStyle("-fx-pref-width: 100%;");
        jobTitleBox.getChildren().addAll(jobTitleLabel, jobTitleField);

        // Company field
        VBox companyBox = new VBox(5);
        Label companyLabel = new Label("Company:");
        companyLabel.setFont(Font.font("System", 13));
        companyLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField companyField = new TextField();
        companyField.setPromptText("e.g., Tech Company Inc.");
        companyField.setStyle("-fx-pref-width: 100%;");
        companyBox.getChildren().addAll(companyLabel, companyField);

        // Duration field
        VBox durationBox = new VBox(5);
        Label durationLabel = new Label("Duration:");
        durationLabel.setFont(Font.font("System", 13));
        durationLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField durationField = new TextField();
        durationField.setPromptText("e.g., Jan 2022 - Present");
        durationField.setStyle("-fx-pref-width: 100%;");
        durationBox.getChildren().addAll(durationLabel, durationField);

        // Description field
        VBox descBox = new VBox(5);
        Label descLabel = new Label("Description:");
        descLabel.setFont(Font.font("System", 13));
        descLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Describe your responsibilities and achievements");
        descArea.setPrefRowCount(3);
        descArea.setStyle("-fx-pref-width: 100%;");
        descArea.setWrapText(true);
        descBox.getChildren().addAll(descLabel, descArea);

        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-padding: 8 20;");
        removeButton.setOnAction(e -> workExperienceContainer.getChildren().remove(entryBox));

        HBox buttonBox = new HBox(removeButton);
        buttonBox.setStyle("-fx-alignment: center-right;");
        VBox.setMargin(buttonBox, new Insets(10, 0, 0, 0));

        entryBox.getChildren().addAll(jobTitleBox, companyBox, durationBox, descBox, buttonBox);
        workExperienceContainer.getChildren().add(entryBox);
    }

    @FXML
    private void handleAddProject() {
        addProjectEntry();
    }

    private void addProjectEntry() {
        VBox entryBox = new VBox(15);
        entryBox.setStyle("-fx-background-color: #f8f9fa; -fx-padding: 15; -fx-background-radius: 5;");

        // Project Name field
        VBox nameBox = new VBox(5);
        Label nameLabel = new Label("Project Name:");
        nameLabel.setFont(Font.font("System", 13));
        nameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField nameField = new TextField();
        nameField.setPromptText("e.g., E-Commerce Platform");
        nameField.setStyle("-fx-pref-width: 100%;");
        nameBox.getChildren().addAll(nameLabel, nameField);

        // Technologies field
        VBox techBox = new VBox(5);
        Label techLabel = new Label("Technologies:");
        techLabel.setFont(Font.font("System", 13));
        techLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField techField = new TextField();
        techField.setPromptText("e.g., Java, MySQL, Spring Boot");
        techField.setStyle("-fx-pref-width: 100%;");
        techBox.getChildren().addAll(techLabel, techField);

        // Duration field
        VBox durationBox = new VBox(5);
        Label durationLabel = new Label("Duration:");
        durationLabel.setFont(Font.font("System", 13));
        durationLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextField durationField = new TextField();
        durationField.setPromptText("e.g., 3 months");
        durationField.setStyle("-fx-pref-width: 100%;");
        durationBox.getChildren().addAll(durationLabel, durationField);

        // Description field
        VBox descBox = new VBox(5);
        Label descLabel = new Label("Description:");
        descLabel.setFont(Font.font("System", 13));
        descLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #34495e;");
        TextArea descArea = new TextArea();
        descArea.setPromptText("Describe the project and your role");
        descArea.setPrefRowCount(3);
        descArea.setStyle("-fx-pref-width: 100%;");
        descArea.setWrapText(true);
        descBox.getChildren().addAll(descLabel, descArea);

        Button removeButton = new Button("Remove");
        removeButton.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-padding: 8 20;");
        removeButton.setOnAction(e -> projectsContainer.getChildren().remove(entryBox));

        HBox buttonBox = new HBox(removeButton);
        buttonBox.setStyle("-fx-alignment: center-right;");
        VBox.setMargin(buttonBox, new Insets(10, 0, 0, 0));

        entryBox.getChildren().addAll(nameBox, techBox, durationBox, descBox, buttonBox);
        projectsContainer.getChildren().add(entryBox);
    }

    @FXML
    private void handleGenerateCV() {
        // Validate required fields
        if (fullNameField.getText().trim().isEmpty() ||
                emailField.getText().trim().isEmpty() ||
                phoneField.getText().trim().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Validation Error");
            alert.setHeaderText("Missing Required Fields");
            alert.setContentText("Please fill in all required fields (marked with *):\n- Full Name\n- Email Address\n- Phone Number");
            alert.showAndWait();
            return;
        }

        // Collect data from form
        currentCV.setFullName(fullNameField.getText().trim());
        currentCV.setEmail(emailField.getText().trim());
        currentCV.setPhoneNumber(phoneField.getText().trim());
        currentCV.setAddress(addressField.getText().trim());

        // Collect education
        currentCV.getEducationList().clear();
        for (Node node : educationContainer.getChildren()) {
            if (node instanceof VBox) {
                VBox entryBox = (VBox) node;
                VBox departmentBox = (VBox) entryBox.getChildren().get(0);
                VBox institutionBox = (VBox) entryBox.getChildren().get(1);
                HBox detailsBox = (HBox) entryBox.getChildren().get(2);

                TextField departmentField = (TextField) departmentBox.getChildren().get(1);
                TextField institutionField = (TextField) institutionBox.getChildren().get(1);
                VBox yearBox = (VBox) detailsBox.getChildren().get(0);
                VBox gradeBox = (VBox) detailsBox.getChildren().get(1);
                TextField yearField = (TextField) yearBox.getChildren().get(1);
                TextField gradeField = (TextField) gradeBox.getChildren().get(1);

                String department = departmentField.getText().trim();
                String institution = institutionField.getText().trim();
                String year = yearField.getText().trim();
                String grade = gradeField.getText().trim();

                if (!department.isEmpty() && !institution.isEmpty()) {
                    currentCV.getEducationList().add(new Education(department, institution, year, grade));
                }
            }
        }

        // Collect skills
        currentCV.getSkills().clear();
        String skillsText = skillsArea.getText().trim();
        if (!skillsText.isEmpty()) {
            String[] skills = skillsText.split(",");
            for (String skill : skills) {
                String trimmedSkill = skill.trim();
                if (!trimmedSkill.isEmpty()) {
                    currentCV.getSkills().add(trimmedSkill);
                }
            }
        }

        // Collect work experience
        currentCV.getWorkExperienceList().clear();
        for (Node node : workExperienceContainer.getChildren()) {
            if (node instanceof VBox) {
                VBox entryBox = (VBox) node;
                VBox jobTitleBox = (VBox) entryBox.getChildren().get(0);
                VBox companyBox = (VBox) entryBox.getChildren().get(1);
                VBox durationBox = (VBox) entryBox.getChildren().get(2);
                VBox descBox = (VBox) entryBox.getChildren().get(3);

                TextField jobTitleField = (TextField) jobTitleBox.getChildren().get(1);
                TextField companyField = (TextField) companyBox.getChildren().get(1);
                TextField durationField = (TextField) durationBox.getChildren().get(1);
                TextArea descArea = (TextArea) descBox.getChildren().get(1);

                String jobTitle = jobTitleField.getText().trim();
                String company = companyField.getText().trim();
                String duration = durationField.getText().trim();
                String description = descArea.getText().trim();

                if (!jobTitle.isEmpty() && !company.isEmpty()) {
                    currentCV.getWorkExperienceList().add(new WorkExperience(jobTitle, company, duration, description));
                }
            }
        }

        // Collect projects
        currentCV.getProjects().clear();
        for (Node node : projectsContainer.getChildren()) {
            if (node instanceof VBox) {
                VBox entryBox = (VBox) node;
                VBox nameBox = (VBox) entryBox.getChildren().get(0);
                VBox techBox = (VBox) entryBox.getChildren().get(1);
                VBox durationBox = (VBox) entryBox.getChildren().get(2);
                VBox descBox = (VBox) entryBox.getChildren().get(3);

                TextField nameField = (TextField) nameBox.getChildren().get(1);
                TextField techField = (TextField) techBox.getChildren().get(1);
                TextField durationField = (TextField) durationBox.getChildren().get(1);
                TextArea descArea = (TextArea) descBox.getChildren().get(1);

                String name = nameField.getText().trim();
                String tech = techField.getText().trim();
                String duration = durationField.getText().trim();
                String description = descArea.getText().trim();

                if (!name.isEmpty()) {
                    currentCV.getProjects().add(new Project(name, description, tech, duration));
                }
            }
        }

        // Show preview
        CVBuilderApplication.getInstance().showPreviewView(currentCV);
    }

    @FXML
    private void handleBackToHome() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirm");
        alert.setHeaderText("Go back to Home?");
        alert.setContentText("Any unsaved changes will be lost. Do you want to continue?");

        alert.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                CVBuilderApplication.getInstance().showHomeView();
            }
        });
    }
}
