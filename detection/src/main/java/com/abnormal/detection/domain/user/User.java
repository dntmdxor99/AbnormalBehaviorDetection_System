package com.abnormal.detection.domain.user;

import java.util.Date;

public class User {
    private String userId;
    private String userEmail;
    private String userPhoneNumber;
    private String password;
    private Date createTime;
    private String userName;
    private String department;
    private String employeeNumber;

    public User(String userId, String userEmail, String userPhoneNumber, String password, Date createTime, String userName, String department, String employeeNumber) {
        this.userId = userId;
        this.userEmail = userEmail;
        this.userPhoneNumber = userPhoneNumber;
        this.password = password;
        this.createTime = createTime;
        this.userName = userName;
        this.department = department;
        this.employeeNumber = employeeNumber;
    }
    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(String employeeNumber) {
        this.employeeNumber = employeeNumber;
    }



}

