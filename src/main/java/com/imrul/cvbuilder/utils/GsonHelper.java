package com.imrul.cvbuilder.utils;

import com.google.gson.*;
import com.imrul.cvbuilder.models.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Helper class for Gson serialization/deserialization with ObservableList support
 */
public class GsonHelper {
    
    private static Gson gsonInstance;
    
    /**
     * Get a configured Gson instance with custom deserializers for ObservableList
     */
    public static Gson getGson() {
        if (gsonInstance == null) {
            GsonBuilder builder = new GsonBuilder();
            
            // Register custom deserializer for CV
            builder.registerTypeAdapter(CV.class, new CVDeserializer());
            
            gsonInstance = builder.create();
        }
        return gsonInstance;
    }
    
    /**
     * Custom deserializer for CV that properly handles ObservableList fields
     */
    private static class CVDeserializer implements JsonDeserializer<CV> {
        @Override
        public CV deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) {
            JsonObject jsonObject = json.getAsJsonObject();
            CV cv = new CV();
            
            // Deserialize simple fields
            if (jsonObject.has("id") && !jsonObject.get("id").isJsonNull()) {
                cv.setId(jsonObject.get("id").getAsInt());
            }
            if (jsonObject.has("fullName")) {
                cv.setFullName(jsonObject.get("fullName").getAsString());
            }
            if (jsonObject.has("email")) {
                cv.setEmail(jsonObject.get("email").getAsString());
            }
            if (jsonObject.has("phoneNumber")) {
                cv.setPhoneNumber(jsonObject.get("phoneNumber").getAsString());
            }
            if (jsonObject.has("address") && !jsonObject.get("address").isJsonNull()) {
                cv.setAddress(jsonObject.get("address").getAsString());
            }
            if (jsonObject.has("profilePhotoPath") && !jsonObject.get("profilePhotoPath").isJsonNull()) {
                cv.setProfilePhotoPath(jsonObject.get("profilePhotoPath").getAsString());
            }
            
            // Deserialize ObservableList fields
            if (jsonObject.has("educationList") && jsonObject.get("educationList").isJsonArray()) {
                JsonArray educationArray = jsonObject.getAsJsonArray("educationList");
                for (JsonElement element : educationArray) {
                    Education education = context.deserialize(element, Education.class);
                    cv.getEducationList().add(education);
                }
            }
            
            if (jsonObject.has("skills") && jsonObject.get("skills").isJsonArray()) {
                JsonArray skillsArray = jsonObject.getAsJsonArray("skills");
                for (JsonElement element : skillsArray) {
                    cv.getSkills().add(element.getAsString());
                }
            }
            
            if (jsonObject.has("workExperienceList") && jsonObject.get("workExperienceList").isJsonArray()) {
                JsonArray workArray = jsonObject.getAsJsonArray("workExperienceList");
                for (JsonElement element : workArray) {
                    WorkExperience work = context.deserialize(element, WorkExperience.class);
                    cv.getWorkExperienceList().add(work);
                }
            }
            
            if (jsonObject.has("projects") && jsonObject.get("projects").isJsonArray()) {
                JsonArray projectsArray = jsonObject.getAsJsonArray("projects");
                for (JsonElement element : projectsArray) {
                    Project project = context.deserialize(element, Project.class);
                    cv.getProjects().add(project);
                }
            }
            
            return cv;
        }
    }
}
