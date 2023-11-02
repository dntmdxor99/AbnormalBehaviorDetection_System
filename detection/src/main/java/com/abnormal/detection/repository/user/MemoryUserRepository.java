package com.abnormal.detection.repository.user;

import com.abnormal.detection.domain.user.User;

import java.util.List;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class MemoryUserRepository implements UserRepository {
    private List<User> userList;

    public MemoryUserRepository() {
        userList = new ArrayList<>();
    }

    @Override
    public User createUser(User user) {
        // 고유 ID 생성 및 설정
        user.setUserId(generateUniqueUserId());
        userList.add(user);
        return user;
    }

    @Override
    public User getUserById(String userId) {
        return userList.stream()
                .filter(u -> u.getUserId().equals(userId))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getAllUsers() {
        return userList;
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
        User existingUser = getUserById(userId);
        if (existingUser != null) {
            // 업데이트된 정보로 사용자 업데이트
            existingUser.setUserEmail(updatedUser.getUserEmail());
            existingUser.setUserPhoneNumber(updatedUser.getUserPhoneNumber());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setDepartment(updatedUser.getDepartment());
            existingUser.setEmployeeNumber(updatedUser.getEmployeeNumber());
            return existingUser;
        }
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        userList = userList.stream()
                .filter(u -> !u.getUserId().equals(userId))
                .collect(Collectors.toList());
    }

    @Override
    public User getUserByEmail(String userEmail) {
        return userList.stream()
                .filter(u -> u.getUserEmail().equals(userEmail))
                .findFirst()
                .orElse(null);
    }

    @Override
    public User getUserByPhoneNumber(String userPhoneNumber) {
        return userList.stream()
                .filter(u -> u.getUserPhoneNumber().equals(userPhoneNumber))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<User> getUsersByUserName(String userName) {
        return userList.stream()
                .filter(u -> u.getUserName().equals(userName))
                .collect(Collectors.toList());
    }

    @Override
    public List<User> getUsersByDepartment(String department) {
        return userList.stream()
                .filter(u -> u.getDepartment().equals(department))
                .collect(Collectors.toList());
    }

    //무작위의 고유아이디 형성 중복x
    private String generateUniqueUserId() {
        return UUID.randomUUID().toString();
    }
}

