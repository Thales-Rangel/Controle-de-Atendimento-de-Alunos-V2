package View.Student;

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

import Models.DAO;
import Models.Professor;
import Models.Solicitation;

public class ViewProfessorS extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JTable table;

	public ViewProfessorS(Professor p, StudentView sv) {
		setBounds(0, 0, 637, 593);
		
		JLabel lblProfessor = new JLabel(p.getNome()+ " - "+ p.getMatricula());
		lblProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		
		JLabel lblSolicitations = new JLabel("Total de solicitações enviadas: 0");
		lblSolicitations.setFont(new Font("Arial", Font.PLAIN, 18));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(lblSolicitations, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(lblProfessor, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblProfessor)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(lblSolicitations)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 515, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				sv.setViewPanel(new ViewSolicitationS(new Solicitation((int) table.getValueAt(table.getSelectedRow(), 0))));
			}
		});
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"ID", "Dúvida", "Respondido"
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
		
		String select = "select id, duvida, respondido from solicitacoes where matricula_p= '"+ p.getMatricula()+"' and matricula_a= '"+ sv.s.getMatricula()+"'";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(select);
			rs = pst.executeQuery();
			
			int count = 0;
			while(rs.next()) {
				if (rs.getString(3).equals("T")) {
					model.addRow(new Object[] { rs.getInt(1), rs.getString(2), "Respondido" });
				} else {
					model.addRow(new Object[] { rs.getInt(1), rs.getString(2), "Não respondido" });
				}
				count++;
			}
			
			table.setModel(model);
			lblSolicitations.setText("Total de solicitações enviadas: "+ count);
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar as suas solicitações a esse professor(a):"+e);
		}
		
		table.getColumnModel().getColumn(0).setPreferredWidth(35);
		table.getColumnModel().getColumn(0).setMinWidth(25);
		table.getColumnModel().getColumn(0).setMaxWidth(35);
		table.getColumnModel().getColumn(1).setPreferredWidth(300);
		table.getColumnModel().getColumn(1).setMinWidth(300);
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane.setViewportView(table);
		setLayout(groupLayout);

	}

}
