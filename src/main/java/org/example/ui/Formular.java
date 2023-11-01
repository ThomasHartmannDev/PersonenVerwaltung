package org.example.ui;

import javax.swing.*;
import com.toedter.calendar.JDateChooser;
import org.example.database.DbController;
import org.example.model.Personen;

import java.awt.*;
import java.awt.event.*;
import java.text.Normalizer;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.text.DateFormatter;
import javax.swing.text.MaskFormatter;
import javax.swing.text.DefaultFormatterFactory;

public class Formular extends JDialog {
    private JTextField nameField;
    private JTextField firstNameField;
    private JRadioButton herrRadioButton;
    private JRadioButton frauRadioButton;
    private ButtonGroup anredeGroup;
    private JDateChooser birthDateChooser;
    //private JTextField ahv_nummer;
    private JFormattedTextField ahv_nummer;
    private JComboBox<String> regionComboBox;
    private JSpinner kinderSpinner;

    /**
     *  Formular Constructor, on this constructor we create the Formular that we will use to insert new Datas on the Database
     * @param owner
     * @param dbController
     * @param personenList
     */
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
        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("###.####.####.##");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JFormattedTextField ahv_nummer = new JFormattedTextField();
        ahv_nummer.setFormatterFactory(new DefaultFormatterFactory(formatter));

