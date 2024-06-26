package com.harrisonmauseth.petfoodtracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

@JsonPropertyOrder({"pet_id", "user_id", "name", "shortened_name", "type", "birthday", "image_path", "notes"})
public class Pet {
    @JsonProperty("pet_id")
    private int petId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("name")
    @NotEmpty(message = "Pet must have name")
    @Size(max = 50, message = "Pet name cannot exceed 50 characters")
    @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Pet name can only contain letters, numbers, and spaces")
    private String petName;
    @JsonProperty("shortened_name")
    @Size(max = 8, message = "Shortened pet name must be 8 characters or less for small display")
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Shortened pet name can only contain letters, numbers, and spaces")
    private String petNickname;
    @JsonProperty("type")
    @NotEmpty
    private String petType;
    @Past
    private LocalDate birthday;
    @JsonProperty("image_path")
    private String imagePath;
    private String notes;

    public Pet() {
    }

    public Pet(int petId, int userId, String petName, String petNickname, String petType, LocalDate birthday, String imagePath, String notes) {
        this.petId = petId;
        this.userId = userId;
        this.petName = petName;
        this.petNickname = petNickname;
        this.petType = petType;
        this.birthday = birthday;
        this.imagePath = imagePath;
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

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
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
