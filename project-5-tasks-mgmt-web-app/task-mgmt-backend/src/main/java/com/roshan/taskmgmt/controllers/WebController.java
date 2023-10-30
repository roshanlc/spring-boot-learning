package com.roshan.taskmgmt.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1")
public class WebController {

    @GetMapping("/home")
    public String home() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return ":home after auth successfully";
    }

}
