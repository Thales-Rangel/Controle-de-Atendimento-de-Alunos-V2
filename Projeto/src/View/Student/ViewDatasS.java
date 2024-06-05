package View.Student;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;

import Models.Student;
import Utils.DAO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewDatasS extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	
	private Student s;
	private JList<String> listProfessores;
	private JList<String> listDisciplinas;


	public ViewDatasS(StudentView sv) {
		this.s = sv.s;
		
		setBounds(0, 0, 630, 593);
		
		JLabel lblPage = new JLabel("Dados pessoais");
		lblPage.setFont(new Font("Arial Black", Font.PLAIN, 25));
		
		JLabel lblNome = new JLabel("Nome: "+ s.getNome());
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel lblMatricula = new JLabel("Matricula: "+ s.getMatricula());
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel lblTurma = new JLabel("Turma: "+ s.getTurma().getNome());
		lblTurma.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel lblSenha = new JLabel("Senha: ");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		
		for (int i = 0; i < s.getSenha().length(); i++) {
			lblSenha.setText(lblSenha.getText() + "*");
		}
		
		JLabel lblDisciplinas = new JLabel("Minhas disciplinas");
		lblDisciplinas.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisciplinas.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel lblDisciplinas_1 = new JLabel("Meus professores");
		lblDisciplinas_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblDisciplinas_1.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JScrollPane scrollPane = new JScrollPane();
		
		JScrollPane scrollPane_1 = new JScrollPane();
		
		JButton btnEdit = new JButton("");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditDatasS(sv).setVisible(true);
			}
		});
		btnEdit.setToolTipText("Editar dados pessoais");
		btnEdit.setIcon(new ImageIcon(ViewDatasS.class.getResource("/img/Edit_Icon.png")));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPage, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblDisciplinas, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
							.addGap(33)
							.addComponent(lblDisciplinas_1, GroupLayout.DEFAULT_SIZE, 280, Short.MAX_VALUE)
							.addGap(3))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 294, Short.MAX_VALUE)
							.addGap(33)
							.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 283, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(lblMatricula, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTurma, GroupLayout.PREFERRED_SIZE, 309, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(10)
							.addComponent(lblPage)
							.addGap(18)
							.addComponent(lblNome)
							.addGap(6)
							.addComponent(lblMatricula)
							.addGap(6)
							.addComponent(lblTurma)
							.addGap(6)
							.addComponent(lblSenha)
							.addGap(18))
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 34, GroupLayout.PREFERRED_SIZE)
							.addGap(58)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDisciplinas)
						.addComponent(lblDisciplinas_1))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE)
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 353, Short.MAX_VALUE))
					.addGap(10))
		);
		
		listProfessores = new JList<String>();
		scrollPane_1.setViewportView(listProfessores);
		
		listDisciplinas = new JList<String>();
		scrollPane.setViewportView(listDisciplinas);
		setLayout(groupLayout);
		
		listagens();

	}
	
	private void listagens() {
		String selectProfessores = "select p.nome from estuda es "
				+ "join ensina en "
				+ "on es.id_disciplina = en.id_disciplina "
				+ "join professores p "
				+ "on p.matricula = en.matricula_professor "
				+ "where es.id_turma= '"+ s.getId_turma() +"' "
				+ "group by p.matricula "
				+ "order by p.nome";
		
		try {
			con = DAO.conectar();
			
			DefaultListModel<String> modelD = new DefaultListModel<String>();
			
			for (int i = 0; i < s.getTurma().getDisciplinas().size(); i++)
				modelD.addElement(s.getTurma().getDisciplinas().get(i).getNome());
			
			DefaultListModel<String> modelP = new DefaultListModel<String>();
			
			pst = con.prepareStatement(selectProfessores);
			rs = pst.executeQuery();
			while(rs.next())
				modelP.addElement(rs.getString(1));
			
			listDisciplinas.setModel(modelD);
			listProfessores.setModel(modelP);
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel listar as disciplinas e os professores:\n"+e);
		}
	}
}
