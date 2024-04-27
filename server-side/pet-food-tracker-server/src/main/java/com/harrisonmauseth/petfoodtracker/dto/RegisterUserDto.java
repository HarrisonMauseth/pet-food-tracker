package com.harrisonmauseth.petfoodtracker.dto;

import jakarta.validation.constraints.NotEmpty;

/**
 * RegisteredUserDto is a class used to hold the registration information for a new user
 * that is sent from the client to the server for the register endpoint.
 */
public class RegisterUserDto {

    @NotEmpty
    private String username;
    @NotEmpty
    private String password;

    public RegisterUserDto() {
    }

    public RegisterUserDto(String username, String password) {
        this.username = username;
        this.password = password;
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

}
