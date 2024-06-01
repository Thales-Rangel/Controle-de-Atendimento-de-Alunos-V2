package View.Student;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingConstants;
import javax.swing.border.LineBorder;

import Models.DAO;
import Models.Disciplane;
import Models.Student;

public class CreateSolicitation extends JPanel {
	
	private Connection con;
	private PreparedStatement pst;
	private ResultSet rs;

	private static final long serialVersionUID = 1L;

	private Student s;
	private StudentView sv;
	
	private JPanel panel;
	private JTextField textFieldDisciplane;
	private JTextField textFieldProfessor;
	private JButton btnDisciplinas;
	private JList<String> listDisciplinas;
	private JScrollPane scrollPaneDisciplinas;
	private JLabel lblProfessor;
	private JLabel lblDisciplina;
	private JList<String> listProfessores;
	private JScrollPane scrollPaneProfessores;
	private JButton btnProfessor;
	private JButton btnSubmit;
	private JTextArea txtrQuestion;
	
	public CreateSolicitation(StudentView sv) {
		this.s = sv.s;
		this.sv = sv;
		
		setBounds(100, 1000, 630, 593);
		
		JLabel lblNewLabel = new JLabel("Fazer uma solicitação");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Arial Black", Font.BOLD, 20));
		
		panel = new JPanel();
		panel.setBorder(new LineBorder(new Color(0, 0, 0)));
		
		JScrollPane scrollPane = new JScrollPane();
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.TRAILING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE)
						.addComponent(lblNewLabel, GroupLayout.DEFAULT_SIZE, 397, Short.MAX_VALUE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addComponent(panel, GroupLayout.PREFERRED_SIZE, 203, GroupLayout.PREFERRED_SIZE)
					.addContainerGap())
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblNewLabel)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(scrollPane, GroupLayout.DEFAULT_SIZE, 538, Short.MAX_VALUE))
						.addComponent(panel, GroupLayout.DEFAULT_SIZE, 573, Short.MAX_VALUE))
					.addContainerGap())
		);
		
		txtrQuestion = new JTextArea();
		txtrQuestion.setEditable(false);
		txtrQuestion.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if(txtrQuestion.getText().equals("Escreva aqui a sua dúvida/solicitação...")) {
					txtrQuestion.setText("");
					txtrQuestion.setEditable(true);
				}
			}
		});
		txtrQuestion.setWrapStyleWord(true);
		txtrQuestion.setLineWrap(true);
		txtrQuestion.setFont(new Font("Arial", Font.PLAIN, 15));
		txtrQuestion.setText("Escreva aqui a sua dúvida/solicitação...");
		scrollPane.setViewportView(txtrQuestion);
		
		lblDisciplina = new JLabel("Selecione a disciplina:");
		lblDisciplina.setFont(new Font("Arial", Font.PLAIN, 15));
		
		textFieldDisciplane = new JTextField();
		textFieldDisciplane.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				listarDisciplinas();
			}
		});
		textFieldDisciplane.setBackground(new Color(255, 255, 255));
		textFieldDisciplane.setEditable(false);
		textFieldDisciplane.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldDisciplane.setColumns(10);
		
		btnDisciplinas = new JButton("");
		btnDisciplinas.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listarDisciplinas();
			}
		});
		btnDisciplinas.setIcon(new ImageIcon(CreateSolicitation.class.getResource("/img/seta_de_itens_icon.png")));
		
		scrollPaneDisciplinas = new JScrollPane();
		
		listDisciplinas = new JList<String>();
		listDisciplinas.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				listarDisciplinas();
				textFieldDisciplane.setText(listDisciplinas.getSelectedValue());
				
				try {
					Disciplane disciplina = null;
					DefaultListModel<String> model = new DefaultListModel<String>();
					
					for(int i = 0; i < s.getTurma().getDisciplinas().size(); i++) {
						if(s.getTurma().getDisciplinas().get(i).getNome().equals(textFieldDisciplane.getText())) {
							disciplina = s.getTurma().getDisciplinas().get(i);
							break;
						}
					}
					
					String insert = "select p.nome, p.matricula from ensina en join professores p on p.matricula = en.matricula_professor where en.id_disciplina= '"+ disciplina.getId()+"'";
					
					con = DAO.conectar();
					pst = con.prepareStatement(insert);
					rs = pst.executeQuery();
					while(rs.next()) {
						model.addElement(rs.getString(1)+ " ("+ rs.getString(2)+")");
					}
					
					listProfessores.setModel(model);
					textFieldProfessor.setText("");
				} catch (Exception e2) {
					e2.printStackTrace();
					JOptionPane.showMessageDialog(null, "Não foi possivel localizar os professores:\n"+e2);
				}
				
			}
		});
		listDisciplinas.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneDisciplinas.setViewportView(listDisciplinas);
		
		scrollPaneDisciplinas.setVisible(false);
		listDisciplinas.setVisible(false);
		
		lblProfessor = new JLabel("Selecione o professor:");
		lblProfessor.setFont(new Font("Arial", Font.PLAIN, 15));
		
		textFieldProfessor = new JTextField();
		textFieldProfessor.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				if (!(scrollPaneProfessores.isVisible() || listProfessores.isVisible())) {
					scrollPaneProfessores.setVisible(true);
					listProfessores.setVisible(true);
					btnProfessor.setIcon(new ImageIcon(CreateSolicitation.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
				} else {
					textFieldProfessor.setText("");
					scrollPaneProfessores.setVisible(false);
					listProfessores.setVisible(false);
					btnProfessor.setIcon(new ImageIcon(CreateSolicitation.class.getResource("/img/seta_de_itens_icon.png")));
				}
				dimensionarPanel();
			}
		});
		textFieldProfessor.setBackground(new Color(255, 255, 255));
		textFieldProfessor.setEditable(false);
		textFieldProfessor.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldProfessor.setColumns(10);
		
		btnProfessor = new JButton("");
		btnProfessor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (!(scrollPaneProfessores.isVisible() || listProfessores.isVisible())) {
					scrollPaneProfessores.setVisible(true);
					listProfessores.setVisible(true);
					btnProfessor.setIcon(new ImageIcon(CreateSolicitation.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
				} else {
					textFieldProfessor.setText("");
					scrollPaneProfessores.setVisible(false);
					listProfessores.setVisible(false);
					btnProfessor.setIcon(new ImageIcon(CreateSolicitation.class.getResource("/img/seta_de_itens_icon.png")));
				}
				
				dimensionarPanel();
			}
		});
		btnProfessor.setIcon(new ImageIcon(CreateSolicitation.class.getResource("/img/seta_de_itens_icon.png")));
		
		scrollPaneProfessores = new JScrollPane();
		
		listProfessores = new JList<String>();
		listProfessores.addMouseListener(new MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				textFieldProfessor.setText(listProfessores.getSelectedValue());
				
				scrollPaneProfessores.setVisible(false);
				listProfessores.setVisible(false);
				btnProfessor.setIcon(new ImageIcon(CreateSolicitation.class.getResource("/img/seta_de_itens_icon.png")));
			}
		});
		listProfessores.setFont(new Font("Arial", Font.PLAIN, 15));
		scrollPaneProfessores.setViewportView(listProfessores);
		
		scrollPaneProfessores.setVisible(false);;
		listProfessores.setVisible(false);
		
		btnSubmit = new JButton("Enviar sollicitação");
		btnSubmit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				int confirma = JOptionPane.showConfirmDialog(null, "Você tem certeza que deseja enviar essa solicitação?");
				
				if(confirma == JOptionPane.YES_OPTION) {
					create();
				}
			}
		});
		btnSubmit.setFont(new Font("Arial", Font.PLAIN, 15));
		
		dimensionarPanel();
		
		setLayout(groupLayout);

	}
	
	private void listarDisciplinas() {
		if(!(scrollPaneDisciplinas.isVisible() || listDisciplinas.isVisible())) {
			DefaultListModel<String> model = new DefaultListModel<String>();
			
			for(int i = 0; i < s.getTurma().getDisciplinas().size(); i++)
				model.addElement(s.getTurma().getDisciplinas().get(i).getNome());
			
			listDisciplinas.setModel(model);
			
			scrollPaneDisciplinas.setVisible(true);
			listDisciplinas.setVisible(true);
			btnDisciplinas.setIcon(new ImageIcon(CreateSolicitation.class.getResource("/img/seta_de_itens_para_cima_icon.png")));
		} else {
			textFieldDisciplane.setText("");
			textFieldProfessor.setText("");
			scrollPaneDisciplinas.setVisible(false);
			listDisciplinas.setVisible(false);
			btnDisciplinas.setIcon(new ImageIcon(CreateSolicitation.class.getResource("/img/seta_de_itens_icon.png")));
			
			listProfessores.setModel(new DefaultListModel<String>());
		}
		
		dimensionarPanel();
	}
	
	private void create() {
		if (!((txtrQuestion.getText().isBlank() || txtrQuestion.getText().equals("Escreva aqui a sua dúvida/solicitação...") || textFieldDisciplane.getText().isBlank() || textFieldProfessor.getText().isBlank()))) {
			String professor = textFieldProfessor.getText();
			
			String matriculaP = professor.substring(professor.indexOf("(")+1, professor.indexOf(")"));
			int idDisciplina = 0;
			String duvida = txtrQuestion.getText().trim();
			
			for(int i = 0; i < s.getTurma().getDisciplinas().size(); i++) {
				if(s.getTurma().getDisciplinas().get(i).getNome().equals(textFieldDisciplane.getText())) {
					idDisciplina = s.getTurma().getDisciplinas().get(i).getId();
					break;
				}
			}
			
			String insert = "insert into solicitacoes values "
					+ "(default, '"+ s.getMatricula() +"', '"+ matriculaP +"', '"+ idDisciplina +"', '"+ duvida +"', null, default)";
			
			try {
				con = DAO.conectar();
				pst = con.prepareStatement(insert);
				
				int confirma = pst.executeUpdate();
				if (confirma == 1) {
					JOptionPane.showMessageDialog(null, "Solicitação cadastrada com sucesso!");
					
					sv.setViewPanel(new ViewSolicitationsListS(sv));
				} else {
					JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar a solicitação!");
				}
				
				con.close();
			} catch (Exception e) {
				e.printStackTrace();
				JOptionPane.showMessageDialog(null, "Não foi possivel cadastrar a solicitação:\n"+e);
			}
		} else {
			JOptionPane.showMessageDialog(null, "Preencha todos os campos para poder enviar a solicitação!");
		}
	}
	
	private void dimensionarPanel() {
		GroupLayout gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblDisciplina, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textFieldDisciplane, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnDisciplinas, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneDisciplinas, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)
						.addComponent(lblProfessor, GroupLayout.PREFERRED_SIZE, 182, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addComponent(textFieldProfessor, GroupLayout.PREFERRED_SIZE, 156, GroupLayout.PREFERRED_SIZE)
							.addComponent(btnProfessor, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addComponent(scrollPaneProfessores, GroupLayout.PREFERRED_SIZE, 181, GroupLayout.PREFERRED_SIZE)))
				.addGroup(Alignment.TRAILING, gl_panel.createSequentialGroup()
					.addGap(24)
					.addComponent(btnSubmit, GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
					.addGap(23))
		);
		gl_panel.setVerticalGroup(
			gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
					.addGap(10)
					.addComponent(lblDisciplina)
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldDisciplane, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnDisciplinas, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
					.addGap(1)
					.addComponent(scrollPaneDisciplinas, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)
					.addGap(26)
					.addComponent(lblProfessor)
					.addGap(10)
					.addGroup(gl_panel.createParallelGroup(Alignment.LEADING)
						.addComponent(textFieldProfessor, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addComponent(btnProfessor, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE)
						.addGroup(gl_panel.createSequentialGroup()
							.addGap(23)
							.addComponent(scrollPaneProfessores, GroupLayout.PREFERRED_SIZE, 125, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED, 115, Short.MAX_VALUE)
					.addComponent(btnSubmit)
					.addGap(44))
		);
		panel.setLayout(gl_panel);
	}
}
