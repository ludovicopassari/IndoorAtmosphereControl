package com.restapi.IndoorAtmosphereControl.jwt;

import com.restapi.IndoorAtmosphereControl.entity.UserInfoDetails;
import com.restapi.IndoorAtmosphereControl.service.UserInfoService;
import io.jsonwebtoken.ExpiredJwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userDetailsService;

    /*
        GET /api/protected-resource HTTP/1.1
        Host: example.com
        Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOiIxMjM0NTY3ODkwIiwibmFtZSI6IkpvaG4gRG9lIiwiaWF0IjoxNTE2MjM5MDIyfQ.SflKxwRJSMeKKF2QT4fwpMeJf36POk6yJV_adQssw5c
    */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");
        String token = null;


        if (authHeader != null && authHeader.startsWith("Bearer ")) {

            token = authHeader.substring(7);

            try{
                String email = jwtService.extractEmail(token);

                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    UserDetails userDetails = userDetailsService.loadUserByUsername(email);


                    if (jwtService.validateToken(token,(UserInfoDetails) userDetails)) {
                        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                        authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                        SecurityContextHolder.getContext().setAuthentication(authToken);
                    }
                }

            }catch (ExpiredJwtException e ){
                handleJwtException(response, e); // Gestione specifica per token JWT scaduto
                return;
            } catch (Exception e) {
                handleGeneralException(response, e); // Gestione generica per altre eccezioni
                return;
            }

        }

        filterChain.doFilter(request, response);
    }

    private void handleJwtException(HttpServletResponse response, ExpiredJwtException e) throws IOException {
        // Puoi personalizzare la gestione dell'eccezione ExpiredJwtException qui
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.getWriter().write("Token JWT scaduto o non valido");
    }

    private void handleGeneralException(HttpServletResponse response, Exception e) throws IOException {
        // Gestione generica delle altre eccezioni qui
        response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.value());
        response.getWriter().write("Errore durante l'autenticazione JWT: " + e.getMessage());
    }

}