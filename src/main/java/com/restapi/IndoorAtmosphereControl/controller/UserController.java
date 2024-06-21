package com.restapi.IndoorAtmosphereControl.controller;

import com.restapi.IndoorAtmosphereControl.http.response.DeviceResponse;
import com.restapi.IndoorAtmosphereControl.jwt.JwtService;
import com.restapi.IndoorAtmosphereControl.repository.UserInfoRepository;
import com.restapi.IndoorAtmosphereControl.service.UserInfoService;
import com.restapi.IndoorAtmosphereControl.xml.XmlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.jdom2.Element;
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

    @Autowired
    private XmlConfig xmlConfigEndpoint;

    @GetMapping("/devices")
    public String getAllUserDevice(@RequestHeader("Authorization") String token) {
        String email = jwtService.extractEmail(token.substring(7));
        String id = userInfoService.getIdByEmail(email);
        logger.info(id);


        Element element = xmlConfigEndpoint.XmlParser().getRootElement();

        String domain = element.getChildText("domain");
        String port = element.getChildText("port");
        String apiBaseEndpoint = element.getChildText("apibaseendpoint");


        String endpoint = "http://"+domain+":"+port+apiBaseEndpoint+ "get/"+id;
        logger.info(endpoint);
        return restTemplate.getForObject(endpoint, String.class);
    }

    @GetMapping("/measurement/{idDevice}")
    public String getLastDeviceMesure(@RequestHeader("Authorization") String token , @PathVariable String idDevice) {
        String email = jwtService.extractEmail(token.substring(7));
        String id = userInfoService.getIdByEmail(email);

        return restTemplate.getForObject("http://localhost:8081/api/devices/measurement/22F44", String.class);
    }
}
