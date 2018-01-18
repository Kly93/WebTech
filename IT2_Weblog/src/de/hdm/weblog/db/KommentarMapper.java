package de.hdm.weblog.db;

import java.sql.Connection;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.weblog.Blogeintrag;

public class KommentarMapper {

	
	// Alle Kommentare aus Vektor in Blogeintrag werden ausgegeben
	public static void findAllForBlogeintrag(Blogeintrag be) {
		// Verbindung zu DB aufgebaut
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			/* Abfrage in Form von Query aus Kommentartabelle und Speichern in rs
			 * --> Join der Tabellen textbeitrag & kommentar �ber gleichen id Wert
			*/
			ResultSet rs = stmt.executeQuery("select * from textbeitrag, kommentar "
					+ "where textbeitrag.id = kommentar.id and kommentar.blogeintrag = " + be.getId());
			/* Iteration der Werte in Tabellen Textbeitrag und Kommentar und speichern der R�ckgabe 
			 *  von Textbeitragklasse in kom
			*/
			while (rs.next()) {
				Blogeintrag.Kommentar kom = be.createKommentar(rs.getString("inhalt"),
						// findbyid? -> Da FK von Objekt Person ist, wird �ber PersonMapper id aufgerufen
						PersonMapper.findById(rs.getInt("autor")),
						rs.getDate("datum"));
				kom.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	// Kommentar beif�gen 
	public static void insert(Blogeintrag.Kommentar kommentar) {
		// �ber insert Methode aus TextbeitragMapper wird neuer Blogeintrag in Textbeitrag Tabelle eingef�gt
		TextbeitragMapper.insert(kommentar);
		// Verbindung zu DB aufgebaut
		Connection con = DBConnection.connection();
		try {
			// Erzeugung eines SQL Statements �ber Verbindung con 
			Statement statement = con.createStatement();
			/* Einf�gen der Eingaben aus Methoden aus Kommentar Klasse in Tabelle Kommentar
			 * --> Warum �ber getBlogeintrag() auf getId()? --> id als Fremdschl�ssel zu BLogeintrag Tabelle abgerufen
			*/
			statement.executeUpdate("INSERT INTO kommentar (id, blogeintrag) VALUES (" + kommentar.getId() + ", "
					+ kommentar.getBlogeintrag().getId() + ")");
		} catch (SQLException e) {
			e.printStackTrace();	
		}

	}

	// L�schen der Id �ber Aufruf der getId() --> In Applikation?
	public static void delete(Blogeintrag.Kommentar kommentar) {
		delete(kommentar.getId());
	}

	// L�schen der Tabelleneintr�ge f�r id in Kommentartabelle
	public static void delete(int id) {
		TextbeitragMapper.delete(id);
		// Verbindung zu DB aufgebaut
		Connection con = DBConnection.connection();
		try {
			// SQL Abfrage generiert
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM kommentar " + "WHERE id = " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// L�schen der Tabelle Kommentar
	public static void removeTable() {
		// Aufbau zu DB
		Connection con = DBConnection.connection();
		try {
			// Setzen des SQL Statements
			Statement stmt = con.createStatement();
			// SQL Befehl zum L�schen der Tabelle Kommentar in DB
			stmt.executeUpdate("DROP TABLE kommentar");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Erstellen der Tabelle Kommentar, Setzen des Fremdschl�ssels
	public static void createTable() {
		// Aufbau der Tabelle Kommentar und speichern in sqlString
		String sqlString = "CREATE TABLE kommentar (\n" + 
				"  id int NOT NULL AUTO_INCREMENT,\n" + 
				"  blogeintrag int DEFAULT NULL,\n" + 
				"  PRIMARY KEY (id), \n" + 
				"  CONSTRAINT blogeintragid FOREIGN KEY (blogeintrag) REFERENCES blogeintrag (id) ON DELETE NO ACTION ON UPDATE NO ACTION\n" + 
				" )";
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate(sqlString);
		} catch (SQLException e) {
			System.out.println(sqlString);
			System.out.println(e.getMessage());
		}

	}

}
