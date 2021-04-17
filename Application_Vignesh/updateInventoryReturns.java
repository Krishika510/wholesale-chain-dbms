import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;

public class updateInventoryReturns {


// Update your user info alone here
private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/vksabesa"; // Using SERVICE_NAME

// Update your user and password info here!

private static final String user = "vksabesa";
private static final String password = "Qwerty@123";

public static void main(String[] args) {
try {
// Loading the driver. This creates an instance of the driver
// and calls the registerDriver method to make MySql(MariaDB) Thin available to clients.


Class.forName("org.mariadb.jdbc.Driver");

Connection connection = null;
Statement statement = null;
ResultSet result = null;

Scanner input = new Scanner(System.in);

    try {
        // Get a connection instance from the first driver in the
        // DriverManager list that recognizes the URL jdbcURL
        connection = DriverManager.getConnection(jdbcURL, user, password);
        statement = connection.createStatement();

        // Take Product ID as input
        System.out.println("Enter Product ID:");
        int productID = input.nextInt();

        // Take Store ID as input
        System.out.println("Enter Store ID:");
        int storeID = input.nextInt();

        // Take Store ID as input
        System.out.println("Enter Warehouse Staff ID:");
        int warehouseStaffID = input.nextInt();

        String sqlselect1 = "Select ProductID, StoreID from productInfo where ProductID = '%d' AND StoreID = '%d'";
        sqlselect1 = String.format(sqlselect1, productID, storeID);
        result = statement.executeQuery(sqlselect1);

        while(result.next()){
            int s = result.getInt("StoreID");
            int p = result.getInt("ProductID");
            // Take Return Quantity as input
            System.out.println("Enter Return Quantity:");
            int returnQuantity = input.nextInt();
            String sqlUpdate1 = "UPDATE productInfo SET StoreQuantity = StoreQuantity - %d, ProductDest='Warehouse', ProductSource='NULL' WHERE ProductID = %d AND StoreID = %d";
            sqlUpdate1 = String.format(sqlUpdate1, returnQuantity, p, s);
            statement.executeQuery(sqlUpdate1);
            String sqlUpdate2 = "UPDATE Merchandise SET TotalQuantity = TotalQuantity + %d, WarehouseStaffID = %d WHERE ProductID = %d";
            sqlUpdate2 = String.format(sqlUpdate2, returnQuantity, warehouseStaffID, p);
            statement.executeQuery(sqlUpdate2);
            System.out.println("Table Updated Successfully");
            return;
        }
        System.out.println("Invalid Store ID or Product ID");

        //End Transaction
        connection.commit();

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
                                        
