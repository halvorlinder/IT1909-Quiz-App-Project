package core;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class UserDataTest {
    UserData userData;
    UserRecord userRecord1 = new UserRecord("user1", "pWord");
    UserRecord userRecord2 = new UserRecord("user2", "pWord");

    /**
     * Set up a UserData object
     */
    @BeforeEach
    public void setup() {
        userData = new UserData();
        userData.attemptRegister(userRecord1);
    }

    /**
     * Test registering a new user and re-registering an existing user
     */
    @Test
    public void testAttemptRegister() {
        assertFalse(userData.attemptRegister(userRecord1));
        assertTrue(userData.attemptRegister(userRecord2));
        assertEquals(userData.getUserNames().size(), 2);
    }

    /**
     * Test logging in with an existing and non-existing user
     */
    @Test
    public void testAttemptLogIn() {
        assertTrue(userData.attemptLogIn(userRecord1));
        assertFalse(userData.attemptLogIn(userRecord2));
        assertFalse(userData.attemptRegister(userRecord1));
    }

    /**
     * Test hash function for password of users
     */
    @Test
    public void testHash() {
        assertEquals(0, UserData.hash(""));
        UserRecord userRecord3 = new UserRecord("user2", "password");
        userData.attemptRegister(userRecord3);
        assertEquals(UserData.hash("password"), userData.getPasswordHash("user2"));
        assertThrows(NoSuchElementException.class, () ->
                userData.getPasswordHash("user3"));
    }
}
