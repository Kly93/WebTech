package de.hdm.weblog.tests;
import java.util.Vector;

import de.hdm.weblog.BlogAdministration;
import de.hdm.weblog.Blogeintrag;



public class deleteBlogeintrag {

	public static void main(String[] args) {
		
		BlogAdministration adm = new BlogAdministration();
		Vector<Blogeintrag> blogs = adm.findAll();
		if (blogs.size()>0) {
			adm.deleteBlogeintrag(blogs.get(0));
		}
		
		ShowAll.main(new String[0]);
	}

}
