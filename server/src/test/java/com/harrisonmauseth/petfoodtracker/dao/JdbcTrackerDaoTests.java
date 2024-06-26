package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.model.Tracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class JdbcTrackerDaoTests extends BaseDaoTests {
    private static final Tracker TRACKER_1 = new Tracker(1, 1, 1, LocalDateTime.parse("2000-01-01T01:00:00"),
            "food1", 1, "unit1", "notes1");
    private static final Tracker TRACKER_2 = new Tracker(2, 2, 2, LocalDateTime.parse("2000-02-02T02:00:00"),
            "food2", 2, "unit2", "notes2");
    private static final Tracker TRACKER_3 = new Tracker(3, 3, 3, LocalDateTime.parse("2000-03-03T03:00:00"),
            "food3", 3, "unit3", "notes3");
    private static final Tracker TRACKER_4 = new Tracker(4, 1, 1, LocalDateTime.parse("2000-04-04T04:00:00"),
            "food1", 1, "unit1", "same food, different time");
    private static final String USERNAME_1 = "user1";
    private static final String USERNAME_2 = "user2";
    private static final String USERNAME_3 = "user3";
    List<Tracker> events = new ArrayList<>();
    private JdbcTrackerDao dao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTrackerDao(jdbcTemplate);
    }

    @Test
    public void getAllEvents_returns_correct_number_of_feeding_events() {
        events = dao.getAllEvents(USERNAME_1);
        Assert.assertNotNull("getAllEvents() returned null instead of a list.", events);
        Assert.assertEquals("getEvents() did not return correct number of feeding events.", 2, events.size());
    }

    @Test
    public void getAllEvents_returns_correct_pets_for_specified_user() {
        events = dao.getAllEvents(USERNAME_2);
        Assert.assertNotNull("getAllEvents() returned null instead of a list.", events);
        Assert.assertFalse("getAllEvents() did not return any events.", events.isEmpty());
        assertEventsMatch("getAllEvents() did not return correct event:", TRACKER_2, events.get(0));
    }

    @Test
    public void getEventsByPetId_returns_correct_number_of_feeding_events() {
        events = dao.getEventsByPetId(TRACKER_1.getPetId(), "user1");
        Assert.assertNotNull("getEventsByPetId() returned null instead of a list.", events);
        Assert.assertFalse("getEventsByPetId() did not return any events.", events.isEmpty());
        Assert.assertEquals("getEventsByPetId() did not return correct number of feeding events.", 2, events.size());
    }

    @Test
    public void getEventsByPetId_returns_correct_event() {
        events = dao.getEventsByPetId(TRACKER_3.getPetId(), "user3");
        Assert.assertNotNull("getEventsByPetId() returned null instead of a list.", events);
        Assert.assertFalse("getEventsByPetId() did not return any events.", events.isEmpty());
        assertEventsMatch("getEventsByPetId() did not return correct event.", TRACKER_3, events.get(0));
    }

    @Test
    public void getEventByTrackerId_returns_correct_event() {
        Tracker event = dao.getEventByTrackerId(TRACKER_4.getTrackerId());
        Assert.assertNotNull("getEventsByTrackerId() returned null instead of an event.", event);
        assertEventsMatch("getEventByTrackerId()", TRACKER_4, event);
    }

    @Test
    public void createNewEvent_creates_event() {
        Tracker eventToCreate = new Tracker(0, 1, 1, LocalDateTime.parse("2000-05-05T05:00:00"),
                "food1", 1, "unit1", "same food, different time, new event'");

        Tracker createdEvent = dao.createNewEvent(eventToCreate, "user1");
        Assert.assertNotNull("createNewEvent() returned a null event.", createdEvent);
        Assert.assertTrue("createNewEvent() did not return a pet with the id set.", createdEvent.getPetId() > 0);

        // Set the eventToCreate trackerId to match the createdEvent trackerId, then compare values
        eventToCreate.setTrackerId(createdEvent.getTrackerId());
        assertEventsMatch("created events do not match:", eventToCreate, createdEvent);

        // Verify that the event can be retrieved from the database
        Tracker retrievedEvent = dao.getEventByTrackerId(createdEvent.getTrackerId());
        assertEventsMatch("created event did not store properly within the database", createdEvent, retrievedEvent);
    }

    @Test
    public void createNewEvents_creates_correctly_creates_events() {
        Tracker newEvent1 = new Tracker(5, 1, 1, LocalDateTime.parse("2020-01-01T01:11:11"),
                "food1", 1, "unit1", "newEvent1");
        Tracker newEvent2 = new Tracker(6, 1, 1, LocalDateTime.parse("2020-01-02T02:22:22"),
                "food1", 1, "unit1", "newEvent2");
        Tracker newEvent3 = new Tracker(7, 1, 1, LocalDateTime.parse("2020-01-03T03:33:33"),
                "food1", 1, "unit1", "newEvent3");
        Tracker[] events = new Tracker[]{newEvent1, newEvent2, newEvent3};

        List<Tracker> createdEvents = dao.createNewEvents(events, USERNAME_1);
        Assert.assertNotNull("createNewEvents() returned a nul list of events.", createdEvents);
        Assert.assertEquals("createNewEvents() did not return the correct number of events.", 3, createdEvents.size());
    }

    @Test
    public void updateEvent_updates_event() {
        Tracker eventToUpdate = new Tracker(4, 1, 2, LocalDateTime.parse("2000-05-05T05:55:55"),
                "updatedFood", 2, "updatedUnit", "updated notes");

        Tracker updatedEvent = dao.updateEvent(eventToUpdate, "user1");
        Assert.assertNotNull("updateEvent() returned a null event.", updatedEvent);
        assertEventsMatch("updateEvent() returned an incorrect or incomplete event:", eventToUpdate, updatedEvent);

        // Verify the event was saved to the database and can be retrieved.
        Tracker retrievedEvent = dao.getEventByTrackerId(updatedEvent.getTrackerId());
        assertEventsMatch("updateEvent() updated but failed to save to the database:", updatedEvent, retrievedEvent);
    }

    @Test
    public void deleteEvent_deletes_event() {
        int rowsDeleted = dao.deleteEvent(TRACKER_4.getTrackerId(), "user1");
        Assert.assertEquals("deleteEvent() did not delete the correct number of rows.", 1, rowsDeleted);
        Tracker retrievedEvent = dao.getEventByTrackerId(TRACKER_4.getTrackerId());
        Assert.assertNull("deleteEvent() did not remove the event from the database.", retrievedEvent);
    }

    private void assertEventsMatch(String methodInvoked, Tracker expected, Tracker actual) {
        Assert.assertEquals(methodInvoked + " trackerIds do not match.", expected.getTrackerId(), actual.getTrackerId());
        Assert.assertEquals(methodInvoked + " userIds do not match.", expected.getUserId(), actual.getUserId());
        Assert.assertEquals(methodInvoked + " petIds do not match.", expected.getPetId(), actual.getPetId());
        Assert.assertEquals(methodInvoked + " timestamps do not match.", expected.getTimeFed(), actual.getTimeFed());
        Assert.assertEquals(methodInvoked + " foodTypes do not match.", expected.getFoodType(), actual.getFoodType());
        Assert.assertEquals(methodInvoked + " portionAmounts do not match.", expected.getPortionAmount(), actual.getPortionAmount(), 0.001);
        Assert.assertEquals(methodInvoked + " portionUnits do not match.", expected.getPortionUnits(), actual.getPortionUnits());
        Assert.assertEquals(methodInvoked + " notes do not match.", expected.getNotes(), actual.getNotes());
    }
}
