package org.example.model;

import java.time.LocalDate;

public class Personen {
    private String name;
    private String Vorname;
    private String Anrede;
    private LocalDate Geburtsdatum;
    private long AHV_Nummer;
    private String Region;
    private int Kinder;
    private long id;


    /**
     * This model from Personen, we use when we are getting the infomation from the Database, we need the ID.
     * @param name
     * @param vorname
     * @param anrede
     * @param geburtsdatum
     * @param AHV_Nummer
     * @param region
     * @param kinder
     */
    public Personen(String name, String vorname, String anrede, LocalDate geburtsdatum, Long AHV_Nummer, String region, int kinder, Long id) {
        this.name = name;
        Vorname = vorname;
        Anrede = anrede;
        Geburtsdatum = geburtsdatum;
        this.AHV_Nummer = AHV_Nummer;
        Region = region;
        Kinder = kinder;
        this.id = id;
    }

    /***
     * This model from Personen, we use when we are creating a new user. we don't need to set up the ID manually.
     * @param name
     * @param vorname
     * @param anrede
     * @param geburtsdatum
     * @param AHV_Nummer
     * @param region
     * @param kinder
     * @param id
     */
    public Personen(String name, String vorname, String anrede, LocalDate geburtsdatum, Long AHV_Nummer, String region, int kinder) {
        this.name = name;
        Vorname = vorname;
        Anrede = anrede;
        Geburtsdatum = geburtsdatum;
        this.AHV_Nummer = AHV_Nummer;
        Region = region;
        Kinder = kinder;
    }

    /**
     *  Getters & Setters
     */
    public void setID(Long id){this.id = id;}
    public long getID(){ return id;}
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVorname() {
        return Vorname;
    }

    public void setVorname(String vorname) {
        Vorname = vorname;
    }

    public String getAnrede() {
        return Anrede;
    }

    public void setAnrede(String anrede) {
        Anrede = anrede;
    }

    public LocalDate getGeburtsdatum() {
        return Geburtsdatum;
    }

    public void setGeburtsdatum(LocalDate geburtsdatum) {
        Geburtsdatum = geburtsdatum;
    }

    public long getAHV_Nummer() {
        return AHV_Nummer;
    }

    public void setAHV_Nummer(long AHV_Nummer) {
        this.AHV_Nummer = AHV_Nummer;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public int getKinder() {
        return Kinder;
    }

    public void setKinder(int kinder) {
        Kinder = kinder;
    }

    @Override
    public String toString() {
        return  "Name: " + name +
                "| Vorname: " + Vorname +
                "| Anrede: " + Anrede +
                "| Geburtsdatum: " + Geburtsdatum +
                "| AHV Nummer: " + AHV_Nummer +
                "| Region: " + Region +
                "| Kinder: " + Kinder;
    }

}
