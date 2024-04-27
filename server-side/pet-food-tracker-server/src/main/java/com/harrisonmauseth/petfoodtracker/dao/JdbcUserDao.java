package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.UserEntity;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
public class JdbcUserDao implements UserDao {

    private final JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<UserEntity> getUsers() {
        List<UserEntity> users = new ArrayList<>();
        String sql = "SELECT * FROM users ORDER BY username;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
            while (results.next()) {
                UserEntity user = mapRowToUser(results);
                users.add(user);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return users;
    }

    @Override
    public UserEntity getUserById(int userId) {
        UserEntity user = null;
        String sql = "SELECT * FROM users WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public UserEntity getUserByUsername(String username) {
        if (username == null) {
            username = "";
        }
        UserEntity user = null;
        String sql = "SELECT * FROM users WHERE username = LOWER(TRIM(?));";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
            if (results.next()) {
                user = mapRowToUser(results);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        }
        return user;
    }

    @Override
    public UserEntity createUser(UserEntity newUser) {
        UserEntity user = null;
        String insertUserSql = "INSERT INTO users (username, password_hash, role) VALUES (LOWER(TRIM(?)), ?, ?) RETURNING user_id;";
        if (newUser.getPassword() == null) {
            throw new DaoException("User cannot be created with null password");
        }
        try {
            int userId = jdbcTemplate.queryForObject(insertUserSql, int.class, newUser.getUsername(), newUser.getPassword(), newUser.getRolesAsString());
            user = getUserById(userId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to server or database", e);
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation", e);
        }
        return user;
    }

    @Override
    public boolean existsByUsername(String username) {
        UserEntity user = getUserByUsername(username);
        return user != null;
    }

    private UserEntity mapRowToUser(SqlRowSet rowSet) {
        UserEntity user = new UserEntity();
        user.setId(rowSet.getInt("user_id"));
        user.setUsername(rowSet.getString("username"));
        user.setPassword(rowSet.getString("password_hash"));
        user.setRoles(Objects.requireNonNull(rowSet.getString("role")));
        user.setActivated(true);
        return user;
    }

}
