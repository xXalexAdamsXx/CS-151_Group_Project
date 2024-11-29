package main.services;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Service class to manage inventory operations such as loading inventory from a JSON file,
 * validating product codes, searching products, and retrieving inventory data.
 */
public class InventoryService {

    private static List<Product> inventory = new ArrayList<>();

    /**
     * Loads inventory data from the specified JSON file.
     *
     * @param filePath The path to the JSON file containing inventory data.
     * @throws Exception If the file cannot be read, parsed, or contains invalid data.
     */
    public static void loadInventory(String filePath) throws Exception {
        File file = new File(filePath);
        if (!file.exists() || !file.isFile()) {
            throw new Exception("Error: Inventory file not found at " + filePath);
        }

        JSONParser parser = new JSONParser();
        try (FileReader reader = new FileReader(file)) {
            JSONObject data = (JSONObject) parser.parse(reader);

            // Validate structure of JSON
            if (!data.containsKey("product_info")) {
                throw new Exception("Error: JSON file is missing 'product_info' key.");
            }

            JSONArray products = (JSONArray) data.get("product_info");

            inventory.clear(); // Clear existing inventory before loading new data
            for (Object obj : products) {
                JSONObject productJson = (JSONObject) obj;

                // Validate required fields in each product entry
                if (!productJson.containsKey("code") || !productJson.containsKey("name") ||
                        !productJson.containsKey("price") || !productJson.containsKey("desc")) {
                    throw new Exception("Error: One or more products are missing required fields (code, name, price, desc).");
                }

                String code = (String) productJson.get("code");
                String name = (String) productJson.get("name");
                double price = Double.parseDouble(productJson.get("price").toString());
                String description = (String) productJson.get("desc");
                inventory.add(new Product(code, name, price, description));
            }
        } catch (Exception e) {
            throw new Exception("Error parsing inventory file: " + e.getMessage(), e);
        }
    }

    /**
     * Validates if a given product code exists in the inventory.
     *
     * @param code The product code to validate.
     * @return true if the product code exists; false otherwise.
     */
    public static boolean validateProductCode(String code) {
        return inventory.stream().anyMatch(product -> product.getCode().equalsIgnoreCase(code));
    }

    /**
     * Searches the inventory for products matching the provided code prefix.
     *
     * @param codePrefix The code prefix to search for. Supports '*' wildcard for partial matching.
     * @return A list of matching products.
     */
    public static List<Product> searchProducts(String codePrefix) {
        if (codePrefix.endsWith("*")) {
            String prefix = codePrefix.substring(0, codePrefix.length() - 1);
            return inventory.stream()
                    .filter(product -> product.getCode().startsWith(prefix))
                    .collect(Collectors.toList());
        } else {
            return inventory.stream()
                    .filter(product -> product.getCode().equalsIgnoreCase(codePrefix))
                    .collect(Collectors.toList());
        }
    }

    /**
     * Retrieves the inventory data in a format suitable for display in a JTable.
     *
     * @return A 2D Object array representing the inventory data.
     */
    public static Object[][] getInventoryTableData() {
        Object[][] data = new Object[inventory.size()][4];
        for (int i = 0; i < inventory.size(); i++) {
            Product product = inventory.get(i);
            data[i][0] = product.getCode();
            data[i][1] = product.getName();
            data[i][2] = product.getPrice();
            data[i][3] = product.getDescription();
        }
        return data;
    }

    /**
     * Retrieves the list of all products in the inventory.
     *
     * @return The list of products.
     */
    public static List<Product> getInventory() {
        return inventory;
    }

    /**
     * Represents a product in the inventory.
     */
    public static class Product {
        private String code;
        private String name;
        private double price;
        private String description;

        /**
         * Constructs a Product object with the specified details.
         *
         * @param code        The product code.
         * @param name        The product name.
         * @param price       The price of the product.
         * @param description A brief description of the product.
         */
        public Product(String code, String name, double price, String description) {
            this.code = code;
            this.name = name;
            this.price = price;
            this.description = description;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public double getPrice() {
            return price;
        }

        public String getDescription() {
            return description;
        }

        @Override
        public String toString() {
            return String.format("%s: %s ($%.2f) - %s", code, name, price, description);
        }
    }
}


