package main;

import model.*;
import service.*;
import java.util.*;
import java.io.IOException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("=== SISTEMA FOLHA DE PAGAMENTO ===");
        
        System.out.println("\nLembre-se de criar o banco de dados 'folha_pagamento' e rodar o script SQL antes de continuar.");
        
        System.out.print("\n📁 Digite o nome do arquivo de entrada: ");
        String arquivoEntrada = scanner.nextLine();
        
        System.out.print("💾 Digite o nome do arquivo de saída: ");
        String arquivoSaida = scanner.nextLine();

        try {
            System.out.println("\n📊 Processando arquivo...");
            List<Funcionario> funcionarios = ProcessadorArquivo.lerArquivo(arquivoEntrada);
            
            System.out.println("✅ Validando dependentes...");
            for (Funcionario func : funcionarios) {
                for (Dependente dep : func.getDependentes()) {
                    if (dep.getDataNascimento().plusYears(18).isAfter(LocalDate.now())) {
                    }
                }
            }
            
            System.out.println("💾 Salvando dados no banco...");
            DatabaseService.salvarFuncionarios(funcionarios);
            
            System.out.println("📄 Gerando arquivo de saída...");
            ProcessadorArquivo.escreverArquivo(arquivoSaida, funcionarios);
            
            System.out.println("💰 Gerando folha de pagamento no banco...");
            DatabaseService.gerarFolhaPagamento(funcionarios);
            
            System.out.println("\n🎉 Processamento concluído com sucesso!");
            System.out.println("📊 Total de funcionários processados: " + funcionarios.size());
            
        } catch (IOException e) {
            System.out.println("❌ Erro de arquivo: " + e.getMessage());
        } catch (DependenteException e) {
            System.out.println("❌ Erro de validação: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("❌ Erro inesperado: " + e.getMessage());
            e.printStackTrace();
        } finally {
            scanner.close();
        }
    }
}