package br.com.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Funcionario extends Pessoa {
    private double salarioBruto;
    private double descontoINSS;
    private double descontoIR;
    private Set<Dependente> dependentes = new HashSet<>();

    public Funcionario(String nome, String cpf, LocalDate dataNascimento, double salarioBruto) {
        super(nome, cpf, dataNascimento);
        this.salarioBruto = salarioBruto;
    }

    public double getSalarioBruto() { return salarioBruto; }
    public void setSalarioBruto(double salarioBruto) { this.salarioBruto = salarioBruto; }

    public double getDescontoINSS() { return descontoINSS; }
    public void setDescontoINSS(double descontoINSS) { this.descontoINSS = descontoINSS; }

    public double getDescontoIR() { return descontoIR; }
    public void setDescontoIR(double descontoIR) { this.descontoIR = descontoIR; }

    public Set<Dependente> getDependentes() { return dependentes; }

    public void adicionarDependente(Dependente dependente) {
        dependentes.add(dependente);
    }
}
