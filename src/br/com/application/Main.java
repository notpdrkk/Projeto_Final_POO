package br.com.application;

import br.com.views.*;
import br.com.controller.FolhaController;
import br.com.service.FolhaService;

public class Main {
	public static void main(String[] args) {
		View view = new View();
		FolhaService service = new FolhaService();
		FolhaController controller = new FolhaController(service, view);

		String entrada = view.pedirCaminhoEntrada();
		String saida = view.pedirCaminhoSaida();

		controller.processarFolhaPagamento(entrada, saida);
	}
}
