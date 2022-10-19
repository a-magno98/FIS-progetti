package test;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.io.File;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.rifornimenti.Rifornimenti;
import application.model.rifornimenti.Rifornimento;
import application.utils.TipoCialda;

class TestRifornimenti {
	
	Rifornimenti rifornimenti, rifornimentiEmpty;
	int size = 10, numScatola = 1;
	File file = new File("res/test.txt");
	String DB_DRIVER = "org.h2.Driver";
	String DB_PATH="jdbc:h2:~/test";
	String DB_USER = "";
	String DB_PASSWORD = "";
	Connection connection;
	String path = "res/test.sql";

	
	@BeforeEach
	void setUp() throws Exception {
		Class.forName(DB_DRIVER);
	    connection = DriverManager.getConnection(DB_PATH+";",DB_USER, DB_PASSWORD);
	    Statement statement = connection.createStatement();
	    String create = "DROP SCHEMA IF EXISTS LATAZZASCHEMA CASCADE; RUNSCRIPT FROM "+ "'"+path+"'";
	    statement.execute(create);
	    rifornimentiEmpty= new Rifornimenti(connection);
	    
		rifornimenti= new Rifornimenti(connection);
		for(int i=0; i<size; i++) {
			if(rifornimenti.addRifornimento(numScatola,TipoCialda.caffè))
				Thread.sleep(100);
		}
	}
	
	
	@Test
	void testGetRifornimentiByTipoCialda() {
		for(Rifornimento rif : rifornimenti.getRifornimenti()) {
			assertEquals(rif.getTipoCialda(),TipoCialda.caffè);
			assertEquals(rif.getNumeroScatole(),numScatola);
		}
	}
	
	@Test
	void testGetRifornimentiSize() {
		assertEquals(rifornimenti.getRifornimenti().size(),size);
	}
	
	@Test
	void testLoad() {
		assertEquals(rifornimentiEmpty.getRifornimenti().size(),10);
	}
	
	@Test
	void testLoadFileNotExists() throws SQLException {
		String create = "DROP SCHEMA IF EXISTS LATAZZASCHEMA CASCADE; RUNSCRIPT FROM "+ "'"+path+"'";
		Statement statement = connection.createStatement();
	    statement.execute(create);
		assertEquals(rifornimentiEmpty.getRifornimenti().size(),0);
	}
	
	@Test
	void testLoadFileWithNoRifornimenti() throws SQLException {
		String create = "DROP SCHEMA IF EXISTS LATAZZASCHEMA CASCADE; RUNSCRIPT FROM "+ "'"+path+"'";
		Statement statement = connection.createStatement();
	    statement.execute(create);
		assertEquals(rifornimentiEmpty.getRifornimenti().size(),0);
	}
}