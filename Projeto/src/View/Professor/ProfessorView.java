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

public class ProfessorView extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private Professor prof;
	private JLabel lblNome;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ProfessorView frame = new ProfessorView(null);
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
		
		JButton btnVerDados = new JButton("Ver meus dados");
		btnVerDados.setFont(new Font("Arial", Font.PLAIN, 15));
		btnVerDados.setBounds(32, 62, 145, 27);
		panel.add(btnVerDados);
		
		JButton btnVerSolicitations = new JButton("Ver solicitações");
		btnVerSolicitations.setFont(new Font("Arial", Font.PLAIN, 15));
		btnVerSolicitations.setBounds(32, 99, 145, 27);
		panel.add(btnVerSolicitations);
	}
}
