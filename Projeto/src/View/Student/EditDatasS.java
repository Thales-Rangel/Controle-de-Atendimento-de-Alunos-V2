package View.Student;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Models.Student;
import Utils.Validador;

public class EditDatasS extends JDialog {

	private static final long serialVersionUID = 1L;
	
	private Student s;
	private StudentView sv;
	
	private final JPanel contentPanel = new JPanel();
	private JTextField textFieldNome;
	private JTextField textFieldNewSenha;
	private JPasswordField passwordField;

	public EditDatasS(StudentView sv) {
		this.s = sv.s;
		this.sv = sv;
		
		setResizable(false);
		setTitle("Editar dados pessoais");
		setIconImage(Toolkit.getDefaultToolkit().getImage(EditDatasS.class.getResource("/img/Project_Icon.png")));
		setBounds(100, 100, 521, 279);
		getContentPane().setLayout(new BorderLayout());
		contentPanel.setBorder(new EmptyBorder(5, 5, 5, 5));
		getContentPane().add(contentPanel, BorderLayout.CENTER);
		contentPanel.setLayout(null);
		
		JLabel lblTitle = new JLabel("Editar dados pessoais");
		lblTitle.setFont(new Font("Arial", Font.PLAIN, 20));
		lblTitle.setBounds(10, 10, 229, 24);
		contentPanel.add(lblTitle);
		
		JLabel lblNome = new JLabel("Nome:");
		lblNome.setFont(new Font("Arial", Font.PLAIN, 15));
		lblNome.setBounds(10, 44, 60, 24);
		contentPanel.add(lblNome);
		
		JLabel lblSenha = new JLabel("Senha:");
		lblSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblSenha.setBounds(10, 78, 60, 24);
		contentPanel.add(lblSenha);
		
		JLabel lblOldSenha = new JLabel("Senha antiga:");
		lblOldSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		lblOldSenha.setBounds(10, 134, 99, 24);
		contentPanel.add(lblOldSenha);
		
		textFieldNome = new JTextField();
		textFieldNome.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldNome.setBounds(80, 46, 229, 24);
		contentPanel.add(textFieldNome);
		textFieldNome.setColumns(10);
		textFieldNome.setDocument(new Validador(30));
		textFieldNome.setText(s.getNome());
		
		textFieldNewSenha = new JTextField();
		textFieldNewSenha.setFont(new Font("Arial", Font.PLAIN, 15));
		textFieldNewSenha.setBounds(75, 80, 234, 24);
		contentPanel.add(textFieldNewSenha);
		textFieldNewSenha.setColumns(10);
		textFieldNewSenha.setDocument(new Validador(100));
		
		passwordField = new JPasswordField();
		passwordField.setBounds(119, 134, 190, 24);
		passwordField.setDocument(new Validador(100));
		
		contentPanel.add(passwordField);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(EditDatasS.class.getResource("/img/Icon_cadastro.png")));
		lblNewLabel.setBounds(319, 53, 178, 133);
		contentPanel.add(lblNewLabel);
		
		setLocationRelativeTo(null);
		
		{
			JPanel buttonPane = new JPanel();
			buttonPane.setLayout(new FlowLayout(FlowLayout.RIGHT));
			getContentPane().add(buttonPane, BorderLayout.SOUTH);
			{
				JButton okButton = new JButton("OK");
				okButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						editar();
					}
				});
				okButton.setActionCommand("OK");
				buttonPane.add(okButton);
				getRootPane().setDefaultButton(okButton);
			}
			{
				JButton cancelButton = new JButton("Cancelar");
				cancelButton.addActionListener(new ActionListener() {
					public void actionPerformed(ActionEvent e) {
						dispose();
					}
				});
				cancelButton.setActionCommand("Cancel");
				buttonPane.add(cancelButton);
			}
		}
	}
	
	@SuppressWarnings("deprecation")
	private void editar() {
		if (!passwordField.getText().isBlank() && passwordField.getText().equals(s.getSenha())) {
			if (!((textFieldNome.getText().trim().equals(s.getNome()) || textFieldNome.getText().isBlank()) && textFieldNewSenha.getText().isBlank())) {
				
				int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que deseja mudar esses dados?");
				
				if(confirma == JOptionPane.YES_OPTION) {
					if(!(textFieldNome.getText().isBlank() || textFieldNome.getText().trim().equals(s.getNome())))
						s.setNome(textFieldNome.getText().trim());
					
					if(!textFieldNewSenha.getText().isBlank())
						s.setSenha(textFieldNewSenha.getText().trim());
					
					sv.setViewPanel(new ViewDatasS(sv));
					dispose();
				}
				
			} else {
				JOptionPane.showMessageDialog(null, "Tu entrou aqui pq se não vai mudar nada?\nMude ao menos o nome ou a senha");
			}
		} else {
			JOptionPane.showMessageDialog(null, "Sua senha não foi digitada corretamente\nPor favor, digite sua senha antiga para confirmar sua atenticidade!");
		}
	}
}
