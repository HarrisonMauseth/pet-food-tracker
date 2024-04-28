package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.model.Pet;

import java.util.List;

public interface PetDao {

    /**
     * Gets all pets belonging to a specific user.
     *
     * @param username the authenticated user's username
     * @return a list of all pets belonging to the user
     */
    List<Pet> getPets(String username);

    /**
     * Get a specific pet.
     *
     * @param petId the id of the pet you wish to get
     * @return the retrieved pet object
     */
    List<Pet> getPetByPetId(int petId);

    /**
     * Insert a new pet into the database.
     *
     * @param pet the pet object to create
     * @return the pet object with its new id filled in
     */
    Pet createPet(Pet pet);

    /**
     * Updates an existing pet in the database.
     *
     * @param pet the pet object to update
     * @return the pet object with its updated fields
     */
    Pet updatePet(Pet pet);

    /**
     * Removes a pet from the database.
     *
     * @param petId the id of the pet to remove
     * @return the number of pets deleted
     */
    int deletePetByPetId(int petId);

}