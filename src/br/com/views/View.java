package br.com.views;

import java.util.Scanner;

public class View {

    private Scanner scanner = new Scanner(System.in);
    
    public String pedirCaminhoEntrada() {
        System.out.print("Informe o caminho do arquivo de entrada: ");
        return scanner.nextLine();
    }

    public String pedirCaminhoSaida() {
        System.out.print("Informe o caminho do arquivo de saída: ");
        return scanner.nextLine();
    }

    public void exibirMensagem(String mensagem) {
        System.out.println(mensagem);
    }
}
