package org.example;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.paint.Color;

public class AjoutDeMedicament extends Application {

    @Override
    public void start(Stage primaryStage) {
        Text title = new Text("Dashboard");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        title.setStyle("-fx-fill: white");

        HBox titleBox = new HBox();
        titleBox.setAlignment(Pos.CENTER);
        titleBox.getChildren().add(title);
        titleBox.setPadding(new Insets(20));

        Pane line = new Pane();
        line.setMinHeight(1);
        line.setStyle("-fx-background-color: white;");


        Button save= new Button("\uD83D\uDCBE Save");
        Button logout= new Button("Logout");
        logout.setStyle("-fx-background-color: transparent; ");
        logout.setPrefWidth(300);
        Button exit= new Button("Exit");
        exit.setStyle("-fx-background-color: transparent; ");
        exit.setPrefWidth(300);

        TextField ID = new TextField();
        TextField Name = new TextField();
        TextField Company = new TextField();
        TextField Quantity = new TextField();
        TextField Price = new TextField();

        Label l0 = new Label("Add Medicine");
        l0.setFont(Font.font("Arial", FontWeight.BOLD, 26));
        l0.setStyle("-fx-fill: black");

        HBox l0Box = new HBox();
        l0Box.setAlignment(Pos.CENTER);
        l0Box.getChildren().add(l0);

        Pane line2 = new Pane();
        line2.setMinHeight(1);
        line2.setStyle("-fx-background-color: dimgrey;");
        line2.setPrefWidth(500);



        Label l1 = new Label("Medicine ID ");
        l1.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label l2 = new Label("Name");
        l2.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label l3 = new Label("Company Name");
        l3.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label l4 = new Label("Quantity");
        l4.setFont(Font.font("Arial", FontWeight.BOLD, 15));
        Label l5 = new Label("Price Per Unit");
        l5.setFont(Font.font("Arial", FontWeight.BOLD, 15));

        GridPane grid = new GridPane();
        grid.setHgap(120); // Horizontal gap between columns
        grid.setVgap(20); // Vertical gap between rows
        grid.setPadding(new Insets(40));
        grid.setAlignment(Pos.CENTER);

        Label messageLabel = new Label();
        messageLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));
        messageLabel.setTextFill(Color.RED);

        // Add text fields to the grid
        grid.add(l0Box, 0, 0, 2, 1); // l0 spans 2 columns and 1 row
        grid.add(line2, 0, 1, 2, 1); // Line spans 2 columns and 1 row
        grid.add(l1, 0, 2);
        grid.add(ID, 0, 3);
        grid.add(l2, 0, 4);
        grid.add(Name, 0, 5);
        grid.add(l3, 0, 6);
        grid.add(Company, 0, 7);
        grid.add(l4, 1, 2);
        grid.add(Quantity, 1, 3);
        grid.add(l5, 1, 4);
        grid.add(Price, 1, 5);
        grid.add(save,1,6);
        grid.add(messageLabel,1,7);

        grid.setStyle("-fx-background-color: white;  -fx-border-radius: 10px; -fx-background-radius: 10px;");
        grid.setMinWidth(700); // Set minimum width
        grid.setMaxWidth(700);


        // Add the grid to a VBox for padding and positioning
        VBox gridBox = new VBox();
        gridBox.setPadding(new Insets(250)); // Add padding around the grid
        gridBox.getChildren().add(grid);
        gridBox.setAlignment(Pos.CENTER);
        gridBox.setPadding(new Insets(100));


        // Add gridBox to a StackPane to control its background
        StackPane gridContainer = new StackPane();
        gridContainer.setStyle("-fx-background-color: dimgrey;");
        gridContainer.getChildren().add(gridBox);

        VBox topBox = new VBox(5);
        topBox.getChildren().addAll(titleBox, line);
        topBox.setAlignment(Pos.TOP_CENTER);

        // Create individual HBox for each button with style and padding
        HBox logoutBox = new HBox(logout);
        logoutBox.setAlignment(Pos.CENTER);
        logoutBox.setPadding(new Insets(10));
        logoutBox.setPrefWidth(300); // Set a preferred width to make it smaller
        logoutBox.setStyle("-fx-background-color: white;  -fx-border-radius: 10px; -fx-background-radius: 10px;");

        HBox exitBox = new HBox(exit);
        exitBox.setAlignment(Pos.CENTER);
        exitBox.setPadding(new Insets(10));
        exitBox.setPrefWidth(300); // Set a preferred width to make it smaller
        exitBox.setStyle("-fx-background-color: white;  -fx-border-radius: 10px; -fx-background-radius: 10px;");

        // Create an HBox to hold both button boxes with spacing to keep them side-by-side
        HBox buttonBox = new HBox(10, logoutBox, exitBox);
        buttonBox.setPadding(new Insets(20));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setPadding(new Insets(0, 0, 1000, 0));


        BorderPane root = new BorderPane();
        root.setTop(topBox);
        root.setCenter(gridContainer);
        root.setBottom(buttonBox);
        root.setStyle("-fx-background-color: dimgrey;");



        Scene scene = new Scene(root, 1000, 1000);

        primaryStage.setTitle("JavaFX Application");
        primaryStage.setScene(scene);
        primaryStage.show();

        save.setOnAction(actionEvent -> {
            String id = ID.getText().trim();
            String name = Name.getText().trim();
            String company = Company.getText().trim();
            String quantityText = Quantity.getText().trim();
            String priceText = Price.getText().trim();

            // Check for empty fields
            if (id.isEmpty() || name.isEmpty() || company.isEmpty() || quantityText.isEmpty() || priceText.isEmpty()) {
                // Display a warning message if any field is empty
                messageLabel.setText("Please fill in all fields.");
                return;
            }

            try {
                int quantity = Integer.parseInt(quantityText);
                double price = Double.parseDouble(priceText);

                Database db = new Database();
                db.insertMedicine(id, name, company, quantity, price);
                db.closeConnection();

                // Clear input fields after saving
                ID.clear();
                Name.clear();
                Company.clear();
                Quantity.clear();
                Price.clear();

            } catch (NumberFormatException e) {
                System.out.println("Invalid input: " + e.getMessage());
            }
        });
    }

    public static void main(String[] args) {

        launch(args);
    }
}