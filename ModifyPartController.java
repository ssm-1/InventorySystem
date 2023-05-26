package com.example.inventorysys2.Controllers;

import com.example.inventorysys2.Model.InHouse;
import com.example.inventorysys2.Model.Inventory;
import com.example.inventorysys2.Model.Outsourced;
import com.example.inventorysys2.Model.Part;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This controller provides the logic needed to implement the modify part form
 * @author Sravya Maddipatla */

public class ModifyPartController implements Initializable {

/** The save button in the modify part form */
    public Button saveButton;

    /** The cancel button in the modify part form */

    public Button cancelButton;

    /** modify part label in the modify part form */

    public Label modifyPart;

    /**
     * The in-house radio button.
     */
    public RadioButton inHouseLabel;
    /** The toggle feature for the radio buttons. */

    public ToggleGroup tgroup;

    /**
     * The outsourced radio button.
     */
    public RadioButton outsourcedLabel;

    /** ID part label in the modify part form */

    public Label idLabel;

    /** name part label in the modify part form */

    public Label nameLabel;

    /** inventory part label in the modify part form */

    public Label invLabel;

    /**
     * The id text field for the part.
     */

    public TextField idField;

    /**
     * The inventory text field for the part.
     */

    public TextField invField;

    /**
     * The part name text field for the part.
     */

    public TextField nameField;
    /**
     * The price text field for the part.
     */

    public TextField priceField;

    /**
     * The machine id text field for the part.
     */

    public TextField machineIdField;
    /**
     * The max text field for the part.
     */
    public TextField maxField;
    /**
     * The min text field for the part.
     */
    public TextField minField;
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
    public Label machineID;

    /**
     * The min label for the part.
     */
    public Label minLabel;

    /**The selected Part in the main view controller.*/
    private Part selectedPart;
    /**
     * Will initialize the controller and change the name of the machine ID text ID field based on the radio button selected.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        selectedPart = MainViewController.modifiedPart();

        if(selectedPart instanceof InHouse) {
            inHouseLabel.setSelected(true);
            machineID.setText("Machine ID");
            machineIdField.setText(String.valueOf(((InHouse) selectedPart).getMachineID()));
        }

        if (selectedPart instanceof Outsourced) {
            outsourcedLabel.setSelected(true);
            machineID.setText("Company Name");
            machineIdField.setText(String.valueOf(((Outsourced) selectedPart).getCompanyName()));
        }

        nameField.setText(selectedPart.getName());
        idField.setText(String.valueOf(selectedPart.getId()));
        invField.setText(String.valueOf(selectedPart.getStock()));
        priceField.setText(String.valueOf(selectedPart.getPrice()));
        maxField.setText(String.valueOf(selectedPart.getMax()));
        minField.setText(String.valueOf(selectedPart.getMin()));
    }


    /**
     * Modifies the part and saves it to inventory and then displays the MainViewController.
     *
     * @param actionEvent is the action for the save button
     *
     */
    public void onSaveButton(ActionEvent actionEvent) {
        try {
            int id = selectedPart.getId();
            String name = nameField.getText();
            Double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());
            int machineId;
            String companyName;
            boolean addSuccess = false;

            if (validMin(min, max) && validInventory(min, max, stock)) {
                    if (inHouseLabel.isSelected()) {
                        try {
                            machineId = Integer.parseInt(machineIdField.getText());
                            InHouse newInHouse = new InHouse(id, name, price, stock, min, max, machineId);
                            Inventory.addPart(newInHouse);
                            addSuccess = true;

                        } catch (Exception e) {
                            Alert alert = new Alert(Alert.AlertType.ERROR);
                            alert.setTitle("Error");
                            alert.setHeaderText("Invalid value for machine ID field.");
                            alert.setContentText("Machine ID can only contain numbers.");
                            alert.showAndWait();
                        }
                    }

                    if (outsourcedLabel.isSelected()) {
                        companyName = machineIdField.getText();
                        Outsourced newOutsourced = new Outsourced(id, name, price, stock, min, max,
                                companyName);
                        Inventory.addPart(newOutsourced);
                        addSuccess = true;
                    }
                    if (addSuccess) {
                        Inventory.deletePart(selectedPart);
                        Parent screen = FXMLLoader.load(getClass().getResource("/com/example/inventorysys2/mainview1.fxml"));
                        Scene scene = new Scene(screen);
                        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                        window.setScene(scene);
                        window.show();
                    }
                }

    } catch(Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("There was an error adding the part.");
            alert.setContentText("Invalid values or Blank fields.");
            alert.showAndWait();
        }

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
     * Displays a confirmation alert and opens the MainViewController.
     *
     * @param actionEvent is the action for the button
     *
     * @throws IOException from FXML loader.
     */

    public void onCancelButton(ActionEvent actionEvent) throws IOException {
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
    /**
     * This will set the machine ID label to "Machine ID".
     *
     * @param actionEvent Outsourced radio button.
     */

    public void onInHouse(ActionEvent actionEvent) {
        this.machineID.setText("Machine ID");

    }
    /**
     * This will set the machine ID label to "Company Name".
     *
     * @param actionEvent Outsourced radio button.
     */
    public void onOutsourced(ActionEvent actionEvent) {
        this.machineID.setText("Company Name");

    }
}
