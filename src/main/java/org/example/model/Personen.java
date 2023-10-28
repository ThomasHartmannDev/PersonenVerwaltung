package org.example.model;

import java.time.LocalDate;
import java.util.Date;

public class Personen {
    private String name;
    private String Vorname;
    private String Anrede;
    private LocalDate Geburtsdatum;
    private long AHV_Nummer;
    private String Region;
    private int Kinder;

    public Personen(String name, String vorname, String anrede, LocalDate geburtsdatum, Long AHV_Nummer, String region, int kinder) {
        this.name = name;
        Vorname = vorname;
        Anrede = anrede;
        Geburtsdatum = geburtsdatum;
        this.AHV_Nummer = AHV_Nummer;
        Region = region;
        Kinder = kinder;
    }

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
