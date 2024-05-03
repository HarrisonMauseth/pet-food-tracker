package com.harrisonmauseth.petfoodtracker.controller;

import com.harrisonmauseth.petfoodtracker.dao.PetDao;

import com.harrisonmauseth.petfoodtracker.model.Pet;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
