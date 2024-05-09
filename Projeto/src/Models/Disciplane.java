package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

public class Disciplane {
	
	private Connection con;
	private PreparedStatement pst;

	private int id;
	private String nome;
	
	
	public Disciplane(int id, String nome) {
		this.id = id;
		this.nome = nome;
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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

}
