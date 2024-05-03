package com.harrisonmauseth.petfoodtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDate;

@JsonPropertyOrder({"id", "name", "short_name", "type", "birthday", "notes"})
public class Pet {
    @JsonProperty("id")
    private int petId;
    @JsonIgnore
    private int userId;
    @JsonProperty("name")
    private String petName;
    @JsonProperty("short_name")
    private String petNickname;
    @JsonProperty("type")
    private String petType;
    private LocalDate birthday;
    private String notes;

    public Pet() {
    }

    public Pet(int petId, int userId, String petName, String petNickname, String petType, LocalDate birthday, String notes) {
        this.petId = petId;
        this.userId = userId;
        this.petName = petName;
        this.petNickname = petNickname;
        this.petType = petType;
        this.birthday = birthday;
        this.notes = notes;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getPetName() {
        return petName;
    }

    public void setPetName(String petName) {
        this.petName = petName;
    }

    public String getPetNickname() {
        return petNickname;
    }

    public void setPetNickname(String petNickname) {
        this.petNickname = petNickname;
    }

    public String getPetType() {
        return petType;
    }

    public void setPetType(String petType) {
        this.petType = petType;
    }

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Pet{" +
                "petId=" + petId +
                ", userId=" + userId +
                ", petName='" + petName + '\'' +
                ", petNickname='" + petNickname + '\'' +
                ", petType='" + petType + '\'' +
                ", birthday=" + birthday +
                ", notes='" + notes + '\'' +
                '}';
    }
}
