package djajkov.app.ui;


import djajkov.app.*;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.zip.GZIPOutputStream;

import static javax.swing.JOptionPane.showMessageDialog;

class SaveListener extends MouseAdapter {

    GUI gui;

    public SaveListener(GUI gui){
        this.gui = gui;
    }

    public LocalDate convertToLocalDateViaInstant(Date dateToConvert) {
        return dateToConvert.toInstant()
                .atZone(ZoneId.systemDefault())
                .toLocalDate();
    }

    GUI.ManageOption manageOption;

    public void changeMO(GUI.ManageOption manageOption) {
        this.manageOption = manageOption;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        super.mouseClicked(e);
        String name = null, email = null, city = null, state = null, phone = null;
        LocalDate usableUntil = null;
        int capacity = 0, serialNumber = 0;
        ProductType productType = ProductType.TECHNOLOGY;
        boolean expandable = false, moreSamples = false;
        String alertMessage = "";
        switch (manageOption) {
            case CHANGE:
            case NEW:
                switch (gui.getCurrentScreen()) {
                    case CENTERS -> {
                        name = ((JTextField) gui.getManagerJPanel().getComponent(1)).getText();
                        email = ((JTextField) gui.getManagerJPanel().getComponent(3)).getText();
                        city = ((JTextField) gui.getManagerJPanel().getComponent(5)).getText();
                        state = ((JTextField) gui.getManagerJPanel().getComponent(7)).getText();
                        capacity = (int) ((JSpinner) gui.getManagerJPanel().getComponent(9)).getValue();
                        //part 2
                        if (name.isBlank())
                            alertMessage += "Please enter a name\n";
                        if (email.isBlank())
                            alertMessage += "Please enter am email\n";
                        if (city.isBlank())
                            alertMessage += "Please enter a city\n";
                        if (state.isBlank())
                            alertMessage += "Please enter a state\n";
                        if (capacity < 0)
                            alertMessage += "Capacity must be over -1\n";
                        if (!alertMessage.isBlank()) {
                            showMessageDialog(null, alertMessage);
                            return;
                        }
                    }
                    case STORES -> {
                        name = ((JTextField) gui.getManagerJPanel().getComponent(1)).getText();
                        phone = ((JTextField) gui.getManagerJPanel().getComponent(3)).getText();
                        city = ((JTextField) gui.getManagerJPanel().getComponent(5)).getText();
                        state = ((JTextField) gui.getManagerJPanel().getComponent(7)).getText();
                        //part 2
                        if (name.isBlank())
                            alertMessage += "Please enter a name\n";
                        if (phone.isBlank())
                            alertMessage += "Please enter a phone\n";
                        if (city.isBlank())
                            alertMessage += "Please enter a city\n";
                        if (state.isBlank())
                            alertMessage += "Please enter a state\n";
                        if (!alertMessage.isBlank()) {
                            showMessageDialog(null, alertMessage);
                            return;
                        }
                    }
                    case CARS -> {
                        name = ((JTextField) gui.getManagerJPanel().getComponent(1)).getText();
                        capacity = (int) ((JSpinner) gui.getManagerJPanel().getComponent(3)).getValue();
                        productType = ProductType.values()[((JComboBox) gui.getManagerJPanel().getComponent(5)).getSelectedIndex()];
                        //part 2
                        if (name.isBlank())
                            alertMessage += "Please enter a name\n";
                        if (capacity < 0)
                            alertMessage += "Capacity must be over-1\n";
                        if (!alertMessage.isBlank()) {
                            showMessageDialog(null, alertMessage);
                            return;
                        }
                    }
                    case VANS -> {
                        name = ((JTextField) gui.getManagerJPanel().getComponent(1)).getText();
                        capacity = (int) ((JSpinner) gui.getManagerJPanel().getComponent(3)).getValue();
                        expandable = ((JCheckBox) gui.getManagerJPanel().getComponent(4)).isSelected();
                        //part 2
                        if (name.isBlank())
                            alertMessage += "Please enter a name\n";
                        if (capacity < 0)
                            alertMessage += "Capacity must be over -1\n";
                        if (!alertMessage.isBlank()) {
                            showMessageDialog(null, alertMessage);
                            return;
                        }
                    }
                    case PRODUCTS -> {
                        try {
                            serialNumber = Integer.parseInt(((JTextField) gui.getManagerJPanel().getComponent(1)).getText());
                        } catch (Exception ignored) {
                            alertMessage += "Numbers only\n";
                            showMessageDialog(null, alertMessage);
                            return;
                        }
                        usableUntil = convertToLocalDateViaInstant((Date) (((JSpinner) gui.getManagerJPanel().getComponent(3)).getValue()));
                        productType = ProductType.values()[((JComboBox) gui.getManagerJPanel().getComponent(5)).getSelectedIndex()];
                        moreSamples = ((JCheckBox) gui.getManagerJPanel().getComponent(6)).isSelected();
                    }
                }
                break;
        }
        switch (manageOption) {
            case NEW -> {
                switch (gui.getCurrentScreen()) {
                    case CENTERS -> gui.getArrayCenters().add(new ProductsCenter(name, email, new Location(city, state), capacity));
                    case STORES -> gui.getArrayStores().add(new Store(name, phone, new Location(city, state)));
                    case CARS -> gui.getArrayCars().add(new Car(name, capacity, productType));
                    case VANS -> gui.getArrayVans().add(new Van(name, capacity, expandable));
                    case PRODUCTS -> {
                        gui.getArrayCenters().get(gui.getTempCentersIndex()).addProduct(new Product(serialNumber, usableUntil, productType, moreSamples));
                    }
                }
            }
            case CHANGE -> {
                switch (gui.getCurrentScreen()) {
                    case CENTERS -> gui.getArrayCenters().set(gui.getTempCentersIndex(), new ProductsCenter(name, email, new Location(city, state), capacity));
                    case STORES -> gui.getArrayStores().set(gui.getTempStoresIndex(), new Store(name, phone, new Location(city, state)));
                    case CARS -> gui.getArrayCars().set(gui.getTempCarsIndex(), new Car(name, capacity, productType));
                    case VANS -> gui.getArrayVans().set(gui.getTempVansIndex(), new Van(name, capacity, expandable));
                    case PRODUCTS -> gui.getArrayCenters().get(gui.getTempCentersIndex()).getProducts()[gui.getTempProductIndex()] = new Product(serialNumber, usableUntil, productType, moreSamples);
                }
            }
            case DELETE -> {
                switch (gui.getCurrentScreen()) {
                    case CENTERS -> gui.getArrayCenters().remove(gui.getTempCentersIndex());
                    case STORES -> gui.getArrayStores().remove(gui.getTempStoresIndex());
                    case CARS -> gui.getArrayCars().remove(gui.getTempCarsIndex());
                    case VANS -> gui.getArrayVans().remove(gui.getTempVansIndex());
                    case PRODUCTS -> gui.getArrayCenters().get(gui.getTempCentersIndex()).removeProduct(gui.getTempProduct().getSerialNumber());
                }
            }
        }

        try {
            FileOutputStream file;
            if(gui.getCurrentScreen() == GUI.Screen.PRODUCTS)
                file = new FileOutputStream(GUI.Screen.CENTERS.toString()+".gz");
            else
                file = new FileOutputStream(gui.getCurrentScreen().toString()+".gz");
            GZIPOutputStream gzip = new GZIPOutputStream(file);
            ObjectOutputStream out = new ObjectOutputStream(gzip);
            switch (gui.getCurrentScreen()) {
                case CENTERS, PRODUCTS -> out.writeObject(gui.getArrayCenters());
                case STORES -> out.writeObject(gui.getArrayStores());
                case CARS -> out.writeObject(gui.getArrayCars());
                case VANS -> out.writeObject(gui.getArrayVans());
            }
            out.close();
            file.close();
            System.out.println("Object has been serialized");
        } catch (IOException ioException) {
            ioException.printStackTrace();
            CustomLog.write(CustomLog.LogLevel.ERROR, ioException.toString());
        }

        CustomLog.write(CustomLog.LogLevel.DEFAULT, "Array "+ gui.getCurrentScreen().toString()+" modified with "+manageOption.toString());
        gui.changeScreen(gui.getCurrentScreen());
    }
}