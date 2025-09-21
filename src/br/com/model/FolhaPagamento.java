package br.com.model;

import java.time.LocalDate;

public class FolhaPagamento {
	private int codigo;
	private Funcionario funcionario;
	private LocalDate dataPagamento;
	private double descontoINSS;
	private double descontoIR;
	private double salarioLiquido;

	public FolhaPagamento(int codigo, Funcionario funcionario, LocalDate dataPagamento) {
		this.codigo = codigo;
		this.funcionario = funcionario;
		this.dataPagamento = dataPagamento;
	}

	public int getCodigo() {
		return codigo;
	}

	public Funcionario getFuncionario() {
		return funcionario;
	}

	public LocalDate getDataPagamento() {
		return dataPagamento;
	}

	public double getDescontoINSS() {
		return descontoINSS;
	}

	public void setDescontoINSS(double descontoINSS) {
		this.descontoINSS = descontoINSS;
	}

	public double getDescontoIR() {
		return descontoIR;
	}

	public void setDescontoIR(double descontoIR) {
		this.descontoIR = descontoIR;
	}

	public double getSalarioLiquido() {
		return salarioLiquido;
	}

	public void setSalarioLiquido(double salarioLiquido) {
		this.salarioLiquido = salarioLiquido;
	}
}
