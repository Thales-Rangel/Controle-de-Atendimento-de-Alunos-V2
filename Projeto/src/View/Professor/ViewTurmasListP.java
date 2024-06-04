package View.Professor;

import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

import Models.DAO;
import Models.Professor;
import Models.Turma;
import Utils.Validador;

public class ViewTurmasListP extends JPanel {

	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	private static final long serialVersionUID = 1L;
	
	private Professor p;
	private ProfessorView pv;
	
	private JTextField textField;
	private JTable table;

	/**
	 * Create the panel.
	 */
	public ViewTurmasListP(Professor p, ProfessorView pv) {
		this.p = p;
		this.pv = pv;
		
		setBounds(0, 0, 637, 593);
		
		JLabel lblTitle = new JLabel("Minhas Turmas");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 20));
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				buscar();
			}
		});
		textField.setColumns(10);
		textField.setDocument(new Validador(20));
		;
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ViewTurmasListP.class.getResource("/img/Search_Icon.png")));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
							.addGap(75)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
						.addGroup(Alignment.LEADING, groupLayout.createParallelGroup(Alignment.BASELINE)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 26, Short.MAX_VALUE)
							.addComponent(lblNewLabel))
						.addComponent(lblTitle, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 535, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				select();
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Turma", "Qnt. Alunos", "Solicitações"
			}
		) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
		
		buscar();

	}
	
	private void buscar() {
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Turma", "Qnt. Alunos", "Solicitações"
				}
			) {
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] {
					false, false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			
		String read = "select t.*, count(distinct a.matricula), count(distinct s.id) from estuda es "
				+ "join turmas t "
				+ "on t.id = es.id_turma "
				+ "join ensina en "
				+ "on en.id_disciplina = es.id_disciplina "
				+ "left join alunos a "
				+ "on a.id_turma = t.id "
				+ "left join solicitacoes s "
				+ "on s.matricula_a = a.matricula and en.matricula_professor = s.matricula_p "
				+ "where en.matricula_professor= '"+p.getMatricula()+"' and t.nome like '"+textField.getText().trim()+"%'"
				+ "group by t.id "
				+ "order by t.nome";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(read);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4) });
			}
			
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar as turmas:\n"+ e);
		}
	}
	
	private void select() {
		pv.viewPanel.setVisible(false);
		pv.viewPanel = new ViewTurmaP(new Turma((int) table.getValueAt(table.getSelectedRow(), 0), (String) table.getValueAt(table.getSelectedRow(), 1)), pv);
		pv.contentPane.add(pv.viewPanel);
		pv.dimensionar();
	}
}
