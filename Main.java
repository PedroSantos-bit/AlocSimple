package alocSimple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
	public static void clear() {

		System.out.print("\033[H\033[2J");
		System.out.flush();
		System.out.print("\033[1;1H");
	}

	public static void main(String[] args) throws SQLException {

		clear();
		Scanner sc = new Scanner(System.in);
		String path = "C:\\temp\\in.txt";

		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			System.out.print("Senha do adm: "); // Senha do ADM para iniciar o sistema.
			String linha = br.readLine();
			String senha = sc.nextLine();

			int verificacao = senha.compareTo(linha);

			while (verificacao < 0 || verificacao > 0) { // Loop caso a senha não condiz
				System.out.println("Senha incorreta.");
				System.out.print("Gerando log do ocorrido..\n\n");
				System.out.print("Senha do adm: ");

				senha = sc.nextLine();
				verificacao = senha.compareTo(linha);
			}

		} catch (Exception e) { // Tratamento de algum erro generico
			System.out.println(e.getMessage());
		} finally {
			clear();
		}

		while (true) {
			try {
				System.out.print("ADICIONAR - [1]     REMOVER - [2]   " + "  CADASTROS - [3]   "
						+ "  CONFIGURAÇÕES - [4]   " + "  FINALIZAR - [5]   " + "  LIMPAR - [6]   ");

				Integer pretencao = sc.nextInt();
				Integer tempoDeAlocacao = 0;

				if (pretencao == 1) {
					clear();
					DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");

					Integer anoAtual = 0;
					System.out.println("						 	AlocSimple");
					System.out.printf("%n%nINÍCIO DO CONTRATO: "); // Entrando com a data de alocação do inquilino
					sc.nextLine();
					LocalDate dataDeAlocacao = LocalDate.parse(sc.nextLine().strip(), formato);
					anoAtual = Integer.parseInt(dataDeAlocacao.format(formato).substring(6, 10));

					// Enquanto a data não atender os criterios, irá repetir a entrada dos dados
					while (anoAtual > 2027 || anoAtual < 2024) {
						System.out.print(
								"\nData de entrada inválida. (A data deve estar" + " entre 01/01/2024 - 01/01/2027)\n");

						System.out.printf("%nINÍCIO DO CONTRATO: ");
						dataDeAlocacao = LocalDate.parse(sc.nextLine().strip(), formato);
						anoAtual = Integer.parseInt(dataDeAlocacao.format(formato).substring(6, 10));

						continue;
					}
					clear();

					System.out.printf("%n%n						 ------| DADOS INQUILINO |------%n%n");

					String dataNascimentoChar;
					String nome = null;
					String rg = null;
					String status = null;
					int idade = 0;

					try {

						System.out.print("NOME COMPLETO: "); // Entrando com os dados do inquilino
						nome = sc.nextLine();

						System.out.print("DATA DE NASCIMENTO: ");
						dataNascimentoChar = sc.nextLine().strip();

						Integer anoNascimento = Integer.parseInt(dataNascimentoChar.substring(6).strip());
						idade = anoAtual - anoNascimento;

						while (idade > 110 || idade < 17) { // Inquilino deve possuir, no mínimo, 17 anos.
							System.out.print("Idade inválida.\n\n");
							System.out.print("DATA DE NASCIMENTO: "); // Recebendo a idade do usuario
							dataNascimentoChar = sc.nextLine().strip();
							anoNascimento = Integer.parseInt(dataNascimentoChar.substring(6).strip());
							idade = anoAtual - anoNascimento;
							continue;
						}

						System.out.print("RG: "); // Recebendo o RG do usuario
						rg = sc.nextLine().strip().replace(".", "");

						if (rg.length() < 7) {
							System.out
									.printf("%nRG com poucos Dígitos numéricos, recomendado a checkagem no final.%n%n");
						}

						System.out.print("\nCASADO(A)? (s/n): "); // Recebendo o status do usuario
						char respostaCasado = sc.next().strip().toLowerCase().charAt(0);
						System.out.println();
						if (respostaCasado == 's') {
							status = "CASADO";
						} else if (respostaCasado == 'n') {
							status = "NÃO CASADO(A)";
						}

					} catch (InputMismatchException erro) {
						System.out.println("Seu nome deve ser em caracteres.");
						continue;
					}

					System.out.printf("						 ----- ESCOLHA DO APARTAMENTO -----%n%n");
					System.out.print("Número do apartamento (801 - 809): ");
					Integer numeroApartamento = sc.nextInt(); // escolha do apartamento

					while (numeroApartamento < 801 || numeroApartamento > 809) {
						System.out.printf("Apartamento inexistente.\n\n");
						System.out.print("Número do apartamento (801 - 809): ");
						numeroApartamento = sc.nextInt();
						continue;
					}

					System.out.print("\n\n");

					System.out.println("6 Meses - [1]    12 Meses - [2] ");
					System.out.print("    Tempo de alocação: ");
					tempoDeAlocacao = sc.nextInt();
					System.out.print("\n\n");

					if (tempoDeAlocacao != 1 && tempoDeAlocacao != 2) {
						System.out.println("Entrada inválida");
						continue;
					}

					Pessoa pessoa = new Pessoa(nome, idade, rg, status);// Criando o objeto pessoa.

					pessoa.adicionarAoApartamento(new Apartamento(numeroApartamento));// associando o inquilino ao
																						// apartamento
					pessoa.fazerContrato(new Contrato(dataDeAlocacao, tempoDeAlocacao)); // Basicamente atrbibuindo a
					clear(); // validade do contrato
					System.out.println(pessoa);
					System.out.print("					Salvar - [1]     Cancelar - [2]");

					Integer respostaSalvamento = sc.nextInt();

					if (respostaSalvamento == 1) {
						jdbcBD.addInBD(pessoa); // Salvamento dos dados..
					} else if (respostaSalvamento == 2) {
						System.out.print("\n\nSalvamento cancelado\n\n");
					} else if (respostaSalvamento == 3) {
						pretencao = 1;
						continue;
					}
					sc.nextLine();

				} else if (pretencao == 5) { // Finalizar programa
					System.out.print("PROGRAMA FINALIZADO");
					System.exit(0);
				} else if (pretencao == 3) {
					System.out.print("\n\n\n");
					jdbcBD.viewDatas();
				} else if (pretencao == 6) {
					clear();
				} else if (pretencao == 4) { // Finalizar programa
					System.out.print("\n\n\nSystem version - 2.19.2.\n");
					System.out.print("Number and email of Developer: 31 97313-5341 / f.santosPedro225@gmail.com\n\n\n");
					continue;
				} else if (pretencao == 2) { // Fechando o "if" de adicao do inquilino[1].

					System.out.println("\n\n");
					System.out.print("INFORME O APARTAMENTO QUE DESEJA DESALOCAR: ");
					Integer pretencaoDesalocarApartamento = sc.nextInt();

					boolean teste = Apartamento.checkApartamentoExiste(pretencaoDesalocarApartamento);

					if (!teste) {// caso o número escolhido não esteja entre o range (801-809), vai retornar ao
									// fluxo default
						continue;
					}
					clear();
					System.out.printf("\n\n\n						    !! ATENÇÃO !!\n\n\n");
					System.out.printf(
							"			Desalocar o apartamento %d" + " irá DELETAR os dados do(a) inquilino(a):%n%n%n",
							pretencaoDesalocarApartamento);

					jdbcBD.viewDatas(pretencaoDesalocarApartamento);

					System.out.print("		  \n\n\nCANCELAR - [1]    CONFIRMAR - [2]:  ");
					Integer resposta = sc.nextInt();

					if (resposta == 2) {
						jdbcBD.removeData(pretencaoDesalocarApartamento);
					} else if (resposta == 1) {
						System.out.print("\n\nProcedimento cancelado\n\n");
						continue;
					}

				} 
			} catch (DateTimeParseException e) {
				System.out.println("Formato da data inválida.");
			} catch (StringIndexOutOfBoundsException e) {
				System.out.println("Houve algum erro na entrada de dados, checke os dados e tente novamente.");
			} catch (InputMismatchException e) {
				System.out.println("Finalizando programa.");
				System.exit(0);
			} catch (NumberFormatException e) {
				System.out.println("Não pode haver letras na data nascimento!");
			} finally {
				System.out.println();
			}
		}

	}
}
