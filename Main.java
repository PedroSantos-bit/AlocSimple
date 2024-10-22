package alocSimple;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Locale;
import java.util.Scanner;




public class Main {
	
	public static void clear() {
		for(int i=0;i<=50;i++) {
			System.out.println();	
		}
	}

	public static void main(String[] args) throws SQLException{
		
		Scanner sc = new Scanner(System.in);
		String path = "C:\\temp\\in.txt";
		
		try (BufferedReader br = new BufferedReader(new FileReader(path))){
			
			System.out.print("Senha do adm: "); //A ideia é pegar a senha inicial para começar a usar o sistema.
			String linha = br.readLine();
			String senha =  sc.nextLine();
		
			int verificacao = senha.compareTo(linha);
			
			while (verificacao < 0 || verificacao > 0) {
				System.out.println("Senha incorreta.");
				System.out.println("Gerando log do ocorrido.."); System.out.println();
				System.out.print("Senha do adm: "); //A ideia é pegar a senha inicial para começar a usar o sistema.
				senha =  sc.nextLine();
				verificacao = senha.compareTo(linha);
			}
			
		}catch(Exception e) {
			System.out.println(e.getMessage());
		}
		
		Integer pretencao;
		while (true){ 	
			try {			
				System.out.print("ADICIONAR - [1]     REMOVER - [2]     CADASTROS - [3]     CONFIGURAÇÕES - [4]     FINALIZAR - [5]");
				
				pretencao = sc.nextInt();
				Integer tempoDeAlocacao = 0;
				
				if(pretencao == 1) {
					clear();
					DateTimeFormatter formato = DateTimeFormatter.ofPattern("dd/MM/yyyy");
					
					
					Integer anoAtual = 0;
					
					// Entrando com a data de alocação do inquilino
					System.out.printf("%n%nDATA DA ENTRADA: ");
					sc.nextLine();
					LocalDate dataDeAlocacao = LocalDate.parse(sc.nextLine().strip(),formato);
					anoAtual = Integer.parseInt(dataDeAlocacao.format(formato).substring(6,10));
				
					//enquanto a data não atender os criterios, irá repetir a entrada dos dados
					while(anoAtual > 2027 || anoAtual < 2024) { 
						System.out.println("Data de entrada inválida! As datas devem estar entre: ");
						System.out.println("01/01/2024 - 01/01/2027");
						
						System.out.printf("%nEntre com a data de entrada entre 2024 - 2027: ");
						dataDeAlocacao = LocalDate.parse(sc.nextLine().strip(),formato);
						anoAtual = Integer.parseInt(dataDeAlocacao.format(formato).substring(6,10));
						
						continue;
					}
					//Entrando com os dados do inquilino
					System.out.printf("%n%n------- DADOS INQUILINO -------%n%n");
					
					String dataNascimentoChar;
					String nome = null;
					String rg=null;
					String status=null;
					int idade=0;
					
					try {
						System.out.print("NOME COMPLETO: "); //Recebendo nome do usuario
						nome = sc.nextLine();
						
						System.out.print("DATA DE NASCIMENTO: "); //Recebendo a idade do usuario
						dataNascimentoChar = sc.nextLine().strip();
						
						Integer anoNascimento = Integer.parseInt(dataNascimentoChar.substring(6).strip());
						idade = anoAtual - anoNascimento;

						while(idade > 110 || idade < 17) { //Inquilino deve possuir, no mínimo, 17 anos.
							System.out.println("Idade inválida.");
							System.out.print("DATA DE NASCIMENTO: "); //Recebendo a idade do usuario
							dataNascimentoChar = sc.nextLine().strip();
							anoNascimento = Integer.parseInt(dataNascimentoChar.substring(6).strip());
							idade = anoAtual - anoNascimento;
							continue;
						}
						
						System.out.print("RG: "); //Recebendo o RG do usuario
						rg = sc.nextLine().strip().replace(".", ""); 
						
						if(rg.length() < 71){
							System.out.println("RG com poucos Dígitos numéricos, recomendado a checkagem no final.");
						}
						
						System.out.print("CASADO(a)? (s/n): "); //Recebendo o status do usuario
						char respostaCasado = sc.next().strip().toLowerCase().charAt(0);
						
						if(respostaCasado =='s') {
							status = "CASADO";
						}else if(respostaCasado =='n'){
							status = "NÃO CASADO";
						}
						
					
					}
					catch(InputMismatchException erro)
					{
						System.out.println("Seu nome deve ser em caracteres.");		
						continue;
					}
					
					clear();
					System.out.printf("----- ESCOLHA DO APARTAMENTO -----%n%n");
					System.out.print("Número do apartamento (801 - 809): ");
					Integer numeroApartamento = sc.nextInt(); //escolha do apartamento
					
					while(numeroApartamento < 801 || numeroApartamento > 809) {
						System.out.printf("Apartamento inexistente.\n\n");
						System.out.print("Número do apartamento (801 - 809): ");
						numeroApartamento = sc.nextInt();
						continue;
					}
					
					System.out.println();
					
					System.out.println("Tempo de alocação: ");
					System.out.println("6 Meses - [1]    12 Meses - [2] ");
					tempoDeAlocacao = sc.nextInt();
					
					if(tempoDeAlocacao != 1 && tempoDeAlocacao != 2) {
						System.out.println("Entrada inválida.");
						continue;
					}
					
					Pessoa pessoa = new Pessoa(nome, idade,rg,status);//Criando o objeto pessoa.
				
					pessoa.adicionarAoApartamento(new Apartamento(numeroApartamento));//associando o inquilino ao apartamento 
					pessoa.fazerContrato(new Contrato(dataDeAlocacao,tempoDeAlocacao));		//Basicamente atrbibuindo a validade do contrato
					
					System.out.println(pessoa);
					System.out.printf("	Salvar - [1]     Cancelar - [2]%n%n"); //Informando se deseja salvar o inquilino
					Integer respostaSalvamento = sc.nextInt();
					
					
					if(respostaSalvamento == 1) {
						jdbcBD.addInBD(pessoa);	//Salvamento dos dados..
					}else if(respostaSalvamento == 2) {
						System.out.println("Salvamento cancelado!");
					}
						
					sc.nextLine();
					
				}
				else if(pretencao == 5){ //Finalizar programa
					System.out.print("PROGRAMA FINALIZADO.");
					System.exit(0);
				}else if(pretencao == 3){ 
					System.out.println("\n\n");
					clear();
					jdbcBD.viewDatas();
				}
				else if(pretencao == 4){ //Finalizar programa
					clear();
					System.out.println("System version - 2.19.2.");
					System.out.println("Number and email of Developer: 31 97313-5341 / f.santosPedro225@gmail.com");
					continue;
				}
				else if(pretencao == 2){ //Fechando o "if" de adicao do inquilino[1].
				
					System.out.println("\n\n");
					System.out.print("INFORME O APARTAMENTO QUE DESEJA DESALOCAR: ");
					Integer pretencaoDesalocarApartamento = sc.nextInt();
					
					boolean teste = Apartamento.checkApartamentoExiste(pretencaoDesalocarApartamento);
					
					if(!teste) {//caso o número escolhido não esteja entre o range (801-809), vai retornar ao fluxo default
						continue;
					}
		
					
					System.out.printf("\n\n** ATENÇÃO **\n\n");
					System.out.printf("Desalocar o apartamento referido (%d)"
							+ " exluirá os dados do(a) atual inquilino(a):%n",pretencaoDesalocarApartamento);
					
					jdbcBD.viewDatas(pretencaoDesalocarApartamento);
					
					System.out.print("CANCELAR - [1]    CONFIRMAR - [2]:  ");
					Integer resposta = sc.nextInt();
							
					if (resposta == 2) {
						jdbcBD.removeData(pretencaoDesalocarApartamento);
					}else if(resposta == 1) {
						System.out.println("Procedimento cancelado!");
						continue;
					}
					
					
				}
					}catch(DateTimeParseException e) {
						System.out.println("Formato da data inválida.");
					}catch (StringIndexOutOfBoundsException e) {
						System.out.println("Houve algum erro na entrada de dados, checke os dados e tente novamente.");
					}catch(InputMismatchException e) {
						System.out.println("Finalizando programa.");	
						System.exit(0);
					}catch(NumberFormatException e) {
						System.out.println("Não pode haver letras na data nascimento!");
					}
					finally {
						System.out.println();
					}
			}
		

		}
	}
	
   	
