package org.example.ui;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import org.example.database.DbController;
import org.example.model.Personen;

import java.awt.*;
import java.awt.event.*;
import java.text.Normalizer;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JComboBox;

public class Formular extends JDialog {
    private JTextField nameField;
    private JTextField firstNameField;
    private JRadioButton herrRadioButton;
    private JRadioButton frauRadioButton;
    private ButtonGroup anredeGroup;
    private JDateChooser birthDateChooser;
    private JTextField ahv_nummer;
    private JComboBox<String> regionComboBox;
    private JSpinner kinderSpinner;

    public Formular(JFrame owner, DbController dbController, PersonenList personenList){
        super(owner, "Formular", true);
        setTitle("Data Form");
        setSize(400, 300);
        setLayout(new GridLayout(9, 2));
        setLocationRelativeTo(null);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Vorname:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Anrede:"));
        herrRadioButton = new JRadioButton("Herr");
        frauRadioButton = new JRadioButton("Frau");
        anredeGroup = new ButtonGroup();
        anredeGroup.add(herrRadioButton);
        anredeGroup.add(frauRadioButton);
        JPanel anredePanel = new JPanel();
        anredePanel.add(herrRadioButton);
        anredePanel.add(frauRadioButton);
        add(anredePanel);

        add(new JLabel("Geburtsdatum:"));
        birthDateChooser = new JDateChooser();
        add(birthDateChooser);

        add(new JLabel("AHV-Nummer:"));
        ahv_nummer = new JTextField();
        add(ahv_nummer);

        add(new JLabel("Region:"));
        String[] swissRegions = {
                "Aargau",
                "Appenzell Innerrhoden",
                "Appenzell Ausserrhoden",
                "Basel-Landschaft",
                "Basel-Stadt",
                "Bern",
                "Freiburg",
                "Genf (Genève)",
                "Glarus",
                "Graubünden (Grigioni)",
                "Jura",
                "Luzern",
                "Neuenburg (Neuchâtel)",
                "Nidwalden",
                "Obwalden",
                "Schaffhausen",
                "Schwyz",
                "Solothurn",
                "St. Gallen",
                "Tessin (Ticino)",
                "Thurgau",
                "Uri",
                "Waadt (Vaud)",
                "Wallis (Valais)",
                "Zug",
                "Zürich"
        };
        regionComboBox = new JComboBox<>(swissRegions);
        add(regionComboBox);

        add(new JLabel("Kinder:"));
        kinderSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        add(kinderSpinner);

        JButton submitButton = new JButton("Submit");

        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String firstName = firstNameField.getText();
                String anrede = herrRadioButton.isSelected() ? "Herr" : "Frau";
                java.util.Date birthDate = birthDateChooser.getDate();
                LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long ahv_nummer = Long.parseLong(Formular.this.ahv_nummer.getText());
                String region = (String) regionComboBox.getSelectedItem();
                int kinder = (int) kinderSpinner.getValue();

                String message = "Name: " + name + "\nVorname: " + firstName + "\nAnrede: " + anrede +
                        "\nGeburtsdatum: " + birthDateLocal + "\nAHV-Nummer: " + ahv_nummer +
                        "\nRegion: " + region + "\nKinder: " + kinder;
                Personen p = new Personen(name, firstName,anrede, birthDateLocal, ahv_nummer, region,kinder);
                int choice = JOptionPane.showConfirmDialog(Formular.this, message, "Confirm",
                        JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    dbController.insertPersonen(p);
                    personenList.personenList.clear();
                    personenList.personenList = dbController.personenListDB();
                    personenList.maxIndex = personenList.personenList.size();
                    JOptionPane.showMessageDialog(Formular.this, "Data sended.");
                    nameField.setText("");
                    firstNameField.setText("");
                    herrRadioButton.setSelected(false);
                    frauRadioButton.setSelected(false);
                    birthDateChooser.setDate(null);
                    Formular.this.ahv_nummer.setText("");
                    regionComboBox.setSelectedItem(null);
                    kinderSpinner.setValue(0);

                } else {
                    JOptionPane.showMessageDialog(Formular.this, "Action cancelled.");
                }
            }
        });

        add(submitButton);
        setVisible(true);
    }

    public Formular(JFrame owner, DbController dbController, Personen p, PersonenList personenList){
        super(owner, "Formular", true);
        setTitle("Edit Data Form");
        setSize(400, 300);
        setLayout(new GridLayout(9, 2));
        setLocationRelativeTo(null);

        add(new JLabel("Name:"));
        nameField = new JTextField();
        add(nameField);

        add(new JLabel("Vorname:"));
        firstNameField = new JTextField();
        add(firstNameField);

        add(new JLabel("Anrede:"));
        herrRadioButton = new JRadioButton("Herr");
        frauRadioButton = new JRadioButton("Frau");
        anredeGroup = new ButtonGroup();
        anredeGroup.add(herrRadioButton);
        anredeGroup.add(frauRadioButton);
        JPanel anredePanel = new JPanel();
        anredePanel.add(herrRadioButton);
        anredePanel.add(frauRadioButton);
        add(anredePanel);

        add(new JLabel("Geburtsdatum:"));
        birthDateChooser = new JDateChooser();
        add(birthDateChooser);

        add(new JLabel("AHV-Nummer:"));
        ahv_nummer = new JTextField();

        add(ahv_nummer);

        add(new JLabel("Region:"));
        String[] swissRegions = {
                "Aargau",
                "Appenzell Innerrhoden",
                "Appenzell Ausserrhoden",
                "Basel-Landschaft",
                "Basel-Stadt",
                "Bern",
                "Freiburg",
                "Genf (Genève)",
                "Glarus",
                "Graubünden (Grigioni)",
                "Jura",
                "Luzern",
                "Neuenburg (Neuchâtel)",
                "Nidwalden",
                "Obwalden",
                "Schaffhausen",
                "Schwyz",
                "Solothurn",
                "St. Gallen",
                "Tessin (Ticino)",
                "Thurgau",
                "Uri",
                "Waadt (Vaud)",
                "Wallis (Valais)",
                "Zug",
                "Zürich"
        };
        regionComboBox = new JComboBox<>(swissRegions);
        add(regionComboBox);

        add(new JLabel("Kinder:"));
        kinderSpinner = new JSpinner(new SpinnerNumberModel(0, 0, 100, 1));
        add(kinderSpinner);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String firstName = firstNameField.getText();
                String anrede = herrRadioButton.isSelected() ? "Herr" : "Frau";
                java.util.Date birthDate = birthDateChooser.getDate();
                LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long ahv_nummer = Long.parseLong(Formular.this.ahv_nummer.getText());
                String region = (String) regionComboBox.getSelectedItem();
                int kinder = (int) kinderSpinner.getValue();

                String message = "Name: " + name + "\nVorname: " + firstName + "\nAnrede: " + anrede +
                        "\nGeburtsdatum: " + birthDateLocal + "\nAHV-Nummer: " + ahv_nummer +
                        "\nRegion: " + region + "\nKinder: " + kinder;
                Personen newP = new Personen(name, firstName,anrede, birthDateLocal, ahv_nummer, region,kinder, p.getID());
                int choice = JOptionPane.showConfirmDialog(Formular.this, message, "Confirm",
                        JOptionPane.OK_CANCEL_OPTION);
                personenList.edit = true;
                if (choice == JOptionPane.OK_OPTION) {
                    dbController.updatePersonById(newP);
                    personenList.personenList.clear();
                    personenList.personenList = dbController.personenListDB();
                    personenList.maxIndex = personenList.personenList.size();

                    JOptionPane.showMessageDialog(Formular.this, "Data Sended.");
                    Formular.this.dispose();

                } else {
                    JOptionPane.showMessageDialog(Formular.this, "Action cancelled.");
                }
            }
        });
        add(submitButton);


        nameField.setText(p.getName());
        firstNameField.setText(p.getVorname());
        if(p.getAnrede().equals("Herr")){
            herrRadioButton.setSelected(true);
        } else {
            frauRadioButton.setSelected(false);
        }
        Date date = convertToLocalDateToDate(p.getGeburtsdatum());
        birthDateChooser.setDate(date);
        Long ahv = p.getAHV_Nummer();
        Formular.this.ahv_nummer.setText(ahv.toString());
        regionComboBox.setSelectedItem(p.getRegion());
        kinderSpinner.setValue(p.getKinder());

        setVisible(true);

    }
    public static Date convertToLocalDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }
}
