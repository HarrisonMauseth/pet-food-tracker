package com.harrisonmauseth.petfoodtracker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Model class representing an application user.Contains information about the user -
 * their id, username, name, password (hashed) and roles.
 */
public class UserEntity {

    private int id;
    private String username;
    @JsonIgnore
    private String password;
    @JsonIgnore
    private boolean activated;
    private Set<Role> roles = new HashSet<>();

    public UserEntity() {
    }

    public UserEntity(int id, String username, String password, String roles) {
        this.id = id;
        this.username = username;
        this.password = password;
        if (roles != null) {
            this.setRoles(roles);
        }
        this.activated = true;
    }

    public UserEntity(String username, String password, String roles) {
        this(0, username, password, roles);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public boolean isActivated() {
        return activated;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    @JsonIgnore
    public Set<Role> getRoles() {
        return roles;
    }

    @JsonProperty("roles")
    public String getRolesAsString() {
        String authorityString = "";
        for (Role authority : roles) {
            if (authorityString.length() == 0) {
                authorityString += authority.getName();
            } else {
                authorityString += "," + authority.getName();
            }
        }
        return authorityString;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public void setRoles(String roles) {
        String[] auths = roles.split(",");
        for (String auth : auths) {
            String authority = auth.contains("ROLE_") ? auth : "ROLE_" + auth.toUpperCase();
            this.roles.add(new Role(authority));
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserEntity user = (UserEntity) o;
        return id == user.id &&
                activated == user.activated &&
                Objects.equals(username, user.username) &&
                Objects.equals(password, user.password) &&
                Objects.equals(roles, user.roles);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, activated);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", activated=" + activated +
//                ", authorities=" + authorities +
                '}';
    }
}
