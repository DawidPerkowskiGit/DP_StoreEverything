package com.dmrl.storeverything.user;

import com.dmrl.storeverything.user.CustomUserDetails;
import com.dmrl.storeverything.user.User;
import com.dmrl.storeverything.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * Extension of UserDetailsService, adds method to fetch user based on username
 */
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repo;

    /**
     * Fetch User's details based on his username
     *
     * @param login the username identifying the user whose data is required.
     * @return User's details
     */
    @Override
    public UserDetails loadUserByUsername(String login) {
        User user = new User();
        try {
            user = repo.findByLogin(login);
        } catch (Exception e) {
            System.out.println("Could not find user in the datbase. Exception: " + e);
        }

        return new CustomUserDetails(user);
    }
}
