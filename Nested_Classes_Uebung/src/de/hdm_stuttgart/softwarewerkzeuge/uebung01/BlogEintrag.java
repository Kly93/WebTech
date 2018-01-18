package de.hdm_stuttgart.softwarewerkzeuge.uebung01;

import java.util.Date;

/**
 *
 *
 * Created by elcpt on 15.10.2016.
 */
public class BlogEintrag extends TextBeitrag {

    // Einziges Attribut
    String titel;

    // innere Klasse: Kommentar
    class Kommentar extends TextBeitrag {

        BlogEintrag blogeintrag;

        // Getter und Setter
        public BlogEintrag getBlogeintrag() {
            return blogeintrag;
        }

        public void setBlogEintrag(BlogEintrag blogeintrag) {
            this.blogeintrag = blogeintrag;
        }
    }

    // Konstruktor
    public BlogEintrag(Person autor, Date datum, String text, String titel) {
        super(autor, datum, text);
        this.titel = titel;
    }

    // Getter und Setter
    public String getTitel() {
        return titel;
    }

    public void setTitel(String titel) {
        this.titel = titel;
    }
}
