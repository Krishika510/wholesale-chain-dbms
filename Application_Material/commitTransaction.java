import java.sql.*;
import java.util.Scanner;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.io.Console;

public class commitTransaction {


// Update your user info alone here
private static String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/"; // Using SERVICE_NAME

// Update your user and password info here!

private static String user;
private static String password;

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
ResultSet resultTransaction = null;
ResultSet contains = null;
Integer choice = null;

Scanner input = new Scanner(System.in);
System.out.println("Enter database name:");
user = input.nextLine();
Console console = System.console();
password = new String(console.readPassword("Enter Password:\n"));
jdbcURL = jdbcURL + user;

    try {
        // Get a connection instance from the first driver in the
        // DriverManager list that recognizes the URL jdbcURL
        connection = DriverManager.getConnection(jdbcURL, user, password);
        // Create a statement instance that will be sending
        // your SQL statements to the DBMS
        statement = connection.createStatement();

        do {

        System.out.println("\n\t\tTransaction Operations");
        System.out.println("\t\t--------------------------\n");
        System.out.println("1. Add a transaction\n");
        System.out.println("2. Return a product\n");
        System.out.println("3. Exit menu\n\n");
        
        System.out.println("Which task do you want to perform?");

        choice = input.nextInt();

        switch (choice) {

            case 1: 

                try {
                // Take Transaction ID as input.
                System.out.println("Enter Transaction ID:");
                int transactionIDcommit = input.nextInt();

                //Take Store ID as input.
                System.out.println("Enter Store ID:");
                int storeIDcommit = input.nextInt();

                // Take Staff ID as input.
                System.out.println("Enter Staff ID:");
                int staffID = input.nextInt();

                // Take Customer ID as input.
                System.out.println("Enter CustomerID:");
                int customerID = input.nextInt();

                connection.setAutoCommit(false); //BEGIN TRANSACTION.

                // Insert Transaction details obtained from user.
                String sqlInsert1 = "INSERT INTO Transaction(TransactionID, StoreID, CustomerID, CashierStaffID, PurchaseDate, TotalAmount) VALUES(%d,%d,%d,%d,CURDATE(),0)";
                sqlInsert1 = String.format(sqlInsert1, transactionIDcommit, storeIDcommit, customerID, staffID);

                // Create the CATS table
                int i = statement.executeUpdate(sqlInsert1);

                System.out.format("%d Row(s) inserted in Transaction table for Transaction ID %d registered in Store ID %d by Customer ID %d, scanned by Staff ID %d",
                    i, transactionIDcommit, storeIDcommit, customerID, staffID);
                System.out.println("\nPlease fill in details about the transaction....");

                System.out.println("Enter Number of products in the Transaction");

                // Take number of products from the user to create entries in contains table.
                int numberOfProducts = input.nextInt();

                for(int p =0 ;p< numberOfProducts;p++) {

                    // Take Product ID from user as input.
                    System.out.println("\nEnter ProductID");
                    int productID = input.nextInt();

                    // Take quantity of the product ID as input.
                    System.out.println("Enter quantity of the ProductID: "+productID);
                    int quantity = input.nextInt();

                    String sqlSelect4 = "Select StoreQuantity from productInfo where ProductID = %d AND StoreID = %d";
                    sqlSelect4 = String.format(sqlSelect4, productID, storeIDcommit);

                    ResultSet resultSelect4 = statement.executeQuery(sqlSelect4);

                    while(resultSelect4.next()) {
                        int existingStoreQuantity = resultSelect4.getInt("StoreQuantity");
                        if (quantity > existingStoreQuantity) {
                            System.out.format("Invalid quantity, Store quantity %d lesser than Transaction quantity %d\n", existingStoreQuantity, quantity);
                            return;
                        }

                    }
                    // Insert into contains table.
                    String sqlInsert2 = "INSERT INTO contains (TransactionID, ProductID, ProdSellQty) VALUES (%d,%d,%d)";

                    sqlInsert2 = String.format(sqlInsert2, transactionIDcommit, productID, quantity);

                    int j = statement.executeUpdate(sqlInsert2);
                    System.out.format("%d Row(s) inserted in contains table for Transaction ID %d, Product ID %d, quantity %d.",j, transactionIDcommit, productID, quantity);
                    
                }

                //Get items of a transaction
                String sqlSelect1 = "Select ProductID, ProdSellQty from contains where TransactionID =" + transactionIDcommit;
                resultcontains = statement.executeQuery(sqlSelect1);

                double total = 0;

                while (resultcontains.next()) {
                double discount = 0;
                int p = resultcontains.getInt("ProductID");
                int n = resultcontains.getInt("ProdSellQty");

                // Get product Info of the product in the store.
                String sqlSelect2 = "SELECT SellingPrice, DiscountInfo, SaleStartDate, SaleEndDate, StoreQuantity FROM productInfo WHERE ProductID = %d AND StoreID = %d";
                sqlSelect2 = String.format(sqlSelect2, p, storeIDcommit);

                resultProductInfo = statement.executeQuery(sqlSelect2);
                    while (resultProductInfo.next()) {
                        double price = resultProductInfo.getFloat("SellingPrice");
                        if (resultProductInfo.getDate("SaleStartDate") != null) {
                        java.sql.Date startDate = resultProductInfo.getDate("SaleStartDate");
                        java.sql.Date endDate = resultProductInfo.getDate("SaleEndDate");
                        Date currDate = new Date();

                        if (startDate.compareTo(currDate) <= 0 && endDate.compareTo(currDate) >= 0) {
                            discount = resultProductInfo.getFloat("DiscountInfo");
                        }
                    }
                        total += (1-discount)*price*n;
                    }
                }
                String sqlUpdate = "Update Transaction TotalAmount set TotalAmount = %f where TransactionID = %d";
                sqlUpdate = String.format(sqlUpdate,total,transactionIDcommit);
                statement.executeQuery(sqlUpdate);

                // Subtract quantity from productInfo table.
                resultcontains = statement.executeQuery(sqlSelect1);
                while (resultcontains.next()) {

                    int p = resultcontains.getInt("ProductID");
                    int sellQuantity = resultcontains.getInt("ProdSellQty");
                    System.out.format("\nQuantity sold for Product ID %d = %d\n", p, sellQuantity);

                    String sqlSelect3 = "SELECT StoreQuantity FROM productInfo WHERE ProductID = %d AND StoreID = %d";
                    sqlSelect3 = String.format(sqlSelect3, p, storeIDcommit);

                    resultProductInfo2 = statement.executeQuery(sqlSelect3);
                    while(resultProductInfo2.next()) {

                    int existingQuantity = resultProductInfo2.getInt("StoreQuantity");
                    int remainingQuantity = existingQuantity - sellQuantity;

                    String sqlUpdate1 = "UPDATE productInfo SET StoreQuantity = %d where ProductID = %d and StoreID = %d";
                    sqlUpdate1 = String.format(sqlUpdate1, remainingQuantity, p, storeIDcommit);
                    statement.executeQuery(sqlUpdate1);
        }

}
                connection.commit(); //COMMIT TRANSACTION IF EVERYTHING IS SUCCESSFUL.
                System.out.println("Transaction updated successfully");
                break;

            } catch(SQLException e) {
                System.out.println(e);
                if (connection != null) {
                    try {
                        System.err.print("There was a problem. Transaction is being rolled back");
                        connection.rollback(); //ROLLBACK TRANSACTION IN CASE OF A FAILURE.
                        break;
                    } catch (SQLException excep) {
                        System.out.println(excep);
                        break;
                    }
                }
            }

        case 2:

            connection.setAutoCommit(false); // BEGIN TRANSACTION
            System.out.println("Enter Transaction ID: ");
            int transactionIDreturn = input.nextInt();

            System.out.println("Enter Product ID being returned: ");
            int productID = input.nextInt();

            System.out.println("Enter CashierStaffID: ");
            int cashierStaffID = input.nextInt();

            System.out.println("Enter quantity being returned: ");
            int returnQuantity = input.nextInt();

            String sqlSelectCheck = "SELECT ReturnQuantity, ProdSellQty from contains where TransactionID = %d and ProductID = %d";
            sqlSelectCheck = String.format(sqlSelectCheck, transactionIDreturn, productID);
            ResultSet resultCheck = statement.executeQuery(sqlSelectCheck);
            if(resultCheck.next() == false) {
                System.out.println("Wrong transaction ID/ProductID. Please check again.");
                return;
            }

            while (resultCheck.next()) {
                System.out.println(resultCheck.next());
                    int earlierReturn = resultCheck.getInt("ReturnQuantity");
                    int prodSellQty = resultCheck.getInt("ProdSellQty");

                    if (returnQuantity > prodSellQty - earlierReturn) {
                        System.out.println("Invalid return quantity, please enter return quantity less than or equal to sell quantity.");
                        return;

                }
            }
            String sqlUpdateContains = "UPDATE contains SET ReturnQuantity = %d, ReturnDate = CURDATE() where TransactionID = %d and ProductID = %d";
            sqlUpdateContains = String.format(sqlUpdateContains, returnQuantity, transactionIDreturn, productID);
            statement.executeQuery(sqlUpdateContains);

            String sqlSelectTransaction = "SELECT StoreID, PurchaseDate FROM Transaction WHERE TransactionID = %d";
            sqlSelectTransaction = String.format(sqlSelectTransaction, transactionIDreturn);
            resultTransaction = statement.executeQuery(sqlSelectTransaction);

            while(resultTransaction.next()) {
            int storeIDreturn = resultTransaction.getInt("StoreID");
            java.sql.Date purchaseDate = resultTransaction.getDate("PurchaseDate");
            String sqlSelectproductInfo = "SELECT SaleStartDate, SaleEndDate, DiscountInfo, SellingPrice FROM productInfo where StoreID = %d and ProductID = %d";
            sqlSelectproductInfo = String.format(sqlSelectproductInfo, storeIDreturn, productID);
            ResultSet resultProductInfo3 = statement.executeQuery(sqlSelectproductInfo);
            double discount2 = 0;
            double total2 = 0;

            while (resultProductInfo3.next()) {
            double price = resultProductInfo3.getFloat("SellingPrice");
            if (resultProductInfo3.getDate("SaleStartDate") != null) {
            java.sql.Date startDate = resultProductInfo3.getDate("SaleStartDate");
            java.sql.Date endDate = resultProductInfo3.getDate("SaleEndDate");

            if (startDate.compareTo(purchaseDate) <= 0 && endDate.compareTo(purchaseDate) >= 0) {
                discount2 = resultProductInfo3.getFloat("DiscountInfo");
            }
        }
            total2 += (1-discount2)*price*returnQuantity;
        }

        String sqlUpdateTransaction = "UPDATE Transaction SET TotalAmount = TotalAmount - %f where TransactionID = %d";
        sqlUpdateTransaction = String.format(sqlUpdateTransaction, total2, transactionIDreturn);
        statement.executeQuery(sqlUpdateTransaction);

    }
                connection.commit(); //END TRANSACTION
                System.out.println("Transaction updated successfully");
                break;

}
        } while(!choice.equals(3));

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


                                        
