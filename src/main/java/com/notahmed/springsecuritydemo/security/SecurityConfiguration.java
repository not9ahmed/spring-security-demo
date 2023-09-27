package com.notahmed.springsecuritydemo.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;


@Configuration
@EnableWebSecurity
public class SecurityConfiguration{


    // using the updated spring security


    // bean telling spring security to use in memory authentication
    @Bean
    public InMemoryUserDetailsManager userDetailsService() {

        //.withDefaultPasswordEncoder()

        UserDetails user = User.builder()
                .username("user")
                .password("{noop}password")
                .roles("USER")
                .build();

        // adding second user
        UserDetails admin = User.builder()
                .username("admin")
                .password("{noop}password")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, admin);
    }




    // for some reason it is causing issues
//    @Bean
//    public PasswordEncoder getPasswordEncoder(){
//
//        // this is plain text password!! which is bad
//        return NoOpPasswordEncoder.getInstance();
//    }


    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {


        // Most restrictive to less

        http
                .authorizeHttpRequests(auth -> auth
                                    .requestMatchers("/admin").hasRole("ADMIN")
                                    .requestMatchers("/user").hasAnyRole("ADMIN", "USER")
                                    .requestMatchers("/").permitAll()

                )
                .formLogin(Customizer.withDefaults());


        return http.build();
    }


}


/*

!!previous version for authentication!!

    @Configuration
    public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
        @Override
        protected void configure(AuthenticationManagerBuilder auth) throws Exception {
            UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("password")
                .roles("USER")
                .build();
            auth.inMemoryAuthentication()
                .withUser(user);
        }
    }

 */


/*
Previous version for http security

@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((authz) -> authz
                .anyRequest().authenticated()
            )
            .httpBasic(withDefaults());
    }

}

 */