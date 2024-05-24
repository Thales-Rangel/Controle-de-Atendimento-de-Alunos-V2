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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.SwingConstants;

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

		JLabel lblTitulo = new JLabel("Alterar dados pessoais");
		lblTitulo.setFont(new Font("Arial Black", Font.PLAIN, 25));

		JLabel lblNome = new JLabel("Nome: ");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));

		JLabel lblSenha = new JLabel("Senha antiga: ");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 20));

		JButton btnEdit = new JButton("Alterar dados");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				atualizar();
			}
		});
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 15));

		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (passwordField.getText().isBlank()) {
						passwordField.requestFocus();
					} else if (newPasswordField.getText().isBlank()) {
						newPasswordField.requestFocus();
					} else if (RepeatPasswordField.getText().isBlank()) {
						RepeatPasswordField.requestFocus();
					} else {
						atualizar();
					}
				}
			}
		});
		textField.setFont(new Font("Arial", Font.PLAIN, 20));
		textField.setColumns(10);
		textField.setDocument(new Validador(30));
		textField.setText(p.getNome());

		JLabel lblNewSenha = new JLabel("Nova senha:");
		lblNewSenha.setFont(new Font("Arial", Font.PLAIN, 20));

		JLabel lblRepeatNewSenha = new JLabel("Repetir nova senha:");
		lblRepeatNewSenha.setFont(new Font("Arial", Font.PLAIN, 20));

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (newPasswordField.getText().isBlank()) {
						newPasswordField.requestFocus();
					} else if (RepeatPasswordField.getText().isBlank()) {
						RepeatPasswordField.requestFocus();
					} else if (textField.getText().isBlank()) {
						textField.requestFocus();
					} else {
						atualizar();
					}
				}
			}
		});
		passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
		passwordField.setDocument(new Validador(100));

		newPasswordField = new JPasswordField();
		newPasswordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (RepeatPasswordField.getText().isBlank()) {
						RepeatPasswordField.requestFocus();
					} else if (textField.getText().isBlank()) {
						textField.requestFocus();
					} else if (passwordField.getText().isBlank()) {
						passwordField.requestFocus();
					} else {
						atualizar();
					}
				}
			}
		});
		newPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));
		newPasswordField.setDocument(new Validador(100));

		RepeatPasswordField = new JPasswordField();
		RepeatPasswordField.addKeyListener(new KeyAdapter() {
			@SuppressWarnings("deprecation")
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textField.getText().isBlank()) {
						textField.requestFocus();
					} else if (passwordField.getText().isBlank()) {
						passwordField.requestFocus();
					} else if (newPasswordField.getText().isBlank()) {
						newPasswordField.requestFocus();
					} else {
						atualizar();
					}
				}
			}
		});
		RepeatPasswordField.setFont(new Font("Arial", Font.PLAIN, 20));

		JLabel lblIMG = new JLabel("");
		lblIMG.setHorizontalAlignment(SwingConstants.CENTER);
		lblIMG.setIcon(new ImageIcon(AlterDatas.class.getResource("/img/TonyStark.png")));

		JButton btnCancel = new JButton("Cancelar");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				pv.viewPanel.setVisible(false);
				pv.viewPanel = new ViewDatas(pv);
				pv.contentPane.add(pv.viewPanel);
				pv.dimensionar();
			}
		});
		btnCancel.setFont(new Font("Arial", Font.PLAIN, 15));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblTitulo, GroupLayout.DEFAULT_SIZE, 465, Short.MAX_VALUE)
					.addGap(162))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)
					.addGap(22)
					.addComponent(textField, GroupLayout.PREFERRED_SIZE, 339, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewSenha, GroupLayout.PREFERRED_SIZE, 141, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(newPasswordField, GroupLayout.PREFERRED_SIZE, 291, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblRepeatNewSenha, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(RepeatPasswordField, GroupLayout.PREFERRED_SIZE, 233, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(4)
					.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 147, GroupLayout.PREFERRED_SIZE))
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblIMG, GroupLayout.DEFAULT_SIZE, 623, Short.MAX_VALUE)
					.addGap(4))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblTitulo)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblSenha, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(passwordField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNewSenha, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(newPasswordField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblRepeatNewSenha, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addComponent(RepeatPasswordField, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(btnEdit, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnCancel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE))
					.addGap(26)
					.addComponent(lblIMG, GroupLayout.PREFERRED_SIZE, 385, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

	@SuppressWarnings("deprecation")
	private void atualizar() {
		if (!passwordField.getText().isBlank() && passwordField.getText().trim().equals(p.getSenha())) {
			if (!((textField.getText().isBlank() || textField.getText().trim().equals(p.getNome())) && newPasswordField.getText().isBlank())) {
				int confirma = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja mudar esses dados?");

				if (confirma == JOptionPane.YES_OPTION) {

					if (!(textField.getText().isBlank() || textField.getText().trim().equals(p.getNome()))) {
						String newNome = textField.getText().trim();

						p.setNome(newNome);
					}

					if (!newPasswordField.getText().isBlank() && newPasswordField.getText().trim().equals(RepeatPasswordField.getText().trim())) {
						String newSenha = newPasswordField.getText().trim();

						p.setSenha(newSenha);

					} else if (!newPasswordField.getText().trim().equals(RepeatPasswordField.getText().trim())) {
						JOptionPane.showMessageDialog(null, "A nova senha não foi repetida corretamente!");
					}
					
					if (newPasswordField.getText().isBlank() || newPasswordField.getText().trim().equals(RepeatPasswordField.getText().trim())) {
						pv.viewPanel.setVisible(false);
						pv.viewPanel = new ViewDatas(pv);
						pv.contentPane.add(pv.viewPanel);
						
						pv.dimensionar();
					}
				}
			} else {
				JOptionPane.showMessageDialog(null, "Tu não vai editar nada?\nColoque um novo nome ou uma nova senha pelo menos!");
			}
		} else {
			JOptionPane.showMessageDialog(null,
					"Senha antiga está incorreta ou então não foi digitada!\nPor favor digite sua senha antiga para poder confirmar sua atenticidade!");
		}
	}

}
