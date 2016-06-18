package br.edu.unirn.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import br.edu.unirn.model.Aluno;

public class AlunoDAO {

	private Connection connection;

	public AlunoDAO() throws Exception {
		Class.forName("org.postgresql.Driver");
		connection = DriverManager.getConnection("jdbc:postgresql://127.0.0.1:5432/exemplo-jdbc", "exemplo-jdbc",
				"exemplo-jdbc");
		Statement statement = connection.createStatement();
		String createAluno = "CREATE TABLE IF NOT EXISTS aluno(" + 
					"id serial not null," + 
					"nome VARCHAR(200) not null," + 
					"matricula VARCHAR(20)," +
					"data_cadastro timestamp with time zone DEFAULT now()," + 
					"PRIMARY KEY(id)" + ")";
		statement.executeUpdate(createAluno);
		statement.close();
	}

	public ArrayList<Aluno> findAll() throws SQLException {
		ArrayList<Aluno> retorno = new ArrayList<Aluno>();
		Statement statement = connection.createStatement();
		ResultSet rs = statement.executeQuery("select * from aluno");
		while (rs.next()) {
			Aluno a = new Aluno();
			a.setId(rs.getInt("id"));
			a.setNome(rs.getString("nome"));
			a.setMatricula(rs.getString("matricula"));
			a.setDataCadastro(rs.getDate("data_cadastro")); //java.sql.Date -> java.util.Date
			retorno.add(a);
		}
		rs.close();
		statement.close();
		return retorno;
	}

	public void inserir(Aluno aluno) throws Exception {
		PreparedStatement pst = connection.prepareStatement("INSERT INTO aluno (nome,matricula,data_cadastro) " + " VALUES (?,?,?)");
		pst.setString(1, aluno.getNome());
		pst.setString(2, aluno.getMatricula());
		pst.setDate(3, new java.sql.Date(aluno.getDataCadastro().getTime()));
		pst.executeUpdate();
		pst.close();
	}

	public void alterar(Aluno aluno) throws Exception {
		PreparedStatement pst = connection.prepareStatement("UPDATE aluno SET nome=?,matricula=?,data_cadastro=? WHERE id=?");
		pst.setString(1, aluno.getNome());
		pst.setString(2, aluno.getMatricula());
		pst.setDate(3, new java.sql.Date(aluno.getDataCadastro().getTime()));
		pst.setInt(4, aluno.getId());
		pst.executeUpdate();
		pst.close();
	}

	public Aluno findById(int id) throws Exception {
		Aluno aluno = null;
		PreparedStatement pst = connection.prepareStatement("select * from aluno where id=?");
		pst.setInt(1, id);
		ResultSet rs = pst.executeQuery();
		if (rs.next()) {
			aluno = new Aluno();
			aluno.setId(rs.getInt("id"));
			aluno.setNome(rs.getString("nome"));
			aluno.setMatricula(rs.getString("matricula"));
			aluno.setDataCadastro(rs.getDate("data_cadastro")); //java.sql.Date -> java.util.Date
		}
		rs.close();
		pst.close();
		return aluno;
	}

	public void remover(Aluno aluno) throws Exception {
		PreparedStatement pst = connection.prepareStatement("DELETE FROM aluno WHERE id=?");
		pst.setInt(1, aluno.getId());
		pst.executeUpdate();
		pst.close();

	}

	public ArrayList<Aluno> findAllByNome(String nome) throws Exception {
		ArrayList<Aluno> retorno = new ArrayList<Aluno>();
		PreparedStatement pst = connection.prepareStatement("select * from aluno where upper(nome) like ?");
		int pos = 0;
		pst.setString(++pos, nome + "%".toUpperCase());
		ResultSet rs = pst.executeQuery();
		while (rs.next()) {
			Aluno a = new Aluno();
			a.setId(rs.getInt("id"));
			a.setNome(rs.getString("nome"));
			a.setMatricula(rs.getString("matricula"));
			a.setDataCadastro(rs.getDate("data_cadastro")); //java.sql.Date -> java.util.Date
			retorno.add(a);
		}
		rs.close();
		pst.close();
		return retorno;
	}
}
