package org.example;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Database {
    private Connection con = null;

    // Constructor to initialize the database connection
    public Database() {
        try {
            // Load the MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Connect to the database
            con = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3308/test", "root", "");

            System.out.println("Database connection established successfully.");
        } catch (ClassNotFoundException e) {
            System.out.println("JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("SQLException: " + e.getMessage());
            System.out.println("SQLState: " + e.getSQLState());
            System.out.println("VendorError: " + e.getErrorCode());
        }
    }

    // Method to insert a new medicine into the database
    public void insertMedicine(String id, String name, String company, int quantity, double price) {
        // Check if the connection is null
        if (con == null) {
            System.out.println("Database connection is not initialized.");
            return;
        }

        String query = "INSERT INTO medicines (id, name, company, quantity, price) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, id);
            pstmt.setString(2, name);
            pstmt.setString(3, company);
            pstmt.setInt(4, quantity);
            pstmt.setDouble(5, price);

            int rowsInserted = pstmt.executeUpdate();
            if (rowsInserted > 0) {
                System.out.println("Medicine saved successfully!");
            }
        } catch (SQLException e) {
            System.out.println("Error inserting medicine: " + e.getMessage());
        }
    }


    // Method to fetch all medicines from the database
    public List<Map<String, Object>> getMedicines() {
        List<Map<String, Object>> medicines = new ArrayList<>();
        if (con == null) {
            System.out.println("Database connection is not initialized.");
            return medicines;
        }

        String query = "SELECT id, name, company, quantity, price FROM medicines";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Map<String, Object> row = new HashMap<>();
                row.put("id", rs.getString("id"));
                row.put("name", rs.getString("name"));
                row.put("company", rs.getString("company"));
                row.put("quantity", rs.getInt("quantity"));
                row.put("price", rs.getDouble("price"));

                medicines.add(row);
            }
        } catch (SQLException e) {
            System.out.println("Error fetching medicines: " + e.getMessage());
            e.printStackTrace();
        }
        return medicines;
    }

    // Method to delete medicament
    public void deleteMedicine(String id) {
        if (con == null) {
            System.out.println("Database connection is not initialized.");
            return;
        }

        String query = "DELETE FROM medicines WHERE id = ?";
        try (PreparedStatement pstmt = con.prepareStatement(query)) {
            pstmt.setString(1, id);

            int rowsDeleted = pstmt.executeUpdate();
            if (rowsDeleted > 0) {
                System.out.println("Medicine deleted successfully!");
            } else {
                System.out.println("No medicine found with ID: " + id);
            }
        } catch (SQLException e) {
            System.out.println("Error deleting medicine: " + e.getMessage());
        }
    }



    // Method to close the database connection
    public void closeConnection() {
        try {
            if (con != null) {
                con.close();
                System.out.println("Database connection closed.");
            }
        } catch (SQLException e) {
            System.out.println("Error closing connection: " + e.getMessage());
        }
    }


}
