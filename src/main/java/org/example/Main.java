/**
 * @author Thomas Hartmann, Aya El Houssami, Jozsef Gönczi
 * @version 1.0
 * */


package org.example;

import org.example.database.DbController;
import org.example.ui.PersonenList;



public class Main {
    /**
     * Main Start the first Window - PersonenList and Start the dbControler
     * */
    public static void main(String[] args) {

        DbController dbController = new DbController();
        dbController.createTablePersonen();
        PersonenList personenList = new PersonenList(dbController);
        personenList.setVisible(true);
        personenList.setResizable(false);

    }
}