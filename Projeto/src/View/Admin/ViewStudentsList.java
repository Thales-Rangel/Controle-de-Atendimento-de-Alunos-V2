package View.Admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Student;
import Utils.Validador;
import View.Login;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class ViewStudentsList extends JPanel {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JTextField textFieldSearch;
	private JTable table;
	private JTextField textFieldSearchMatricula;
	private JTextField textFieldFilterClass;
	private JList<String> listTurmas;
	private JScrollPane scrollPaneFilterClass;
	private JButton btnFilterClass;

	/**
	 * Create the panel.
	 */
	public ViewStudentsList(Admin adm) {
		setBounds(100, 100, 846, 621);

		JMenuBar menuBar = new JMenuBar();

		JMenu mnStudents = new JMenu("Alunos");
		mnStudents.setForeground(Color.BLACK);
		mnStudents.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnStudents);

		JMenuItem mntmViewAllStudents = new JMenuItem("Ver os estudantes cadastrados");
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
				retorno(adm);
			}
		});
		mntmReturn.setFont(new Font("Arial", Font.PLAIN, 13));
		mnExit.add(mntmReturn);

		JMenuItem mntmExit = new JMenuItem("Sair (Página de Login)");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				sair(adm);
			}
		});
		mntmExit.setFont(new Font("Arial", Font.PLAIN, 13));
		mnExit.add(mntmExit);

		JLabel lblPagina = new JLabel("Tabela de alunos cadastrados");
		lblPagina.setHorizontalAlignment(SwingConstants.CENTER);
		lblPagina.setFont(new Font("Arial Black", Font.PLAIN, 20));

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/Search_Users_Icon.png")));

		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!textFieldSearch.getText().isEmpty()) {
					buscar();
				} else if (textFieldSearchMatricula.getText().isEmpty() && textFieldFilterClass.getText().isEmpty()) {
					listagem();
				} else {
					buscar();
				}
			}
		});
		textFieldSearch.setToolTipText("Pesquisar por nome do aluno");
		textFieldSearch.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldSearch.setColumns(10);
		textFieldSearch.setDocument(new Validador(30));

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionar(adm);
			}
		});
		table.setModel(new DefaultTableModel(
				new Object[][] { { "Teste 1", "Teste2", "Teste3" }, { "Teste6", "Teste5", "Teste4" }, },
				new String[] { "Nome", "Matricula", "Turma" }));
		scrollPane.setViewportView(table);

		JPanel panelFilters = new JPanel();
		panelFilters.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		JLabel lblFilters = new JLabel("Outros mecânismos de busca:");
		lblFilters.setLabelFor(panelFilters);
		lblFilters.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilters.setFont(new Font("Arial", Font.BOLD, 15));

		JLabel lblSearchMatricula = new JLabel("Buscar por matrícula:");
		lblSearchMatricula.setFont(new Font("Arial", Font.PLAIN, 15));

		textFieldSearchMatricula = new JTextField();
		textFieldSearchMatricula.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				if (!textFieldSearchMatricula.getText().isEmpty()) {
					buscar();
				} else if (textFieldSearch.getText().isEmpty() && textFieldFilterClass.getText().isEmpty()) {
					listagem();
				} else {
					buscar();
				}
			}
		});
		textFieldSearchMatricula.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchMatricula.setLabelFor(textFieldSearchMatricula);
		textFieldSearchMatricula.setColumns(10);
		textFieldSearchMatricula.setDocument(new Validador(20));

		JLabel lblFilterClass = new JLabel("Filtrar por turma:");
		lblFilterClass.setFont(new Font("Arial", Font.PLAIN, 15));

		textFieldFilterClass = new JTextField();
		textFieldFilterClass.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (scrollPaneFilterClass.isVisible() && listTurmas.isVisible()) {
					scrollPaneFilterClass.setVisible(false);
					listTurmas.setVisible(false);
					textFieldFilterClass.setText("");
					btnFilterClass
							.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));
					if (textFieldSearch.getText().isEmpty() && textFieldSearchMatricula.getText().isEmpty()) {
						listagem();
					} else {
						buscar();
					}
				} else {
					listarTurmas();
				}
			}
		});

		textFieldFilterClass.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldFilterClass.setBackground(Color.WHITE);
		textFieldFilterClass.setEditable(false);
		textFieldFilterClass.setColumns(10);

		scrollPaneFilterClass = new JScrollPane();

		listTurmas = new JList<String>();
		listTurmas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldFilterClass.setText(listTurmas.getSelectedValue());

				scrollPaneFilterClass.setVisible(false);
				listTurmas.setVisible(false);
				btnFilterClass
						.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));

				buscar();
			}
		});
		listTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneFilterClass.setViewportView(listTurmas);

		btnFilterClass = new JButton("");
		btnFilterClass.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scrollPaneFilterClass.isVisible() && listTurmas.isVisible()) {
					scrollPaneFilterClass.setVisible(false);
					listTurmas.setVisible(false);
					textFieldFilterClass.setText("");
					btnFilterClass
							.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));
					if (textFieldSearch.getText().isEmpty() && textFieldSearchMatricula.getText().isEmpty()) {
						listagem();
					} else {
						buscar();
					}
				} else {
					listarTurmas();
					textFieldFilterClass.requestFocus();
				}
			}
		});
		btnFilterClass.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));
		GroupLayout gl_panelFilters = new GroupLayout(panelFilters);
		gl_panelFilters.setHorizontalGroup(
			gl_panelFilters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFilters.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panelFilters.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFilters, GroupLayout.PREFERRED_SIZE, 260, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSearchMatricula, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldSearchMatricula, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFilterClass, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldFilterClass, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneFilterClass, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelFilters.createSequentialGroup()
							.addGap(230)
							.addComponent(btnFilterClass, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))))
		);
		gl_panelFilters.setVerticalGroup(
			gl_panelFilters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFilters.createSequentialGroup()
					.addGap(8)
					.addComponent(lblFilters, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addComponent(lblSearchMatricula, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addComponent(textFieldSearchMatricula, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(42)
					.addComponent(lblFilterClass, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(gl_panelFilters.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldFilterClass, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelFilters.createSequentialGroup()
							.addGap(25)
							.addComponent(scrollPaneFilterClass, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnFilterClass, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
		);
		panelFilters.setLayout(gl_panelFilters);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblPagina, GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
					.addGap(39)
					.addComponent(lblIcon)
					.addGap(15)
					.addComponent(textFieldSearch, GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
					.addGap(39))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 546, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(panelFilters, GroupLayout.PREFERRED_SIZE, 270, GroupLayout.PREFERRED_SIZE)
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addComponent(menuBar, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(18)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblPagina, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblIcon)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(3)
							.addComponent(textFieldSearch, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)))
					.addGap(16)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE)
						.addComponent(panelFilters, GroupLayout.DEFAULT_SIZE, 523, Short.MAX_VALUE))
					.addGap(10))
		);
		setLayout(groupLayout);

		scrollPaneFilterClass.setVisible(false);
		listTurmas.setVisible(false);
		listagem();

	}

	private void listagem() {

		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Matricula", "Turma" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readLista = "select a.nome, a.matricula, t.nome from alunos as a inner join turmas as t where a.id_turma = t.id order by a.nome";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) });
			}

			table.setModel(model);

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void listarTurmas() {
		DefaultListModel<String> model = new DefaultListModel<String>();

		String readTurmas = "select t.nome from alunos as a inner join turmas as t  where a.id_turma = t.id group by t.nome order by t.nome";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readTurmas);
			rs = pst.executeQuery();

			while (rs.next()) {
				model.addElement(rs.getString(1));

			}
			listTurmas.setModel(model);
			listTurmas.setVisible(true);
			scrollPaneFilterClass.setVisible(true);
			btnFilterClass.setIcon(
					new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void buscar() {
		String insert = "select a.nome, a.matricula, t.nome from alunos as a inner join turmas as t ";

		if (!textFieldSearch.getText().isEmpty()) {
			insert += "where a.nome like '" + textFieldSearch.getText() + "%' ";
		}
		if (!textFieldSearchMatricula.getText().isEmpty()) {
			if (textFieldSearch.getText().isEmpty()) {
				insert += "where a.matricula like '" + textFieldSearchMatricula.getText() + "%' ";
			} else {
				insert += "and a.matricula like '" + textFieldSearchMatricula.getText() + "%' ";
			}
		}
		if (!textFieldFilterClass.getText().isEmpty()) {

			String insertBuscarTurma = "select * from turmas where nome= '" + textFieldFilterClass.getText() + "'";
			int id_turma = 0;
			try {
				con = DAO.conectar();
				pst = con.prepareStatement(insertBuscarTurma);
				rs = pst.executeQuery();

				if (rs.next()) {
					id_turma = rs.getInt(1);
				}

				con.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}

			if (textFieldSearch.getText().isEmpty() && textFieldSearchMatricula.getText().isEmpty()) {
				insert += "where a.id_turma = '" + id_turma + "' ";
			} else {
				insert += "and a.id_turma = '" + id_turma + "' ";
			}
		}

		insert += "and a.id_turma = t.id order by a.nome";

		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "Nome", "Matricula", "Turma" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			rs = pst.executeQuery();

			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3) });
			}

			table.setModel(model);

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void selecionar(Admin adm) {

		String matriculaSelect = (String) table.getValueAt(table.getSelectedRow(), 1);


		setVisible(false);
		adm.setContentPane(new ViewStudent(adm, new Student(matriculaSelect)));
	}

	private void retorno(Admin adm) {
		setVisible(false);
		adm.contentPane.setVisible(true);
		adm.setContentPane(adm.contentPane);
	}

	private void sair(Admin adm) {
		adm.dispose();
		new Login().setVisible(true);
	}
}
