package Models;

import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Professor extends User {
	
	private ArrayList<Disciplane> disciplinas = new ArrayList<>();

	public Professor(String nome, String matricula, String senha) {
		super(nome, matricula, senha);
		
		String insert = "select d.id, d.nome from ensina en "
				+ "inner join disciplinas d "
				+ "on d.id = en.id_disciplina "
				+ "where en.matricula_professor= ?";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, matricula);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				disciplinas.add(new Disciplane(rs.getInt(1), rs.getString(2)));
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public Professor(String matricula) {
		super(matricula);
		
		String insert = "select d.id, d.nome from ensina en "
				+ "inner join disciplinas d "
				+ "on d.id = en.id_disciplina "
				+ "where en.matricula_professor= ?";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, matricula);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				disciplinas.add(new Disciplane(rs.getInt(1), rs.getString(2)));
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@Override
	public void setNome(String nome) {
		super.setNome(nome);

		String insert = "update professores set nome= ? where matricula= ?";
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, nome);
			pst.setString(2, this.matricula);

			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Nome atualizado com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possivel atualizar o nome!");
			}

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@Override
	public void setSenha(String senha) {
		super.setSenha(senha);

		String insert = "update professores set senha= ? where matricula= ?";
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, senha);
			pst.setString(2, this.matricula);

			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Senha atualizada com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possível atualizar a senha!");
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	
	public ArrayList<Disciplane> getDisciplinas() {
		return disciplinas;
	}
	
	public void atualizaDisciplinas() {
		disciplinas = new ArrayList<Disciplane>();
		
		String insert = "select d.id, d.nome from ensina en "
				+ "join disciplinas d "
				+ "on d.id = en.id_disciplina "
				+ "where en.matricula_professor= ? "
				+ "order by d.nome";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, this.matricula);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				disciplinas.add(new Disciplane(rs.getInt(1), rs.getString(2)));
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
