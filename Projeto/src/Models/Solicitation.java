package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;

import javax.swing.JOptionPane;

public class Solicitation {
	
	private Connection con;
	private PreparedStatement pst;
	
	private int id;
	private Student aluno;
	private Professor professor;
	private String duvida;
	private String resposta;
	private boolean respondido;
	
	public Solicitation(int id, Student aluno, Professor professor, String duvida) {
		this.id = id;
		this.aluno = aluno;
		this.professor = professor;
		this.duvida = duvida;
		
		this.respondido = false;
	}
	
	public int getId() {
		return id;
	}
	
	public Student getAluno() {
		return aluno;
	}
	
	public Professor getProfessor() {
		return professor;
	}
	
	public String getDuvida() {
		return duvida;
	}
	
	public void setRespostsa(String resposta) {
		this.resposta = resposta;
		this.respondido = true;
		
		String update = "update solicitacoes set resposta= ? where id= ?";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(update);
			pst.setString(1, resposta);
			pst.setInt(2, this.id);
			
			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Resposta cadastrada com sucesso!");
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar a resposta!");
			}
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar a resposta:\n"+ e);
		}
	}
	
	public String getResposta() {
		return resposta;
	}
	
	public boolean isRespondido() {
		return respondido;
	}

}
