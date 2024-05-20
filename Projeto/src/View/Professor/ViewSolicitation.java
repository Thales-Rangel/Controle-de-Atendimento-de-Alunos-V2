package View.Professor;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

import Models.Solicitation;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ViewSolicitation extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea txtrResposta;
	private JButton btnSetResp;
	/**
	 * Create the panel.
	 */
	public ViewSolicitation(Solicitation s, ProfessorView pv) {
		
		setBounds(0, 0, 637, 593);
		
		JPanel panelStudentIformation = new JPanel();
		panelStudentIformation.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));
		
		JLabel lblID = new JLabel("Solicitação N°: "+ s.getId()+"   ");
		lblID.setFont(new Font("Arial Black", Font.BOLD, 20));
		
		JLabel lblDuvida = new JLabel("Duvida/Solicitação");
		lblDuvida.setHorizontalAlignment(SwingConstants.CENTER);
		lblDuvida.setFont(new Font("Arial", Font.BOLD, 15));
		
		JTextPane txtpnDuvida = new JTextPane();
		txtpnDuvida.setEditable(false);
		txtpnDuvida.setFont(new Font("Arial", Font.PLAIN, 15));
		txtpnDuvida.setText(s.getDuvida());
		
		txtrResposta = new JTextArea();
		txtrResposta.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				if (!(txtrResposta.getText().isBlank() || s.isRespondido())) {
					btnSetResp.setEnabled(true);
				} else {
					btnSetResp.setEnabled(false);
				}
			}
		});
		txtrResposta.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if (txtrResposta.getText().equals("Sem resposta"))
					txtrResposta.setText("");
			}
		});
		
		if (s.isRespondido()) {
			txtrResposta.setText(s.getResposta());
			txtrResposta.setEditable(false);
		} else {
			txtrResposta.setText("Sem resposta");
		}
		
		txtrResposta.setWrapStyleWord(true);
		txtrResposta.setLineWrap(true);
		txtrResposta.setFont(new Font("Arial", Font.PLAIN, 15));
		
		JLabel lblNewLabel = new JLabel("Sua resposta");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));
		
		JPanel panel = new JPanel();
		
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblID, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(panelStudentIformation, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(lblDuvida, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 296, Short.MAX_VALUE)
								.addComponent(txtpnDuvida, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 299, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(panel, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 314, Short.MAX_VALUE)
								.addComponent(txtrResposta, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 302, Short.MAX_VALUE)
								.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 324, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblID)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelStudentIformation, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblDuvida)
						.addComponent(lblNewLabel))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(txtrResposta, GroupLayout.DEFAULT_SIZE, 355, Short.MAX_VALUE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(panel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
						.addComponent(txtpnDuvida, GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		btnSetResp = new JButton("Definir Resposta");
		btnSetResp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja enviar essa resposta?\nNão será possivel muda-lá depois de enviada.");
				
				if (confirma == JOptionPane.YES_OPTION) {
					s.setResposta(txtrResposta.getText().trim());
					
					pv.viewPanel.setVisible(false);
					pv.viewPanel = new ViewSolicitation(s, pv);
					pv.contentPane.add(pv.viewPanel);
					pv.dimensionar();
				}
			}
		});
		btnSetResp.setEnabled(false);
		btnSetResp.setFont(new Font("Arial", Font.PLAIN, 25));
		panel.add(btnSetResp);
		panelStudentIformation.setLayout(new BoxLayout(panelStudentIformation, BoxLayout.X_AXIS));
		
		JLabel lblNome = new JLabel("Aluno: "+ s.getAluno().getNome() +" - "+ s.getAluno().getMatricula() +"   ");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));
		panelStudentIformation.add(lblNome);
		
		JLabel lblDisciplina = new JLabel("Disciplina: "+ s.getDisciplina().getNome());
		lblDisciplina.setFont(new Font("Arial", Font.PLAIN, 20));
		panelStudentIformation.add(lblDisciplina);
		setLayout(groupLayout);
	}
}
