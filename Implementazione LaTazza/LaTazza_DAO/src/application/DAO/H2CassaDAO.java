package application.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import application.utils.Euro;

public class H2CassaDAO implements CassaDAO{
	private Connection conn;
	
	public H2CassaDAO(Connection connection) {
		// TODO Auto-generated constructor stub
		conn = connection;
	}

	@Override
	public Euro getAvailability() {
		// TODO Auto-generated method stub
		try {
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT euro FROM LATAZZASCHEMA.cassa");
	        if(rs.next())
	        	return new Euro(rs.getBigDecimal("euro").doubleValue());
		 } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		
		return new Euro(0);
	}

	@Override
	public void getPayment(Euro e) {
		// TODO Auto-generated method stub
		try {
			double euro = (double)e.getValore()/100;
	        PreparedStatement ps = conn.prepareStatement("UPDATE LATAZZASCHEMA.cassa SET EURO=(SELECT euro FROM LATAZZASCHEMA.cassa)+?");
	        ps.setBigDecimal(1, new BigDecimal(euro));
	      int i = ps.executeUpdate();
	      if(i == 1) 
	        return;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}

    @Override
	public void doPayment(Euro e) {
		// TODO Auto-generated method stub
    	try {
    		double euro = (double)e.getValore()/100;
	        PreparedStatement ps = conn.prepareStatement("UPDATE LATAZZASCHEMA.cassa SET EURO=(SELECT euro FROM LATAZZASCHEMA.cassa)-?");
	        ps.setBigDecimal(1, new BigDecimal(euro));
	      int i = ps.executeUpdate();
	      if(i == 1) 
	        return;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	}

}
