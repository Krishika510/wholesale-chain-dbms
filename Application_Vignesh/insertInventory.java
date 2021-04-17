import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;

public class insertInventory {


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

        // Take Transaction ID as input.productID
        System.out.println("Enter Product ID:");
        int productID = input.nextInt();

        String sqlselect1 = "Select ProductID from Merchandise where ProductID = " + productID;
        result = statement.executeQuery(sqlselect1);

        while(result.next()){
            int p = result.getInt("ProductID");
            //Take Supplier ID as input.
            System.out.println("Enter Total Quantity:");
            int totalQuantity = input.nextInt();
            String sqlUpdate1 = "UPDATE Merchandise SET TotalQuantity = TotalQuantity + %d WHERE ProductID = %d";
            sqlUpdate1 = String.format(sqlUpdate1, totalQuantity, p);
            statement.executeQuery(sqlUpdate1);
            System.out.println("Table Updated Successfully");
            return;
        }

        //Take Supplier ID as input.
        System.out.println("Enter Supplier ID:");
        int supplierID = input.nextInt();

        // Take Warehouse Staff ID as input.
        System.out.println("Enter Warehouse Staff ID:");
        int warehouseStaffID = input.nextInt();

        input.nextLine();

        // Take Product Name as input.
        System.out.println("Enter Product Name:");
        String productName = input.nextLine();

        // Take Production Date as input.
        System.out.println("Enter Production Date:");
        String productionDate = input.nextLine();

        // Take Expiration Date as input.
        System.out.println("Enter Expiration Date:");
        String expDate = input.nextLine();

        //Take Market Price as input.
        System.out.println("Enter Market Price:");
        float marketPrice = input.nextFloat();

        //Take Buy Price as input.
        System.out.println("Enter Buy Price:");
        float buyPrice = input.nextFloat();

        // Take Total Quantity as input.
        System.out.println("Enter Total Quantity:");
        int totalQuantity = input.nextInt();

        // Create a statement instance that will be sending
        // your SQL statements to the DBMS
        

        // Begin Transaction
        connection.setAutoCommit(false);

        // Insert Transaction details obtained from user.
        String sqlInsert1 = "INSERT INTO Merchandise (ProductID, SupplierID, WarehouseStaffID, ProductName, ProductionDate, ExpirationDate, MarketPrice, BuyPrice, TotalQuantity) VALUES (%d,%d,%d,'%s','%s','%s',%f,%f,%d)";
        sqlInsert1 = String.format(sqlInsert1, productID, supplierID, warehouseStaffID, productName, productionDate, expDate, marketPrice, buyPrice, totalQuantity);

        int i = statement.executeUpdate(sqlInsert1);

        System.out.format("%d Row inserted", i);



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
                                        
