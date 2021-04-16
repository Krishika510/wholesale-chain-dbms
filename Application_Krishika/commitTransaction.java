import java.sql.*;
import java.util.Scanner;
import java.util.Date;

public class commitTransaction {


// Update your user info alone here
private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/kmshivna"; // Using SERVICE_NAME

// Update your user and password info here!

private static final String user = "kmshivna";
private static final String password = "200368095";

public static void main(String[] args) {
    try {
// Loading the driver. This creates an instance of the driver
// and calls the registerDriver method to make MySql(MariaDB) Thin available to clients.


Class.forName("org.mariadb.jdbc.Driver");

Connection connection = null;
Statement statement = null;
ResultSet resultcontains = null;
ResultSet resultProductInfo = null;
ResultSet resultProductInfo2 = null;

Scanner input = new Scanner(System.in);

    try {
        // Get a connection instance from the first driver in the
        // DriverManager list that recognizes the URL jdbcURL
        connection = DriverManager.getConnection(jdbcURL, user, password);

        // Take Transaction ID as input.
        System.out.println("Enter Transaction ID:");
        int transactionID = input.nextInt();

        //Take Store ID as input.
        System.out.println("Enter Store ID:");
        int storeID = input.nextInt();

        // Take Staff ID as input.
        System.out.println("Enter Staff ID:");
        int staffID = input.nextInt();

        // Take Customer ID as input.
        System.out.println("Enter CustomerID:");
        int customerID = input.nextInt();

        // Create a statement instance that will be sending
        // your SQL statements to the DBMS
        statement = connection.createStatement();

        // Begin Transaction
        connection.setAutoCommit(false);

        // Insert Transaction details obtained from user.
        String sqlInsert1 = "INSERT INTO Transaction(TransactionID, StoreID, CustomerID, CashierStaffID, PurchaseDate, TotalAmount) VALUES(%d,%d,%d,%d,CURDATE(),0)";
        sqlInsert1 = String.format(sqlInsert1, transactionID, storeID, customerID, staffID);

        // Create the CATS table
        int i = statement.executeUpdate(sqlInsert1);

        System.out.format("%d Row(s) inserted in Transaction table for Transaction ID %d registered in Store ID %d by Customer ID %d, scanned by Staff ID %d : ",
            i, transactionID, storeID, customerID, staffID);
        System.out.println("\nPlease fill in details about the transaction....");

        System.out.println("Enter Number of products in the Transaction");

        // Take number of products from the user to create entries in contains table.
        int numberOfProducts = input.nextInt();

        for(int p =0 ;p< numberOfProducts;p++) {

            // Take Product ID from user as input.
            System.out.println("Enter ProductID");
            int productID = input.nextInt();

            // Take quantity of the product ID as input.
            System.out.println("Enter quantity of the ProductID: "+productID);
            int quantity = input.nextInt();

            // Insert into contains table.
            String sqlInsert2 = "INSERT INTO contains (TransactionID, ProductID, ProdSellQty) VALUES (%d,%d,%d)";

            sqlInsert2 = String.format(sqlInsert2, transactionID, productID, quantity);

            int j = statement.executeUpdate(sqlInsert2);
            System.out.format("%d Row(s) inserted in contains table for Transaction ID %d, Product ID %d, quantity %d.",j, transactionID, productID, quantity);
            
        }

        //Get items of a transaction
        String sqlSelect1 = "Select ProductID, ProdSellQty from contains where TransactionID =" + transactionID;
        resultcontains = statement.executeQuery(sqlSelect1);

        double total = 0;
        double discount = 1;

        while (resultcontains.next()) {
        int p = resultcontains.getInt("ProductID");
        int n = resultcontains.getInt("ProdSellQty");

        // Get product Info of the product in the store.
        String sqlSelect2 = "SELECT SellingPrice, DiscountInfo, SaleStartDate, SaleEndDate, StoreQuantity FROM productInfo WHERE ProductID = %d AND StoreID = %d";
        sqlSelect2 = String.format(sqlSelect2, p, storeID);

        resultProductInfo = statement.executeQuery(sqlSelect2);
            while (resultProductInfo.next()) {
                double price = resultProductInfo.getFloat("SellingPrice");
                java.sql.Date startDate = resultProductInfo.getDate("SaleStartDate");
                java.sql.Date endDate = resultProductInfo.getDate("SaleEndDate");
                Date currDate = new Date();

                if (startDate.compareTo(currDate) < 0 && endDate.compareTo(currDate) > 0) {
                    discount = resultProductInfo.getFloat("DiscountInfo");
                }
                total += discount*price*n;
            }
        }
        String sqlUpdate = "Update Transaction TotalAmount set TotalAmount = %f where TransactionID = %d";
        sqlUpdate = String.format(sqlUpdate,total,transactionID);
        statement.executeQuery(sqlUpdate);

        // Subtract quantity from productInfo table.
        resultcontains = statement.executeQuery(sqlSelect1);
        while (resultcontains.next()) {

            int p = resultcontains.getInt("ProductID");
            int sellQuantity = resultcontains.getInt("ProdSellQty");
            System.out.format("Quantity sold for Product ID %d = %d", p, sellQuantity);

            String sqlSelect3 = "SELECT StoreQuantity FROM productInfo WHERE ProductID = %d AND StoreID = %d";
            sqlSelect3 = String.format(sqlSelect3, p, storeID);

            resultProductInfo2 = statement.executeQuery(sqlSelect3);
            while(resultProductInfo2.next()) {

            int existingQuantity = resultProductInfo2.getInt("StoreQuantity");
            int remainingQuantity = existingQuantity - sellQuantity;

            String sqlUpdate1 = "UPDATE productInfo SET StoreQuantity = %d where ProductID = %d and StoreID = %d";
            sqlUpdate1 = String.format(sqlUpdate1, remainingQuantity, p, storeID);
            statement.executeQuery(sqlUpdate1);
        }
        }

        //End Transaction
        connection.commit();

} finally {
    close(resultcontains);
    close(resultProductInfo);
    close(statement);
    close(connection);
    }
} catch(Throwable oops) {
            oops.printStackTrace();
        }
}
static void close(Connection connection) {
        if(connection != null) {
            try {
            connection.close();
            } catch(Throwable whatever) {}
        }
    }
    static void close(Statement statement) {
        if(statement != null) {
            try {
            statement.close();
            } catch(Throwable whatever) {}
        }
    }
    static void close(ResultSet result) {
        if(result != null) {
            try {
            result.close();
            } catch(Throwable whatever) {}
        }
    }
}


                                        
