package application.model.rifornimenti;

import java.sql.Connection;
import java.util.ArrayList;

import application.DAO.H2RifornimentiDAO;
import application.model.rifornimenti.Rifornimento;
import application.utils.TipoCialda;

public class Rifornimenti {
	
	private H2RifornimentiDAO db_rif;
	
	public Rifornimenti(Connection conn) {
		db_rif=new H2RifornimentiDAO(conn);
	}
	
	
	public ArrayList<Rifornimento> getRifornimenti() {
		return db_rif.getAll();
	}

	
	public void load() {
		db_rif.getAll();
	}
	

	public boolean addRifornimento (int numeroScatole, TipoCialda tc) {		
		return db_rif.add(new Rifornimento(numeroScatole, tc));	
	}
	
	
	public String print() {
		String rifornimentoString="RIFORNIMENTI\n";
		for(Rifornimento rif : db_rif.getAll())
			rifornimentoString+=String.valueOf(rif.getEpoch())+" "+rif.getNumeroScatole()+" "+rif.getTipoCialda()+'\n';
		return rifornimentoString+='\n';
	}
	
}