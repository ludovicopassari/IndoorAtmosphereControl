package com.restapi.IndoorAtmosphereControl.jwt;


import com.restapi.IndoorAtmosphereControl.auth.UserAlreadyExistsException;
import com.restapi.IndoorAtmosphereControl.entity.UserInfo;
import com.restapi.IndoorAtmosphereControl.entity.UserInfoDetails;
import com.restapi.IndoorAtmosphereControl.repository.UserInfoRepository;
import com.restapi.IndoorAtmosphereControl.http.request.AuthRequest;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    public static final String SECRET = "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserInfoRepository  repository;

    @Autowired
    private PasswordEncoder encoder;


    public String generateToken(String email){
        Map<String, Object> claims = new HashMap<>();
        return createToken(claims,email);
    }


    private String createToken(Map<String, Object> claims, String email) {
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(email)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();
    }


    private Key getSignKey() {
        byte[] keyBytes= Decoders.BASE64.decode(SECRET);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String extractEmail(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Boolean isTokenExpired(String token)  {
        return extractExpiration(token).before(new Date());
    }

    /*
        Verifica che il token sia dell'utente
     */
    public Boolean validateToken(String token, UserInfoDetails userDetails) {
        final String email = extractEmail(token);
        return (email.equals(userDetails.getEmail()) && !isTokenExpired(token));
    }

    public UserInfoDetails authenticate(AuthRequest authRequest) throws Exception {
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword()));

            return (UserInfoDetails) authentication.getPrincipal();

        } catch (AuthenticationException e) {

            throw new Exception("Invalid username or password");

        }

    }

    public void registerUser(UserInfo userInfo) throws UserAlreadyExistsException {
        if (repository.findByUsername(userInfo.getUsername()).isPresent()) {
            throw new UserAlreadyExistsException("Username is already taken");
        }
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
    }

}