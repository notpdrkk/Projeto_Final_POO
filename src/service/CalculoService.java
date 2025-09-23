package service;

import model.Funcionario;

public interface CalculoService {
    double calcularINSS(double salarioBruto);
    double calcularIR(double salarioBruto, double inss, int numDependentes);
    double calcularSalarioLiquido(Funcionario funcionario);
}