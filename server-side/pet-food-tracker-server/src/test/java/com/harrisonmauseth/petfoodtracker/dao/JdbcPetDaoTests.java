package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.model.Pet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class JdbcPetDaoTests extends BaseDaoTests {

    private static final Pet PET_1 = new Pet(1, 1, "name1", "short1",
            "type1", LocalDate.of(2000, 1, 1), "notes1");
    private static final Pet PET_2 = new Pet(2, 2, "name2", "short2",
            "type2", LocalDate.of(2000, 2, 2), "notes2");
    private static final Pet PET_3 = new Pet(3, 3, "name3", "short3",
            "type3", LocalDate.of(2000, 3, 3), "notes3");
    private static final String USERNAME_1 = "user1";
    private static final String USERNAME_2 = "user2";
    private static final String USERNAME_3 = "user3";

    private JdbcPetDao dao;
    private List<Pet> pets = new ArrayList<>();

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcPetDao(jdbcTemplate);
    }

    @Test
    public void getPets_returns_correct_number_of_pets() {
        pets = dao.getPets(USERNAME_1);
        Assert.assertNotNull(pets);
        Assert.assertEquals("getProducts() returned incorrect size", 1, pets.size());
    }

    @Test
    public void getPets_returns_correct_pets_for_specified_user() {
        pets = dao.getPets(USERNAME_1);
        Assert.assertNotNull(pets);
        assertPetsMatch("getPets(" + USERNAME_1 +")", PET_1, pets.get(0));

        pets = dao.getPets(USERNAME_2);
        Assert.assertNotNull(pets);
        assertPetsMatch("getPets(" + USERNAME_2 +")", PET_2, pets.get(0));

        pets = dao.getPets(USERNAME_3);
        Assert.assertNotNull(pets);
        assertPetsMatch("getPets(" + USERNAME_3 +")", PET_3, pets.get(0));
    }

    private void assertPetsMatch(String methodInvoked, Pet expected, Pet actual) {
        Assert.assertEquals(methodInvoked + " petIds do not match.", expected.getPetId(), actual.getPetId());
        Assert.assertEquals(methodInvoked + " userIds do not match.", expected.getUserId(), actual.getUserId());
        Assert.assertEquals(methodInvoked + " petNames do not match.", expected.getPetName(), actual.getPetName());
        Assert.assertEquals(methodInvoked + " petNicknames do not match", expected.getPetNickname(), actual.getPetNickname());
        Assert.assertEquals(methodInvoked + " petTypes do not match.", expected.getPetType(), actual.getPetType());
        Assert.assertEquals(methodInvoked + " birthdays do not match", expected.getBirthday(), actual.getBirthday());
        Assert.assertEquals(methodInvoked + " notes do not match", expected.getNotes(), actual.getNotes());
    }

}