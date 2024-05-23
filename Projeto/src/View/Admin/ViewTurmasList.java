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
import Models.Turma;
import Utils.Validador;
import View.Login;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class ViewTurmasList extends JPanel {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JTextField textFieldSearch;
	private JTable table;
	private JTextField textFieldSearchID;
	private JTextField textFieldFilterDisciplane;
	private JList<String> listDisciplanes;
	private JScrollPane scrollPaneFilterDisciplane;
	private JButton btnFilterDisciplane;
	
	private Admin adm;
	private JLabel lblFilters;
	private JLabel lblSearchID;
	private JLabel lblFilterDisciplane;
	private JPanel panelFilters;

	/**
	 * Create the panel.
	 */
	public ViewTurmasList(Admin adm) {
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

		JMenuItem mntmViewAllTurmas = new JMenuItem("Ver Turmas Cadastradas\r\n");
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

		JLabel lblPagina = new JLabel("Tabela de turmas cadastradas");
		lblPagina.setHorizontalAlignment(SwingConstants.CENTER);
		lblPagina.setFont(new Font("Arial Black", Font.PLAIN, 20));

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(ViewTurmasList.class.getResource("/img/Search_Icon.png")));

		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				buscar();
			}
		});
		textFieldSearch.setToolTipText("Pesquisar por nome da turma");
		textFieldSearch.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldSearch.setColumns(10);
		textFieldSearch.setDocument(new Validador(30));

		JScrollPane scrollPane = new JScrollPane();

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionar();
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "ID", "Turma", "Qnt. de Alunos" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.getColumnModel().getColumn(2).setPreferredWidth(150);
		table.getColumnModel().getColumn(1).setPreferredWidth(150);
		scrollPane.setViewportView(table);

		panelFilters = new JPanel();
		panelFilters.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));

		lblFilters = new JLabel("Outros mecânismos de busca:");
		lblFilters.setLabelFor(panelFilters);
		lblFilters.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilters.setFont(new Font("Arial", Font.BOLD, 15));

		lblSearchID = new JLabel("Buscar por ID:");
		lblSearchID.setFont(new Font("Arial", Font.PLAIN, 15));

		textFieldSearchID = new JTextField();
		textFieldSearchID.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

			@Override
			public void keyReleased(KeyEvent e) {
				buscar();
			}
		});
		textFieldSearchID.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchID.setLabelFor(textFieldSearchID);
		textFieldSearchID.setColumns(10);
		textFieldSearchID.setDocument(new Validador(20));

		lblFilterDisciplane = new JLabel("Filtrar por disciplina:");
		lblFilterDisciplane.setFont(new Font("Arial", Font.PLAIN, 15));

		textFieldFilterDisciplane = new JTextField();
		textFieldFilterDisciplane.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (scrollPaneFilterDisciplane.isVisible() && listDisciplanes.isVisible()) {
					scrollPaneFilterDisciplane.setVisible(false);
					listDisciplanes.setVisible(false);
					textFieldFilterDisciplane.setText("");
					btnFilterDisciplane
							.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));
					
					dimensionar();
					buscar();
				} else {
					listarDisciplinas();
				}
			}
		});

		textFieldFilterDisciplane.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldFilterDisciplane.setBackground(Color.WHITE);
		textFieldFilterDisciplane.setEditable(false);
		textFieldFilterDisciplane.setColumns(10);

		scrollPaneFilterDisciplane = new JScrollPane();

		listDisciplanes = new JList<String>();
		listDisciplanes.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldFilterDisciplane.setText(listDisciplanes.getSelectedValue());

				scrollPaneFilterDisciplane.setVisible(false);
				listDisciplanes.setVisible(false);
				btnFilterDisciplane
						.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));

				buscar();
			}
		});
		listDisciplanes.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneFilterDisciplane.setViewportView(listDisciplanes);

		btnFilterDisciplane = new JButton("");
		btnFilterDisciplane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (scrollPaneFilterDisciplane.isVisible() && listDisciplanes.isVisible()) {
					scrollPaneFilterDisciplane.setVisible(false);
					listDisciplanes.setVisible(false);
					textFieldFilterDisciplane.setText("");
					btnFilterDisciplane
							.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));
					
					dimensionar();
					buscar();
				} else {
					listarDisciplinas();
					textFieldFilterDisciplane.requestFocus();
				}
			}
		});
		btnFilterDisciplane.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));
		
		dimensionar();
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(menuBar, GroupLayout.DEFAULT_SIZE, 846, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblPagina, GroupLayout.DEFAULT_SIZE, 381, Short.MAX_VALUE)
					.addGap(10)
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

		scrollPaneFilterDisciplane.setVisible(false);
		listDisciplanes.setVisible(false);
		listagem();

	}

	private void listagem() {

		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Turma", "Qnt. de Alunos" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readLista = "select t.id, t.nome, count(a.nome) from turmas as t left outer join alunos as a on a.id_turma = t.id group by t.id, t.nome order by t.nome";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readLista);
			rs = pst.executeQuery();

			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getInt(3) });
			}

			table.setModel(model);

			table.getColumnModel().getColumn(0).setPreferredWidth(45);
			table.getColumnModel().getColumn(1).setPreferredWidth(400);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void listarDisciplinas() {
		DefaultListModel<String> model = new DefaultListModel<String>();

		String readTurmas = "select d.nome from estuda as e inner join disciplinas as d inner join turmas as t where e.id_turma = t.id and d.id = e.id_disciplina group by d.nome order by d.nome";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readTurmas);
			rs = pst.executeQuery();

			while (rs.next()) {
				model.addElement(rs.getString(1));

			}
			listDisciplanes.setModel(model);
			listDisciplanes.setVisible(true);
			scrollPaneFilterDisciplane.setVisible(true);
			btnFilterDisciplane.setIcon(
					new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
			
			dimensionar();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void buscar() {
		String insert = "select t.id, t.nome, count(a.matricula) from turmas t "
				+ "left join alunos a on a.id_turma = t.id ";
		if (!textFieldFilterDisciplane.getText().isEmpty()) {
			insert += "join estuda es "
					+ "on es.id_turma = t.id "
					+ "join disciplinas d on d.id = es.id_disciplina "
					+ "where d.nome = '" + textFieldFilterDisciplane.getText() + "' ";
		}

		if (!textFieldSearch.getText().isBlank()) {
			if (textFieldFilterDisciplane.getText().isBlank()) {
				insert += "where t.nome like '" + textFieldSearch.getText() + "%' ";
			} else {
				insert += "and t.nome like '" + textFieldSearch.getText() +"%' ";
			}
		}
		if (!textFieldSearchID.getText().isEmpty()) {
			if (textFieldSearch.getText().isBlank() && textFieldFilterDisciplane.getText().isBlank()) {
				insert += "where t.id like '" + textFieldSearchID.getText() + "%' ";
			} else {
				insert += "and t.id like '" + textFieldSearchID.getText() + "%' ";
			}
		}

		insert += "group by t.id order by t.nome";

		DefaultTableModel model = new DefaultTableModel(new Object[][] {},
				new String[] { "ID", "Turma", "Qnt. de Alunos" }) {
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
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getInt(3) });
			}

			table.setModel(model);

			table.getColumnModel().getColumn(0).setPreferredWidth(45);
			table.getColumnModel().getColumn(1).setPreferredWidth(400);
			table.getColumnModel().getColumn(2).setPreferredWidth(100);

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void selecionar() {

		int idSelect = Integer.parseInt((String) table.getValueAt(table.getSelectedRow(), 0));
		String nomeSelect = (String) table.getValueAt(table.getSelectedRow(), 1);
		
		setVisible(false);
		adm.setContentPane(new ViewTurma(adm, new Turma(idSelect, nomeSelect)));
	}

	private void retorno(Admin adm) {
		adm.contentPane.setVisible(true);
		adm.setContentPane(adm.contentPane);
	}

	private void sair(Admin adm) {
		adm.dispose();
		new Login().setVisible(true);
	}
	
	private void dimensionar() {
		GroupLayout gl_panelFilters = new GroupLayout(panelFilters);
		gl_panelFilters.setHorizontalGroup(
			gl_panelFilters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFilters.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panelFilters.createParallelGroup(Alignment.LEADING)
						.addComponent(lblFilters, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblSearchID, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldSearchID, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblFilterDisciplane, GroupLayout.PREFERRED_SIZE, 157, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelFilters.createSequentialGroup()
							.addGap(230)
							.addComponent(btnFilterDisciplane, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addComponent(textFieldFilterDisciplane, GroupLayout.PREFERRED_SIZE, 230, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneFilterDisciplane, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE)))
		);
		gl_panelFilters.setVerticalGroup(
			gl_panelFilters.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelFilters.createSequentialGroup()
					.addGap(8)
					.addComponent(lblFilters, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(39)
					.addComponent(lblSearchID, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addComponent(textFieldSearchID, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(42)
					.addComponent(lblFilterDisciplane, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(gl_panelFilters.createParallelGroup(Alignment.LEADING)
						.addComponent(btnFilterDisciplane, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addComponent(textFieldFilterDisciplane, GroupLayout.PREFERRED_SIZE, 26, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelFilters.createSequentialGroup()
							.addGap(25)
							.addComponent(scrollPaneFilterDisciplane, GroupLayout.PREFERRED_SIZE, 149, GroupLayout.PREFERRED_SIZE))))
		);
		panelFilters.setLayout(gl_panelFilters);
	}

}
