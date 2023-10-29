package org.example;

import org.example.database.DbController;
import org.example.model.Personen;
import org.example.ui.PersonenList;

import java.time.LocalDate;
import java.util.List;

public class Main {


    public static void main(String[] args) {

        DbController dbController = new DbController();
        dbController.createTablePersonen();

//        Personen p1 = new Personen("Thomas", "Hartmann", "Herr", LocalDate.of(2001,6,6), 1231234123412L, "Bern",0);
//        dbController.insertPersonen(p1);
//        Personen p2 = new Personen("nome", "Han", "Herr", LocalDate.of(2201,6,6), 12122123412L, "Zürich",0);
//        dbController.insertPersonen(p2);
//        Personen p3 = new Personen("Thos", "Hart", "Frau", LocalDate.of(2501,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p3);
//        Personen p4 = new Personen("Ths", "Hartn", "Herr", LocalDate.of(1990,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p4);
//        Personen p5 = new Personen("Ts", "Hartn", "Herr", LocalDate.of(2061,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p5);
//        Personen p6 = new Personen("Ths", "Han", "Herr", LocalDate.of(2051,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p6);
//        Personen p7 = new Personen("Th1212omas", "Ha212n", "Herr", LocalDate.of(2031,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p7);
//        Personen p8 = new Personen("Thoas", "Hart1212mann", "Herr", LocalDate.of(2021,6,6), 123123213112L, "Aru",0);
//        dbController.insertPersonen(p8);
//        Personen p9 = new Personen("Thos", "Hart1212mann", "Herr", LocalDate.of(2011,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p9);
//        Personen p10 = new Personen("Thmas", "Hn", "Herr", LocalDate.of(2012,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p10);
//        Personen p11 = new Personen("Ths", "Hann", "Herr", LocalDate.of(2031,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p11);
//        Personen p12 = new Personen("Tas", "Han", "Herr", LocalDate.of(2034,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p12);
//        Personen p13 = new Personen("Thas", "Hartmn", "Herr", LocalDate.of(2003,6,6), 123123213112L, "Argau",0);
//        dbController.insertPersonen(p13);
        List<Personen> personenListDB = dbController.personenListDB();

        PersonenList personenList = new PersonenList(personenListDB);
        personenList.setVisible(true);
        personenList.setResizable(false);

//        Personen personen = dbController.searchPersonenById(1L);
//        System.out.println("Search Result: " + personen);
//
//        //Update the user info
//        personen.setGeburtsdatum(LocalDate.of(2000,12,12));
//        personen.setKinder(2);
//        personen.setRegion("Zürich");
//
//        dbController.updatePersonById(personen);
//
//        personen = dbController.searchPersonenById(1L);
//        System.out.println("Search Result: " + personen);

//        boolean b = dbController.removePersonById(1L);
//        System.out.println("Result: " + b);


    }
}