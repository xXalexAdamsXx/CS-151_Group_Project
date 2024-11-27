package models;

/**
 * Represents a product in the inventory.
 */
public class Product {
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
        return code + ": " + name + " ($" + price + ") - " + description;
    }
}
