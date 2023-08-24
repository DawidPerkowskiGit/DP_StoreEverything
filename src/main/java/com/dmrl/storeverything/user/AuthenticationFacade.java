package com.dmrl.storeverything.user;


import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * User authentication service
 */
@Component
public class AuthenticationFacade implements IAuthenticationFacade {

    /**
     * Fetches user authentication data from application context
     * @return User's authentication data
     */
    @Override
    public Authentication getAuthentication() {
        return SecurityContextHolder.getContext().getAuthentication();
    }
}
