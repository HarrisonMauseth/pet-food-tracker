package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.Pet;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcPetDao implements PetDao {

    private JdbcTemplate jdbcTemplate;

    public JdbcPetDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Pet> getPets(String username) {
        List<Pet> pets = new ArrayList<>();
        int userId = getUserId(username);
        String sql = "SELECT p.pet_id, p.user_id, p.pet_name, p.pet_nickname, p.pet_type, p.pet_birthday, p.notes " +
                "FROM pet p WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                pets.add(mapRowToPet(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.");
        }
        return pets;
    }

    @Override
    public Pet getPetByPetId(int petId) {
        return null;
    }

    @Override
    public Pet createPet(Pet pet) {
        return null;
    }

    @Override
    public Pet updatePet(Pet pet) {
        return null;
    }

    @Override
    public int deletePetByPetId(int petId) {
        return 0;
    }

    private int getUserId(String username) {
        int userId = 0;
        String sql = "SELECT user_id FROM users WHERE username = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, username);
            if (result.next()) {
                userId = result.getInt("user_id");
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database");
        }
        return userId;
    }

    private Pet mapRowToPet(SqlRowSet rowSet) {
        Pet pet = new Pet();
        pet.setPetId(rowSet.getInt("pet_id"));
        pet.setUserId(rowSet.getInt("user_id"));
        pet.setPetName(rowSet.getString("pet_name"));
        if (rowSet.getString("pet_nickname") != null) {
            pet.setPetNickname(rowSet.getString("pet_nickname"));
        }
        pet.setPetType(rowSet.getString("pet_type"));
        if (rowSet.getDate("pet_birthday") != null) {
            pet.setBirthday(rowSet.getDate("pet_birthday").toLocalDate());
        }
        if (rowSet.getString("notes") != null) {
            pet.setNotes(rowSet.getString("notes"));
        }
        return pet;
    }
}
