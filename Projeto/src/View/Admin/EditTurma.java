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

public class EditTurma extends JDialog {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Admin adm;
	private Turma t;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JScrollPane scrollPane;
	private JTable tableDisciplinas;

	boolean[] selectedRows;

	public EditTurma(Admin adm, Turma t) {
		this.adm = adm;
		this.t = t;

		setResizable(false);
		setTitle("Editar Turma");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CdstrStudent.class.getResource("/img/IF Logo - Remove.png")));
		setBounds(100, 100, 450, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblPag = new JLabel(" Editar turmas");
		lblPag.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPag.setBounds(10, 0, 229, 30);
		contentPanel.add(lblPag);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNome.setBounds(10, 40, 132, 24);
		contentPanel.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.setToolTipText("Cadastre o nome do(a) professor(a)");
		textFieldNome.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldNome.setBounds(10, 74, 229, 22);
		contentPanel.add(textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.setDocument(new Validador(20));
		textFieldNome.setText(t.getNome());

		JLabel lblDisciplina = new JLabel("Designar disciplinas disciplinas");
		lblDisciplina.setToolTipText("Designar diciplinas");
		lblDisciplina.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDisciplina.setBounds(10, 106, 229, 24);
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
				JButton cdstButton = new JButton("Editar Turma");
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

	public void listarDisciplinas() {
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readDisciplinas = "select * from disciplinas order by nome";
		
		String readEstuda = "select id_disciplina from estuda where id_turma= " + t.getId() + " and id_disciplina= ?";

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
				model.addRow(new Object[] { rs.getString(2) });

				pst = con.prepareStatement(readEstuda);
				pst.setInt(1, rs.getInt(1));
				ResultSet verifica = pst.executeQuery();

				if (verifica.next()) {
					selectedRows[i] = true;
				} else {
					selectedRows[i] = false;
				}
			}

			tableDisciplinas.setModel(model);

			selectDisciplinas();

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
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

	private void atualizar() {
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement("delete from estuda where id_turma= " + t.getId());
			pst.execute();
			
			if (!textFieldNome.getText().isBlank() && !textFieldNome.getText().equals(t.getNome())) {
				String novoNome = textFieldNome.getText().trim();

				pst = con.prepareStatement("select * from turmas where nome= ?");
				pst.setString(1, novoNome);
				rs = pst.executeQuery();

				if (!rs.next()) {
					t.setNome(novoNome);
				} else {
					JOptionPane.showMessageDialog(null, "Esse nome já foi usado!\nTente outro nome");
				}
			}

			if (tableDisciplinas.getSelectedRows().length > 0) {
				
				for (int i = 0; i < tableDisciplinas.getSelectedRows().length; i++) {
					String disciplina = (String) tableDisciplinas.getValueAt(tableDisciplinas.getSelectedRows()[i], 0);

					pst = con.prepareStatement("insert into estuda values (default, (select id from disciplinas where nome= ?), ?)");
					pst.setString(1, disciplina);
					pst.setInt(2, t.getId());

					pst.execute();

					selectedRows[tableDisciplinas.getSelectedRows()[i]] = false;
				}
			}
			con.close();

			t.atualizaDisciplinas();
			adm.getContentPane().setVisible(false);
			adm.setContentPane(new ViewTurma(adm, t));
			adm.listagens();
			
			dispose();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possível fazer o cadastro:\n" + e);
		}
	}

}
