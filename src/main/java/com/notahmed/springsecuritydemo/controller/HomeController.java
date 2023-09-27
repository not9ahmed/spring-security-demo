package com.notahmed.springsecuritydemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequestMapping("")
public class HomeController {

    @GetMapping("/")
    public String home() {
        return "<h1>Hello World</h1>";
    }


    // accessible by user only
    @GetMapping("/user")
    public String user() {
        return "<h1>Welcome User</h1>";
    }



    // accessible by admin only
    @GetMapping("/admin")
    public String admin() {
        return "<h1>Welcome Admin</h1>";
    }




}
