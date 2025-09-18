package br.com.empresa.folhapagamento.app;

import br.com.empresa.folhapagamento.model.Funcionario;
import br.com.empresa.folhapagamento.model.FolhaPagamento;
import br.com.empresa.folhapagamento.service.FolhaService;
import br.com.empresa.folhapagamento.util.CsvReader;
import br.com.empresa.folhapagamento.util.CsvWriter;
import br.com.empresa.folhapagamento.exception.DependenteException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            String arquivoEntrada = "entrada.csv";
            String arquivoSaida = "saida.csv";

            // 1. Ler dados do CSV
            List<Funcionario> funcionarios = CsvReader.lerArquivo(arquivoEntrada);

            // 2. Gerar folhas de pagamento
            FolhaService folhaService = new FolhaService();
            List<FolhaPagamento> folhas = new ArrayList<>();
            int codigo = 1;
            for (Funcionario f : funcionarios) {
                folhas.add(folhaService.gerarFolha(f, codigo++));
            }

            // 3. Escrever saída no CSV
            CsvWriter.escreverArquivo(arquivoSaida, folhas);

            System.out.println("Processamento concluído! Arquivo gerado: " + arquivoSaida);

        } catch (IOException | DependenteException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    	
    
    }
}
