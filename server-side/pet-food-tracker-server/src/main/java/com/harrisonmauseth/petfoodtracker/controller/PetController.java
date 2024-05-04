package com.harrisonmauseth.petfoodtracker.controller;

import com.harrisonmauseth.petfoodtracker.dao.PetDao;

import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.Pet;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.List;

@RestController
@CrossOrigin
@PreAuthorize("isAuthenticated()")
@RequestMapping("/pet")
public class PetController {
    private PetDao petDao;

    public PetController(PetDao dao) {
        this.petDao = dao;
    }

    @GetMapping()
    public List<Pet> list(Principal principal) {
        return petDao.getPets(principal.getName());
    }

    @PostMapping()
    public Pet addPet(@Valid @RequestBody Pet pet, Principal principal) {
        Pet createdPet = null;
        try {
            createdPet = petDao.createPet(pet, principal.getName());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Database Error - " + e.getMessage());
        }
        return createdPet;
    }

}
