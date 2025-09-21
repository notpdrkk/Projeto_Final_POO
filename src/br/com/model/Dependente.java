package br.com.model;

import java.time.LocalDate;

public class Dependente extends Pessoa {
	private Parentesco parentesco;
	private Funcionario funcionarioResponsavel;

	public Dependente(String nome, String cpf, LocalDate dataNascimento, Parentesco parentesco,
			Funcionario funcionarioResponsavel) {
		super(nome, cpf, dataNascimento);
		this.parentesco = parentesco;
		this.funcionarioResponsavel = funcionarioResponsavel;
	}

	public Parentesco getParentesco() {
		return parentesco;
	}

	public void setParentesco(Parentesco parentesco) {
		this.parentesco = parentesco;
	}

	public Funcionario getFuncionarioResponsavel() {
		return funcionarioResponsavel;
	}

	public void setFuncionarioResponsavel(Funcionario funcionarioResponsavel) {
		this.funcionarioResponsavel = funcionarioResponsavel;
	}

	public static Dependente parseDependente(String[] campos, Funcionario funcionario) {
		String nome = campos[0];
		String cpf = campos[1];
		LocalDate dataNascimento = LocalDate.parse(campos[2], java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
		Parentesco parentesco = Parentesco.valueOf(campos[3]);
		return new Dependente(nome, cpf, dataNascimento, parentesco, funcionario);
	}

}