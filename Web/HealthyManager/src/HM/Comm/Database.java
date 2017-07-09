package HM.Comm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

	private static final String driver = CommConstant.DatabaseDriver;
	private static final String url = CommConstant.DatabaseUrl;
	private static final String username = CommConstant.DatabaseUsername;
	private static final String password = CommConstant.DatabasePassword;
	
	public Connection getCon(){
		Connection con=null;
		try {
			Class.forName(driver);
			//OracleDriver od=new OracleDriver();
			con=DriverManager.getConnection(url,username,password);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return con;
	}
}
