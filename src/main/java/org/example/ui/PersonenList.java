package org.example.ui;

import org.example.database.DbController;
import org.example.model.Personen;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class PersonenList extends JFrame {
    private JLabel titleLabel;
    private JSeparator separator;
    private JTextArea userTextArea;
    private JButton previousButton;
    private JButton nextButton;
    private JButton newButton;
    private JButton editButton;
    public List<Personen> personenList;
    private int currentIndex = 0;
    public int maxIndex;
    private int oldMaxIndex;
    public boolean edit;
    Formular forms;
    public PersonenList(DbController dbController) {
        this.personenList = dbController.personenListDB();
        this.maxIndex = personenList.size();
        this.oldMaxIndex = maxIndex;
        this.edit = false;
        System.out.println("MaxIndex:" + maxIndex);
        setTitle("People Screen");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 350);
        setLocationRelativeTo(null);

        // Layout to organize the elements
        BorderLayout layout = new BorderLayout();
        setLayout(layout);

        // Top panel with the title and separator
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));
        titleLabel = new JLabel("People: ");
        topPanel.add(titleLabel);
        separator = new JSeparator(SwingConstants.HORIZONTAL);
        topPanel.add(separator);

        // Center panel with the user display area
        userTextArea = new JTextArea(getPersonenDetails(currentIndex));
        userTextArea.setEditable(false);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        separator = new JSeparator();
        previousButton = new JButton("<");
        nextButton = new JButton(">");
        newButton = new JButton("New");
        editButton = new JButton("Edit");

        bottomPanel.add(previousButton);
        bottomPanel.add(nextButton);
        bottomPanel.add(new JSeparator(SwingConstants.VERTICAL)); // Add a vertical separator
        bottomPanel.add(newButton);
        bottomPanel.add(editButton);

        // Adding the panels to the main window
        add(topPanel, BorderLayout.NORTH);
        add(userTextArea, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action for the "Previous" button
        previousButton.addActionListener(e -> {
            if (currentIndex == 0) {
                currentIndex = maxIndex;
            } else {
                currentIndex--;
                userTextArea.setText(getPersonenDetails(currentIndex));
            }
        });

        // Action for the "Next" button
        nextButton.addActionListener(e -> {
            System.out.println(currentIndex);
            currentIndex++;
            if (currentIndex >= personenList.size()) {
                currentIndex = 0;
            }
            userTextArea.setText(getPersonenDetails(currentIndex));
        });

        // Action for the "New" button
        newButton.addActionListener(e -> {
            // Logic to create a new user
           forms = new Formular(this, dbController, this);
        });

        // Action for the "Edit" button
        editButton.addActionListener(e -> {
            // Logic to edit the current user
            forms = new Formular(this, dbController, personenList.get(currentIndex), this);
        });

        if(oldMaxIndex != maxIndex){
            this.dispose();
            oldMaxIndex = maxIndex;
            new PersonenList(dbController);

        } else if(edit){
            System.out.println("edit true");
            System.out.println("edit true");
            System.out.println("edit true");
            System.out.println("edit true");
            System.out.println("edit true");
            System.out.println("edit true");
        }
    }

    private String getPersonenDetails(int index) {
        if (index >= 0 && index < personenList.size()) {
            Personen person = personenList.get(index);
            return "Name: " + person.getName() + "\nVorname: " + person.getVorname() + "\nAnrede: " + person.getAnrede()
                    + "\nGeburtsdatum: " + person.getGeburtsdatum() + "\nAHV_Nummer: " + person.getAHV_Nummer()
                    + "\nRegion: " + person.getRegion() + "\nKinder: " + person.getKinder() + "\nID: " + person.getID();
        }
        return "No data";
    }
}
