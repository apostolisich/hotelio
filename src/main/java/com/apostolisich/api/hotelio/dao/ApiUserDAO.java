package com.apostolisich.api.hotelio.dao;

import com.apostolisich.api.hotelio.dao.entity.ApiUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ApiUserDAO extends JpaRepository<ApiUser, Long> {
    ApiUser findByUsername(String username);

}
