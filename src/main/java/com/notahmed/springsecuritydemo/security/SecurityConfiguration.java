package com.notahmed.springsecuritydemo.security;


// will be using jdbc authentication

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.jdbc.JdbcDaoImpl;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {



    // this also got changed
//    using @Autowired is not recommended
    // adding the data source
//    @Bean
//    public DataSource dataSource() {
//        return new EmbeddedDatabaseBuilder()
//                .setType(EmbeddedDatabaseType.H2)
//                .addScript(JdbcDaoImpl.DEFAULT_USER_SCHEMA_DDL_LOCATION)
//                .build();
//    }

    // so spring is just looking up for the file here
    // TODO update to fix the warning
    @Autowired
    DataSource dataSource;

    // adding authentication
    // it will take from the authentication above
    @Bean
    public UserDetailsManager users(DataSource dataSource) {

        // withDefaultPasswordEncoder does noop

        // creating 2 users inside the H2 database
        // will change it in order to create users from file
//        UserDetails user = User.withDefaultPasswordEncoder()
//                .username("user")
//                .password("123456")
//                .roles("USER")
//                .build();
//
//        UserDetails admin = User.withDefaultPasswordEncoder()
//                .username("admin")
//                .password("123456")
//                .roles("ADMIN")
//                .build();

        JdbcUserDetailsManager users  = new JdbcUserDetailsManager(dataSource);
//                .getUsersByUsernameQuery("SELECT username, password, enabled FROM users WHERE username=?",);


//        users.createUser(user);
//        users.createUser(admin);

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



    // this works now because there is no .withDefaultPasswordEncoder()
    @Bean
    public PasswordEncoder getPasswordEncoder(){

        // this is plain text password!! which is bad
        return NoOpPasswordEncoder.getInstance();
    }

}
