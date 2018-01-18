package de.hdm.weblog.db;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Vector;

import de.hdm.weblog.Blogeintrag;
import de.hdm.weblog.Person;
import de.hdm.weblog.Textbeitrag;
import de.hdm.weblog.Blogeintrag.Kommentar;

/* Ansatz: Werte aus SQL Tabellen aus Textbeitrag, Kommentar & Blogeintrag 
 * (ID, Inhalt, Datum, Autor) für Blogeintrag über SQL Statement abfragen und in 
*/
public class BlogeintragMapper {

	
	/* Tabellendaten aus Textbeitrag und Blogeintrag über Methode createKommentar als Blogeinträge zurückgegeben 
	 * --> Redundante Abfrage (Vgl. KommentarMapper) nötig?
	 */
	public static Blogeintrag findById(int id) {
		Connection con = DBConnection.connection();
		Blogeintrag be = null;
		Statement stmt;
		try {
			stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT * FROM blogeintrag " + "WHERE id = " + id);
			if (rs.next()) {
				be = new Blogeintrag(rs.getString("inhalt"), 
						 			 PersonMapper.findById(rs.getInt("autor")), 
						 			 rs.getDate("datum"),
									 rs.getString("titel"), 
									 rs.getString("untertitel"));
				be.setId(rs.getInt("id"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return be;
	}
		
		
	// Irgendwas mit Vektoren die Kommentare beinhalten 
	public static Vector<Blogeintrag> findAll() {
		// Verbindung zu DB aufgebaut
				Connection con = DBConnection.connection();
				// Leeres Vektorobjekt erzeugt, in das alle Einträge gespeichert werden
				Vector<Blogeintrag> einträge = new Vector<Blogeintrag>();
				try {
					Statement stmt = con.createStatement();
					/* Abfrage in Form von Query aus Kommentartabelle und Speichern in rs
					 * --> Join der Tabellen textbeitrag & blogeintrag über gleichen id Wert
					*/
					ResultSet rs = stmt.executeQuery("select * from textbeitrag, blogeintrag "
							+ "where textbeitrag.id = blogeintrag.id");
					/* Iteration der Werte in Tabellen Textbeitrag und Kommentar und speichern der Rückgabe 
					 *  von Textbeitragklasse in kom
					*/
					while (rs.next()) {
						Blogeintrag be = new Blogeintrag(rs.getString("inhalt"), 
										    			 PersonMapper.findById(rs.getInt("autor")), 
														 rs.getDate("datum"), 
														 rs.getString("titel"), 
														 rs.getString("utitel"));
						be.setId(rs.getInt("id"));
						// Befüllte Werte aus Tabelle als neues Element in Vektor angefügt
						einträge.add(be);
						
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
				return einträge;
	}
	
	/* Eingabe über Methode in Tabellenwerte 
	 * Problem: Gleiche Werte wie in Kommentar eintragen? Blogeintrag = Kommentar?
	 */
	public static void insert(Blogeintrag be) {
		Connection con = DBConnection.connection();

		try {
			PreparedStatement statement = con.prepareStatement(
					"INSERT INTO blogeintrag (titel, untertitel) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setString(1, be.getTitel());
			statement.setString(2, be.getUntertitel());

			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				be.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	// LÖschen der id --> Methode doppelt? (Vgl. KommentarMapper)
	public static void delete(Blogeintrag blogeintrag) {
		delete(blogeintrag.getId());
	}

	// Löschen der id für Blogeintrag über delete Methode in KommentarMapper 
	public static void delete(int id) {
		KommentarMapper.delete(id);
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM blogeintrag " + "WHERE id = " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	// Löschen der Tabelle Blogeintrag 
	public static void removeTable() {
		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE blogeintrag");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	// Erstellen der Tabelle Blogeintrag
	public static void createTable() {
		String sqlString = "CREATE TABLE blogeintrag (\n" + 
				"  id int NOT NULL AUTO_INCREMENT,\n" + 
				"  titel VARCHAR(255) DEFAULT NULL,\n" + 
				"  untertitel VARCHAR(255) DEFAULT NULL, \n" +
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
