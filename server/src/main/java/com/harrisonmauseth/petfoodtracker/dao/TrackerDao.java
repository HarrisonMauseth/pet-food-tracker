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
     * Get all the pet feeding events for a specific user's specific pet, ordered chronologically, descending.
     *
     * @param petId    the id of the pet whose feeding events you wish to get
     * @param username username of the user attempting to access the information
     * @return a list of feeding events for a specific pet
     */
    List<Tracker> getEventsByPetId(int petId, String username);

    /**
     * Get a specific tracker event.
     *
     * @param trackerId the id of the tracker event you wish to retrieve
     * @return the specified tracker event
     */
    Tracker getEventByTrackerId(int trackerId);

    /**
     * Log a user's pet feeding event in the database.
     *
     * @param eventToCreate the event to add to the database
     * @param username      the username of the user wishing to log an event
     * @return the feeding event data
     */
    Tracker createNewEvent(Tracker eventToCreate, String username);

    /**
     * Log multiple events for a user simultaneously
     *
     * @param events   an array of events to add to the database
     * @param username the username of the user wishing to log an event
     * @return a list of all the feeding events that were created
     */
    List<Tracker> createNewEvents(Tracker[] events, String username);

    /**
     * Update a user's pet feeding event in the database.
     *
     * @param eventToUpdate the event to update in the database
     * @param username      the username of the user wishing to update an event
     * @return the updated feeding event data
     */
    Tracker updateEvent(Tracker eventToUpdate, String username);

    /**
     * Delete a user's pet feeding event from the database.
     *
     * @param trackerId the id of the event you wish to delete
     * @param username  the username of the user wishing to delete an event
     * @return the number of events deleted
     */
    int deleteEvent(int trackerId, String username);

}
