package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import application.utils.TipoCialda;

public class H2MagazzinoDAO implements MagazzinoDAO {
	private Connection conn;
	
	public H2MagazzinoDAO(Connection connection) {
		conn = connection;
	}

	@Override
	public HashMap<TipoCialda, Integer> get() {
		// TODO Auto-generated method stub
		try {
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT tipo,qta_cialde FROM LATAZZASCHEMA.magazzino");
	        HashMap<TipoCialda, Integer> magazzino = new HashMap<TipoCialda, Integer>();
	        while(rs.next())
	        	magazzino.put(TipoCialda.fromString(rs.getString("tipo")), Integer.valueOf(rs.getInt("qta_cialde")));
	        return magazzino;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return null;
	}

	@Override
	public int getNumCialde(TipoCialda tc) {
		// TODO Auto-generated method stub
		try {
			String tipoCialda = "'"+tc.toString()+"'";
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT qta_cialde FROM LATAZZASCHEMA.magazzino WHERE tipo="+tipoCialda);
	        if(rs.next())
	        	return rs.getInt("qta_cialde");
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return 0;
	}

	@Override
	public boolean add(TipoCialda tc, int numCialde) {
		// TODO Auto-generated method stub
		try {
			String tipoCialda = tc.toString();
			String query = "UPDATE LATAZZASCHEMA.MAGAZZINO SET QTA_CIALDE= "
					+ "(SELECT QTA_CIALDE FROM LATAZZASCHEMA.MAGAZZINO "
					+ "WHERE TIPO=?)+? WHERE TIPO=?";  
			 PreparedStatement ps = conn.prepareStatement(query);
			 ps.setString(1, tipoCialda);
			 ps.setInt(2, numCialde);
			 ps.setString(3, tipoCialda);
			 int i = ps.executeUpdate();
		      if(i == 1) 
		        return true;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return false;
	}

	@Override
	public boolean remove(TipoCialda tc, int numCialde) {
		// TODO Auto-generated method stub
		try {
			String tipoCialda = tc.toString();
			String query = "UPDATE LATAZZASCHEMA.MAGAZZINO SET QTA_CIALDE= "
					+ "(SELECT QTA_CIALDE FROM LATAZZASCHEMA.MAGAZZINO "
					+ "WHERE TIPO=?)-? WHERE TIPO=?";  
			 PreparedStatement ps = conn.prepareStatement(query);
			 ps.setString(1, tipoCialda);
			 ps.setInt(2, numCialde);
			 ps.setString(3, tipoCialda);
			 int i = ps.executeUpdate();
		      if(i == 1) 
		        return true;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return false;
		
	}

	
}
