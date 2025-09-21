package br.com.service;

public class CalculoINSS implements Calculo {

    @Override
    public double calcular(double salarioBruto) {
        double desconto;

        if (salarioBruto <= 1320.00) {
            desconto = salarioBruto * 0.075;
        } else if (salarioBruto <= 2571.29) {
            desconto = salarioBruto * 0.09 - 19.80;
        } else if (salarioBruto <= 3856.94) {
            desconto = salarioBruto * 0.12 - 96.94;
        } else if (salarioBruto <= 7507.49) {
            desconto = salarioBruto * 0.14 - 174.08;
        } else {
            desconto = 7507.49 * 0.14; // teto
        }

        return desconto;
    }
}
