package org.example.database;

import org.example.model.Personen;

import java.sql.ClientInfoStatus;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DbController extends DbConnection{
    //Create the table if that doesn't exist!
    public void createTablePersonen(){
        // method to create the table!
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

    // Search the client based on the AHV_nummer
    public Personen searchPersonenByAHV(long AHV_nummer){
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

    // Search the client based on the ID
    public Personen searchPersonenById(long id){
        Personen personenResult = null;
        String sqlSearch = "SELECT * FROM Personen WHERE ID = ?";
        try {
            openConnection();
            System.out.println("Getting 'Personen' infos");
            PreparedStatement preparedStatement = connection.prepareStatement(sqlSearch);
            preparedStatement.setLong(1, id);
            ResultSet result = preparedStatement.executeQuery();
            while(result.next()){
                String name = result.getString("name");
                String vorname = result.getString("vorname");
                String anrede = result.getString("anrede");
                String geburtsdatum = result.getString("geburtsdatum");
                long ahvNummer = result.getLong("AHV_nummer");
                String region = result.getString("region");
                int kinder = result.getInt("kinder");
                personenResult = new Personen(name,vorname,anrede, LocalDate.parse(geburtsdatum),ahvNummer,region,kinder,id);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return personenResult;
    }

    // Update the information based on the ID
    public boolean updatePersonById(Personen personen){
        String sqlUpdate = "UPDATE Personen SET name = ?,vorname = ?,anrede = ?," +
                           "geburtsdatum = ?,AHV_nummer = ?,region = ?,kinder = ? WHERE id = ?";
        System.out.println("Trying to update the information!");
        try {
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlUpdate);
            preparedStatement.setString(1,personen.getName());
            preparedStatement.setString(2,personen.getVorname());
            preparedStatement.setString(3,personen.getAnrede());
            preparedStatement.setString(4,personen.getGeburtsdatum().toString());
            preparedStatement.setLong(5, personen.getAHV_Nummer());
            preparedStatement.setString(6,personen.getRegion());
            preparedStatement.setInt(7, personen.getKinder());
            preparedStatement.setLong(8, personen.getID());

            if(preparedStatement.executeUpdate() == 1){
                connection.commit();
                System.out.println("The information has been updated!");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return false;
    }

    public boolean removePersonById(long id){
        String sqlRemove = "DELETE FROM Personen WHERE ID = ?";
        System.out.println("Trying to delete the Information!");
        try {
            openConnection();
            connection.setAutoCommit(false);
            PreparedStatement preparedStatement = connection.prepareStatement(sqlRemove);
            preparedStatement.setLong(1, id);

            if(preparedStatement.executeUpdate() == 1){
                connection.commit();
                System.out.println("Information has been deleted!");
                return true;
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
        return false;
    }
    public List<Personen> personenListDB() {
        String sql = "SELECT * FROM Personen";
        List<Personen> personenList = new ArrayList<>();

        try {
            openConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                String name = resultSet.getString("name");
                String vorname = resultSet.getString("Vorname");
                String anrede = resultSet.getString("Anrede");
                LocalDate geburtsdatum = resultSet.getDate("Geburtsdatum").toLocalDate();
                Long AHV_Nummer = resultSet.getLong("AHV_Nummer");
                String region = resultSet.getString("Region");
                int kinder = resultSet.getInt("Kinder");
                Long id = resultSet.getLong("id");

                Personen person = new Personen(name, vorname, anrede, geburtsdatum, AHV_Nummer, region, kinder, id);
                personenList.add(person);
            }

            resultSet.close();
            preparedStatement.close();
            return personenList;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } finally {
            closeConnection();
        }
    }

}
