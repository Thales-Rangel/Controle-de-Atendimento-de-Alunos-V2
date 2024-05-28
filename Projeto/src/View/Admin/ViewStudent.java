package View.Admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Student;
import View.Login;

public class ViewStudent extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private Student s;

	private static final long serialVersionUID = 1L;
	private JTable tableDisciplinas;
	private JTable tableProfessores;

	/**
	 * Create the panel.
	 */
	public ViewStudent(Admin adm, Student s) {
		this.s = s;
		
		setBounds(100, 100, 846, 621);
		
		JMenuBar menuBar = new JMenuBar();
		
		JMenu mnStudents = new JMenu("Alunos");
		mnStudents.setForeground(Color.BLACK);
		mnStudents.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnStudents);
		
		JMenuItem mntmViewAllStudents = new JMenuItem("Ver os estudantes cadastrados");
		mntmViewAllStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				adm.setContentPane(new ViewStudentsList(adm));
			}
		});
		mntmViewAllStudents.setFont(new Font("Arial", Font.PLAIN, 13));
		mnStudents.add(mntmViewAllStudents);
		
		JMenuItem mntmCdstStudent = new JMenuItem("Cadastrar um estudante");
		mntmCdstStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrStudent(adm).setVisible(true);
			}
		});
		mntmCdstStudent.setFont(new Font("Arial", Font.PLAIN, 13));
		mnStudents.add(mntmCdstStudent);
		
		JMenu mnProfessors = new JMenu("Professores");
		mnProfessors.setForeground(Color.BLACK);
		mnProfessors.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnProfessors);
		
		JMenuItem mntmViewAllProfessors = new JMenuItem("Ver professores cadastrados");
		mntmViewAllProfessors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				adm.setContentPane(new ViewProfessorsList(adm));
			}
		});
		mntmViewAllProfessors.setFont(new Font("Arial", Font.PLAIN, 13));
		mnProfessors.add(mntmViewAllProfessors);
		
		JMenuItem mntmCadstProfessor = new JMenuItem("Cadastrar um professor");
		mntmCadstProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrProfessor(adm).setVisible(true);
			}
		});
		mntmCadstProfessor.setFont(new Font("Arial", Font.PLAIN, 13));
		mnProfessors.add(mntmCadstProfessor);
		
		JMenu mnTurmas = new JMenu("Turmas");
		mnTurmas.setForeground(Color.BLACK);
		mnTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnTurmas);
		
		JMenuItem mntmViewAllTurmas = new JMenuItem("Ver Turmas Cadastradas\r\n");
		mntmViewAllTurmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				adm.setContentPane(new ViewTurmasList(adm));
			}
		});
		mntmViewAllTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		mnTurmas.add(mntmViewAllTurmas);
		
		JMenuItem mntmCdstTurma = new JMenuItem("Cadastrar uma turma");
		mntmCdstTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrTurma(adm).setVisible(true);
			}
		});
		mntmCdstTurma.setFont(new Font("Arial", Font.PLAIN, 13));
		mnTurmas.add(mntmCdstTurma);
		
		JMenu mnDisciplinas = new JMenu("Disciplinas");
		mnDisciplinas.setForeground(Color.BLACK);
		mnDisciplinas.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnDisciplinas);
		
		JMenuItem mntmViewAllDisciplinas = new JMenuItem("Ver disciplinas cadastradas");
		mntmViewAllDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				adm.setContentPane(new ViewDisciplanesList(adm));
			}
		});
		mntmViewAllDisciplinas.setFont(new Font("Arial", Font.PLAIN, 13));
		mnDisciplinas.add(mntmViewAllDisciplinas);
		
		JMenuItem mntmCdstDisciplina = new JMenuItem("Cadastrar uma disciplina");
		mntmCdstDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrDisciplane(adm).setVisible(true);
			}
		});
		mntmCdstDisciplina.setFont(new Font("Arial", Font.PLAIN, 13));
		mnDisciplinas.add(mntmCdstDisciplina);
		
		JMenu mnExit = new JMenu("Sair");
		mnExit.setForeground(Color.BLACK);
		mnExit.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnExit);
		
		JMenuItem mntmReturn = new JMenuItem("Ir para a página principal");
		mntmReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.contentPane.setVisible(true);
				setVisible(false);
				adm.setContentPane(adm.contentPane);
			}
		});
		mntmReturn.setFont(new Font("Arial", Font.PLAIN, 13));
		mnExit.add(mntmReturn);
		
		JMenuItem mntmExit = new JMenuItem("Sair (Página de Login)");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.dispose();
				new Login().setVisible(true);
			}
		});
		mntmExit.setFont(new Font("Arial", Font.PLAIN, 13));
		mnExit.add(mntmExit);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblNome = new JLabel(s.getNome());
		lblNome.setFont(new Font("Arial", Font.PLAIN, 40));
		panel.add(lblNome);
		
		JLabel lblMatricula = new JLabel("  -  "+ s.getMatricula());
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 40));
		panel.add(lblMatricula);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		JLabel lblTurma = new JLabel("Turma: " + s.getTurma().getNome());
		lblTurma.setFont(new Font("Arial", Font.PLAIN, 25));
		panel_1.add(lblTurma);
		
		JScrollPane scrollPaneDisciplinas = new JScrollPane();
		
		tableDisciplinas = new JTable();
		tableDisciplinas.setRowSelectionAllowed(false);
		tableDisciplinas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Disciplinas"
			}
		));
		tableDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneDisciplinas.setViewportView(tableDisciplinas);
		
		JScrollPane scrollPaneProfessores = new JScrollPane();
		
		tableProfessores = new JTable();
		tableProfessores.setRowSelectionAllowed(false);
		tableProfessores.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Professores"
			}
		));
		tableProfessores.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneProfessores.setViewportView(tableProfessores);
		
		JButton btnDelete = new JButton("Apagar aluno(a)");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir esse(a) aluno(a) permanentemente?");
				
				if (confirma == JOptionPane.YES_OPTION) {
					String deleteStudent = "delete from alunos where matricula= ?";
					
					String deleteSolicitations = "delete from solicitacoes where matricula_a= ?";
					try {
						con = DAO.conectar();
						
						pst = con.prepareStatement(deleteSolicitations);
						pst.setString(1, s.getMatricula());
						pst.execute();
						
						pst = con.prepareStatement(deleteStudent);
						pst.setString(1, s.getMatricula());
						int confirmaExclusao = pst.executeUpdate();
						
						if (confirmaExclusao == 1) {
							JOptionPane.showMessageDialog(null, s.getNome()+ " ecluido com êxito!!");
						}
						
						adm.listagens();
						setVisible(false);
						adm.setContentPane(new ViewStudentsList(adm));
						
						con.close();
					} catch (Exception e2) {
						JOptionPane.showMessageDialog(null, e2);
					}
				}
			}
		});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setBackground(Color.RED);
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JButton btnReturn = new JButton("Voltar");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setVisible(false);
				adm.setContentPane(new ViewStudentsList(adm));
			}
		});
		btnReturn.setForeground(Color.BLACK);
		btnReturn.setFont(new Font("Arial", Font.PLAIN, 20));
		btnReturn.setBackground(UIManager.getColor("Button.background"));
		
		JButton btnEdit = new JButton("Alterar turma do aluno");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditStudentTurma(s, adm).setVisible(true);
			}
		});
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 20));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
					.addGap(10))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
					.addGap(10))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPaneDisciplinas, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(scrollPaneProfessores, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
								.addComponent(btnEdit))
							.addGap(3)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
					.addGap(9)
					.addComponent(panel_1, GroupLayout.DEFAULT_SIZE, 53, Short.MAX_VALUE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneDisciplinas, GroupLayout.PREFERRED_SIZE, 419, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneProfessores, GroupLayout.DEFAULT_SIZE, 419, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(108)
							.addComponent(btnDelete)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnEdit)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(btnReturn)))
					.addGap(10))
		);
		setLayout(groupLayout);
		
		listar();

	}
	
	private void listar() {
		DefaultTableModel modeloDisciplinas = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Disciplinas"
				}
			);
		
		for (int i = 0; i < s.getTurma().getDisciplinas().size(); i++) {
			modeloDisciplinas.addRow(new Object[] { s.getTurma().getDisciplinas().get(i).getNome() });
		}
		
		tableDisciplinas.setModel(modeloDisciplinas);
		
		DefaultTableModel modeloProfessores = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
						"Professores"
				});
		
		String readP = "select p.nome from estuda es "
				+ "inner join ensina en "
				+ "on es.id_disciplina = en.id_disciplina "
				+ "inner join professores p "
				+ "on p.matricula = en.matricula_professor "
				+ "where es.id_turma= ? "
				+ "group by p.matricula "
				+ "order by p.nome";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readP);
			pst.setInt(1, s.getId_turma());
			rs = pst.executeQuery();
			
			while(rs.next()) {
				modeloProfessores.addRow(new Object[] { rs.getString(1) });
			}
			
			tableProfessores.setModel(modeloProfessores);
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
}
