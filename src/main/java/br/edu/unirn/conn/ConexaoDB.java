package br.edu.unirn.conn;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConexaoDB {

	private Connection con;

	private ConexaoDB() throws Exception {
		Class.forName("org.postgresql.Driver");
	}

	public Connection open() throws SQLException {
		try {
			if (this.con == null || this.con.isClosed()) {
				this.con = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/exemplo-jdbc", "exemplo-jdbc",
						"exemplo-jdbc");
			}
			return this.con;
		} catch (SQLException e) {
			throw e;
		}
	}

	public void close() throws SQLException {
		try {
			if (this.con != null && !this.con.isClosed())
				this.con.close();
		} catch (SQLException e) {
			throw e;
		}
	}

}
