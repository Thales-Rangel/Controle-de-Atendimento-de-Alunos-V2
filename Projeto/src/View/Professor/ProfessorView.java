package View.Professor;

import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import Models.Professor;
import View.Admin.Admin;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import java.awt.Font;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class ProfessorView extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	
	Professor prof;
	private JLabel lblNome;
	public JPanel viewPanel;
	private JButton btnVerDados;
	private JButton btnVerSolicitations;
	private JButton btnVerTurmas;
	private JButton btnVerDisciplinas;
	private JButton btnPaginaInicial;
	private JLabel lblIFLogo;
	private JButton btnSair;
	private JPanel panel;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorView frame = new ProfessorView(new Professor("Carlos", "1002", "senha p2"));
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public ProfessorView(Professor prof) {
		this.prof = prof;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Admin.class.getResource("/img/IF Logo - Remove.png")));
		setTitle("Página do Professor");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 650);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		
		panel = new JPanel();
		panel.setBackground(new Color(26, 150, 3));
		
		lblIFLogo = new JLabel("");
		lblIFLogo.setIcon(new ImageIcon(ProfessorView.class.getResource("/img/IF Logo Branca - Remove.png")));
		
		lblNome = new JLabel(prof.getNome());
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));
		
		ProfessorView pv = this;
		
		btnVerDados = new JButton("Ver meus dados");
		btnVerDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPanel.setVisible(false);
				viewPanel = new ViewDatas(pv);
				contentPane.add(viewPanel);
				dimensionar();
			}
		});
		btnVerDados.setFont(new Font("Arial", Font.PLAIN, 14));
		
		btnVerSolicitations = new JButton("Ver solicitações");
		btnVerSolicitations.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPanel.setVisible(false);
				viewPanel = new ViewSolicitationsList(prof, pv);
				contentPane.add(viewPanel);
				dimensionar();
			}
		});
		btnVerSolicitations.setFont(new Font("Arial", Font.PLAIN, 15));
		
		btnVerTurmas = new JButton("Ver minhas turmas");
		btnVerTurmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPanel.setVisible(false);
				viewPanel = new ViewTurmasList(prof, pv);
				contentPane.add(viewPanel);
				dimensionar();
			}
		});
		btnVerTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		
		btnVerDisciplinas = new JButton("Ver minhas disciplinas");
		btnVerDisciplinas.setFont(new Font("Arial", Font.PLAIN, 10));
		
		btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Arial", Font.PLAIN, 15));
		
		btnPaginaInicial = new JButton("Página Inicial");
		btnPaginaInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPanel.setVisible(false);
				viewPanel = new PagInicial(prof);
				contentPane.add(viewPanel);
				dimensionar();
			}
		});
		btnPaginaInicial.setFont(new Font("Arial", Font.PLAIN, 15));
		
		viewPanel = new PagInicial(prof);
		dimensionar();
		
	}
	
	public void dimensionar() {
		GroupLayout gl_contentPane = new GroupLayout(contentPane);
		gl_contentPane.setHorizontalGroup(
			gl_contentPane.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, gl_contentPane.createSequentialGroup()
					.addComponent(viewPanel, GroupLayout.DEFAULT_SIZE, 627, Short.MAX_VALUE)
					.addGap(9)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 210, GroupLayout.PREFERRED_SIZE))
		);
		gl_contentPane.setVerticalGroup(
			gl_contentPane.createParallelGroup(Alignment.TRAILING)
				.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
				.addComponent(viewPanel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 603, Short.MAX_VALUE)
		);
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(btnVerDados, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(btnVerSolicitations, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(btnVerTurmas, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(btnVerDisciplinas, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(btnPaginaInicial, GroupLayout.PREFERRED_SIZE, 145, GroupLayout.PREFERRED_SIZE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(32)
					.addComponent(lblIFLogo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(33))
				.addComponent(btnSair, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 210, Short.MAX_VALUE)
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNome, GroupLayout.PREFERRED_SIZE, 42, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnVerDados, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnVerSolicitations)
					.addGap(10)
					.addComponent(btnVerTurmas, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnVerDisciplinas, GroupLayout.PREFERRED_SIZE, 27, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(btnPaginaInicial)
					.addGap(84)
					.addComponent(lblIFLogo, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(103)
					.addComponent(btnSair))
		);
		panel.setLayout(gl_panel);
		contentPane.setLayout(gl_contentPane);
	}
}
