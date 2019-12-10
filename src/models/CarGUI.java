package models;
import klasy.*;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.Timer;
import java.util.regex.Pattern;

public class CarGUI {
    //region JComponents
    private JPanel CarGUI;
    private JTextField statsGearTextField;
    private JTextField statsXPositionTextField;
    private JTextField statsYPositionTextField;
    private JComboBox infoComboBox;
    private JButton addCarButton;
    private JButton removeButton;
    private JTextField infoRelativeSpeedTextField;
    private JTextField infoNameTextField;
    private JTextField infoRegistrationNumberTextField;
    private JTextField statsEngineSpeedTextField;
    private JButton steerIncreaseGearButton;
    private JButton steerReduceGearButton;
    private JButton steerChangeEngineSpeedButton;
    private JTextField steerSpeedChangeTextField;
    private JPanel mapJPanel;
    private JLabel mapCarLabel;
    private JButton steerGoButton;
    private JTextField steerInputXTextField;
    private JTextField steerInputYTextField;
    private JTextField statsCarSpeedTextField;
    private JButton steerTurnOnButton;
    private JButton steerTurnOffButton;
    //endregion

    private Car car;
    private JFrame newCarFrame;

    final Gearbox sportsGearbox = new Gearbox("Sports Gearbox", 300, 1000,1,6,1);
    final Engine sportsEngine = new Engine("Sport Engine", 700, 2500, 10000);
    final Clutch clutch1 = new Clutch("clutch1", 30, 120);
    final Car ferrari = new Car(false, "KR 1954", "F50", clutch1, sportsGearbox, sportsEngine);

    List<Car> carList = new ArrayList<>(Arrays.asList(ferrari));
    List<String> carListString = new ArrayList<>();


