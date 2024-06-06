package View.Admin;

import java.awt.BorderLayout;
import java.awt.Color;
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
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import Models.Professor;
import Utils.DAO;
import Utils.Validador;
import java.awt.Cursor;

public class CdstrProfessor extends JDialog {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Admin adm;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JTextField textFieldMatricula;
	private JTextField textFieldSenha;
	private JTextField textFieldDisciplina;
	private JList<String> listDisciplinas;
	private JScrollPane scrollPane;
	private JButton btnDisciplinas;

	public CdstrProfessor(Admin adm) {
		this.adm = adm;

		setResizable(false);
		setTitle("Cadastro de professores");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CdstrStudent.class.getResource("/img/IF Logo - Remove.png")));
		setBounds(100, 100, 450, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);

		JLabel lblPag = new JLabel("Cadastro de professores");
		lblPag.setFont(new Font("Arial", Font.PLAIN, 18));
		lblPag.setBounds(10, 0, 436, 30);
		contentPanel.add(lblPag);

		JLabel lblNome = new JLabel("Nome");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNome.setBounds(10, 40, 132, 24);
		contentPanel.add(lblNome);

		textFieldNome = new JTextField();
		textFieldNome.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textFieldMatricula.getText().isEmpty()) {
						textFieldMatricula.requestFocus();
					} else if (textFieldSenha.getText().isBlank()) {
						textFieldSenha.requestFocus();
					} else {
						cadastrar();
					}
				}
			}
		});
		textFieldNome.setToolTipText("Cadastre o nome do(a) professor(a)");
		lblNome.setLabelFor(textFieldNome);
		textFieldNome.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldNome.setBounds(10, 74, 229, 22);
		contentPanel.add(textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.setDocument(new Validador(30));

		JLabel lblMatricula = new JLabel("Matricula");
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 15));
		lblMatricula.setBounds(10, 114, 132, 24);
		contentPanel.add(lblMatricula);

		textFieldMatricula = new JTextField();
		textFieldMatricula.addKeyListener(new KeyAdapter() {
			public void keyTyped(KeyEvent e) {
				String caracteres = "0123456789";

				if (!caracteres.contains(e.getKeyChar() + "")) {
					e.consume();
				}
			}

			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textFieldSenha.getText().isBlank()) {
						textFieldSenha.requestFocus();
					} else if (textFieldNome.getText().isBlank()) {
						textFieldNome.requestFocus();
					} else {
						cadastrar();
					}
				}
			}
		});
		textFieldMatricula.setToolTipText("Cadastre a matricula do(a) professor(a)");
		lblMatricula.setLabelFor(textFieldMatricula);
		textFieldMatricula.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldMatricula.setColumns(10);
		textFieldMatricula.setBounds(10, 148, 229, 22);
		textFieldMatricula.setDocument(new Validador(8));
		contentPanel.add(textFieldMatricula);

		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSenha.setBounds(10, 190, 132, 24);
		contentPanel.add(lblSenha);

		textFieldSenha = new JTextField();
		textFieldSenha.addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_ENTER) {
					if (textFieldMatricula.getText().isEmpty()) {
						textFieldMatricula.requestFocus();
					} else if (textFieldNome.getText().isBlank()) {
						textFieldNome.requestFocus();
					} else {
						cadastrar();
					}
				}
			}
		});
		textFieldSenha.setToolTipText("Defina a senha de login do(a) professor(a)");
		lblSenha.setLabelFor(textFieldSenha);
		textFieldSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(10, 224, 229, 22);
		textFieldSenha.setDocument(new Validador(100));
		contentPanel.add(textFieldSenha);

		JLabel lblDisciplina = new JLabel("Disciplina *");
		lblDisciplina.setFont(new Font("Arial", Font.PLAIN, 15));
		lblDisciplina.setBounds(10, 266, 132, 24);
		contentPanel.add(lblDisciplina);

		textFieldDisciplina = new JTextField();
		textFieldDisciplina.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		textFieldDisciplina.setEditable(false);
		textFieldDisciplina.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				listarDisciplinas();
			}
		});
		lblDisciplina.setLabelFor(textFieldDisciplina);
		textFieldDisciplina.setToolTipText("Definir uma disciplina previamente ao professor (opicional)");
		textFieldDisciplina.setBackground(Color.WHITE);
		textFieldDisciplina.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldDisciplina.setColumns(10);
		textFieldDisciplina.setBounds(10, 300, 207, 22);
		contentPanel.add(textFieldDisciplina);

		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 321, 229, 90);
		contentPanel.add(scrollPane);

		listDisciplinas = new JList<String>();
		listDisciplinas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		listDisciplinas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textFieldDisciplina.setText(listDisciplinas.getSelectedValue());

				scrollPane.setVisible(false);
				listDisciplinas.setVisible(false);
				btnDisciplinas
						.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));
			}
		});
		listDisciplinas.setFont(new Font("Arial", Font.PLAIN, 13));
		scrollPane.setViewportView(listDisciplinas);

		btnDisciplinas = new JButton("");
		btnDisciplinas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarDisciplinas();
			}
		});
		btnDisciplinas.setIcon(new ImageIcon(CdstrStudent.class.getResource("/img/seta_de_itens_icon.png")));
		btnDisciplinas.setBounds(217, 300, 22, 22);
		contentPanel.add(btnDisciplinas);

		scrollPane.setVisible(false);
		listDisciplinas.setVisible(false);

		JLabel lblIF_logo = new JLabel("");
		lblIF_logo.setIcon(new ImageIcon(CdstrStudent.class.getResource("/img/IF Logo - Remove.png")));
		lblIF_logo.setBounds(316, 5, 87, 133);
		contentPanel.add(lblIF_logo);

		JLabel lblDesenho = new JLabel("");
		lblDesenho.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesenho.setIcon(new ImageIcon(CdstrProfessor.class.getResource("/img/Professor_Icon.png")));
		lblDesenho.setBounds(249, 188, 187, 187);
		contentPanel.add(lblDesenho);

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
		if (scrollPane.isVisible() && listDisciplinas.isVisible()) {
			listDisciplinas.setVisible(false);
			scrollPane.setVisible(false);
			textFieldDisciplina.setText("");
			btnDisciplinas.setIcon(new ImageIcon(CdstrStudent.class.getResource("/img/seta_de_itens_icon.png")));

			return;
		}

		DefaultListModel<String> model = new DefaultListModel<String>();

		String readTurmas = "select nome from disciplinas order by nome";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readTurmas);
			rs = pst.executeQuery();

			while (rs.next()) {
				model.addElement(rs.getString(1));
			}

			listDisciplinas.setModel(model);
			listDisciplinas.setVisible(true);
			scrollPane.setVisible(true);
			btnDisciplinas
					.setIcon(new ImageIcon(CdstrStudent.class.getResource("/img/seta_de_itens_para_cima_icon.png")));

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	private void cadastrar() {

		if (!(textFieldNome.getText().isBlank() || textFieldMatricula.getText().isEmpty()
				|| textFieldSenha.getText().isBlank())) {

			if (textFieldMatricula.getText().length() == 8) {
				String nome = textFieldNome.getText().trim();
				String matricula = textFieldMatricula.getText();
				String senha = textFieldSenha.getText().trim();

				try {
					con = DAO.conectar();
					pst = con.prepareStatement("select * from professores where matricula= ?");
					pst.setString(1, matricula);
					rs = pst.executeQuery();

					if (!rs.next()) {

						String insert = "insert into professores values (? , ? , ? )";

						pst = con.prepareStatement(insert);
						pst.setString(1, nome);
						pst.setString(2, matricula);
						pst.setString(3, senha);

						int confirma = pst.executeUpdate();

						if (!textFieldDisciplina.getText().isBlank()) {
							pst = con.prepareStatement(
									"insert into ensina values (default, ?, (select id from disciplinas where nome= ?))");
							pst.setString(1, matricula);
							pst.setString(2, textFieldDisciplina.getText());

							confirma += pst.executeUpdate();
						}

						if (confirma == 2 || (confirma == 1 && textFieldDisciplina.getText().isBlank())) {
							JOptionPane.showMessageDialog(null, "Professor cadastrado com sucesso!");
						} else if (confirma == 1 && !textFieldDisciplina.getText().isEmpty()) {
							JOptionPane.showMessageDialog(null,
									"Não foi possível designar a disciplina!!\nProfessor cadastrado com sucesso!");
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível fazer o cadastro!");
						}

						Professor professor = new Professor(nome, matricula, senha);

						adm.listagens();
						adm.status();
						adm.getContentPane().setVisible(false);
						adm.setContentPane(new ViewProfessor(adm, professor));

						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Matrícula já cadastrada!");
					}

					con.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Não foi possível fazer o cadastro:\n" + e);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Matrícula inválida!\nDigite uma matrícula com 8 dígitos.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos para cadastrar!");
		}
	}

}
