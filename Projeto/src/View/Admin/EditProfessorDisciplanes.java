package View.Admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Models.Professor;
import Utils.DAO;

import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Toolkit;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class EditProfessorDisciplanes extends JDialog {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Professor p;
	private Admin adm;

	private boolean[] selecteds;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTable table;

	public EditProfessorDisciplanes(Professor p, Admin adm) {
		this.p = p;
		this.adm = adm;

		setIconImage(Toolkit.getDefaultToolkit()
				.getImage(EditProfessorDisciplanes.class.getResource("/img/IF Logo - Remove.png")));
		setTitle("Editar disciplinas do professor");
		setResizable(false);

		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		setLocationRelativeTo(null);
		{
			JLabel lblNewLabel = new JLabel("Editar disciplinas do professor:");
			lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
			lblNewLabel.setBounds(10, 10, 285, 24);
			contentPanel.add(lblNewLabel);
		}
		{
			JScrollPane scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 44, 250, 178);
			contentPanel.add(scrollPane);
			{
				table = new JTable();
				table.addMouseListener(new MouseAdapter() {
					public void mouseClicked(MouseEvent e) {
						if (selecteds[table.getSelectedRow()]) {
							selecteds[table.getSelectedRow()] = false;
						} else {
							selecteds[table.getSelectedRow()] = true;
						}

						select();
					}
				});
				table.setFont(new Font("Arial", Font.PLAIN, 15));
				scrollPane.setViewportView(table);
			}
		}
		{
			JLabel Ilustration = new JLabel("");
			Ilustration.setIcon(new ImageIcon(EditProfessorDisciplanes.class.getResource("/img/Professor_Icon.png")));
			Ilustration.setBounds(270, 35, 156, 187);
			contentPanel.add(Ilustration);
		}

		listar();

		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						editar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancel");
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

	private void listar() {
		DefaultTableModel model = new DefaultTableModel(new Object[][] {}, new String[] { "" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String read = "select nome from disciplinas";

		String readEnsina = "select * from ensina where matricula_professor = '" + p.getMatricula() + "' "
				+ "and id_disciplina= (select id from disciplinas where nome= ?)";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement("select count(*) from disciplinas");
			rs = pst.executeQuery();
			if (rs.next()) {
				selecteds = new boolean[rs.getInt(1)];
			}

			pst = con.prepareStatement(read);
			rs = pst.executeQuery();

			for (int i = 0; rs.next(); i++) {
				model.addRow(new Object[] { rs.getString(1) });

				pst = con.prepareStatement(readEnsina);
				pst.setString(1, rs.getString(1));
				ResultSet verifica = pst.executeQuery();
				if (verifica.next()) {
					selecteds[i] = true;
				} else {
					selecteds[i] = false;
				}
			}

			table.setModel(model);

			select();

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel listar as disciplinas:\n" + e);
		}
	}

	private void select() {
		for (int i = 0; i < selecteds.length; i++) {
			if (selecteds[i]) {
				table.addRowSelectionInterval(i, i);
			} else {
				table.removeRowSelectionInterval(i, i);
			}
		}
	}

	private void editar() {
		try {
			String deleteEnsina = "delete from ensina where matricula_professor= '" + p.getMatricula() + "'";

			String insert = "insert into ensina values (default,'" + p.getMatricula()
					+ "', (select id from disciplinas where nome= ?))";

			con = DAO.conectar();
			pst = con.prepareStatement(deleteEnsina);
			pst.execute();

			for (int i = 0; i < table.getSelectedRows().length; i++) {
				pst = con.prepareStatement(insert);
				pst.setString(1, (String) table.getValueAt(table.getSelectedRows()[i], 0));
				pst.execute();
			}
			con.close();

			p.atualizaDisciplinas();

			adm.getContentPane().setVisible(false);
			adm.setContentPane(new ViewProfessor(adm, p));
			dispose();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(adm, "Não foi possivel editar as disciplinas do professor:\n" + e);
		}
	}

}
