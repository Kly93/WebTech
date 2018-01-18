package de.hdm_stuttgart.softwarewerkzeuge.uebung01;

import java.util.Date;

public class Start {

    public static void main(String[] args) {
    	
    	
    	// Auf String begrenzten Beitrag instanziieren
        Beitrag<String> testBeitrag = new Beitrag<String>();
        
        // String-Typen funktionieren
        testBeitrag.setInhalt("Lorem ipsum sit dolor amet");
        
        // Integer werden direkt von eclipse bemängelt, Fehler!
        testBeitrag.setInhalt(32);
        
        // Das hier wäre jedoch wieder erlaubt
    	testBeitrag.setInhalt("32");
    	

    }
}
