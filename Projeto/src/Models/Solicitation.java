package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JOptionPane;

public class Solicitation {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private int id;
	private Student aluno;
	private Professor professor;
	private Disciplane disciplina;
	private String duvida;
	private String resposta;
	private boolean respondido;
	
	public Solicitation(int id, Student aluno, Professor professor, Disciplane disciplina, String duvida) {
		this.id = id;
		this.aluno = aluno;
		this.professor = professor;
		this.disciplina = disciplina;
		this.duvida = duvida;
		
		this.respondido = false;
	}
	
	public Solicitation(int id, Student aluno, Professor professor, Disciplane disciplina, String duvida, boolean respondido) {
		this.id = id;
		this.aluno = aluno;
		this.professor = professor;
		this.disciplina = disciplina;
		this.duvida = duvida;
		this.respondido = respondido;
		
		if (respondido) {
			try {
				con = DAO.conectar();
				pst = con.prepareStatement("select resposta from solicitacoes where id= '"+id+"'");
				rs = pst.executeQuery();
				if (rs.next())
					this.resposta = rs.getString(1);
				
				con.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Não foi possivel carregar a resposta da solicitação!\n"+e);
			}
		}
	}
	
	public Solicitation(int id) {
		String insert = "select * from solicitacoes where id= '"+ id +"'";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				this.id = rs.getInt(1);
				this.aluno = new Student(rs.getString(2));
				this.professor = new Professor(rs.getString(3));
				this.disciplina = new Disciplane(rs.getInt(4));
				this.duvida = rs.getString(5);
				if(rs.getString(7).equals("T"))
					this.resposta = rs.getString(6);
				this.respondido = rs.getString(7).equals("T");
			} else {
				JOptionPane.showMessageDialog(null, "Solicitação não encontrada!");
			}
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar a solicitação"+e);
		}
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
	
	public Disciplane getDisciplina() {
		return disciplina;
	}
	
	public String getDuvida() {
		return duvida;
	}
	
	public void setResposta(String resposta) {
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
				
				pst = con.prepareStatement("update solicitacoes set respondido= 'T' where id= ?");
				pst.setInt(1, this.id);
				
				int confirma2 = pst.executeUpdate();
				if (confirma2 == 1)
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
