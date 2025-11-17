package com.imrul.cvbuilder.models;

public class Education {
    private String department;
    private String institution;
    private String year;
    private String grade;

    public Education() {
    }

    public Education(String department, String institution, String year, String grade) {
        this.department = department;
        this.institution = institution;
        this.year = year;
        this.grade = grade;
    }

    // Getters and Setters
    public String getDegree() {
        return department;
    }

    public void setDegree(String department) {
        this.department = department;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    @Override
    public String toString() {
        return department + " - " + institution + " (" + year + ")";
    }
}
