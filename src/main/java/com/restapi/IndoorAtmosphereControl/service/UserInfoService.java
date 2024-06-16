package com.restapi.IndoorAtmosphereControl.service;

import com.restapi.IndoorAtmosphereControl.entity.UserInfo;
import com.restapi.IndoorAtmosphereControl.entity.UserInfoDetails;
import com.restapi.IndoorAtmosphereControl.repository.UserInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserInfoService implements UserDetailsService {

    @Autowired
    private UserInfoRepository repository;
    Logger logger = Logger.getLogger(UserInfoService.class.getName());

    /*
        Implementiamo il metodo in modo tale che q.....
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserInfo> userDetail = repository.findByEmail(username);
        if (userDetail.isPresent()) {
            UserInfo userInfo = userDetail.get();
            return new UserInfoDetails(userInfo);
        }else{
            throw new UsernameNotFoundException(username);
        }
    }


}
