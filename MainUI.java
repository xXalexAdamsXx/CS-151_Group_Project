package src.ui;

import src.services.InventoryService;
import src.services.InvoiceService;
import src.utils.DateTimeUtils;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

/**
 * Main user interface for the cashier application.
 */
public class MainUI extends JFrame {

    public MainUI(String cashierName) {
        setTitle("Cashier System - Main UI");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Welcome message
        JLabel welcomeLabel = new JLabel("Welcome, " + cashierName, SwingConstants.CENTER);
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(welcomeLabel, BorderLayout.NORTH);

        // Tabbed Pane for panels
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.addTab("Shift Management", createShiftPanel());
        tabbedPane.addTab("Inventory Management", createInventoryPanel());
        tabbedPane.addTab("Invoice Management", createInvoicePanel());
        tabbedPane.addTab("Tax and Discount", createTaxDiscountPanel());
        tabbedPane.addTab("Receipt", createReceiptPanel());
        add(tabbedPane, BorderLayout.CENTER);

        setVisible(true);
    }

    /**
     * Creates the Shift Management panel.
     * @return A JPanel for managing shifts.
     */
    private JPanel createShiftPanel() {
        JPanel shiftPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        JTextField firstNameField = new JTextField();
        JTextField lastNameField = new JTextField();
        JTextField startTimeField = new JTextField();
        startTimeField.setEditable(false);
        JTextField endTimeField = new JTextField();
        endTimeField.setEditable(false);

        JButton startShiftButton = new JButton("Start Shift");
        startShiftButton.addActionListener(e -> {
            if (!firstNameField.getText().isEmpty() && !lastNameField.getText().isEmpty()) {
                startTimeField.setText(DateTimeUtils.getCurrentTimestamp());
            } else {
                JOptionPane.showMessageDialog(this, "Please enter First and Last name.");
            }
        });

        JButton endShiftButton = new JButton("End Shift");
        endShiftButton.addActionListener(e -> {
            endTimeField.setText(DateTimeUtils.getCurrentTimestamp());
            startTimeField.setText("");
            JOptionPane.showMessageDialog(this, "Shift ended. All data cleared.");
        });

        shiftPanel.add(new JLabel("First Name:"));
        shiftPanel.add(firstNameField);
        shiftPanel.add(new JLabel("Last Name:"));
        shiftPanel.add(lastNameField);
        shiftPanel.add(startShiftButton);
        shiftPanel.add(startTimeField);
        shiftPanel.add(endShiftButton);
        shiftPanel.add(endTimeField);
        return shiftPanel;
    }

    /**
     * Creates the Inventory Management panel.
     * @return A JPanel for managing inventory.
     */
    private JPanel createInventoryPanel() {
        JPanel inventoryPanel = new JPanel(new BorderLayout());
        JButton loadButton = new JButton("Load Inventory");
        JButton showButton = new JButton("Show Inventory");

        loadButton.addActionListener(e -> {
            try {
                InventoryService.loadInventory("resources/StoreInfo.json");
                JOptionPane.showMessageDialog(this, "Inventory loaded successfully.");
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Error loading inventory: " + ex.getMessage());
            }
        });

        showButton.addActionListener(e -> {
            JFrame inventoryFrame = new JFrame("Inventory");
            inventoryFrame.setSize(600, 400);
            JTable inventoryTable = new JTable(InventoryService.getInventoryTableData(),
                    new String[]{"Code", "Name", "Price", "Description"});
            inventoryFrame.add(new JScrollPane(inventoryTable));
            inventoryFrame.setVisible(true);
        });

        inventoryPanel.add(loadButton, BorderLayout.NORTH);
        inventoryPanel.add(showButton, BorderLayout.SOUTH);
        return inventoryPanel;
    }

