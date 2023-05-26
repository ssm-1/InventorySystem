package com.example.inventorysys2.Model;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/** Serves as a product that can have associated parts.
 @author Sravya Maddipatla
 */
public class Product {

    /** A list of associated Parts for the product. */
    public ObservableList<Part> associatedParts = FXCollections.observableArrayList();
    /** ID of the product */
    private int id;
    /** name of the product */
    private String name;

    /** price of the product */

    private double price;

    /** stock level of the product */

    private int stock;

    /** minimum value of the product */

    private int min;

    /** maximum value of the product */

    private int max;

    /**
     * Serves as a constructor for a Product.
     *
     * @param id the ID of the product
     * @param name the name of the product
     * @param price the price of the product
     * @param stock the inventory level of the product
     * @param min the minimum level of the product
     * @param max the maximum level of the product
     */

    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /** getter for the Id value
     * @return the id
     */


    public int getId() {
        return id;
    }

    /** sets the ID value
     * @param id the id value of the product
     */

    public void setId(int id) {
        this.id = id;
    }

    /** getter for the name of the product
     * @return the name
     */

    public String getName() {
        return name;
    }

    /** sets the name
     * @param name the name of the product
     */

    public void setName(String name) {
        this.name = name;
    }


    /** getter for the price of the product
     * @return the price
     */
    public double getPrice() {
        return price;
    }
    /** sets the price
     * @param price the price of the product
     */

    public void setPrice(double price) {
        this.price = price;
    }

    /** getter for the stock of the product
     * @return the stock
     */
    public int getStock() {
        return stock;
    }
    /** sets the stock value
     * @param stock the stock of the product
     */

    public void setStock(int stock) {
        this.stock = stock;
    }

    /** getter for the minimum value of the product
     * @return the minimum value
     */

    public int getMin() {
        return min;
    }

    /** sets the minimum value
     * @param min the minimum value of the product
     */

    public void setMin(int min) {
        this.min = min;
    }


    /** getter for the maximum value of the product
     * @return the maximum value
     */

    public int getMax() {
        return max;
    }

    /** sets the maximum value
     * @param max the maximum of the product
     */


    public void setMax(int max) {
        this.max = max;
    }


    /**
     * Deletes the selected associated part.
     *
     * @param selectedAssociatedPart the part to be deleted
     * @return this will indicate the status of the deletion
     */

    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {

            associatedParts.remove(selectedAssociatedPart);


            return false;
    }

    /**
     * This will get a list of all associated parts.
     *
     * @return the list of parts retrieved
     */

    public ObservableList<Part> getAllAssociatedParts() {
        return associatedParts;
    }

    /**This will add the new part to the list
     * @param part the part to be added */

    public void addAssociatedPart(Part part) {

        associatedParts.add(part);
    }



}
