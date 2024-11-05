package alocSimple;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class Apartamento {

	public Integer numeroApartamento;

	Scanner sc = new Scanner(System.in);

	public Apartamento() {

	}

	public Apartamento(Integer numeroApartamento) {
		this.numeroApartamento = numeroApartamento;
	}

	public Integer getNumeroApartamento() {
		return this.numeroApartamento;
	}

	public static boolean checkApartamentoExiste(Integer apartamento) {
		if (apartamento > 809 || apartamento < 801) {
			System.out.print("Apartamento  inexistente.\n\n");
			return false;
		}
		return true;
	}
}
