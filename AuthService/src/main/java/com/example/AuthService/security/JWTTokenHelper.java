package com.example.AuthService.security;

import com.example.AuthService.domain.Role;
import com.example.AuthService.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.lang.reflect.Array;
import java.util.*;

import static com.example.AuthService.security.SecurityConstants.EXPIRE;
import static com.example.AuthService.security.SecurityConstants.SECRET;

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
            return null;
        }
    }

    public List<String> getRolesFromJWT(String token) {
        try {
            Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();
            List<Role> roles = (List<Role>) claims.get("role", List.class);
            String username = (String)claims.get("username");
            System.out.println("Ispisujem jeahagagagagag");
            System.out.println("ROles size" + roles.size());
            System.out.println(username);

            for(Role r : roles) {
                System.out.println(r.getName());
            }

            return null;
        } catch (Exception e) {
            return null;
        }
    }
}