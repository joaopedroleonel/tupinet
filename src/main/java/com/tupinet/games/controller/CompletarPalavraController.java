package com.tupinet.games.controller;

import com.tupinet.games.model.PalavraJogo;
import com.tupinet.games.service.PalavraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import jakarta.servlet.http.HttpSession;

import java.util.*;

@Controller
public class CompletarPalavraController {

    @Autowired
    private PalavraService palavraService;

    private PalavraJogo palavraAtual;

    @GetMapping("/completarPalavra")
    public String exibirJogo(Model model, HttpSession session) {
        iniciarSessaoSeNecessario(session);

        palavraAtual = palavraService.getPalavraAleatoria();
        session.setAttribute("palavraAtual", palavraAtual);

        model.addAttribute("lacuna", palavraAtual.getComLacunas());
        model.addAttribute("indices", palavraAtual.getIndicesFaltando());
        model.addAttribute("rodada", session.getAttribute("rodada"));
        model.addAttribute("score", session.getAttribute("score"));
        return "completarPalavra.html";
    }

    @PostMapping("/verificar")
    public String verificarResposta(@RequestParam Map<String, String> params, Model model, HttpSession session) {
        PalavraJogo palavraSessao = (PalavraJogo) session.getAttribute("palavraAtual");

        Map<Integer, Character> respostas = new HashMap<>();
        for (String key : params.keySet()) {
            if (key.startsWith("letra")) {
                int idx = Integer.parseInt(key.replace("letra", ""));
                char c = params.get(key).charAt(0);
                respostas.put(idx, c);
            }
        }

        boolean acertou = palavraService.validarResposta(palavraSessao, respostas);

        // Atualiza score e rodada
        int rodada = (int) session.getAttribute("rodada");
        int score = (int) session.getAttribute("score");
        int acertos = (int) session.getAttribute("acertos");

        if (acertou) {
            score += 10;
            acertos++;
            model.addAttribute("mensagem", "✅ Parabéns! Você acertou!");
        } else {
            model.addAttribute("mensagem", "❌ Você errou!");
        }

        rodada++;

        session.setAttribute("rodada", rodada);
        session.setAttribute("score", score);
        session.setAttribute("acertos", acertos);

        // Verifica se acabou
        if (rodada > 10) {
            model.addAttribute("acertos", acertos);
            model.addAttribute("score", score);
            return "fimDeJogo.html"; // Página final com resultado
        }

        // Próxima palavra
        palavraAtual = palavraService.getPalavraAleatoria();
        session.setAttribute("palavraAtual", palavraAtual);

        model.addAttribute("lacuna", palavraAtual.getComLacunas());
        model.addAttribute("indices", palavraAtual.getIndicesFaltando());
        model.addAttribute("rodada", rodada);
        model.addAttribute("score", score);

        return "completarPalavra.html";
    }

    @GetMapping("/reiniciar")
    public String reiniciarJogo(HttpSession session) {
        session.invalidate();
        return "redirect:/completarPalavra";
    }

    private void iniciarSessaoSeNecessario(HttpSession session) {
        if (session.getAttribute("rodada") == null) {
            session.setAttribute("rodada", 1);
            session.setAttribute("score", 0);
            session.setAttribute("acertos", 0);
        }
    }
}
