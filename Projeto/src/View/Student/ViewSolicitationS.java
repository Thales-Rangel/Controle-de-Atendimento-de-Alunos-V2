package View.Student;

import javax.swing.JPanel;

import Models.Solicitation;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.BoxLayout;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;

public class ViewSolicitationS extends JPanel {

	private static final long serialVersionUID = 1L;

	public ViewSolicitationS(Solicitation s) {
		
		setBounds(100, 1000, 630, 593);
		
		JLabel lblSolicitation = new JLabel("Solicitação N°: "+ s.getId());
		lblSolicitation.setFont(new Font("Arial Black", Font.BOLD, 20));
		
		JPanel panelInformations = new JPanel();
		panelInformations.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JLabel lblQuestion = new JLabel("Dúvida/Solicitação");
		lblQuestion.setFont(new Font("Arial", Font.BOLD, 15));
		lblQuestion.setHorizontalAlignment(SwingConstants.CENTER);
		
		JLabel lblResp = new JLabel("Resposta do professor");
		lblResp.setFont(new Font("Arial", Font.BOLD, 15));
		lblResp.setHorizontalAlignment(SwingConstants.CENTER);
		
		JScrollPane scrollPaneQuestion = new JScrollPane();
		
		JScrollPane scrollPaneResp = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(panelInformations, GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
						.addComponent(lblSolicitation, GroupLayout.DEFAULT_SIZE, 610, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(scrollPaneQuestion, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
								.addComponent(lblQuestion, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING)
								.addComponent(scrollPaneResp, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
								.addComponent(lblResp, Alignment.LEADING, GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE))))
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addComponent(lblSolicitation)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(panelInformations, GroupLayout.PREFERRED_SIZE, 72, GroupLayout.PREFERRED_SIZE)
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblQuestion)
						.addComponent(lblResp))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPaneQuestion, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE)
						.addComponent(scrollPaneResp, GroupLayout.DEFAULT_SIZE, 436, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		JTextPane textPaneResp = new JTextPane();
		if(s.isRespondido()) {
			textPaneResp.setText(s.getResposta());
		} else {
			textPaneResp.setText("Nenhuma resposta enviada.");
		}
		textPaneResp.setEditable(false);
		textPaneResp.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneResp.setViewportView(textPaneResp);
		
		JTextPane textPaneQuestion = new JTextPane();
		textPaneQuestion.setText(s.getDuvida());
		textPaneQuestion.setEditable(false);
		textPaneQuestion.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneQuestion.setViewportView(textPaneQuestion);
		panelInformations.setLayout(new BoxLayout(panelInformations, BoxLayout.X_AXIS));
		
		JLabel lblProfessor = new JLabel("Professor: "+ s.getProfessor().getNome() +" - "+s.getProfessor().getMatricula()+"   ");
		lblProfessor.setFont(new Font("Arial", Font.PLAIN, 20));
		panelInformations.add(lblProfessor);
		
		JLabel lblDisciplane = new JLabel("Disicplina: "+ s.getDisciplina().getNome());
		lblDisciplane.setFont(new Font("Arial", Font.PLAIN, 20));
		panelInformations.add(lblDisciplane);
		setLayout(groupLayout);

	}
}
