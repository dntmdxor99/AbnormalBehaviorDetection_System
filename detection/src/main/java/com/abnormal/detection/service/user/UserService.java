package com.abnormal.detection.service.user;


import com.abnormal.detection.domain.user.JoinStatus;
import com.abnormal.detection.domain.user.LoginStatus;
import com.abnormal.detection.domain.user.User;
import com.abnormal.detection.repository.user.JpaUserRepository;
import com.abnormal.detection.repository.user.UserRepository;
import com.abnormal.detection.util.JwtUtil;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;


@Slf4j
@Transactional
public class UserService {
    private final UserRepository userRepository;

    @Value("${jwt.secret}")
    private String secretKey;
    private Long expiredMs = 1000 * 60 * 60l;


    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public JoinStatus join(User user){

        try{
            validateDuplicateMember(user);
        }
        catch (IllegalStateException e){
            System.out.println(e);
            return JoinStatus.DUPLICATE;
        }
        try{
            checkPasswordLength(user);
        }
        catch (IllegalStateException e){
            System.out.println(e);
            return JoinStatus.INVALID_PASSWORD_LENGTH;
        }
        try{
            checkStrongPassword(user);
        }
        catch (IllegalStateException e)
        {
            System.out.println(e);
            return JoinStatus.INVALID_PASSWORD_STRENGTH;
        }

        userRepository.save(user);
        return JoinStatus.SUCCESS;
    }
    //////////jwt
    public String afterSuccessLogin(String userId) {
        //return JwtUtil.createJwt(userEmail, "normal", secretKey, expiredMs);
        return JwtUtil.createJwt(userId, secretKey, expiredMs);
    }

    public User getUserInfoByJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = "";

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
        }

        String userEmail = JwtUtil.getUserName(jwtToken, secretKey);
        Optional<User> ret = userRepository.findByEmail(userEmail);
        return ret.orElse(null);
    }

    //////////////refresh

    public String createRefreshToken(String userId) {
        return JwtUtil.createRefreshToken(userId, secretKey, expiredMs);
    }

    public boolean validateRefreshToken(String refreshToken) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(refreshToken);
            return true;
        } catch (Exception e) {
            log.error("리프레시 토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    public String refreshAccessToken(String refreshToken) {
        if (validateRefreshToken(refreshToken)) {
            String userId = JwtUtil.getUserName(refreshToken, secretKey);
            return JwtUtil.createJwt(userId, secretKey, expiredMs);
        }
        return null;
    }



    //////////////jwt
    public LoginStatus login(String userId, String userPassword) {
        User user = userRepository.getUserById(userId);
        log.info(userId);
        log.info(user.getUserName());
        log.info(user.getUserId());
        log.info(user.getPassword());
        log.info(userPassword);
        if (user == null || !user.getPassword().equals(userPassword)) {
            return LoginStatus.FAIL;
        }
        return LoginStatus.SUCCESS;
    }

    /**
     * 로그아웃
     */
    public void logout(HttpServletRequest request) {
        // 클라이언트 측에서 토큰을 삭제하거나 무효화하는 로직 추가
        // 여기서는 클라이언트 측에서 토큰을 삭제하는 방법으로 가정

        // 로그아웃한 토큰을 블랙리스트에 추가
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            String token = authorizationHeader.substring(7);
            JwtUtil.blacklistToken(token);
            log.info("토큰 블랙리스트에 추가: {}", token);
        }

        // 다른 로그아웃 관련 로직 추가 가능
    }


    private void validateDuplicateMember(User user) {
        // 이미 존재하는 사용자인지 확인
        Optional<User> existingUser = userRepository.findById(user.getUserId());
        if (existingUser.isPresent()) {
            throw new IllegalStateException("이미 존재하는 회원입니다.");
        }
    }



    private void checkPasswordLength(User user) {
        String password = user.getPassword();
        if (password.length() < 8 || password.length() > 16) {
            throw new IllegalStateException("비밀번호는 8 ~ 16자 사이여야 합니다.");
        }
    }

    private void checkStrongPassword(User user) {
        String password = user.getPassword();
        boolean hasUpperCase = false;
        boolean hasLowerCase = false;
        boolean hasSpecialChar = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            } else if (Character.isLowerCase(c)) {
                hasLowerCase = true;
            } else if ("!@#$%^&*()-_=+[]{}|;:'\",.<>/?".indexOf(c) != -1) {
                hasSpecialChar = true;
            }
        }

        if (!(hasUpperCase && hasLowerCase && hasSpecialChar)) {
            throw new IllegalStateException("비밀번호는 영문 소문자, 대문자, 특수문자를 포함해야됩니다.");
        }
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

