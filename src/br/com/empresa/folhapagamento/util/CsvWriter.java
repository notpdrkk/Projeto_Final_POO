package br.com.empresa.folhapagamento.util;

import br.com.empresa.folhapagamento.model.FolhaPagamento;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CsvWriter {

    public static void escreverArquivo(String caminhoArquivo, List<FolhaPagamento> folhas) throws IOException {
        BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));

        for (FolhaPagamento folha : folhas) {
            String linha = String.format("%s;%s;%.2f;%.2f;%.2f",
                    folha.getFuncionario().getNome(),
                    folha.getFuncionario().getCpf(),
                    folha.getDescontoINSS(),
                    folha.getDescontoIR(),
                    folha.getSalarioLiquido()
            );
            bw.write(linha);
            bw.newLine();
        }

        bw.close();
    }
}
