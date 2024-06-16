package com.restapi.IndoorAtmosphereControl.service;

import com.restapi.IndoorAtmosphereControl.controller.ControllerRest;
import com.restapi.IndoorAtmosphereControl.entity.UserInfo;
import com.restapi.IndoorAtmosphereControl.entity.UserInfoDetails;
import com.restapi.IndoorAtmosphereControl.repository.UserInfoRepository;
import com.restapi.IndoorAtmosphereControl.requests.AuthRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;
    Logger logger = Logger.getLogger(UserInfoService.class.getName());
    /*
    @Autowired
    private PasswordEncoder encoder;
/*
    @Autowired
    private AuthenticationManager authenticationManager;
*/
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByUsername(username);
        logger.info(username+ "load");
        return userDetail.map(UserInfoDetails::new).orElseThrow(() -> new UsernameNotFoundException(username));
    }
    /*

    public void registerUser(UserInfo userInfo) throws Exception {
        if (repository.findByUsername(userInfo.getUsername()).isPresent()) {
            throw new Exception("Username is already taken");
        }
        userInfo.setPassword(encoder.encode(userInfo.getPassword()));
        repository.save(userInfo);
    }
    /*

    public UserInfoDetails authenticate(AuthRequest authRequest) throws Exception {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
            );
            return (UserInfoDetails) authentication.getPrincipal();
        } catch (AuthenticationException e) {
            throw new Exception("Invalid username or password");
        }

    }*/


}
