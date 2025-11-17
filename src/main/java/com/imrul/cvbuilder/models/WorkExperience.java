package com.imrul.cvbuilder.models;

public class WorkExperience {
    private String jobTitle;
    private String company;
    private String duration;
    private String description;

    public WorkExperience() {
    }

    public WorkExperience(String jobTitle, String company, String duration, String description) {
        this.jobTitle = jobTitle;
        this.company = company;
        this.duration = duration;
        this.description = description;
    }

    // Getters and Setters
    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return jobTitle + " at " + company + " (" + duration + ")";
    }
}
