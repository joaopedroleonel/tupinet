package com.tupinet.games.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoginProfessorController {

    @GetMapping("/login-professor")
    public String loginP(){
        return "loginP.html";
    }

}
