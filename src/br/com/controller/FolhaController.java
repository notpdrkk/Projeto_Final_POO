package br.com.controller;

import java.util.List;

import br.com.model.FolhaPagamento;
import br.com.model.Funcionario;
import br.com.service.FolhaService;
import br.com.views.View;

public class FolhaController {

	private FolhaService folhaService;
	private View view;

	public FolhaController(FolhaService folhaService, View view) {
		this.folhaService = folhaService;
		this.view = view;
	}

	public void processarFolhaPagamento(String caminhoEntrada, String caminhoSaida) {
		try {
			List<Funcionario> funcionarios = folhaService.lerFuncionarios(caminhoEntrada);
			List<FolhaPagamento> folhas = folhaService.calcularFolha(funcionarios);
			folhaService.salvarResultado(folhas, caminhoSaida);
			view.exibirMensagem("Folha processada com sucesso!");
		} catch (Exception e) {
			view.exibirMensagem("Erro ao processar folha: " + e.getMessage());
		}
	}
}
