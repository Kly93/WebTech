package de.hdm.weblog.db;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Verwalten einer Verbindung zur Datenbank.
 * 
 * @author Thies
 */
public class DBConnection {

	private static Connection con = null;
	
	/**
	 * Der Name des Datenbank-Servers in form eines Hostnames.
	 */
	private static String dbHost = 
			//"db4free.net";
			"localhost:3306";
			//"edu.hdm-server.eu:1433";
	
	
	/**
	 * Der Name der Datenbank. Diese Datenbank wird entweder in Derby mit Hilfe von
	 * Dateien im Dateisystem realisiert. Dann ist der Name der Datenbank gleichzeit
	 * der Name des Verzeichnisses, in dem sich die Datenbankdateien befinden. Alternativ
	 * kann die Datenbank als MySQL- oder SQLServer-Datenbank realisiert werden.
	 */
	private static String dbName = "it2weblog";

	/**
	 * Die URL, mit deren Hilfe die Datenbank angesprochen wird.
	 */
	private static String connectionUrl = 
			"jdbc:mysql://" + dbHost + "/" + dbName + "?user=root&password=hallo123";
			//"jdbc:oracle:thin:@" + hostname + ":" + portnumber + ":"  + dbName + ";create=true";
			//"jdbc:sqlserver://" + dbHost + ";user=weblogdemo;password=weblogdemo";

	/**
	 * 
	 * @return DAS <code>DBConncetion</code>-Objekt.
	 * @see con
	 */
	public static Connection connection() {
		// Wenn es bisher keine Conncetion zur DB gab, ...
		if (con == null) {
			try {
				/*
				 * Manchmal (?) wird der DB-Treiber nicht automatisch instanziiert. Hiermit wird dies erzwungen.
				 */
				Class.forName("com.mysql.jdbc.Driver").newInstance();
				/*
				 * Der DriverManager nimmt eine Verbindung mit den
				 * oben in der Variable url angegebenen Verbindungsinformationen
				 * auf.
				 *
				 * Diese Verbindung wird in der statischen Variable con
				 * abgespeichert und fortan verwendet.
				 */
				con = DriverManager.getConnection(connectionUrl);
			} catch (SQLException e1) {
				con = null;
				e1.printStackTrace();
			} catch (InstantiationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		// Zurueckgegeben der Verbindung
		return con;
	}
}
