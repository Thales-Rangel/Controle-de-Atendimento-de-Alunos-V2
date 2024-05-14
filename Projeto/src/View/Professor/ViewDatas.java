package View.Professor;

import javax.swing.JPanel;

import Models.Professor;
import javax.swing.JLabel;
import java.awt.Font;

public class ViewDatas extends JPanel {
	
	private ProfessorView pv;
	private Professor p;

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ViewDatas(ProfessorView pv) {
		this.pv = pv;
		this.p = pv.prof;

		setBounds(0, 0, 637, 593);
		setLayout(null);
		
		JLabel lblTitulo = new JLabel("Dados pessoais");
		lblTitulo.setFont(new Font("Arial Black", Font.PLAIN, 25));
		lblTitulo.setBounds(10, 10, 302, 39);
		add(lblTitulo);
		
		JLabel lblNome = new JLabel("Nome: "+ p.getNome());
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNome.setBounds(10, 59, 617, 28);
		add(lblNome);
		
		JLabel lblMatricula = new JLabel("Matricula: "+ p.getMatricula());
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 20));
		lblMatricula.setBounds(10, 97, 617, 28);
		add(lblMatricula);
		
		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		lblSenha.setBounds(10, 135, 617, 28);
		
		String senha = "Senha: ";
		for(int i = 0; i < p.getSenha().length(); i++) {
			senha += "*";
		}
		lblSenha.setText(senha);
		
		add(lblSenha);

	}
}
