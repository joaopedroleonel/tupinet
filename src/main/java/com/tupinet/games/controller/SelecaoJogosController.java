package com.tupinet.games.controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class SelecaoJogosController {
    @GetMapping("/selecaoJogos")
    public String selecaoJogosAluno(){
        return "homeAlunoSelJogos.html";
    }
}

