package model;

import java.time.LocalDate;

public class FolhaPagamento {
    private int codigo;
    private Funcionario funcionario;
    private LocalDate dataPagamento;
    private double descontoINSS;
    private double descontoIR;
    private double salarioLiquido;

    public FolhaPagamento(Funcionario funcionario, LocalDate dataPagamento) {
        this.funcionario = funcionario;
        this.dataPagamento = dataPagamento;
    }

    public int getCodigo() { return codigo; }
    public void setCodigo(int codigo) { this.codigo = codigo; }

    public Funcionario getFuncionario() { return funcionario; }
    public void setFuncionario(Funcionario funcionario) { this.funcionario = funcionario; }

    public LocalDate getDataPagamento() { return dataPagamento; }
    public void setDataPagamento(LocalDate dataPagamento) { this.dataPagamento = dataPagamento; }

    public double getDescontoINSS() { return descontoINSS; }
    public void setDescontoINSS(double descontoINSS) { this.descontoINSS = descontoINSS; }

    public double getDescontoIR() { return descontoIR; }
    public void setDescontoIR(double descontoIR) { this.descontoIR = descontoIR; }

    public double getSalarioLiquido() { return salarioLiquido; }
    public void setSalarioLiquido(double salarioLiquido) { this.salarioLiquido = salarioLiquido; }
}