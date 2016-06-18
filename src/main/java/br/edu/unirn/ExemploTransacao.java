package br.edu.unirn;

import java.sql.Connection;
import java.sql.DriverManager;

public class ExemploTransacao {
	public static void main(String[] args) throws Exception{
		Class.forName("org.postgresql.Driver"); // carregar o driver
		try {
			Connection connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/exemplo-jdbc",
					"exemplo-jdbc", "exemplo-jdbc");
			connection.setAutoCommit(false);
			for(int i=0; i<1000; i++){
				connection.createStatement().executeUpdate("INSERT INTO aluno (nome,matricula) "
						+ " VALUES ('Romulo','2013B020411')");
			}
			connection.rollback();
			connection.close();
		} catch (Exception e) {

		}
	}
}
