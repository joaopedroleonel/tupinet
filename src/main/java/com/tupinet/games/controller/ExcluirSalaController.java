package com.tupinet.games.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ExcluirSalaController {

    @GetMapping("/excluir-sala")
    public String excluirSala(){
        return "excluirSala.html";
    }
}
