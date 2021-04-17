import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;

public class insertInventory {


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
ResultSet result = null;

Scanner input = new Scanner(System.in);
System.out.println("Enter database name:");
user = input.nextLine();
System.out.println("Enter password:");
password = input.nextLine();
jdbcURL = jdbcURL + user;

    try {
        // Get a connection instance from the first driver in the
        // DriverManager list that recognizes the URL jdbcURL
        connection = DriverManager.getConnection(jdbcURL, user, password);
        statement = connection.createStatement();
        // Begin Transaction
        connection.setAutoCommit(false);
        // Take Transaction ID as input.productID
        System.out.println("Enter Product ID:");
        int productID = input.nextInt();

         //Take Supplier ID as input.
        System.out.println("Enter Supplier ID:");
        int supplierID = input.nextInt();

        String sqlselect1 = "Select BuyPrice from Merchandise where ProductID = %d and SupplierID = %d";
        sqlselect1 = String.format(sqlselect1,productID, supplierID);
        result = statement.executeQuery(sqlselect1);

        while(result.next()){
            int price = result.getInt("BuyPrice");
            //Take Supplier ID as input.
            System.out.println("Enter Total Quantity:");
            int totalQuantity = input.nextInt();

            String sqlUpdate1 = "UPDATE Merchandise SET TotalQuantity = TotalQuantity + %d WHERE ProductID = %d and SupplierID = %d";
            sqlUpdate1 = String.format(sqlUpdate1, totalQuantity, productID,supplierID);
            statement.executeQuery(sqlUpdate1);

            String sqlselect2 = "Select SuppliedQuantity, IsBilled from generateBills WHERE ProductID = %d and SupplierID = %d";
            sqlselect2 = String.format(sqlselect2, productID,supplierID);
            ResultSet result2 = statement.executeQuery(sqlselect2);

            while(result2.next()){ 
                int initialQty = result2.getInt("SuppliedQuantity");
                boolean isB = result2.getBoolean("IsBilled");
                String sqlUpdate2;
                if(isB){
                    sqlUpdate2 = "UPDATE generateBills SET SuppliedQuantity = %d, Amount = %d, IsBilled = FALSE WHERE ProductID = %d and SupplierID = %d";
                    sqlUpdate2 = String.format(sqlUpdate2, totalQuantity,totalQuantity*price,productID,supplierID);
                }else{
                    sqlUpdate2 = "UPDATE generateBills SET SuppliedQuantity = SuppliedQuantity +  %d , Amount = Amount + %d WHERE ProductID = %d and SupplierID = %d";
                    sqlUpdate2 = String.format(sqlUpdate2, totalQuantity,totalQuantity*price, productID,supplierID);
                }
                statement.executeQuery(sqlUpdate2);
            }
            System.out.println("Table Updated Successfully");
            connection.commit();
            return;
        }

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

        // Insert Transaction details obtained from user.
        String sqlInsert1 = "INSERT INTO Merchandise (ProductID, SupplierID, WarehouseStaffID, ProductName, ProductionDate, ExpirationDate, MarketPrice, BuyPrice, TotalQuantity) VALUES (%d,%d,%d,'%s','%s','%s',%f,%f,%d)";
        sqlInsert1 = String.format(sqlInsert1, productID, supplierID, warehouseStaffID, productName, productionDate, expDate, marketPrice, buyPrice, totalQuantity);
        int i = statement.executeUpdate(sqlInsert1);

        System.out.format("%d Row inserted into Merchandise", i);

        String sqlInsert2 = "INSERT INTO generateBills ( ProductID,SupplierID,SuppliedQuantity,Amount,IsBilled) values(%d,%d,%d,%f,FALSE)";
        sqlInsert2 = String.format(sqlInsert2, productID, supplierID,totalQuantity,buyPrice*totalQuantity);
        int j = statement.executeUpdate(sqlInsert2);
        System.out.format("%d Row inserted into generateBills", j);

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
                                        
