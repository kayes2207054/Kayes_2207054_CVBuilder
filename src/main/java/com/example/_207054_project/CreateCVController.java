package com.example._207054_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.event.ActionEvent;

public class CreateCVController {

    @FXML private TextField fullName;
    @FXML private TextField email;
    @FXML private TextField phone;
    @FXML private TextArea address;
    @FXML private TextArea education;
    @FXML private TextArea skills;
    @FXML private TextArea experience;
    @FXML private TextArea projects;

    @FXML
    public void generateCV(ActionEvent e) throws Exception {

        CVModel cv = new CVModel();
        cv.setFullName(fullName.getText());
        cv.setEmail(email.getText());
        cv.setPhone(phone.getText());
        cv.setAddress(address.getText());
        cv.setEducation(education.getText());
        cv.setSkills(skills.getText());
        cv.setExperience(experience.getText());
        cv.setProjects(projects.getText());

        FXMLLoader loader = new FXMLLoader(getClass().getResource("preview.fxml"));
        Scene scene = new Scene(loader.load());
        scene.getStylesheets().add(getClass().getResource("style.css").toExternalForm());

        PreviewController controller = loader.getController();
        controller.setData(cv);

        Stage stage = (Stage)((Node)e.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
}
