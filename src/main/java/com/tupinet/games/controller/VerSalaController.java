package com.tupinet.games.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class VerSalaController {

    @GetMapping("/ver-sala")
    public String verSala() {
        return "verSala"; // sem .html
    }

}
