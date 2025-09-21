package resources;

import br.com.exception.DependenteException;
import br.com.model.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CsvReader {

	public static List<Funcionario> lerArquivo(String caminhoArquivo) throws IOException, DependenteException {
		List<Funcionario> funcionarios = new ArrayList<>();
		BufferedReader br = new BufferedReader(new FileReader(caminhoArquivo));

		String linha;
		Funcionario funcionarioAtual = null;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

		while ((linha = br.readLine()) != null) {
			if (linha.trim().isEmpty()) {
				funcionarioAtual = null;
				continue;
			}

			String[] campos = linha.split(";");

			if (funcionarioAtual == null) {
				String nome = campos[0];
				String cpf = campos[1];
				LocalDate nascimento = LocalDate.parse(campos[2], formatter);
				double salarioBruto = Double.parseDouble(campos[3]);

				funcionarioAtual = new Funcionario(nome, cpf, nascimento, salarioBruto);

				boolean cpfDuplicado = funcionarios.stream().anyMatch(f -> f.getCpf().equals(cpf));
				if (cpfDuplicado) {
					throw new DependenteException("CPF duplicado detectado para funcionário: " + cpf);
				}

				funcionarios.add(funcionarioAtual);
			} else {
				String nome = campos[0];
				String cpf = campos[1];
				LocalDate nascimento = LocalDate.parse(campos[2], formatter);
				Parentesco parentesco = Parentesco.valueOf(campos[3]);

				if (!nascimento.isAfter(LocalDate.now().minusYears(18))) {
					throw new DependenteException("Dependente maior de 18 anos: " + nome);
				}

				boolean cpfDuplicado = funcionarios.stream().anyMatch(f -> f.getCpf().equals(cpf))
						|| funcionarioAtual.getDependentes().stream().anyMatch(d -> d.getCpf().equals(cpf));
				if (cpfDuplicado) {
					throw new DependenteException("CPF duplicado em dependentes: " + cpf);
				}

				Dependente dependente = new Dependente(nome, cpf, nascimento, parentesco);
				funcionarioAtual.adicionarDependente(dependente);
			}
		}

		br.close();
		return funcionarios;
	}
}
