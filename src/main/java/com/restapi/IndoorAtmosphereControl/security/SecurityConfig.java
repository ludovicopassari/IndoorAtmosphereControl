package com.restapi.IndoorAtmosphereControl.security;

import com.restapi.IndoorAtmosphereControl.jwt.JwtAuthFilter;
import com.restapi.IndoorAtmosphereControl.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Autowired
    private JwtAuthFilter authFilter;


    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoService();
    }

    /*
        Il metodo securityFilterChain è un metodo di configurazione in Spring Security, annotato con @Bean.
        Questo metodo è responsabile per configurare e definire le regole di sicurezza per le richieste HTTP nella tua applicazione.
        Vediamo nel dettaglio cosa fa ciascuna parte del metodo

        Questo metodo definisce e restituisce una catena di filtri di sicurezza (SecurityFilterChain),
        che sono applicati a tutte le richieste HTTP che passano attraverso l'applicazione.
        La catena di filtri gestisce l'autenticazione, l'autorizzazione e la gestione delle sessioni in base alle regole specificate.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/register","/login").permitAll())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/user/**").authenticated())
                .authorizeHttpRequests(auth -> auth.requestMatchers("/auth/admin/**").authenticated())
                .sessionManagement(sess -> sess.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
                .build();

    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }


    // Password Encoding
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    /*


    * AuthenticationManager in Spring Security è un'interfaccia fondamentale che gestisce
    * il processo di autenticazione degli utenti all'interno di un'applicazione.
    * Il compito principale di AuthenticationManager è quello di accettare un oggetto Authentication
    * che rappresenta le credenziali di accesso dell'utente (come username e password) e verificare
    *  se queste credenziali sono valide. Se le credenziali sono valide, AuthenticationManager
    * crea un oggetto Authentication completo che rappresenta l'utente autenticato.
    * */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }



}
