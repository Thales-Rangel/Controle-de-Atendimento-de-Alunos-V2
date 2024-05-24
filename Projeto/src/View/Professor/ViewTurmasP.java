package View.Professor;

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

import Models.DAO;
import Models.Student;
import Models.Turma;

public class ViewTurmasP extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;
	private JTable table;

	private Turma t;
	private ProfessorView pv;
	private JLabel lblAlunos;
	private JLabel lblSolicitacoes;
	/**
	 * Create the panel.
	 */
	public ViewTurmasP(Turma t, ProfessorView pv) {
		this.t = t;
		this.pv = pv;
		
		setBounds(0, 0, 637, 593);
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JPanel panel_1 = new JPanel();
		panel_1.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JScrollPane scrollPane = new JScrollPane();
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addComponent(scrollPane, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(panel_1, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(panel, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 51, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 460, Short.MAX_VALUE)
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
				"Matricula", "Aluno"
			}
		) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false, false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		table.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPane.setViewportView(table);
		panel_1.setLayout(new BoxLayout(panel_1, BoxLayout.X_AXIS));
		
		lblAlunos = new JLabel("Total de alunos: ");
		lblAlunos.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_1.add(lblAlunos);
		
		lblSolicitacoes = new JLabel("   Total de solicitações: ");
		lblSolicitacoes.setFont(new Font("Arial", Font.PLAIN, 20));
		panel_1.add(lblSolicitacoes);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("Turma: "+ t.getNome() +" - ID: "+ t.getId());
		lblNewLabel.setFont(new Font("Arial", Font.PLAIN, 25));
		panel.add(lblNewLabel);
		setLayout(groupLayout);
		
		info();
		listar();

	}
	
	private void info() {
		String read = "select count(distinct a.matricula), count(distinct s.id) from estuda es "
				+ "join turmas t "
				+ "on t.id = es.id_turma "
				+ "join ensina en "
				+ "on en.id_disciplina = es.id_disciplina "
				+ "left join alunos a "
				+ "on a.id_turma = t.id "
				+ "left join solicitacoes s "
				+ "on s.matricula_a = a.matricula and en.matricula_professor = s.matricula_p "
				+ "where en.matricula_professor= '"+pv.prof.getMatricula()+"' and t.id= '"+t.getId()+"%'"
				+ "group by t.id ";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(read);
			rs = pst.executeQuery();
			
			if(rs.next()) {
				lblAlunos.setText("Total de alunos: "+ rs.getInt(1));
				lblSolicitacoes.setText("   Total de solicitações: "+ rs.getInt(2));
			}
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel buscar as informações da turma:\n"+ e);
		}
	}
	
	private void listar() {
		DefaultTableModel model = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Matricula", "Aluno", "Solicitações"
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
		
		String readStudents = "select a.matricula, a.nome, count(s.id) from alunos a "
				+ "left join solicitacoes s "
				+ "on s.matricula_a = a.matricula and s.matricula_p= '"+ pv.prof.getMatricula()+ "' "
				+ "where a.id_turma= '"+ t.getId() +"' "
				+ "group by a.matricula "
				+ "order by a.nome";
		
		try {
			con = DAO.conectar();
			pst = con.prepareStatement(readStudents);
			rs = pst.executeQuery();
			
			while(rs.next()) {
				model.addRow(new Object[] { rs.getString(1), rs.getString(2), rs.getInt(3) });
			}
			
			table.setModel(model);
			
			con.close();
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "Não foi possivel listar os alunos:\n"+ e);
		}
	}
	
	private void select() {
		Student s = new Student((String) table.getValueAt(table.getSelectedRow(), 0));
			
		pv.viewPanel.setVisible(false);
		pv.viewPanel = new ViewStudentP(s, pv);
		pv.contentPane.add(pv.viewPanel);
		pv.dimensionar();
		
	}
}
