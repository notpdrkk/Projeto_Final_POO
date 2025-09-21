package br.com.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import br.com.model.Dependente;
import br.com.model.FolhaPagamento;
import br.com.model.Funcionario;

public class FolhaService {

	private static final double DEDUCAO_DEPENDENTE = 189.59; @SuppressWarnings("unused")

	public FolhaPagamento gerarFolha(Funcionario funcionario, int codigo) {
		double salarioBruto = funcionario.getSalarioBruto();

		
		Calculo calcINSS = new CalculoINSS();
		Calculo calcIR = new CalculoIR();

		// Calcular INSS
		double descontoINSS = calcINSS.calcular(salarioBruto);

		// Base de cálculo do IR
		double baseIR = salarioBruto - descontoINSS - (funcionario.getDependentes().size() * DEDUCAO_DEPENDENTE);
		if (baseIR < 0)
			baseIR = 0;

		// Calcular IR
		double descontoIR = calcIR.calcular(baseIR);
		if (descontoIR < 0)
			descontoIR = 0;

		// Salário líquido
		double salarioLiquido = salarioBruto - descontoINSS - descontoIR;

		// Criar folha
		FolhaPagamento folha = new FolhaPagamento(codigo, funcionario, LocalDate.now());
		folha.setDescontoINSS(descontoINSS);
		folha.setDescontoIR(descontoIR);
		folha.setSalarioLiquido(salarioLiquido);

		return folha;
	}

	public List<FolhaPagamento> calcularFolha(List<Funcionario> funcionarios) {
		List<FolhaPagamento> folhas = new ArrayList<>();
		int codigo = 1;
		for (Funcionario funcionario : funcionarios) {
			FolhaPagamento folha = gerarFolha(funcionario, codigo++);
			folhas.add(folha);
		}
		return folhas;
	}

	public void salvarResultado(List<FolhaPagamento> folhas, String caminhoSaida) {
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoSaida))) {
			for (FolhaPagamento folha : folhas) {
				Funcionario funcionario = folha.getFuncionario();
				String linha = funcionario.getNome() + ";" + funcionario.getCpf() + ";"
						+ String.format("%.2f", folha.getDescontoINSS()) + ";"
						+ String.format("%.2f", folha.getDescontoIR()) + ";"
						+ String.format("%.2f", folha.getSalarioLiquido());
				bw.write(linha);
				bw.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public List<Funcionario> lerFuncionarios(String arquivoEntrada) {
		List<Funcionario> funcionarios = new ArrayList<>();
		Funcionario funcionarioAtual = null;
		try (BufferedReader br = new BufferedReader(new FileReader(arquivoEntrada))) {
			String linha;
			while ((linha = br.readLine()) != null) {
				if (linha.trim().isEmpty()) {
					funcionarioAtual = null;
					continue;
				}
				String[] campos = linha.split(";");
				if (campos.length == 4 && campos[3].matches("\\d+\\.\\d{2}")) {
					// Funcionário
					String nome = campos[0];
					String cpf = campos[1];
					LocalDate dataNascimento = LocalDate.parse(campos[2], DateTimeFormatter.ofPattern("yyyyMMdd"));
					double salarioBruto = Double.parseDouble(campos[3]);
					funcionarioAtual = new Funcionario(nome, cpf, dataNascimento, salarioBruto);
					funcionarios.add(funcionarioAtual);
				} else if (funcionarioAtual != null && campos.length == 4) {
					// Dependente
					Dependente dependente = Dependente.parseDependente(campos, funcionarioAtual);
					funcionarioAtual.addDependente(dependente);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return funcionarios;
	}

	public void processarFolha(String arquivoEntrada, String arquivoSaida) {
		List<Funcionario> funcionarios = lerFuncionarios(arquivoEntrada);
		List<FolhaPagamento> folhas = calcularFolha(funcionarios);
		salvarResultado(folhas, arquivoSaida);
	
	}
}