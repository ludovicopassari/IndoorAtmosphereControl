package com.restapi.IndoorAtmosphereControl.controller;

import com.restapi.IndoorAtmosphereControl.auth.UserAlreadyExistsException;
import com.restapi.IndoorAtmosphereControl.entity.UserInfo;
import com.restapi.IndoorAtmosphereControl.entity.UserInfoDetails;
import com.restapi.IndoorAtmosphereControl.http.request.AuthRequest;
import com.restapi.IndoorAtmosphereControl.http.response.AuthResponse;
import com.restapi.IndoorAtmosphereControl.jwt.JwtService;
import com.restapi.IndoorAtmosphereControl.utils.Message;
import com.restapi.IndoorAtmosphereControl.utils.RegisterMessage;
import com.restapi.IndoorAtmosphereControl.utils.RegisterStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.logging.Logger;

@RestController
public class AuthController {

    Logger logger = Logger.getLogger(AuthController.class.getName());

    @Autowired
    private JwtService jwtService;


    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserInfo userInfo) {
        Message message = new RegisterMessage();
        try {

            jwtService.registerUser(userInfo);
            return ResponseEntity.ok(message.getMessage(RegisterStatus.SUCCESSFULLY_REGISTER));

        } catch (UserAlreadyExistsException e) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(message.getMessage(RegisterStatus.BAD_REGISTER));
        }

    }


    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@RequestBody AuthRequest authRequest) {
        try {
            // todo stai modificando qui UserInfoDetails userDetails = jwtService.authenticate(authRequest);
            UserInfoDetails userDetails = jwtService.authenticate(authRequest);
            logger.info(userDetails.getEmail());
            String token = jwtService.generateToken(userDetails.getEmail());

            return ResponseEntity.ok().body(new AuthResponse(token));

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Login failed: " + e.getMessage());
        }
    }

}