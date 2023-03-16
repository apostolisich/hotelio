package com.apostolisich.api.hotelio.restcontroller;

import com.apostolisich.api.hotelio.service.JwtTokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    private final Logger LOG = LoggerFactory.getLogger(AuthController.class);

    private final JwtTokenService jwtTokenService;

    @Autowired
    public AuthController(JwtTokenService jwtTokenService) {
        this.jwtTokenService = jwtTokenService;
    }

    @PostMapping("/jwtToken")
    public String generateJwtToken(Authentication authentication) {
        LOG.debug("JwtToken requested for user: '{}'", authentication.getName());
        String jwtToken = jwtTokenService.generateJwtToken(authentication);
        LOG.debug("JwtToken granted: {}", jwtToken);

        return jwtToken;
    }

}
