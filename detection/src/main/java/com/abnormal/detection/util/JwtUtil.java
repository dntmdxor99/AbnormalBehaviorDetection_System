package com.abnormal.detection.util;

import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


@Slf4j
public class JwtUtil {
    public static String getUserName(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().get("userName", String.class);
    }

    /*
    public static String getUserType(String token, String secretKey){
        return Jwts.parser().setSigningKey(secretKey.getBytes()).parseClaimsJws(token)
                .getBody().get("userType", String.class);
    }

     */

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
}