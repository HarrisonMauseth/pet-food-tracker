package com.harrisonmauseth.petfoodtracker.controller;

import com.harrisonmauseth.petfoodtracker.dao.PetDao;

import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.Pet;
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

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public Pet addPet(@Valid @RequestBody Pet pet, Principal principal) {
        Pet createdPet = null;
        try {
            createdPet = petDao.createPet(pet, principal.getName());
        } catch (DaoException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
        }
        return createdPet;
    }

    @PutMapping(path = "/{id}")
    public Pet updatePet(@Valid @RequestBody Pet pet, @PathVariable int id, Principal principal) {
        pet.setPetId(id);
        try {
            return petDao.updatePet(pet, principal.getName());
        } catch (DaoException e) {
            if (e.getMessage().equals("Zero rows affected, expected at least one.")) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pet id " + id + "not found.");
            } else {
                throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Server error: " + e.getMessage());
            }
        }
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping(path = "/{id}")
    public void deletePet(@PathVariable int id, Principal principal) {
        petDao.deletePetByPetId(id, principal.getName());
    }

}
