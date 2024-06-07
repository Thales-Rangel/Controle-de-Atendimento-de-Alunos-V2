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

import Models.Professor;
import Utils.DAO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;

public class ViewDatasP extends JPanel {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Professor p;

	private static final long serialVersionUID = 1L;
	private JList<String> listDisciplinas;
	private JList<String> listTurmas;

	public ViewDatasP(ProfessorView pv) {
		this.p = pv.prof;

		setBounds(0, 0, 637, 593);

		JLabel lblTitulo = new JLabel("Dados pessoais");
		lblTitulo.setFont(new Font("Arial Black", Font.PLAIN, 25));

		JLabel lblNome = new JLabel("Nome: " + p.getNome());
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));

		JLabel lblMatricula = new JLabel("Matricula: " + p.getMatricula());
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 20));

		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 20));

		String senha = "Senha: ";
		for (int i = 0; i < p.getSenha().length(); i++) {
			senha += "*";
		}
		lblSenha.setText(senha);

		JScrollPane scrollPaneDisciplinas = new JScrollPane();

		listDisciplinas = new JList<String>();
		listDisciplinas.setFont(new Font("Arial", Font.PLAIN, 13));
		scrollPaneDisciplinas.setViewportView(listDisciplinas);

		JScrollPane scrollPaneTurmas = new JScrollPane();

		listTurmas = new JList<String>();
		listTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		scrollPaneTurmas.setViewportView(listTurmas);

		JButton btnEdit = new JButton("");
		btnEdit.setIcon(new ImageIcon(ViewDatasP.class.getResource("/img/Edit_Icon.png")));
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.setViewPanel(new EditDatasP(pv));
			}
		});
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 15));

		JLabel lblDisciplinas = new JLabel("Minhas disciplinas:");
		lblDisciplinas.setFont(new Font("Arial", Font.PLAIN, 20));

		JLabel lblTurmas = new JLabel("Minhas turmas:");
		lblTurmas.setFont(new Font("Arial", Font.PLAIN, 20));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(10)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 302, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblMatricula, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 617, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblDisciplinas, GroupLayout.PREFERRED_SIZE, 304,
										GroupLayout.PREFERRED_SIZE)
								.addGap(6)
								.addComponent(lblTurmas, GroupLayout.PREFERRED_SIZE, 304, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(scrollPaneDisciplinas, GroupLayout.PREFERRED_SIZE, 302,
										GroupLayout.PREFERRED_SIZE)
								.addGap(10).addComponent(scrollPaneTurmas, GroupLayout.PREFERRED_SIZE, 302,
										GroupLayout.PREFERRED_SIZE)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(10)
				.addComponent(lblTitulo, GroupLayout.PREFERRED_SIZE, 37, GroupLayout.PREFERRED_SIZE).addGap(10)
				.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addGap(10)
				.addComponent(lblMatricula, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addGap(10)
				.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 30, GroupLayout.PREFERRED_SIZE).addGap(10)
				.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE).addGap(17)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDisciplinas, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblTurmas, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))
				.addGap(6)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneDisciplinas, GroupLayout.PREFERRED_SIZE, 319,
								GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneTurmas, GroupLayout.PREFERRED_SIZE, 319, GroupLayout.PREFERRED_SIZE))));
		setLayout(groupLayout);

		listagem();

	}

	private void listagem() {
		DefaultListModel<String> modeloDisciplinas = new DefaultListModel<String>();

		for (int i = 0; i < p.getDisciplinas().size(); i++) {
			modeloDisciplinas.addElement(p.getDisciplinas().get(i).getNome());
		}

		listDisciplinas.setModel(modeloDisciplinas);

		DefaultListModel<String> modeloTurmas = new DefaultListModel<String>();

		String readT = "select distinct t.nome from estuda es join turmas t on t.id = es.id_turma "
				+ "join ensina en on en.id_disciplina = es.id_disciplina where en.matricula_professor= '"
				+ p.getMatricula() + "' order by t.nome";
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
			JOptionPane.showMessageDialog(null, "Não foi possivel listar as turmas:\n" + e);
		}
	}
}
