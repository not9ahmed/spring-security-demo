package com.notahmed.springsecuritydemo.security;


// will be using jdbc authentication

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {



    // adding the data source
    @Autowired
    DataSource dataSource;


    // adding authentication
    @Bean
    public UserDetailsManager users(DataSource dataSource) {
        UserDetails user = User.withDefaultPasswordEncoder().build();

        JdbcUserDetailsManager users  = new JdbcUserDetailsManager(dataSource);
        users.createUser(user);

        return users;
    }


    // adding authorization
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/admin").hasRole("ADMIN")
                        .requestMatchers("/user").hasAnyRole("ADMIN","USER")
                        .requestMatchers("/**").permitAll()
                )
                .formLogin(Customizer.withDefaults());

        // form login is the default spring security login form that appears

        return http.build();
    }


}
