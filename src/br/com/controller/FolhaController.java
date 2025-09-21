package br.com.controller;

import br.com.model.Funcionario;
import br.com.service.FolhaService;
import br.com.views.View;

import java.util.List;

public class FolhaController {

    private FolhaService folhaService;
    private View view;

    public FolhaController(FolhaService folhaService, View view) {
        this.folhaService = folhaService;
        this.view = view;
    }

    // Lida com entrada do usuário (via View) e aciona o Service
    public void processarFolhaPagamento(String caminhoEntrada, String caminhoSaida) {
        try {
            List<Funcionario> funcionarios = folhaService.lerFuncionarios(caminhoEntrada);
            folhaService.calcularFolha(funcionarios);
            folhaService.salvarResultado(funcionarios, caminhoSaida);
            view.exibirMensagem("Folha processada com sucesso!");
        } catch (Exception e) {
            view.exibirMensagem("Erro ao processar folha: " + e.getMessage());
        }
    }
}
