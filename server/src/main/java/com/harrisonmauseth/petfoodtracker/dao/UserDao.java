package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.model.UserEntity;

import java.util.List;

/**
 * The UserDao interface provides a means for a Data Access Object to be created while
 * maintaining loosely coupled code, offering datastore flexibility, and providing
 * a simple way to test the integration without needing to use the data. Primarily,
 * the UserDao is intended to be used with the AuthenticationController so user
 * credentials can be securely saved outside of memory.
 */
public interface UserDao {

    /**
     * Get all users from the database, ordered by username.
     *
     * @return All users as UserEntity objects in a List.
     */
    List<UserEntity> getUsers();

    /**
     * Get a specific user from the database with the given id.
     *
     * @param userId The id of the user.
     * @return A filled out UserEntity object, null if the userId isn't in the database.
     */
    UserEntity getUserById(int userId);

    /**
     * Get a specific user from the database with the given username.
     *
     * @param username The username of the user.
     * @return A filled out UserEntity object, null if the username isn't in the database.
     */
    UserEntity getUserByUsername(String username);

    /**
     * Register a new user by passing in a UserEntity object.
     *
     * IMPORTANT: The intent is that the password hashing will take place in the
     * AuthenticationController. Passwords that have not been hashed should not be stored
     * for obvious security reasons.
     *
     * @param newUser the UserEntity to add to the database.
     * @return The added UserEntity with its new user_id filled in.
     */
    UserEntity createUser(UserEntity newUser);

    /**
     * Verify if the user exists in the database.
     *
     * @param username The username to check.
     * @return true if the user is in the database, otherwise return false.
     */
    boolean existsByUsername (String username);
}
