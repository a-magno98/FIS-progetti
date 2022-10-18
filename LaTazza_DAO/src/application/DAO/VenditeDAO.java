package application.DAO;

import java.util.ArrayList;

import application.model.vendite.Vendita;

public interface VenditeDAO {
	ArrayList<Vendita> getAll();
	boolean add(Vendita v);
}
