package com.apostolisich.api.hotelio.service;

import com.apostolisich.api.hotelio.dao.ApiUserDAO;
import com.apostolisich.api.hotelio.dao.entity.ApiUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * A custom implementation of the {@link UserDetailsService} in order to create a {@code UserDetails}
 * object based on user info that was retrieved from a custom table in the DB.
 *
 * The class is marked with the {@code Service} annotation and will therefore be automatically
 * injected/used to/by the {@code DaoAuthenticationProvider}.
 */
@Service
public class ApiUserDetailsService implements UserDetailsService {

    private final ApiUserDAO apiUserDAO;

    @Autowired
    public ApiUserDetailsService(ApiUserDAO apiUserDAO) {
        this.apiUserDAO = apiUserDAO;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiUser apiUser = apiUserDAO.findByUsername(username);
        if(apiUser == null) {
            throw new UsernameNotFoundException("User doesn't exist: " + username);
        }

        return User.builder()
                .username(apiUser.getUsername())
                .password(apiUser.getPassword())
                //TODO Implement roles
                .roles("")
                .build();
    }

}
