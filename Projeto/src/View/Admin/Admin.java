package View.Admin;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;

import Models.DAO;
import View.Login;
import javax.swing.JScrollPane;
import javax.swing.JList;
import javax.swing.SwingConstants;
import javax.swing.border.EtchedBorder;
import java.awt.SystemColor;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Cursor;
import javax.swing.BoxLayout;

public class Admin extends JFrame {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	public JPanel contentPane;
	private JLabel lblUsersDB;
	private JLabel lblTurmasDB;
	private JLabel lblDisciplinasDB;
	private JList<String> listStudents;
	private JList<String> listProfessores;
	private JList<String> listTurmas;
	private JList<String> listDisciplinas;
	private JLabel lblStatusDB;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Admin frame = new Admin();
					frame.setVisible(true);
				} catch (Exception e) {
					JOptionPane.showMessageDialog(null, e);;
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public Admin() {
		Admin adm = this;
		
		setIconImage(Toolkit.getDefaultToolkit().getImage(Admin.class.getResource("/img/IF Logo - Remove.png")));
		setTitle("Página de Admin");
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 860, 630);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		
		setLocationRelativeTo(null);

		setContentPane(contentPane);
		contentPane.setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 846, 22);
		contentPane.add(menuBar);

		JMenu mnStudents = new JMenu("Alunos");
		mnStudents.setForeground(Color.BLACK);
		mnStudents.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnStudents);

		JMenuItem mntmViewAllStudents = new JMenuItem("Ver os estudantes cadastrados");
		mntmViewAllStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewStudentsList(adm));
			}
		});
		mntmViewAllStudents.setFont(new Font("Arial", Font.PLAIN, 13));
		mnStudents.add(mntmViewAllStudents);

		JMenuItem mntmCdstStudent = new JMenuItem("Cadastrar um estudante");
		mntmCdstStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrStudent(adm).setVisible(true);
			}
		});
		mntmCdstStudent.setFont(new Font("Arial", Font.PLAIN, 13));
		mnStudents.add(mntmCdstStudent);

		JMenu mnProfessors = new JMenu("Professores");
		mnProfessors.setForeground(Color.BLACK);
		mnProfessors.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnProfessors);

		JMenuItem mntmViewAllProfessors = new JMenuItem("Ver professores cadastrados");
		mntmViewAllProfessors.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewProfessorsList(adm));
			}
		});
		mntmViewAllProfessors.setFont(new Font("Arial", Font.PLAIN, 13));
		mnProfessors.add(mntmViewAllProfessors);

		JMenuItem mntmCadstProfessor = new JMenuItem("Cadastrar um professor");
		mntmCadstProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrProfessor(adm).setVisible(true);
			}
		});
		mntmCadstProfessor.setFont(new Font("Arial", Font.PLAIN, 13));
		mnProfessors.add(mntmCadstProfessor);

		JMenu mnTurmas = new JMenu("Turmas");
		mnTurmas.setForeground(Color.BLACK);
		mnTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnTurmas);

		JMenuItem mntmViewAllTurmas = new JMenuItem("Ver Turmas Cadastradas\r\n");
		mntmViewAllTurmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewTurmasList(adm));
			}
		});
		mntmViewAllTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		mnTurmas.add(mntmViewAllTurmas);

		JMenuItem mntmCdstTurma = new JMenuItem("Cadastrar uma turma");
		mntmCdstTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrTurma(adm).setVisible(true);
			}
		});
		mntmCdstTurma.setFont(new Font("Arial", Font.PLAIN, 13));
		mnTurmas.add(mntmCdstTurma);

		JMenu mnDisciplinas = new JMenu("Disciplinas");
		mnDisciplinas.setForeground(Color.BLACK);
		mnDisciplinas.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnDisciplinas);

		JMenuItem mntmViewAllDisciplinas = new JMenuItem("Ver disciplinas cadastradas");
		mntmViewAllDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewDisciplanesList(adm));
			}
		});
		mntmViewAllDisciplinas.setFont(new Font("Arial", Font.PLAIN, 13));
		mnDisciplinas.add(mntmViewAllDisciplinas);

		JMenuItem mntmCdstDisciplina = new JMenuItem("Cadastrar uma disciplina");
		mntmCdstDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrDisciplane(adm).setVisible(true);
			}
		});
		mntmCdstDisciplina.setFont(new Font("Arial", Font.PLAIN, 13));
		mnDisciplinas.add(mntmCdstDisciplina);

		JMenu mnExit = new JMenu("Sair");
		mnExit.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				sair();
			}
		});
		mnExit.setForeground(Color.BLACK);
		mnExit.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnExit);

		JPanel panelCabeçalho = new JPanel();
		panelCabeçalho.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelCabeçalho.setBounds(10, 32, 826, 133);
		contentPane.add(panelCabeçalho);
		panelCabeçalho.setLayout(null);

		JLabel label = new JLabel("New label");
		label.setBounds(781, 0, -134, 123);
		panelCabeçalho.add(label);

		JLabel ifLogo = new JLabel("");
		ifLogo.setHorizontalAlignment(SwingConstants.CENTER);
		ifLogo.setIcon(new ImageIcon(Admin.class.getResource("/img/IF Logo - Remove.png")));
		ifLogo.setBounds(693, 0, 133, 133);
		panelCabeçalho.add(ifLogo);

		JLabel lblIFTitle = new JLabel("Instituto Federal de Educação, Ciência e Tecnologia do Rio Grande do Norte");
		lblIFTitle.setFont(new Font("Times New Roman", Font.BOLD, 20));
		lblIFTitle.setBounds(10, 10, 673, 24);
		panelCabeçalho.add(lblIFTitle);

		JLabel lblIFSubtitle = new JLabel("Campus Avançado Lajes");
		lblIFSubtitle.setFont(new Font("Times New Roman", Font.PLAIN, 15));
		lblIFSubtitle.setBounds(10, 33, 673, 18);
		panelCabeçalho.add(lblIFSubtitle);

		JLabel lblPagAdmin = new JLabel("Página do Admin - Controle de Atendimento de Alunos");
		lblPagAdmin.setFont(new Font("Times New Roman", Font.BOLD, 22));
		lblPagAdmin.setBounds(10, 61, 673, 24);
		panelCabeçalho.add(lblPagAdmin);

		JLabel lblDados_do_Projeto = new JLabel("Autor: Thales Rangel;  Matéria: PABD;  Professor: Danilo");
		lblDados_do_Projeto.setFont(new Font("Times New Roman", Font.PLAIN, 20));
		lblDados_do_Projeto.setBounds(10, 84, 673, 24);
		panelCabeçalho.add(lblDados_do_Projeto);

		JPanel panelDBInformation = new JPanel();
		panelDBInformation.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelDBInformation.setBounds(10, 175, 826, 48);
		contentPane.add(panelDBInformation);
		panelDBInformation.setLayout(null);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBounds(10, 10, 765, 28);
		panelDBInformation.add(panel_1);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		lblUsersDB = new JLabel("Usúarios cadastrados: x (A: y, P: z);");
		panel_1.add(lblUsersDB);
		lblUsersDB.setFont(new Font("Arial", Font.PLAIN, 15));
		
		lblTurmasDB = new JLabel("   Turmas cadastradas: x;");
		panel_1.add(lblTurmasDB);
		lblTurmasDB.setFont(new Font("Arial", Font.PLAIN, 15));
		
		lblDisciplinasDB = new JLabel("   Disciplinas cadastradas: x;");
		panel_1.add(lblDisciplinasDB);
		lblDisciplinasDB.setFont(new Font("Arial", Font.PLAIN, 15));
		
		lblStatusDB = new JLabel("");
		lblStatusDB.setBounds(778, 0, 48, 48);
		panelDBInformation.add(lblStatusDB);
		lblStatusDB.setIcon(new ImageIcon(Admin.class.getResource("/img/dboff.png")));
		lblStatusDB.setToolTipText("Conexão com o Banco de Dados: Não conectado");
		
		JPanel panelLists = new JPanel();
		panelLists.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelLists.setBounds(10, 233, 529, 360);
		contentPane.add(panelLists);
		panelLists.setLayout(null);
		
		JScrollPane scrollPaneStudents = new JScrollPane();
		scrollPaneStudents.setBounds(10, 36, 240, 139);
		panelLists.add(scrollPaneStudents);
		
		listStudents = new JList<String>();
		listStudents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewStudentsList(adm));
			}
		});
		listStudents.setToolTipText("Lista de alunos cadastrados");
		listStudents.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneStudents.setViewportView(listStudents);
		
		JScrollPane scrollPaneProfessores = new JScrollPane();
		scrollPaneProfessores.setBounds(279, 36, 240, 139);
		panelLists.add(scrollPaneProfessores);
		
		listProfessores = new JList<String>();
		listProfessores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewProfessorsList(adm));
			}
		});
		listProfessores.setToolTipText("Lista de professores cadastrados");
		listProfessores.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneProfessores.setViewportView(listProfessores);
		
		JScrollPane scrollPaneTurmas = new JScrollPane();
		scrollPaneTurmas.setBounds(10, 211, 240, 139);
		panelLists.add(scrollPaneTurmas);
		
		listTurmas = new JList<String>();
		listTurmas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewTurmasList(adm));
			}
		});
		listTurmas.setToolTipText("Lista de turmas cadastradas");
		listTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneTurmas.setViewportView(listTurmas);
		
		JScrollPane scrollPaneDisciplinas = new JScrollPane();
		scrollPaneDisciplinas.setBounds(279, 211, 240, 139);
		panelLists.add(scrollPaneDisciplinas);
		
		listDisciplinas = new JList<String>();
		listDisciplinas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewDisciplanesList(adm));
			}
		});
		listDisciplinas.setToolTipText("Lista de disciplinas cadastradas");
		listDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneDisciplinas.setViewportView(listDisciplinas);
		
		JLabel lblListStudents = new JLabel("Alunos");
		lblListStudents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewStudentsList(adm));
			}
		});
		lblListStudents.setLabelFor(listStudents);
		lblListStudents.setToolTipText("Lista de alunos cadastrados");
		lblListStudents.setHorizontalAlignment(SwingConstants.CENTER);
		lblListStudents.setFont(new Font("Arial", Font.BOLD, 18));
		lblListStudents.setBounds(10, 13, 240, 25);
		panelLists.add(lblListStudents);
		
		JLabel lblListProfessores = new JLabel("Professores");
		lblListProfessores.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewProfessorsList(adm));
			}
		});
		lblListProfessores.setLabelFor(listProfessores);
		lblListProfessores.setToolTipText("Lista de professores cadastrados");
		lblListProfessores.setHorizontalAlignment(SwingConstants.CENTER);
		lblListProfessores.setFont(new Font("Arial", Font.BOLD, 18));
		lblListProfessores.setBounds(279, 13, 240, 25);
		panelLists.add(lblListProfessores);
		
		JLabel lblListTurmas = new JLabel("Turmas");
		lblListTurmas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewTurmasList(adm));
			}
		});
		lblListTurmas.setLabelFor(listTurmas);
		lblListTurmas.setToolTipText("Lista de turmas cadastrados");
		lblListTurmas.setHorizontalAlignment(SwingConstants.CENTER);
		lblListTurmas.setFont(new Font("Arial", Font.BOLD, 18));
		lblListTurmas.setBounds(10, 185, 240, 25);
		panelLists.add(lblListTurmas);
		
		JLabel lblListDisciplinas = new JLabel("Disciplinas");
		lblListDisciplinas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				contentPane.setVisible(false);
				setContentPane(new ViewDisciplanesList(adm));
			}
		});
		lblListDisciplinas.setLabelFor(listDisciplinas);
		lblListDisciplinas.setToolTipText("Lista de disciplinas cadastrados");
		lblListDisciplinas.setHorizontalAlignment(SwingConstants.CENTER);
		lblListDisciplinas.setFont(new Font("Arial", Font.BOLD, 18));
		lblListDisciplinas.setBounds(279, 185, 240, 25);
		panelLists.add(lblListDisciplinas);
		
		JPanel panelADM = new JPanel();
		panelADM.setBackground(SystemColor.control);
		panelADM.setBorder(new EtchedBorder(EtchedBorder.LOWERED, null, null));
		panelADM.setBounds(549, 233, 287, 360);
		contentPane.add(panelADM);
		panelADM.setLayout(null);
		
		JLabel lblADM = new JLabel("Ações Administrativas");
		lblADM.setHorizontalAlignment(SwingConstants.CENTER);
		lblADM.setFont(new Font("Arial", Font.PLAIN, 20));
		lblADM.setBounds(0, 7, 287, 57);
		panelADM.add(lblADM);
		
		JButton btnCdstStudent = new JButton("Cadastrar Aluno");
		btnCdstStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrStudent(adm).setVisible(true);
			}
		});
		btnCdstStudent.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCdstStudent.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCdstStudent.setBounds(65, 74, 156, 27);
		panelADM.add(btnCdstStudent);
		
		JButton btnCdstProfessor = new JButton("Cadastrar Professor");
		btnCdstProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrProfessor(adm).setVisible(true);
			}
		});
		btnCdstProfessor.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCdstProfessor.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCdstProfessor.setBounds(65, 111, 156, 27);
		panelADM.add(btnCdstProfessor);
		
		JButton btnCdstDisciplane = new JButton("Cadastrar Disciplina");
		btnCdstDisciplane.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrDisciplane(adm).setVisible(true);
			}
		});
		btnCdstDisciplane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCdstDisciplane.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCdstDisciplane.setBounds(65, 185, 156, 27);
		panelADM.add(btnCdstDisciplane);
		
		JButton btnCdstTurma = new JButton("Cadastrar Turma");
		btnCdstTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new CdstrTurma(adm).setVisible(true);
			}
		});
		btnCdstTurma.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		btnCdstTurma.setFont(new Font("Arial", Font.PLAIN, 13));
		btnCdstTurma.setBounds(65, 148, 156, 27);
		panelADM.add(btnCdstTurma);
		
		status();
		listagens();
	}

	public void status() {
		try {
			con = DAO.conectar();

			if (con == null) {
				lblStatusDB.setIcon(new ImageIcon(Admin.class.getResource("/img/dboff.png")));
				lblStatusDB.setToolTipText("Conexão com o Banco de Dados: Não conectado");
				
				lblUsersDB.setText("Usúarios cadastrados: *;");
				lblTurmasDB.setText("   Turmas cadastradas: *;");
				lblDisciplinasDB.setText("   Disciplinas cadastradas: *;");
			} else {
				lblStatusDB.setIcon(new ImageIcon(Admin.class.getResource("/img/dbon.png")));
				lblStatusDB.setToolTipText("Conexão com o Banco de Dados: Conectado");
				
				PreparedStatement pst1 = con.prepareStatement("select count(*) from alunos");
				PreparedStatement pst2 = con.prepareStatement("select count(*) from professores");
				PreparedStatement pst3 = con.prepareStatement("select count(*) from turmas");
				PreparedStatement pst4 = con.prepareStatement("select count(*) from disciplinas");
				
				ResultSet rs1 = pst1.executeQuery();
				ResultSet rs2 = pst2.executeQuery();
				ResultSet rs3 = pst3.executeQuery();
				ResultSet rs4 = pst4.executeQuery();
				
				int userTotal;
				int alunosTotal = 0;
				int professoresTotal = 0;
				int turmasTotal = 0;
				int disciplinasTotal = 0;
				
				if(rs1.next()) {
					alunosTotal = rs1.getInt(1);
				}
				if(rs2.next()) {
					professoresTotal = rs2.getInt(1);
				}
				if (rs3.next()) {
					turmasTotal = rs3.getInt(1);
				}
				if (rs4.next()) {
					disciplinasTotal = rs4.getInt(1);
				}
				
				userTotal = alunosTotal + professoresTotal;
				
				lblUsersDB.setText("Usúarios cadastrados: "+ userTotal +" (A: "+ alunosTotal +", P: "+ professoresTotal +");");
				lblTurmasDB.setText("   Turmas cadastradas: "+ turmasTotal +";");
				lblDisciplinasDB.setText("   Disciplinas cadastradas: "+ disciplinasTotal +";");
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

	public void listagens() {
		DefaultListModel<String> modelo1 = new DefaultListModel<>();
		DefaultListModel<String> modelo2 = new DefaultListModel<>();
		DefaultListModel<String> modelo3 = new DefaultListModel<>();
		DefaultListModel<String> modelo4 = new DefaultListModel<>();
		listStudents.setModel(modelo1);
		listProfessores.setModel(modelo2);
		listTurmas.setModel(modelo3);
		listDisciplinas.setModel(modelo4);
		
		String readStudents = "select * from alunos order by nome";
		String readProfessors = "select * from professores order by nome";
		String readTurmas = "select * from turmas order by nome";
		String readDisciplanes = "select * from disciplinas order by nome";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readStudents);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				modelo1.addElement(rs.getString(1));
			}
			
			pst = con.prepareStatement(readProfessors);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				modelo2.addElement(rs.getString(1));
			}
			
			pst = con.prepareStatement(readTurmas);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				modelo3.addElement(rs.getString(2));
			}
			
			pst = con.prepareStatement(readDisciplanes);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				modelo4.addElement(rs.getString(2));
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void sair() {
		dispose();
		new Login().setVisible(true);
	}
}
