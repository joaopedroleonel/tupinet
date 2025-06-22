package com.tupinet.games.controller;

import com.tupinet.games.model.Palavreca;
import com.tupinet.games.service.PalavrecaService;
import com.tupinet.games.service.PontuacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.ui.Model;

import jakarta.servlet.http.HttpSession;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
@AutoConfigureMockMvc(addFilters = false)
class PalavrecaControllerUnitTest {

    @Mock
    private PalavrecaService palavraService;

    @Mock
    private PontuacaoService pontuacaoService;

    @Mock
    private Model model;

    @Mock
    private HttpSession session;

    @InjectMocks
    private PalavrecaController palavrecaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void deveAtualizarScoreRodadaRedirecionar() {
        Palavreca palavraSessao = new Palavreca("java", "j_v_", new HashSet<>(Arrays.asList(0, 2)), "linguagem");
        when(session.getAttribute("palavraAtual")).thenReturn(palavraSessao);
        when(session.getAttribute("rodada")).thenReturn(1);
        when(session.getAttribute("score")).thenReturn(0);
        when(session.getAttribute("acertos")).thenReturn(0);

        when(palavraService.validarResposta(eq(palavraSessao), anyMap())).thenReturn(true);
        when(palavraService.getPalavraAleatoria()).thenReturn(new Palavreca("nova", "n_va", new HashSet<>(Arrays.asList(0)), "nova_tupi"));

        Map<String, String> params = new HashMap<>();
        params.put("letra0", "j");
        params.put("letra2", "v");

        String viewName = palavrecaController.verificarResposta(params, session);

        assertEquals("redirect:/palavreca", viewName);
        verify(session).removeAttribute("palavraAtual");
        verify(palavraService).validarResposta(eq(palavraSessao), anyMap());
        verify(session).setAttribute("mensagem", "✅ Parabéns! Você acertou!");
        verify(session).setAttribute("rodada", 2);
        verify(session).setAttribute("score", 10);
        verify(session).setAttribute("acertos", 1);
        verify(palavraService).getPalavraAleatoria();
    }

    @Test
    void deveAtualizarRodadaManterScoreRedirecionar() {
        Palavreca palavraSessao = new Palavreca("erro", "_rr_", new HashSet<>(Arrays.asList(0, 1)), "falha");
        when(session.getAttribute("palavraAtual")).thenReturn(palavraSessao);
        when(session.getAttribute("rodada")).thenReturn(1);
        when(session.getAttribute("score")).thenReturn(0);
        when(session.getAttribute("acertos")).thenReturn(0);

        when(palavraService.validarResposta(eq(palavraSessao), anyMap())).thenReturn(false);
        when(palavraService.getPalavraAleatoria()).thenReturn(new Palavreca("prox", "pr_x", new HashSet<>(Arrays.asList(0)), "prox_tupi"));

        Map<String, String> params = new HashMap<>();
        params.put("letra0", "x");
        params.put("letra1", "y");

        String viewName = palavrecaController.verificarResposta(params, session);

        assertEquals("redirect:/palavreca", viewName);
        verify(session).removeAttribute("palavraAtual");
        verify(palavraService).validarResposta(eq(palavraSessao), anyMap());
        verify(session).setAttribute("mensagem", "❌ Você errou!");
        verify(session).setAttribute("rodada", 2);
        verify(session).setAttribute("score", 0);
        verify(session).setAttribute("acertos", 0);
        verify(palavraService).getPalavraAleatoria();
    }

    @Test
    void deveRedirecionarParaFinal() {
        Palavreca palavraSessao = new Palavreca("fim", "f_m", new HashSet<>(Arrays.asList(0)), "final");
        when(session.getAttribute("palavraAtual")).thenReturn(palavraSessao);
        when(session.getAttribute("rodada")).thenReturn(10);
        when(session.getAttribute("score")).thenReturn(90);
        when(session.getAttribute("acertos")).thenReturn(9);

        when(palavraService.validarResposta(eq(palavraSessao), anyMap())).thenReturn(true);

        Map<String, String> params = new HashMap<>();
        params.put("letra0", "f");

        String viewName = palavrecaController.verificarResposta(params, session);

        assertEquals("redirect:/palavreca/final", viewName);
        verify(session).removeAttribute("palavraAtual");
        verify(session).setAttribute("rodada", 11);
        verify(session).setAttribute("score", 100);
        verify(session).setAttribute("acertos", 10);
        verify(palavraService, never()).getPalavraAleatoria();
    }

    @Test
    void deveRedirecionarParaPalavreca() {
        when(session.getAttribute("palavraAtual")).thenReturn(null);

        Map<String, String> params = new HashMap<>();

        String viewName = palavrecaController.verificarResposta(params, session);

        assertEquals("redirect:/palavreca", viewName);
        verify(palavraService, never()).validarResposta(any(), anyMap());
        verify(palavraService, never()).getPalavraAleatoria();
    }

    @Test
    void deveAdicionarScoreAoModelo() {
        when(session.getAttribute("acertos")).thenReturn(7);
        when(session.getAttribute("score")).thenReturn(70);

        String viewName = palavrecaController.exibirFinal(model, session);

        assertEquals("palavrecaFinal.html", viewName);
        verify(model).addAttribute("acertos", 7);
        verify(model).addAttribute("score", 70);
    }

    @Test
    void deveResetarPalavrasInvalidarSessao() {
        String viewName = palavrecaController.reiniciarJogo(session);

        assertEquals("redirect:/palavreca", viewName);
        verify(palavraService).resetPalavrasUsadas();
        verify(session).invalidate();
    }
}