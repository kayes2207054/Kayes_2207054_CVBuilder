package com.imrul.cvbuilder.models;

public class Project {
    private String projectName;
    private String description;
    private String technologies;
    private String duration;

    public Project() {
    }

    public Project(String projectName, String description, String technologies, String duration) {
        this.projectName = projectName;
        this.description = description;
        this.technologies = technologies;
        this.duration = duration;
    }

    // Getters and Setters
    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTechnologies() {
        return technologies;
    }

    public void setTechnologies(String technologies) {
        this.technologies = technologies;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return projectName + " (" + duration + ")";
    }
}
