package com.abnormal.detection.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

//회원가입,로그인
@Slf4j
public class JwtUtil {
    public static String getUserName(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().get("userName", String.class);
    }


    public static String getUserType(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().get("userType", String.class);
    }



    public static boolean isExpired(String token, String secretKey) {
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().getExpiration().before(new Date());
    }
    //public static String createJwt(String userName, String userType, String secretKey, Long expireMs) {
    public static String createJwt(String userName, String secretKey, Long expireMs) {
        log.info("*********************************************");
        log.info(userName);
        log.info(secretKey);
        log.info(expireMs.toString());
        Claims claims = Jwts.claims();
        claims.put("userName", userName);
        //claims.put("userType", userType);
        log.info("*********************************************");
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    //refresh token 발급
    public static String createRefreshToken(String userName, String secretKey, Long expireMs) {
        Claims claims = Jwts.claims();
        claims.put("userName", userName);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireMs))
                .signWith(SignatureAlgorithm.HS256, secretKey.getBytes())
                .compact();
    }

    //logout 기능
    private static Set<String> blacklistedTokens = new HashSet<>();

    public static boolean isTokenValid(String token, String secretKey) {
        try {
            Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token);
            return !isTokenBlacklisted(token);
        } catch (Exception e) {
            log.error("토큰 검증 실패: {}", e.getMessage());
            return false;
        }
    }

    public static void blacklistToken(String token) {
        blacklistedTokens.add(token);
    }

    public static boolean isTokenBlacklisted(String token) {

        return blacklistedTokens.contains(token);
    }



}