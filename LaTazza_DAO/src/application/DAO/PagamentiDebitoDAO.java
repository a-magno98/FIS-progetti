package application.DAO;

import java.sql.SQLException;
import java.util.LinkedHashSet;

import application.model.utenti.PagamentoDebito;

public interface PagamentiDebitoDAO {
	LinkedHashSet<PagamentoDebito> getAll( )throws SQLException, ClassNotFoundException;;
	boolean add(PagamentoDebito pag_deb) throws SQLException, ClassNotFoundException;
}
