package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.Tracker;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import java.util.ArrayList;
import java.util.List;

public class JdbcTrackerDao implements TrackerDao {
    private JdbcTemplate jdbcTemplate;

    public JdbcTrackerDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tracker> getAllEvents(String username) {
        List<Tracker> events = new ArrayList<>();
        int userId = getUserId(username);
        String sql = "SELECT t.tracker_id, t.user_id, t.pet_id, t.time_fed, t.food_type, t.portion_amount, t.portion_units, t.notes " +
                "FROM tracker t WHERE user_id = ?;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userId);
            while (results.next()) {
                events.add(mapRowToTracker(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.");
        }
        return events;
    }

    @Override
    public List<Tracker> getEventsByPetId(int petId) {
        List<Tracker> events = new ArrayList<>();
        return events;
    }

    @Override
    public Tracker createNewEvent(int petId) {
        Tracker loggedEvent = null;
        return loggedEvent;
    }

    @Override
    public Tracker updateEvent(Tracker eventToUpdate) {
        Tracker updatedEvent = null;
        return updatedEvent;
    }

    @Override
    public int deleteEvent(int trackerId) {
        int numberOfRowsDeleted = 0;
        return numberOfRowsDeleted;
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

    private Tracker mapRowToTracker(SqlRowSet rowSet) {
        Tracker tracker = new Tracker();
        tracker.setTrackerId(rowSet.getInt("tracker_id"));
        tracker.setUserId(rowSet.getInt("user_id"));
        tracker.setPetId(rowSet.getInt("pet_id"));
        tracker.setTimeFed(rowSet.getTimestamp("time_fed"));
        if (rowSet.getString("food_type") != null) {
            tracker.setFoodType(rowSet.getString("food_type"));
        }
        if (rowSet.getObject("portion_amount") != null) {
            tracker.setPortionAmount(rowSet.getDouble("portion_amount"));
        }
        if (rowSet.getString("portion_units") != null) {
            tracker.setPortionUnits(rowSet.getString("portion_units"));
        }
        if (rowSet.getString("notes") != null) {
            tracker.setNotes(rowSet.getString("notes"));
        }
        return tracker;
    }
}
