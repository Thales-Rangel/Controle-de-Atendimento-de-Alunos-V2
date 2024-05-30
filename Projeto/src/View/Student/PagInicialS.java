package View.Student;

import java.awt.Color;
import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.BoxLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Models.DAO;
import Models.Student;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PagInicialS extends JPanel {

	private static final long serialVersionUID = 1L;
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;
	
	private Student s;
	private JTable tableSolicitacoes;
	private JTable tableDisciplinas;

	public PagInicialS(Student s) {
		this.s = s;
		
		setBounds(0, 0, 637, 593);
		
		JPanel panelDatas = new JPanel();
		panelDatas.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		
		JPanel panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JScrollPane scrollPaneSolicitações = new JScrollPane();
		
		JScrollPane scrollPaneDisciplinas = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(scrollPaneSolicitações, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(scrollPaneDisciplinas, GroupLayout.DEFAULT_SIZE, 303, Short.MAX_VALUE)
							.addGap(1))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(panelDatas, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(panelDatas, GroupLayout.PREFERRED_SIZE, 133, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 53, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneSolicitações, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE)
						.addComponent(scrollPaneDisciplinas, GroupLayout.DEFAULT_SIZE, 371, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		tableDisciplinas = new JTable();
		tableDisciplinas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Minhas disciplinas"
			}
		));
		tableDisciplinas.setRowSelectionAllowed(false);
		tableDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneDisciplinas.setViewportView(tableDisciplinas);
		
		tableSolicitacoes = new JTable();
		tableSolicitacoes.setFont(new Font("Arial", Font.PLAIN, 15));
		tableSolicitacoes.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Solicitações"
			}
		) {
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableSolicitacoes.setRowSelectionAllowed(false);
		scrollPaneSolicitações.setViewportView(tableSolicitacoes);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblSolicitations = new JLabel("Solicitações enviadas: x   ");
		lblSolicitations.setFont(new Font("Arial", Font.PLAIN, 15));
		panel.add(lblSolicitations);
		
		JLabel lblDisciplinas = new JLabel("Minhas disciplinas: "+ s.getTurma().getDisciplinas().size());
		lblDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		panel.add(lblDisciplinas);
		
		try {
			con = DAO.conectar();
			
			pst = con.prepareStatement("select count(*) from solicitacoes where matricula_a= '"+ s.getMatricula()+"'");
			rs = pst.executeQuery();
			
			if(rs.next()) {
				lblSolicitations.setText("Solicitações enviadas: "+ rs.getInt(1) +"   ");
			}
			
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel contaras solicitações e as disciplinas:\n"+e);
		}
		
		JLabel lblNome = new JLabel(s.getNome());
		lblNome.setFont(new Font("Arial", Font.PLAIN, 30));
		
		JLabel lblLogo = new JLabel("");
		lblLogo.setHorizontalAlignment(SwingConstants.CENTER);
		lblLogo.setIcon(new ImageIcon(PagInicialS.class.getResource("/img/IF Logo - Remove.png")));
		
		JLabel lblMatricula = new JLabel(s.getMatricula());
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 25));
		
		JLabel lblTurma = new JLabel(s.getTurma().getNome());
		lblTurma.setFont(new Font("Arial", Font.PLAIN, 25));
		GroupLayout gl_panelDatas = new GroupLayout(panelDatas);
		gl_panelDatas.setHorizontalGroup(
			gl_panelDatas.createParallelGroup(Alignment.TRAILING)
				.addGroup(gl_panelDatas.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDatas.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTurma, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
						.addComponent(lblMatricula, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
						.addComponent(lblNome, GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 105, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		gl_panelDatas.setVerticalGroup(
			gl_panelDatas.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panelDatas.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_panelDatas.createParallelGroup(Alignment.LEADING)
						.addComponent(lblLogo, GroupLayout.PREFERRED_SIZE, 109, Short.MAX_VALUE)
						.addGroup(gl_panelDatas.createSequentialGroup()
							.addComponent(lblNome)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblMatricula)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(lblTurma)))
					.addContainerGap())
		);
		panelDatas.setLayout(gl_panelDatas);
		setLayout(groupLayout);
		
		listagens();

	}
	
	private void listagens() {
		DefaultTableModel modelS = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Solicitações"
				}
			) {
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] {
					false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
			
		DefaultTableModel modelD = new DefaultTableModel(
				new Object[][] {
				},
				new String[] {
					"Minhas disciplinas"
				}
			) {
				private static final long serialVersionUID = 1L;
				boolean[] columnEditables = new boolean[] {
					false
				};
				public boolean isCellEditable(int row, int column) {
					return columnEditables[column];
				}
			};
		
		String readSolicitations = "select duvida from solicitacoes where matricula_a= '"+ s.getMatricula() +"' and respondido= 'F' order by id desc";
		
		try {
			for (int i = 0; i < s.getTurma().getDisciplinas().size(); i++)
				modelD.addRow(new Object[] { s.getTurma().getDisciplinas().get(i).getNome() });
			
			con = DAO.conectar();
			pst = con.prepareStatement(readSolicitations);
			rs = pst.executeQuery();
			while(rs.next())
				modelS.addRow(new Object[] { rs.getString(1) });
			
			tableSolicitacoes.setModel(modelS);
			tableDisciplinas.setModel(modelD);
			
			con.close();
		} catch(Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "Não foi possivel listar:\n"+e);
		}
	}
}
