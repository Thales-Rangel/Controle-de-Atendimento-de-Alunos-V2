package View.Professor;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Professor;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;

public class PagInicial extends JPanel {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private Professor p;
	
	private static final long serialVersionUID = 1L;
	private JTable tableSolicitations;
	private JTable tableTurmas;
	private JTable tableDisciplinas;
	private JLabel lblSolicitacoes;
	private JLabel lblTurmas;
	private JLabel lblDisciplinas;

	/**
	 * Create the panel.
	 */
	public PagInicial(Professor p) {
		this.p = p;
		
		setBounds(0, 0, 637, 593);

		JPanel panelCabecalho = new JPanel();
		panelCabecalho.setBorder(new LineBorder(new Color(0, 0, 0), 2));

		JLabel lblNome = new JLabel(p.getNome());
		lblNome.setFont(new Font("Arial", Font.PLAIN, 30));

		JLabel lblMatricula = new JLabel(p.getMatricula());
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 25));
		GroupLayout gl_panelCabecalho = new GroupLayout(panelCabecalho);
		gl_panelCabecalho.setHorizontalGroup(
			gl_panelCabecalho.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCabecalho.createSequentialGroup()
					.addGap(8)
					.addGroup(gl_panelCabecalho.createParallelGroup(Alignment.LEADING)
						.addComponent(lblNome, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE)
						.addComponent(lblMatricula, GroupLayout.DEFAULT_SIZE, 597, Short.MAX_VALUE))
					.addGap(8))
		);
		gl_panelCabecalho.setVerticalGroup(
			gl_panelCabecalho.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelCabecalho.createSequentialGroup()
					.addGap(8)
					.addComponent(lblNome, GroupLayout.DEFAULT_SIZE, 36, Short.MAX_VALUE)
					.addGap(10)
					.addComponent(lblMatricula, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGap(10))
		);
		panelCabecalho.setLayout(gl_panelCabecalho);

		JPanel panelRecebidos = new JPanel();
		panelRecebidos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelRecebidos.setLayout(new BoxLayout(panelRecebidos, BoxLayout.X_AXIS));

		lblSolicitacoes = new JLabel("Solicitações recebidas: x   ");
		lblSolicitacoes.setFont(new Font("Arial", Font.PLAIN, 15));
		panelRecebidos.add(lblSolicitacoes);

		lblTurmas = new JLabel("Minhas turmas: y  ");
		lblTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		panelRecebidos.add(lblTurmas);

		lblDisciplinas = new JLabel("Minhas disciplinas: z");
		lblDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		panelRecebidos.add(lblDisciplinas);

		JScrollPane scrollPaneSolicitations = new JScrollPane();

		tableSolicitations = new JTable();
		tableSolicitations
				.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Solicita\u00E7\u00F5es" }) {
					/**
					 * 
					 */
					private static final long serialVersionUID = 1L;
					boolean[] columnEditables = new boolean[] { false };

					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				});
		tableSolicitations.setRowSelectionAllowed(false);
		tableSolicitations.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneSolicitations.setViewportView(tableSolicitations);

		JScrollPane scrollPaneSolicitations_1 = new JScrollPane();

		tableTurmas = new JTable();
		tableTurmas.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Minhas turmas" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableTurmas.setRowSelectionAllowed(false);
		tableTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneSolicitations_1.setViewportView(tableTurmas);

		JScrollPane scrollPaneSolicitations_1_1 = new JScrollPane();

		tableDisciplinas = new JTable();
		tableDisciplinas.setModel(new DefaultTableModel(new Object[][] {}, new String[] { "Minhas disciplinas" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		tableDisciplinas.setRowSelectionAllowed(false);
		scrollPaneSolicitations_1_1.setViewportView(tableDisciplinas);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelCabecalho, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(panelRecebidos, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPaneSolicitations, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
							.addGap(13)
							.addComponent(scrollPaneSolicitations_1, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)
							.addGap(10)
							.addComponent(scrollPaneSolicitations_1_1, GroupLayout.DEFAULT_SIZE, 198, Short.MAX_VALUE)))
					.addGap(10))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(panelCabecalho, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addComponent(panelRecebidos, GroupLayout.PREFERRED_SIZE, 55, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneSolicitations, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
						.addComponent(scrollPaneSolicitations_1, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
						.addComponent(scrollPaneSolicitations_1_1, GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE))
					.addGap(10))
		);
		setLayout(groupLayout);

		listagens();
		contagem();
	}
	
	private void contagem() {
		String readSolicitations = "select count(*) from solicitacoes where matricula_p= '"+ p.getMatricula()+ "'";
		
		String readDisciplinas = "select count(*) from ensina where matricula_professor= '"+ p.getMatricula() +"'";
		
		String readTurmas = "select count(*) from estuda es "
				+ "inner join ensina en "
				+ "on en.id_disciplina = es.id_disciplina "
				+ "where en.matricula_professor= '"+ p.getMatricula() +"'";
		
		try {
			con = DAO.conectar();
			
			pst = con.prepareStatement(readSolicitations);
			rs = pst.executeQuery();
			if (rs.next()) {
				lblSolicitacoes.setText("Solicitações recebidas: "+ rs.getInt(1) +";   ");
			}
			
			pst = con.prepareStatement(readTurmas);
			rs = pst.executeQuery();
			if (rs.next()) {
				lblTurmas.setText("Minhas turmas: "+ rs.getInt(1) +"  ");
			}
			
			pst = con.prepareStatement(readDisciplinas);
			rs = pst.executeQuery();
			if (rs.next()) {
				lblDisciplinas.setText("Minhas disciplinas: "+ rs.getInt(1));
			}
			
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel conectar no servidor:\n"+ e);
		}
	}

	private void listagens() {
		String readSolicitacoes = "select duvida from solicitacoes where respondido= 'F' and matricula_p= '"+p.getMatricula()+"'";
		
		DefaultTableModel modelSolicitacoes = new DefaultTableModel(new Object[][] {},
				new String[] { "Solicita\u00E7\u00F5es" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readTurmas = "select t.nome from estuda es "
				+ "inner join turmas t "
				+ "on t.id = es.id_turma "
				+ "inner join ensina en "
				+ "on en.id_disciplina = es.id_disciplina "
				+ "where en.matricula_professor = '"+p.getMatricula()+"' "
				+ "order by t.nome";
		
		DefaultTableModel modelTurmas = new DefaultTableModel(new Object[][] {}, new String[] { "Minhas turmas" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readDisciplinas = "select d.nome from ensina en "
				+ "inner join disciplinas d "
				+ "on d.id = en.id_disciplina "
				+ "where en.matricula_professor= '"+p.getMatricula()+"' "
				+ "order by d.nome";
		
		DefaultTableModel modelDisciplinas = new DefaultTableModel(new Object[][] {},
				new String[] { "Minhas turmas" }) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		
		try {
			con = DAO.conectar();
			
			pst = con.prepareStatement(readSolicitacoes);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				modelSolicitacoes.addRow(new Object[] { rs.getString(1) });
			}
			
			tableSolicitations.setModel(modelSolicitacoes);
			
			pst = con.prepareStatement(readTurmas);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				modelTurmas.addRow(new Object[] { rs.getString(1) });
			}
			
			tableTurmas.setModel(modelTurmas);
			
			pst = con.prepareStatement(readDisciplinas);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				modelDisciplinas.addRow(new Object[] { rs.getString(1) });
			}
			
			tableDisciplinas.setModel(modelDisciplinas);
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel listar:\n"+ e);
		}

	}
}
