package service;

import model.*;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class ProcessadorArquivo {
    
    public static List<Funcionario> lerArquivo(String nomeArquivo) throws IOException, DependenteException {
        List<Funcionario> funcionarios = new ArrayList<>();
        Set<String> cpfsProcessados = new HashSet<>();
        Funcionario funcionarioAtual = null;

        try (BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                if (linha.trim().isEmpty()) {
                    funcionarioAtual = null;
                    continue;
                }

                String[] dados = linha.split(";");
                if (dados.length == 4) {
                    String cpf = dados[1].trim();
                    if (!cpfsProcessados.add(cpf)) {
                        throw new DependenteException("Erro: CPF duplicado encontrado no arquivo: " + cpf);
                    }

                    if (dados[3].matches("\\d+\\.\\d{2}")) {
                        String nome = dados[0].trim();
                        LocalDate dataNasc = parseData(dados[2].trim());
                        double salario = Double.parseDouble(dados[3].trim());
                        
                        funcionarioAtual = new Funcionario(nome, cpf, dataNasc, salario);
                        funcionarios.add(funcionarioAtual);
                    } else {
                        if (funcionarioAtual != null) {
                            String nome = dados[0].trim();
                            LocalDate dataNasc = parseData(dados[2].trim());
                            Parentesco parentesco = Parentesco.valueOf(dados[3].trim().toUpperCase());
                            
                            Dependente dependente = new Dependente(nome, cpf, dataNasc, parentesco);
                            funcionarioAtual.addDependente(dependente);
                        }
                    }
                }
            }
        }
        return funcionarios;
    }

    public static void escreverArquivo(String nomeArquivo, List<Funcionario> funcionarios) throws IOException {
        CalculoService calcService = new CalculadoraSalario();
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(nomeArquivo))) {
            for (Funcionario func : funcionarios) {
                double liquido = calcService.calcularSalarioLiquido(func);
                
                String linha = String.format(new Locale("pt", "BR"), "%s;%s;%.2f;%.2f;%.2f\n", 
                    func.getNome(), 
                    func.getCpf(), 
                    func.getDescontoINSS(), 
                    func.getDescontoIR(), 
                    liquido);
                writer.write(linha);
            }
        }
    }

    private static LocalDate parseData(String dataStr) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");
        return LocalDate.parse(dataStr, formatter);
    }
}