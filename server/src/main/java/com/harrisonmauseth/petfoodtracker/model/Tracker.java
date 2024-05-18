package com.harrisonmauseth.petfoodtracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({"log_id", "user_id", "pet_id", "time_fed", "food_type", "portion_size", "portion_units", "notes"})
public class Tracker {
    @JsonProperty("log_id")
    private int trackerId;
    @JsonProperty("user_id")
    private int userId;
    @JsonProperty("pet_id")
    private int petId;
    @JsonProperty("time_fed")
    private LocalDateTime timeFed;
    @JsonProperty("food_type")
    private String foodType;
    @JsonProperty("portion_size")
    private double portionAmount;
    @JsonProperty("portion_units")
    private String portionUnits;
    private String notes;

    public Tracker() {
    }

    public Tracker(int trackerId, int userId, int petId, LocalDateTime timeFed, String foodType, double portionAmount, String portionUnits, String notes) {
        this.trackerId = trackerId;
        this.userId = userId;
        this.petId = petId;
        this.timeFed = timeFed;
        this.foodType = foodType;
        this.portionAmount = portionAmount;
        this.portionUnits = portionUnits;
        this.notes = notes;
    }

    public int getTrackerId() {
        return trackerId;
    }

    public void setTrackerId(int trackerId) {
        this.trackerId = trackerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPetId() {
        return petId;
    }

    public void setPetId(int petId) {
        this.petId = petId;
    }

    public LocalDateTime getTimeFed() {
        return timeFed;
    }

    public void setTimeFed(LocalDateTime timeFed) {
        this.timeFed = timeFed;
    }

    public String getFoodType() {
        return foodType;
    }

    public void setFoodType(String foodType) {
        this.foodType = foodType;
    }

    public double getPortionAmount() {
        return portionAmount;
    }

    public void setPortionAmount(double portionAmount) {
        this.portionAmount = portionAmount;
    }

    public String getPortionUnits() {
        return portionUnits;
    }

    public void setPortionUnits(String portionUnits) {
        this.portionUnits = portionUnits;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    @Override
    public String toString() {
        return "Tracker{" +
                "trackerId=" + trackerId +
                ", userId=" + userId +
                ", petId=" + petId +
                ", timeFed=" + timeFed +
                ", foodType='" + foodType + '\'' +
                ", portionAmount=" + portionAmount +
                ", portionUnits='" + portionUnits + '\'' +
                ", notes='" + notes + '\'' +
                '}';
    }
}
