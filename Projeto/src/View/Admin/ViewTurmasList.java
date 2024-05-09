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

	/**
	 * Create the panel.
	 */
	public ViewTurmasList(Admin adm) {
		this.adm = adm;
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
				adm.vtl.setVisible(false);
				adm.vsl.setVisible(true);
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
				adm.vtl.setVisible(false);
				adm.vpl.setVisible(true);
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

		JMenuItem mntmViewAllTurmas = new JMenuItem("Ver Turmas Cadastradas\r\n");
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
				adm.vtl.setVisible(false);
				adm.vdl.setVisible(true);
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
		lblPagina.setBounds(10, 40, 381, 32);
		add(lblPagina);

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(ViewTurmasList.class.getResource("/img/Search_Icon.png")));
		lblIcon.setBounds(401, 40, 32, 32);
		add(lblIcon);

		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!textFieldSearch.getText().isEmpty()) {
					buscar();
				} else if (textFieldSearchID.getText().isEmpty() && textFieldFilterDisciplane.getText().isEmpty()) {
					listagem();
				} else {
					buscar();
				}
			}
		});
		textFieldSearch.setToolTipText("Pesquisar por nome da turma");
		textFieldSearch.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldSearch.setBounds(448, 43, 359, 26);
		textFieldSearch.setColumns(10);
		textFieldSearch.setDocument(new Validador(30));
		add(textFieldSearch);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 88, 546, 523);
		add(scrollPane);

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

		JPanel panelFilters = new JPanel();
		panelFilters.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelFilters.setBounds(566, 88, 280, 533);
		add(panelFilters);
		panelFilters.setLayout(null);

		JLabel lblFilters = new JLabel("Outros mecânismos de busca:");
		lblFilters.setLabelFor(panelFilters);
		lblFilters.setHorizontalAlignment(SwingConstants.CENTER);
		lblFilters.setFont(new Font("Arial", Font.BOLD, 15));
		lblFilters.setBounds(10, 10, 260, 26);
		panelFilters.add(lblFilters);

		JLabel lblSearchID = new JLabel("Buscar por ID:");
		lblSearchID.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchID.setBounds(10, 75, 157, 26);
		panelFilters.add(lblSearchID);

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
				if (!textFieldSearchID.getText().isEmpty()) {
					buscar();
				} else if (textFieldSearch.getText().isEmpty() && textFieldFilterDisciplane.getText().isEmpty()) {
					listagem();
				} else {
					buscar();
				}
			}
		});
		textFieldSearchID.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSearchID.setLabelFor(textFieldSearchID);
		textFieldSearchID.setBounds(10, 101, 260, 26);
		panelFilters.add(textFieldSearchID);
		textFieldSearchID.setColumns(10);
		textFieldSearchID.setDocument(new Validador(20));

		JLabel lblFilterDisciplane = new JLabel("Filtrar por disciplina:");
		lblFilterDisciplane.setFont(new Font("Arial", Font.PLAIN, 15));
		lblFilterDisciplane.setBounds(10, 169, 157, 26);
		panelFilters.add(lblFilterDisciplane);

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
					if (textFieldSearch.getText().isEmpty() && textFieldSearchID.getText().isEmpty()) {
						listagem();
					} else {
						buscar();
					}
				} else {
					listarDisciplinas();
				}
			}
		});

		textFieldFilterDisciplane.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldFilterDisciplane.setBackground(Color.WHITE);
		textFieldFilterDisciplane.setEditable(false);
		textFieldFilterDisciplane.setBounds(10, 205, 240, 26);
		panelFilters.add(textFieldFilterDisciplane);
		textFieldFilterDisciplane.setColumns(10);

		scrollPaneFilterDisciplane = new JScrollPane();
		scrollPaneFilterDisciplane.setBounds(10, 230, 260, 149);
		panelFilters.add(scrollPaneFilterDisciplane);

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
					if (textFieldSearch.getText().isEmpty() && textFieldSearchID.getText().isEmpty()) {
						listagem();
					} else {
						buscar();
					}
				} else {
					listarDisciplinas();
					textFieldFilterDisciplane.requestFocus();
				}
			}
		});
		btnFilterDisciplane.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));
		btnFilterDisciplane.setBounds(250, 205, 20, 26);
		panelFilters.add(btnFilterDisciplane);

		scrollPaneFilterDisciplane.setVisible(false);
		listDisciplanes.setVisible(false);
		listagem();

	}

	public void listagem() {

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
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void buscar() {
		String insert = "select t.id, t.nome, count(a.matricula) from turmas as t "
				+ "left outer join alunos as a on a.id_turma = t.id ";
		if (!textFieldFilterDisciplane.getText().isEmpty()) {

			String insertBuscarDisciplina = "select * from disciplinas where nome= '"
					+ textFieldFilterDisciplane.getText() + "'";
			int id_disciplina = 0;
			try {
				con = DAO.conectar();
				pst = con.prepareStatement(insertBuscarDisciplina);
				rs = pst.executeQuery();

				if (rs.next()) {
					id_disciplina = rs.getInt(1);
				}

				con.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, e);
			}

			insert += "inner join estuda as es "
					+ "on es.id_turma = t.id "
					+ "where es.id_disciplina = '" + id_disciplina + "' ";

		}

		if (!textFieldSearch.getText().isEmpty()) {
			if (textFieldFilterDisciplane.getText().isEmpty()) {
				insert += "where t.nome like '" + textFieldSearch.getText() + "%' ";
			} else {
				insert += "and t.nome like '" + textFieldSearch.getText() +"%' ";
			}
		}
		if (!textFieldSearchID.getText().isEmpty()) {
			if (textFieldSearch.getText().isEmpty() && textFieldFilterDisciplane.getText().isEmpty()) {
				insert += "where t.id like '" + textFieldSearchID.getText() + "%' ";
			} else {
				insert += "and t.id like '" + textFieldSearchID.getText() + "%' ";
			}
		}

		insert += "group by t.id, t.nome order by t.nome";

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

			table.getColumnModel().getColumn(0).setPreferredWidth(15);
			table.getColumnModel().getColumn(1).setPreferredWidth(200);
			table.getColumnModel().getColumn(2).setPreferredWidth(200);

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void selecionar() {

		String idSelect = (String) table.getValueAt(table.getSelectedRow(), 0);

		String insert = "select * from turmas where id= ?";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(insert);
			pst.setString(1, idSelect);
			rs = pst.executeQuery();

			if (rs.next()) {
				Turma turma = new Turma(rs.getInt(1), rs.getString(2));

				adm.vt = new ViewTurma(adm, turma);
				textFieldSearch.setText("");
				textFieldSearchID.setText("");
				textFieldFilterDisciplane.setText("");
				scrollPaneFilterDisciplane.setVisible(false);
				listDisciplanes.setVisible(false);
				listagem();
				adm.vt.setVisible(true);
				adm.vtl.setVisible(false);
				adm.setContentPane(adm.vt);
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void retorno(Admin adm) {
		adm.vsl.setVisible(false);
		adm.contentPane.setVisible(true);
		adm.setContentPane(adm.contentPane);
	}

	private void sair(Admin adm) {
		adm.dispose();
		new Login().setVisible(true);
	}

}
