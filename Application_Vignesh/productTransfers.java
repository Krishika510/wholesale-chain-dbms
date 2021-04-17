import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;

public class productTransfers {


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

        //input.nextLine();

        // Take Store ID as input
        System.out.println("Enter Store ID:");
        int storeID = input.nextInt();

        // Take Store ID as input
        System.out.println("Enter Warehouse Staff ID:");
        int warehouseStaffID = input.nextInt();

        // Take Store ID as input
        System.out.println("Enter Destination Store ID:");
        int storeDestID = input.nextInt();

        // Take Return Quantity as input
        System.out.println("Enter Transfer Quantity:");
        int returnQuantity = input.nextInt();

        String sqlselect1 = "Select * from productInfo where ProductID = '%d' AND StoreID = '%d'";
        sqlselect1= String.format(sqlselect1, productID, storeID);
        result = statement.executeQuery(sqlselect1);

        String sqlselect3 = "Select * from productInfo where ProductID = '%d' AND StoreID = '%d'";
        sqlselect3= String.format(sqlselect3, productID, storeDestID);
        ResultSet result2 = statement.executeQuery(sqlselect3);
        
        if(result2.next()!= false){

            while(result.next()){
                int t = result.getInt("StoreQuantity");
                if(t < returnQuantity) {
                    System.out.println("Not enough stock to transfer");
                    return;
                }
                else{
                String sqlUpdate1 = "UPDATE productInfo SET StoreQuantity = StoreQuantity - %d, ProductDest='%d', ProductSource='NULL' WHERE ProductID = '%d' AND StoreID = '%d'";
                sqlUpdate1 = String.format(sqlUpdate1, returnQuantity, storeDestID, productID, storeID);
                statement.executeQuery(sqlUpdate1);
                }
            }

            String sqlselect2 = "Select * from productInfo where ProductID = '%d' AND StoreID = '%d'";
            sqlselect2= String.format(sqlselect2, productID, storeDestID);
            result = statement.executeQuery(sqlselect2);

            while(result.next()){
                String sqlUpdate2 = "UPDATE productInfo SET StoreQuantity = StoreQuantity + %d, ProductSource='%d', ProductDest='%d' WHERE ProductID = '%d' AND StoreID = '%d'";
                sqlUpdate2 = String.format(sqlUpdate2, returnQuantity, storeID, storeDestID, productID, storeDestID);
                statement.executeQuery(sqlUpdate2);
                System.out.println("Table Updated Successfully");
                return;
            }
        }else{
            while(result.next()){
                int t = result.getInt("StoreQuantity");
                if(t < returnQuantity) {
                    System.out.println("Not enough stock to transfer");
                    return;
                }
                else{
                String sqlUpdate1 = "UPDATE productInfo SET StoreQuantity = StoreQuantity - %d, ProductDest='%d', ProductSource='NULL' WHERE ProductID = '%d' AND StoreID = '%d'";
                sqlUpdate1 = String.format(sqlUpdate1, returnQuantity, storeDestID, productID, storeID);
                statement.executeQuery(sqlUpdate1);
                }
            }
            String sqlInsert1 = "INSERT INTO productInfo (StoreID, ProductID, WarehouseStaffID, ProductSource, ProductDest, SaleStartDate, SaleEndDate, DiscountInfo, SellingPrice, StoreQuantity) VALUES (%d,%d,%d,%d,%d,'2020-08-07','2020-10-09','NULL',10,%d)";
            sqlInsert1 = String.format(sqlInsert1, storeDestID, productID, warehouseStaffID, storeID, storeDestID, returnQuantity );

            int i = statement.executeUpdate(sqlInsert1);

            System.out.format("%d Row inserted", i);
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
                                        
