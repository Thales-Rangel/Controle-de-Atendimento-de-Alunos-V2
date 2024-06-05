package View.Student;

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
import javax.swing.table.DefaultTableModel;

import Models.Disciplane;
import Models.Student;
import Utils.DAO;

public class ViewDisciplanesListS extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	
	private Student s;
	
	private JTextField textField;
	private JTable table;

	public ViewDisciplanesListS(StudentView sv) {
		this.s = sv.s;
		
		setBounds(0, 0, 637, 593);
		
		JLabel lblTitle = new JLabel("Minhas disciplinas");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 20));
		
		textField = new JTextField();
		textField.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				buscar();
			}
		});
		textField.setFont(new Font("Arial", Font.PLAIN, 15));
		textField.setColumns(10);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(ViewDisciplanesListS.class.getResource("/img/Search_Icon.png")));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblTitle, GroupLayout.DEFAULT_SIZE, 206, Short.MAX_VALUE)
							.addGap(50)
							.addComponent(lblNewLabel, GroupLayout.PREFERRED_SIZE, 39, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(textField, GroupLayout.DEFAULT_SIZE, 312, Short.MAX_VALUE)))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(textField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblTitle)))
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 539, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				sv.setViewPanel(new ViewDisciplaneS(new Disciplane( (int) table.getValueAt(table.getSelectedRow(), 0)), sv));
			}
		});
		table.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Disciplina", "Qnt. Professores", "Qnt. Solicitações"
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
					"ID", "Disciplina", "Qnt. Professores", "Qnt. Solicitações"
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
			
			String select = "select d.*, count(distinct en.matricula_professor), count(distinct s.id) from estuda es "
					+ "join disciplinas d on d.id = es.id_disciplina "
					+ "left join ensina en on en.id_disciplina = d.id "
					+ "left join solicitacoes s on s.matricula_p = en.matricula_professor and s.id_disciplina = d.id and s.matricula_a= '"+ s.getMatricula()+"' "
					+ "where es.id_turma= '"+ s.getId_turma()+"' and d.nome like '"+ textField.getText().trim()+"%' "
					+ "group by d.id order by d.nome";
			
			try {
				con = DAO.conectar();
				pst = con.prepareStatement(select);
				rs = pst.executeQuery();
				
				while(rs.next())
					model.addRow(new Object[] { rs.getInt(1), rs.getString(2), rs.getInt(3), rs.getInt(4) });
				
				table.setModel(model);
				table.getColumnModel().getColumn(0).setPreferredWidth(15);
				table.getColumnModel().getColumn(1).setPreferredWidth(320);
				table.getColumnModel().getColumn(2).setPreferredWidth(70);
				table.getColumnModel().getColumn(3).setPreferredWidth(70);
				
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Não foi possivel buscar as disciplinas:\n"+e);
			}
	}
}
