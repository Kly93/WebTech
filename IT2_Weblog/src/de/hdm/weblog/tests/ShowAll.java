package de.hdm.weblog.tests;

import de.hdm.weblog.BlogAdministration;
import de.hdm.weblog.Blogeintrag;

public class ShowAll {

	public static void main(String[] args) {

		// Test Blogeinträge auslesen

		BlogAdministration adm = new BlogAdministration();
		// findAll liefert einen Vector mit allen Blogeintrag-Objekten zurück
		// Diese werden ausgegeben
		for (Blogeintrag be : adm.findAll()) {
			System.out.println(be);
			for (Blogeintrag.Kommentar kom : be.getKommentare()) {
				System.out.println("     " + kom);
			}
		}

	}

}
