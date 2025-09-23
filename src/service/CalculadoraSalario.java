package service;

import model.Funcionario;

public class CalculadoraSalario implements CalculoService {
    private static final double VALOR_DEPENDENTE = 189.59;

    @Override
    public double calcularINSS(double salarioBruto) {
        if (salarioBruto <= 1518.00) {
            return salarioBruto * 0.075;
        } else if (salarioBruto <= 2793.88) {
            return salarioBruto * 0.09 - 22.77;
        } else if (salarioBruto <= 4190.83) {
            return salarioBruto * 0.12 - 106.60;
        } else if (salarioBruto <= 8157.41) {
            return salarioBruto * 0.14 - 190.42;
        } else {
            return 951.62;
        }
    }

    @Override
    public double calcularIR(double salarioBruto, double inss, int numDependentes) {
        double baseCalculo = salarioBruto - inss - (numDependentes * VALOR_DEPENDENTE);
        
        if (baseCalculo <= 2259.00) {
            return 0;
        } else if (baseCalculo <= 2826.65) {
            return baseCalculo * 0.075 - 169.44;
        } else if (baseCalculo <= 3751.05) {
            return baseCalculo * 0.15 - 381.44;
        } else if (baseCalculo <= 4664.68) {
            return baseCalculo * 0.225 - 662.77;
        } else {
            return baseCalculo * 0.275 - 896.00;
        }
    }

    @Override
    public double calcularSalarioLiquido(Funcionario funcionario) {
        double inss = calcularINSS(funcionario.getSalarioBruto());
        double ir = calcularIR(funcionario.getSalarioBruto(), inss, funcionario.getDependentes().size());
        
        funcionario.setDescontoINSS(inss);
        funcionario.setDescontoIR(ir);
        
        return funcionario.getSalarioBruto() - inss - ir;
    }
}