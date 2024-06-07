package View.Professor;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JPanel;

import Models.Solicitation;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
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
import javax.swing.JScrollPane;
import javax.swing.LayoutStyle.ComponentPlacement;

public class ViewSolicitationP extends JPanel {

	private static final long serialVersionUID = 1L;

	private JTextArea txtrResposta;
	private JButton btnSetResp;

	public ViewSolicitationP(Solicitation s, ProfessorView pv) {
		setBounds(0, 0, 637, 593);

		JPanel panelStudentIformation = new JPanel();
		panelStudentIformation.setBorder(new LineBorder(new Color(0, 0, 0), 1, true));

		JLabel lblID = new JLabel("Solicitação N°: " + s.getId() + "   ");
		lblID.setFont(new Font("Arial Black", Font.BOLD, 20));

		JLabel lblDuvida = new JLabel("Duvida/Solicitação");
		lblDuvida.setHorizontalAlignment(SwingConstants.CENTER);
		lblDuvida.setFont(new Font("Arial", Font.BOLD, 15));

		JLabel lblNewLabel = new JLabel("Sua resposta");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial", Font.BOLD, 15));

		JPanel panel = new JPanel();

		btnSetResp = new JButton("Definir Resposta");
		btnSetResp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirma = JOptionPane.showConfirmDialog(null,
						"Tem certeza que deseja enviar essa resposta?\nNão será possivel muda-lá depois de enviada.");

				if (confirma == JOptionPane.YES_OPTION) {
					s.setResposta(txtrResposta.getText().trim());
					pv.setViewPanel(new ViewSolicitationP(s, pv));
				}
			}
		});
		btnSetResp.setEnabled(false);
		btnSetResp.setFont(new Font("Arial", Font.PLAIN, 25));
		panel.add(btnSetResp);
		panelStudentIformation.setLayout(new BoxLayout(panelStudentIformation, BoxLayout.X_AXIS));

		JLabel lblNome = new JLabel("Aluno: " + s.getAluno().getNome() + " - " + s.getAluno().getMatricula() + "   ");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 20));
		panelStudentIformation.add(lblNome);

		JLabel lblDisciplina = new JLabel("Disciplina: " + s.getDisciplina().getNome());
		lblDisciplina.setFont(new Font("Arial", Font.PLAIN, 20));
		panelStudentIformation.add(lblDisciplina);

		JScrollPane scrollPaneQuestion = new JScrollPane();

		JTextPane txtpnDuvida = new JTextPane();
		scrollPaneQuestion.setViewportView(txtpnDuvida);
		txtpnDuvida.setEditable(false);
		txtpnDuvida.setFont(new Font("Arial", Font.PLAIN, 15));
		txtpnDuvida.setText(s.getDuvida());

		JScrollPane scrollPaneResp = new JScrollPane();

		txtrResposta = new JTextArea();
		scrollPaneResp.setViewportView(txtrResposta);
		txtrResposta.addKeyListener(new KeyAdapter() {
			public void keyReleased(KeyEvent e) {
				if (!(txtrResposta.getText().isBlank() || s.isRespondido())) {
					btnSetResp.setEnabled(true);
				} else {
					btnSetResp.setEnabled(false);
				}
			}
		});
		txtrResposta.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (txtrResposta.getText().equals("Sem resposta"))
					txtrResposta.setText("");
			}
		});

		txtrResposta.setWrapStyleWord(true);
		txtrResposta.setLineWrap(true);
		txtrResposta.setFont(new Font("Arial", Font.PLAIN, 15));

		if (s.isRespondido()) {
			txtrResposta.setText(s.getResposta());
			txtrResposta.setEditable(false);
		} else {
			txtrResposta.setText("Sem resposta");
		}

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(10)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(lblID, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addComponent(panelStudentIformation, GroupLayout.DEFAULT_SIZE, 617, Short.MAX_VALUE)
						.addGroup(groupLayout.createSequentialGroup()
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblDuvida, GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
										.addComponent(scrollPaneQuestion, Alignment.TRAILING, GroupLayout.DEFAULT_SIZE,
												293, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
										.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE)
										.addGroup(Alignment.TRAILING,
												groupLayout.createSequentialGroup().addGap(6).addComponent(panel,
														GroupLayout.DEFAULT_SIZE, 318, Short.MAX_VALUE))
										.addGroup(groupLayout.createSequentialGroup()
												.addPreferredGap(ComponentPlacement.RELATED)
												.addComponent(scrollPaneResp, GroupLayout.DEFAULT_SIZE, 318,
														Short.MAX_VALUE)))))
				.addGap(10)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(groupLayout
				.createSequentialGroup().addGap(10).addComponent(lblID).addGap(6)
				.addComponent(panelStudentIformation, GroupLayout.PREFERRED_SIZE, 77, GroupLayout.PREFERRED_SIZE)
				.addGap(10)
				.addGroup(groupLayout
						.createParallelGroup(Alignment.LEADING).addComponent(lblNewLabel).addComponent(lblDuvida))
				.addGap(10)
				.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
								.addComponent(scrollPaneResp, GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE).addGap(10)
								.addComponent(panel, GroupLayout.PREFERRED_SIZE, 66, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneQuestion, GroupLayout.DEFAULT_SIZE, 423, Short.MAX_VALUE))
				.addGap(10)));
		setLayout(groupLayout);
	}
}
