package View.Professor;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Models.Professor;
import Utils.Validador;
import javax.swing.ImageIcon;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class AlterDatas extends JPanel {

	private ProfessorView pv;
	private Professor p;

	private static final long serialVersionUID = 1L;
	private JTextField textField;
	private JPasswordField passwordField;
	private JPasswordField newPasswordField;
	private JPasswordField RepeatPasswordField;

	/**
	 * Create the panel.
	 */
	public AlterDatas(ProfessorView pv) {
		this.pv = pv;
		this.p = pv.prof;

		setBounds(0, 0, 637, 593);
		setLayout(null);

		JLabel lblTitulo = new JLabel("Alterar dados pessoais");
		lblTitulo.setFont(new Font("Arial Black", Font.PLAIN, 25));
		lblTitulo.setBounds(10, 10, 465, 39);
		add(lblTitulo);

		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNome.setBounds(10, 59, 81, 28);
		add(lblNome);

		JLabel lblSenha = new JLabel("Senha antiga: ");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		lblSenha.setBounds(10, 97, 141, 28);

		add(lblSenha);

		JButton btnEdit = new JButton("Alterar dados");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
			}
		});
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 15));
		btnEdit.setBounds(4, 211, 147, 39);
		add(btnEdit);

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (passwordField.getText().trim().isEmpty()) {
						passwordField.requestFocus();
					} else if (newPasswordField.getText().trim().isEmpty()) {
						newPasswordField.requestFocus();
					} else if (RepeatPasswordField.getText().trim().isEmpty()) {
						RepeatPasswordField.requestFocus();
					} else {
						atualizar();
					}
				}
			}
		});
		textField.setFont(new Font("Arial", Font.PLAIN, 20));
		textField.setBounds(113, 59, 339, 28);
		add(textField);
		textField.setColumns(10);
		textField.setDocument(new Validador(30));
		textField.setText(p.getNome());

		JLabel lblNewSenha = new JLabel("Nova senha:");
		lblNewSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNewSenha.setBounds(10, 135, 141, 28);
		add(lblNewSenha);

		JLabel lblRepeatNewSenha = new JLabel("Repetir nova senha:");
		lblRepeatNewSenha.setFont(new Font("Arial", Font.PLAIN, 20));
		lblRepeatNewSenha.setBounds(10, 173, 199, 28);
		add(lblRepeatNewSenha);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (newPasswordField.getText().trim().isEmpty()) {
						newPasswordField.requestFocus();
					} else if (RepeatPasswordField.getText().trim().isEmpty()) {
						RepeatPasswordField.requestFocus();
					} else if (textField.getText().trim().isEmpty()) {
						textField.requestFocus();
					} else {
						atualizar();
					}
				}
			}
		});
		passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
		passwordField.setBounds(161, 97, 291, 28);
		add(passwordField);
		passwordField.setDocument(new Validador(100));

		newPasswordField = new JPasswordField();
		newPasswordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (RepeatPasswordField.getText().trim().isEmpty()) {
						RepeatPasswordField.requestFocus();
					} else if (textField.getText().trim().isEmpty()) {
						textField.requestFocus();
					} else if (passwordField.getText().trim().isEmpty()) {
						passwordField.requestFocus();
					} else {
						atualizar();
					}
				}
			}
		});
		newPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));
		newPasswordField.setBounds(161, 135, 291, 28);
		add(newPasswordField);
		newPasswordField.setDocument(new Validador(100));

		RepeatPasswordField = new JPasswordField();
		RepeatPasswordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textField.getText().trim().isEmpty()) {
						textField.requestFocus();
					} else if (passwordField.getText().trim().isEmpty()) {
						passwordField.requestFocus();
					} else if (newPasswordField.getText().trim().isEmpty()) {
						newPasswordField.requestFocus();
					} else {
						atualizar();
					}
				}
			}
		});
		RepeatPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));
		RepeatPasswordField.setBounds(219, 173, 233, 28);
		add(RepeatPasswordField);

		JLabel lblIMG = new JLabel("");
		lblIMG.setIcon(new ImageIcon(AlterDatas.class.getResource("/img/TonyStark.png")));
		lblIMG.setBounds(10, 276, 623, 385);
		add(lblIMG);

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.viewPanel.setVisible(false);
				pv.viewPanel = new ViewDatas(pv);
				pv.contentPane.add(pv.viewPanel);
			}
		});
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 15));
		btnCancel.setBounds(161, 211, 147, 39);
		add(btnCancel);

	}

	@SuppressWarnings("deprecation")
	private void atualizar() {
		if (!passwordField.getText().trim().isEmpty() && passwordField.getText().trim().equals(p.getSenha())) {
			if (!((textField.getText().trim().isEmpty() || textField.getText().trim().equals(p.getNome()))
					&& newPasswordField.getText().trim().isEmpty())) {
				int confirma = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja mudar esses dados?");

				if (confirma == JOptionPane.YES_OPTION) {

					if (!(textField.getText().trim().isEmpty() && textField.getText().trim().equals(p.getNome()))) {
						String newNome = textField.getText().trim();

						p.setNome(newNome);
					}

					if (!newPasswordField.getText().trim().isEmpty() && newPasswordField.getText().trim().equals(RepeatPasswordField.getText().trim())) {
						String newSenha = newPasswordField.getText().trim();

						p.setSenha(newSenha);

					} else if (!newPasswordField.getText().trim().equals(RepeatPasswordField.getText().trim())) {
						JOptionPane.showMessageDialog(null, "A nova senha não foi repetida corretamente!");
					}
					
					if (newPasswordField.getText().trim().isEmpty() || newPasswordField.getText().trim().equals(RepeatPasswordField.getText().trim())) {
						pv.viewPanel.setVisible(false);
						pv.viewPanel = new ViewDatas(pv);
						pv.contentPane.add(pv.viewPanel);
					}
				}
			} else {
				JOptionPane.showMessageDialog(null,
						"Tu não vai editar nada?\nColoque um novo nome ou uma nova senha pelo menos!");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Senha antiga está incorreta ou então não foi digitada!\nPor favor digite sua senha antiga para poder confirmar sua atenticidade!");
		}
	}

}
