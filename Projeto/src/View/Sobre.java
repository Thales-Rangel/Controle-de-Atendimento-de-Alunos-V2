package View;

import java.awt.Desktop;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URI;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.ScrollPaneConstants;
import java.awt.Cursor;

public class Sobre extends JDialog {

	private static final long serialVersionUID = 1L;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Sobre dialog = new Sobre();
					dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
					dialog.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Sobre() {
		setIconImage(Toolkit.getDefaultToolkit().getImage(Sobre.class.getResource("/img/Project_Icon.png")));
		setTitle("Sobre");
		setResizable(false);
		setBounds(100, 100, 450, 525);
		getContentPane().setLayout(null);

		setLocationRelativeTo(null);

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBorder(null);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(10, 10, 426, 430);
		getContentPane().add(scrollPane);

		JPanel panel = new JPanel();
		scrollPane.setViewportView(panel);

		JLabel lblTitle = new JLabel("Controle de Atendimento de Alunos V2");
		lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
		lblTitle.setFont(new Font("Tahoma", Font.BOLD, 19));

		JTextPane txtpnDesc = new JTextPane();
		txtpnDesc.setText(
				"\tEste projeto foi desenvolvido com o intuito de botar em prática estudos sobre Banco de Dados MySQL, utilizando de um projeto anteriormente já feito.\r\n\r\n"
						+ "\tEste projeto consiste em um programa capaz de gerenciar dúvidas e solicitações de alunos para professores, onde é possível cadastrar alunos, professores, turmas, disciplinas e solicitações, através de um Banco de Dados MySQL.\r\n\r\n"
						+ "\tEsta primeira versão deste projeto funcionará por meio do localhoast do computador, e terá que ter uma base de dados de nome 'project_pabd' para funcionar corretamente.\r\n\r\n"
						+ "\tMais detalhes sobre este projeto, junto com os códigos para criar a estrutura do Banco de Dados para o programa funcionar devidamente estão disponiveis no repositório do projeto no GitHub, que estará disponível no botão abaixo.\r\n\r\n"
						+ "\tSiga meus perfis no GitHub e Instagram para ver mais dos meus projetos;\r\n\r\n"
						+ "*Acesse o repositório no GitHub para configurar a base de dados e o programa funcionar perfeitamente*\r\n\r\n"
						+ "(Acesse a janela do Admin usando uma matricula \"0001\" e senha \"admin\", para poder cadastrar alunos, professores, turmas e disciplinas)");
		txtpnDesc.setEditable(false);
		txtpnDesc.setBackground(SystemColor.menu);
		txtpnDesc.setFont(new Font("Tahoma", Font.PLAIN, 15));

		JLabel lblPerfis = new JLabel("Perfis e redes sociais:");
		lblPerfis.setFont(new Font("Tahoma", Font.PLAIN, 19));

		JButton btnGitHubButton = new JButton("");
		btnGitHubButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnGitHubButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://github.com/Thales-Rangel");
			}
		});
		btnGitHubButton.setToolTipText("Repositório do projeto (GitHub)");
		btnGitHubButton.setIcon(new ImageIcon(Sobre.class.getResource("/img/GitHub_logo.png")));

		JButton btnInstaButton = new JButton("");
		btnInstaButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnInstaButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				link("https://www.instagram.com/thales.dev/");
			}
		});
		btnInstaButton.setToolTipText("Perfil do Instagram");
		btnInstaButton.setIcon(new ImageIcon(Sobre.class.getResource("/img/Insta_logo.png")));
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup().addGap(10)
						.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
								.addComponent(txtpnDesc, GroupLayout.PREFERRED_SIZE, 396, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblPerfis, GroupLayout.PREFERRED_SIZE, 213, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panel.createSequentialGroup()
										.addComponent(btnGitHubButton, GroupLayout.PREFERRED_SIZE, 32,
												GroupLayout.PREFERRED_SIZE)
										.addGap(20).addComponent(btnInstaButton, GroupLayout.PREFERRED_SIZE, 32,
												GroupLayout.PREFERRED_SIZE)))));
		gl_panel.setVerticalGroup(gl_panel.createParallelGroup(Alignment.LEADING).addGroup(gl_panel
				.createSequentialGroup().addGap(10).addComponent(lblTitle).addGap(18)
				.addComponent(txtpnDesc, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
						GroupLayout.PREFERRED_SIZE)
				.addGap(10).addComponent(lblPerfis).addGap(10)
				.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnGitHubButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnInstaButton, GroupLayout.PREFERRED_SIZE, 32, GroupLayout.PREFERRED_SIZE))));
		panel.setLayout(gl_panel);

		JButton btnOkButton = new JButton("OK");
		btnOkButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				dispose();
			}
		});
		btnOkButton.setBounds(175, 453, 85, 21);
		getContentPane().add(btnOkButton);

	}

	private void link(String url) {
		Desktop desktop = Desktop.getDesktop();
		try {
			URI uri = new URI(url);
			desktop.browse(uri);
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel acessar o link:\n" + e);
		}

	}
}
