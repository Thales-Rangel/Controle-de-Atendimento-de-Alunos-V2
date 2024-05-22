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

import Models.DAO;
import Models.Student;
import Utils.Validador;
import java.awt.Cursor;

public class CdstrStudent extends JDialog {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private Admin adm;

	private static final long serialVersionUID = 1L;
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JTextField textFieldMatricula;
	private JTextField textFieldSenha;
	private JTextField textFieldTurma;
	private JList<String> listTurmas;
	private JScrollPane scrollPane;
	private JButton btnTurmas;

	/**
	 * Create the dialog.
	 */
	public CdstrStudent(Admin adm) {
		this.adm = adm;
		
		setResizable(false);
		setTitle("Cadastro de alunos");
		setIconImage(Toolkit.getDefaultToolkit().getImage(CdstrStudent.class.getResource("/img/IF Logo - Remove.png")));
		setBounds(100, 100, 450, 500);
		setLocationRelativeTo(null);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblPag = new JLabel("Cadastro de alunos");
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
		textFieldNome.setToolTipText("Cadastre o nome do aluno(a)");
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
					if (textFieldSenha.getText().isBlank()) {
						textFieldSenha.requestFocus();
					} else if(textFieldNome.getText().isBlank()){
						textFieldNome.requestFocus();
					} else {
						cadastrar();
					}
				}
			}
		});
		textFieldMatricula.setToolTipText("Cadastre a matricula do aluno(a)");
		lblMatricula.setLabelFor(textFieldMatricula);
		textFieldMatricula.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldMatricula.setColumns(10);
		textFieldMatricula.setBounds(10, 148, 229, 22);
		textFieldMatricula.setDocument(new Validador(14));
		contentPanel.add(textFieldMatricula);
		
		JLabel lblSenha = new JLabel("Senha");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSenha.setBounds(10, 190, 132, 24);
		contentPanel.add(lblSenha);
		
		textFieldSenha = new JTextField();
		textFieldSenha.addKeyListener(new KeyAdapter() {
			@Override
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
		textFieldSenha.setToolTipText("Defina a senha de login do aluno(a)");
		lblSenha.setLabelFor(textFieldSenha);
		textFieldSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldSenha.setColumns(10);
		textFieldSenha.setBounds(10, 224, 229, 22);
		textFieldSenha.setDocument(new Validador(100));
		contentPanel.add(textFieldSenha);
		
		JLabel lblTurma = new JLabel("Turma");
		lblTurma.setFont(new Font("Arial", Font.PLAIN, 15));
		lblTurma.setBounds(10, 266, 132, 24);
		contentPanel.add(lblTurma);
		
		textFieldTurma = new JTextField();
		textFieldTurma.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		textFieldTurma.setEditable(false);
		textFieldTurma.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				listarTurmas();
			}
		});
		lblTurma.setLabelFor(textFieldTurma);
		textFieldTurma.setToolTipText("Defina a turma do aluno(a)");
		textFieldTurma.setBackground(Color.WHITE);
		textFieldTurma.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldTurma.setColumns(10);
		textFieldTurma.setBounds(10, 300, 207, 22);
		contentPanel.add(textFieldTurma);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 321, 229, 90);
		contentPanel.add(scrollPane);
		
		listTurmas = new JList<String>();
		listTurmas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		listTurmas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldTurma.setText(listTurmas.getSelectedValue());

				scrollPane.setVisible(false);
				listTurmas.setVisible(false);
				btnTurmas
						.setIcon(new ImageIcon(ViewStudentsList.class.getResource("/img/seta_de_itens_icon.png")));
			}
		});
		listTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		scrollPane.setViewportView(listTurmas);
		
		btnTurmas = new JButton("");
		btnTurmas.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnTurmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarTurmas();
			}
		});
		btnTurmas.setIcon(new ImageIcon(CdstrStudent.class.getResource("/img/seta_de_itens_icon.png")));
		btnTurmas.setBounds(217, 300, 22, 22);
		contentPanel.add(btnTurmas);
		
		scrollPane.setVisible(false);
		listTurmas.setVisible(false);
		
		JLabel lblIF_logo = new JLabel("");
		lblIF_logo.setIcon(new ImageIcon(CdstrStudent.class.getResource("/img/IF Logo - Remove.png")));
		lblIF_logo.setBounds(316, 5, 87, 133);
		contentPanel.add(lblIF_logo);
		
		JLabel lblDesenho = new JLabel("");
		lblDesenho.setHorizontalAlignment(SwingConstants.CENTER);
		lblDesenho.setIcon(new ImageIcon(CdstrStudent.class.getResource("/img/Icon_cadastro.png")));
		lblDesenho.setBounds(249, 188, 187, 134);
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
	
	private void listarTurmas() {
		if (scrollPane.isVisible() && listTurmas.isVisible()) {
			listTurmas.setVisible(false);
			scrollPane.setVisible(false);
			textFieldTurma.setText("");
			btnTurmas.setIcon(new ImageIcon(CdstrStudent.class.getResource("/img/seta_de_itens_icon.png")));
			
			return;
		}
		
		DefaultListModel<String> model = new DefaultListModel<String>();
		
		String readTurmas = "select nome from turmas order by nome";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readTurmas);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				model.addElement(rs.getString(1));
			}
			
			listTurmas.setModel(model);
			listTurmas.setVisible(true);
			scrollPane.setVisible(true);
			btnTurmas.setIcon(new ImageIcon(CdstrStudent.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void cadastrar() {
		String nome;
		String matricula;
		String senha;
		int id_turma = 0;
		
		if(!(textFieldNome.getText().isBlank() || textFieldMatricula.getText().isEmpty() || textFieldSenha.getText().isBlank() || textFieldTurma.getText().isBlank())) {
			
			if (textFieldMatricula.getText().length() == 14) {
				nome = textFieldNome.getText().trim();
				matricula = textFieldMatricula.getText();
				senha = textFieldSenha.getText().trim();
				
				try {
					con = DAO.conectar();
					pst = con.prepareStatement("select id from turmas where nome= ?");
					pst.setString(1, textFieldTurma.getText());
					rs = pst.executeQuery();
					
					if (rs.next()) {
						id_turma = rs.getInt(1);
					}
					
					pst = con.prepareStatement("select * from alunos where matricula= ?");
					pst.setString(1, matricula);
					rs = pst.executeQuery();
					
					if (!rs.next()) {

						String insert = "insert into alunos values "
								+ "(? , ? , ? , ?)";
						
						pst = con.prepareStatement(insert);
						pst.setString(1, nome);
						pst.setString(2, matricula);
						pst.setString(3, senha);
						pst.setInt(4, id_turma);
						
						int confirma = pst.executeUpdate();
						if (confirma == 1) {
							JOptionPane.showMessageDialog(null, "Aluno cadastrado com sucesso!");
						} else {
							JOptionPane.showMessageDialog(null, "Não foi possível fazer o cadastro!");
						}
						
						Student aluno = new Student(nome, matricula, senha, id_turma);
						
						adm.listagens();
						adm.status();
						adm.getContentPane().setVisible(false);
						adm.setContentPane(new ViewStudent(adm, aluno));
						
						dispose();
					} else {
						JOptionPane.showMessageDialog(null, "Matrícula já cadastrada!");
					}
					
					con.close();
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, "Não foi possível fazer o cadastro:\n"+e);
				}
			} else {
				JOptionPane.showMessageDialog(null, "Matrícula inválida!\nDigite uma matrícula com 14 dígitos.");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos para cadastrar!");
		}
	}
}
