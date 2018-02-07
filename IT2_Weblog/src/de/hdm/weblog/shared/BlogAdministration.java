package de.hdm.weblog;

import java.util.Date;
import java.util.Vector;

import de.hdm.weblog.db.BlogeintragMapper;
import de.hdm.weblog.db.KommentarMapper;
import de.hdm.weblog.db.PersonMapper;

public class BlogAdministration {

	public BlogAdministration() {

		createPerson("Blogger", "Jonny", "blogger");

	}

	public Vector<Blogeintrag> findAll() {
		Vector<Blogeintrag> blogs = BlogeintragMapper.findAll();
		return blogs;
	}
	

	public Vector<Blogeintrag> findAllLatestFirst() {
		Vector<Blogeintrag> blogs = findAll();
		blogs.sort(null);
		return blogs;
	}
	

	public Blogeintrag findBlogeintragById(int id) {
		return BlogeintragMapper.findById(id);
	}

	public Person createPerson(String name, String vorname, String email) {
		// Prüfung, ob Person bereits vorhanden
		Person pers = PersonMapper.findByEmail(email);
		if (pers != null) {
			return pers;
		}

		pers = new Person(name, vorname, email);
		PersonMapper.insert(pers);

		return pers;

	}

	public Person findPersonByEmail(String email) {
		return PersonMapper.findByEmail(email);
	}

	public Person findPersonById(int id) {
		return PersonMapper.findById(id);
	}

	// Kommentar spezifisch zu Blogeintrag erstellt
	public Blogeintrag.Kommentar createKommentar(String inhalt, Person autor, Blogeintrag be) {
		Blogeintrag.Kommentar kom = be.createKommentar(inhalt, autor, new Date());
		KommentarMapper.insert(kom);
		return kom;
	}

	public Blogeintrag.Kommentar createKommentar(String inhalt, Blogeintrag be) {
		return createKommentar(inhalt, findPersonByEmail("blogger"), be);
	}

	public Blogeintrag createBlogeintrag(String inhalt, Person autor, String titel, String utitel) {

		// Textbeitrag anlegen
		// Daten aus Formular lesen

		Blogeintrag blogeintr = new Blogeintrag(inhalt, autor, new Date(), titel, utitel);

		BlogeintragMapper.insert(blogeintr);

		return blogeintr;

	}

	public Blogeintrag createBlogeintrag(String inhalt, String titel, String utitel) {
		return createBlogeintrag(inhalt, findPersonByEmail("blogger"), titel, utitel);
	}

	public void deleteBlogeintrag(Blogeintrag be) {
		// Alle Kommentare die in Blogeintrag enthalten sind werden iteriert und gelöscht
		for (Blogeintrag.Kommentar kom : (Vector<Blogeintrag.Kommentar>) be.getKommentare().clone()) {
			be.removeKommentar(kom);
			KommentarMapper.delete(kom);
		}
		BlogeintragMapper.delete(be);
	}

	public void deleteKommentar(Blogeintrag.Kommentar kom) {
		kom.getBlogeintrag().removeKommentar(kom);
		KommentarMapper.delete(kom);
	}

}
