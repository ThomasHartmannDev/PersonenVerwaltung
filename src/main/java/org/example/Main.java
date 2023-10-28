package org.example;

import org.example.database.DbController;
import org.example.model.Personen;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        DbController dbController = new DbController();
        dbController.createTablePersonen();

        //Personen p1 = new Personen("Thomas", "Hartmann", "Herr", LocalDate.of(2001,6,6), 1231234123412L, "Zürich",0);
        //dbController.insertPersonen(p1);

        Personen personen = dbController.searchPersonenAHV(1231234123412L);
        System.out.println("Search Result: " + personen);
    }
}