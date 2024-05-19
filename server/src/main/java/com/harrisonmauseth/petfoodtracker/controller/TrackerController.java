package com.harrisonmauseth.petfoodtracker.controller;

import com.harrisonmauseth.petfoodtracker.dao.TrackerDao;
import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.Tracker;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping("/log")
public class TrackerController {

    private TrackerDao trackerDao;

    public TrackerController(TrackerDao dao) {
        this.trackerDao = dao;
    }

    @GetMapping
    public List<Tracker> getAllLogs(Principal principal) {
        List<Tracker> logs;
        try {
            logs = trackerDao.getAllEvents(principal.getName());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
        if (!logs.isEmpty()) {
            return logs;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No events found");
        }
    }

    @GetMapping(path = "/{id}")
    public List<Tracker> getLogsByPetId(@PathVariable int id) {
        List<Tracker> logs;
        try {
            logs = trackerDao.getEventsByPetId(id);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
        if (!logs.isEmpty()) {
            return logs;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No events found (pet_id: " + id + ")");
        }
    }

}
