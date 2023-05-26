package com.example.inventorysys2.Controllers;

import com.example.inventorysys2.Model.Inventory;
import com.example.inventorysys2.Model.Part;
import com.example.inventorysys2.Model.Product;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import static com.example.inventorysys2.Model.Inventory.*;

/** This controller provides the logic needed to implement the main form
 * @author Sravya Maddipatla */
public class MainViewController implements Initializable {

    /**The products table in the form*/
    public TableView<Product> productsTable;
    /** The column for the product ID column for the products table. */

    public TableColumn productIdCol;

    /** The column for the product cost column for the products table. */

    public TableColumn productCostCol;
    /**The parts table in the form*/
    public TableView<Part> partsTable;

    /** The column for the part ID column for the parts table. */
    public TableColumn partId;

    /** The column for the part name column for the parts table. */

    public TableColumn partName;

    /** The column for the part inventory column for the parts table. */

    public TableColumn partInventory;

    /** The column for the part price column for the parts table. */

    public TableColumn partPrice;

    /** The column for the product name column for the products table. */

    public TableColumn productNameCol;

    /** The column for the product stock column for the products table. */

    public TableColumn productStockCol;
    /**The add button that will open the 'Add Parts' form */
    public Button addButton;
    /**The modify button that will open the 'Modify Parts' form */

    public Button modifyParts;

    /**The add button that will open the 'Add Products' form */

    public Button addProducts;

    /**The modify button that will open the 'Modify Products' form */

    public Button modifyProducts;

    /**The delete button that will delete the selected product */

    public Button deleteProducts;

    /**
     * The search text field in the product table.
     */
    public TextField searchProducts;

    /**The delete button that will close the form */

    public Button exitButton;

    /**The delete button that will delete the selected part. */

    public Button deleteParts;

    /**
     * The search text field in the parts table.
     */

    public TextField searchPartsID;

    /**The part that will be modified.*/
    private static Part partModify;

    /**The product that will be modified.*/

    private static Product productModify;

    /**The modified Part method
     * @return partModify the modified part being returned. */
    public static Part modifiedPart() {
        return partModify;
    }
    /**The modified Product method
     * @return productModify the modified part being returned. */

    public static Product modifiedProduct() {
        return productModify;
    }

    /**
     * Will initialize the controller and populate tables.
     *
     * @param url The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param rb The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        partsTable.setItems(Inventory.getAllParts());

        partId.setCellValueFactory(new PropertyValueFactory<>("id"));
        partName.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventory.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPrice.setCellValueFactory(new PropertyValueFactory<>("price"));

        productsTable.setItems(Inventory.getAllProducts());

        productIdCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        productStockCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productCostCol.setCellValueFactory(new PropertyValueFactory<>("price"));
    }

    /**
     * Closes the main form.
     *
     * @param actionEvent is the action for the button
     */
    public void onExit(ActionEvent actionEvent) {
        Stage stageTheEventSourceNodeBelongs = (Stage) ((Node)actionEvent.getSource()).getScene().getWindow();
        stageTheEventSourceNodeBelongs.close();

    }

    /**
     * This will open the 'Add parts' screen.
     *
     * @param actionEvent the action for the remove button.
     * @throws IOException from FXML loader.
     */

    public void onAddParts(ActionEvent actionEvent) throws IOException {
        Parent screen = FXMLLoader.load(getClass().getResource("/com/example/inventorysys2/addPart.fxml"));
        Scene scene = new Scene(screen);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();

    }

    /**
     * This will open the 'modify parts' screen once an item is selected.
     * An error will be displayed if no item is selected.
     * @param actionEvent the action for the remove button.
     * @throws IOException from FXML loader.
     */

    public void onmodifyParts(ActionEvent actionEvent) throws IOException {

        partModify = partsTable.getSelectionModel().getSelectedItem();

        if(partModify == null) {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            Alert Error = new Alert(Alert.AlertType.ERROR);
            Error.setTitle("Error");
            Error.setHeaderText("Part not selected");
            Error.showAndWait();

        }
        else {

            Parent screen = FXMLLoader.load(getClass().getResource("/com/example/inventorysys2/modifyPart.fxml"));
            Scene scene = new Scene(screen);
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        }

    }

