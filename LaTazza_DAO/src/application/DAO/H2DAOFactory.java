package application.DAO;
import java.sql.*;

//H2 implementazione concreta DAO Factory
public class H2DAOFactory extends DAOFactory{
		 private static final String DB_DRIVER = "org.h2.Driver";
		 private static final String DB_PATH="jdbc:h2:~/databaseLaTazza";
		 private static final String DB_USER = "";
		 private static final String DB_PASSWORD = "";
		 private Connection conn;
		
		private void createConnection() throws SQLException, ClassNotFoundException {
			if(conn==null||conn.isClosed()){
		         Class.forName(DB_DRIVER);
		         conn = DriverManager.getConnection(DB_PATH+";",DB_USER, DB_PASSWORD);
		     }
		}
		 
		public H2DAOFactory(){
			 try {
				createConnection();
			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }

		 public void closeConnection() throws SQLException {
			 if (conn != null && !conn.isClosed())
				 conn.close();
		 }
		 
		 public Connection getConnection() {
			 return conn;
		 }

		 @Override
		 public PersonaleDAO getPersonaDAO() {
		    // CloudscapeCustomerDAO implements CustomerDAO
		    return new H2PersonaleDAO(conn);
		  }
		 
		@Override
		public CassaDAO getCassaDAO() {
			// TODO Auto-generated method stub
			return new H2CassaDAO(conn);
		}
		
		@Override
		public VenditeDAO getVenditeDAO() {
			return new H2VenditeDAO(conn);
		}

		@Override
		public MagazzinoDAO getMagazzinoDAO() {
			// TODO Auto-generated method stub
			return new H2MagazzinoDAO(conn);
		}

		@Override
		public RifornimentiDAO getRifornimentiDAO() {
			// TODO Auto-generated method stub
			return new H2RifornimentiDAO(conn);
		}

		@Override
		public PagamentiDebitoDAO getPagamentiDebitoDAO() {
			// TODO Auto-generated method stub
			return new H2PagamentiDebitoDAO(conn);
		}
		
}