        /**
         * Listener for make sure just Interger will be inserted on this field
         */
        ahv_nummer.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = ahv_nummer.getText();
                int l = value.length();
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    ahv_nummer.setEditable(true);
                } else {
                    ahv_nummer.setEditable(false);
                }
            }
        });
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

        /**
         * On this Listener, we make all verification if all the datas are inserted on the Right way.
         */
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String firstName = firstNameField.getText();
                String ahvm = ahv_nummer.getText().replace(".", "").replace(" ", "");
                String anrede = herrRadioButton.isSelected() ? "Herr" : "Frau";
                System.out.println("ahvm: " + ahvm);
                if (name.isEmpty() || firstName.isEmpty()) {
                    JOptionPane.showMessageDialog(Formular.this, "Action cancelled.\nFill Name & Vorname");
                } else if(ahvm.length() < 13 || ahvm.isEmpty()){
                    JOptionPane.showMessageDialog(Formular.this, "Action cancelled.\nAHV Nummer is wrong or not filled!");
                }
                else {
                    java.util.Date birthDate = birthDateChooser.getDate();
                    LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    String region = (String) regionComboBox.getSelectedItem();
                    int kinder = (int) kinderSpinner.getValue();
                    long ahv = Long.parseLong((ahv_nummer.getText()).replace(".", ""));
                    String message = "Name: " + name + "\nVorname: " + firstName + "\nAnrede: " + anrede +
                            "\nGeburtsdatum: " + birthDateLocal + "\nAHV-Nummer: " + ahv_nummer.getText() +
                            "\nRegion: " + region + "\nKinder: " + kinder;
                    Personen p = new Personen(name, firstName,anrede, birthDateLocal, ahv, region,kinder);
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
                        birthDateChooser.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
                        ahv_nummer.setText("");
                        regionComboBox.setSelectedItem(null);
                        kinderSpinner.setValue(0);

                    } else {
                        JOptionPane.showMessageDialog(Formular.this, "Action cancelled.");
                    }
                }
            }
        });

        birthDateChooser.setDate(Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()));
        herrRadioButton.setSelected(true);
        add(submitButton);
        setVisible(true);
    }

    /**
     * Constructor Formular, on this constructor use when we have already data, and we want to edit then.
     *
     * @param owner
     * @param dbController
     * @param p
     * @param personenList
     */
    public Formular(JFrame owner, DbController dbController, Personen p, PersonenList personenList) {
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

        MaskFormatter formatter = null;
        try {
            formatter = new MaskFormatter("###.####.####.##");
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        JFormattedTextField ahv_nummer = new JFormattedTextField();
        ahv_nummer.setFormatterFactory(new DefaultFormatterFactory(formatter));
        add(new JLabel("AHV-Nummer:"));
        //ahv_nummer = new JTextField();
        ahv_nummer.addKeyListener(new KeyAdapter() {
            public void keyPressed(KeyEvent ke) {
                String value = ahv_nummer.getText();
                int l = value.length();
                if (ke.getKeyChar() >= '0' && ke.getKeyChar() <= '9' || ke.getKeyCode() == KeyEvent.VK_BACK_SPACE) {
                    ahv_nummer.setEditable(true);
                } else {
                    ahv_nummer.setEditable(false);
                }
            }
        });
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
        JButton deleteButton = new JButton("Delete");
        /**
         * On this Listener, we make all verification if all the datas are inserted on the Right way.
         */
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String firstName = firstNameField.getText();
                String ahvm = ahv_nummer.getText().replace(".", "").replace(" ", "");
                String anrede = herrRadioButton.isSelected() ? "Herr" : "Frau";
                System.out.println("ahvm: " + ahvm);
                if (name.isEmpty() || firstName.isEmpty()) {
                    JOptionPane.showMessageDialog(Formular.this, "Action cancelled.\nFill Name & Vorname");
                } else if (ahvm.length() < 13 || ahvm.isEmpty()) {
                    JOptionPane.showMessageDialog(Formular.this, "Action cancelled.\nAHV Nummer is wrong or not filled!");
                } else {
                    java.util.Date birthDate = birthDateChooser.getDate();
                    LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                    long ahv = Long.parseLong((ahv_nummer.getText()).replace(".", ""));
                    String region = (String) regionComboBox.getSelectedItem();
                    int kinder = (int) kinderSpinner.getValue();

                    String message = "Name: " + name + "\nVorname: " + firstName + "\nAnrede: " + anrede +
                            "\nGeburtsdatum: " + birthDateLocal + "\nAHV-Nummer: " + ahvm +
                            "\nRegion: " + region + "\nKinder: " + kinder;
                    Personen newP = new Personen(name, firstName, anrede, birthDateLocal, ahv, region, kinder, p.getID());
                    int choice = JOptionPane.showConfirmDialog(Formular.this, message, "Confirm",
                            JOptionPane.OK_CANCEL_OPTION);
                    personenList.edit = true;
                    if (choice == JOptionPane.OK_OPTION) {
                        dbController.updatePersonById(newP);
                        personenList.personenList.clear();
                        personenList.personenList = dbController.personenListDB();
                        personenList.maxIndex = personenList.personenList.size();

                        JOptionPane.showMessageDialog(Formular.this, "Data Updated.");
                        Formular.this.dispose();

                    } else {
                        JOptionPane.showMessageDialog(Formular.this, "Action cancelled.");
                    }
                }
            }
        });
        /**
         * On this Listener, we Delete the data.
         */
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String firstName = firstNameField.getText();
                String anrede = herrRadioButton.isSelected() ? "Herr" : "Frau";
                java.util.Date birthDate = birthDateChooser.getDate();
                LocalDate birthDateLocal = birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
                long ahv = Long.parseLong((ahv_nummer.getText()).replace(".", "").replace(" ", ""));
                String region = (String) regionComboBox.getSelectedItem();
                int kinder = (int) kinderSpinner.getValue();

                String message = "Name: " + name + "\nVorname: " + firstName + "\nAnrede: " + anrede +
                        "\nGeburtsdatum: " + birthDateLocal + "\nAHV-Nummer: " + ahv_nummer.getText() +
                        "\nRegion: " + region + "\nKinder: " + kinder;
                int choice = JOptionPane.showConfirmDialog(Formular.this, message, "Confirm",
                        JOptionPane.OK_CANCEL_OPTION);
                if (choice == JOptionPane.OK_OPTION) {
                    dbController.removePersonById(p.getID());
                    personenList.personenList.clear();
                    personenList.personenList = dbController.personenListDB();
                    personenList.maxIndex = personenList.personenList.size();

                    JOptionPane.showMessageDialog(Formular.this, "Data Deleted.");
                    Formular.this.dispose();

                } else {
                    JOptionPane.showMessageDialog(Formular.this, "Action cancelled.");
                }
            }
        });

        add(deleteButton);
        add(submitButton);

        //Set all the information from the user.

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
        ahv_nummer.setText(ahv.toString());
        regionComboBox.setSelectedItem(p.getRegion());
        kinderSpinner.setValue(p.getKinder());

        setVisible(true);

    }

    /**
     * converToLocalDataToDate, we need to convert the LocalDate, to the Date type.
     * @param localDate
     * @return
     */
    public static Date convertToLocalDateToDate(LocalDate localDate) {
        ZoneId zoneId = ZoneId.systemDefault();
        return Date.from(localDate.atStartOfDay(zoneId).toInstant());
    }
}
