package View.Professor;

import javax.swing.JPanel;

public class ViewDatas extends JPanel {
	
	private ProfessorView pv;

	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ViewDatas(ProfessorView pv) {
		this.pv = pv;

		setBounds(0, 0, 637, 593);
		setLayout(null);

	}

}
