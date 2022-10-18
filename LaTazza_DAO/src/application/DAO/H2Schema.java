package application.DAO;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class H2Schema {
	private Connection conn;
	String path = "res/databaseLaTazza.sql";
	
	private Connection getConn() {
		return conn;
	}
	
	public H2Schema (Connection connection) {
		conn = connection;
	}
	
	private boolean check() {
		Connection connection = getConn();
		Statement stmt;
		try {
			stmt = connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT euro FROM LATAZZASCHEMA.cassa");
	        if (rs.next()) return true;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
		}
		return false;
	}
	
	public void checkSchema() throws SQLException {
		boolean tableExists = check();
		String createStructure = "RUNSCRIPT FROM "+"'"+path+"'";
        if(!tableExists) {
        	Connection connection = getConn();
        	Statement statement = connection.createStatement();
        	statement.execute(createStructure);
        }
	}
}
