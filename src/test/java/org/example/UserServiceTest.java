package org.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {
    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserService();
    }

    @Test
    void testRegisterUser() {
        User user = new User("john", "password123", "john@example.com");
        assertTrue(userService.registerUser(user));
        assertFalse(userService.registerUser(user)); // Attempting to register the same user again
    }

    @Test
    void testLoginUser() {
        User user = new User("alice", "securepass", "alice@example.com");
        userService.registerUser(user);

        User loggedInUser = userService.loginUser("alice", "securepass");
        assertNotNull(loggedInUser);
        assertEquals("alice", loggedInUser.getUsername());

        assertNull(userService.loginUser("alice", "wrongpass"));
        assertNull(userService.loginUser("bob", "anypass")); // Non-existent user
    }

    @Test
    void testUpdateUserProfile() {
        User user = new User("sam", "oldpass", "sam@example.com");
        userService.registerUser(user);

        // Test successful update
        assertTrue(userService.updateUserProfile(user, "samantha", "newpass", "samantha@example.com"));

        // Test update with an existing username
        User anotherUser = new User("john", "password123", "john@example.com");
        userService.registerUser(anotherUser);
        assertFalse(userService.updateUserProfile(user, "john", "somepass", "john2@example.com"));

        // Verify the update
        User updatedUser = userService.loginUser("samantha", "newpass");
        assertNotNull(updatedUser);
        assertEquals("samantha@example.com", updatedUser.getEmail());
    }
}