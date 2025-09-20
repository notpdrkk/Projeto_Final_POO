package br.com.application;

import br.com.exception.DependenteException;
import br.com.model.Dependente;
import br.com.model.FolhaPagamento;
import br.com.model.Funcionario;
import br.com.model.Parentesco;
import br.com.service.FolhaService;
import resources.CsvReader;
import resources.CsvWriter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
	public static void main(String[] args) {
//        try {
//            String arquivoEntrada = "entrada.csv";
//            String arquivoSaida = "saida.csv";
//
//            // 1. Ler dados do CSV
//            List<Funcionario> funcionarios = CsvReader.lerArquivo(arquivoEntrada);
//
//            // 2. Gerar folhas de pagamento
//            FolhaService folhaService = new FolhaService();
//            List<FolhaPagamento> folhas = new ArrayList<>();
//            int codigo = 1;
//            for (Funcionario f : funcionarios) {
//                folhas.add(folhaService.gerarFolha(f, codigo++));
//            }
//
//            // 3. Escrever saída no CSV
//            CsvWriter.escreverArquivo(arquivoSaida, folhas);
//
//            System.out.println("Processamento concluído! Arquivo gerado: " + arquivoSaida);
//
//        } catch (IOException | DependenteException e) {
//            System.err.println("Erro: " + e.getMessage());
//        }
//    	
//    
//    }

		Funcionario f1 = new Funcionario("teste1", "teste", null, 2000.00);
		Dependente d1 = new Dependente("testeDEPENDENTE", "testecpfDEPENDENTE", null, Parentesco.FILHO);

		System.out.println(f1.getDependentes());

	}
}