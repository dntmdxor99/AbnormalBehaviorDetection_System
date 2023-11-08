package com.abnormal.detection.repository.user;

import com.abnormal.detection.domain.cctv.Cctv;
import com.abnormal.detection.domain.user.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Repository
public class JpaUserRepository implements UserRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public User createUser(User user) {
        // 고유 ID 생성 및 설정
        user.setUserId(null); // ID는 자동 생성
        entityManager.persist(user);
        return user;
    }

    @Override
    public User getUserById(String userId) {
        return entityManager.find(User.class, userId);
    }

    @Override
    public List<User> getAllUsers() {
        String jpql = "SELECT u FROM User u";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        return query.getResultList();
    }

    @Override
    @Transactional
    public User updateUser(String userId, User updatedUser) {
        User existingUser = entityManager.find(User.class, userId);
        if (existingUser != null) {
            // 업데이트된 정보로 사용자 업데이트
            existingUser.setUserEmail(updatedUser.getUserEmail());
            existingUser.setUserPhoneNumber(updatedUser.getUserPhoneNumber());
            existingUser.setPassword(updatedUser.getPassword());
            existingUser.setUserName(updatedUser.getUserName());
            existingUser.setDepartment(updatedUser.getDepartment());
            existingUser.setEmployeeNumber(updatedUser.getEmployeeNumber());
            entityManager.merge(existingUser);
            return existingUser;
        }
        return null;
    }

    @Override
    @Transactional
    public void deleteUser(String userId) {
        User user = entityManager.find(User.class, userId);
        if (user != null) {
            entityManager.remove(user);
        }
    }

    @Override
    public User getUserByEmail(String userEmail) {
        String jpql = "SELECT u FROM User u WHERE u.userEmail = :userEmail";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("userEmail", userEmail);
        return query.getSingleResult();
    }

    @Override
    public User getUserByPhoneNumber(String userPhoneNumber) {
        String jpql = "SELECT u FROM User u WHERE u.userPhoneNumber = :userPhoneNumber";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("userPhoneNumber", userPhoneNumber);
        return query.getSingleResult();
    }

    @Override
    public List<User> getUsersByUserName(String userName) {
        String jpql = "SELECT u FROM User u WHERE u.userName = :userName";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("userName", userName);
        return query.getResultList();
    }

    @Override
    public List<User> getUsersByDepartment(String department) {
        String jpql = "SELECT u FROM User u WHERE u.department = :department";
        TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
        query.setParameter("department", department);
        return query.getResultList();
    }
}