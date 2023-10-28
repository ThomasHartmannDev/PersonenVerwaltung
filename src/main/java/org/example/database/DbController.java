package org.example.database;

import org.example.model.Personen;

import java.sql.ClientInfoStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

public class DbController extends DbConnection{
    public void createTablePersonen(){ // method to create the table!
        // Create the SQL String
        String tablePersonen = "CREATE TABLE IF NOT EXISTS Personen" +
                "(" +
                "ID bigint AUTO_INCREMENT Primary key," +
                "name VARCHAR(25) not null," +
                "vorname VARCHAR(100) not null," +
                "anrede VARCHAR(4) not null," +
                "geburtsdatum DATE not null," +
                "AHV_nummer bigint not null," +
                "region VARCHAR(50) not null," +
                "kinder int not null" +
                ")";
        // Always using try-catch because the connection with the DB can fail or even the SQL String can be wrong!
        try {
            openConnection();
            connection.prepareStatement(tablePersonen).execute();
            System.out.println("Table Created or already exist!");
        } catch (SQLException e) {
            System.out.println("createTable Error: ");
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    //Add a new "Personen" to the table!
    public void insertPersonen(Personen personen){
        String insertPersonen = "INSERT INTO Personen (name,vorname,anrede,geburtsdatum,AHV_nummer,region,kinder)" +
                "VALUES (?,?,?,?,?,?,?)";
        try{
            openConnection();
            connection.setAutoCommit(false); // Don't save everything directly on the database,
            // so if we have an error it doesn't mess up the database!

            PreparedStatement preparedStatement = connection.prepareStatement(insertPersonen);

            preparedStatement.setString(1,personen.getName());
            preparedStatement.setString(2,personen.getVorname());
            preparedStatement.setString(3,personen.getAnrede());
            preparedStatement.setString(4,personen.getGeburtsdatum().toString());
            preparedStatement.setLong(5, personen.getAHV_Nummer());
            preparedStatement.setString(6,personen.getRegion());
            preparedStatement.setInt(7, personen.getKinder());

            if(preparedStatement.execute()){ // If there isn't any error
                connection.commit(); // Commit to the database!
            }
            System.out.println("Data Inserted!");
        } catch (SQLException e){
            System.out.println("Can't insert data on the table");
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

    public Personen searchPersonenAHV(long AHV_nummer){
        Personen personenResult = null;
        String sqlSearch = "SELECT * FROM Personen WHERE AHV_nummer = ?";
        try {
            openConnection();
            System.out.println("Getting 'Personen' infos");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSearch);
            preparedStatement.setLong(1, AHV_nummer);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                String name = result.getString("name");
                String vorname = result.getString("vorname");
                String anrede = result.getString("anrede");
                String geburtsdatum = result.getString("geburtsdatum");
                long ahvNummer = result.getLong("AHV_nummer");
                String region = result.getString("region");
                int kinder = result.getInt("kinder");
                personenResult = new Personen(name,vorname,anrede, LocalDate.parse(geburtsdatum),ahvNummer,region,kinder);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return personenResult;
    }
}
