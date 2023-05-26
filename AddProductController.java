package com.example.inventorysys2.Controllers;

import com.example.inventorysys2.Model.Inventory;
import com.example.inventorysys2.Model.Part;
import com.example.inventorysys2.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

/** This controller provides the logic needed to implement the add product form
 * @author Sravya Maddipatla */

public class AddProductController implements Initializable {
    /**
     * The add button in the add product form.
     */
    public Button addButton;
    /** The text field for searching a part. */
    public TextField searchPartField;
    /**
     * The cancel button in the add product form.
     */
    public Button cancelButton;
    /**
     * The save button in the add product form.
     */
    public Button saveButton;
    /**
     * The remove button in the add product form.
     */
    public Button removeButton;
    /**
     * The add product label in the add product form.
     */

    public Label addProductLabel;

    /** The grid pane that holds all the components in the form. */
    public GridPane gridpane22;

    /**
     * The id label in the add product form.
     */

    public Label idLabel;

    /**
     * The name label in the add product form.
     */
    public Label nameLabel;

    /** The table-view for the topmost table in the add product form. */
    public TableView<Part> table1;

    /**The index of the selected product. */
    private int selectedIndex;

    /** The column for the part ID column for the first table. */

    public TableColumn<Object, Object> partIdCol;

    /** The column for the part name column for the first table. */

    public TableColumn partNameCol;

    /** The column for the part inventory column for the first table. */

    public TableColumn partInvCol;

    /** The column for the part price column for the first table. */

    public TableColumn partPriceCol;
    /** The table-view for the bottommost table in the add product form. */
    public TableView<Part> table2;

    /** The column for the part ID column for the second table. */

    public TableColumn partIdCol1;
    /** The column for the part name column for the second table. */

    public TableColumn partNameCol1;
    /** The column for the part inventory column for the second table. */

    public TableColumn partInvCol1;

    /** The column for the part price column for the second table. */

    public TableColumn partPriceCol1;

    /**
     * The results label in the add product form.
     */
    public Label resultsP;
    /**
     * The id text field in the add product form.
     */
    public TextField idField;
    /**
     * The name text field in the add product form.
     */
    public TextField nameField;
    /**
     * The inventory text field in the add product form.
     */
    public TextField invField;
    /**
     * The price text field in the add product form.
     */
    public TextField priceField;
    /**
     * The maximum value text field in the add product form.
     */
    public TextField maxField;

    /**The selected product to be modified.*/
    private Product selectedProduct = new Product(23, "Car", 2999.99, 124, 1, 1000);

    /**
     * The minimum value text field in the add product form.
     */
    public TextField minField;

    /**
     * A list of parts to be added to the second table in the form.
     */
    private ObservableList<Part> allProducts2 = FXCollections.observableArrayList();

    /**
     * A list of associated Parts to be displayed in the second table in the form.
     */
    private ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    /**
     * Will initialize the controller and populate tables.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
    */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        partIdCol.setCellValueFactory((new PropertyValueFactory<>("id")));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        table1.setItems(Inventory.getAllParts());


        partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));

    }

    /**
     * Will display a confirmation dialog and then will remove the selected part in the associated Parts table.
     * Also, will display an error message if nothing selected.
     *
     * @param actionEvent the action for the remove button.
     */

    public void onRemoveB(ActionEvent actionEvent)  {
        System.out.println("on remove clicked");
        Part selectedPart = table2.getSelectionModel().getSelectedItem();
        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Part needs to be selected");
            alert.showAndWait();
            return;
        }
        else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                System.out.println("test");
                selectedProduct.deleteAssociatedPart(selectedPart);
            }
        }

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
        if (min <= 0 || min >= max) {
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
     * Adds a new product to inventory and then displays the MainViewController.
     *
     * @param actionEvent is the action for the save button.
     */

    public void onSaveB(ActionEvent actionEvent) {

        System.out.println("on save clicked");
        try {
            int id = 0;
            String name = nameField.getText();
            Double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Name field is empty.");
                alert.setContentText("Please enter an input for the name field in order to add your data.");
                alert.showAndWait();
            } else {
                if (validMin(min, max) && validInventory(min, max, stock)) {

                    Product newPr = new Product(id, name, price, stock, min, max);

                    newPr.setId(Inventory.contiguousPartID());
                    Inventory.addProduct(newPr);
                    //Inventory.updateProduct(selectedIndex, newPr);


                    for (Part part : associatedParts) {
                        newPr.addAssociatedPart(part);
                    }
                    System.out.println(newPr.getAllAssociatedParts().size());
                    Parent screen = FXMLLoader.load(getClass().getResource("/com/example/inventorysys2/mainview1.fxml"));
                    Scene scene = new Scene(screen);
                    Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    window.setScene(scene);
                    window.show();
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
     * Initiates a search based on the String or integer entered and then refreshes the table.
     * Parts can be searched for by ID or name.
     *
     * @param actionEvent the action for searching the parts table.
     */
    public void onSearchP(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = searchPartField.getText();

        for (Part part : allParts) {
            if (String.valueOf(part.getId()).contains(searchString) ||
                    part.getName().contains(searchString)) {
                partsFound.add(part);
            }
        }
        table1.setItems(partsFound);

        if (partsFound.size() == 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Search not completed.");
            alert.setContentText("Invalid value");
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
    public void onCancelB(ActionEvent actionEvent) throws IOException {
        System.out.println("on cancel clicked");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Go back to the main screen?");
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
     * Will display a confirmation dialog and then will add the selected part to the associated Parts table.
     *
     * Also, will display an error message if nothing selected.
     *
     * @param actionEvent the action for the remove button.
     */

    public void onAddB(ActionEvent actionEvent) {
        System.out.println("on add clicked");
        Part SP = table1.getSelectionModel().getSelectedItem();
        if (SP == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("No item selected.");
            alert.setContentText("Select an item in order to add it to the associated parts table.");
            alert.showAndWait();
        } else {
       //     selectedProduct.addAssociatedPart(SP);
      //      table2.setItems(selectedProduct.getAllAssociatedParts());
            associatedParts.add(SP);
            table2.setItems(associatedParts);
        }
    }

    /** This method will specify how the cells in the columns should be populated.
     * It also will set the tables appropriately.
     * @param productModify the selected product to be modified
     * @param selectedIndex the selected Index that will be used
     * */

    public void setData(Product productModify, int selectedIndex) {

        selectedProduct = productModify;
        this.selectedIndex = selectedIndex;

        table2.setItems(selectedProduct.getAllAssociatedParts());
        System.out.println(selectedProduct.getAllAssociatedParts().size());
        partIdCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}
