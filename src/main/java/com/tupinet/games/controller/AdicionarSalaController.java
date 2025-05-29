package com.tupinet.games.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdicionarSalaController {

    @GetMapping("/adicionar-sala")
    public String adicionarSala() {
        return "adicionarSala.html";
    }

}