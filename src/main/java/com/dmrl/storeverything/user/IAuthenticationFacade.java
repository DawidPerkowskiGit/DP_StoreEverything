package com.dmrl.storeverything.user;

import org.springframework.security.core.Authentication;

/**
 * Common interface for Authentication service
 */
public interface IAuthenticationFacade {
    Authentication getAuthentication();
}
