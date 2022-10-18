package application.model.vendite;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.Date;

import application.DAO.H2VenditeDAO;
import application.model.utenti.Cliente;
import application.model.vendite.Vendita;
import application.utils.TipoCialda;

public class Vendite {

	private H2VenditeDAO db_vendite;
	
	public Vendite(Connection conn) {
		db_vendite = new H2VenditeDAO(conn);
	}
	
	
	public ArrayList<Vendita> getVendite() {
		return db_vendite.getAll();
	}
	
	
	public void load() {
		db_vendite.getAll();
	}
	
	
	public boolean addVendita (Cliente cl, int quantita, TipoCialda tipoCialda, boolean cont) {
		return db_vendite.add(new Vendita(cl, quantita, tipoCialda, cont));
	}
	
	public boolean addVendita (Cliente cl, int quantita, TipoCialda tipoCialda, boolean cont, Date date) {
		return db_vendite.add(new Vendita(cl, quantita, tipoCialda, cont, date));	
	}	
	
	public String print() {
		String venditeString="VENDITE\n";
		for(Vendita vend : db_vendite.getAll())
			venditeString+=vend.getEpoch()+" "+vend.getCliente().getNome()+" "+vend.getQuantita()+" "+vend.getTipoCialda().toString()+" "+vend.isContanti()+'\n';
		return venditeString+='\n';
	}
	
}
