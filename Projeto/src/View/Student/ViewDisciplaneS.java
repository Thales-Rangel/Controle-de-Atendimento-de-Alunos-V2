package View.Student;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import Models.Disciplane;
import Models.Professor;
import Utils.DAO;

public class ViewDisciplaneS extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	
	private JTable table;
	
	public ViewDisciplaneS(Disciplane d, StudentView sv) {
		
		setBounds(0, 0, 637, 593);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 70, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 493, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		table = new JTable();
		table.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				sv.setViewPanel(new ViewProfessorS(new Professor((String) table.getValueAt(table.getSelectedRow(), 0)), sv));
			}
		});
		DefaultTableModel model = new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Matricula", "Professor", "Solicitações"
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
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		
		String select = "select p.matricula, p.nome, count(s.id) from professores p "
				+ "join ensina en on en.matricula_professor = p.matricula "
				+ "left join solicitacoes s on s.matricula_p = p.matricula and s.id_disciplina = en.id_disciplina and s.matricula_a= '"+ sv.s.getMatricula()+"' "
				+ "where en.id_disciplina= '"+ d.getId()+"' "
				+ "group by p.matricula order by p.nome";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(select);
			rs = pst.executeQuery();
			
			while(rs.next())
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getInt(3) });
			
			table.setModel(model);
			
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar os professores:\n"+e);
		}
		
		scrollPane.setViewportView(table);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblDisciplane = new JLabel(d.getNome()+ " - ID: "+d.getId());
		lblDisciplane.setFont(new Font("Arial", Font.PLAIN, 20));
		panel.add(lblDisciplane);
		setLayout(groupLayout);

	}
}