    public CarGUI() {
        this.car = carList.get(0);
        updateComboBox();
        refreshCarInfo();
        initComponents();
        Timer timer = new Timer();
        TimerTask myTask = new TimerTask() {
            @Override
            public void run() {
                refreshStatistics();
                refreshMap();
            }
        };
        if(carList.isEmpty()){
            removeButton.setEnabled(false);
        }


        mapJPanel.setBackground(Color.YELLOW);
        mapJPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                System.out.println(e.getX() + " ---- " + e.getY());
                car.driveTo(e.getX(),e.getY());
                //car.driveTo(e.getX(), e.getY());
            }
        });
        //region TextField Controllers

        steerSpeedChangeTextField.getDocument().addDocumentListener(new DocumentListener() {
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
                if(Pattern.matches("^[0-9-]?[0-9]+$", steerSpeedChangeTextField.getText())){
                    steerChangeEngineSpeedButton.setEnabled(true);
                }
                else {
                    steerChangeEngineSpeedButton.setEnabled(false);
                }
            }
        });

        steerInputXTextField.getDocument().addDocumentListener(new DocumentListener() {
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
                if(Pattern.matches("^[0-9]+$", steerInputYTextField.getText()) && Pattern.matches("^[0-9]+$", steerInputXTextField.getText())){
                    steerGoButton.setEnabled(true);
                }
                else {
                    steerGoButton.setEnabled(false);
                }
            }
        });

        steerInputYTextField.getDocument().addDocumentListener(new DocumentListener() {
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
                if(Pattern.matches("^[0-9]+$", steerInputYTextField.getText()) && Pattern.matches("^[0-9]+$", steerInputXTextField.getText())){
                    steerGoButton.setEnabled(true);
                }
                else {
                    steerGoButton.setEnabled(false);
                }
            }
        });

        //endregion

        //region Button Listeners

        addCarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(newCarFrame == null) {
                    newCarFrame = new JFrame("Add new car");
                    newCarFrame.setContentPane(new NewCarGUI(newCarFrame, carList).NewCarGUI);
                    newCarFrame.addWindowListener(new WindowAdapter() {
                        @Override
                        public void windowClosed(WindowEvent e) {
                            super.windowClosed(e);
                            System.out.println("Button exit");
                            updateComboBox();
                            initComponents();
                            refreshCarInfo();
                            newCarFrame = null;
                        }
                        @Override
                        public void windowClosing(WindowEvent e) {
                            super.windowClosing(e);
                            System.out.println("System exit");
                            newCarFrame = null;
                        }
                    });
                    //newCarFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    newCarFrame.pack();
                    newCarFrame.setVisible(true);
                }
            }
        });

        removeButton.addActionListener(e -> {
            int selectedIndex = infoComboBox.getSelectedIndex();
            carList.remove(selectedIndex);
            updateComboBox();
            if(carList.isEmpty()){
                removeButton.setEnabled(false);
            }
            try {
                car.interrupt();
            } catch (NullPointerException ex) {
                //Thread must be started before interrupting
            }
            car = null;
        });

        steerChangeEngineSpeedButton.addActionListener(e -> {
            int deltaSpeed = Integer.parseInt(steerSpeedChangeTextField.getText());
            car.faster(deltaSpeed);
        });
        steerReduceGearButton.addActionListener(e -> {
            if(!car.decreaseGear()){
                steerReduceGearButton.setEnabled(false);
            }
            else {
                steerIncreaseGearButton.setEnabled(true);
            }
            refreshStatistics();
        });
        steerIncreaseGearButton.addActionListener(e -> {
            if(!car.increaseGear()){
                steerIncreaseGearButton.setEnabled(false);
            }
            else {
                steerReduceGearButton.setEnabled(true);
            }
            refreshStatistics();
        });

        steerGoButton.addActionListener(e -> {
            int x = Integer.parseInt(steerInputXTextField.getText());
            int y = Integer.parseInt(steerInputYTextField.getText());
            car.driveTo(x, y);
        });
        steerTurnOnButton.addActionListener(e -> {
            car.turnOn();
            steerTurnOnButton.setEnabled(false);
            steerTurnOffButton.setEnabled(true);
        });
        steerTurnOffButton.addActionListener(e -> {
            car.turnOff();
            steerTurnOffButton.setEnabled(false);
            steerTurnOnButton.setEnabled(true);
        });

        //endregion

        infoComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
            }
        });
        infoComboBox.addActionListener(e -> {
            int selectedIndex = infoComboBox.getSelectedIndex();
            System.out.println(selectedIndex);
            if(selectedIndex >= 0){
                car = carList.get(selectedIndex);
                refreshStatistics();
            }
            else {
                clearGUI();
            }
        });
        timer.schedule(myTask,1,100);
    }

    private void updateComboBox(){
        carListString.clear();
        for (Car car :carList) {
                carListString.add(car.getModel());
                if(!car.isAlive())
                    car.start();
        }
        infoComboBox.setModel(new DefaultComboBoxModel(carListString.toArray()));
        if(!carList.isEmpty()){
            removeButton.setEnabled(true);
        }
        infoComboBox.setSelectedIndex(carListString.size()-1);
    }

    private void clearGUI(){
        infoNameTextField.setText("");
        infoRegistrationNumberTextField.setText("");
        mapCarLabel.setVisible(false);
        infoRelativeSpeedTextField.setText("");
        statsGearTextField.setText("");
        statsEngineSpeedTextField.setText("");
        statsCarSpeedTextField.setText("");
        statsXPositionTextField.setText("");
        statsYPositionTextField.setText("");
    }

    private void refreshCarInfo(){
        if (car != null) {
            infoNameTextField.setText(car.getModel());
            infoRegistrationNumberTextField.setText(car.getRegistrationNumber());
        }
    }

    private void refreshStatistics(){
        if (car != null) {
            double relativeSpeed = Math.round(car.getCarSpeed()/car.getMaxSpeed() * 100);
            infoRelativeSpeedTextField.setText(relativeSpeed + " %");
            statsCarSpeedTextField.setText(String.valueOf(car.getCarSpeed()));
            statsEngineSpeedTextField.setText(String.valueOf(car.getEngineSpeed()));
            statsGearTextField.setText(String.valueOf(car.getCurrentGear()));
            statsXPositionTextField.setText(String.valueOf(car.getPosition().getX()));
            statsYPositionTextField.setText(String.valueOf(car.getPosition().getY()));
        }
    }

    private void refreshMap(){
        if (car != null) {
            mapCarLabel.setText("*");
            mapCarLabel.setForeground(Color.red);
            double x = Math.round(car.getPosition().getX());
            double y = Math.round(car.getPosition().getY());
            mapCarLabel.setLocation((int)x, (int)y);
        }
    }

    private void initComponents(){
        if(steerSpeedChangeTextField.getText().isEmpty()){
            steerChangeEngineSpeedButton.setEnabled(false);
        }
        if(steerInputXTextField.getText().isEmpty() || steerInputYTextField.getText().isEmpty()){
            steerGoButton.setEnabled(false);
        }
        steerTurnOffButton.setEnabled(false);
        steerTurnOnButton.setEnabled(true);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("CarGUI");
        frame.setContentPane(new CarGUI().CarGUI);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
    }



}
