package com.example.inventorysys2.Model;

/**
 * This will model an outsourced part.
 *
 * @author Sravya Maddipatla
 *
 */
public class Outsourced extends Part {


    /**
     * Company name for the respective part
     */
    String companyName;

    /**
     * Serves as a constructor for an Outsourced object.
     *
     * @param id the ID of the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory level of the part
     * @param min the minimum level of the part
     * @param max the maximum level of the part
     * @param companyName the company name of the part
     */

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        super(id, name, price, stock, min, max);
        setCompanyName(companyName);
    }

    /**
     * This will set the companyName value
     *
     * @param companyName is the company name of the part
     */
    public void setCompanyName(String companyName){
        this.companyName = companyName;
    }

    /**
     * This will retrieve the companyName value
     * @return companyName of the part
     */
    public String getCompanyName(){
        return companyName;
    }

}
