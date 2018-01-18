package de.hdm.weblog.db;

import java.sql.*;

import de.hdm.weblog.Person;

public class PersonMapper {

	// Abfrage aus Person Tabelle, dann schreiben in Java Objekte
	public static Person findById(int id) {
		Connection con = DBConnection.connection();
		Person person = null;
		Statement stmt;
		try {
			stmt = con.createStatement();
			// * = Spalten der Tabelle, id wird aus Java Variable an Query gehängt und über ResultSet zurückgegeben 
			ResultSet rs = stmt.executeQuery("SELECT * FROM person WHERE id = " + id);
			// Wenn id gefunden, ist next() True und neues Objekt von Typ Person wird erzeugt und 
			if (rs.next()) {
				// getString() SQL Methode die Inhalt als String ausgibt --> Konvertierung SQL Datentyp in Java Datentyp
				person = new Person(rs.getString("nachname"), rs.getString("vorname"), rs.getString("email"));
				person.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
	}

	public static Person findByEmail(String email) {
		Connection con = DBConnection.connection();
		Person person = null;
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM person " + "WHERE email = \'" + email + "\'");
			if (rs.next()) {
				person = new Person(rs.getString("nachname"), rs.getString("vorname"), rs.getString("email"));
				person.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return person;
	}

	public static void insert(Person person) {
		Connection con = DBConnection.connection();

		try {
			// PreparedStatement verhindert, dass über Texteingaben ungewollt fremde SQL Statements eingegeben werden
			PreparedStatement stmt = con.prepareStatement(
					// Return_generated_keys gibt automatisch Zugriff auf gesetzte Primärschlüssel 
					"INSERT INTO person (nachname, vorname, email) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			stmt.setString(1, person.getName());
			stmt.setString(2, person.getVorname());
			stmt.setString(3, person.getEmail());
			// DB aktualisieren
			stmt.executeUpdate();

			// Über Return_generated_keys können zurückgegebene Keys aus DB für Person erzeugt werden
			ResultSet key = stmt.getGeneratedKeys();
			if (key.next()) {
				// 1 = Spaltenname, über Inkrementierung in Return_generated_keys wird hochgezählt und Id vergeben
				person.setId(key.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void delete(int id) {

		Connection con = DBConnection.connection();

		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM person " + "WHERE id = " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// Method Overloading, sodass über beide Parameter in Tabelle Werte gelöscht werden
	public static void delete(Person person) {
		delete(person.getId());
	}
	
	public static void removeTable() {
		Connection con = DBConnection.connection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE person");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createTable() {
		String sqlString = "CREATE TABLE person (\n" + 
				"  id int NOT NULL AUTO_INCREMENT,\n" + 
				"  vorname VARCHAR(45) DEFAULT NULL,\n" + 
				"  nachname VARCHAR(45) DEFAULT NULL,\n" + 
				"  email VARCHAR(45) DEFAULT NULL,\n" + 
				"  PRIMARY KEY (id)\n" +  
				" )";
		Connection con = DBConnection.connection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate(sqlString);
		} catch (SQLException e) {
			System.out.println(sqlString);
			System.out.println(e.getMessage());
		}

	}

}
