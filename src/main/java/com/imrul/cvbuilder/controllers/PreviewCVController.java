package com.imrul.cvbuilder.controllers;

import com.imrul.cvbuilder.CVBuilderApplication;
import com.imrul.cvbuilder.models.*;
import com.imrul.cvbuilder.CVBuilderApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

/**
 * Controller for the CV Preview View
 */
public class PreviewCVController {

    @FXML private Label nameLabel;
    @FXML private Label emailLabel;
    @FXML private Label phoneLabel;
    @FXML private Label addressLabel;
    @FXML private Label addressSeparator;

    @FXML private VBox educationSection;
    @FXML private VBox educationContent;

    @FXML private VBox skillsSection;
    @FXML private FlowPane skillsContent;

    @FXML private VBox workExperienceSection;
    @FXML private VBox workExperienceContent;

    @FXML private VBox projectsSection;
    @FXML private VBox projectsContent;

    private CV currentCV;

    public void setCV(CV cv) {
        this.currentCV = cv;
        displayCV();
    }

    private void displayCV() {
        if (currentCV == null) {
            return;
        }

        // Display personal information
        nameLabel.setText(currentCV.getFullName());
        emailLabel.setText(currentCV.getEmail());
        phoneLabel.setText(currentCV.getPhoneNumber());

        if (currentCV.getAddress() != null && !currentCV.getAddress().isEmpty()) {
            addressLabel.setText(currentCV.getAddress());
            addressLabel.setVisible(true);
            addressLabel.setManaged(true);
            addressSeparator.setVisible(true);
            addressSeparator.setManaged(true);
        }

        // Display education
        if (!currentCV.getEducationList().isEmpty()) {
            educationContent.getChildren().clear();
            for (Education edu : currentCV.getEducationList()) {
                VBox eduBox = new VBox(8);
                eduBox.setStyle("-fx-padding: 0 0 20 0;");

                Label departmentLabel = new Label(edu.getDepartment());
                departmentLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
                departmentLabel.setStyle("-fx-text-fill: #2c3e50;");

                Label institutionLabel = new Label(edu.getInstitution());
                institutionLabel.setFont(Font.font("System", FontWeight.NORMAL, 15));
                institutionLabel.setStyle("-fx-text-fill: #555555; -fx-padding: 3 0 0 0;");

                HBox detailsBox = new HBox(20);
                detailsBox.setStyle("-fx-padding: 5 0 0 0;");

                if (edu.getYear() != null && !edu.getYear().isEmpty()) {
                    Label yearLabel = new Label("Year: " + edu.getYear());
                    yearLabel.setFont(Font.font(14));
                    yearLabel.setStyle("-fx-text-fill: #666666;");
                    detailsBox.getChildren().add(yearLabel);
                }
                if (edu.getGrade() != null && !edu.getGrade().isEmpty()) {
                    Label gradeLabel = new Label("CGPA: " + edu.getGrade());
                    gradeLabel.setFont(Font.font(14));
                    gradeLabel.setStyle("-fx-text-fill: #666666;");
                    detailsBox.getChildren().add(gradeLabel);
                }

                eduBox.getChildren().addAll(departmentLabel, institutionLabel);
                if (!detailsBox.getChildren().isEmpty()) {
                    eduBox.getChildren().add(detailsBox);
                }

                educationContent.getChildren().add(eduBox);
            }
            educationSection.setVisible(true);
            educationSection.setManaged(true);
        }

        // Display skills
        if (!currentCV.getSkills().isEmpty()) {
            skillsContent.getChildren().clear();
            for (String skill : currentCV.getSkills()) {
                Label skillLabel = new Label(skill);
                skillLabel.setStyle("-fx-background-color: #f0f0f0; -fx-text-fill: #2c3e50; " +
                        "-fx-padding: 10 18 10 18; -fx-background-radius: 5; -fx-font-size: 14; -fx-font-weight: 500; " +
                        "-fx-border-color: #d0d0d0; -fx-border-width: 1; -fx-border-radius: 5;");
                skillsContent.getChildren().add(skillLabel);
            }
            skillsSection.setVisible(true);
            skillsSection.setManaged(true);
        }

        // Display work experience
        if (!currentCV.getWorkExperienceList().isEmpty()) {
            workExperienceContent.getChildren().clear();
            for (WorkExperience work : currentCV.getWorkExperienceList()) {
                VBox workBox = new VBox(8);
                workBox.setStyle("-fx-padding: 0 0 20 0;");

                Label jobTitleLabel = new Label(work.getJobTitle());
                jobTitleLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
                jobTitleLabel.setStyle("-fx-text-fill: #2c3e50;");

                Label companyLabel = new Label(work.getCompany());
                companyLabel.setFont(Font.font("System", FontWeight.NORMAL, 15));
                companyLabel.setStyle("-fx-text-fill: #555555; -fx-padding: 3 0 0 0;");

                workBox.getChildren().addAll(jobTitleLabel, companyLabel);

                if (work.getDuration() != null && !work.getDuration().isEmpty()) {
                    Label durationLabel = new Label(work.getDuration());
                    durationLabel.setFont(Font.font(14));
                    durationLabel.setStyle("-fx-text-fill: #666666; -fx-font-style: italic; -fx-padding: 3 0 0 0;");
                    workBox.getChildren().add(durationLabel);
                }

                if (work.getDescription() != null && !work.getDescription().isEmpty()) {
                    Label descLabel = new Label(work.getDescription());
                    descLabel.setFont(Font.font(14));
                    descLabel.setStyle("-fx-text-fill: #666666; -fx-padding: 8 0 0 0;");
                    descLabel.setWrapText(true);
                    workBox.getChildren().add(descLabel);
                }

                workExperienceContent.getChildren().add(workBox);
            }
            workExperienceSection.setVisible(true);
            workExperienceSection.setManaged(true);
        }

        // Display projects
        if (!currentCV.getProjects().isEmpty()) {
            projectsContent.getChildren().clear();
            for (Project project : currentCV.getProjects()) {
                VBox projectBox = new VBox(8);
                projectBox.setStyle("-fx-padding: 0 0 20 0;");

                Label nameLabel = new Label(project.getProjectName());
                nameLabel.setFont(Font.font("System", FontWeight.BOLD, 16));
                nameLabel.setStyle("-fx-text-fill: #2c3e50;");

                projectBox.getChildren().add(nameLabel);

                if (project.getTechnologies() != null && !project.getTechnologies().isEmpty()) {
                    Label techLabel = new Label("Technologies: " + project.getTechnologies());
                    techLabel.setFont(Font.font(14));
                    techLabel.setStyle("-fx-text-fill: #555555; -fx-padding: 3 0 0 0;");
                    projectBox.getChildren().add(techLabel);
                }

                if (project.getDuration() != null && !project.getDuration().isEmpty()) {
                    Label durationLabel = new Label("Duration: " + project.getDuration());
                    durationLabel.setFont(Font.font(14));
                    durationLabel.setStyle("-fx-text-fill: #666666; -fx-font-style: italic; -fx-padding: 2 0 0 0;");
                    projectBox.getChildren().add(durationLabel);
                }

                if (project.getDescription() != null && !project.getDescription().isEmpty()) {
                    Label descLabel = new Label(project.getDescription());
                    descLabel.setFont(Font.font(14));
                    descLabel.setStyle("-fx-text-fill: #666666; -fx-padding: 8 0 0 0;");
                    descLabel.setWrapText(true);
                    projectBox.getChildren().add(descLabel);
                }

                projectsContent.getChildren().add(projectBox);
            }
            projectsSection.setVisible(true);
            projectsSection.setManaged(true);
        }
    }

    @FXML
    private void handleBackToEdit() {
        CVBuilderApplication.getInstance().showCreateCVView();
    }

    @FXML
    private void handleCreateNew() {
        CVBuilderApplication.getInstance().showHomeView();
    }
}
