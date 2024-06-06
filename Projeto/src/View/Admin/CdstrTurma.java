package View.Admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Models.Turma;
import Utils.DAO;
import Utils.Validador;

public class CdstrTurma extends JDialog {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Admin adm;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JScrollPane scrollPane;
	private JTable tableDisciplinas;

	boolean[] selectedRows;

	public CdstrTurma(Admin adm) {
		this.adm = adm;

		setResizable(false);
		setTitle("Cadastro de turmas");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CdstrStudent.class.getResource("/img/IF Logo - Remove.png")));
		setBounds(100, 100, 450, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblPag = new JLabel("Cadastro de Turmas");
		lblPag.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPag.setBounds(10, 0, 436, 30);
		contentPanel.add(lblPag);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNome.setBounds(10, 40, 132, 24);
		contentPanel.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setToolTipText("Cadastre o nome da turma");
		lblNome.setLabelFor(textFieldNome);
		textFieldNome.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldNome.setBounds(10, 74, 229, 22);
		contentPanel.add(textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.setDocument(new Validador(20));

		JLabel lblDisciplina = new JLabel("Disciplinas *");
		lblDisciplina.setToolTipText("Designar diciplinas (opicional)");
		lblDisciplina.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDisciplina.setBounds(10, 106, 132, 24);
		contentPanel.add(lblDisciplina);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 140, 229, 248);
		contentPanel.add(scrollPane);

		tableDisciplinas = new JTable();
		tableDisciplinas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (selectedRows[tableDisciplinas.getSelectedRow()]) {
					selectedRows[tableDisciplinas.getSelectedRow()] = false;
				} else {
					selectedRows[tableDisciplinas.getSelectedRow()] = true;
				}

				selectDisciplinas();
			}
		});
		tableDisciplinas.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "New column" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDisciplinas.setRowSelectionAllowed(true);
		tableDisciplinas.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		scrollPane.setViewportView(tableDisciplinas);

		JLabel lblDesenho = new JLabel("");
		lblDesenho.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesenho.setIcon(new ImageIcon(CdstrTurma.class.getResource("/img/Class_Icon.png")));
		lblDesenho.setBounds(249, 188, 187, 187);
		contentPanel.add(lblDesenho);

		JLabel lblIF_logo = new JLabel("");
		lblIF_logo.setIcon(new ImageIcon(CdstrTurma.class.getResource("/img/IF Logo - Remove.png")));
		lblIF_logo.setBounds(317, 0, 87, 133);
		contentPanel.add(lblIF_logo);

		listarDisciplinas();

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
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}

	private void listarDisciplinas() {
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readDisciplinas = "select nome from disciplinas order by nome";

		try {
			con = DAO.conectar();

			pst = con.prepareStatement("select count(*) from disciplinas");
			rs = pst.executeQuery();

			if (rs.next()) {
				selectedRows = new boolean[rs.getInt(1)];
			}

			pst = con.prepareStatement(readDisciplinas);
			rs = pst.executeQuery();

			for (int i = 0; rs.next(); i++) {
				model.addRow(new Object[] { rs.getString(1) });
				selectedRows[i] = false;
			}

			tableDisciplinas.setModel(model);

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel listar as disciplinas:\n" + e);
		}
	}

	private void selectDisciplinas() {
		for (int i = 0; i < selectedRows.length; i++) {
			if (selectedRows[i]) {
				tableDisciplinas.addRowSelectionInterval(i, i);
			} else {
				tableDisciplinas.removeRowSelectionInterval(i, i);
			}
		}
	}

	private void cadastrar() {

		if (!textFieldNome.getText().isBlank()) {

			String nome = textFieldNome.getText().trim();
			int idTurma = 0;

			String insert = "insert into turmas values " + "(default , ? )";

			try {
				con = DAO.conectar();

				pst = con.prepareStatement("select * from turmas where nome= ?");
				pst.setString(1, nome);
				rs = pst.executeQuery();

				if (!rs.next()) {
					pst = con.prepareStatement(insert);
					pst.setString(1, nome);

					int confirma = pst.executeUpdate();

					pst = con.prepareStatement("select id from turmas where nome= ?");
					pst.setString(1, nome);
					rs = pst.executeQuery();
					if (rs.next())
						idTurma = rs.getInt(1);

					for (int i = 0; i < tableDisciplinas.getSelectedRows().length; i++) {
						String disciplina = (String) tableDisciplinas.getValueAt(tableDisciplinas.getSelectedRows()[i],
								0);

						pst = con.prepareStatement(
								"insert into estuda values (default, (select id from disciplinas where nome= ?), ?)");
						pst.setString(1, disciplina);
						pst.setInt(2, idTurma);

						pst.execute();
					}

					if (confirma == 1 && tableDisciplinas.getSelectedRows().length == 0) {
						JOptionPane.showMessageDialog(null, "Turma cadastrada com sucesso!");
					} else {
						JOptionPane.showMessageDialog(null, "Não foi possível fazer o cadastro!");
					}

					Turma turma = new Turma(idTurma, nome);

					adm.listagens();
					adm.status();
					adm.getContentPane().setVisible(false);
					adm.setContentPane(new ViewTurma(adm, turma));

					dispose();

				} else {
					JOptionPane.showMessageDialog(null, "Nome da turma já cadastrado!\nTente outro nome");
				}

				con.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Não foi possível fazer o cadastro:\n" + e);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos para cadastrar!");
		}
	}
}
