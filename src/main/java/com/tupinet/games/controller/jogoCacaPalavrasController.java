package com.tupinet.games.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class jogoCacaPalavrasController {

    @GetMapping("/jogoCacaPalavras")
    public String cacaPalavras() {
        return "jogoCacaPalavras.html";
    }

}
