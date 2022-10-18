package application.DAO;

import java.util.HashMap;

import application.utils.TipoCialda;

public interface MagazzinoDAO {
	HashMap<TipoCialda, Integer> get();
	int getNumCialde(TipoCialda tc);
	boolean add(TipoCialda tc, int numCialde);
	boolean remove(TipoCialda tc, int numCialde);
}
