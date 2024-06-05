package View.Professor;

import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.table.DefaultTableModel;

import Models.Disciplane;
import Models.Professor;
import Models.Solicitation;
import Models.Student;
import Utils.DAO;

public class ViewStudentP extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;

	private Student s;
	private ProfessorView pv;
	
	private JTable table;
	
	/**
	 * Create the panel.
	 */
	public ViewStudentP(Student s, ProfessorView pv) {
		this.s = s;
		this.pv = pv;
		
		setBounds(0, 0, 637, 593);
		
		JLabel lblStudent = new JLabel(s.getNome()+ " - " +s.getMatricula());
		lblStudent.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel lblSolicitations = new JLabel("Total de solcitações: 0");
		lblSolicitations.setFont(new Font("Arial", Font.PLAIN, 18));
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement("select count(s.id) from alunos a left join solicitacoes s on s.matricula_a = a.matricula and s.matricula_p= '"+ pv.prof.getMatricula()+"' where a.matricula= '"+s.getMatricula()+"' group by a.matricula");
			rs = pst.executeQuery();
			
			if (rs.next()) {
				lblSolicitations.setText("Total de solicitações: "+ rs.getInt(1));
			} else {
				JOptionPane.showMessageDialog(null, "Não foi possivel contar as solicitações!");
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel contar as solicitações:\n"+ e);
		}
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(lblSolicitations, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(lblStudent, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblStudent)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSolicitations)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 511, Short.MAX_VALUE)
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
				"ID", "Duvida", "Respondido"
			}
		) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.getColumnModel().getColumn(0).setPreferredWidth(20);
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane.setViewportView(table);
		setLayout(groupLayout);
		
		listar();
		
	}
	
	private void listar() {
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"ID", "Duvida", "Respondido"
				}
			) {
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] {
					false, false, false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		
		String readSolicitations = "select s.id, s.duvida, s.respondido from solicitacoes s "
				+ "where matricula_a= '"+ s.getMatricula() +"' and matricula_p= '"+ pv.prof.getMatricula()+ "'";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readSolicitations);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				if (rs.getString(3).equals("T")) {
					model.addRow(new Object[] { rs.getInt(1), rs.getString(2), "Respondido"});
				} else {
					model.addRow(new Object[] {rs.getInt(1), rs.getString(2), "Sem resposta"});
				}
			}
			
			if (model.getRowCount() == 0)
				model.addRow(new Object[] { "", "Nenhuma solicitação", "" });
			
			table.setModel(model);
			table.getColumnModel().getColumn(0).setPreferredWidth(20);
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar as solicitações do alunos:\n"+e);
		}
	}
	
	private void select() {
		try {
			String read = "select * from solicitacoes where id= ?";
			
			con = DAO.conectar();
			pst = con.prepareStatement(read);
			pst.setInt(1, (int) table.getValueAt(table.getSelectedRow(), 0));
			rs = pst.executeQuery();
			if(rs.next()) {
				Solicitation solicitacao = new Solicitation(rs.getInt(1), new Student(rs.getString(2)), new Professor(rs.getString(3)), new Disciplane(rs.getInt(4)), rs.getString(5), (rs.getString(7).equals("T")));
				
				pv.viewPanel.setVisible(false);
				pv.viewPanel = new ViewSolicitationP(solicitacao, pv);
				pv.contentPane.add(pv.viewPanel);
				pv.dimensionar();
			}
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar a solicitação:\n"+e);
		}
	}
}
