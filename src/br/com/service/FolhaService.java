package br.com.service;

import java.time.LocalDate;

import br.com.model.FolhaPagamento;
import br.com.model.Funcionario;

public class FolhaService {

    private static final double DEDUCAO_DEPENDENTE = 189.59;

    public FolhaPagamento gerarFolha(Funcionario funcionario, int codigo) {
        double salarioBruto = funcionario.getSalarioBruto();

        // Estratégias de cálculo via interface
        Calculo calcINSS = new CalculoINSS();
        Calculo calcIR = new CalculoIR();

        // 1. Calcular INSS
        double descontoINSS = calcINSS.calcular(salarioBruto);

        // 2. Base de cálculo do IR
        double baseIR = salarioBruto - descontoINSS - (funcionario.getDependentes().size() * DEDUCAO_DEPENDENTE);
        if (baseIR < 0) baseIR = 0;

        // 3. Calcular IR
        double descontoIR = calcIR.calcular(baseIR);
        if (descontoIR < 0) descontoIR = 0;

        // 4. Salário líquido
        double salarioLiquido = salarioBruto - descontoINSS - descontoIR;

        // Criar folha
        FolhaPagamento folha = new FolhaPagamento(codigo, funcionario, LocalDate.now());
        folha.setDescontoINSS(descontoINSS);
        folha.setDescontoIR(descontoIR);
        folha.setSalarioLiquido(salarioLiquido);

        return folha;
    }
}
