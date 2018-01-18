package de.hdm_stuttgart.softwarewerkzeuge.uebung01;

/**
 * Created by elcpt on 15.10.2016.
 */
public class Person {

    // Attribut
   String name;

    // Konstruktoren
    public Person(String name) {
        this.name = name;
    }

    public Person() {
    }

    // Getter und Setter
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
