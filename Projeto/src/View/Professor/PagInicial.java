package View.Professor;

import javax.swing.JPanel;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class PagInicial extends JPanel {

	private static final long serialVersionUID = 1L;
	private JTable tableSolicitations;
	private JTable tableTurmas;
	private JTable tableDisciplinas;

	/**
	 * Create the panel.
	 */
	public PagInicial(ProfessorView pv) {
		setBounds(0, 0, 637, 593);
		setLayout(null);
		
		JPanel panelCabecalho = new JPanel();
		panelCabecalho.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		panelCabecalho.setBounds(10, 10, 617, 98);
		add(panelCabecalho);
		panelCabecalho.setLayout(null);
		
		JLabel lblNome = new JLabel(pv.prof.getNome());
		lblNome.setBounds(10, 10, 597, 36);
		panelCabecalho.add(lblNome);
		lblNome.setFont(new Font("Arial", Font.PLAIN, 30));
		
		JLabel lblMatricula = new JLabel(pv.prof.getMatricula());
		lblMatricula.setBounds(10, 56, 597, 30);
		panelCabecalho.add(lblMatricula);
		lblMatricula.setFont(new Font("Arial", Font.PLAIN, 25));
		
		JPanel panelRecebidos = new JPanel();
		panelRecebidos.setBorder(new LineBorder(new Color(0, 0, 0)));
		panelRecebidos.setBounds(10, 118, 617, 55);
		add(panelRecebidos);
		panelRecebidos.setLayout(new BoxLayout(panelRecebidos, BoxLayout.X_AXIS));
		
		JLabel lblSolicitacoes = new JLabel("Solicitações recebidas: x   ");
		lblSolicitacoes.setFont(new Font("Arial", Font.PLAIN, 15));
		panelRecebidos.add(lblSolicitacoes);
		
		JLabel lblTurmas = new JLabel("Minhas turmas: y  ");
		lblTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		panelRecebidos.add(lblTurmas);
		
		JLabel lblDisciplinas = new JLabel("Minhas disciplinas: z");
		lblDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		panelRecebidos.add(lblDisciplinas);
		
		JScrollPane scrollPaneSolicitations = new JScrollPane();
		scrollPaneSolicitations.setBounds(10, 183, 198, 400);
		add(scrollPaneSolicitations);
		
		tableSolicitations = new JTable();
		tableSolicitations.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Solicita\u00E7\u00F5es"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableSolicitations.setRowSelectionAllowed(false);
		tableSolicitations.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneSolicitations.setViewportView(tableSolicitations);
		
		JScrollPane scrollPaneSolicitations_1 = new JScrollPane();
		scrollPaneSolicitations_1.setBounds(221, 183, 198, 400);
		add(scrollPaneSolicitations_1);
		
		tableTurmas = new JTable();
		tableTurmas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Minhas turmas"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableTurmas.setRowSelectionAllowed(false);
		tableTurmas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneSolicitations_1.setViewportView(tableTurmas);
		
		JScrollPane scrollPaneSolicitations_1_1 = new JScrollPane();
		scrollPaneSolicitations_1_1.setBounds(429, 183, 198, 400);
		add(scrollPaneSolicitations_1_1);
		
		tableDisciplinas = new JTable();
		tableDisciplinas.setModel(new DefaultTableModel(
			new Object[][] {
			},
			new String[] {
				"Minhas disciplinas"
			}
		) {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			boolean[] columnEditables = new boolean[] {
				false
			};
			public boolean isCellEditable(int row, int column) {
				return columnEditables[column];
			}
		});
		tableDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		tableDisciplinas.setRowSelectionAllowed(false);
		scrollPaneSolicitations_1_1.setViewportView(tableDisciplinas);

	}
}