    /**
     * Creates the Invoice Management panel.
     * @return A JPanel for managing invoices.
     */
    private JPanel createInvoicePanel() {
        JPanel invoicePanel = new JPanel(new BorderLayout());
        JTable invoiceTable = new JTable();
        JTextField productCodeField = new JTextField();
        JTextField quantityField = new JTextField();

        JButton addItemButton = new JButton("Add Item");
        JButton removeItemButton = new JButton("Remove Item");

        addItemButton.addActionListener(e -> {
            String code = productCodeField.getText();
            try {
                int quantity = Integer.parseInt(quantityField.getText());
                if (InventoryService.validateProductCode(code)) {
                    InvoiceService.addItemToInvoice(code, quantity);
                    invoiceTable.setModel(new DefaultTableModel(
                            InvoiceService.getInvoiceTableData(),
                            new String[]{"Code", "Name", "Quantity", "Price"}));
                    updateReceipt();
                } else {
                    JOptionPane.showMessageDialog(this, "The product code '" + code + "' does not exist.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Quantity. Please enter a positive number.");
            }
        });

        removeItemButton.addActionListener(e -> {
            int selectedRow = invoiceTable.getSelectedRow();
            if (selectedRow >= 0) {
                InvoiceService.removeItemFromInvoice(selectedRow);
                invoiceTable.setModel(new DefaultTableModel(
                        InvoiceService.getInvoiceTableData(),
                        new String[]{"Code", "Name", "Quantity", "Price"}));
                updateReceipt();
            } else {
                JOptionPane.showMessageDialog(this, "Please select a row to remove.");
            }
        });

        JPanel controlsPanel = new JPanel(new GridLayout(1, 4));
        controlsPanel.add(new JLabel("Code:"));
        controlsPanel.add(productCodeField);
        controlsPanel.add(new JLabel("Quantity:"));
        controlsPanel.add(quantityField);

        JPanel buttonsPanel = new JPanel();
        buttonsPanel.add(addItemButton);
        buttonsPanel.add(removeItemButton);

        invoicePanel.add(controlsPanel, BorderLayout.NORTH);
        invoicePanel.add(new JScrollPane(invoiceTable), BorderLayout.CENTER);
        invoicePanel.add(buttonsPanel, BorderLayout.SOUTH);
        return invoicePanel;
    }

    /**
     * Creates the Tax and Discount panel.
     * @return A JPanel for managing taxes and discounts.
     */
    private JPanel createTaxDiscountPanel() {
        JPanel taxDiscountPanel = new JPanel(new GridLayout(4, 2));
        JTextField taxField = new JTextField("8.25");
        JTextField discountField = new JTextField("0.00");
        JCheckBox discountCheckBox = new JCheckBox("Apply Discount");
        JTextField subtotalField = new JTextField();
        subtotalField.setEditable(false);
        JTextField totalField = new JTextField();
        totalField.setEditable(false);

        discountCheckBox.addActionListener(e -> {
            try {
                double tax = Double.parseDouble(taxField.getText());
                double discount = discountCheckBox.isSelected() ? Double.parseDouble(discountField.getText()) : 0.0;
                if (tax < 0 || discount < 0) throw new NumberFormatException();

                double subtotal = InvoiceService.calculateSubtotal();
                double total = InvoiceService.calculateTotal(subtotal, tax, discount);
                subtotalField.setText(String.format("%.2f", subtotal));
                totalField.setText(String.format("%.2f", total));
                updateReceipt();
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid tax or discount percentage. Please enter positive numbers.");
            }
        });

        taxDiscountPanel.add(new JLabel("Tax (%):"));
        taxDiscountPanel.add(taxField);
        taxDiscountPanel.add(new JLabel("Discount (%):"));
        taxDiscountPanel.add(discountField);
        taxDiscountPanel.add(discountCheckBox);
        taxDiscountPanel.add(new JLabel("Subtotal:"));
        taxDiscountPanel.add(subtotalField);
        taxDiscountPanel.add(new JLabel("Total:"));
        taxDiscountPanel.add(totalField);
        return taxDiscountPanel;
    }

    /**
     * Creates the Receipt panel.
     * @return A JPanel for displaying receipts.
     */
    private JPanel createReceiptPanel() {
        JPanel receiptPanel = new JPanel(new BorderLayout());
        JTextArea receiptArea = new JTextArea();
        JButton printReceiptButton = new JButton("Print Receipt");

        printReceiptButton.addActionListener(e -> receiptArea.setText(InvoiceService.generateReceipt()));

        receiptPanel.add(new JScrollPane(receiptArea), BorderLayout.CENTER);
        receiptPanel.add(printReceiptButton, BorderLayout.SOUTH);
        return receiptPanel;
    }

    /**
     * Updates the receipt dynamically.
     */
    private void updateReceipt() {
        // Logic to refresh receipt dynamically
    }
}
