package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Disciplane {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private int id;
	private String nome;

	public Disciplane(int id, String nome) {
		this.id = id;
		this.nome = nome;
	}

	public Disciplane(int id) {
		String read = "select * from disciplinas where id= '" + id + "'";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(read);
			rs = pst.executeQuery();

			if (rs.next()) {
				this.id = id;
				this.nome = rs.getString(2);
			}

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível achar a disciplina:\n" + e);
		}
	}

	public int getId() {
		return id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;

		String insert = "update disciplinas set nome= ? where id= ?";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, nome);
			pst.setInt(2, this.id);

			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Nome da disciplina atualizado com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possível atualizar o nome da disciplina!");
			}

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel atualizar o nome da disciplina:\n" + e);
		}
	}

}
