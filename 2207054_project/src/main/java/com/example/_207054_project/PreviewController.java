package com.example._207054_project;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PreviewController {

    @FXML private Label nameLbl;
    @FXML private Label emailLbl;
    @FXML private Label phoneLbl;
    @FXML private Label addressLbl;
    @FXML private Label educationLbl;
    @FXML private Label skillsLbl;
    @FXML private Label experienceLbl;
    @FXML private Label projectsLbl;

    public void setData(CVModel cv) {
        nameLbl.setText(cv.getFullName());
        emailLbl.setText("Email: " + cv.getEmail());
        phoneLbl.setText("Phone: " + cv.getPhone());
        addressLbl.setText("Address: " + cv.getAddress());
        educationLbl.setText(cv.getEducation());
        skillsLbl.setText(cv.getSkills());
        experienceLbl.setText(cv.getExperience());
        projectsLbl.setText(cv.getProjects());
    }
}
