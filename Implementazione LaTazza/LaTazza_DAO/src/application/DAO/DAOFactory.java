package application.DAO;

//Abstract class DAO Factory
public abstract class DAOFactory {
	  
	  public abstract PersonaleDAO getPersonaDAO();
	  public abstract CassaDAO getCassaDAO();
	  public abstract VenditeDAO getVenditeDAO();
	  public abstract MagazzinoDAO getMagazzinoDAO();
	  public abstract RifornimentiDAO getRifornimentiDAO();
	  public abstract PagamentiDebitoDAO getPagamentiDebitoDAO();
	  
	  public static DAOFactory getDAOFactory() {
		  return new H2DAOFactory();
	  }
		
}
