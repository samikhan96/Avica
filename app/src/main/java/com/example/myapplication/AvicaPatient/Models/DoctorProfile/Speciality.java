
package com.example.myapplication.AvicaPatient.Models.DoctorProfile;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Speciality {

    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("institute_name")
    @Expose
    private String instituteName;
    @SerializedName("graduation_date")
    @Expose
    private String graduationDate;
    @SerializedName("working_from")
    @Expose
    private String workingFrom;
    @SerializedName("uri")
    @Expose
    private List<Object> uri;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getInstituteName() {
        return instituteName;
    }

    public void setInstituteName(String instituteName) {
        this.instituteName = instituteName;
    }

    public String getGraduationDate() {
        return graduationDate;
    }

    public void setGraduationDate(String graduationDate) {
        this.graduationDate = graduationDate;
    }

    public String getWorkingFrom() {
        return workingFrom;
    }

    public void setWorkingFrom(String workingFrom) {
        this.workingFrom = workingFrom;
    }

    public List<Object> getUri() {
        return uri;
    }

    public void setUri(List<Object> uri) {
        this.uri = uri;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

}
