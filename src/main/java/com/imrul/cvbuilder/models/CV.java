package com.imrul.cvbuilder.models;

import com.google.gson.Gson;
import com.imrul.cvbuilder.utils.GsonHelper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class CV {
    private Integer id; // Database ID
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String profilePhotoPath;

    private ObservableList<Education> educationList;
    private ObservableList<String> skills;
    private ObservableList<WorkExperience> workExperienceList;
    private ObservableList<Project> projects;

    public CV() {
        this.educationList = FXCollections.observableArrayList();
        this.skills = FXCollections.observableArrayList();
        this.workExperienceList = FXCollections.observableArrayList();
        this.projects = FXCollections.observableArrayList();
    }

    // Getters and Setters
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getProfilePhotoPath() {
        return profilePhotoPath;
    }

    public void setProfilePhotoPath(String profilePhotoPath) {
        this.profilePhotoPath = profilePhotoPath;
    }

    public ObservableList<Education> getEducationList() {
        return educationList;
    }

    public ObservableList<String> getSkills() {
        return skills;
    }

    public ObservableList<WorkExperience> getWorkExperienceList() {
        return workExperienceList;
    }

    public ObservableList<Project> getProjects() {
        return projects;
    }

    public boolean isValid() {
        return fullName != null && !fullName.trim().isEmpty() &&
                email != null && !email.trim().isEmpty() &&
                phoneNumber != null && !phoneNumber.trim().isEmpty();
    }

    /**
     * Convert CV to JSON string
     */
    public String toJSON() {
        return GsonHelper.getGson().toJson(this);
    }

    /**
     * Create CV from JSON string
     */
    public static CV fromJSON(String json) {
        return GsonHelper.getGson().fromJson(json, CV.class);
    }

    @Override
    public String toString() {
        return fullName + " (" + email + ")";
    }
}
