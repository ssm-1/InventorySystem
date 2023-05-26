package com.example.inventorysys2.Controllers;

import com.example.inventorysys2.Model.InHouse;
import com.example.inventorysys2.Model.Inventory;
import com.example.inventorysys2.Model.Outsourced;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This controller provides the logic needed to implement the add part form
 * @author Sravya Maddipatla
 * RUNTIME ERROR - I ran into an NumberFormatException error. This happened because I was trying to
 * convert an integer into a string. To resolve this, I made sure to use the proper function needed
 * for the conversion.
 */
public class AddPartController implements Initializable {

    /**
     * The add Part label for the part.
     */
    public Label addPartLabel;

    /**
     * The outsourced radio button.
     */
    public RadioButton outsourcedLabel;

    /** The toggle feature for the radio buttons. */

    public ToggleGroup tgroup;

    /**
     * The in-house radio button.
     */
    public RadioButton inHouseLabel;

    /** The anchor pane that ties all the elements together */
    public AnchorPane anchorAdd;

    /**
     * The ID label for the part.
     */
    public Label idLabel;


    /**
     * The name label for the part.
     */
    public Label nameLabel;


    /**
     * The inventory label for the part.
     */
    public Label invLabel;


    /**
     * The price label for the part.
     */
    public Label priceLabel;

    /**
     * The max label for the part.
     */
    public Label maxLabel;

    /**
     * The machine ID label for the part.
     */
    public Label machineIDlabel;

    /**
     * The min label for the part.
     */
    public Label minLabel;

    /**
     * The id text field for the part.
     */
    public TextField idField;

    /**
     * The part name text field for the part.
     */
    public TextField partNameText;

    /**
     * The inventory text field for the part.
     */
    public TextField partInvText;
    /**
     * The price text field for the part.
     */
    public TextField partPriceText;
    /**
     * The max text field for the part.
     */
    public TextField partMaxText;
    /**
     * The machine id text field for the part.
     */
    public TextField partMachineIDText;
    /**
     * The min text field for the part.
     */
    public TextField partMinText;

    /** The string that will be assigned to the generated ID */

    public String value;

    /**
     * Will initialize the controller and set the in-house radio button to be selected.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        inHouseLabel.setSelected(true);
    }

    /**
     * This will set the machine ID label to "Company Name".
     *
     * @param actionEvent Outsourced radio button.
     */

    public void onOutsourced(ActionEvent actionEvent) {
        addPartLabel.setText("OutSourced was clicked");
        outsourcedLabel.setSelected(true);
        this.machineIDlabel.setText("Company Name");
        value = String.valueOf(Inventory.contiguousPartID());
        this.idField.setText(value);

    }
    /**
     * This will set the machine ID label to "Machine ID".
     *
     * @param actionEvent Outsourced radio button.
     */

    public void onInHouse(ActionEvent actionEvent) {
        addPartLabel.setText("In-House was clicked");
        inHouseLabel.setSelected(true);
        this.machineIDlabel.setText("Machine ID");
        value = String.valueOf(Inventory.contiguousPartID());
        this.idField.setText(value);
    }

    /**
     * Checks to see if min is greater than zero and less than the maximum value.
     *
     * @param min the minimum value for the selected.
     * @param max the maximum value for the selected.
     * @return A boolean that indicates if min is valid.
     */
    private boolean validMin(int min, int max) {
        boolean ifValid = true;
        if(min <= 0 || min >= max) {
            ifValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid value for the min field.");
            alert.setContentText("Min value must be greater than zero and less than max value.");
            alert.showAndWait();
        }
        return ifValid;
    }

    /**
     * This will check if the inventory is in an inclusive range between the min and max.
     *
     * @param min the minimum value for the selected.
     * @param max the maximum value for the selected.
     * @param stock the inventory level for the selected.
     * @return A boolean that shows if valid inventory.
     */

    private boolean validInventory(int min, int max, int stock) {

        boolean ifValid = true;

        if (stock < min || stock > max) {
            ifValid = false;
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Invalid value.");
            alert.setContentText("Inventory value must lie in an inclusive range between the min and max values");
            alert.showAndWait();
        }

        return ifValid;
    }

    /**
     * Adds a new part to inventory and then displays the MainViewController.
     *
     * @param actionEvent is the action for the save button
     *
     */

    public void onSaveB(ActionEvent actionEvent) {
        try {
            int id = 0;
            String name = partNameText.getText();
            Double price = Double.parseDouble(partPriceText.getText());
            int stock = Integer.parseInt(partInvText.getText());
            int min = Integer.parseInt(partMinText.getText());
            int max = Integer.parseInt(partMaxText.getText());
            int machineId;
            String companyName;
            boolean addSuccess = false;

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name field is empty.");
                alert.setContentText("Please enter an input for the name field in order to add your data.");
                alert.showAndWait();
            } else {
                if (validMin(min, max) && validInventory(min, max, stock)) {

                    if (inHouseLabel.isSelected()) {

                        machineId = Integer.parseInt(partMachineIDText.getText());
                        InHouse newInHouse = new InHouse(id, name, price, stock, min, max, machineId);
                        newInHouse.setId(Inventory.contiguousPartID());
                        Inventory.addPart(newInHouse);
                        addSuccess = true;
                    }

                    if (outsourcedLabel.isSelected()) {
                        companyName = partMachineIDText.getText();
                        Outsourced newOutsourced = new Outsourced(id, name, price, stock, min, max,
                                companyName);
                        newOutsourced.setId(Inventory.contiguousPartID());
                        Inventory.addPart(newOutsourced);
                        addSuccess = true;
                    }

                    if (addSuccess) {
                        Parent screen = FXMLLoader.load(getClass().getResource("/com/example/inventorysys2/mainview1.fxml"));
                        Scene scene = new Scene(screen);
                        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        window.setScene(scene);
                        window.show();
                    }
                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There was an error adding the part.");
            alert.setContentText("Invalid values or blank fields.");
            alert.showAndWait();


        }
    }

    /**
     * Displays a confirmation alert and opens the MainViewController.
     *
     * @param actionEvent is the action for the button
     *
     * @throws IOException from FXML loader.
     */

    public void onCancelB(ActionEvent actionEvent) throws IOException{

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to go to the main screen?");
        Optional<ButtonType> result = alert.showAndWait();

        if (result.isPresent() && result.get() == ButtonType.OK) {
            Parent screen = FXMLLoader.load(getClass().getResource("/com/example/inventorysys2/mainview1.fxml"));
            Scene scene = new Scene(screen);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();

        }
    }
}



