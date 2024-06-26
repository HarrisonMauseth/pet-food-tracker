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
            "type1", LocalDate.of(2000, 1, 1), "image1", "notes1");
    private static final Pet PET_2 = new Pet(2, 2, "name2", "short2",
            "type2", LocalDate.of(2000, 2, 2), "image2", "notes2");
    private static final Pet PET_3 = new Pet(3, 3, "name3", "short3",
            "type3", LocalDate.of(2000, 3, 3), "image3", "notes3");
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
        Assert.assertNotNull("getPets() returned a null.", pets);
        assertPetsMatch("getPets(" + USERNAME_1 + ")", PET_1, pets.get(0));

        pets = dao.getPets(USERNAME_2);
        Assert.assertNotNull("getPets() returned a null.", pets);
        assertPetsMatch("getPets(" + USERNAME_2 + ")", PET_2, pets.get(0));

        pets = dao.getPets(USERNAME_3);
        Assert.assertNotNull("getPets() returned null.", pets);
        assertPetsMatch("getPets(" + USERNAME_3 + ")", PET_3, pets.get(0));
    }

    @Test
    public void getPetByPetId_returns_correct_pet_without_username() {
        Pet retrievedPet = dao.getPetByPetId(PET_1.getPetId());
        Assert.assertNotNull("getPetByPetId() returned a null pet.", retrievedPet);
        assertPetsMatch("getPetByPetId()", PET_1, retrievedPet);

        retrievedPet = dao.getPetByPetId(PET_2.getPetId());
        Assert.assertNotNull("getPetByPetId() returned a null pet.", retrievedPet);
        assertPetsMatch("getPetByPetId()", PET_2, retrievedPet);

        retrievedPet = dao.getPetByPetId(PET_3.getPetId());
        Assert.assertNotNull("getPetByPetId() returned a null pet.", retrievedPet);
        assertPetsMatch("getPetByPetId()", PET_3, retrievedPet);
    }

    @Test
    public void getPetByPetId_returns_correct_pet_with_username() {
        Pet retrievedPet = dao.getPetByPetId(USERNAME_1, PET_1.getPetId());
        Assert.assertNotNull("getPetByPetId() returned a null pet.", retrievedPet);
        assertPetsMatch("getPetByPetId()", PET_1, retrievedPet);

        retrievedPet = dao.getPetByPetId(USERNAME_2, PET_2.getPetId());
        Assert.assertNotNull("getPetByPetId() returned a null pet.", retrievedPet);
        assertPetsMatch("getPetByPetId()", PET_2, retrievedPet);

        retrievedPet = dao.getPetByPetId(USERNAME_3, PET_3.getPetId());
        Assert.assertNotNull("getPetByPetId() returned a null pet.", retrievedPet);
        assertPetsMatch("getPetByPetId()", PET_3, retrievedPet);
    }

    @Test
    public void createPet_creates_pet() {
        Pet petToCreate = new Pet(0, 1, "newPet", "shortNew",
                "typeNew", LocalDate.of(2000, 4, 4), "imageNew", "notesNew");

        Pet createdPet = dao.createPet(petToCreate, USERNAME_1);
        Assert.assertNotNull("createPet() returned a null pet.", createdPet);
        Assert.assertTrue("createPet did not return a pet with the id set.", createdPet.getPetId() > 0);

        // Set the petToCreate petId to match the createdPet petId, then pass through the assertPetsMatch helper method
        petToCreate.setPetId(createdPet.getPetId());
        assertPetsMatch("createPet()", petToCreate, createdPet);

        // Verify that the pet was saved to database
        Pet retrievedPet = dao.getPetByPetId(createdPet.getPetId());
        assertPetsMatch("createPet() created a pet was unable to be retrieved:", createdPet, retrievedPet);
    }

    @Test
    public void updatePet_updatesPet() {
        Pet petToUpdate = new Pet();
        petToUpdate.setPetId(PET_1.getPetId());
        petToUpdate.setUserId(PET_1.getUserId());
        petToUpdate.setPetName("updatedPet");
        petToUpdate.setPetNickname("update");
        petToUpdate.setPetType("updatedType");
        petToUpdate.setBirthday(LocalDate.of(1900, 12, 12));
        petToUpdate.setNotes("updatedNotes");

        Pet updatedPet = dao.updatePet(petToUpdate, USERNAME_1);
        Assert.assertNotNull("updatePet() returned a null pet.", updatedPet);
        assertPetsMatch("updatePet() returned an incorrect or incomplete pet:", petToUpdate, updatedPet);

        // Verify pet was saved to database and can be retrieved.
        Pet retrievedPet = dao.getPetByPetId(PET_1.getPetId());
        assertPetsMatch("updatePet() updated but failed to save to database:", updatedPet, retrievedPet);
    }

    @Test
    public void deletePetByPetId_deletes_pet() {
        int rowsAffected = dao.deletePetByPetId(PET_1.getPetId(), USERNAME_1);
        Assert.assertEquals("deletePetByPetId() did not return the correct number of rows deleted.", 1, rowsAffected);
        Pet retreivedPet = dao.getPetByPetId(PET_1.getPetId());
        Assert.assertNull("deletePetByPetId() did not remove the pet from the database.", retreivedPet);
    }

    private void assertPetsMatch(String methodInvoked, Pet expected, Pet actual) {
        Assert.assertEquals(methodInvoked + " petIds do not match.", expected.getPetId(), actual.getPetId());
        Assert.assertEquals(methodInvoked + " userIds do not match.", expected.getUserId(), actual.getUserId());
        Assert.assertEquals(methodInvoked + " petNames do not match.", expected.getPetName(), actual.getPetName());
        Assert.assertEquals(methodInvoked + " petNicknames do not match", expected.getPetNickname(), actual.getPetNickname());
        Assert.assertEquals(methodInvoked + " petTypes do not match.", expected.getPetType(), actual.getPetType());
        Assert.assertEquals(methodInvoked + " birthdays do not match", expected.getBirthday(), actual.getBirthday());
        Assert.assertEquals(methodInvoked + " image paths do not match", expected.getImagePath(), actual.getImagePath());
        Assert.assertEquals(methodInvoked + " notes do not match", expected.getNotes(), actual.getNotes());
    }

}
