package de.hdm.weblog;

import java.util.Date;
import java.util.Vector;

public class Blogeintrag extends Textbeitrag {

	/* Elemente können dynamisch in Vektor hinzugefügt werden
	 * */
	private Vector<Kommentar> kommentare = new Vector<Kommentar>();

	private String titel;
	private String untertitel;
	
	
	public class Kommentar extends Textbeitrag {
		
		// Aufruf über Angabe der Textbeitrag Superklasse, da Inhalt hier gesetzt
		public Kommentar(String inhalt) {
			super(inhalt);
		}
		
		// Methode ruft getThisBLogeintrag in Blogeintrag Klasse auf um Objekt vom Typ Blogeintrag wiederzugeben
		public Blogeintrag getBlogeintrag() {
			return getThisBlogeintrag();
		}

		public String toString() {
			return getAutor().toString() + ": "  + getInhalt(); 
		}

	}
	
	

	public Blogeintrag(String inhalt) {
		super(inhalt);
	}

	public Blogeintrag(String inhalt, Person autor, Date datum, String titel, String utitel) {
		this(inhalt);
		this.titel = titel;
		this.untertitel = utitel;
		setAutor(autor);
		setDatum(datum);
	}
	
	// Würde auch funktionieren, wenn Kommentarklasse private!
	public Kommentar createKommentar(String inhalt, Person autor, Date datum) {
		Kommentar kom = new Kommentar(inhalt);
		kom.setAutor(autor);
		kom.setDatum(datum);
		// Neues Element an Vektor hinzugefügt über Aufruf der Variable in Blogeintrag Klasse
		kommentare.add(kom);
		return kom;
	}
	
	// Returned sich selbst
	private Blogeintrag getThisBlogeintrag() {
		return this;
	}


	public String getTitel() {
		return titel;
	}

	public void setTitel(String titel) {
		this.titel = titel;
	}

	public String getUntertitel() {
		return untertitel;
	}

	public void setUntertitel(String untertitel) {
		this.untertitel = untertitel;
	}

	public Vector<Kommentar> getKommentare() {
		return kommentare;
	}

	public void setKommentare(Vector<Kommentar> koms) {
		kommentare = koms;
	}

	public void addKommentar(Kommentar k) {
		kommentare.add(k);
		
	}

	public void removeKommentar(Kommentar k) {
		kommentare.remove(k);
	}

	public String toString() {
		return getAutor().toString() + ": " + titel + " (" + untertitel + ") " + ": " + getInhalt();
	}
	

}
