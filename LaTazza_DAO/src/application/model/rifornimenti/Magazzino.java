package application.model.rifornimenti;

import java.sql.Connection;
import java.util.ArrayList;

import application.DAO.H2MagazzinoDAO;
import application.utils.TipoCialda;

public class Magazzino {
	
	
	private Rifornimenti rifornimenti;
	private H2MagazzinoDAO db_mag;
	
	public Magazzino(Connection conn) {	
		rifornimenti=new Rifornimenti(conn);
		db_mag=new H2MagazzinoDAO(conn);
	}
	
	public ArrayList<Rifornimento> getRifornimenti() {
		return rifornimenti.getRifornimenti();
	}


	public void load() {
		db_mag.get();
	}
	
	
	public int numeroCialdeDisponibili(TipoCialda tipoCialda) {	
		return db_mag.getNumCialde(tipoCialda);
	}
	
	
	public boolean aggiungiRifornimento(int numScatole, TipoCialda tipoCialda) {
		if(numScatole<1)
			return false;
		return db_mag.add(tipoCialda, 50*numScatole) && 
				rifornimenti.addRifornimento(numScatole, tipoCialda);
	}
	
	
	
	public boolean rimuoviCialde(int numeroCialde,TipoCialda tipoCialda) {
		if(numeroCialde<1)
			return false;
		return db_mag.remove(tipoCialda,numeroCialde);
	}
	
	
	public String print() {
		String magString="MAGAZZINO\n";
		for(TipoCialda tipoCialda : db_mag.get().keySet())
			magString+=tipoCialda.toString()+" "+db_mag.get().get(tipoCialda)+'\n';
		return magString+="\n\n"+rifornimenti.print();
	}
	
}
