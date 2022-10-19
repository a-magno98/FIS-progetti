package application.model;

import java.sql.Connection;
import java.util.Locale;

import application.DAO.H2CassaDAO;
import application.utils.Euro;

public class Cassa {

	private H2CassaDAO db_cassa;

	public Cassa(Connection connection) {
		db_cassa = new H2CassaDAO(connection);
	}

	public void load() {
		/*
		try (BufferedReader br = new BufferedReader(new FileReader(path))) {
			String sCurrentLine;
			while ((sCurrentLine = br.readLine()) != null) {
		    	  if(sCurrentLine.equals("CASSA")){
		    		  if ((sCurrentLine = br.readLine()) != null) {
		    			  disponibilita=new Euro(0,Long.parseLong(sCurrentLine));
		    			  return;
		    			  }
		    	  }
			}
			} catch (Exception e) {
		    }
		disponibilita=new Euro(2000);	//se file salvataggio non esiste, diamo 2000euro di partenza	
		*/
		db_cassa.getAvailability();
	}
	
	
	public boolean riceviPagamento(Euro euro) {
		if (euro.minoreDi(new Euro(0)))
			return false;
		db_cassa.getPayment(euro);
		return true;
	}

	public boolean effettuaPagamento(Euro euro) {
		if (euro.minoreDi(new Euro(0)) || euro.ugualeA(new Euro(0)) || db_cassa.getAvailability().minoreDi(euro))
			return false;
		db_cassa.doPayment(euro);
		return true;
	}

	public Euro getDisponibilita() {
		return db_cassa.getAvailability();
	}

	@Override
	public String toString() {
		return String.format(Locale.US, "%,.2f", (double)db_cassa.getAvailability().getValore() / 100);
	}

	public String print() {
		return "CASSA\n"+db_cassa.getAvailability().getValore() + "\n\n";
	}

}
