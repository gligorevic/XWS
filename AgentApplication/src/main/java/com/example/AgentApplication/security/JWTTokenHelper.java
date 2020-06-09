package com.example.AgentApplication.security;

import com.example.AgentApplication.domain.Car;
import io.jsonwebtoken.*;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.example.AgentApplication.security.SecurityConstants.SECRET;
import static com.example.AgentApplication.security.SecurityConstants.TOKEN_BEARER_PREFIX;


@Component
public class JWTTokenHelper {

    public boolean validate(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
            System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT claims string is empty");
        }
        return false;
    }

    public String getUserUsernameFromJWT(String token){
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            String username = (String)claims.get("username");
            return username;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<String> getPrivilegesFromAccessToken(String jwt) {
        List<String> privileges = new ArrayList<>();

        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();

        String privilegesString = claims.get("privileges").toString();

        privilegesString = privilegesString.replaceFirst("\\[", "");
        privilegesString = privilegesString.replace("]", "");

        String[] privilegesArr = privilegesString.split(", ");

        for(String privilege : privilegesArr) {
            privileges.add(privilege);
        }

        return privileges;
    }

    public List<String> getRoleFromAccesToken(String jwt) {
        List<String> rolesFromJWT = new ArrayList<>();

        System.out.println(jwt);

        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();


        String role = claims.get("role").toString();

        System.out.println(role);
        role = role.replaceFirst("\\[", "");
        role = role.replace("]", "");

        String[] roles = role.split(", ");

        for(String r : roles) {
            rolesFromJWT.add(r);
        }


        return rolesFromJWT;
    }

    public String getUserEmailFromAccesToken(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();

        String email = claims.get("username").toString();

        return email;
    }

    public String getJWTFromBearerToken(String bearerToken){


        if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_BEARER_PREFIX)){
            return bearerToken.substring(7);
        }

        return null;
    }

    public String generateLocationToken(Car car) {
        Map<String,Object> claims = new HashMap<>();
        claims.put("carId", (Long.toString(car.getId())));
        claims.put("ownerUsername", car.getUserEmail());

        return Jwts.builder()
                .setSubject(car.getId().toString())
                .setClaims(claims)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }
}