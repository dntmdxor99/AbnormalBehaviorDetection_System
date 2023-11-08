package com.abnormal.detection.service.user;


import com.abnormal.detection.domain.user.User;
import com.abnormal.detection.repository.user.JpaUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {
    private final JpaUserRepository userRepository;

    @Autowired
    public UserService(JpaUserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    public User getUserById(String userId) {
        return userRepository.getUserById(userId);
    }

    public List<User> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public User updateUser(String userId, User updatedUser) {
        return userRepository.updateUser(userId, updatedUser);
    }

    public void deleteUser(String userId) {
        userRepository.deleteUser(userId);
    }

    public User getUserByEmail(String userEmail) {
        return userRepository.getUserByEmail(userEmail);
    }

    public User getUserByPhoneNumber(String userPhoneNumber) {
        return userRepository.getUserByPhoneNumber(userPhoneNumber);
    }

    public List<User> getUsersByUserName(String userName) {
        return userRepository.getUsersByUserName(userName);
    }

    public List<User> getUsersByDepartment(String department) {
        return userRepository.getUsersByDepartment(department);
    }
}

