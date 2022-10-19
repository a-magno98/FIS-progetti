package application.DAO;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import java.util.LinkedHashSet;

import application.model.utenti.PagamentoDebito;
import application.model.utenti.Persona;
import application.utils.Euro;

public class H2PagamentiDebitoDAO implements PagamentiDebitoDAO {
	private Connection conn;
	
	private PagamentoDebito extractPaymentDebitFromResultSet(ResultSet rs) throws SQLException {
	 	Euro euro = new Euro(rs.getBigDecimal("euro").doubleValue());
	    Persona persona = new Persona(rs.getString("nome"));
	    Date data = new Date(Long.valueOf(rs.getTimestamp("data").getTime()));
	    return new PagamentoDebito(persona, euro, data);
	}
	
	public H2PagamentiDebitoDAO(Connection connection) {
		conn = connection;
	}

	@Override
	public LinkedHashSet<PagamentoDebito> getAll() {
		// TODO Auto-generated method stub
		try {
	        Statement stmt = conn.createStatement();
	        ResultSet rs = stmt.executeQuery("SELECT nome, data, euro FROM LATAZZASCHEMA.pagamento_debito");
	        LinkedHashSet<PagamentoDebito> pagamentoDebito = new LinkedHashSet<PagamentoDebito>();
	        while(rs.next())
	        {
	            PagamentoDebito pag = extractPaymentDebitFromResultSet(rs);
	            pagamentoDebito.add(pag);
	        }
	        return pagamentoDebito;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
		return null;
	}

	@Override
	public boolean add(PagamentoDebito pag_deb) {
		// TODO Auto-generated method stub
		try {
	        PreparedStatement ps = conn.prepareStatement("INSERT INTO LATAZZASCHEMA.pagamento_debito (nome,data,euro) VALUES (?,?,?)");
	        ps.setString(1, pag_deb.getPersona().getNome());
	        ps.setTimestamp(2, new Timestamp(pag_deb.getDate().getTime()));
	        double euro = (double)pag_deb.getAmmontare().getValore()/100;
	        ps.setBigDecimal(3, new BigDecimal(euro));
	      int i = ps.executeUpdate();
	      if(i == 1) 
	        return true;
	    } catch (SQLException ex) {
	        ex.printStackTrace();
	    }
	    return false;
	}
}
