package main.services;

import java.util.ArrayList;
import java.util.List;
import main.utils.DateTimeUtils;

/**
 * Service class to manage invoice operations, including item addition/removal,
 * subtotal/tax/discount calculations, and receipt generation.
 */
public class InvoiceService {

    private static final List<InvoiceItem> invoice = new ArrayList<>();

    /**
     * Adds an item to the invoice.
     *
     * @param code     The product code.
     * @param quantity The quantity of the product.
     */
    public static void addItemToInvoice(String code, int quantity) {
        for (InventoryService.Product product : InventoryService.getInventory()) {
            if (product.getCode().equals(code)) {
                invoice.add(new InvoiceItem(product, quantity));
                return;
            }
        }
    }

    /**
     * Removes an item from the invoice by index.
     *
     * @param index The index of the item to remove.
     */
    public static void removeItemFromInvoice(int index) {
        if (index >= 0 && index < invoice.size()) {
            invoice.remove(index);
        }
    }

    /**
     * Calculates the subtotal of the invoice.
     *
     * @return The subtotal amount.
     */
    public static double calculateSubtotal() {
        return invoice.stream()
                .mapToDouble(item -> item.getProduct().getPrice() * item.getQuantity())
                .sum();
    }

    /**
     * Calculates the total amount after applying tax and discount.
     *
     * @param subtotal The subtotal amount.
     * @param tax      The tax percentage.
     * @param discount The discount percentage.
     * @return The total amount.
     */
    public static double calculateTotal(double subtotal, double tax, double discount) {
        double taxAmount = (tax / 100) * subtotal;
        double discountAmount = (discount / 100) * subtotal;
        return subtotal + taxAmount - discountAmount;
    }

    /**
     * Retrieves the invoice data in a format suitable for display in a JTable.
     *
     * @return A 2D Object array representing the invoice data.
     */
    public static Object[][] getInvoiceTableData() {
        Object[][] data = new Object[invoice.size()][4];
        for (int i = 0; i < invoice.size(); i++) {
            InvoiceItem item = invoice.get(i);
            data[i][0] = item.getProduct().getCode();
            data[i][1] = item.getProduct().getName();
            data[i][2] = item.getQuantity();
            data[i][3] = item.getProduct().getPrice() * item.getQuantity();
        }
        return data;
    }

    /**
     * Generates a formatted receipt string.
     *
     * @return The receipt string.
     */
    public static String generateReceipt() {
        StringBuilder receipt = new StringBuilder();
        receipt.append("Store Name - City, State\n")
                .append("Phone: 555-1234\n")
                .append("Date/Time: ").append(DateTimeUtils.getCurrentTimestamp()).append("\n\n")
                .append("Products:\n");

        for (InvoiceItem item : invoice) {
            receipt.append(item.getProduct().getName()).append(" (")
                    .append(item.getProduct().getCode()).append(") - Qty: ")
                    .append(item.getQuantity()).append(" - $")
                    .append(String.format("%.2f", item.getProduct().getPrice() * item.getQuantity())).append("\n");
        }

        double subtotal = calculateSubtotal();
        double tax = 8.25; // Example tax rate
        double discount = 5.0; // Example discount rate
        double total = calculateTotal(subtotal, tax, discount);

        receipt.append("\nSubtotal: $").append(String.format("%.2f", subtotal))
                .append("\nTax: $").append(String.format("%.2f", (tax / 100) * subtotal))
                .append("\nDiscount: $").append(String.format("%.2f", (discount / 100) * subtotal))
                .append("\nGrand Total: $").append(String.format("%.2f", total))
                .append("\n\nYour cashier serving you today is User.\nThank you for shopping!");

        return receipt.toString();
    }

    /**
     * Represents an item in the invoice.
     */
    static class InvoiceItem {
        private final InventoryService.Product product;
        private final int quantity;

        /**
         * Constructs an InvoiceItem with the given product and quantity.
         *
         * @param product  The product associated with the item.
         * @param quantity The quantity of the product.
         */
        public InvoiceItem(InventoryService.Product product, int quantity) {
            this.product = product;
            this.quantity = quantity;
        }

        public InventoryService.Product getProduct() {
            return product;
        }

        public int getQuantity() {
            return quantity;
        }
    }
}
