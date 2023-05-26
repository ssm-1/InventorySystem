package com.example.inventorysys2.Model;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Serves as an inventory for the Parts and Products.
 This class provides the data needed to run the application
 @author Sravya Maddipatla
  */
public class Inventory {


    /**
     * Lists all parts in inventory.
     */
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    /**
     * Lists all products in inventory.
     */
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Initializes the partID variable that will be used to generate IDs.
     */

    private static int partID = 3;

    /** Will add a Part to the Inventory
     @param newPart the passed Part 
     */
   public  static  void addPart(Part newPart) {

       allParts.add(newPart);
   }

    /** Will add a Product to the Inventory
     @param newProduct the passed Product
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }
    /**
     * This will remove a product from the parts list.
     *
     * @param SP is the product that will be removed.
     * @return This will indicate the status of removing the product.
     */

    public static boolean deleteProduct(Product SP) {
        if (allProducts.contains(SP)) {
            allProducts.remove(SP);
            return true;
        }
        else {
            return false;
        }
    }

    /** This will call the function that adds test data.
     */

    static {
       addTestData();
    }

    /** Adds test data to the inventory for both the Part and Product tables.
     */
    public static void addTestData() {

        Part a = new Outsourced(1, "Brakes", 15.00, 10, 1, 1000, "Brakes R Us");
        Inventory.addPart(a);
        Part c = new InHouse(2, "Wheel", 11.00, 16, 1, 1000, 32);
        Inventory.addPart(c);
        Part d = new InHouse(3, "Seat", 15.00, 10, 1, 1000, 65);
        Inventory.addPart(d);

        Product b = new Product(1000, "Giant Bike", 299.99, 5, 0, 1000);
        Inventory.addProduct(b);
        Product e = new Product(1001, "Tricycle", 99.99, 3, 0, 1000);
        Inventory.addProduct(e);
    }



    /** This will search the list of parts by a string.
     * @param partName is the searched String passed in.
     * @return the list of parts found
     */
    public static ObservableList<Part> lookupPart(String partName) {

       ObservableList<Part> namedParts = FXCollections.observableArrayList();

       ObservableList<Part> allParts = Inventory.getAllParts();

       for(Part bp : allParts) {
           if (bp.getName().contains(partName)) {
               namedParts.add(bp);

           }
       }
        return namedParts;
    }

    /** This will search the list of parts by an integer.
     * @param partId is the searched integer passed in.
     * @return the list of parts found
     */

    public static Part lookupPart(int partId) {
        ObservableList<Part> allParts = Inventory.getAllParts();

        for (int i = 0; i < allParts.size(); i++) {
            Part Ac = allParts.get(i);

            if(Ac.getId() == partId) {
                return Ac;
            }
        }
        return null;
    }
    /** This will search the list of products by an integer.
     * @param productId is the searched integer passed in.
     * @return the list of products found
     */

    public static Product lookupProduct(int productId) {
        ObservableList<Product> allProducts = Inventory.getAllProducts();

        for (int i = 0; i < allProducts.size(); i++) {
            Product Ad = allProducts.get(i);

            if(Ad.getId() == productId) {
                return Ad;

            }
        }
        return null;
    }
    /** This will search the list of products by a String.
     * @param productName is the searched String passed in.
     * @return the list of products found
     */


    public static ObservableList<Product> lookupProduct(String productName) {
       ObservableList<Product> namedProducts = FXCollections.observableArrayList();

        for(Product bb : allProducts) {
            if (bb.getName().contains(productName)) {
                namedProducts.add(bb);

            }
        }

       return namedProducts;
    }

    /**
     * This will update a part in the parts list.
     *
     * @param index the index of the part replaced.
     * @param selectedPart the part used to replace.
     */

    public static void updatePart(int index, Part selectedPart) {
        int i = -1;
        for (Part p : getAllParts()) {
            i++;
            if (p.getId() == index) {
                getAllParts().set(i, selectedPart);
                return;
            }
        }
    }


    /**
     * This will update a product in the products list.
     *
     * @param index the index of the part replaced.
     * @param newProduct the part used to replace.
     */

    public static void updateProduct(int index, Product newProduct) {
//        int i = -1;
//        for (Product p : getAllProducts()) {
//            i++;
//            if (p.getId() == index) {
//                getAllProducts().set(i, newProduct);
//                return;
//            }
//        }
        allProducts.set(index, newProduct);
    }


    /**
     * This removes a part from the part list.
     *
     * @param selectedPart the part being removed.
     * @return This will indicate the status of removing it.
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * This will retrieve a list of all parts in the inventory.
     * @return The list of parts found.
     */

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * This will retrieve a list of all products in the inventory.
     * @return The list of products found.
     */


    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }



    /** This will generate unique and contiguous Part IDs
     * @return the unique partID generated
     */

    public static int contiguousPartID() {

       partID += 1;

       return partID;
    }


}




