package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.model.Tracker;
import org.springframework.jdbc.core.JdbcTemplate;

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
}
