import java.sql.*;
import java.util.Scanner;

public class WholeSaleStore {


// Update your user info alone here
private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/avijaya6"; // Using SERVICE_NAME

// Update your user and password info here!

private static final String user = "avijaya6";
private static final String password = "200343796";

public static void main(String[] args) {
try {
// Loading the driver. This creates an instance of the driver
// and calls the registerDriver method to make MySql(MariaDB) Thin available to clients.


Class.forName("org.mariadb.jdbc.Driver");

Connection connection = null;
Statement statement = null;
ResultSet result = null;

Scanner input = new Scanner(System.in);

Class.forName("org.mariadb.jdbc.Driver");


try {
    // Get a connection instance from the first driver in the
    // DriverManager list that recognizes the URL jdbcURL
    connection = DriverManager.getConnection(jdbcURL, user, password);
    System.out.println("Enter Transaction ID");
    int transactionID = input.nextInt();
    System.out.println("Enter Store ID: ");
    int storeID = input.nextInt();
    System.out.println("Enter Staff ID");
    int staffID = input.nextInt();
    System.out.println("Enter CustomerID");
    int customerID = input.nextInt();
    // Create a statement instance that will be sending
    // your SQL statements to the DBMS
    statement = connection.createStatement();
    String sqlInsert1 = "INSERT INTO Transaction(TransactionID, StoreID, CustomerID,CashierStaffID,PurchaseDate, TotalAmount) VALUES(%d,%d,%d,%d,CURDATE(),0)";
    sqlInsert1 = String.format(sqlInsert1, transactionID, storeID, customerID,staffID);
    // Create the CATS table
    statement.executeUpdate(sqlInsert1);
    System.out.println("Enter Number of product in the Transaction");
    int numberOfProducts = input.nextInt();
    for(int i =0 ;i< numberOfProducts;i++){
         System.out.println("Enter ProductID");
        int productID = input.nextInt();
        System.out.println("Enter number of Items");
        int quantity = input.nextInt();
        String sqlInsert2 = "INSERT INTO contains (TransactionID, ProductID, ProdSellQty) VALUES (%d,%d,%d)";
        sqlInsert2 = String.format(sqlInsert2, transactionID, productID, quantity);
        statement.executeUpdate(sqlInsert2);
    }
    String sqlSelect1 = "Select ProductID, ProdSellQty from contains where TransactionID =" + transactionID;
    result = statement.executeQuery(sqlSelect1);
    double total = 0;
    while (result.next()) {
    int p = result.getInt ("ProductID");
    int n = result.getInt("ProdSellQty");
    String sqlSelect2 = "select SellingPrice from productInfo where ProductID ="+p;
    result = statement.executeQuery(sqlSelect2);
        while (result.next()) {
            double price = result.getFloat("SellingPrice");
            total += price*n;
        }
    }
    String sqlUpdate = "Update Transaction TotalAmount set TotalAmount = %f where TransactionID = %d";
    sqlUpdate = String.format(sqlUpdate,total,transactionID);
    statement.executeQuery(sqlUpdate);

} finally {
    close(result);
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
                                        
