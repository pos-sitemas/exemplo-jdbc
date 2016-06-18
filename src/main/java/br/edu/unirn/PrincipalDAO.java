package br.edu.unirn;

import java.util.ArrayList;
import java.util.Date;

import br.edu.unirn.dao.AlunoDAO;
import br.edu.unirn.model.Aluno;

public class PrincipalDAO {

	
	public static void main(String[] args) throws Exception{
		AlunoDAO dao = new AlunoDAO();
		ArrayList<Aluno> lista = dao.findAll();
		
		for (Aluno aluno : lista) {
			System.out.println(aluno.getNome());
		}
		
		System.out.println(new Date().getTime());
	}
}
