package View.Admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Disciplane;
import Utils.Validador;

public class CdstrDisciplane extends JDialog {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Admin adm;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JScrollPane scrollPane;
	private JTable tableProfessores;

	boolean[] selectedRowsP;
	boolean[] selectedRowsT;
	private JTable tableTurmas;

	/**
	 * Create the dialog.
	 */
	public CdstrDisciplane(Admin adm) {
		setResizable(false);
		setTitle("Cadastro de disciplinas");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CdstrStudent.class.getResource("/img/IF Logo - Remove.png")));
		setBounds(100, 100, 450, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblPag = new JLabel("Cadastro de disciplinas");
		lblPag.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPag.setBounds(10, 0, 436, 30);
		contentPanel.add(lblPag);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNome.setBounds(10, 40, 132, 24);
		contentPanel.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
			}
		});
		textFieldNome.setToolTipText("Cadastre o nome da disciplina");
		lblNome.setLabelFor(textFieldNome);
		textFieldNome.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldNome.setBounds(10, 74, 229, 22);
		contentPanel.add(textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.setDocument(new Validador(80));

		JLabel lblProfessores = new JLabel("Professores *");
		lblProfessores.setToolTipText("Designar professores (opicional)");
		lblProfessores.setFont(new Font("Arial", Font.PLAIN, 15));
		lblProfessores.setBounds(10, 140, 132, 24);
		contentPanel.add(lblProfessores);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 174, 196, 248);
		contentPanel.add(scrollPane);

		tableProfessores = new JTable();
		tableProfessores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (selectedRowsP[tableProfessores.getSelectedRow()]) {
					selectedRowsP[tableProfessores.getSelectedRow()] = false;
				} else {
					selectedRowsP[tableProfessores.getSelectedRow()] = true;
				}

				select();
			}
		});
		tableProfessores.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "New column" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableProfessores.setRowSelectionAllowed(true);
		tableProfessores.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(tableProfessores);

		JLabel lblIF_logo = new JLabel("");
		lblIF_logo.setIcon(new ImageIcon(CdstrTurma.class.getResource("/img/IF Logo - Remove.png")));
		lblIF_logo.setBounds(317, 0, 87, 133);
		contentPanel.add(lblIF_logo);

		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(230, 174, 196, 248);
		contentPanel.add(scrollPane_1);

		tableTurmas = new JTable();
		tableTurmas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {

				if (selectedRowsT[tableTurmas.getSelectedRow()]) {
					selectedRowsT[tableTurmas.getSelectedRow()] = false;
				} else {
					selectedRowsT[tableTurmas.getSelectedRow()] = true;
				}

				select();
			}
		});
		tableTurmas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		tableTurmas.setRowSelectionAllowed(true);
		scrollPane_1.setViewportView(tableTurmas);

		JLabel lblTurmas = new JLabel("Turmas *");
		lblTurmas.setToolTipText("Designar turmas (opicional)");
		lblTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTurmas.setBounds(230, 140, 132, 24);
		contentPanel.add(lblTurmas);

		listar();

		this.adm = adm;
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cdstButton = new JButton("Cadastrar");
				cdstButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						cadastrar();
					}
				});
				buttonPane.add(cdstButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						textFieldNome.setText("");
						
						for (int i = 0; i < selectedRowsP.length; i++) {
							selectedRowsP[i] = false;
						}
						
						for (int i = 0; i < selectedRowsT.length; i++) {
							selectedRowsT[i] = false;
						}
						
						select();
						
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void listar() {
		DefaultTableModel model1 = new DefaultTableModel(new Object[][] {}, new String[] { "", "" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		DefaultTableModel model2 = new DefaultTableModel(new Object[][] {}, new String[] { "" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readProfs = "select nome, matricula from professores order by nome";

		try {
			con = DAO.conectar();

			pst = con.prepareStatement("select count(*) from professores");
			rs = pst.executeQuery();

			if (rs.next()) {
				selectedRowsP = new boolean[rs.getInt(1)];
			}

			pst = con.prepareStatement(readProfs);
			rs = pst.executeQuery();

			for (int i = 0; rs.next(); i++) {
				model1.addRow(new Object[] { rs.getString(1), rs.getString(2) });
				selectedRowsP[i] = false;
			}

			tableProfessores.setModel(model1);

			pst = con.prepareStatement("select count(*) from turmas");
			rs = pst.executeQuery();

			if (rs.next())
				selectedRowsT = new boolean[rs.getInt(1)];

			pst = con.prepareStatement("select nome from turmas order by nome");
			rs = pst.executeQuery();

			for (int i = 0; rs.next(); i++) {
				model2.addRow(new Object[] { rs.getString(1) });
				selectedRowsT[i] = false;
			}

			tableTurmas.setModel(model2);

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void select() {
		for (int i = 0; i < selectedRowsP.length; i++) {
			if (selectedRowsP[i]) {
				tableProfessores.addRowSelectionInterval(i, i);
			} else {
				tableProfessores.removeRowSelectionInterval(i, i);
			}
		}

		for (int i = 0; i < selectedRowsT.length; i++) {
			if (selectedRowsT[i]) {
				tableTurmas.addRowSelectionInterval(i, i);
			} else {
				tableTurmas.removeRowSelectionInterval(i, i);
			}
		}
	}

	private void cadastrar() {
		String nome = "";
		int idDisciplina = 0;

		if (!textFieldNome.getText().isEmpty()) {

			nome = textFieldNome.getText().trim();

			String insert = "insert into disciplinas values " + "(default , ? )";

			try {
				con = DAO.conectar();

				pst = con.prepareStatement("select * from disciplinas where nome= ?");
				pst.setString(1, nome);
				rs = pst.executeQuery();

				if (!rs.next()) {
					pst = con.prepareStatement(insert);
					pst.setString(1, nome);

					int confirma = pst.executeUpdate();

					try {
						if (tableProfessores.getSelectedRows().length > 0) {

							pst = con.prepareStatement("select id from disciplinas where nome= ?");
							pst.setString(1, nome);
							rs = pst.executeQuery();
							if (rs.next()) {
								idDisciplina = rs.getInt(1);
							}

							for (int i = 0; i < tableProfessores.getSelectedRows().length; i++) {
								String matriculaProfessor = (String) tableProfessores
										.getValueAt(tableProfessores.getSelectedRows()[i], 1);

								pst = con.prepareStatement("insert into ensina values (default, ?, ?)");
								pst.setString(1, matriculaProfessor);
								pst.setInt(2, idDisciplina);

								pst.executeUpdate();

								selectedRowsP[tableProfessores.getSelectedRows()[i]] = false;
							}

							select();
						}

						if (tableTurmas.getSelectedRows().length > 0) {

							pst = con.prepareStatement("select id from disciplinas where nome= ?");
							pst.setString(1, nome);
							rs = pst.executeQuery();
							if (rs.next()) {
								idDisciplina = rs.getInt(1);
							}

							for (int i = 0; i < tableTurmas.getSelectedRows().length; i++) {
								String turma = (String) tableTurmas.getValueAt(tableTurmas.getSelectedRows()[i], 0);

								pst = con.prepareStatement("select id from turmas where nome= ?");
								pst.setString(1, turma);
								rs = pst.executeQuery();
								int idTurma = 0;
								if (rs.next()) {
									idTurma = rs.getInt(1);
								}

								pst = con.prepareStatement("insert into estuda values (default, ?, ?)");
								pst.setInt(1, idDisciplina);
								pst.setInt(2, idTurma);

								pst.executeUpdate();

								selectedRowsT[tableTurmas.getSelectedRows()[i]] = false;
							}

							select();
						}

					} catch (Exception e) {
						JOptionPane.showMessageDialog(null,
								"Não foi possivel designar o(s) professor(es) ou a(s) turma(s):" + e);
					}

					if (confirma == 1) {
						JOptionPane.showMessageDialog(null, "Disciplina cadastrada com sucesso!");

					} else {
						JOptionPane.showMessageDialog(null, "Não foi possível fazer o cadastro!");
					}

					adm.listagens();
					adm.status();
					adm.getContentPane().setVisible(false);
					adm.setContentPane(new ViewDisciplane(adm, new Disciplane(idDisciplina, nome)));

					dispose();

					con.close();
				} else {
					JOptionPane.showMessageDialog(null, "Nome da disciplina já cadastrado!\nTente outro nome");
				}
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Não foi possível fazer o cadastro:\n" + e);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos para cadastrar!");
		}
	}
}
