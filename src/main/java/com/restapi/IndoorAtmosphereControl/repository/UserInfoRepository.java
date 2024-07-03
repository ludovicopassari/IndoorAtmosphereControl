package com.restapi.IndoorAtmosphereControl.repository;

import com.restapi.IndoorAtmosphereControl.entity.UserInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserInfoRepository extends JpaRepository<UserInfo,Long> {

    Optional<UserInfo> findByUsername(String username);
    Optional<UserInfo> findByEmail(String email);

}
