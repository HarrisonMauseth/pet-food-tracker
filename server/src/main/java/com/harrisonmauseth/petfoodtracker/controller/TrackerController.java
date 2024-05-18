package com.harrisonmauseth.petfoodtracker.controller;

import com.harrisonmauseth.petfoodtracker.dao.TrackerDao;
import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.Tracker;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

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

    @GetMapping()
    public List<Tracker> getEvenetsByPetId(int petId) {
        try {
            return trackerDao.getEventsByPetId(petId);
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
    }

}
