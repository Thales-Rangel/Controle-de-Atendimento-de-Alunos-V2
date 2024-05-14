package View.Professor;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import Models.DAO;
import Models.Professor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewDatas extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Professor p;

	private static final long serialVersionUID = 1L;
	private JList<String> listDisciplinas;
	private JList<String> listTurmas;

	/**
	 * Create the panel.
	 */
	public ViewDatas(ProfessorView pv) {
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
		
		JScrollPane scrollPaneDisciplinas = new JScrollPane();
		scrollPaneDisciplinas.setBounds(10, 264, 302, 319);
		add(scrollPaneDisciplinas);
		
		listDisciplinas = new JList<String>();
		listDisciplinas.setFont(new Font("Arial", Font.PLAIN, 13));
		scrollPaneDisciplinas.setViewportView(listDisciplinas);
		
		JScrollPane scrollPaneTurmas = new JScrollPane();
		scrollPaneTurmas.setBounds(322, 264, 302, 319);
		add(scrollPaneTurmas);
		
		listTurmas = new JList<String>();
		listTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		scrollPaneTurmas.setViewportView(listTurmas);
		
		JButton btnEdit = new JButton("Editar dados pessoais");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.viewPanel.setVisible(false);
				pv.viewPanel = new AlterDatas(pv);
				pv.contentPane.add(pv.viewPanel);
			}
		});
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 15));
		btnEdit.setBounds(10, 173, 189, 39);
		add(btnEdit);
		
		JLabel lblDisciplinas = new JLabel("Minhas disciplinas:");
		lblDisciplinas.setFont(new Font("Arial", Font.PLAIN, 20));
		lblDisciplinas.setBounds(10, 222, 302, 32);
		add(lblDisciplinas);
		
		JLabel lblTurmas = new JLabel("Minhas turmas:");
		lblTurmas.setFont(new Font("Arial", Font.PLAIN, 20));
		lblTurmas.setBounds(322, 222, 302, 32);
		add(lblTurmas);

		listagem();
		
	}
	
	private void listagem() {
		DefaultListModel<String> modeloDisciplinas = new DefaultListModel<String>();
		
		for(int i = 0; i < p.getDisciplinas().size(); i++) {
			modeloDisciplinas.addElement(p.getDisciplinas().get(i).getNome());
		}
		
		listDisciplinas.setModel(modeloDisciplinas);
		
		DefaultListModel<String> modeloTurmas = new DefaultListModel<String>();
		
		String readT = "select t.nome from estuda es "
				+ "inner join turmas t "
				+ "on t.id = es.id_turma "
				+ "inner join ensina en "
				+ "on en.id_disciplina = es.id_disciplina "
				+ "where en.matricula_professor= '"+ p.getMatricula()+"' "
				+ "order by t.nome";
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readT);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				modeloTurmas.addElement(rs.getString(1));
			}
			
			listTurmas.setModel(modeloTurmas);
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel listar as turmas:\n"+ e);
		}
	}
}
