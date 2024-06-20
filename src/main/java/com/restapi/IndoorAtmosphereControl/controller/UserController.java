package com.restapi.IndoorAtmosphereControl.controller;

import com.restapi.IndoorAtmosphereControl.http.response.DeviceResponse;
import com.restapi.IndoorAtmosphereControl.jwt.JwtService;
import com.restapi.IndoorAtmosphereControl.repository.UserInfoRepository;
import com.restapi.IndoorAtmosphereControl.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.logging.Logger;

@RestController
@RequestMapping("/user")
public class UserController {

    Logger logger = Logger.getLogger(this.getClass().getName());

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("/devices")
    public String areapersonale(@RequestHeader("Authorization") String token) {
        String email = jwtService.extractEmail(token.substring(7));
        String id = userInfoService.getIdByEmail(email);
        logger.info(id);
        return restTemplate.getForObject("http://localhost:8081/api/devices/get/1", String.class);
    }

}
