package View.Admin;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Models.Student;
import Utils.DAO;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.Color;
import java.awt.Toolkit;

public class EditStudentTurma extends JDialog {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Student s;
	private Admin adm;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textField;
	private JList<String> list;
	private JScrollPane scrollPane;
	private JButton btnNewButton;
	private JLabel lblIlustration;

	public EditStudentTurma(Student s, Admin adm) {
		this.s = s;
		this.adm = adm;

		setIconImage(
				Toolkit.getDefaultToolkit().getImage(EditStudentTurma.class.getResource("/img/Project_Icon.png")));
		setTitle("Mudar turma do aluno");

		setResizable(false);
		setBounds(100, 100, 450, 300);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		setLocationRelativeTo(null);

		JLabel lblNewLabel = new JLabel("Mudar turma do aluno:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewLabel.setBounds(10, 10, 237, 24);
		contentPanel.add(lblNewLabel);

		textField = new JTextField();
		textField.setBackground(new Color(255, 255, 255));
		textField.setEditable(false);
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				buscar();
			}
		});
		textField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				listar();
			}
		});
		textField.setFont(new Font("Arial", Font.PLAIN, 20));
		textField.setBounds(10, 44, 211, 24);
		contentPanel.add(textField);
		textField.setColumns(10);

		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listar();
			}
		});
		btnNewButton.setIcon(new ImageIcon(EditStudentTurma.class.getResource("/img/seta_de_itens_icon.png")));
		btnNewButton.setBounds(219, 44, 24, 24);
		contentPanel.add(btnNewButton);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 66, 229, 156);
		contentPanel.add(scrollPane);

		list = new JList<String>();
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textField.setText(list.getSelectedValue());
				textField.setEditable(false);

				scrollPane.setVisible(false);
				list.setVisible(false);
				btnNewButton.setIcon(new ImageIcon(EditStudentTurma.class.getResource("/img/seta_de_itens_icon.png")));
			}
		});
		list.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane.setViewportView(list);

		lblIlustration = new JLabel("");
		lblIlustration.setIcon(new ImageIcon(EditStudentTurma.class.getResource("/img/Icon_cadastro.png")));
		lblIlustration.setBounds(253, 53, 173, 169);
		contentPanel.add(lblIlustration);
		scrollPane.setVisible(false);
		list.setVisible(false);

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
		if (scrollPane.isVisible() && list.isVisible()) {
			scrollPane.setVisible(false);
			list.setVisible(false);
			btnNewButton.setIcon(new ImageIcon(EditStudentTurma.class.getResource("/img/seta_de_itens_icon.png")));
			textField.setEditable(false);
			textField.setText("");
			return;
		}

		DefaultListModel<String> model = new DefaultListModel<String>();

		String read = "select nome from turmas";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(read);
			rs = pst.executeQuery();

			while (rs.next()) {
				model.addElement(rs.getString(1));
			}

			list.setModel(model);

			con.close();

			scrollPane.setVisible(true);
			list.setVisible(true);
			btnNewButton.setIcon(
					new ImageIcon(EditStudentTurma.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
			textField.setEditable(true);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar as turmas:\n" + e);
		}
	}

	private void buscar() {
		DefaultListModel<String> model = new DefaultListModel<String>();

		String read = "select nome from turmas where nome like '" + textField.getText().trim() + "%'";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(read);
			rs = pst.executeQuery();

			while (rs.next()) {
				model.addElement(rs.getString(1));
			}

			list.setModel(model);
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar as turmas:\n" + e);
		}
	}

	private void editar() {
		if (!textField.getText().isBlank()) {
			int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja mudar o aluno " + s.getNome()
					+ " da turma " + s.getTurma().getNome() + " para a turma " + textField.getText().trim() + "?");

			if (confirma == JOptionPane.YES_OPTION) {
				String select = "select id from turmas where nome= '" + textField.getText().trim() + "'";

				try {
					con = DAO.conectar();
					pst = con.prepareStatement(select);
					rs = pst.executeQuery();
					if (rs.next()) {
						s.setId_turma(rs.getInt(1));
					} else {
						JOptionPane.showMessageDialog(null, "Turma não encontrada!");
					}

					con.close();

					adm.getContentPane().setVisible(false);
					adm.setContentPane(new ViewStudent(adm, s));
					dispose();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Não foi possivel designar o aluno a turma:\n" + e);
				}
			}
		} else {
			JOptionPane.showMessageDialog(null, "Escolha uma turma!");
		}
	}
}
