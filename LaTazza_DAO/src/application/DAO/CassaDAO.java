package application.DAO;

import application.utils.Euro;

public interface CassaDAO {
		Euro getAvailability();
		void getPayment(Euro e);
		void doPayment(Euro e);
		
}
