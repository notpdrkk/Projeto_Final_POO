package br.com.empresa.folhapagamento.service;

public class CalculoIR implements Calculo {

    @Override
    public double calcular(double baseCalculo) {
        if (baseCalculo <= 2259.00) {
            return 0.0;
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
}
