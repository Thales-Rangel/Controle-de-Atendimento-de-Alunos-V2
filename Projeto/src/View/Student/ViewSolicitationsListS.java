package View.Student;

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
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Solicitation;
import Models.Student;

public class ViewSolicitationsListS extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	
	private Student s;
	private JLabel lblFilterDisciplane;
	private JRadioButton rdbtnAllResp;
	private JRadioButton rdbtnNoResp;
	private JRadioButton rdbtnResp;
	private JLabel lblFilter;
	private JLabel lblSearch;
	private JTextField textField;
	private JButton btnNewButton;
	private JLabel lblSearchProfessor;
	private JScrollPane scrollPaneDisciplinas;
	private JTextField textFieldSearchProfessor;
	private JTable table;
	private JPanel panelSearch;
	private JList<String> list;

	public ViewSolicitationsListS(StudentView sv) {
		this.s = sv.s;
		
		setBounds(100, 1000, 630, 593);
		
		panelSearch = new JPanel();
		panelSearch.setBackground(SystemColor.activeCaptionBorder);
		panelSearch.setBorder(new EmptyBorder(2, 2, 2, 2));
		
		JLabel lblNewLabel = new JLabel("Solicitações enviadas");
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 20));
		
		JScrollPane scrollPaneSolicitations = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE))
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(12)
							.addComponent(scrollPaneSolicitations, GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panelSearch, GroupLayout.PREFERRED_SIZE, 212, GroupLayout.PREFERRED_SIZE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(panelSearch, GroupLayout.DEFAULT_SIZE, 593, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addGap(10)
					.addComponent(lblNewLabel)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPaneSolicitations, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				sv.setViewPanel(new ViewSolicitationS(new Solicitation((int) table.getValueAt(table.getSelectedRow(), 0))));
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Professor", "Dúvida", "Disciplina", "Respondido"
			}
		) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(18);
		table.getColumnModel().getColumn(1).setPreferredWidth(120);
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneSolicitations.setViewportView(table);
		
		lblSearch = new JLabel("Mecanismos de busca");
		lblSearch.setHorizontalAlignment(SwingConstants.CENTER);
		lblSearch.setFont(new Font("Arial", Font.BOLD, 17));
		
		lblFilter = new JLabel("FIltrar solicitações por:");
		lblFilter.setFont(new Font("Arial", Font.PLAIN, 15));
		
		rdbtnResp = new JRadioButton("Respondidas");
		rdbtnResp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!rdbtnResp.isSelected()) {
					rdbtnNoResp.setSelected(true);
				} else {
					rdbtnNoResp.setSelected(false);
					rdbtnAllResp.setSelected(false);
				}
				
				busca();
			}
		});
		rdbtnResp.setFont(new Font("Arial", Font.PLAIN, 15));
		rdbtnResp.setBackground(SystemColor.activeCaptionBorder);
		
		rdbtnNoResp = new JRadioButton("Não respondidas");
		rdbtnNoResp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!rdbtnNoResp.isSelected()) {
					rdbtnNoResp.setSelected(true);
				} else {
					rdbtnResp.setSelected(false);
					rdbtnAllResp.setSelected(false);
				}
				
				busca();
			}
		});
		rdbtnNoResp.setSelected(true);
		rdbtnNoResp.setBackground(SystemColor.activeCaptionBorder);
		rdbtnNoResp.setFont(new Font("Arial", Font.PLAIN, 15));
		
		rdbtnAllResp = new JRadioButton("Todas");
		rdbtnAllResp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!rdbtnAllResp.isSelected()) {
					rdbtnNoResp.setSelected(true);
				} else {
					rdbtnResp.setSelected(false);
					rdbtnNoResp.setSelected(false);
				}
				
				busca();
			}
		});
		rdbtnAllResp.setBackground(SystemColor.activeCaptionBorder);
		rdbtnAllResp.setFont(new Font("Arial", Font.PLAIN, 15));
		
		lblFilterDisciplane = new JLabel("FIltrar por disciplina:");
		lblFilterDisciplane.setFont(new Font("Arial", Font.PLAIN, 15));
		
		textField = new JTextField();
		textField.setEditable(false);
		textField.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				listarDisciplinas();
			}
		});
		textField.setFont(new Font("Arial", Font.PLAIN, 15));
		textField.setColumns(10);
		
		btnNewButton = new JButton("");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarDisciplinas();
			}
		});
		btnNewButton.setIcon(new ImageIcon(ViewSolicitationsListS.class.getResource("/img/seta_de_itens_icon.png")));
		
		lblSearchProfessor = new JLabel("Pesquisar por professor:");
		lblSearchProfessor.setFont(new Font("Arial", Font.PLAIN, 15));
		
		scrollPaneDisciplinas = new JScrollPane();
		
		list = new JList<String>();
		list.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				listarDisciplinas();
				textField.setText(list.getSelectedValue());
				busca();
			}
		});
		list.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneDisciplinas.setViewportView(list);
		
		scrollPaneDisciplinas.setVisible(false);
		list.setVisible(false);
		
		textFieldSearchProfessor = new JTextField();
		textFieldSearchProfessor.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				busca();
			}
		});
		textFieldSearchProfessor.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldSearchProfessor.setColumns(10);
		
		dimensionar();
		setLayout(groupLayout);
		
		busca();

	}
	
	private void listarDisciplinas() {
		if (!(scrollPaneDisciplinas.isVisible() || list.isVisible())) {
			DefaultListModel<String> model = new DefaultListModel<String>();
			
			for(int i = 0; i < s.getTurma().getDisciplinas().size(); i++) {
				model.addElement(s.getTurma().getDisciplinas().get(i).getNome());
			}
			
			list.setModel(model);
			
			scrollPaneDisciplinas.setVisible(true);
			list.setVisible(true);
			btnNewButton.setIcon(new ImageIcon(ViewSolicitationsListS.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
		} else {
			textField.setText("");
			scrollPaneDisciplinas.setVisible(false);
			list.setVisible(false);
			btnNewButton.setIcon(new ImageIcon(ViewSolicitationsListS.class.getResource("/img/seta_de_itens_icon.png")));
			busca();
		}
		
		dimensionar();
	}
	
	private void busca() {
		try {
			DefaultTableModel model = new DefaultTableModel(
					new Object[][] {
					},
					new String[] {
						"ID", "Professor", "Dúvida", "Disciplina", "Respondido"
					}
				) {
					private static final long serialVersionUID = 1L;
					boolean[] columnEditables = new boolean[] {
						false, false, false, false, false
					};
					public boolean isCellEditable(int row, int column) {
						return columnEditables[column];
					}
				};
			
				String read = "select s.id, p.nome, s.duvida, d.nome, s.respondido from solicitacoes s "
						+ "join professores p on p.matricula = s.matricula_p "
						+ "join disciplinas d on d.id = s.id_disciplina "
						+ "where s.matricula_a= '"+ s.getMatricula()+"' ";
				
				if(!rdbtnAllResp.isSelected()) {
					if (rdbtnResp.isSelected()) {
						read += "and s.respondido= 'T' ";
					} else {
						read += "and s.respondido= 'F' ";
					}
				}
				
				if(!textField.getText().isBlank()) {
					read += "and d.nome= '"+ textField.getText() +"' ";
				}
				
				if(!textFieldSearchProfessor.getText().isBlank()) {
					read += "and p.nome like '"+ textFieldSearchProfessor.getText().trim()+"%' ";
				}
				
				read += "order by s.id desc";
				
				con = DAO.conectar();
				pst = con.prepareStatement(read);
				rs = pst.executeQuery();
				
				while(rs.next()) {
					if (rs.getString(5).equals("T")) {
						model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), "Respondido" });
					} else {
						model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4), "Sem resposta" });
					}
				}
				
				con.close();
				
				table.setModel(model);
				table.getColumnModel().getColumn(0).setPreferredWidth(18);
				table.getColumnModel().getColumn(2).setPreferredWidth(120);
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar as solicitações:\n"+ e);
		}
	}
	
	private void dimensionar() {
		GroupLayout gl_panelSearch = new GroupLayout(panelSearch);
		gl_panelSearch.setHorizontalGroup(
			gl_panelSearch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSearch.createSequentialGroup()
					.addGroup(gl_panelSearch.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_panelSearch.createSequentialGroup()
							.addGap(10)
							.addGroup(gl_panelSearch.createParallelGroup(Alignment.LEADING)
								.addComponent(lblSearch, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblFilter, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnResp, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnNoResp, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addComponent(rdbtnAllResp, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblFilterDisciplane, GroupLayout.PREFERRED_SIZE, 194, GroupLayout.PREFERRED_SIZE)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, 165, GroupLayout.PREFERRED_SIZE)
								.addComponent(scrollPaneDisciplinas, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_panelSearch.createSequentialGroup()
									.addGap(165)
									.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
								.addComponent(lblSearchProfessor, GroupLayout.PREFERRED_SIZE, 190, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_panelSearch.createSequentialGroup()
							.addContainerGap()
							.addComponent(textFieldSearchProfessor, GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)))
					.addContainerGap())
		);
		gl_panelSearch.setVerticalGroup(
			gl_panelSearch.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelSearch.createSequentialGroup()
					.addGap(10)
					.addComponent(lblSearch)
					.addGap(10)
					.addComponent(lblFilter)
					.addGap(6)
					.addComponent(rdbtnResp)
					.addComponent(rdbtnNoResp)
					.addComponent(rdbtnAllResp)
					.addGap(6)
					.addComponent(lblFilterDisciplane)
					.addGap(6)
					.addGroup(gl_panelSearch.createParallelGroup(Alignment.LEADING)
						.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panelSearch.createSequentialGroup()
							.addGap(22)
							.addComponent(scrollPaneDisciplinas, GroupLayout.PREFERRED_SIZE, 139, GroupLayout.PREFERRED_SIZE))
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 24, GroupLayout.PREFERRED_SIZE))
					.addGap(10)
					.addComponent(lblSearchProfessor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(textFieldSearchProfessor, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
					.addContainerGap(199, Short.MAX_VALUE))
		);
		panelSearch.setLayout(gl_panelSearch);
	}
}
