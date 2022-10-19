package application.model.utenti;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.LinkedHashSet;
import java.util.Set;

import application.utils.Euro;
import application.DAO.H2PagamentiDebitoDAO;
import application.DAO.H2PersonaleDAO;

public class Personale {

	private H2PersonaleDAO  db_personale;
	private H2PagamentiDebitoDAO db_pagamentiDebito;
	
	public Personale(Connection connection) {
		db_personale= new H2PersonaleDAO(connection);
		db_pagamentiDebito = new H2PagamentiDebitoDAO(connection);
	}
	
	
	public LinkedHashSet<Persona> getPersonale() throws ClassNotFoundException, SQLException {
		return db_personale.getAll();
	}

	public LinkedHashSet<PagamentoDebito> getPagamentiDebito() throws ClassNotFoundException, SQLException{
		return db_pagamentiDebito.getAll();
	}


	public void load() throws ClassNotFoundException, SQLException {
		db_personale.getAll();
		db_pagamentiDebito.getAll();
		
	}
	
	public boolean addPersona(String nome) {
			try {
				return db_personale.add(new Persona(nome));
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
	}
	
	public boolean removePersona (Persona p) throws ClassNotFoundException, SQLException {	
		if(db_personale.isDebito(p)) return false;
		return db_personale.remove(p);
	}
	
	
	public Set<Persona> getIndebitati() throws ClassNotFoundException, SQLException{
		return db_personale.getAllIndebitati();
	}
	
	public boolean aumentaDebito(Cliente cl, Euro euro) {
		// TODO Auto-generated method stub
		if(euro.getValore() < 0)
			return false;
		return db_personale.increaseDebito((Persona)cl, euro);
	}
	
	
	public boolean diminuisciDebito(Persona pers, Euro ammontare) {
		if(ammontare.getValore()<0 || ammontare.getValore()>pers.getDebito().getValore())
			return false;
		try {
			if(db_personale.decreaseDebito(pers, ammontare))
				return db_pagamentiDebito.add(new PagamentoDebito(pers, ammontare));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	
	public String print() throws ClassNotFoundException, SQLException {
		String personaleString="PERSONALE\n";
		for(Persona pers : db_personale.getAll())
			personaleString+=pers.toString()+" "+pers.getDebito().getValore()+'\n';
		String pagamentiDebitiString="PAGAMENTO\n";
		for(PagamentoDebito pagDeb : db_pagamentiDebito.getAll()) 
			pagamentiDebitiString+=pagDeb.getEpoch()+" "+pagDeb.getPersona()+' '+pagDeb.getAmmontare().getValore()+'\n';
		return personaleString+='\n'+pagamentiDebitiString+'\n';
	}

}