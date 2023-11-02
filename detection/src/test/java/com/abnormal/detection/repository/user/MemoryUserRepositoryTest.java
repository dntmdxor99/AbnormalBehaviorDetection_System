/*

package com.abnormal.detection.repository.user;

import com.abnormal.detection.domain.user.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class MemoryUserRepositoryTest {
    private MemoryUserRepository userRepository;

    @BeforeEach
    void setUp() {
        userRepository = new MemoryUserRepository();
    }

    @Test
    void createUser() {
        // Given
        User newUser = new User(null, "test@example.com", "123456789", "password", null, "Test User", "IT", "12345");

        // When
        User createdUser = userRepository.createUser(newUser);

        // Then
        assertNotNull(createdUser.getUserId());
        assertEquals(newUser.getUserEmail(), createdUser.getUserEmail());
    }

    @Test
    void getUserById() {
        // Given
        User newUser = new User(null, "test@example.com", "123456789", "password", null, "Test User", "IT", "12345");
        User createdUser = userRepository.createUser(newUser);

        // When
        User retrievedUser = userRepository.getUserById(createdUser.getUserId());

        // Then
        assertNotNull(retrievedUser);
        assertEquals(createdUser.getUserId(), retrievedUser.getUserId());
    }

    @Test
    void getAllUsers() {
        // Given
        User user1 = new User(null, "user1@example.com", "111111111", "password1", null, "User 1", "HR", "11111");
        User user2 = new User(null, "user2@example.com", "222222222", "password2", null, "User 2", "Finance", "22222");
        userRepository.createUser(user1);
        userRepository.createUser(user2);

        // When
        List<User> users = userRepository.getAllUsers();

        // Then
        assertNotNull(users);
        assertTrue(users.size() >= 2);
    }

    @Test
    void updateUser() {
        // Given
        User user = new User(null, "update@example.com", "333333333", "password", null, "Update User", "Marketing", "33333");
        User createdUser = userRepository.createUser(user);

        // When
        createdUser.setUserEmail("updated@example.com");
        User updatedUser = userRepository.updateUser(createdUser.getUserId(), createdUser);

        // Then
        assertNotNull(updatedUser);
        assertEquals("updated@example.com", updatedUser.getUserEmail());
    }

    @Test
    void deleteUser() {
        // Given
        User user = new User(null, "delete@example.com", "444444444", "password", null, "Delete User", "Sales", "44444");
        User createdUser = userRepository.createUser(user);

        // When
        userRepository.deleteUser(createdUser.getUserId());

        // Then
        User deletedUser = userRepository.getUserById(createdUser.getUserId());
        assertNull(deletedUser);
    }

    @Test
    void getUserByEmail() {
        // Given
        User newUser = new User(null, "email@example.com", "555555555", "password", null, "Email User", "HR", "55555");
        User createdUser = userRepository.createUser(newUser);

        // When
        User retrievedUser = userRepository.getUserByEmail(createdUser.getUserEmail());

        // Then
        assertNotNull(retrievedUser);
        assertEquals(createdUser.getUserEmail(), retrievedUser.getUserEmail());
    }

    @Test
    void getUserByPhoneNumber() {
        // Given
        User newUser = new User(null, "phone@example.com", "666666666", "password", null, "Phone User", "IT", "66666");
        User createdUser = userRepository.createUser(newUser);

        // When
        User retrievedUser = userRepository.getUserByPhoneNumber(createdUser.getUserPhoneNumber());

        // Then
        assertNotNull(retrievedUser);
        assertEquals(createdUser.getUserPhoneNumber(), retrievedUser.getUserPhoneNumber());
    }

    @Test
    void getUsersByUserName() {
        // Given
        User user1 = new User(null, "user1@example.com", "111111111", "password1", null, "User 1", "HR", "11111");
        User user2 = new User(null, "user2@example.com", "222222222", "password2", null, "User 2", "Finance", "22222");
        userRepository.createUser(user1);
        userRepository.createUser(user2);

        // When
        List<User> users = userRepository.getUsersByUserName("User 1");

        // Then
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("User 1", users.get(0).getUserName());
    }

    @Test
    void getUsersByDepartment() {
        // Given
        User user1 = new User(null, "user1@example.com", "111111111", "password1", null, "User 1", "HR", "11111");
        User user2 = new User(null, "user2@example.com", "222222222", "password2", null, "User 2", "Finance", "22222");
        userRepository.createUser(user1);
        userRepository.createUser(user2);

        // When
        List<User> users = userRepository.getUsersByDepartment("HR");

        // Then
        assertNotNull(users);
        assertEquals(1, users.size());
        assertEquals("HR", users.get(0).getDepartment());
    }
}

*/
