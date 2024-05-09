package Models;

import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.JOptionPane;

public class DAO {
	
	static String Driver = "com.mysql.cj.jdbc.Driver";
	static String url = "jdbc:mysql://localhost:3306/project_pabd";
	static String user = "root";
	static String password = "";
	
	public static Connection conectar() {
		Connection con;
		
		try {
			Class.forName(Driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, e);
			return null;
		}
	}

}
