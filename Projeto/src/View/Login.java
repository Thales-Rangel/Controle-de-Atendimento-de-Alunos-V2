package View;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Models.DAO;
import Utils.Validador;
import View.Admin.Admin;
import View.Professor.ProfessorView;
import View.Student.StudentView;

public class Login extends JFrame {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textMatricula;
	private JPasswordField passwordField;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login frame = new Login();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Login() {
		setResizable(false);
		setIconImage(Toolkit.getDefaultToolkit().getImage(Login.class.getResource("/img/IF Logo - Remove.png")));
		setTitle("Login");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblMatricula = new JLabel("Matrícula");
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMatricula.setBounds(10, 31, 61, 18);
		contentPane.add(lblMatricula);

		textMatricula = new JTextField();
		textMatricula.addKeyListener(new KeyAdapter() {
			@Override
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					passwordField.requestFocus();
				}
			}
		});
		lblMatricula.setLabelFor(textMatricula);
		textMatricula.setFont(new Font("Arial", Font.PLAIN, 20));
		textMatricula.setBounds(10, 59, 252, 27);
		contentPane.add(textMatricula);
		textMatricula.setColumns(10);
		textMatricula.setDocument(new Validador(20));

		JLabel lblNewLabel = new JLabel("Senha");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNewLabel.setBounds(10, 96, 61, 18);
		contentPane.add(lblNewLabel);

		passwordField = new JPasswordField();
		passwordField.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!textMatricula.getText().isEmpty()) {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						entrar();
					}
				} else {
					if (e.getKeyCode() == KeyEvent.VK_ENTER) {
						textMatricula.requestFocus();
					}
				}
			}
		});
		lblNewLabel.setLabelFor(passwordField);
		passwordField.setToolTipText("Senha");
		passwordField.setFont(new Font("Arial", Font.PLAIN, 20));
		passwordField.setColumns(10);
		passwordField.setBounds(10, 124, 252, 27);
		passwordField.setDocument(new Validador(100));

		contentPane.add(passwordField);

		JButton btnNewButton = new JButton("Entrar");
		btnNewButton.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			public void actionPerformed(ActionEvent e) {
				if (textMatricula.getText().isEmpty() || passwordField.getText().isEmpty()) {
					JOptionPane.showMessageDialog(null, "Preencha os campos de nome e senha!");
				} else {
					entrar();
				}
			}
		});
		btnNewButton.setFont(new Font("Arial", Font.PLAIN, 15));
		btnNewButton.setBounds(78, 184, 112, 27);
		contentPane.add(btnNewButton);

		JLabel lblIcon = new JLabel("");
		lblIcon.setIcon(new ImageIcon(Login.class.getResource("/img/Student_icon.png")));
		lblIcon.setBounds(298, 42, 128, 128);
		contentPane.add(lblIcon);
	}

	private void entrar() {
		String matricula = textMatricula.getText();
		@SuppressWarnings("deprecation")
		String senha = passwordField.getText();

		String insert = "select * from professores where matricula = ? ";

		if (!(matricula.equals("0001") && senha.equals("admin"))) {
			try {
				con = DAO.conectar();
				pst = con.prepareStatement(insert);
				pst.setString(1, matricula);

				rs = pst.executeQuery();
				if (rs.next()) {
					if (rs.getString(3).equals(senha)) {
						JOptionPane.showMessageDialog(null, rs.getString(1));
						dispose();
						new ProfessorView().setVisible(true);
					} else {
						JOptionPane.showMessageDialog(null, "Senha incorreta!");
					}
				} else {
					insert = "select * from alunos where matricula = ?";
					pst = con.prepareStatement(insert);
					pst.setString(1, matricula);

					rs = pst.executeQuery();
					if (rs.next()) {
						if (rs.getString(3).equals(senha)) {
							JOptionPane.showMessageDialog(null, rs.getString(1));
							dispose();
							new StudentView().setVisible(true);
						} else {
							JOptionPane.showMessageDialog(null, "Senha incorreta!");
						}
					} else {
						JOptionPane.showMessageDialog(null, "Matrícula não encontrada!");
					}
				}

				con.close();
			} catch (Exception e) {
				JOptionPane.showMessageDialog(null, "Não foi possivel fazer o login:\n"+e);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Admin");
			dispose();
			new Admin().setVisible(true);
		}
	}
}
