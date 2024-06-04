package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

import javax.swing.JOptionPane;

public class Turma {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private int id;
	private String nome;

	private ArrayList<Disciplane> disciplinas = new ArrayList<>();

	public Turma(int id, String nome) {
		this.id = id;
		this.nome = nome;

		String insert = "select d.* from estuda es join disciplinas d on d.id = es.id_disciplina "
				+ "where es.id_turma= ? order by d.nome";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setInt(1, id);
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

	public Turma(int id) {
		String select = "select * from turmas where id= '" + id + "'";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(select);
			rs = pst.executeQuery();
			if (rs.next()) {
				this.id = rs.getInt(1);
				this.nome = rs.getString(2);

				String selectD = "select d.* from estuda es join disciplinas d on d.id = es.id_disciplina "
						+ "where es.id_turma= '" + id + "' order by d.nome";

				pst = con.prepareStatement(selectD);
				rs = pst.executeQuery();
				while (rs.next())
					disciplinas.add(new Disciplane(rs.getInt(1), rs.getString(2)));

			} else {
				JOptionPane.showMessageDialog(null, "Turma não encontrada!");
			}

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi posseivel localizar a turma:\n" + e);
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

		String insert = "update turmas set nome= ? where id= ?";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, nome);
			pst.setInt(2, this.id);

			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Nome da turma atualizado com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possivel atualizar o nome da turma!!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public ArrayList<Disciplane> getDisciplinas() {
		return disciplinas;
	}

	public void atualizaDisciplinas() {
		disciplinas = new ArrayList<Disciplane>();

		String insert = "select d.* from estuda es join disciplinas d on d.id = es.id_disciplina "
				+ "where es.id_turma= ? order by d.nome";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setInt(1, id);
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
