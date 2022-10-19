package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;

import application.model.rifornimenti.Rifornimento;
import application.utils.TipoCialda;

public class H2RifornimentiDAO implements RifornimentiDAO {
	private Connection conn;
	
	private Rifornimento extractRifFromResultSet(ResultSet rs) throws SQLException {
		return new Rifornimento(
				Integer.valueOf(rs.getInt("numero_scatole")), 
				  TipoCialda.fromString(rs.getString("tipo_cialda")), 
				  new Date(Long.valueOf(rs.getTimestamp("dataR").getTime())));
	}
	
	public H2RifornimentiDAO(Connection connection) {
		conn = connection;
	}
	
	@Override
	public ArrayList<Rifornimento> getAll() {
		// TODO Auto-generated method stub
		try {
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT dataR, numero_scatole, tipo_cialda FROM LATAZZASCHEMA.rifornimento");
	        ArrayList<Rifornimento> rifornimenti = new ArrayList<Rifornimento>();
	        while(rs.next())
	        {
	            Rifornimento rif = extractRifFromResultSet(rs);
	            rifornimenti.add(rif);
	        }
	        return rifornimenti;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return null;
	}

	@Override
	public boolean add(Rifornimento rif) {
		// TODO Auto-generated method stub
		try {
		        PreparedStatement ps = conn.prepareStatement("INSERT INTO LATAZZASCHEMA.rifornimento(dataR,numero_scatole,tipo_cialda) VALUES (?,?,?)");
		        ps.setTimestamp(1, new Timestamp(rif.getData().getTime()));
		        ps.setInt(2, rif.getNumeroScatole());
		        ps.setString(3, rif.getTipoCialda().toString());
		      int i = ps.executeUpdate();
		      if(i == 1) 
		        return true;
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		    return false;
	}
	
}
