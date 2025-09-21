package resources;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

import br.com.model.FolhaPagamento;

public class CsvWriter {

	public static void escreverArquivo(String caminhoArquivo, List<FolhaPagamento> folhas) throws IOException {
		BufferedWriter bw = new BufferedWriter(new FileWriter(caminhoArquivo));

		for (FolhaPagamento folha : folhas) {
			String linha = String.format("%s;%s;%.2f;%.2f;%.2f", folha.getFuncionario().getNome(),
					folha.getFuncionario().getCpf(), folha.getDescontoINSS(), folha.getDescontoIR(),
					folha.getSalarioLiquido());
			bw.write(linha);
			bw.newLine();
		}

		bw.close();
	}
}
