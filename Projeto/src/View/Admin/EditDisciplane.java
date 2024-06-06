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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Models.Disciplane;
import Utils.DAO;
import Utils.Validador;

public class EditDisciplane extends JDialog {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Admin adm;
	private Disciplane d;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JScrollPane scrollPane;
	private JTable tableProfessores;

	boolean[] selectedRowsP;
	boolean[] selectedRowsT;
	private JTable tableTurmas;

	public EditDisciplane(Admin adm, Disciplane d) {
		this.adm = adm;
		this.d = d;

		setResizable(false);
		setTitle("Editar disciplina");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CdstrStudent.class.getResource("/img/IF Logo - Remove.png")));
		setBounds(100, 100, 450, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblPag = new JLabel("Editar disciplina");
		lblPag.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPag.setBounds(10, 0, 436, 30);
		contentPanel.add(lblPag);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNome.setBounds(10, 40, 132, 24);
		contentPanel.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setToolTipText("Altere o nome da disciplina");
		lblNome.setLabelFor(textFieldNome);
		textFieldNome.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldNome.setBounds(10, 74, 229, 22);
		contentPanel.add(textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.setDocument(new Validador(80));
		textFieldNome.setText(d.getNome());

		JLabel lblProfessores = new JLabel("Professores");
		lblProfessores.setToolTipText("Designar professores");
		lblProfessores.setFont(new Font("Arial", Font.PLAIN, 15));
		lblProfessores.setBounds(10, 140, 132, 24);
		contentPanel.add(lblProfessores);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 174, 196, 248);
		contentPanel.add(scrollPane);

		tableProfessores = new JTable();
		tableProfessores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (selectedRowsP[tableProfessores.getSelectedRow()]) {
					selectedRowsP[tableProfessores.getSelectedRow()] = false;
				} else {
					selectedRowsP[tableProfessores.getSelectedRow()] = true;
				}
				select();
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

		JLabel lblTurmas = new JLabel("Turmas");
		lblTurmas.setToolTipText("Designar turmas");
		lblTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTurmas.setBounds(230, 140, 132, 24);
		contentPanel.add(lblTurmas);

		listar();

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton cdstButton = new JButton("Editar");
				cdstButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						atualizar();
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

	public void listar() {
		DefaultTableModel model1 = new DefaultTableModel(new Object[][] {}, new String[] { "", "" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		DefaultTableModel model2 = new DefaultTableModel(new Object[][] {}, new String[] { "" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readProfs = "select nome, matricula from professores order by nome";

		String readEnsina = "select * from ensina where id_disciplina = " + d.getId() + " and matricula_professor= ?";

		String readEstuda = "select * from estuda where id_disciplina = " + d.getId() + " and id_turma= ?";
		try {
			con = DAO.conectar();

			pst = con.prepareStatement("select count(*) from professores");
			rs = pst.executeQuery();

			if (rs.next())
				selectedRowsP = new boolean[rs.getInt(1)];

			pst = con.prepareStatement(readProfs);
			rs = pst.executeQuery();

			for (int i = 0; rs.next(); i++) {
				model1.addRow(new Object[] { rs.getString(1), rs.getString(2) });

				pst = con.prepareStatement(readEnsina);
				pst.setString(1, rs.getString(2));
				ResultSet verifica = pst.executeQuery();

				if (verifica.next()) {
					selectedRowsP[i] = true;
				} else {
					selectedRowsP[i] = false;
				}
			}

			tableProfessores.setModel(model1);

			pst = con.prepareStatement("select count(*) from turmas");
			rs = pst.executeQuery();

			if (rs.next())
				selectedRowsT = new boolean[rs.getInt(1)];

			pst = con.prepareStatement("select * from turmas order by nome");
			rs = pst.executeQuery();

			for (int i = 0; rs.next(); i++) {
				model2.addRow(new Object[] { rs.getString(2) });

				pst = con.prepareStatement(readEstuda);
				pst.setInt(1, rs.getInt(1));
				ResultSet verifica = pst.executeQuery();

				if (verifica.next()) {
					selectedRowsT[i] = true;
				} else {
					selectedRowsT[i] = false;
				}
			}

			tableTurmas.setModel(model2);

			select();

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel listar:\n" + e);
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

	private void atualizar() {
		try {
			con = DAO.conectar();

			pst = con.prepareStatement("delete from ensina where id_disciplina= ?");
			pst.setInt(1, d.getId());
			pst.execute();

			pst = con.prepareStatement("delete from estuda where id_disciplina= ?");
			pst.setInt(1, d.getId());
			pst.execute();

			if (!textFieldNome.getText().isBlank() && !textFieldNome.getText().equals(d.getNome())) {
				String nome = textFieldNome.getText().trim();

				pst = con.prepareStatement("select * from disciplinas where nome= ?");
				pst.setString(1, nome);
				rs = pst.executeQuery();

				if (!rs.next()) {
					d.setNome(nome);
				} else {
					JOptionPane.showMessageDialog(null, "Esse nome de disciplina já foi usado!\nTente outro nome");
				}
			}

			for (int i = 0; i < tableProfessores.getSelectedRows().length; i++) {
				String matriculaProfessor = (String) tableProfessores.getValueAt(tableProfessores.getSelectedRows()[i],
						1);

				pst = con.prepareStatement("insert into ensina values (default, ?, ?)");
				pst.setString(1, matriculaProfessor);
				pst.setInt(2, d.getId());
				pst.execute();
			}

			for (int i = 0; i < tableTurmas.getSelectedRows().length; i++) {
				String turma = (String) tableTurmas.getValueAt(tableTurmas.getSelectedRows()[i], 0);

				pst = con.prepareStatement(
						"insert into estuda values (default, ?, (select id from turmas where nome= ?))");
				pst.setInt(1, d.getId());
				pst.setString(2, turma);
				pst.execute();
			}
			con.close();

			adm.listagens();
			adm.getContentPane().setVisible(false);
			adm.setContentPane(new ViewDisciplane(adm, d));

			dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possível atualizar a disciplina:\n" + e);
		}
	}

}
