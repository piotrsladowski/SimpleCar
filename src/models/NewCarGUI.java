package models;

import klasy.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.util.List;

public class NewCarGUI extends JFrame {
    public JPanel NewCarGUI;
    private JButton addButton;
    private JButton cancelButton;
    private JTextField modelTextField;
    private JTextField regNumberTextField;
    private JRadioButton gearboxSportRadio;
    private JRadioButton gearboxPoorRadio;
    private JRadioButton gearboxStandardRadio;
    private JRadioButton engineSportsRadio;
    private JRadioButton engineStandardRadio;
    private JRadioButton enginePoorRadio;

    final Gearbox sportsGearbox = new Gearbox("Sports Gearbox", 300, 1000,1,6,1);
    final Gearbox standardGearbox = new Gearbox("Standard Gearbox", 100, 4501,1,5,1);
    final Gearbox poorGearbox = new Gearbox("Poor Gearbox", 300, 1000,1,4,1);

    final Engine sportsEngine = new Engine("Sport Engine", 700, 2500, 10000);
    final Engine standardEngine = new Engine("Standard Engine", 700, 2500, 8000);
    final Engine poorEngine = new Engine("Poor Engine", 700, 2500, 6000);

    Gearbox gearboxChosen;
    Engine engineChosen;

    private String model;
    private String registrationNumber;


    public NewCarGUI(JFrame frame, List<Car> carList) {

        cancelButton.addActionListener(e -> {
            frame.setVisible(false);
            frame.dispose();
        });

        addButton.addActionListener(e -> {
            Position pos1 = new Position(0,0);
            Clutch c1 = new Clutch("clutch1", 30, 120);
            Car newCar = new Car(true, registrationNumber, model, c1, gearboxChosen, engineChosen);
            carList.add(newCar);
            frame.setVisible(false);
            frame.dispose();
        });


        //region RadioButtons
        gearboxSportRadio.addActionListener(e -> {
            if (gearboxSportRadio.isSelected()) {
                gearboxChosen = sportsGearbox;
                gearboxPoorRadio.setSelected(false);
                gearboxStandardRadio.setSelected(false);
                checkAddButton();
            }
            else {
                gearboxChosen = null;
                checkAddButton();
            }
        });
        gearboxStandardRadio.addActionListener(e -> {
            if (gearboxStandardRadio.isSelected()) {
                gearboxChosen = standardGearbox;
                gearboxPoorRadio.setSelected(false);
                gearboxSportRadio.setSelected(false);
                checkAddButton();
            }
            else {
                gearboxChosen = null;
                checkAddButton();
            }
        });
        gearboxPoorRadio.addActionListener(e -> {
            if (gearboxPoorRadio.isSelected()) {
                gearboxChosen = poorGearbox;
                gearboxSportRadio.setSelected(false);
                gearboxStandardRadio.setSelected(false);
                checkAddButton();
            }
            else {
                gearboxChosen = null;
                checkAddButton();
            }
        });
        engineSportsRadio.addActionListener(e -> {
            if (engineSportsRadio.isSelected()) {
                engineChosen = sportsEngine;
                enginePoorRadio.setSelected(false);
                engineStandardRadio.setSelected(false);
                checkAddButton();
            }
            else {
                engineChosen = null;
                checkAddButton();
            }
        });
        engineStandardRadio.addActionListener(e -> {
            if (engineStandardRadio.isSelected()) {
                engineChosen = standardEngine;
                enginePoorRadio.setSelected(false);
                engineSportsRadio.setSelected(false);
                checkAddButton();
            }
            else {
                engineChosen = null;
                checkAddButton();
            }
        });
        enginePoorRadio.addActionListener(e -> {
            if(enginePoorRadio.isSelected()){
            engineChosen = poorEngine;
            engineSportsRadio.setSelected(false);
            engineStandardRadio.setSelected(false);
            checkAddButton();
            }
            else {
                engineChosen = null;
                checkAddButton();
            }
        });
        //endregion


        modelTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changed();
            }
            public void removeUpdate(DocumentEvent e) {
                changed();
            }
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                model = modelTextField.getText();
                checkAddButton();

            }
        });

        regNumberTextField.getDocument().addDocumentListener(new DocumentListener() {
            public void changedUpdate(DocumentEvent e) {
                changed();
            }
            public void removeUpdate(DocumentEvent e) {
                changed();
            }
            public void insertUpdate(DocumentEvent e) {
                changed();
            }

            public void changed() {
                registrationNumber = regNumberTextField.getText();
                checkAddButton();
            }
        });

        addButton.addActionListener(e -> System.out.println("Car added"));
    }

    private void checkAddButton(){
        if(gearboxChosen == null || engineChosen == null || model == null || model.isEmpty() || registrationNumber == null || registrationNumber.isEmpty()){
            addButton.setEnabled(false);
        }
        else {
            addButton.setEnabled(true);
        }
    }
}
