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

public class ProfessorView extends JFrame {

	private static final long serialVersionUID = 1L;
	JPanel contentPane;
	
	Professor prof;
	private JLabel lblNome;
	public JPanel viewPanel;

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
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(26, 150, 3));
		panel.setBounds(636, 0, 210, 593);
		contentPane.add(panel);
		panel.setLayout(null);
		
		JLabel lblIFLogo = new JLabel("");
		lblIFLogo.setIcon(new ImageIcon(ProfessorView.class.getResource("/img/IF Logo Branca - Remove.png")));
		lblIFLogo.setBounds(32, 321, 145, 142);
		panel.add(lblIFLogo);
		
		lblNome = new JLabel(prof.getNome());
		lblNome.setHorizontalAlignment(SwingConstants.CENTER);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));
		lblNome.setBounds(10, 10, 190, 42);
		panel.add(lblNome);
		
		ProfessorView pv = this;
		
		JButton btnVerDados = new JButton("Ver meus dados");
		btnVerDados.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPanel.setVisible(false);
				viewPanel = new ViewDatas(pv);
				contentPane.add(viewPanel);
			}
		});
		btnVerDados.setFont(new Font("Arial", Font.PLAIN, 14));
		btnVerDados.setBounds(32, 62, 145, 27);
		panel.add(btnVerDados);
		
		JButton btnVerSolicitations = new JButton("Ver solicitações");
		btnVerSolicitations.setFont(new Font("Arial", Font.PLAIN, 15));
		btnVerSolicitations.setBounds(32, 99, 145, 27);
		panel.add(btnVerSolicitations);
		
		JButton btnVerTurmas = new JButton("Ver minhas turmas");
		btnVerTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		btnVerTurmas.setBounds(32, 136, 145, 27);
		panel.add(btnVerTurmas);
		
		JButton btnVerDisciplinas = new JButton("Ver minhas disciplinas");
		btnVerDisciplinas.setFont(new Font("Arial", Font.PLAIN, 10));
		btnVerDisciplinas.setBounds(32, 173, 145, 27);
		panel.add(btnVerDisciplinas);
		
		JButton btnSair = new JButton("Sair");
		btnSair.setFont(new Font("Arial", Font.PLAIN, 15));
		btnSair.setBounds(0, 562, 210, 31);
		panel.add(btnSair);
		
		JButton btnPaginaInicial = new JButton("Página Inicial");
		btnPaginaInicial.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				viewPanel.setVisible(false);
				viewPanel = new PagInicial(prof);
				contentPane.add(viewPanel);
			}
		});
		btnPaginaInicial.setFont(new Font("Arial", Font.PLAIN, 15));
		btnPaginaInicial.setBounds(32, 210, 145, 27);
		panel.add(btnPaginaInicial);
		
		viewPanel = new PagInicial(prof);
		viewPanel.setBounds(0, 0, 637, 593);
		contentPane.add(viewPanel);
	}
}
