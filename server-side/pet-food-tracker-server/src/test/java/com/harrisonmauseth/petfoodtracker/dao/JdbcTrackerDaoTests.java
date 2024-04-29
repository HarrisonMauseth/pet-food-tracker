package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.model.Tracker;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.Timestamp;

public class JdbcTrackerDaoTests extends BaseDaoTests {
    private static final Tracker TRACKER_1 = new Tracker(1, 1, 1, Timestamp.valueOf("2000-01-01 01:00:00"),
            "food1", 1, "unit1", "notes1");
    private static final Tracker TRACKER_2 = new Tracker(2, 2, 2, Timestamp.valueOf("2000-02-02 02:00:00"),
            "food2", 2, "unit2", "notes2");
    private static final Tracker TRACKER_3 = new Tracker(3, 3, 3, Timestamp.valueOf("2000-03-03 03:00:00"),
            "food3", 3, "unit3", "notes3");

    private JdbcTrackerDao dao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcTrackerDao(jdbcTemplate);
    }
}
