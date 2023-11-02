package com.abnormal.detection.repository.user;

import com.abnormal.detection.domain.user.User;

import java.util.List;

public class JpaUserRepository implements UserRepository{
    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User getUserById(String userId) {
        return null;
    }

    @Override
    public List<User> getAllUsers() {
        return null;
    }

    @Override
    public User updateUser(String userId, User updatedUser) {
        return null;
    }

    @Override
    public void deleteUser(String userId) {

    }

    @Override
    public User getUserByEmail(String userEmail) {
        return null;
    }

    @Override
    public User getUserByPhoneNumber(String userPhoneNumber) {
        return null;
    }

    @Override
    public List<User> getUsersByUserName(String userName) {
        return null;
    }

    @Override
    public List<User> getUsersByDepartment(String department) {
        return null;
    }
}
