package djajkov.app.ui;

import djajkov.app.*;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.text.Collator;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.zip.GZIPInputStream;

import static javax.swing.JOptionPane.showMessageDialog;

public class GUI {

    GUI health = this;

    public Date convertToDateViaInstant(LocalDate dateToConvert) {
        return java.util.Date.from(dateToConvert.atStartOfDay()
                .atZone(ZoneId.systemDefault())
                .toInstant());
    }

    private JPanel root;
    private JPanel dashboard;
    private JPanel unity;
    private JButton backJButton;
    private JSplitPane showCenters;
    private JSplitPane showCars;
    private JSplitPane showStores;
    private JSplitPane showVans;
    private JButton buttonNew;
    private JButton buttonChange;
    private JButton buttonDelete;
    private JButton buttonTransferTo;
    private JButton buttonCancel;
    private JButton buttonCancel1;
    private JButton buttonSave;
    private JButton showTransferButton;
    private JPanel buttonsJPanel;
    private JPanel navigationJPanel;
    private JTable tableJTable;
    private JLabel nameJLabel;
    private JPanel transfer;
    private JTable transferFrom;
    private JTable transferTo;
    private JButton showProductsButton;
    private JPanel manager;
    private JLabel object;
    private JPanel managerJPanel;
    private JLabel name1;
    private JLabel dashboardQuantityCenters;
    private JLabel dashboardQuantityCars;
    private JLabel dashboardQuantityStores;
    private JLabel dashboardQuantityVans;

    public JPanel getManagerJPanel() {
        return managerJPanel;
    }

    public void setManagerJPanel(JPanel managerJPanel) {
        this.managerJPanel = managerJPanel;
    }

    public Screen getCurrentScreen() {
        return currentScreen;
    }

    public void setCurrentScreen(Screen currentScreen) {
        this.currentScreen = currentScreen;
    }

    public ArrayList<Van> getArrayVans() {
        return arrayVans;
    }

    public void setArrayVans(ArrayList<Van> arrayVans) {
        this.arrayVans = arrayVans;
    }

    public ArrayList<Car> getArrayCars() {
        return arrayCars;
    }

    public void setArrayCars(ArrayList<Car> arrayCars) {
        this.arrayCars = arrayCars;
    }

    public ArrayList<ProductsCenter> getArrayCenters() {
        return arrayCenters;
    }

    public void setArrayCenters(ArrayList<ProductsCenter> arrayCenters) {
        this.arrayCenters = arrayCenters;
    }

    public ArrayList<Store> getArrayStores() {
        return arrayStores;
    }

    public void setArrayStores(ArrayList<Store> arrayStores) {
        this.arrayStores = arrayStores;
    }

    public int getTempCentersIndex() {
        return tempCentersIndex;
    }

    public void setTempCentersIndex(int tempCentersIndex) {
        this.tempCentersIndex = tempCentersIndex;
    }

    public int getTempStoresIndex() {
        return tempStoresIndex;
    }

    public void setTempStoresIndex(int tempStoresIndex) {
        this.tempStoresIndex = tempStoresIndex;
    }

    public int getTempCarsIndex() {
        return tempCarsIndex;
    }

    public void setTempCarsIndex(int tempCarsIndex) {
        this.tempCarsIndex = tempCarsIndex;
    }

    public int getTempVansIndex() {
        return tempVansIndex;
    }

    public void setTempVansIndex(int tempVansIndex) {
        this.tempVansIndex = tempVansIndex;
    }

    public int getTempProductIndex() {
        return tempProductIndex;
    }

    public void setTempProductIndex(int tempProductIndex) {
        this.tempProductIndex = tempProductIndex;
    }

    private Screen currentScreen = null, previousScreen = null;

    ArrayList<Van> arrayVans = new ArrayList<Van>();
    ArrayList<Car> arrayCars = new ArrayList<Car>();
    ArrayList<ProductsCenter> arrayCenters = new ArrayList<ProductsCenter>();
    ArrayList<Store> arrayStores = new ArrayList<Store>();

    SaveListener saveSL = null;

    Product tempProduct;

    public Product getTempProduct() {
        return tempProduct;
    }

    public void setTempProduct(Product tempProduct) {
        this.tempProduct = tempProduct;
    }

