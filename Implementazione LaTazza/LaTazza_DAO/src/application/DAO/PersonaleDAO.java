package application.DAO;
import java.sql.SQLException;
import java.util.LinkedHashSet;

import application.model.utenti.Persona;
import application.utils.Euro;

public interface PersonaleDAO {
		boolean add(Persona p) throws SQLException, ClassNotFoundException;
		boolean remove(Persona p) throws SQLException, ClassNotFoundException;
		LinkedHashSet<Persona> getAll() throws SQLException, ClassNotFoundException;
		LinkedHashSet<Persona> getAllIndebitati() throws SQLException, ClassNotFoundException;
		boolean increaseDebito(Persona p, Euro e) throws SQLException, ClassNotFoundException;
		boolean decreaseDebito(Persona p, Euro e) throws SQLException, ClassNotFoundException;
		boolean isDebito(Persona p) throws SQLException, ClassNotFoundException;
}
