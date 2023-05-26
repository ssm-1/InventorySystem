package com.example.inventorysys2.Model;


/**
 * This will model an in-house part.
 *
 * @author Sravya Maddipatla
 *
 */
public class InHouse extends Part {

    /**
     * Machine ID for the respective part
     */
    int machineID;

    /**
     * Serves as a constructor for a new instance of an object.
     *
     * @param id the ID of the part
     * @param name the name of the part
     * @param price the price of the part
     * @param stock the inventory level of the part
     * @param min the minimum level of the part
     * @param max the maximum level of the part
     * @param machineID the machine ID of the part
     */

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        super(id,name,price,stock,min,max);
        setMachineID(machineID);
    }
    /**
     * This will set the machineID value
     *
     * @param machineID is the machine ID of the part
     */

    public void setMachineID(int machineID){
        this.machineID = machineID;
    }

    /**
     * This will retrieve the machineID value
     * @return machineId of the part
     */
    public int getMachineID(){
        return machineID;
    }

}


