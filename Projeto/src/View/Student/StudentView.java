package View.Student;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Models.Student;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ImageIcon;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class StudentView extends JFrame {

	Student s;
	
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JPanel viewPanel;
	private JPanel panel_1;
	private JLabel lblNome;
	private JButton btnVerDados;
	private JButton btnVerSolicitations;
	private JButton btnVerDisciplinas;
	private JButton btnPaginaInicial;
	private JLabel lblIFLogo;
	private JButton btnSair;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					StudentView frame = new StudentView(new Student("Thales", "20221204010013", "senha T", 1));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public StudentView(Student s) {
		this.s = s;
		StudentView sv = this;
		
		setTitle("Página do aluno");
		setIconImage(Toolkit.getDefaultToolkit().getImage(StudentView.class.getResource("/img/IF Logo - Remove.png")));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		
		panel_1 = new JPanel();
		panel_1.setBackground(new Color(26, 150, 3));
		
		lblNome = new JLabel(s.getNome());
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));
		
		btnVerDados = new JButton("Ver meus dados");
		btnVerDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setViewPanel(new ViewDatasS(sv));
			}
		});
		btnVerDados.setFont(new Font("Arial", Font.PLAIN, 14));
		
		btnVerSolicitations = new JButton("Ver solicitações");
		btnVerSolicitations.setFont(new Font("Arial", Font.PLAIN, 15));
		
		lblIFLogo = new JLabel("");
		lblIFLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblIFLogo.setIcon(new ImageIcon(StudentView.class.getResource("/img/IF Logo Branca - Remove.png")));
		
		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Arial", Font.PLAIN, 15));
		
		btnPaginaInicial = new JButton("Página Inicial");
		btnPaginaInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setViewPanel(new PagInicialS(s));
			}
		});
		btnPaginaInicial.setFont(new Font("Arial", Font.PLAIN, 15));
		
		btnVerDisciplinas = new JButton("Ver minhas disciplinas");
		btnVerDisciplinas.setFont(new Font("Arial", Font.PLAIN, 10));
		

		viewPanel = new PagInicialS(s);
		
		dimensionar();
	}
	
	public void setViewPanel(JPanel panel) {
		this.viewPanel.setVisible(false);
		this.viewPanel = panel;
		this.contentPane.add(this.viewPanel);
		dimensionar();
		
	}
	
	private void dimensionar() {
		GroupLayout gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 210, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(32)
					.addComponent(btnVerDados, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(32)
					.addComponent(btnVerSolicitations, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(32)
					.addComponent(lblIFLogo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(33))
				.addComponent(btnSair, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(32)
					.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
						.addComponent(btnPaginaInicial, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnVerDisciplinas, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE)))
		);
		gl_panel_1.setVerticalGroup(
			gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGap(0, 603, Short.MAX_VALUE)
				.addGroup(gl_panel_1.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnVerDados, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnVerSolicitations)
					.addGap(10)
					.addComponent(btnVerDisciplinas, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(btnPaginaInicial)
					.addGap(121)
					.addComponent(lblIFLogo, GroupLayout.DEFAULT_SIZE, 152, Short.MAX_VALUE)
					.addGap(103)
					.addComponent(btnSair))
		);
		panel_1.setLayout(gl_panel_1);
		
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(viewPanel, GroupLayout.DEFAULT_SIZE, 620, Short.MAX_VALUE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
				.addComponent(viewPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 613, Short.MAX_VALUE)
		);
		contentPane.setLayout(gl_contentPane);
	}
}
