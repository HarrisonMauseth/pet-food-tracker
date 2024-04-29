package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.model.Tracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class JdbcTrackerDaoTests extends BaseDaoTests {
    private static final Tracker TRACKER_1 = new Tracker(1, 1, 1, Timestamp.valueOf("2000-01-01 01:00:00"),
            "food1", 1, "unit1", "notes1");
    private static final Tracker TRACKER_2 = new Tracker(2, 2, 2, Timestamp.valueOf("2000-02-02 02:00:00"),
            "food2", 2, "unit2", "notes2");
    private static final Tracker TRACKER_3 = new Tracker(3, 3, 3, Timestamp.valueOf("2000-03-03 03:00:00"),
            "food3", 3, "unit3", "notes3");
    private static final Tracker TRACKER_4 = new Tracker(4, 1, 1, Timestamp.valueOf("2000-04-04 04:00:00"),
            "food1", 1, "unit1", "same food, different time'");
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
        Assert.assertNotNull("getAllEvents() returned null instead of a list", events);
        Assert.assertEquals("getEvents() did not return correct number of feeding events.", 2, events.size());
    }

    @Test
    public void getAllEvents_returns_correct_pets_for_specified_user() {
        events = dao.getAllEvents(USERNAME_2);
        Assert.assertNotNull("getAllEvents() returned null instead of a list", events);

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
