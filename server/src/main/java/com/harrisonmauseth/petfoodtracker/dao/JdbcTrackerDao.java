package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.Tracker;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Component
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
                "FROM tracker t WHERE user_id = ? ORDER BY t.time_fed DESC;";
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
    public List<Tracker> getEventsByPetId(int petId, String username) {
        List<Tracker> events = new ArrayList<>();
        int userId = getUserId(username);
        String sql = "SELECT t.tracker_id, t.user_id, t.pet_id, t.time_fed, t.food_type, t.portion_amount, t.portion_units, t.notes " +
                "FROM tracker t WHERE pet_id = ? AND user_id = ? ORDER BY t.time_fed DESC;";
        try {
            SqlRowSet results = jdbcTemplate.queryForRowSet(sql, petId, userId);
            while (results.next()) {
                events.add(mapRowToTracker(results));
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.");
        }
        return events;
    }

    @Override
    public Tracker getEventByTrackerId(int trackerId) {
        Tracker event = null;
        String sql = "SELECT t.tracker_id, t.user_id, t.pet_id, t.time_fed, t.food_type, t.portion_amount, t.portion_units, t.notes " +
                "FROM tracker t WHERE tracker_id = ?;";
        try {
            SqlRowSet result = jdbcTemplate.queryForRowSet(sql, trackerId);
            if (result.next()) {
                event = mapRowToTracker(result);
            }
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.");
        }
        return event;
    }

    @Override
    public Tracker createNewEvent(Tracker eventToCreate, String username) {
        Tracker loggedEvent;
        int userId = getUserId(username);
        if (eventToCreate.getTimeFed() == null) {
            eventToCreate.setTimeFed(LocalDateTime.now());
        }
        String sql = "INSERT INTO tracker (user_id, pet_id, time_fed, food_type, portion_amount, portion_units, notes) " +
                "VALUES (?, ?, ?, ?, ?, ?, ?) RETURNING tracker_id;";
        try {
            int trackerId = jdbcTemplate.queryForObject(
                    sql,
                    int.class,
                    userId,
                    eventToCreate.getPetId(),
                    eventToCreate.getTimeFed(),
                    eventToCreate.getFoodType(),
                    eventToCreate.getPortionAmount(),
                    eventToCreate.getPortionUnits(),
                    eventToCreate.getNotes()
            );
            loggedEvent = getEventByTrackerId(trackerId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation");
        }
        return loggedEvent;
    }

    @Override
    public List<Tracker> createNewEvents(Tracker[] events, String username) {
        List<Tracker> createdEvents = new ArrayList<>();
        for (Tracker event : events) {
            createdEvents.add(0, createNewEvent(event, username));
        }
        return createdEvents;
    }

    @Override
    public Tracker updateEvent(Tracker eventToUpdate, String username) {
        Tracker updatedEvent;
        int userId = getUserId(username);
        String sql = "UPDATE tracker SET user_id = ?, pet_id = ?, time_fed = ?, food_type = ?, portion_amount = ?, " +
                "portion_units = ?, notes = ? " +
                "WHERE tracker_id = ? AND user_id = ?;";
        try {
            int numberOfRowsAffected = jdbcTemplate.update(
                    sql,
                    userId,
                    eventToUpdate.getPetId(),
                    eventToUpdate.getTimeFed(),
                    eventToUpdate.getFoodType(),
                    eventToUpdate.getPortionAmount(),
                    eventToUpdate.getPortionUnits(),
                    eventToUpdate.getNotes(),
                    eventToUpdate.getTrackerId(),
                    userId
            );
            if (numberOfRowsAffected == 0) {
                throw new DaoException("Zero rows affected, expected at least one.");
            } else updatedEvent = getEventByTrackerId(eventToUpdate.getTrackerId());
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation");
        }
        return updatedEvent;
    }

    @Override
    public int deleteEvent(int trackerId, String username) {
        int numberOfRowsDeleted;
        int userId = getUserId(username);
        String sql = "DELETE FROM tracker WHERE tracker_id = ? AND user_id = ?;";
        try {
            numberOfRowsDeleted = jdbcTemplate.update(sql, trackerId, userId);
        } catch (CannotGetJdbcConnectionException e) {
            throw new DaoException("Unable to connect to database.");
        } catch (DataIntegrityViolationException e) {
            throw new DaoException("Data integrity violation");
        }
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
        if (rowSet.getTimestamp("time_fed") != null) {
            tracker.setTimeFed(rowSet.getTimestamp("time_fed").toLocalDateTime());
        }
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
