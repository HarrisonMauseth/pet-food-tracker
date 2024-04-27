package com.harrisonmauseth.petfoodtracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * LoginDto is a class used to hold the user login information that is being sent from
 * the client to the server for the login endpoint.
 */
public class LoginDto {
    private String username;
    private String password;
    @JsonProperty("remember_me")
    private boolean remembered;

    public LoginDto() {
    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public LoginDto(String username, String password, boolean remembered) {
        this.username = username;
        this.password = password;
        this.remembered = remembered;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isRemembered() {
        return remembered;
    }

    public void setRemembered(boolean remembered) {
        this.remembered = remembered;
    }
}
