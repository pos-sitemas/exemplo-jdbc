package br.edu.unirn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Principal {

	public static void main(String[] args) throws ClassNotFoundException {
		Class.forName("org.postgresql.Driver"); //carregar o driver
		try {
			Connection connection = 
					DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/exemplo-jdbc",
							"exemplo-jdbc","exemplo-jdbc");
			connection.createStatement().executeQuery("select 1");
			Statement statement = connection.createStatement();
			String createAluno = "CREATE TABLE IF NOT EXISTS aluno("
					+ "id serial not null,"
					+ "nome VARCHAR(200) not null,"
					+ "matricula VARCHAR(20),"
					+ "PRIMARY KEY(id)"
					+ ")";
			statement.executeUpdate(createAluno);
			
			for(int i=0; i<100; i++){
				statement.executeUpdate("INSERT INTO aluno (nome,matricula) "
						+ " VALUES ('Romulo','2013B020411')");
			}
			
			
			ResultSet rs = statement.executeQuery("select * from aluno");
			while(rs.next()){
				int id = rs.getInt("id");
				String nome = rs.getString("nome");
				String matricula = rs.getString("matricula");
				System.out.println("["+id+"] " +nome + " - " + matricula);
			}
			
			PreparedStatement pst = connection
							.prepareStatement("select * from aluno where upper(nome) like ?");
			int pos = 0;
			pst.setString(++pos, "rom%".toUpperCase());
			
			ResultSet rsPst = pst.executeQuery();
			while(rsPst.next()){
				int id = rsPst.getInt("id");
				String nome = rsPst.getString("nome");
				String matricula = rsPst.getString("matricula");
				System.out.println("["+id+"] " +nome + " - " + matricula);
			}
			
			
			connection.close();
			//sysout
			
			System.out.println("Executado com sucesso!");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
