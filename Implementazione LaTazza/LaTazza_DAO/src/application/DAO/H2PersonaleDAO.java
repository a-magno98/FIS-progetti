package application.DAO;
import java.math.BigDecimal;
import java.sql.*;
import java.util.LinkedHashSet;

import application.model.utenti.Persona;
import application.utils.Euro;

public class H2PersonaleDAO implements PersonaleDAO{
	private Connection conn;
	
	private Persona extractUserWithDebitFromResultSet(ResultSet rs) throws SQLException {
		 	Euro euro = new Euro(rs.getBigDecimal("euro").doubleValue());
		    Persona persona = new Persona(rs.getString("nome"), euro);
		    return persona;
	}
	

	 
	 private Persona extractUserFromResultSet(ResultSet rs) throws SQLException {
		    Persona persona = new Persona(rs.getString("nome"));
		    return persona;
	}
	 
	public H2PersonaleDAO(Connection connection) {
		// TODO Auto-generated constructor stub
		conn = connection;
	}

	@Override
	public boolean add(Persona p) throws SQLException, ClassNotFoundException {
	    try {
	        PreparedStatement ps = conn.prepareStatement("INSERT INTO LATAZZASCHEMA.personale (nome) VALUES (?)");
	        ps.setString(1, p.getNome());
	      int i = ps.executeUpdate();
	      if(i == 1) 
	        return true;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
	
	@Override
	public LinkedHashSet<Persona> getAll() throws SQLException, ClassNotFoundException {
		try {
	        Statement stmt = conn.createStatement();
	        ResultSet rs1 = stmt.executeQuery("SELECT nome, euro FROM LATAZZASCHEMA.debito WHERE attivo = true");
	        LinkedHashSet<Persona> personale = new LinkedHashSet<Persona>();
	        while(rs1.next())
	        {
	            Persona pers = extractUserWithDebitFromResultSet(rs1);
	            personale.add(pers);
	        }
	        ResultSet rs2 = stmt.executeQuery("SELECT nome FROM LATAZZASCHEMA.personale MINUS SELECT nome FROM LATAZZASCHEMA.debito WHERE attivo=true");
	        while(rs2.next())
	        {
	        	Persona pers = extractUserFromResultSet(rs2);
	        	personale.add(pers);
	        }
	        return personale;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return null;
	}

	@Override
	public boolean remove(Persona p) throws SQLException, ClassNotFoundException {
		try {
	        PreparedStatement ps = conn.prepareStatement("DELETE FROM LATAZZASCHEMA.personale WHERE nome=?");
	        ps.setString(1, p.getNome());
	      int i = ps.executeUpdate();
	      if(i == 1) 
	        return true;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}

	@Override
	public LinkedHashSet<Persona> getAllIndebitati() throws SQLException, ClassNotFoundException {
		try {
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT nome,euro FROM LATAZZASCHEMA.debito WHERE attivo=true ");
	        LinkedHashSet<Persona> personaleConDebiti = new LinkedHashSet<Persona>();
	        while(rs.next())
	        {
	            Persona pers = extractUserWithDebitFromResultSet(rs);
	            personaleConDebiti.add(pers);
	        }
	        
	        return personaleConDebiti;
		 } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return null;
		
	}

	@Override
	public boolean increaseDebito(Persona p, Euro e) {
		// TODO Auto-generated method stub
		try {
			 boolean attivo = true;
			// double euro = (double)p.getDebito().getValore()/100;
			 PreparedStatement ps = conn.prepareStatement("UPDATE LATAZZASCHEMA.debito SET euro=(SELECT euro FROM LATAZZASCHEMA.debito WHERE nome=?)+?, attivo=? WHERE nome=?");
			 ps.setString(1, p.getNome());
			 ps.setBigDecimal(2, new BigDecimal((double)e.getValore()/100));
			 ps.setBoolean(3, attivo);
			 ps.setString(4, p.getNome());
			 int i = ps.executeUpdate();
			 if(i == 1)
				 return true;
			 PreparedStatement ps1 = conn.prepareStatement("INSERT INTO LATAZZASCHEMA.debito (nome, euro, attivo) VALUES (?,?,?)");
			 ps1.setString(1, p.getNome());
			 ps1.setBigDecimal(2, new BigDecimal((double)e.getValore()/100));
			 ps1.setBoolean(3, true);
			 int j = ps1.executeUpdate();
			 if(j == 1)
				 return true;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return false;
	}
	
	@Override
	public boolean decreaseDebito(Persona p, Euro e) {
		// TODO Auto-generated method stub
		try {
			 PreparedStatement ps = conn.prepareStatement("UPDATE LATAZZASCHEMA.debito SET euro=(SELECT euro FROM LATAZZASCHEMA.debito WHERE nome=?)-? WHERE nome=?");
			 ps.setString(1, p.getNome());
			 ps.setBigDecimal(2, new BigDecimal((double)e.getValore()/100));
			 ps.setString(3, p.getNome());
			 int i = ps.executeUpdate();
			 if(i == 1) {
				 PreparedStatement ps1 = conn.prepareStatement("UPDATE LATAZZASCHEMA.debito SET attivo=? WHERE nome=? and euro=0.0");
				 ps1.setBoolean(1, false);
				 ps1.setString(2, p.getNome());
				 ps1.executeUpdate();
				 return true;
			 }
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return false;
	}



	@Override
	public boolean isDebito(Persona p) throws SQLException, ClassNotFoundException {
		try {
			String nome = p.getNome();
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT attivo FROM LATAZZASCHEMA.debito WHERE nome="+"'"+nome+"'");
	        if(rs.next())
	        	return rs.getBoolean("attivo");
		 } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return false;
	}


}
