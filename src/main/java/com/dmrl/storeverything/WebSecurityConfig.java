package com.dmrl.storeverything;

import com.dmrl.storeverything.user.CustomUserDetailsService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * Configuration class which specifies various security measures
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {


    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Custom User Authentication provider
     * @return Provided authentication
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Configure custom authenctication provider
     * @param auth the {@link AuthenticationManagerBuilder} to use
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        auth.authenticationProvider(authenticationProvider());
    }

    /**
     * Defines which endpoints can be accessed, based by User role
     * @param http the {@link HttpSecurity} to modify
     * @throws Exception Authorization exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/_ah/start").permitAll()
                .antMatchers("/*", "/css/**", "/js/**", "/images/**", "/webjars/**", "/fragments/**" ).permitAll()
                .antMatchers("/homepage").hasAnyAuthority("L_USER", "F_USER", "ADMIN")
                .antMatchers("/informations/**").hasAnyAuthority("F_USER", "ADMIN")
                .antMatchers("/limited/**").hasAnyAuthority("L_USER", "F_USER", "ADMIN")
                .antMatchers("/user/**").hasAnyAuthority("L_USER", "F_USER", "ADMIN")
                .antMatchers("/categories/**").hasAnyAuthority("ADMIN")
                .antMatchers("/admin/**").hasAuthority("ADMIN")
//                .antMatchers("/api/**").permitAll() // testing API
                .antMatchers("/adminapi/**").hasAnyAuthority("ADMIN")
                .antMatchers("/api/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .usernameParameter("login")
                .defaultSuccessUrl("/afterLoggingIn")
                .permitAll()
                .and()
                .logout()
                .logoutSuccessUrl("/?logout").permitAll()
                .and()
                .exceptionHandling().accessDeniedPage("/error/403");
    }
}
