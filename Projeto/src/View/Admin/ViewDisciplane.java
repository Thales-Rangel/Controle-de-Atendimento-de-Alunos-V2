package View.Admin;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Disciplane;
import View.Login;

public class ViewDisciplane extends JPanel {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private Disciplane d;
	private Admin adm;

	private static final long serialVersionUID = 1L;
	private JTable tableProfessors;
	private JTable tableTurmas;
	public JLabel lblNome;

	/**
	 * Create the panel.
	 */
	public ViewDisciplane(Admin adm, Disciplane d) {
		this.d = d;
		this.adm = adm;

		setBounds(100, 100, 846, 621);
		setLayout(null);

		JMenuBar menuBar = new JMenuBar();
		menuBar.setBounds(0, 0, 846, 22);
		add(menuBar);

		JMenu mnStudents = new JMenu("Alunos");
		mnStudents.setForeground(Color.BLACK);
		mnStudents.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnStudents);

		JMenuItem mntmViewAllStudents = new JMenuItem("Ver os estudantes cadastrados");
		mntmViewAllStudents.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.vsl.setVisible(true);
				adm.vd.setVisible(false);
				adm.setContentPane(adm.vsl);
			}
		});
		mntmViewAllStudents.setFont(new Font("Arial", Font.PLAIN, 13));
		mnStudents.add(mntmViewAllStudents);

		JMenuItem mntmCdstStudent = new JMenuItem("Cadastrar um estudante");
		mntmCdstStudent.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.cadastroEstudante.setVisible(true);
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
				adm.vpl.setVisible(true);
				adm.vd.setVisible(false);
				adm.setContentPane(adm.vpl);
			}
		});
		mntmViewAllProfessors.setFont(new Font("Arial", Font.PLAIN, 13));
		mnProfessors.add(mntmViewAllProfessors);

		JMenuItem mntmCadstProfessor = new JMenuItem("Cadastrar um professor");
		mntmCadstProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.cadastroProfessor.setVisible(true);
			}
		});
		mntmCadstProfessor.setFont(new Font("Arial", Font.PLAIN, 13));
		mnProfessors.add(mntmCadstProfessor);

		JMenu mnTurmas = new JMenu("Turmas");
		mnTurmas.setForeground(Color.BLACK);
		mnTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnTurmas);

		JMenuItem mntmViewAllTurmas = new JMenuItem("Ver Turmas Cadastradas");
		mntmViewAllTurmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.vtl.setVisible(true);
				adm.vd.setVisible(false);
				adm.setContentPane(adm.vtl);
			}
		});
		mntmViewAllTurmas.setFont(new Font("Arial", Font.PLAIN, 13));
		mnTurmas.add(mntmViewAllTurmas);

		JMenuItem mntmCdstTurma = new JMenuItem("Cadastrar uma turma");
		mntmCdstTurma.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.cadastroTurma.setVisible(true);
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
				adm.vdl.setVisible(true);
				adm.vd.setVisible(false);
				adm.setContentPane(adm.vdl);
			}
		});
		mntmViewAllDisciplinas.setFont(new Font("Arial", Font.PLAIN, 13));
		mnDisciplinas.add(mntmViewAllDisciplinas);

		JMenuItem mntmCdstDisciplina = new JMenuItem("Cadastrar uma disciplina");
		mntmCdstDisciplina.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.cadastroDisciplina.setVisible(true);
			}
		});
		mntmCdstDisciplina.setFont(new Font("Arial", Font.PLAIN, 13));
		mnDisciplinas.add(mntmCdstDisciplina);

		JMenu mnExit = new JMenu("Sair");
		mnExit.setForeground(Color.BLACK);
		mnExit.setFont(new Font("Arial", Font.PLAIN, 13));
		menuBar.add(mnExit);

		JMenuItem mntmReturn = new JMenuItem("Ir para a página principal");
		mntmReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.contentPane.setVisible(true);
				adm.vd.setVisible(false);
				adm.setContentPane(adm.contentPane);
			}
		});
		mntmReturn.setFont(new Font("Arial", Font.PLAIN, 13));
		mnExit.add(mntmReturn);

		JMenuItem mntmExit = new JMenuItem("Sair (Página de Login)");
		mntmExit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.dispose();
				new Login().setVisible(true);
			}
		});
		mntmExit.setFont(new Font("Arial", Font.PLAIN, 13));
		mnExit.add(mntmExit);

		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panel.setBounds(10, 32, 826, 88);
		add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));

		lblNome = new JLabel(d.getNome());
		lblNome.setFont(new Font("Arial", Font.PLAIN, 40));
		panel.add(lblNome);

		JLabel lblID = new JLabel("  -  ID: "+ d.getId());
		lblID.setFont(new Font("Arial", Font.PLAIN, 40));
		panel.add(lblID);

		JScrollPane scrollPaneProfessors = new JScrollPane();
		scrollPaneProfessors.setBounds(10, 130, 287, 481);
		add(scrollPaneProfessors);

		tableProfessors = new JTable();
		tableProfessors.setRowSelectionAllowed(false);
		tableProfessors.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Professores" }));
		tableProfessors.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneProfessors.setViewportView(tableProfessors);

		JScrollPane scrollPaneTurmas = new JScrollPane();
		scrollPaneTurmas.setBounds(307, 130, 287, 481);
		add(scrollPaneTurmas);

		tableTurmas = new JTable();
		tableTurmas.setRowSelectionAllowed(false);
		tableTurmas.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Turmas" }));
		tableTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneTurmas.setViewportView(tableTurmas);

		JButton btnDelete = new JButton("Apagar disciplina");
		btnDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirma = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja excluir essa disciplina permanentemente?");

				if (confirma == JOptionPane.YES_OPTION) {
					deletar();
				}
			}
		});
		btnDelete.setForeground(Color.WHITE);
		btnDelete.setBackground(Color.RED);
		btnDelete.setFont(new Font("Arial", Font.PLAIN, 20));
		btnDelete.setBounds(604, 294, 232, 39);
		add(btnDelete);

		JButton btnReturn = new JButton("Voltar");
		btnReturn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				adm.vd.setVisible(false);
				adm.vdl.setVisible(true);
				adm.setContentPane(adm.vdl);
			}
		});
		btnReturn.setForeground(Color.BLACK);
		btnReturn.setFont(new Font("Arial", Font.PLAIN, 20));
		btnReturn.setBackground(UIManager.getColor("Button.background"));
		btnReturn.setBounds(604, 392, 232, 39);
		add(btnReturn);
		
		JButton btnEdit = new JButton("Editar Disciplina");
		btnEdit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				new EditDisciplane(adm, d).setVisible(true);
			}
		});
		btnEdit.setFont(new Font("Arial", Font.PLAIN, 20));
		btnEdit.setBounds(604, 343, 232, 39);
		add(btnEdit);

		listar();

	}

	public void listar() {
		DefaultTableModel modeloProfessores = new DefaultTableModel(new Object[][] {}, new String[] { "Professores" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		DefaultTableModel modeloTurmas = new DefaultTableModel(new Object[][] {}, new String[] { "Turmas" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readP = "select p.nome from ensina en "
				+ "inner join professores p "
				+ "on p.matricula = en.matricula_professor "
				+ "where en.id_disciplina= ?";

		String readT = "select t.nome from estuda es "
				+ "inner join turmas t "
				+ "on t.id = es.id_turma "
				+ "where es.id_disciplina= ?";

		try {
			con = DAO.conectar();
			
			pst = con.prepareStatement(readP);
			pst.setInt(1, d.getId());
			rs = pst.executeQuery();
			
			while(rs.next()) {
				modeloProfessores.addRow(new Object[] { rs.getString(1) });
			}
			
			tableProfessors.setModel(modeloProfessores);
			
			pst = con.prepareStatement(readT);
			pst.setInt(1, d.getId());
			rs = pst.executeQuery();

			while (rs.next()) {
				modeloTurmas.addRow(new Object[] { rs.getString(1) });
			}

			tableTurmas.setModel(modeloTurmas);

			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e);
		}
	}
	
	private void deletar() {
		String readEstuda = "select id from estuda where id_disciplina= ?";
		
		String readEnsina = "select id from ensina where id_disciplina= ?";
		
		String delete = "delete from disciplinas where id= ?";
		
		try {
			con = DAO.conectar();
			
			pst = con.prepareStatement(readEstuda);
			pst.setInt(1, d.getId());
			rs = pst.executeQuery();
			while (rs.next()) {
				pst = con.prepareStatement("delete from estuda where id= ?");
				pst.setInt(1, rs.getInt(1));
				pst.execute();
			}
			
			pst = con.prepareStatement(readEnsina);
			pst.setInt(1, d.getId());
			rs = pst.executeQuery();
			while(rs.next()) {
				pst = con.prepareStatement("delete from ensina where id= ?");
				pst.setInt(1, rs.getInt(1));
				pst.execute();
			}
			
			pst = con.prepareStatement(delete);
			pst.setInt(1, d.getId());
			
			int confirma = pst.executeUpdate();
			if (confirma == 1) {
				JOptionPane.showMessageDialog(null, "Disciplina deletada com sucesso!");
				

				adm.vdl.listagem();
				adm.listagens();
				adm.status();
				adm.vd.setVisible(false);
				adm.vdl.setVisible(true);
				adm.setContentPane(adm.vdl);
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possivel excluir a disciplina!");
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
		}
	}

}
