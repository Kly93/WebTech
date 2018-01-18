package de.hdm.weblog.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import de.hdm.weblog.Textbeitrag;

public class TextbeitragMapper {

	public static void insert(Textbeitrag textbeitrag) {
		Connection con = DBConnection.connection();

		try {
			PreparedStatement statement = con.prepareStatement(
					"INSERT INTO textbeitrag (datum, inhalt, autor) VALUES (?, ?, ?)", Statement.RETURN_GENERATED_KEYS);
			statement.setDate(1, new java.sql.Date(textbeitrag.getDatum().getTime()));
			statement.setString(2, textbeitrag.getInhalt());
			statement.setInt(3, textbeitrag.getAutor().getId());

			statement.executeUpdate();
			ResultSet rs = statement.getGeneratedKeys();
			if (rs.next()) {
				textbeitrag.setId(rs.getInt(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void delete(Textbeitrag textbeitrag) {
		delete(textbeitrag.getId());
	}
	
	public static void delete(int id) {

		Connection con = DBConnection.connection();
		try {
			Statement stmt = con.createStatement();
			stmt.executeUpdate("DELETE FROM textbeitrag " + "WHERE id = " + id);
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	
	
	public static void removeTable() {
		Connection con = DBConnection.connection();
		Statement stmt;
		try {
			stmt = con.createStatement();
			stmt.executeUpdate("DROP TABLE textbeitrag");
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}
	}

	public static void createTable() {
		String sqlString = "CREATE TABLE textbeitrag (\n" + 
				"  id int NOT NULL AUTO_INCREMENT,\n" + 
				"  inhalt VARCHAR(255),\n" + 
				"  datum date DEFAULT NULL,\n" + 
				"  autor int DEFAULT NULL,\n" + 
				"  PRIMARY KEY (id),\n" + 
				"  CONSTRAINT autorid FOREIGN KEY (autor) REFERENCES person (id) ON DELETE NO ACTION ON UPDATE NO ACTION\n" + 
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