    ProductsCenter tempCenter;
    Store tempStore;
    Car tempCar;
    Van tempVan;
    Car tempTransferCar;
    Van tempTransferVan;
    int tempCentersIndex, tempStoresIndex, tempCarsIndex, tempVansIndex, tempProductIndex, tempTransferVanIndex, tempTransferCarIndex;


    public enum Screen {
        DASHBOARD("Dashboard"),
        CENTERS("Centers"),
        STORES("Stores"),
        CARS("Cars"),
        VANS("Vans"),
        PRODUCTS("Products");

        private final String text;

        Screen(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    public enum ManageOption {
        NEW("New"),
        CHANGE("Change"),
        DELETE("Delete");

        private final String text;

        ManageOption(final String text) {
            this.text = text;
        }

        @Override
        public String toString() {
            return text;
        }
    }

    class ManageListener extends MouseAdapter {

        ManageOption manageOption;

        ManageListener(ManageOption manageOption) {
            this.manageOption = manageOption;
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            if ((manageOption == ManageOption.NEW) && (currentScreen == Screen.PRODUCTS))
                if (arrayCenters.get(tempCentersIndex).getProducts()[arrayCenters.get(tempCentersIndex).getProducts().length - 1] != null) {
                    showMessageDialog(null, "Max capacity");
                    changeScreen(currentScreen);
                    return;
                }

            if (saveSL == null) {
                saveSL = new SaveListener(health);
                buttonSave.addMouseListener(saveSL);
            }
            ((CardLayout) root.getLayout()).first(root);
            ((CardLayout) root.getLayout()).next(root);
            ((CardLayout) root.getLayout()).next(root);
            ((CardLayout) root.getLayout()).next(root);
            name1.setText(manageOption.toString() + " " + currentScreen.toString().toLowerCase());
            managerJPanel.removeAll();
            managerJPanel.setLayout(new GridLayout(10, 1));

            saveSL.changeMO(manageOption);
            //drawing
            switch (manageOption) {
                case NEW:
                case CHANGE:
                    switch (currentScreen) {
                        case CENTERS -> {
                            managerJPanel.add(new JLabel("Name"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("Email"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("City"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("State"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("Capacity"));
                            JSpinner j = new JSpinner();
                            j.setEditor(new JSpinner.NumberEditor(j));
                            ((JSpinner.NumberEditor) j.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
                            managerJPanel.add(j);
                        }
                        case STORES -> {
                            managerJPanel.add(new JLabel("Naziv"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("Telefon"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("Kraj"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("State"));
                            managerJPanel.add(new JTextField());
                        }
                        case CARS -> {
                            managerJPanel.add(new JLabel("Name"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("Capacity"));
                            JSpinner j1 = new JSpinner();
                            j1.setEditor(new JSpinner.NumberEditor(j1));
                            ((JSpinner.NumberEditor) j1.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
                            managerJPanel.add(j1);
                            managerJPanel.add(new JLabel("Product type"));
                            ArrayList<String> ct = new ArrayList<String>();
                            ct.add("TECHNOLOGY");
                            ct.add("CLOTHES");
                            ct.add("FOOD");
                            Collections.sort(ct, Collator.getInstance(Locale.getDefault()));
                            JComboBox c5 = new JComboBox(ct.toArray());
                            managerJPanel.add(c5);
                        }
                        case VANS -> {
                            managerJPanel.add(new JLabel("Name"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("Capacity"));
                            JSpinner j2 = new JSpinner();
                            j2.setEditor(new JSpinner.NumberEditor(j2));
                            ((JSpinner.NumberEditor) j2.getEditor()).getTextField().setHorizontalAlignment(JTextField.LEFT);
                            managerJPanel.add(j2);
                            managerJPanel.add(new JCheckBox("Expandable"));
                        }
                        case PRODUCTS -> {
                            managerJPanel.add(new JLabel("Serial number"));
                            managerJPanel.add(new JTextField());
                            managerJPanel.add(new JLabel("Usable until"));
                            JSpinner j3 = new JSpinner(new SpinnerDateModel(new Date(), null, null, Calendar.MONTH));
                            j3.setEditor(new JSpinner.DateEditor(j3, "dd-MM-yyyy"));
                            managerJPanel.add(j3);
                            managerJPanel.add(new JLabel("Product type"));
                            ArrayList<String> ct = new ArrayList<String>();
                            ct.add("TECHNOLOGY");
                            ct.add("CLOTHES");
                            ct.add("FOOD");
                            Collections.sort(ct, Collator.getInstance(Locale.getDefault()));
                            JComboBox c3 = new JComboBox(ct.toArray());
                            managerJPanel.add(c3);
                            managerJPanel.add(new JCheckBox("More samples"));
                        }
                    }
                    break;
                case DELETE:
                    JTable tempTable = new JTable();
                    DefaultTableModel model = new DefaultTableModel() {
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            return false;
                        }
                    };
                    switch (currentScreen) {
                        case CENTERS -> {
                            model.setColumnCount(5);
                            model.setColumnIdentifiers(new String[]{"Name", "Email", "City", "State", "Capacity"});
                            model.addRow(new String[]{tempCenter.getName(), tempCenter.getEmail(), tempCenter.getLocation().getCity(), tempCenter.getLocation().getState(), String.valueOf(tempCenter.getProducts().length)});
                        }
                        case STORES -> {
                            model.setColumnCount(4);
                            model.setColumnIdentifiers(new String[]{"Name", "Phone", "City", "State"});
                            model.addRow(new String[]{tempStore.getName(), tempStore.getPhone(), tempStore.getLocation().getCity(), tempStore.getLocation().getState()});
                        }
                        case CARS -> {
                            model.setColumnCount(3);
                            model.setColumnIdentifiers(new String[]{"Name", "Capacity", "Product type"});
                            model.addRow(new String[]{tempCar.getName(), String.valueOf(tempCar.getProducts().length), String.valueOf(tempCar.getProductType())});
                        }
                        case VANS -> {
                            model.setColumnCount(3);
                            model.setColumnIdentifiers(new String[]{"Name", "Capacity", "Expandable"});
                            model.addRow(new String[]{tempVan.getName(), String.valueOf(tempVan.getCapacity()), String.valueOf(tempVan.getExtendableCapacity())});
                        }
                        case PRODUCTS -> {
                            model.setColumnCount(4);
                            model.setColumnIdentifiers(new String[]{"Serial number", "Usable until", "Product type", "More samples"});
                            model.addRow(new String[]{String.valueOf(tempProduct.getSerialNumber()), String.valueOf(tempProduct.getUsableUntil()), String.valueOf(tempProduct.getProductType()), String.valueOf(tempProduct.isMoreSamples())});
                            tableJTable.setModel(model);
                        }
                    }
                    tempTable.setModel(model);
                    managerJPanel.add(tempTable);
                    break;
            }

            if (manageOption == ManageOption.CHANGE) {
                switch (currentScreen) {
                    case CENTERS -> {
                        ((JTextField) managerJPanel.getComponent(1)).setText(tempCenter.getName());
                        ((JTextField) managerJPanel.getComponent(3)).setText(tempCenter.getEmail());
                        ((JTextField) managerJPanel.getComponent(5)).setText(tempCenter.getLocation().getCity());
                        ((JTextField) managerJPanel.getComponent(7)).setText(tempCenter.getLocation().getState());
                        ((JSpinner) managerJPanel.getComponent(9)).setValue(tempCenter.getProducts().length);
                    }
                    case STORES -> {
                        ((JTextField) managerJPanel.getComponent(1)).setText(tempStore.getName());
                        ((JTextField) managerJPanel.getComponent(3)).setText(tempStore.getPhone());
                        ((JTextField) managerJPanel.getComponent(5)).setText(tempStore.getLocation().getCity());
                        ((JTextField) managerJPanel.getComponent(7)).setText(tempStore.getLocation().getState());
                    }
                    case CARS -> {
                        ((JTextField) managerJPanel.getComponent(1)).setText(tempCar.getName());
                        ((JSpinner) managerJPanel.getComponent(3)).setValue(tempCar.getProducts().length);
                        ((JComboBox) managerJPanel.getComponent(5)).setSelectedIndex(tempCar.getProductType().ordinal());
                    }
                    case VANS -> {
                        ((JTextField) managerJPanel.getComponent(1)).setText(tempVan.getName());
                        ((JSpinner) managerJPanel.getComponent(3)).setValue(tempVan.getCapacity());
                        ((JCheckBox) managerJPanel.getComponent(4)).setSelected(tempVan.getExtendableCapacity());
                    }
                    case PRODUCTS -> {
                        ((JTextField) managerJPanel.getComponent(1)).setText(String.valueOf(tempProduct.getSerialNumber()));
                        ((JSpinner) managerJPanel.getComponent(3)).setValue(convertToDateViaInstant(tempProduct.getUsableUntil()));
                        ((JComboBox) managerJPanel.getComponent(5)).setSelectedIndex(tempProduct.getProductType().ordinal());
                        ((JCheckBox) managerJPanel.getComponent(6)).setSelected(tempProduct.isMoreSamples());
                    }
                }
            }
        }
    }

    public void changeScreen(Screen screen) {
        CustomLog.write(CustomLog.LogLevel.DEFAULT, "Switched to screen "+screen.toString());

        previousScreen = currentScreen;
        currentScreen = screen;

        if (screen == Screen.DASHBOARD) {
            ((CardLayout) root.getLayout()).first(root);
            dashboardQuantityCenters.setText(String.valueOf(arrayCenters.size()));
            dashboardQuantityStores.setText(String.valueOf(arrayStores.size()));
            dashboardQuantityVans.setText(String.valueOf(arrayVans.size()));
            dashboardQuantityCars.setText(String.valueOf(arrayCars.size()));
            return;
        }

        ((CardLayout) root.getLayout()).first(root);
        ((CardLayout) root.getLayout()).next(root);
        nameJLabel.setText(screen.toString());
        switch (screen) {
            case CENTERS, STORES -> {
                showTransferButton.setVisible(true);
            }
            case CARS, VANS, PRODUCTS -> {
                showTransferButton.setVisible(false);
            }
        }
        switch (screen) {
            case CENTERS -> showTransferButton.setText("LOAD VEHICLE");
            case STORES -> showTransferButton.setText("OFFLOAD VEHICLE");
        }
        switch (screen) {
            case CENTERS -> {
                showProductsButton.setVisible(true);
            }
            case STORES, CARS, VANS, PRODUCTS -> {
                showProductsButton.setVisible(false);
            }
        }
        switch (screen) {
            case CENTERS -> {
                DefaultTableModel model = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                model.setColumnCount(5);
                model.setColumnIdentifiers(new String[]{"Name", "Email", "City", "State", "Capacity"});
                for (ProductsCenter obj : arrayCenters) {
                    model.addRow(new String[]{obj.getName(), obj.getEmail(), obj.getLocation().getCity(), obj.getLocation().getState(), String.valueOf(obj.getProducts().length)});
                }
                tableJTable.setModel(model);
            }
            case STORES -> {
                DefaultTableModel model = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                model.setColumnCount(4);
                model.setColumnIdentifiers(new String[]{"Name", "Phone", "City", "State"});
                for (Store obj : arrayStores) {
                    model.addRow(new String[]{obj.getName(), obj.getPhone(), obj.getLocation().getCity(), obj.getLocation().getState()});
                }
                tableJTable.setModel(model);
            }
            case CARS -> {
                DefaultTableModel model = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                model.setColumnCount(3);
                model.setColumnIdentifiers(new String[]{"Name", "Capacity", "Product type"});
                for (Car obj : arrayCars) {
                    model.addRow(new String[]{obj.getName(), String.valueOf(obj.getProducts().length), String.valueOf(obj.getProductType())});
                }
                tableJTable.setModel(model);
            }
            case VANS -> {
                DefaultTableModel model = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                model.setColumnCount(3);
                model.setColumnIdentifiers(new String[]{"Name", "Capacity", "Expandable"});
                for (Van obj : arrayVans) {
                    model.addRow(new String[]{obj.getName(), String.valueOf(obj.getCapacity()), String.valueOf(obj.getExtendableCapacity())});
                }
                tableJTable.setModel(model);
            }
            case PRODUCTS -> {
                DefaultTableModel model = new DefaultTableModel() {
                    @Override
                    public boolean isCellEditable(int row, int column) {
                        return false;
                    }
                };
                model.setColumnCount(4);
                model.setColumnIdentifiers(new String[]{"Serial number", "Usable until", "Product type", "More samples"});
                for (Product obj : arrayCenters.get(tempCentersIndex).getProducts()) {
                    if (obj != null)
                        model.addRow(new String[]{String.valueOf(obj.getSerialNumber()), String.valueOf(obj.getUsableUntil()), String.valueOf(obj.getProductType()), String.valueOf(obj.isMoreSamples())});
                }
                tableJTable.setModel(model);
            }
        }
        buttonChange.setEnabled(false);
        buttonDelete.setEnabled(false);
        showTransferButton.setEnabled(false);
        showProductsButton.setEnabled(false);
    }

    public GUI() {
        CustomLog.start();

        try {
            FileInputStream file;
            GZIPInputStream gzip;
            ObjectInputStream in;
            Object obj;

            if (new File(Screen.CENTERS.toString()+".gz").exists()) {
                file = new FileInputStream(Screen.CENTERS.toString() + ".gz");
                gzip = new GZIPInputStream(file);
                in = new ObjectInputStream(gzip);
                obj = in.readObject();
                if (obj != null)
                    arrayCenters = (ArrayList<ProductsCenter>) obj;
                in.close();
                file.close();
                CustomLog.write(CustomLog.LogLevel.DEFAULT, "Object loaded from file "+Screen.CENTERS.toString()+".gz");
            }

            if (new File(Screen.STORES.toString()+".gz").exists()) {
                file = new FileInputStream(Screen.STORES.toString() + ".gz");
                gzip = new GZIPInputStream(file);
                in = new ObjectInputStream(gzip);
                obj = in.readObject();
                if (obj != null)
                    arrayStores = (ArrayList<Store>) obj;
                in.close();
                file.close();
                CustomLog.write(CustomLog.LogLevel.DEFAULT, "Object loaded from file "+Screen.STORES.toString()+".gz");
            }

            if (new File(Screen.CARS.toString()+".gz").exists()) {
                file = new FileInputStream(Screen.CARS.toString() + ".gz");
                gzip = new GZIPInputStream(file);
                in = new ObjectInputStream(gzip);
                obj = in.readObject();
                if (obj != null)
                    arrayCars = (ArrayList<Car>) obj;
                in.close();
                file.close();
                CustomLog.write(CustomLog.LogLevel.DEFAULT, "Object loaded from file "+Screen.CARS.toString()+".gz");
            }

            if (new File(Screen.VANS.toString()+".gz").exists()) {
                file = new FileInputStream(Screen.VANS.toString() + ".gz");
                gzip = new GZIPInputStream(file);
                in = new ObjectInputStream(gzip);
                obj = in.readObject();
                if (obj != null)
                    arrayVans = (ArrayList<Van>) obj;
                in.close();
                file.close();
                CustomLog.write(CustomLog.LogLevel.DEFAULT, "Object loaded from file "+Screen.VANS.toString()+".gz");
            }
        } catch (IOException | ClassNotFoundException exception) {
            exception.printStackTrace();
            CustomLog.write(CustomLog.LogLevel.ERROR, exception.toString());
        }

        dashboardQuantityCenters.setText(String.valueOf(arrayCenters.size()));
        dashboardQuantityStores.setText(String.valueOf(arrayStores.size()));
        dashboardQuantityVans.setText(String.valueOf(arrayVans.size()));
        dashboardQuantityCars.setText(String.valueOf(arrayCars.size()));

        showCenters.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                changeScreen(Screen.CENTERS);
            }
        });
        showStores.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                changeScreen(Screen.STORES);
            }
        });
        showCars.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                changeScreen(Screen.CARS);
            }
        });
        showVans.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                changeScreen(Screen.VANS);
            }
        });

        showTransferButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                transferFunction();
            }
        });

        buttonTransferTo.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                if (currentScreen == Screen.CENTERS) {
                    if (tempTransferCar != null) {
                        try {
                            arrayCenters.get(tempCentersIndex).offloadProducts(arrayCars.get(tempTransferCarIndex));
                        } catch (CapacityOverloadException capacityOverloadException) {
                            CustomLog.write(CustomLog.LogLevel.ERROR, capacityOverloadException.toString());
                            showMessageDialog(null, "E1");
                        } catch (ProductTypeException productTypeException) {
                            CustomLog.write(CustomLog.LogLevel.ERROR, productTypeException.toString());
                            showMessageDialog(null, "E2");
                        }
                    } else {
                        try {
                            arrayCenters.get(tempCentersIndex).offloadProducts(arrayVans.get(tempTransferVanIndex));
                        } catch (CapacityOverloadException capacityOverloadException) {
                            CustomLog.write(CustomLog.LogLevel.ERROR, capacityOverloadException.toString());
                            showMessageDialog(null, "E3");
                        } catch (ProductTypeException productTypeException) {
                            CustomLog.write(CustomLog.LogLevel.ERROR, productTypeException.toString());
                            showMessageDialog(null, "E4");
                        }
                    }
                } else if (currentScreen == Screen.STORES) {
                    if (tempTransferCar != null) {
                        try {
                            arrayStores.get(tempStoresIndex).offloadProducts(arrayCars.get(tempTransferCarIndex));
                        } catch (CapacityOverloadException capacityOverloadException) {
                            CustomLog.write(CustomLog.LogLevel.ERROR, capacityOverloadException.toString());
                            showMessageDialog(null, "E5");
                        }
                    } else {
                        try {
                            arrayStores.get(tempStoresIndex).offloadProducts(arrayVans.get(tempTransferVanIndex));
                        } catch (CapacityOverloadException capacityOverloadException) {
                            CustomLog.write(CustomLog.LogLevel.ERROR, capacityOverloadException.toString());
                            showMessageDialog(null, "E6");
                        }
                    }
                }
                showMessageDialog(null, "Transfer complete!");
                changeScreen(currentScreen);
            }
        });

        showProductsButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                changeScreen(Screen.PRODUCTS);
            }
        });
        backJButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (currentScreen == Screen.PRODUCTS)
                    changeScreen(Screen.CENTERS);
                else
                    changeScreen(Screen.DASHBOARD);
            }
        });
        buttonCancel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeScreen(currentScreen);
            }
        });
        buttonCancel1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                changeScreen(currentScreen);
            }
        });
        buttonNew.addMouseListener(new ManageListener(ManageOption.NEW));
        buttonChange.addMouseListener(new ManageListener(ManageOption.CHANGE));
        buttonDelete.addMouseListener(new ManageListener(ManageOption.DELETE));
        tableJTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableJTable.getSelectedRow() > -1) {
                switch (currentScreen) {
                    case CENTERS -> {
                        tempCenter = new ProductsCenter(
                                tableJTable.getValueAt(tableJTable.getSelectedRow(), 0).toString(),
                                tableJTable.getValueAt(tableJTable.getSelectedRow(), 1).toString(),
                                new Location(tableJTable.getValueAt(tableJTable.getSelectedRow(), 2).toString(),
                                        tableJTable.getValueAt(tableJTable.getSelectedRow(), 3).toString()),
                                Integer.parseInt(tableJTable.getValueAt(tableJTable.getSelectedRow(), 4).toString())
                        );
                        tempCentersIndex = tableJTable.getSelectedRow();
                    }
                    case STORES -> {
                        tempStore = new Store(
                                tableJTable.getValueAt(tableJTable.getSelectedRow(), 0).toString(),
                                tableJTable.getValueAt(tableJTable.getSelectedRow(), 1).toString(),
                                new Location(tableJTable.getValueAt(tableJTable.getSelectedRow(), 2).toString(),
                                        tableJTable.getValueAt(tableJTable.getSelectedRow(), 3).toString())
                        );
                        tempStoresIndex = tableJTable.getSelectedRow();
                    }
                    case CARS -> {
                        ProductType tempTc;
                        switch (tableJTable.getValueAt(tableJTable.getSelectedRow(), 2).toString()) {
                            case "TECHNOLOGY" -> tempTc = ProductType.TECHNOLOGY;
                            case "CLOTHES" -> tempTc = ProductType.CLOTHES;
                            case "FOOD" -> tempTc = ProductType.FOOD;
                            default -> {
                                IllegalStateException exception = new IllegalStateException("Unexpected value: " + tableJTable.getValueAt(tableJTable.getSelectedRow(), 2).toString());
                                CustomLog.write(CustomLog.LogLevel.CRITICAL, exception.toString());
                                throw exception;
                            }
                        }
                        tempCar = new Car(
                                tableJTable.getValueAt(tableJTable.getSelectedRow(), 0).toString(),
                                Integer.parseInt(tableJTable.getValueAt(tableJTable.getSelectedRow(), 1).toString()),
                                tempTc
                        );
                        tempCarsIndex = tableJTable.getSelectedRow();
                    }
                    case VANS -> {
                        tempVan = new Van(
                                tableJTable.getValueAt(tableJTable.getSelectedRow(), 0).toString(),
                                Integer.parseInt(tableJTable.getValueAt(tableJTable.getSelectedRow(), 1).toString()),
                                Boolean.parseBoolean(tableJTable.getValueAt(tableJTable.getSelectedRow(), 2).toString())
                        );
                        tempVansIndex = tableJTable.getSelectedRow();
                    }
                    case PRODUCTS -> {
                        ProductType tempTc;
                        switch (tableJTable.getValueAt(tableJTable.getSelectedRow(), 2).toString()) {
                            case "TECHNOLOGY" -> tempTc = ProductType.TECHNOLOGY;
                            case "CLOTHES" -> tempTc = ProductType.CLOTHES;
                            case "FOOD" -> tempTc = ProductType.FOOD;
                            default -> {
                                IllegalStateException exception = new IllegalStateException("Unexpected value: " + tableJTable.getValueAt(tableJTable.getSelectedRow(), 2).toString());
                                CustomLog.write(CustomLog.LogLevel.CRITICAL, exception.toString());
                                throw exception;
                            }
                        }
                        tempProduct = new Product(
                                Integer.parseInt(tableJTable.getValueAt(tableJTable.getSelectedRow(), 0).toString()),
                                LocalDate.parse(tableJTable.getValueAt(tableJTable.getSelectedRow(), 1).toString()),
                                tempTc,
                                Boolean.parseBoolean(tableJTable.getValueAt(tableJTable.getSelectedRow(), 3).toString())
                        );
                        tempProductIndex = tableJTable.getSelectedRow();
                    }
                }
                buttonChange.setEnabled(true);
                buttonDelete.setEnabled(true);
                showTransferButton.setEnabled(true);
                showProductsButton.setEnabled(true);
            }
        });

        transferFrom.getSelectionModel().addListSelectionListener(e -> {
            if (currentScreen == Screen.STORES)
                if (!e.getValueIsAdjusting() && transferFrom.getSelectedRow() > -1) {
                    buttonTransferTo.setEnabled(true);
                    if ((transferFrom.getValueAt(transferFrom.getSelectedRow(), 2).toString().equals("true")) || (transferFrom.getValueAt(transferFrom.getSelectedRow(), 2).toString().equals("false"))) {
                        tempTransferVan = new Van(
                                transferFrom.getValueAt(transferFrom.getSelectedRow(), 0).toString(),
                                Integer.parseInt(transferFrom.getValueAt(transferFrom.getSelectedRow(), 1).toString()),
                                Boolean.parseBoolean(transferFrom.getValueAt(transferFrom.getSelectedRow(), 2).toString())
                        );
                        tempTransferVanIndex = transferFrom.getSelectedRow();
                        tempTransferCar = null;
                    } else {
                        ProductType tempTc;
                        switch (transferFrom.getValueAt(transferFrom.getSelectedRow(), 2).toString()) {
                            case "TECHNOLOGY" -> tempTc = ProductType.TECHNOLOGY;
                            case "CLOTHES" -> tempTc = ProductType.CLOTHES;
                            case "FOOD" -> tempTc = ProductType.FOOD;
                            default -> {
                                IllegalStateException exception = new IllegalStateException("Unexpected value: " + tableJTable.getValueAt(tableJTable.getSelectedRow(), 2).toString());
                                CustomLog.write(CustomLog.LogLevel.CRITICAL, exception.toString());
                                throw exception;
                            }
                        }
                        tempTransferCar = new Car(
                                transferFrom.getValueAt(transferFrom.getSelectedRow(), 0).toString(),
                                Integer.parseInt(transferFrom.getValueAt(transferFrom.getSelectedRow(), 1).toString()),
                                tempTc
                        );
                        tempTransferCarIndex = transferFrom.getSelectedRow();
                        tempTransferVan = null;
                    }
                }
        });

        transferTo.getSelectionModel().addListSelectionListener(e -> {
            if (currentScreen == Screen.CENTERS)
                if (!e.getValueIsAdjusting() && transferTo.getSelectedRow() > -1) {
                    buttonTransferTo.setEnabled(true);
                    if ((transferTo.getValueAt(transferTo.getSelectedRow(), 2).toString().equals("true")) || (transferTo.getValueAt(transferTo.getSelectedRow(), 2).toString().equals("false"))) {
                        tempTransferVan = new Van(
                                transferTo.getValueAt(transferTo.getSelectedRow(), 0).toString(),
                                Integer.parseInt(transferTo.getValueAt(transferTo.getSelectedRow(), 1).toString()),
                                Boolean.parseBoolean(transferTo.getValueAt(transferTo.getSelectedRow(), 2).toString())
                        );
                        tempTransferVanIndex = transferTo.getSelectedRow();
                        tempTransferCar = null;
                    } else {
                        ProductType tempTc;
                        switch (transferTo.getValueAt(transferTo.getSelectedRow(), 2).toString()) {
                            case "TECHNOLOGY" -> tempTc = ProductType.TECHNOLOGY;
                            case "CLOTHES" -> tempTc = ProductType.CLOTHES;
                            case "FOOD" -> tempTc = ProductType.FOOD;
                            default -> {
                                IllegalStateException exception = new IllegalStateException("Unexpected value: " + tableJTable.getValueAt(tableJTable.getSelectedRow(), 2).toString());
                                CustomLog.write(CustomLog.LogLevel.CRITICAL, exception.toString());
                                throw exception;
                            }
                        }
                        tempTransferCar = new Car(
                                transferTo.getValueAt(transferTo.getSelectedRow(), 0).toString(),
                                Integer.parseInt(transferTo.getValueAt(transferTo.getSelectedRow(), 1).toString()),
                                tempTc
                        );
                        tempTransferCarIndex = transferTo.getSelectedRow();
                        tempTransferVan = null;
                    }
                }
        });
    }

    private void transferFunction() {
        ((CardLayout) root.getLayout()).first(root);
        ((CardLayout) root.getLayout()).next(root);
        ((CardLayout) root.getLayout()).next(root);
        buttonTransferTo.setEnabled(false);
        DefaultTableModel model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        model.setColumnCount(3);
        model.setColumnIdentifiers(new String[]{"Name", "Capacity", "Product type / Extendable"});
        for (Car obj : arrayCars) {
            model.addRow(new String[]{obj.getName(), String.valueOf(obj.getProducts().length), String.valueOf(obj.getProductType())});
        }
        for (Van obj : arrayVans) {
            model.addRow(new String[]{obj.getName(), String.valueOf(obj.getCapacity()), String.valueOf(obj.getExtendableCapacity())});
        }
        if (currentScreen == Screen.CENTERS) {
            transferTo.setModel(model);
            model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.setColumnCount(5);
            model.setColumnIdentifiers(new String[]{"Name", "Email", "City", "State", "Capacity"});
            model.addRow(new String[]{tempCenter.getName(), tempCenter.getEmail(), tempCenter.getLocation().getCity(), tempCenter.getLocation().getState(), String.valueOf(tempCenter.getProducts().length)});
            transferFrom.setModel(model);
        } else if (currentScreen == Screen.STORES) {
            transferFrom.setModel(model);
            model = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            model.setColumnCount(4);
            model.setColumnIdentifiers(new String[]{"Name", "Phone", "City", "State"});
            model.addRow(new String[]{tempStore.getName(), tempStore.getPhone(), tempStore.getLocation().getCity(), tempStore.getLocation().getState()});
            transferTo.setModel(model);
        }
    }

    public static void main(String[] args) {
        try {
            System.setProperty("awt.useSystemAAFontSettings", "on");
            System.setProperty("swing.aatext", "true");
        } catch (Exception e) {
            e.printStackTrace();
            CustomLog.write(CustomLog.LogLevel.ERROR, e.toString());
        }
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
            CustomLog.write(CustomLog.LogLevel.ERROR, e.toString());
        }
        JFrame frame = new JFrame("Management");
        frame.setContentPane(new GUI().root);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.pack();
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                CustomLog.finish();
                super.windowClosing(e);
            }
        });
    }
}
