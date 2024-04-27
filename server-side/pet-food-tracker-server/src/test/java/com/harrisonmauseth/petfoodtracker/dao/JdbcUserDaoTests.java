package com.harrisonmauseth.petfoodtracker.dao;

import com.harrisonmauseth.petfoodtracker.exception.DaoException;
import com.harrisonmauseth.petfoodtracker.model.UserEntity;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.util.List;

public class JdbcUserDaoTests extends BaseDaoTests {

    private static final UserEntity USER_1 = new UserEntity(1, "user1", "password1", "ROLE_USER");
    private static final UserEntity USER_2 = new UserEntity(2, "user2", "password2", "ROLE_ADMIN");
    private static final UserEntity USER_3 = new UserEntity(3, "user3", "password3", "ROLE_USER");

    private JdbcUserDao dao;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        dao = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void getUserByUsername_returns_null_user_with_null_() {
        UserEntity user = dao.getUserByUsername(null);
        Assert.assertNull("getUserByUsername with null username did not return null user", user);
    }

    @Test
    public void getUserByUsername_returns_null_user_with_invalid_username() {
        UserEntity user = dao.getUserByUsername("invalid");
        Assert.assertNull("getUserByUsername with invalid username did not return null user", user);
    }

    @Test
    public void getUserByUsername_returns_correct_user_with_valid_username() {
        UserEntity retrievedUser = dao.getUserByUsername(USER_1.getUsername());
        assertUsersMatch(USER_1, retrievedUser);

        retrievedUser = dao.getUserByUsername(USER_2.getUsername());
        assertUsersMatch(USER_2, retrievedUser);

        retrievedUser = dao.getUserByUsername(USER_3.getUsername());
        assertUsersMatch(USER_3, retrievedUser);
    }

    @Test
    public void getUserById_returns_null_with_invalid_id() {
        UserEntity user = dao.getUserById(-1);
        Assert.assertNull("getUserById did not return a null user with an invalid ID", user);
    }

    @Test
    public void getUserById_returns_correct_user_with_valid_id() {
        UserEntity retrievedUser = dao.getUserById(USER_1.getId());
        assertUsersMatch(USER_1, retrievedUser);

        retrievedUser = dao.getUserById(USER_2.getId());
        assertUsersMatch(USER_2, retrievedUser);

        retrievedUser = dao.getUserById(USER_3.getId());
        assertUsersMatch(USER_3, retrievedUser);
    }

    @Test
    public void getUsers_returns_all_users() {
        List<UserEntity> users = dao.getUsers();

        Assert.assertNotNull("getUsers returned a null list of users", users);
        Assert.assertEquals("getUsers returned incorrect size", 3, users.size());
        Assert.assertEquals("getUsers returned users in incorrect order", USER_1.getUsername(), users.get(0).getUsername());
        Assert.assertEquals("getUsers returned users in incorrect order", USER_2.getUsername(), users.get(1).getUsername());
        Assert.assertEquals("getUsers returned users in incorrect order", USER_3.getUsername(), users.get(2).getUsername());
    }

    @Test(expected = DaoException.class)
    public void createUser_with_null_username_throws_DaoException() {
        dao.createUser(
                new UserEntity(null, "newPassword", "ROLE_TEST")
        );
    }

    @Test(expected = DaoException.class)
    public void createUser_with_existing_username_throws_DaoException() {
        dao.createUser(
                new UserEntity(USER_2.getUsername(), "newPassword", "ROLE_TEST")
        );
    }

    @Test(expected = DaoException.class)
    public void createUser_with_null_password_throws_DaoException() {
        dao.createUser(
                new UserEntity("newUser", null, "ROLE_TEST")
        );
    }

    @Test
    public void createUser_creates_a_user() {
        UserEntity newUser = new UserEntity("new", "password", "ROLE_USER");

        UserEntity createdUser = dao.createUser(newUser);
        Assert.assertNotNull("createUser with a valid user should return non-null user", createdUser);

        UserEntity retrievedUser = dao.getUserById(createdUser.getId());
        Assert.assertNotNull("Unable to getUserById using the returned id of the new user", retrievedUser);

        assertUsersMatch(createdUser, retrievedUser);
    }

    @Test
    public void existsByUsername_returns_true_with_existing_username() {
        boolean actual = dao.existsByUsername(USER_1.getUsername());
        Assert.assertTrue("existsByUsername should have returned true with an existing username", actual);
    }

    @Test
    public void existsByUsername_returns_false_with_invalid_username() {
        boolean actual = dao.existsByUsername("invalid");
        Assert.assertFalse("existsByUsername should have returned false with a username that does not exist", actual);
    }

    private void assertUsersMatch(UserEntity expectedUser, UserEntity actualUser) {
        Assert.assertNotNull("Expected a user object but came back null", actualUser);
        Assert.assertEquals("User IDs do not match", expectedUser.getId(), actualUser.getId());
        Assert.assertEquals("User hashed passwords do not match", expectedUser.getPassword(), actualUser.getPassword());
        Assert.assertEquals("User roles do not match", expectedUser.getRolesAsString(), actualUser.getRolesAsString());
    }

}
