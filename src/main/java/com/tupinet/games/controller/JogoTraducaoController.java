package com.tupinet.games.controller;

import com.tupinet.games.service.TraducaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.Optional;

@Controller
@RequestMapping
public class JogoTraducaoController {

    @Autowired
    TraducaoService traducaoService;

    @GetMapping("jogo-traducao")
    public String jogoTraducao(Model model){

        traducaoService.getPalavraAleatoriaComTraducao().ifPresent(palavraTraducao -> {
            model.addAttribute("palavra", palavraTraducao.get("palavra"));
            model.addAttribute("traducao", palavraTraducao.get("traducao"));
        });

        return "traducao.html";

    }

    @ResponseBody
    @GetMapping("/traducao-aleatoria")
    public ResponseEntity<Map<String, String>> getPalavraAleatoria() {
        Optional<Map<String, String>> resultado = Optional.empty();

        while (resultado.isEmpty()) {
            resultado = traducaoService.getPalavraAleatoriaComTraducao();
        }

        return resultado
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.noContent().build());
    }

    @PostMapping("transcrever")
    public ResponseEntity<String> transcrever(@RequestBody byte[] audioBytes) {
        try {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.valueOf("audio/wav"));

            HttpEntity<byte[]> requestEntity = new HttpEntity<>(audioBytes, headers);

            RestTemplate restTemplate = new RestTemplate();
            String url = "http://127.0.0.1:5000/";

            ResponseEntity<String> response = restTemplate.postForEntity(url, requestEntity, String.class);

            return ResponseEntity.status(response.getStatusCode()).body(response.getBody());

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro: " + e.getMessage());
        }
    }
}