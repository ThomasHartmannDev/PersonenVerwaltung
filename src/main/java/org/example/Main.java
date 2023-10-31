package org.example;

import org.example.database.DbController;
import org.example.model.Personen;
import org.example.ui.Formular;
import org.example.ui.PersonenList;

import java.time.LocalDate;
import java.util.List;


public class Main {
    public boolean reload = false;

    public static void main(String[] args) {

        DbController dbController = new DbController();
        dbController.createTablePersonen();
        PersonenList personenList = new PersonenList(dbController);
        personenList.setVisible(true);
        personenList.setResizable(false);

    }
}