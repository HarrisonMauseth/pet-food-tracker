package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.model.Tracker;

import java.util.List;

public interface TrackerDao {

    /**
     * Get all the pet feeding events for all of a given user's pets, ordered chronologically, descending.
     *
     * @param username the authenticated user's username
     * @return a list of all feeding events for all the user's pets
     */
    List<Tracker> getAllEvents(String username);

    /**
     * Get all the pet feeding events for a specific pet, ordered chronologically, descending.
     *
     * @param petId the id of the pet whose feeding events you wish to get
     * @return a list of feeding events for a specific pet
     */
    List<Tracker> getEventsByPetId(int petId);

    /**
     * Get a specific tracker event.
     *
     * @param trackerId the id of the tracker event you wish to retrieve
     * @return the specified tracker event
     */
    Tracker getEventByTrackerId(int trackerId);

    /**
     * Log a pet feeding event in the database.
     *
     * @param eventToCreate the event to add to the database
     * @return the feeding event data
     */
    Tracker createNewEvent(Tracker eventToCreate);

    /**
     * Update a pet feeding event in the database.
     *
     * @param eventToUpdate the event to update in the database
     * @return the updated feeding event data
     */
    Tracker updateEvent(Tracker eventToUpdate);

    /**
     * Delete a pet feeding event from the database.
     *
     * @param trackerId the id of the event you wish to delete
     * @return the number of events deleted
     */
    int deleteEvent(int trackerId);

}
