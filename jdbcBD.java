package alocSimple;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class jdbcBD {
	
	Scanner sc = new Scanner(System.in);
	
	public static void addInBD(Pessoa pessoa) throws SQLException { //adicionando uma pessoa no bd
	
		PreparedStatement comando = null;
		Connection conexao = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection("jdbc:mysql://localhost/simplealoc","developer","1234567");
			
			comando = conexao.prepareStatement("insert into pessoa"
					+ "(nome_inquilino, idade_inquilino, rg_inquilino, status_inquilino,"
					+ "data_alocacao,vencimento_contrato,apartamento)"
					+ "values(?,?,?,?,?,?,?)");
			
			comando.setString(1, pessoa.getNome());
			comando.setInt(2, pessoa.getIdade());
			comando.setString(3,pessoa.getRg());
			comando.setString(4,pessoa.getStatus());
			comando.setString(5, pessoa.diaDaAlocacao());
			comando.setString(6,pessoa.getVencimento());
			comando.setInt(7,pessoa.visualizarApartamento());
			
			int linhas = comando.executeUpdate();
	
			String arquivoPrototipo = "C:\\temp\\dadosParaContrato.txt";
			
			try (BufferedWriter bw = new BufferedWriter(new FileWriter(arquivoPrototipo))){
				bw.write("MG / Belo Horizonte / Nova Lima\n\n");
				bw.write("Jardim Canada 2, Rua Niágara 1317, Apartamentos\n\n");
				bw.write("Número do Apartamento:  " + pessoa.visualizarApartamento() + "\n\n");
				bw.write("Valor - 1200.00 R$\n\n");
				bw.write("Tempo de Contrato - " + pessoa.getFormatoContrato() + "\n\n"); // aqui a dúvida.
				bw.write("1° Morador: \n\n");
				bw.write(pessoa.getNome() + "\n\n");
				bw.write("Idade atual: " + pessoa.getIdade() + "\n\n");
				bw.write("Serial RG - " + pessoa.getRg() + "\n\n");
				bw.write(pessoa.getStatus()+"(A)"); 			
				System.out.println("Protótipo do contrato criado.");
				
			}catch(IOException e) {
				System.out.println(e.getMessage());
			}
			
			
			
			
			
			
			
			
		
		}
		catch(ClassNotFoundException e) 
		{
			System.out.print("Erro com driver do BD: " + e.getMessage());
		}
		catch(SQLException e) 
		{
			System.out.print("Não foi possível salvar, o apartamento escolhido consta alocação. \n\n");
		}
		finally
		{
		if(conexao != null) {
		   conexao.close();
		}
			
	   }
			
	}
	
	public static void removeData(Integer numero) throws SQLException{
		Connection conexao = null;
		PreparedStatement comando = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection("jdbc:mysql://localhost/simplealoc","developer","1234567");
			
			comando = conexao.prepareStatement("delete from pessoa where apartamento = ?");
			
			comando.setInt(1,numero);
			System.out.println("Apartamento " + numero + " liberado" );
			comando.executeUpdate();
			
		}catch(ClassNotFoundException e) {
			System.out.print("Erro no driver, metodo de remoção: " + e.getMessage());
		}catch (SQLException e) {
			System.out.print("Erro na conexão com o banco, metodo de remoção: "+e.getMessage());
		}
		finally {
			if (conexao != null) {
				conexao.close();
			}
		}	
	}
	
	
	public static void viewDatas(Integer apartamento) throws SQLException{
		
		PreparedStatement comando = null;
		ResultSet resultado = null;
		Connection conexao = null;
		
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection("jdbc:mysql://localhost/simplealoc","developer","1234567");
			
			switch (apartamento){
				case (801): {
					resultado = conexao.createStatement().executeQuery("SELECT * from pessoa where apartamento = 801");
					break;
				}
				case(802):{
					resultado = conexao.createStatement().executeQuery("SELECT * from pessoa where apartamento = 802");
					break;
				}
				case(803):{
					resultado = conexao.createStatement().executeQuery("SELECT * from pessoa where apartamento = 803");
					break;
				}
				case(804):{
					resultado = conexao.createStatement().executeQuery("SELECT * from pessoa where apartamento = 804");
					break;
				}
				case(805):{
					resultado = conexao.createStatement().executeQuery("SELECT * from pessoa where apartamento = 805");
					break;
				}
				case(806):{
					resultado = conexao.createStatement().executeQuery("SELECT * from pessoa where apartamento = 806");
					break;
				}
				case(807):{
					resultado = conexao.createStatement().executeQuery("SELECT * from pessoa where apartamento = 807");
					break;
				}
				case(808):{
					resultado = conexao.createStatement().executeQuery("SELECT * from pessoa where apartamento = 808");
					break;
				}
				case(809):{
					resultado = conexao.createStatement().executeQuery("SELECT * from pessoa where apartamento = 809");
					break;
				}
				default: {
					System.out.println("Comando inválido.");
				}
			}
			
			 	
			while(resultado.next()) {
				System.out.print(resultado.getInt("apartamento") + ": ");
				System.out.print(resultado.getString("nome_inquilino") + " | ");
				System.out.print(resultado.getInt("idade_inquilino") + "(y)" + " | ");
				System.out.print(resultado.getString("rg_inquilino" ) + " | ");
				System.out.print(resultado.getString("status_inquilino")+ " | ");
				System.out.print(resultado.getString("rg_inquilino" )+ " | ");
				System.out.print(resultado.getString("data_alocacao" )+ " - ");
				System.out.print(resultado.getString("vencimento_contrato" ));
				System.out.print("\n\n");
			}
			
		}catch(ClassNotFoundException e) {
			System.out.println("Erro aqui");
			System.out.print(e.getMessage());
		}catch(SQLException e) {
			System.out.println("Erro na conexao com o banco de dados: " + e.getMessage());
		}finally {
			if(conexao != null) {
				conexao.close();
			}
		}
	}
	
	
	public static void viewDatas() throws SQLException{
		ResultSet resultado = null;
		Connection conexao = null;
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			conexao = DriverManager.getConnection("jdbc:mysql://localhost/simplealoc","developer","1234567");
			
			resultado = conexao.createStatement().executeQuery("SELECT * FROM pessoa");
			
			while(resultado.next()) {
				System.out.print(resultado.getInt("apartamento") + ": ");
				System.out.print(resultado.getString("nome_inquilino") + " | ");
				System.out.print(resultado.getInt("idade_inquilino") + "(y)" + " | ");
				System.out.print(resultado.getString("rg_inquilino" ) + " | ");
				System.out.print(resultado.getString("status_inquilino")+ " | ");
				System.out.print(resultado.getString("rg_inquilino" )+ " | ");
				System.out.print(resultado.getString("data_alocacao" )+ " - ");
				System.out.print(resultado.getString("vencimento_contrato" ));
				System.out.print("\n\n");
			}
			
		}catch(ClassNotFoundException e) {
			System.out.print(e.getMessage());
		}catch(SQLException e) {
			System.out.println("Erro na conexao com o banco de dados: " + e.getMessage());
		}finally {
			if(conexao != null) {
				conexao.close();
				System.out.print("\n\n");
			}
		}
		
		
	}
				
}

