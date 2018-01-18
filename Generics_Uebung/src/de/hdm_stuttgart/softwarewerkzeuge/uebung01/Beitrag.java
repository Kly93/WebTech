package de.hdm_stuttgart.softwarewerkzeuge.uebung01;

import java.util.Date;

/**
 * Uebung 01 - Verschachtelte Klassen
 *
 */
// Generics: T ist hier die Typenvariable für einen beliebigen Typ
// T könnte z.B. Integer, String, BufferedImage, Date, Object, .. sein
public class Beitrag<T> {

    // Deklaration der Attribute
    Person autor;
    Date datum;
    
    // Auch das dazugehörige Attribut wird als Typ T deklariert
    T inhalt;

    // Konstruktor mit allen Attributen
    // Alles was z.B. mit String inhalt funktionieren würde
    // Funktioniert auch analog mit T inhalt
    public Beitrag(Person autor, Date datum, T inhalt) {
        this.autor = autor;
        this.datum = datum;
        this.inhalt = inhalt;
    }

    // Default-Konstruktor
    public Beitrag() {
    }

    // Getter und Setter
    public Person getAutor() {
        return autor;
    }

    public void setAutor(Person autor) {
        this.autor = autor;
    }

    public Date getDatum() {
        return datum;
    }

    public void setDatum(Date datum) {
        this.datum = datum;
    }

    public T getInhalt() {
        return inhalt;
    }

    public void setInhalt(T inhalt) {
        this.inhalt = inhalt;
    }
}
