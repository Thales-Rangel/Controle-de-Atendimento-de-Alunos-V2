package Models;

import javax.swing.JOptionPane;

public class Student extends User {
	
	int id_turma;
	private Turma turma;

	public Student(String nome, String matricula, String senha, int id_turma) {
		super(nome, matricula, senha);
		this.id_turma = id_turma;
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement("select * from turmas where id= ?");
			pst.setInt(1, id_turma);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				this.turma = new Turma(rs.getInt(1), rs.getString(2));
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}

	@Override
	public void setNome(String nome) {
		super.setNome(nome);

		String insert = "update alunos set nome= ? where matricula= ?";
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

		String insert = "update alunos set senha= ? where matricula= ?";
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, senha);
			pst.setString(2, this.matricula);

			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Senha atualizada com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possivel atualizar a senha!");
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public void setId_turma(int id_turma) {
		this.id_turma = id_turma;
		
		String insert = "update alunos set id_turma= ? where matricula= ?";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setInt(1, id_turma);
			pst.setString(2, this.matricula);
			
			int confirma = pst.executeUpdate();
			
			pst = con.prepareStatement("select * from turmas where id= ?");
			pst.setInt(1, id_turma);
			rs = pst.executeQuery();
			
			if (rs.next()) {
				this.turma = new Turma(rs.getInt(1), rs.getString(2));
			}
			
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Turma do aluno alterada com suscesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possivel alterar a turma do aluno");
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	public int getId_turma() {
		return id_turma;
	}
	
	
	public Turma getTurma() {
		return turma;
	}

}
