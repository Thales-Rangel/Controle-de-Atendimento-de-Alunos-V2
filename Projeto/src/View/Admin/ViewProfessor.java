package View.Admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Professor;
import View.Login;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class ViewProfessor extends JPanel {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Professor p;
	private Admin adm;

	private static final long serialVersionUID = 1L;
	private JTable tableDisciplinas;
	private JTable tableTurmas;

	/**
	 * Create the panel.
	 */
	public ViewProfessor(Admin adm, Professor p) {
		this.p = p;
		this.adm = adm;

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

		JMenuItem mntmViewAllTurmas = new JMenuItem("Ver Turmas Cadastradas");
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

		JLabel lblNome = new JLabel(p.getNome());
		lblNome.setFont(new Font("Arial", Font.PLAIN, 40));
		panel.add(lblNome);

		JLabel lblMatricula = new JLabel("  -  " + p.getMatricula());
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 40));
		panel.add(lblMatricula);

		JScrollPane scrollPaneDisciplinas = new JScrollPane();

		tableDisciplinas = new JTable();
		tableDisciplinas.setRowSelectionAllowed(false);
		tableDisciplinas.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Disciplinas" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneDisciplinas.setViewportView(tableDisciplinas);

		JScrollPane scrollPaneTurmas = new JScrollPane();

		tableTurmas = new JTable();
		tableTurmas.setRowSelectionAllowed(false);
		tableTurmas.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Turmas" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneTurmas.setViewportView(tableTurmas);

		JButton btnDelete = new JButton("Apagar professor(a)");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirma = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja excluir esse(a) professor(a) permanentemente?");

				if (confirma == JOptionPane.YES_OPTION) {
					deletar();
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
				adm.setContentPane(new ViewProfessorsList(adm));
			}
		});
		btnReturn.setForeground(Color.BLACK);
		btnReturn.setFont(new Font("Arial", Font.PLAIN, 20));
		btnReturn.setBackground(UIManager.getColor("Button.background"));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
					.addGap(10))
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(scrollPaneDisciplinas, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(scrollPaneTurmas, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 232, GroupLayout.PREFERRED_SIZE))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(panel, GroupLayout.DEFAULT_SIZE, 88, Short.MAX_VALUE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneTurmas, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(164)
							.addComponent(btnDelete, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addGap(10)
							.addComponent(btnReturn, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneDisciplinas, GroupLayout.DEFAULT_SIZE, 481, Short.MAX_VALUE))
					.addGap(10))
		);
		setLayout(groupLayout);

		listar();

	}

	private void listar() {
		DefaultTableModel modeloDisciplinas = new DefaultTableModel(new Object[][] {}, new String[] { "Disciplinas" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		for (int i = 0; i < p.getDisciplinas().size(); i++) {
			modeloDisciplinas.addRow(new Object[] { p.getDisciplinas().get(i).getNome() });
		}

		tableDisciplinas.setModel(modeloDisciplinas);

		DefaultTableModel modeloTurmas = new DefaultTableModel(new Object[][] {}, new String[] { "Turmas" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };
			
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readT = "select t.nome from ensina en " + "inner join estuda es "
				+ "on es.id_disciplina = en.id_disciplina " + "inner join turmas t " + "on t.id = es.id_turma "
				+ "where en.matricula_professor= ? " + "group by t.id " + "order by t.nome";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readT);
			pst.setString(1, p.getMatricula());
			rs = pst.executeQuery();

			while (rs.next()) {
				modeloTurmas.addRow(new Object[] { rs.getString(1) });
			}

			tableTurmas.setModel(modeloTurmas);

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void deletar() {
		String deleteEnsina = "delete from ensina where matricula_professor= ?";
		
		String deleteSolicitacoes = "delete from solicitacoes where matricula_p= ?";
		
		String insert = "delete from professores where matricula= ?";

		try {
			con = DAO.conectar();
			
			pst = con.prepareStatement(deleteEnsina);
			pst.setString(1, p.getMatricula());
			pst.execute();
			
			pst = con.prepareStatement(deleteSolicitacoes);
			pst.setString(1, p.getMatricula());
			pst.execute();
			
			pst = con.prepareStatement(insert);
			pst.setString(1, p.getMatricula());
			int confirmaExclusao = pst.executeUpdate();

			if (confirmaExclusao == 1) {
				JOptionPane.showMessageDialog(null, p.getNome() + " ecluido com êxito!!");
				
				adm.listagens();
				setVisible(false);
				adm.setContentPane(new ViewProfessorsList(adm));
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possivel excluir "+ p.getNome());
			}

			con.close();
		} catch (Exception e2) {
			JOptionPane.showMessageDialog(null, e2);
		}
	}

}
