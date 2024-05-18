package com.harrisonmauseth.petfoodtracker.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.harrisonmauseth.petfoodtracker.model.UserEntity;

/**
 * LoginResponseDto is a class used to hold both the authentication token and the user
 * information that's returned from the server to the client from a login endpoint.
 */
public class LoginResponseDto {

    private String token;
    private UserEntity user;

    public LoginResponseDto(String token, UserEntity user) {
        this.token = token;
        this.user = user;
    }

    @JsonProperty("token")
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @JsonProperty("user")
    public UserEntity getUser() {
        return user;
    }

    public void setUser(UserEntity user) {
        this.user = user;
    }
}
