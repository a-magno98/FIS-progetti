package application.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.Date;


import application.model.utenti.Persona;
import application.model.vendite.Vendita;
import application.utils.TipoCialda;

public class H2VenditeDAO implements VenditeDAO {
	private Connection conn;
	
	private Vendita extractSellsFromResultSet(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		// da rivedere perchè già tornano il loro tipi
		Vendita v = new Vendita(
				new Persona(rs.getString("nome")),
				Integer.valueOf(rs.getInt("numero_cialde")), 
				TipoCialda.fromString(rs.getString("tipo_cialda")), 
				Boolean.valueOf(rs.getBoolean("contanti")),
				new Date((rs.getTimestamp("data").getTime())));
		return v;
		
	}
	
	public H2VenditeDAO(Connection connection) {
		// TODO Auto-generated constructor stub
		conn = connection;
	}

	@Override
	public ArrayList<Vendita> getAll() {
		// TODO Auto-generated method stub
		try {
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT nome, tipo_cialda, numero_cialde, data, contanti FROM LATAZZASCHEMA.vendita");
	        ArrayList<Vendita> vendite = new ArrayList<Vendita>();
	        while(rs.next())
	        {
	            Vendita v = extractSellsFromResultSet(rs);
	            vendite.add(v);
	        }
	        return vendite;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return null;
	}

	@Override
	public boolean add(Vendita v) {
		// TODO Auto-generated method stub
		try {
			PreparedStatement ps = conn.prepareStatement("INSERT INTO LATAZZASCHEMA.vendita(nome,tipo_cialda,numero_cialde,data,contanti) VALUES (?,?,?,?,?)");
		     ps.setString(1, v.getCliente().getNome());
		     ps.setString(2, v.getTipoCialda().toString());
		     ps.setInt(3, v.getQuantita());
		     ps.setTimestamp(4, new Timestamp(v.getData().getTime()));
		     ps.setBoolean(5, v.isContanti());
		      int i = ps.executeUpdate();
		      if(i == 1) 
		        return true;
		    } catch (SQLException ex) {
		        ex.printStackTrace();
		    }
		    return false;
	}
	
}
