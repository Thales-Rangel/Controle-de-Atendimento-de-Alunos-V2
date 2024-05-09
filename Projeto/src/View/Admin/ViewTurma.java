package View.Admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Turma;
import View.Login;
import javax.swing.JButton;

public class ViewTurma extends JPanel {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;

	private Admin adm;
	private Turma t;
	private JTable tableProfessores;
	private JTable tableDisciplinas;
	private JTable tableAlunos;
	public JLabel lblNome;

	/**
	 * Create the panel.
	 */
	public ViewTurma(Admin adm, Turma t) {
		this.adm = adm;
		this.t = t;

		setBounds(100, 100, 846, 621);
		setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 846, 22);
		add(menuBar);

		JMenu mnStudents = new JMenu("Alunos");
		mnStudents.setForeground(Color.BLACK);
		mnStudents.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnStudents);

		JMenuItem mntmViewAllStudents = new JMenuItem("Ver os estudantes cadastrados");
		mntmViewAllStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.vsl.setVisible(true);
				adm.vt.setVisible(false);
				adm.setContentPane(adm.vsl);
			}
		});
		mntmViewAllStudents.setFont(new Font("Arial", Font.PLAIN, 13));
		mnStudents.add(mntmViewAllStudents);

		JMenuItem mntmCdstStudent = new JMenuItem("Cadastrar um estudante");
		mntmCdstStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.cadastroEstudante.setVisible(true);
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
				adm.vpl.setVisible(true);
				adm.vt.setVisible(false);
				adm.setContentPane(adm.vpl);
			}
		});
		mntmViewAllProfessors.setFont(new Font("Arial", Font.PLAIN, 13));
		mnProfessors.add(mntmViewAllProfessors);

		JMenuItem mntmCadstProfessor = new JMenuItem("Cadastrar um professor");
		mntmCadstProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.cadastroProfessor.setVisible(true);
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
				adm.vtl.setVisible(true);
				adm.vt.setVisible(false);
				adm.setContentPane(adm.vtl);
			}
		});
		mntmViewAllTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		mnTurmas.add(mntmViewAllTurmas);

		JMenuItem mntmCdstTurma = new JMenuItem("Cadastrar uma turma");
		mntmCdstTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.cadastroTurma.setVisible(true);
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
				adm.vdl.setVisible(true);
				adm.vt.setVisible(false);
				adm.setContentPane(adm.vdl);
			}
		});
		mntmViewAllDisciplinas.setFont(new Font("Arial", Font.PLAIN, 13));
		mnDisciplinas.add(mntmViewAllDisciplinas);

		JMenuItem mntmCdstDisciplina = new JMenuItem("Cadastrar uma disciplina");
		mntmCdstDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.cadastroDisciplina.setVisible(true);
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
				adm.vt.setVisible(false);
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
		panel.setBounds(10, 32, 826, 88);
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		lblNome = new JLabel(t.getNome() + " - ID: " + t.getId());
		lblNome.setFont(new Font("Arial", Font.PLAIN, 40));
		panel.add(lblNome);

		JScrollPane scrollPaneProfessores = new JScrollPane();
		scrollPaneProfessores.setBounds(10, 130, 269, 239);
		add(scrollPaneProfessores);

		tableProfessores = new JTable();
		tableProfessores.setRowSelectionAllowed(false);
		tableProfessores.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Professores" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableProfessores.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneProfessores.setViewportView(tableProfessores);

		JScrollPane scrollPaneDisciplinas = new JScrollPane();
		scrollPaneDisciplinas.setBounds(289, 130, 269, 239);
		add(scrollPaneDisciplinas);

		tableDisciplinas = new JTable();
		tableDisciplinas.setRowSelectionAllowed(false);
		tableDisciplinas.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Disciplinas" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneDisciplinas.setViewportView(tableDisciplinas);

		JScrollPane scrollPaneAlunos = new JScrollPane();
		scrollPaneAlunos.setBounds(10, 379, 548, 232);
		add(scrollPaneAlunos);

		tableAlunos = new JTable();
		tableAlunos.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Aluno", "Matricula" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableAlunos.setRowSelectionAllowed(false);
		tableAlunos.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneAlunos.setViewportView(tableAlunos);

		JButton btnApagarTurma = new JButton("Apagar Turma");
		btnApagarTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deletar();
			}
		});
		btnApagarTurma.setForeground(Color.WHITE);
		btnApagarTurma.setFont(new Font("Arial", Font.PLAIN, 20));
		btnApagarTurma.setBackground(Color.RED);
		btnApagarTurma.setBounds(568, 306, 268, 39);
		add(btnApagarTurma);
		
		JButton btnEdit = new JButton("Editar Turma");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditTurma(adm, t).setVisible(true);;
			}
		});
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 20));
		btnEdit.setBounds(568, 355, 268, 39);
		add(btnEdit);
		
		JButton btnReturn = new JButton("Voltar");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.vt.setVisible(false);
				adm.vtl.setVisible(true);
				adm.setContentPane(adm.vtl);
			}
		});
		btnReturn.setFont(new Font("Arial", Font.PLAIN, 20));
		btnReturn.setBounds(568, 404, 268, 39);
		add(btnReturn);

		listagem();

	}

	public void listagem() {
		DefaultTableModel modeloProfessores = new DefaultTableModel(new Object[][] {}, new String[] { "Professores" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		DefaultTableModel modeloDisciplinas = new DefaultTableModel(new Object[][] {}, new String[] { "Disciplinas" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		for (int i = 0; i < t.getDisciplinas().size(); i++) {
			modeloDisciplinas.addRow(new Object[] { t.getDisciplinas().get(i).getNome() });
		}

		tableDisciplinas.setModel(modeloDisciplinas);

		DefaultTableModel modeloAlunos = new DefaultTableModel(new Object[][] {},
				new String[] { "Alunos", "Matriculas" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readProfessores = "select p.nome from estuda es " + "inner join ensina en "
				+ "on es.id_disciplina = en.id_disciplina " + "inner join professores p "
				+ "on p.matricula = en.matricula_professor " + "where es.id_turma= ? " + "group by p.matricula "
				+ "order by p.nome";

		String readAlunos = "select nome, matricula from alunos " + "where id_turma= ? " + "order by nome";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readProfessores);
			pst.setInt(1, t.getId());
			rs = pst.executeQuery();

			while (rs.next()) {
				modeloProfessores.addRow(new Object[] { rs.getString(1) });
			}
			tableProfessores.setModel(modeloProfessores);

			pst = con.prepareStatement(readAlunos);
			pst.setInt(1, t.getId());
			rs = pst.executeQuery();

			while (rs.next()) {
				modeloAlunos.addRow(new Object[] { rs.getString(1), rs.getString(2) });
			}
			tableAlunos.setModel(modeloAlunos);

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}

	}

	private void deletar() {
		String readAlunos = "select count(*) from alunos " + "where id_turma= ?";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readAlunos);
			pst.setInt(1, t.getId());
			rs = pst.executeQuery();

			if (rs.next() && rs.getInt(1) != 0) {
				int confirma = JOptionPane.showConfirmDialog(null, t.getNome() + " tem " + rs.getInt(1)
						+ " alunos cadastrados.\nDeseja excluir a turma e todos os alunos cadastrados juntos?");

				if (confirma == JOptionPane.YES_OPTION) {

					pst = con.prepareStatement("select count(*) from alunos where id_turma= ?");
					pst.setInt(1, t.getId());
					rs = pst.executeQuery();
					int exTotal = 0;
					if (rs.next()) {
						exTotal = rs.getInt(1);
					}

					String deleteStudents = "select matricula from alunos where id_turma= ?";

					pst = con.prepareStatement(deleteStudents);
					pst.setInt(1, t.getId());
					rs = pst.executeQuery();

					int confirmaDelAlunos = 0;
					while (rs.next()) {
						pst = con.prepareStatement("delete from alunos where matricula= ?");
						pst.setString(1, rs.getString(1));
						confirmaDelAlunos += pst.executeUpdate();
					}

					if (confirmaDelAlunos == exTotal) {

						pst = con.prepareStatement("select count(*) from estuda where id_turma= ?");
						pst.setInt(1, t.getId());
						rs = pst.executeQuery();
						int totaldisciplinas = 0;
						if (rs.next()) {
							totaldisciplinas = rs.getInt(1);
						}

						pst = con.prepareStatement("select id from estuda where id_turma= ?");
						pst.setInt(1, t.getId());
						rs = pst.executeQuery();

						int confirmaDelEstuda = 0;
						while (rs.next()) {
							pst = con.prepareStatement("delete from estuda where id= ?");
							pst.setInt(1, rs.getInt(1));
							confirmaDelEstuda += pst.executeUpdate();
						}

						if (confirmaDelEstuda == totaldisciplinas) {
							pst = con.prepareStatement("delete from turmas where id= ?");
							pst.setInt(1, t.getId());

							int confirmaDelTurma = pst.executeUpdate();
							if (confirmaDelTurma == 1) {
								JOptionPane.showMessageDialog(null, "Turma e alunos excluidos com sucesso!");

								adm.listagens();
								adm.status();
								adm.vtl.listagem();
								adm.vsl.listagem();
								adm.vdl.listagem();
								adm.vtl.setVisible(true);
								adm.vt.setVisible(false);
								adm.setContentPane(adm.vtl);
							} else {
								JOptionPane.showMessageDialog(null, "Não foi poossivel excluir a turma!");
							}
						} else {
							JOptionPane.showMessageDialog(null,"Não foi possivel excluir as conexões da turma com as disciplinas cursadas!");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Não foi possivel excluir os alunos da turma!");
					}
				}
			} else {
				int confirmaDel = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja excluir essa turma?");
				
				if (confirmaDel == JOptionPane.YES_OPTION) {
					
					pst = con.prepareStatement("select id from estuda where id_turma= ?");
					pst.setInt(1, t.getId());
					rs = pst.executeQuery();
					while (rs.next()) {
						pst = con.prepareStatement("delete from estuda where id= ?");
						pst.setInt(1, rs.getInt(1));
						pst.execute();
					}
					
					String delete = "delete from turmas where id= ?";

					pst = con.prepareStatement(delete);
					pst.setInt(1, t.getId());

					int confirma = pst.executeUpdate();
					if (confirma == 1) {
						JOptionPane.showMessageDialog(null, "Turma excluida com sucesso!");
						
						adm.listagens();
						adm.status();
						adm.vtl.listagem();
						adm.vsl.listagem();
						adm.vdl.listagem();
						adm.vtl.setVisible(true);
						adm.vt.setVisible(false);
						adm.setContentPane(adm.vtl);
					} else {
						JOptionPane.showMessageDialog(null, "Não foi possivel excluir a turma!");
					}

				}
			}

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível excluir a turma:\n" + e);
		}
	}
}
