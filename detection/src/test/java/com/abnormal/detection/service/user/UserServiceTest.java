package com.abnormal.detection.service.user;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.domain.user.User;
import com.abnormal.detection.repository.user.JpaUserRepository;
import com.abnormal.detection.repository.user.UserRepository;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

/*
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String userId;
    private String userEmail;
    private String userPhoneNumber;
    private String password;
    private Date createTime;
    private String userName;
    private String department;
    private String employeeNumber;

 */
    @BeforeEach
    void setup() {
        // 테스트 데이터를 미리 데이터베이스에 추가
        User user1 = new User();
        user1.setUserId("solhappy1"); // 수정된 부분
        user1.setUserEmail("user1@example.com");
        user1.setUserPhoneNumber("1111111111");
        user1.setPassword("password1");
        user1.setUserName("User 1");
        user1.setDepartment("Department A");
        user1.setEmployeeNumber("EMP001");
        userService.createUser(user1);

        User user2 = new User();
        user2.setUserId("solhappy2");
        user2.setUserEmail("user2@example.com");
        user2.setUserPhoneNumber("2222222222");
        user2.setPassword("password2");
        user2.setUserName("User 2");
        user2.setDepartment("Department B");
        user2.setEmployeeNumber("EMP002");
        userService.createUser(user2);
    }

    @Test
    void createUser() {
        User newUser = new User();
        newUser.setUserId("solhappy3");
        newUser.setUserEmail("newuser@example.com");
        newUser.setUserPhoneNumber("3333333333");
        newUser.setPassword("newpassword");
        newUser.setUserName("New User");
        newUser.setDepartment("Department C");
        newUser.setEmployeeNumber("EMP003");

        User createdUser = userService.createUser(newUser);

        assertNotNull(createdUser.getUserId());
        assertEquals("solhappy3",createdUser.getUserId());
        assertEquals("newuser@example.com", createdUser.getUserEmail());
        assertEquals("3333333333", createdUser.getUserPhoneNumber());
        assertEquals("newpassword", createdUser.getPassword());
        assertEquals("New User", createdUser.getUserName());
        assertEquals("Department C", createdUser.getDepartment());
        assertEquals("EMP003", createdUser.getEmployeeNumber());
    }

    @Test
    void getUserById() {
        User user = userService.getUserByEmail("user1@example.com");
        User retrievedUser = userService.getUserById(user.getUserId());

        assertEquals(user.getUserId(), retrievedUser.getUserId());
        assertEquals("user1@example.com", retrievedUser.getUserEmail());
    }

    @Test
    void getAllUsers() {
        List<User> userList = userService.getAllUsers();

        assertEquals(2, userList.size());
    }

    @Test
    void updateUser() {
        User user = userService.getUserByEmail("user1@example.com");
        user.setUserEmail("updateduser@example.com");
        user.setDepartment("Updated Department");

        User updatedUser = userService.updateUser(user.getUserId(), user);

        assertEquals("updateduser@example.com", updatedUser.getUserEmail());
        assertEquals("Updated Department", updatedUser.getDepartment());
    }

    @Test
    void deleteUser() {
        User user = userService.getUserByEmail("user1@example.com");
        userService.deleteUser(user.getUserId());

        assertNull(userService.getUserById(user.getUserId()));
    }

    @Test
    void getUserByEmail() {
        User user = userService.getUserByEmail("user1@example.com");

        assertNotNull(user);
        assertEquals("user1@example.com", user.getUserEmail());
    }

    @Test
    void getUserByPhoneNumber() {
        User user = userService.getUserByPhoneNumber("2222222222");

        assertNotNull(user);
        assertEquals("2222222222", user.getUserPhoneNumber());
    }

    @Test
    void getUsersByUserName() {
        List<User> userList = userService.getUsersByUserName("User 1");

        assertEquals(1, userList.size());
        assertEquals("User 1", userList.get(0).getUserName());
    }

    @Test
    void getUsersByDepartment() {
        List<User> userList = userService.getUsersByDepartment("Department A");

        assertEquals(1, userList.size());
        assertEquals("Department A", userList.get(0).getDepartment());
    }
}