    /**
     * This will delete the selected part..
     * An error will be displayed if no item is selected.
     * @param actionEvent the action for the remove button.
     */
    public void onDeleteParts(ActionEvent actionEvent) {
        System.out.println("On Delete clicked");
        partsTable.setItems(getAllParts());
        Part AB = partsTable.getSelectionModel().getSelectedItem();
        if (AB == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Part needs to be selected");
            alert.showAndWait();

            return;
        }

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to delete the selected part?");
        Optional<ButtonType> resultPart = alert.showAndWait();

        if (resultPart.isPresent() && resultPart.get() == ButtonType.OK) {
            Inventory.deletePart(AB);
        }
    }
    /**
     * Initiates a search based on the String or integer entered and then refreshes the table.
     * Parts can be searched for by ID or name.
     *
     * @param actionEvent the action for searching the parts table.
     */
    public void onSearchParts(ActionEvent actionEvent) {

        partsTable.setItems(Inventory.getAllParts());

        String q = searchPartsID.getText();
        ObservableList<Part> partsOut = FXCollections.observableArrayList();
        partsOut = lookupPart(q);
        if (partsOut.size() == 0) {
              int partID  = 0 ;
            try {
                 partID = Integer.parseInt(q);
                Part Ab = lookupPart(partID);

                if (Ab != null) {
                    partsOut.add(Ab);
                }

                if (partsOut.size() == 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Error - Part not found.");
                    alert.showAndWait();
                }

            } catch (NumberFormatException e) {
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Error - Part not found.");
            alert.showAndWait();
            searchProducts.setText("");


        }
        partsTable.setItems(partsOut);
    }
    /**
     * Initiates a search based on the String or integer entered and then refreshes the table.
     * Products can be searched for by ID or name.
     *
     * @param actionEvent the action for searching the parts table.
     */
    public void onSearchProducts(ActionEvent actionEvent) {

        productsTable.setItems(Inventory.getAllProducts());

        String h = searchProducts.getText();
        ObservableList<Product> productsOut = lookupProduct(h);


        if (productsOut.size() == 0) {

            try {
                int productID = Integer.parseInt(h);

                Product Ad = lookupProduct(productID);
                if (Ad != null) {
                    productsOut.add(Ad);
                } else if (Ad == null) {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText("Error - Product not found.");
                    alert.showAndWait();
                    searchProducts.setText("");
                }

            }


            catch (NumberFormatException f) {

            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Error - Product not found.");
            alert.showAndWait();
            searchProducts.setText("");

        }
        productsTable.setItems(productsOut);
    }


    /**
     * This will open the 'add products' screen once an item is selected.
     * An error will be displayed if no item is selected.
     * @param actionEvent the action for the remove button.
     * @throws IOException from FXML loader.
     */
    public void onaddProducts(ActionEvent actionEvent) throws IOException {
        Parent screen = FXMLLoader.load(getClass().getResource("/com/example/inventorysys2/addProduct.fxml"));
        Scene scene = new Scene(screen);
        Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }
    /**
     * This will open the 'modify products' screen once an item is selected.
     * An error will be displayed if no item is selected.
     * @param actionEvent the action for the remove button.
     * @throws IOException from FXML loader.
     */
    public void onmodifyProducts(ActionEvent actionEvent) throws IOException {

        productModify = productsTable.getSelectionModel().getSelectedItem();

        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();

        if(productModify == null) {

            Alert Error = new Alert(Alert.AlertType.ERROR);
            Error.setTitle("Error");
            Error.setHeaderText("Product not selected");
            Error.showAndWait();

        }

else {
            System.out.println(productModify.getAllAssociatedParts().size());
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("/com/example/inventorysys2/modifyProduct.fxml"));
            loader.load();
            Stage window = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
            Parent root = loader.getRoot();
            window.setScene(new Scene(root));
            window.show();

            ModifyProductController mp = loader.getController();
            mp.setData(productModify, selectedIndex);
        }
    }
    /**
     * Will display a confirmation dialog and then will remove the selected product in the Products table.
     * Also, will display an error message if nothing selected.
     *
     * @param actionEvent the action for the remove button.
     */
    public void onDeleteProducts(ActionEvent actionEvent) {
        System.out.println("On Delete clicked");
        productsTable.setItems(getAllProducts());
        Product BC = productsTable.getSelectionModel().getSelectedItem();
        if (BC == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);

            alert.setContentText("Product needs to be selected");
            alert.showAndWait();
            return;
        }
        if(BC.getAllAssociatedParts().size() >= 1) {
            System.out.println("output");
            Alert alertE = new Alert(Alert.AlertType.ERROR);

            alertE.setContentText("Product has associated parts");
            alertE.showAndWait();
            return;
        }
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Alert");
        alert.setContentText("Do you want to delete the selected product?");

        Optional<ButtonType> resultProd = alert.showAndWait();
        if (resultProd.isPresent() && resultProd.get() == ButtonType.OK) {
           // ObservableList<Part> associatedParts = BC.getAllAssociatedParts();





                Inventory.deleteProduct(BC);

        }


    }


}
