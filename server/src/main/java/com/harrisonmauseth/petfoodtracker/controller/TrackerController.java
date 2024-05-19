package com.harrisonmauseth.petfoodtracker.controller;

import com.harrisonmauseth.petfoodtracker.dao.TrackerDao;
import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.Tracker;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
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

    @GetMapping(path = "/pet/{id}")
    public List<Tracker> getLogsByPetId(@PathVariable int id, Principal principal) {
        List<Tracker> logs;
        try {
            logs = trackerDao.getEventsByPetId(id, principal.getName());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
        if (!logs.isEmpty()) {
            return logs;
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No events found (pet_id: " + id + ")");
        }
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Tracker log(@Valid @RequestBody Tracker trackerToCreate, Principal principal) {
        Tracker createdLog;
        try {
            createdLog = trackerDao.createNewEvent(trackerToCreate, principal.getName());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
        if (createdLog != null) {
            return createdLog;
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Unable to log feeding event.");
        }
    }

    @PutMapping(path = "/{id}")
    public Tracker updateLog(@Valid @RequestBody Tracker trackerToUpdate, @PathVariable int id, Principal principal) {
        trackerToUpdate.setTrackerId(id);
        try {
            return trackerDao.updateEvent(trackerToUpdate, principal.getName());
        } catch (DaoException e) {
            if (e.getMessage().equals("Zero rows affected, expected at least one.")) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Event (log_id: " + id + ") not found.");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
            }
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deleteLog(@PathVariable int id, Principal principal) {
        try {
            int rowsAffected = trackerDao.deleteEvent(id, principal.getName());
            if (rowsAffected == 0) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Event (log_id: " + id + ") not found.");
            }
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }
}
