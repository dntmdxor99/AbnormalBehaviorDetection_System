package com.abnormal.detection.controller.user;


import com.abnormal.detection.domain.user.*;
import com.abnormal.detection.service.user.UserService;
import io.micrometer.common.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@Slf4j
@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }


    @PostMapping("/signup")
    public ResponseEntity<String> createUser(@RequestBody User user) {
        JoinStatus result = userService.join(user);
        return new ResponseEntity<>(result.getMessage(), result.getStatus());
    }



    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        log.info("-------------------" + loginRequest.getUserPassword() + "-------------------");
        LoginStatus loginResult = userService.login(loginRequest.getUserId(), loginRequest.getUserPassword());
        if (loginResult != LoginStatus.SUCCESS)return new ResponseEntity<>(loginResult.getMessage(), loginResult.getStatus());
        log.info("------------------------------------------------------------");
        String token = userService.afterSuccessLogin(loginRequest.getUserId());
        log.info(token);
        return ResponseEntity.ok().body(token);
    }

    @PostMapping("/refresh")
    public ResponseEntity<?> refreshAccessToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.getRefreshToken();

        if (StringUtils.isBlank(refreshToken)) {
            return ResponseEntity.badRequest().body("리프레시 토큰이 제공되지 않았습니다.");
        }

        if (userService.validateRefreshToken(refreshToken)) {
            String newAccessToken = userService.refreshAccessToken(refreshToken);

            if (newAccessToken != null) {
                return ResponseEntity.ok(new AccessTokenResponse(newAccessToken));
            } else {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("새로운 액세스 토큰 생성에 실패했습니다.");
            }
        } else {
            return ResponseEntity.badRequest().body("유효하지 않은 리프레시 토큰입니다.");
        }
    }




/*
    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

 */


    @GetMapping("/{userId}")
    public User getUserById(@PathVariable String userId) {
        return userService.getUserById(userId);
    }

    @GetMapping
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }

    @PutMapping("/{userId}")
    public User updateUser(@PathVariable String userId, @RequestBody User updatedUser) {
        return userService.updateUser(userId, updatedUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
    }

    @GetMapping("/email/{userEmail}")
    public User getUserByEmail(@PathVariable String userEmail) {
        return userService.getUserByEmail(userEmail);
    }

    @GetMapping("/phone/{userPhoneNumber}")
    public User getUserByPhoneNumber(@PathVariable String userPhoneNumber) {
        return userService.getUserByPhoneNumber(userPhoneNumber);
    }

    @GetMapping("/name/{userName}")
    public List<User> getUsersByUserName(@PathVariable String userName) {
        return userService.getUsersByUserName(userName);
    }

    @GetMapping("/department/{department}")
    public List<User> getUsersByDepartment(@PathVariable String department) {
        return userService.getUsersByDepartment(department);
    }
}

