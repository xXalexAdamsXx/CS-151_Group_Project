import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
public class ManageInventory {
    String file;
    public ManageInventory(String file) {
        this.file = file;
    }
    /**
     * Gets the Store Information
     * @return Array with all Store Information.
     */
    public ArrayList<String> getStoreInfo() {
        ArrayList<String> storeInfo = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while (!line.contains("\"store_info\":")) {
                line = reader.readLine();
            }
            line = reader.readLine();
            storeInfo.add(line.substring(17,line.length()-2));
            line = reader.readLine();
            storeInfo.add(line.substring(19,line.length()-2));
            line = reader.readLine();
            storeInfo.add(line.substring(11,line.length()-2));
            line = reader.readLine();
            storeInfo.add(line.substring(12,line.length()-2));
            line = reader.readLine();
            storeInfo.add(line.substring(25,line.length()));
            reader.close();
            return storeInfo;

        } catch (IOException e) {
            storeInfo.add("File Not Found!");
            return storeInfo;
        }
    }
    /**
     * Gets a specific product from the Inventory
     * @param productCode Product to get
     * @return Information about product
     */
    public ArrayList<String> getProduct(String productCode) {
        ArrayList<String> productInfo = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.contains("\"product_code\": \"" + productCode + "\",")) {
                    productInfo.add(productCode);
                    line = reader.readLine();
                    productInfo.add(line.substring(20,line.length()-2));
                    line = reader.readLine();
                    productInfo.add(line.substring(12,line.length()-1));
                    line = reader.readLine();
                    productInfo.add(line.substring(19,line.length()-1));
                    break;
                }
            }
            if (productInfo.size() < 1) {productInfo.add("Product Code Not Found!");}
            reader.close();
            return productInfo;

        } catch (IOException e) {
            productInfo.add("File Not Found!");
            return productInfo;
        }

    }
    /**
     * Gets a list of all products and descriptions
     * @return Array of all products
     */
    public ArrayList<ArrayList<String>> getAllProducts() {
        ArrayList<ArrayList<String>> productInfo = new ArrayList<ArrayList<String>>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line = "";
            while (!line.contains("\"product_info\":")) {
                line = reader.readLine();
            }
            line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                ArrayList<String> product = new ArrayList<String>();
                product.add(line.substring(20,line.length()-2));
                line = reader.readLine();
                product.add(line.substring(20,line.length()-2));
                line = reader.readLine();
                product.add(line.substring(12,line.length()-1));
                line = reader.readLine();
                product.add(line.substring(19,line.length()-1));
                productInfo.add(product);
                line = reader.readLine();
                line = reader.readLine();
                if (line.contains("]")) {break;}   
            }
            reader.close();
            return productInfo;
        } catch (IOException e) {
            ArrayList<String> a = new ArrayList<String>();
            a.add("File Not Found!");
            productInfo.add(a);
            return productInfo;
        }

    }
}
