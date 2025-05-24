package com.tupinet.games.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SalasController {

    @GetMapping("/gerenciamento-salas")
    public String gerenciamentoSalas(){
        return "gerenciamentoSalas.html";
    }

}