package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import application.model.Cassa;
import application.model.rifornimenti.Magazzino;
import application.utils.TipoCialda;

class TestMagazzino {
	
	Magazzino magazzino;
	String DB_DRIVER = "org.h2.Driver";
	String DB_PATH="jdbc:h2:~/test";
	String DB_USER = "";
	String DB_PASSWORD = "";
	Cassa cassa;
	Connection connection;
	String path = "res/test.sql";
	
	@BeforeEach
	void setUp() throws Exception {
		Class.forName(DB_DRIVER);
	    connection = DriverManager.getConnection(DB_PATH+";",DB_USER, DB_PASSWORD);
	    Statement statement = connection.createStatement();
	    String create = "DROP SCHEMA IF EXISTS LATAZZASCHEMA CASCADE; RUNSCRIPT FROM "+ "'"+path+"'";
	    statement.execute(create);
		magazzino=new Magazzino(connection);
	}
	
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testVuotoOnCreate(TipoCialda tipoCialda) {
		assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),0);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testNumeroCialdeDisponibili(TipoCialda tipoCialda) {
		assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),0);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testAggiungiRifornimento(TipoCialda tipoCialda) {
		magazzino.aggiungiRifornimento(1,tipoCialda);
		assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),50);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testAggiungiRifornimentoNeg(TipoCialda tipoCialda) {
		assertFalse(magazzino.aggiungiRifornimento(-1,tipoCialda));
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testRimuoviCialde(TipoCialda tipoCialda) {
		magazzino.aggiungiRifornimento(1,tipoCialda);
		magazzino.rimuoviCialde(34, tipoCialda);
		assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),16);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testRimuoviCialdeNeg(TipoCialda tipoCialda) {
		assertFalse(magazzino.rimuoviCialde(-34, tipoCialda));
	}
	

	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void testGetRifornimentiSize(TipoCialda tipoCialda) {
		if(magazzino.aggiungiRifornimento(1,tipoCialda))
			if(magazzino.aggiungiRifornimento(1, tipoCialda))
				if(magazzino.aggiungiRifornimento(1,tipoCialda))
					assertEquals(magazzino.getRifornimenti().size(),3);
	}
	
	@Test
	void testLoad() {
		magazzino.aggiungiRifornimento(4, TipoCialda.caffè);
		String str="";
		try {
			FileWriter fileWriter = new FileWriter("res/test.txt", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(str=magazzino.print());
			printWriter.close();
		} catch (IOException e) {
		}
		magazzino=new Magazzino(connection);
		magazzino.load();
		File file = new File("res/test.txt");
        file.delete();
		assertEquals(magazzino.print(),str);
	}
	
	@Test
	void testLoadFileNotExists() {
		magazzino.load();
        for(TipoCialda tipoCialda: TipoCialda.values()) {
        	assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),0);
        }
	}
	
	@Test
	void testLoadFileWithNoRifornimenti() {
		File file = new File("res/test.txt");
		magazzino.load();
        file.delete();
        for(TipoCialda tipoCialda: TipoCialda.values()) {
        	assertEquals(magazzino.numeroCialdeDisponibili(tipoCialda),0);
        }
	}

}
