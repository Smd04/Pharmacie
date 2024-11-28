package org.example;

import javafx.application.Application;
import javafx.beans.property.SimpleObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class Medicaments extends Application {

    private Database db;

    @Override
    public void start(Stage primaryStage) {
        db = new Database();

        TableView<Map<String, Object>> tableView = new TableView<>();

        // Define columns with appropriate CellValueFactory
        TableColumn<Map<String, Object>, String> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().get("id").toString()));

        TableColumn<Map<String, Object>, String> nameColumn = new TableColumn<>("Name");
        nameColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().get("name").toString()));

        TableColumn<Map<String, Object>, String> companyColumn = new TableColumn<>("Company");
        companyColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>(data.getValue().get("company").toString()));

        TableColumn<Map<String, Object>, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>((Integer) data.getValue().get("quantity")));

        TableColumn<Map<String, Object>, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(data ->
                new SimpleObjectProperty<>((Double) data.getValue().get("price")));

        tableView.getColumns().addAll(idColumn, nameColumn, companyColumn, quantityColumn, priceColumn);

        // Ensure columns cover the full width
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        idColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        nameColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        companyColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        quantityColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));
        priceColumn.prefWidthProperty().bind(tableView.widthProperty().multiply(0.2));

        // Fetch data from the database
        List<Map<String, Object>> medicines = db.getMedicines();
        ObservableList<Map<String, Object>> data = FXCollections.observableArrayList(medicines);
        tableView.setItems(data);

        // Add row click functionality
        tableView.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Double-click
                Map<String, Object> selectedItem = tableView.getSelectionModel().getSelectedItem();
                if (selectedItem != null) {
                    Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                    alert.setTitle("Delete Confirmation");
                    alert.setHeaderText(null);
                    alert.setContentText("Would you like to delete this medicament?");
                    alert.initOwner(primaryStage);
                    alert.initModality(Modality.APPLICATION_MODAL);
                    Optional<ButtonType> result = alert.showAndWait();

                    if (result.isPresent() && result.get() == ButtonType.OK) {
                        try {
                            String id = selectedItem.get("id").toString();
                            db.deleteMedicine(id);
                            data.remove(selectedItem);
                            System.out.println("Medicament deleted: " + id);
                        } catch (Exception e) {
                            System.err.println("Error deleting medicine: " + e.getMessage());
                        }
                    }
                }
            }
        });

        // Add a button in the top-right corner
        Button redButton = new Button("X");
        redButton.setStyle("-fx-background-color: red; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 5px 10px;");
        redButton.setOnAction(event -> {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Button Clicked");
            alert.setHeaderText(null);
            alert.setContentText("You clicked the red button!");
            alert.showAndWait();
        });

        HBox topBar = new HBox(redButton);
        topBar.setAlignment(Pos.TOP_RIGHT);
        topBar.setStyle("-fx-padding: 5px;");

        BorderPane root = new BorderPane();
        root.setTop(topBar);
        root.setCenter(tableView);

        // Set up the scene
        Scene scene = new Scene(root, 800, 600);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Medicines");
        primaryStage.show();

        primaryStage.setOnCloseRequest(event -> {
            db.closeConnection();
            System.out.println("Database connection closed.");
        });
    }

    public static void main(String[] args) {
        launch(args);
    }
}
