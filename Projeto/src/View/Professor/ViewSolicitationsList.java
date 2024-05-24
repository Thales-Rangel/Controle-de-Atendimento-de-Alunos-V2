package View.Professor;

import java.awt.Font;
import java.awt.SystemColor;
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
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Disciplane;
import Models.Professor;
import Models.Solicitation;
import Models.Student;

public class ViewSolicitationsList extends JPanel {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private Professor p;
	private ProfessorView pv;
	private JTextField textFieldDisciplinas;
	private JTextField textFieldSearch;
	private JTextField textFieldTurmas;
	private JTable table;
	private JList<Object> listDisciplinas;
	private JList<Object> listTurmas;
	private JScrollPane scrollPaneDisciplinas;
	private JScrollPane scrollPaneTurmas;
	private JRadioButton rdbtnResp;
	private JRadioButton rdbtnNaoResp;
	private JRadioButton rdbtnAllResp;
	private JButton btnDisciplinas;
	private JButton btnTurmas;
	private JLabel lblBusca;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel_3;
	private JScrollPane scrollPane;
	private JLabel lblTitle;
	private JPanel panel;
	private JLabel lblNewLabel_4;

	/**
	 * Create the panel.
	 */
	public ViewSolicitationsList(Professor p, ProfessorView pv) {
		this.p = p;
		this.pv = pv;

		setBounds(0, 0, 637, 593);

		lblTitle = new JLabel("Solicitações recebidas");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 20));

		scrollPane = new JScrollPane();

		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				selecionar();
			}
		});
		table.setModel(new DefaultTableModel(new Object[][] {},
				new String[] { "Aluno", "Dúvida", "Disciplina", "Respondido" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane.setViewportView(table);

		panel = new JPanel();
		panel.setBackground(SystemColor.activeCaptionBorder);

		lblBusca = new JLabel("Mecanismos de Busca");
		lblBusca.setHorizontalAlignment(SwingConstants.CENTER);
		lblBusca.setFont(new Font("Arial", Font.BOLD, 19));

		textFieldDisciplinas = new JTextField();
		textFieldDisciplinas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!(scrollPaneDisciplinas.isVisible() || listDisciplinas.isVisible())) {
					listarSelect(true);
				} else {
					textFieldDisciplinas.setText("");
					scrollPaneDisciplinas.setVisible(false);
					listDisciplinas.setVisible(false);
					btnDisciplinas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_icon.png")));
					
					buscar();
					dimensionar();
				}
			}
		});

		textFieldDisciplinas.setEditable(false);
		textFieldDisciplinas.setColumns(10);

		lblNewLabel_1 = new JLabel("Filtrar por disciplina:");
		lblNewLabel_1.setFont(new Font("Arial", Font.PLAIN, 15));

		btnDisciplinas = new JButton("");
		btnDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(scrollPaneDisciplinas.isVisible() || listDisciplinas.isVisible())) {
					listarSelect(true);
				} else {
					textFieldDisciplinas.setText("");
					scrollPaneDisciplinas.setVisible(false);
					listDisciplinas.setVisible(false);
					btnDisciplinas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_icon.png")));
					
					buscar();
					dimensionar();
				}
			}
		});
		btnDisciplinas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_icon.png")));

		lblNewLabel = new JLabel("FIltrar solicitações por:");
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 15));

		rdbtnResp = new JRadioButton("Respondidas");
		rdbtnResp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!rdbtnResp.isSelected()) {
					rdbtnNaoResp.setSelected(true);
				} else {
					rdbtnNaoResp.setSelected(false);
					rdbtnAllResp.setSelected(false);
				}
				buscar();
			}
		});
		rdbtnResp.setBackground(SystemColor.activeCaptionBorder);
		rdbtnResp.setFont(new Font("Arial", Font.PLAIN, 15));

		rdbtnNaoResp = new JRadioButton("Não respondidas");
		rdbtnNaoResp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!rdbtnNaoResp.isSelected()) {
					rdbtnNaoResp.setSelected(true);
				} else {
					rdbtnResp.setSelected(false);
					rdbtnAllResp.setSelected(false);
				}
				buscar();
			}
		});
		rdbtnNaoResp.setSelected(true);
		rdbtnNaoResp.setBackground(SystemColor.activeCaptionBorder);
		rdbtnNaoResp.setFont(new Font("Arial", Font.PLAIN, 15));

		rdbtnAllResp = new JRadioButton("Todas");
		rdbtnAllResp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!rdbtnAllResp.isSelected()) {
					rdbtnNaoResp.setSelected(true);
				} else {
					rdbtnResp.setSelected(false);
					rdbtnNaoResp.setSelected(false);
				}
				buscar();
			}
		});
		rdbtnAllResp.setBackground(SystemColor.activeCaptionBorder);
		rdbtnAllResp.setFont(new Font("Arial", Font.PLAIN, 15));

		scrollPaneDisciplinas = new JScrollPane();

		listDisciplinas = new JList<Object>();
		listDisciplinas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldDisciplinas.setText((String) listDisciplinas.getSelectedValue());
				
				scrollPaneDisciplinas.setVisible(false);
				listDisciplinas.setVisible(false);
				btnTurmas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_icon.png")));
				dimensionar();
				
				buscar();
			}
		});
		scrollPaneDisciplinas.setViewportView(listDisciplinas);

		listDisciplinas.setVisible(false);
		scrollPaneDisciplinas.setVisible(false);

		lblNewLabel_3 = new JLabel("Pesquisar pelos alunos:");
		lblNewLabel_3.setFont(new Font("Arial", Font.PLAIN, 15));

		textFieldSearch = new JTextField();
		textFieldSearch.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				buscar();
			}
		});
		textFieldSearch.setColumns(10);

		lblNewLabel_2 = new JLabel("Filtrar por turma:");
		lblNewLabel_2.setFont(new Font("Arial", Font.PLAIN, 15));

		textFieldTurmas = new JTextField();
		textFieldTurmas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (!(scrollPaneTurmas.isVisible() || listTurmas.isVisible())) {
					listarSelect(false);
				} else {
					textFieldTurmas.setText("");
					scrollPaneTurmas.setVisible(false);
					listTurmas.setVisible(false);
					btnTurmas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_icon.png")));
					
					buscar();
					dimensionar();
				}
			}
		});
		textFieldTurmas.setEditable(false);
		textFieldTurmas.setColumns(10);

		btnTurmas = new JButton("");
		btnTurmas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(scrollPaneTurmas.isVisible() || listTurmas.isVisible())) {
					listarSelect(false);
				} else {
					textFieldTurmas.setText("");
					scrollPaneTurmas.setVisible(false);
					listTurmas.setVisible(false);
					btnTurmas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_icon.png")));
					
					buscar();
					dimensionar();
				}
			}
		});
		btnTurmas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_icon.png")));

		scrollPaneTurmas = new JScrollPane();

		listTurmas = new JList<Object>();
		listTurmas.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				textFieldTurmas.setText((String) listTurmas.getSelectedValue());
				
				scrollPaneTurmas.setVisible(false);
				listTurmas.setVisible(false);
				btnTurmas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_icon.png")));
				dimensionar();
				
				buscar();
			}
		});
		scrollPaneTurmas.setViewportView(listTurmas);

		listTurmas.setVisible(false);
		scrollPaneTurmas.setVisible(false);

		lblNewLabel_4 = new JLabel("");
		dimensionar();

		listar();

	}

	private void listar() {
		DefaultTableModel model = new DefaultTableModel(new Object[][] {

		}, new String[] { "ID", "Aluno", "Dúvida", "Disciplina", "Respondido" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};

		String readSolicitacoes = "select s.id, a.nome, s.duvida, d.nome, s.respondido from solicitacoes s "
				+ "join alunos a on a.matricula = s.matricula_a "
				+ "join disciplinas d on d.id = s.id_disciplina "
				+ "where s.matricula_p= '" + p.getMatricula() + "' and s.respondido= 'F' "
				+ "order by s.id desc";

		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readSolicitacoes);
			rs = pst.executeQuery();
			while (rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "Sem resposta" });
			}

			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(18);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);

			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel listar suas solicitações!\n" + e);
		}
	}

	private void listarSelect(boolean DoT) {
		DefaultListModel<Object> model = new DefaultListModel<Object>();

		try {
			if (DoT) {
				for (int i = 0; i < p.getDisciplinas().size(); i++) {
					model.addElement(p.getDisciplinas().get(i).getNome());
				}

				btnDisciplinas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
				listDisciplinas.setModel(model);
				scrollPaneDisciplinas.setVisible(true);
				listDisciplinas.setVisible(true);
				
				dimensionar();
			} else {
				String readTurmas = "select t.nome from ensina en "
						+ "join estuda es "
						+ "on en.id_disciplina = es.id_disciplina "
						+ "join turmas t "
						+ "on t.id = es.id_turma "
						+ "where en.matricula_professor= '"+p.getMatricula()+"' "
						+ "group by t.id "
						+ "order by t.nome";
				
				con = DAO.conectar();
				pst = con.prepareStatement(readTurmas);
				rs = pst.executeQuery();
				while (rs.next()) {
					model.addElement(rs.getString(1));
				}
				
				listTurmas.setModel(model);			
				
				btnTurmas.setIcon(new ImageIcon(ViewSolicitationsList.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
				listTurmas.setVisible(true);
				scrollPaneTurmas.setVisible(true);
				
				dimensionar();
				
				con.close();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel listar:\n" + e);
		}
	}
	
	private void buscar() {
		DefaultTableModel model = new DefaultTableModel(new Object[][] {

		}, new String[] { "ID", "Aluno", "Dúvida", "Disciplina", "Respondido" }) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] { false, false, false, false, false };

			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		};
		
		String busca = "select s.id, a.nome, s.duvida, d.nome, s.respondido from solicitacoes s "
				+ "join alunos a "
				+ "on a.matricula = s.matricula_a "
				+ "join disciplinas d "
				+ "on d.id = s.id_disciplina "
				+ "where s.matricula_p= '"+p.getMatricula()+"' ";
		try {
			con = DAO.conectar();
			
			if(!rdbtnAllResp.isSelected()) {
				if (rdbtnResp.isSelected()) {
					busca += "and s.respondido= 'T' ";
				} else {
					busca += "and s.respondido= 'F' ";
				}
			}
		
			if (!textFieldDisciplinas.getText().isEmpty()) {
				for (int i = 0; i < p.getDisciplinas().size(); i++) {
					if (p.getDisciplinas().get(i).getNome().equals(textFieldDisciplinas.getText())) {
						busca += "and s.id_disciplina= '"+p.getDisciplinas().get(i).getId()+"' ";
						break;
					}
				}
			}
		
			if (!textFieldTurmas.getText().isEmpty()) {
				pst = con.prepareStatement("select id from turmas where nome= '"+textFieldTurmas.getText()+"'");
				rs = pst.executeQuery();
				if (rs.next()) {
					busca += "and a.id_turma= '"+ rs.getInt(1)+"' ";
				}
			}
			
			if (!textFieldSearch.getText().isBlank()) {
				busca += "and a.nome like '"+textFieldSearch.getText().trim()+"%' ";
			}
			
			busca += "order by s.id desc";
			
			pst = con.prepareStatement(busca);
			rs = pst.executeQuery();
			
			while (rs.next()) {
				if (rs.getString(5).equals("T")) {
					model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "Respondido" });
				} else {
					model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), "Não respondido" });
				}
			}
			
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(18);
			table.getColumnModel().getColumn(2).setPreferredWidth(120);
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel fazer a busca:\n"+e);
		}
	}
	
	private void selecionar() {
		String readSolicitation = "select s.id, a.matricula, d.id, s.duvida, s.respondido "
				+ "from solicitacoes s "
				+ "join alunos a "
				+ "on a.matricula = s.matricula_a "
				+ "join disciplinas d "
				+ "on d.id = s.id_disciplina "
				+ "where s.id= '"+ table.getValueAt(table.getSelectedRow(), 0) +"'";
		try {
			Solicitation solicitacao = null;
			
			con = DAO.conectar();
			pst = con.prepareStatement(readSolicitation);
			rs = pst.executeQuery();
			if (rs.next()) {
				solicitacao = new Solicitation(rs.getInt(1), new Student(rs.getString(2)), p, new Disciplane(rs.getInt(3)), rs.getString(4), (rs.getString(5).equals("T")));
			}
				
			if (solicitacao != null) {
				pv.viewPanel.setVisible(false);
				pv.viewPanel = new ViewSolicitation(solicitacao, pv);
				pv.contentPane.add(pv.viewPanel);
				pv.dimensionar();
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possivel mostrar a solicitação!");
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel selecionar a solicitação:\n"+e);
			e.printStackTrace();
		}
		
	}
	
	private void dimensionar() {
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(8)
					.addComponent(lblBusca, GroupLayout.DEFAULT_SIZE, 224, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnResp, GroupLayout.PREFERRED_SIZE, 143, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(83, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(63, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnNaoResp, GroupLayout.PREFERRED_SIZE, 160, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(66, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(rdbtnAllResp, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(139, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 159, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(63, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(13)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldDisciplinas, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
						.addComponent(scrollPaneDisciplinas, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(174)
							.addComponent(btnDisciplinas, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(20, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(14)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textFieldTurmas, GroupLayout.PREFERRED_SIZE, 173, GroupLayout.PREFERRED_SIZE)
							.addGap(1)
							.addComponent(btnTurmas, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneTurmas, GroupLayout.PREFERRED_SIZE, 199, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(19, Short.MAX_VALUE))
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblNewLabel_3, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(textFieldSearch, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)))
							.addGap(18))
						.addGroup(gl_panel.createSequentialGroup()
							.addGroup(gl_panel.createParallelGroup(Alignment.TRAILING)
								.addGroup(gl_panel.createSequentialGroup()
									.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 184, GroupLayout.PREFERRED_SIZE)
									.addGap(15))
								.addComponent(lblNewLabel_4))
							.addContainerGap(23, Short.MAX_VALUE))))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblBusca)
					.addGap(18)
					.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(rdbtnResp, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnNaoResp, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(rdbtnAllResp, GroupLayout.PREFERRED_SIZE, 21, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldDisciplinas, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(23)
							.addComponent(scrollPaneDisciplinas, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnDisciplinas, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldTurmas, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnTurmas, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(23)
							.addComponent(scrollPaneTurmas, GroupLayout.PREFERRED_SIZE, 81, GroupLayout.PREFERRED_SIZE)))
					.addGap(10)
					.addComponent(lblNewLabel_3, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldSearch, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
					.addGap(143)
					.addComponent(lblNewLabel_4, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addContainerGap())
		);
		panel.setLayout(gl_panel);
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 151,
												Short.MAX_VALUE)
										.addGap(243))
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 203, Short.MAX_VALUE)
										.addGap(192).addComponent(panel, GroupLayout.PREFERRED_SIZE, 232,
												GroupLayout.PREFERRED_SIZE)))));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panel, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 594, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup().addGap(10)
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup().addGap(37).addComponent(
										scrollPane, GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE))
								.addComponent(lblTitle, GroupLayout.PREFERRED_SIZE, 39,
										GroupLayout.PREFERRED_SIZE))));
		setLayout(groupLayout);
	}
}
