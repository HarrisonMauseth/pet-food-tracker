package com.harrisonmauseth.petfoodtracker.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

public class Tracker {
    private int trackerId;
    private int userId;
    private int petId;
    private Timestamp timeFed;
    private String foodType;
    private double portionAmount;
    private String portionUnits;
    private String notes;

    public Tracker() {
    }

    public Tracker(int trackerId, int userId, int petId, Timestamp timeFed, String foodType, double portionAmount, String portionUnits, String notes) {
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

    public Timestamp getTimeFed() {
        return timeFed;
    }

    public void setTimeFed(Timestamp timeFed) {
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
