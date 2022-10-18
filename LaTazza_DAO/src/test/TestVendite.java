package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.Date;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

import application.model.Cassa;
import application.model.utenti.Persona;
import application.model.vendite.Vendite;
import application.utils.TipoCialda;


public class TestVendite {

	Vendite vendite;
	Date actualDate = new Date();
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
		vendite = new Vendite(connection);
	}
	
	@Test
	void TestVenditeIsEmpty() {
		assertTrue(vendite.getVendite().isEmpty());
	}
	
	@Test
	void testPrintEmpty() {
		assertEquals(vendite.print(),"VENDITE\n\n");
		
	}
	
	@Test
	void testPrint() {
		vendite.addVendita(new Persona("NewAdded"), 75, TipoCialda.fromString("caffËArabica"), true, actualDate);
		assertEquals(vendite.print(),	"VENDITE\n" +
											actualDate.getTime() + " NewAdded 75 caffËArabica true\n\n");
	}
	
	@Test
	void TestVenditeIsNotEmpty() {
		vendite.addVendita(new Persona("Siji"), 100, TipoCialda.fromString("camomilla"), true);
		vendite.addVendita(new Persona("Bon"), 150, TipoCialda.fromString("cioccolata"), false, actualDate);
		vendite.addVendita(new Persona("Roll"), 15, TipoCialda.fromString("caff√®"), false);
		assertFalse(vendite.getVendite().isEmpty());
	}
	
	@Test 
	void testLoad() {
		String str="";
		try {
			FileWriter fileWriter = new FileWriter("res/pluto.txt", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(str=vendite.print());
			printWriter.close();
		} catch (IOException e) {
		}
		vendite.load();
		new File("res/pluto.txt").delete();
		assertTrue(vendite.print().equals(str));
	}
	
	@Test
	void testLoadFileNotExists() {
		int oldSize = vendite.getVendite().size();
		vendite.load();
		assertEquals(vendite.getVendite().size(),oldSize);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void TestAddVenditaSize(TipoCialda tc) {
		int oldSize = vendite.getVendite().size();
		vendite.addVendita(new Persona("NewAdded"), 75, tc, true);
		int newSize = vendite.getVendite().size();
		assertEquals(oldSize+1,newSize);
	}
	
	@ParameterizedTest
	@EnumSource(TipoCialda.class)
	void TestAddVenditaByGetCliente(TipoCialda tc) {
		int oldSize = vendite.getVendite().size();
		vendite.addVendita(new Persona("NewAdded"), 75, tc, true, actualDate);
		int newSize = oldSize+1;
		assertEquals((Persona)vendite.getVendite().get(newSize-1).getCliente(), new Persona("NewAdded"));
	}
}
