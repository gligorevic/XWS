package com.example.AgentApplication.security;

import com.example.AgentApplication.domain.Car;
import com.example.AgentApplication.domain.Privilege;
import com.example.AgentApplication.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.*;

import static com.example.AgentApplication.security.SecurityConstants.*;


@Component
public class JWTTokenHelper {

    public String generate(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());

        Date expiryDate = new Date(now.getTime() + EXPIRE);

        String userId = Long.toString(user.getId());

        Map<String,Object> claims = new HashMap<>();
        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getEmail());
        claims.put("role", user.getRoles());
        claims.put("blocked", user.isBlocked());

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

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

    public List<Long> getRolesIdFromJWT(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            Collection<?> roles = claims.get("role", Collection.class);

            List<Long> roleIds = new ArrayList<>();

            for(Object r : roles) {
                System.out.println(r.toString());
                String[] roleFields = r.toString().split(",");
                String roleId = roleFields[0].split("=")[1];
                roleIds.add(Long.parseLong(roleId));
            }

            return roleIds;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public String getUserEmailFromAccesToken(String jwt) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();

        String email = claims.get("username").toString();

        return email;
    }

    public String generateAccessToken(List<Privilege> privileges, String jwt) {
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(jwt).getBody();

        String userId = claims.get("id").toString();

        Map<String,Object> newClaims = new HashMap<>();
        newClaims.put("id", userId);
        newClaims.put("username", claims.get("username"));
        newClaims.put("privileges", getNamesFromPrivileges(privileges));
        newClaims.put("role", claims.get("role"));

        Date now = new Date(System.currentTimeMillis());
        Date expiryDate = new Date(now.getTime() + ACCESS_TOKEN_EXPIRE);

        return Jwts.builder()
                .setSubject(userId)
                .setClaims(newClaims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();
    }

    private List<String> getNamesFromPrivileges(List<Privilege> privileges) {
        List<String> privilegeNames = new ArrayList<>();
        for(Privilege p : privileges) {
            privilegeNames.add(p.getName());
        }
        return privilegeNames;
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