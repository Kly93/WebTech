package de.hdm.weblog.tests;
import de.hdm.weblog.BlogAdministration;



public class CreatePerson {

	public static void main(String[] args) {
		
		BlogAdministration adm = new BlogAdministration();		
		adm.createPerson("Rathke", "Christian", "rathke@hdm-stuttgart.de");		

	}

}
