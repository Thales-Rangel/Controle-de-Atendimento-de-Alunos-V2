package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public abstract class User {

	protected Connection con;
	protected PreparedStatement pst;
	protected ResultSet rs;

	protected String nome;
	protected String matricula;
	protected String senha;

	public User(String nome, String matricula, String senha) {
		this.nome = nome;
		this.matricula = matricula;
		this.senha = senha;
	}

	public User(String matricula) {
		try {
			con = DAO.conectar();

			if (this.getClass() == Professor.class) {
				String read = "select * from professores where matricula= '" + matricula + "'";

				pst = con.prepareStatement(read);
				rs = pst.executeQuery();
				if (rs.next()) {
					this.nome = rs.getString(1);
					this.matricula = matricula;
					this.senha = rs.getString(3);
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possivel localizar o professor");
				}
			} else if (this.getClass() == Student.class) {
				String read = "select  * from alunos where matricula= '" + matricula + "'";

				pst = con.prepareStatement(read);
				rs = pst.executeQuery();
				if (rs.next()) {
					this.nome = rs.getString(1);
					this.matricula = matricula;
					this.senha = rs.getString(3);
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possivel localizar o estudante");
				}
			}

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar o usúario:\n" + e);
		}
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getNome() {
		return nome;
	}

	public String getMatricula() {
		return matricula;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getSenha() {
		return senha;
	}

}
