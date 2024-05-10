package Models;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
