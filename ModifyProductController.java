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

/** This controller provides the logic needed to implement the modify product form
 * @author Sravya Maddipatla */
public class ModifyProductController implements Initializable {
    /** The grid pane that holds all the components in the form. */

    public GridPane gridpaneM;

    /**
     * The id label in the modify product form.
     */
    public Label idLabel;

    /**
     * The name label in the modify product form.
     */
    public Label nameLabel;
    /**
     * The inventory label in the modify product form.
     */
    public Label invLabel;

    /**
     * The price label in the modify form.
     */
    public Label priceLabel;

    /**
     * The maximum value label in the modify form.
     */
    public Label maxLabel;

    /**
     * The minimum value label in the modify form.
     */
    public Label minLabel;
    /**
     * The id label in the modify product form.
     */
    public TextField idField;
    /**
     * The name text field in the modify product form.
     */
    public TextField nameField;
    /**
     * The inventory text field in the modify product form.
     */
    public TextField invField;
    /**
     * The price text field in the modify product form.
     */
    public TextField priceField;
    /**
     * The maximum value text field in the modify product form.
     */
    public TextField maxField;

    /**
     * The minimum value text field in the modify product form.
     */
    public TextField minField;
    /** The column for the part ID column for the first table. */

    public TableColumn<Part, Integer> partIDCol;
    /** The column for the part name column for the first table. */

    public TableColumn<Part, String> partNameCol;
    /** The column for the part inventory column for the first table. */

    public TableColumn<Part, Integer> invLevelCol;
    /** The column for the part price column for the first table. */

    public TableColumn<Part, Double> priceCol;
    /**
     * The search text field in the modify product form.
     */
    public TextField searchField;

    /** The column for the part ID column for the second table. */

    public TableColumn<Part, Integer> partIDCol1;
    /** The column for the part name column for the second table. */

    public TableColumn<Part, String> partNameCol1;
    /** The column for the part inventory column for the second table. */

    public TableColumn<Part, Integer> partInvCol1;
    /** The column for the part price column for the second table. */

    public TableColumn<Part, Double> priceCol1;

    /**
     * The add button in the modify product form.
     */
    public Button addButton;
    /**
     * The remove button in the modify product form.
     */
    public Button removeButton;
    /**
     * The save button in the modify product form.
     */
    public Button saveButton;

    /**
     * The cancel button in the modify product form.
     */
    public Button cancelButton;

    /** The table-view for the bottommost table in the add product form. */

    public TableView<Part> table2;

    /** The table-view for the topmost table in the modify product form. */

    public TableView<Part> table1;


    /**
     * A list of associated Parts to be displayed in the second table in the form.
     */
    private ObservableList<Part> assocParts = FXCollections.observableArrayList();

/**The index of the selected product. */
    private int selectedIndex;

    /**The selected product to be modified.*/
    private Product selectedProduct;

    /**
     * Will initialize the controller and populate tables.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectedProduct = MainViewController.modifiedProduct();
        assocParts = selectedProduct.getAllAssociatedParts();

        partIDCol.setCellValueFactory((new PropertyValueFactory<>("id")));
        partNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invLevelCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        table1.setItems(Inventory.getAllParts());

        idField.setText(String.valueOf(selectedProduct.getId()));
        nameField.setText(selectedProduct.getName());
        invField.setText(String.valueOf(selectedProduct.getStock()));
        priceField.setText(String.valueOf(selectedProduct.getPrice()));
        maxField.setText(String.valueOf(selectedProduct.getMax()));
        minField.setText(String.valueOf(selectedProduct.getMin()));
    }
    /**
     * Initiates a search based on the String or integer entered and then refreshes the table.
     * Parts can be searched for by ID or name.
     *
     * @param actionEvent the action for searching the parts table.
     */
    public void onSearchField(ActionEvent actionEvent) {
        ObservableList<Part> allParts = Inventory.getAllParts();
        ObservableList<Part> partsFound = FXCollections.observableArrayList();
        String searchString = searchField.getText();

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
     * Will display a confirmation dialog and then will add the selected part to the associated Parts table.
     *
     * Also, will display an error message if nothing selected.
     *
     * @param actionEvent the action for the remove button.
     */

    public void onAddB(ActionEvent actionEvent) {
        Part selectedPart = table1.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Part not selected");
            alert.showAndWait();

        } else {
            assocParts.add(selectedPart);
            table2.setItems(assocParts);
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
        if (min < 0 || min >= max) {
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
     * Modifies a product to the inventory and then displays the MainViewController.
     *
     * @param actionEvent is the action for the save button.
     * @throws IOException from FXML loader
     */
    public void onSaveButton(ActionEvent actionEvent) throws IOException {
        try {
            int id = selectedProduct.getId();
            String name = nameField.getText();
            double price = Double.parseDouble(priceField.getText());
            int stock = Integer.parseInt(invField.getText());
            int min = Integer.parseInt(minField.getText());
            int max = Integer.parseInt(maxField.getText());

            if (name.isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);

                alert.setTitle("Error");
                alert.setHeaderText("Name field empty");
                alert.showAndWait();
            } else {
                if (validMin(min, max) && validInventory(min, max, stock)) {

                    Product newProduct = new Product(id, name, price, stock, min, max);
                    Inventory.updateProduct(selectedIndex, newProduct);
                    Parent parent = FXMLLoader.load(getClass().getResource("/com/example/inventorysys2/mainview1.fxml"));
                    Scene scene = new Scene(parent);
                    Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                    stage.setScene(scene);
                    stage.show();

                }
            }
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setHeaderText("Blank or invalid values");
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
    public void onCancelButton(ActionEvent actionEvent) throws IOException {
        System.out.println("on cancel clicked");
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
     * Will display a confirmation dialog and then will remove the selected part in the associated Parts table.
     *
     * Also, will display an error message if nothing selected.
     *
     * @param actionEvent the action for the remove button.
     * @throws IOException from FXML loader
     */
    public void onremoveButton(ActionEvent actionEvent) throws IOException {
        Part selectedPart = table2.getSelectionModel().getSelectedItem();

        if (selectedPart == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);


            alert.setTitle("Error");
            alert.setHeaderText("Part not selected");
            alert.showAndWait();

        } else {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Alert");
            alert.setContentText("Do you want to remove the selected part?");
            Optional<ButtonType> result = alert.showAndWait();

            if (result.isPresent() && result.get() == ButtonType.OK) {
                selectedProduct.deleteAssociatedPart(selectedPart);
                table2.setItems(assocParts);
            }
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
        partIDCol1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameCol1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInvCol1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol1.setCellValueFactory(new PropertyValueFactory<>("price"));


    }
}