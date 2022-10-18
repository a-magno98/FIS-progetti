package application.DAO;

import java.util.ArrayList;

import application.model.rifornimenti.Rifornimento;

public interface RifornimentiDAO {
	ArrayList<Rifornimento> getAll();
	boolean add(Rifornimento rif);
}
