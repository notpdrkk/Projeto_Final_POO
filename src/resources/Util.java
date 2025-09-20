package resources;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Util {
	public static String informarString(String msg) {
		Scanner input = new Scanner(System.in);

		System.out.println(msg);
		String texto = input.nextLine();

		return texto;
	}

	public static char informarChar(String msg) {
		Scanner input = new Scanner(System.in);

		System.out.println(msg);
		char texto = input.nextLine().charAt(0);

		return texto;
	}

	public static int informarInt(String msg) {
		Scanner input = new Scanner(System.in);
		int numero = -1;

		System.out.println(msg);

		try {
			numero = input.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Valor inválido.");
		}

		return numero;
	}

	public static String informarPalavra(String msg) {
		Scanner input = new Scanner(System.in);

		System.out.println(msg);
		String texto = input.next();

		return texto;
	}

	public static double informarDouble(String msg) {
		Scanner input = new Scanner(System.in);
		double valor = 0;

		System.out.println(msg);

		try {
			valor = input.nextInt();
		} catch (InputMismatchException e) {
			System.out.println("Valor inválido.");
		}
		return valor;
	}
}
