package test;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import application.model.utenti.Persona;
import application.model.utenti.Personale;
import application.utils.Euro;


class TestPersonale {

	Personale personale;
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
		personale = new Personale(connection);
		personale.addPersona("persona1");
		personale.addPersona("persona2");
		personale.addPersona("persona3");
	}
	
	
	@Test
	void testGetPersonale() throws ClassNotFoundException, SQLException {
		assertEquals(personale.getPersonale().size(),3);
	}
	
	@Test
	void testGetIndebitatiSize() throws ClassNotFoundException, SQLException {
		for(Persona persona : personale.getPersonale()) {
			personale.aumentaDebito(persona,new Euro(5));
		}
		assertEquals(personale.getIndebitati().size(),3);
	}
	
	@Test
	void testGetIndebitatiDebito() throws ClassNotFoundException, SQLException {
		for(Persona persona : personale.getPersonale()) {
			personale.aumentaDebito(persona,new Euro(5));
		}
		for(Persona persona : personale.getIndebitati()) {
			assertEquals(persona.getDebito(),new Euro(5));
		}
	}
	
	
	@Test
	void testAddPersonaTrue() {
		assertTrue(personale.addPersona("persona4"));
	}
	
	@Test
	void testAddPersonaFalse() {
		assertFalse(personale.addPersona("persona1"));
	}
	
	@Test
	void testRemovePersonaTrue() throws ClassNotFoundException, SQLException {
		assertTrue(personale.removePersona(new Persona("persona1")));
	}
	
	@Test
	void testRemovePersonaFalse() throws ClassNotFoundException, SQLException {
		Persona pers=null;
		for(Persona persona : personale.getPersonale()) {
			pers=persona;
			persona.aumentaDebito(new Euro(5));
		}
		assertFalse(personale.removePersona(pers));
	}
	
	@Test
	void testDiminuisciDebitoTrue() throws ClassNotFoundException, SQLException {
		for(Persona persona : personale.getPersonale()) {
			persona.aumentaDebito(new Euro(5));
			assertTrue(personale.diminuisciDebito(persona, new Euro(5)));
		}
		assertEquals(personale.getPagamentiDebito().size(),3);
	}
	
	@Test
	void testDiminuisciDebitoFalseAmmontareMaggioreDebito() throws ClassNotFoundException, SQLException {
		for(Persona persona : personale.getPersonale()) {
			persona.aumentaDebito(new Euro(5));
			assertFalse(personale.diminuisciDebito(persona, new Euro(8)));
		}
	}
	
	@Test
	void testDiminuisciDebito() throws ClassNotFoundException, SQLException {
		for(Persona persona : personale.getPersonale()) {
			persona.aumentaDebito(new Euro(5));
			personale.diminuisciDebito(persona, new Euro(3));
		}
		for(Persona persona : personale.getIndebitati()) {
			assertEquals(persona.getDebito(),new Euro(2));
		}
	}
	
	@Test
	void testDiminuisciDebitoSize() throws ClassNotFoundException, SQLException {
		for(Persona persona : personale.getPersonale()) {
			personale.aumentaDebito(persona,new Euro(5));
			personale.diminuisciDebito(persona, new Euro(5));
		}
		assertEquals(personale.getIndebitati().size(),0);
	}
	
	@Test
	void testLoad() throws ClassNotFoundException, SQLException {
		for(Persona persona : personale.getPersonale()) {
			persona.aumentaDebito(new Euro(5));
			personale.diminuisciDebito(persona,new Euro(3));
		}
		String str="";
		try {
			FileWriter fileWriter = new FileWriter("res/test.txt", false);
			PrintWriter printWriter = new PrintWriter(fileWriter);
			printWriter.print(str=personale.print());
			printWriter.close();
		} catch (IOException e) {
		}
		personale = new Personale(connection);
		personale.load();
		File file = new File("res/test.txt");
        file.delete();
		assertEquals(personale.print(),str);
	}
	
	@Test
	void testLoadFileNotExists() throws ClassNotFoundException, SQLException {
		personale = new Personale(connection);
		personale.load();
		assertEquals(personale.getPersonale().size(),3);
	}
	
	@Test
	void testLoadFileWithNoRifornimenti() throws ClassNotFoundException, SQLException {
		personale = new Personale(connection);
		File file = new File("res/test.txt");
		personale.load();
        file.delete();
		assertEquals(personale.getPersonale().size(),3);
	}
	
}