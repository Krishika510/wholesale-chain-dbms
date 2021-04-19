import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.io.Console;

public class inventoryRecords {


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
Console console = System.console();
password = new String(console.readPassword("Enter Password:\n"));
jdbcURL = jdbcURL + user;

    try {

        // Get a connection instance from the first driver in the
        // DriverManager list that recognizes the URL jdbcURL
        connection = DriverManager.getConnection(jdbcURL, user, password);
        statement = connection.createStatement();
        while(true){
        System.out.println("\n\t\tMaintaining Inventory Records");
        System.out.println("\t\t-----------------------------\n");
        System.out.println("1. Create Inventory for newly arrived products\n");
        System.out.println("2. Update Inventory with returns\n");
        System.out.println("3. Manage product transfers between stores in the chain\n");
        System.out.println("4. Exit menu\n\n");
        
        System.out.println("Which task do you want to perform?");
        int task = input.nextInt();
        

        switch(task){
            case 1:
            try {
                connection.setAutoCommit(false); //BEGIN TRANSACTION.

                // Take Product ID as input
                System.out.println("Enter Product ID:");
                int productID = input.nextInt();

                //Take Supplier ID as input.
                System.out.println("Enter Supplier ID:");
                int supplierID = input.nextInt();

                String sqlselect1 = "Select BuyPrice from Merchandise where ProductID = %d and SupplierID = %d";
                sqlselect1 = String.format(sqlselect1,productID, supplierID);
                result = statement.executeQuery(sqlselect1);
                if(result.next()!=false){
                    
                    int price = result.getInt("BuyPrice");
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
                    
                }
                else{
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
                }
                //End Transaction
                connection.commit(); //COMMIT TRANSACTION IF EVERYTHING IS SUCCESSFUL.
                break;
                
            } catch (SQLException e) {
                System.out.println(e);
                if (connection != null) {
                    try {
                        System.err.print("There was a problem. Transaction is being rolled back.");
                        connection.rollback(); // ROLLBACK IN CASE OF A FAILURE.
                        break;
                    } catch (SQLException excep) {
                        System.out.println(excep);
                        break;
                    }
                }
            }
            case 2:
            {
                connection.setAutoCommit(false);

                // Take Product ID as input
                System.out.println("Enter Product ID:");
                int productID = input.nextInt();

                // Take Store ID as input
                System.out.println("Enter Store ID:");
                int storeID = input.nextInt();

                // Take warehouse staff ID as input
                System.out.println("Enter Warehouse Staff ID:");
                int warehouseStaffID = input.nextInt();

                String sqlselect1 = "Select ProductID, StoreID, StoreQuantity from productInfo where ProductID = '%d' AND StoreID = '%d'";
                sqlselect1 = String.format(sqlselect1, productID, storeID);
                result = statement.executeQuery(sqlselect1);

                while(result.next()){
                    int s = result.getInt("StoreID");
                    int p = result.getInt("ProductID");
                    int q = result.getInt("StoreQuantity");
                    // Take Return Quantity as input
                    System.out.println("Enter Return Quantity:");
                    int returnQuantity = input.nextInt();
                    if(q < returnQuantity) {
                        System.out.println("Not enough stock to transfer");
                        return;
                    }
                    String sqlUpdate1 = "UPDATE productInfo SET StoreQuantity = StoreQuantity - %d, ProductDest='Warehouse', ProductSource='NULL' WHERE ProductID = %d AND StoreID = %d";
                    sqlUpdate1 = String.format(sqlUpdate1, returnQuantity, p, s);
                    statement.executeQuery(sqlUpdate1);
                    String sqlUpdate2 = "UPDATE Merchandise SET TotalQuantity = TotalQuantity + %d, WarehouseStaffID = %d WHERE ProductID = %d";
                    sqlUpdate2 = String.format(sqlUpdate2, returnQuantity, warehouseStaffID, p);
                    statement.executeQuery(sqlUpdate2);
                    System.out.println("Table Updated Successfully");
                    
                }

                //End Transaction
                connection.commit();
                break;
            }
            case 3:
            {
                connection.setAutoCommit(false);

                 // Take Product ID as input
                System.out.println("Enter Product ID:");
                int productID = input.nextInt();

                //input.nextLine();

                // Take Store ID as input
                System.out.println("Enter Store ID:");
                int storeID = input.nextInt();

                // Take Warehouse Staff ID as input
                System.out.println("Enter Warehouse Staff ID:");
                int warehouseStaffID = input.nextInt();

                // Take Destination Store ID as input
                System.out.println("Enter Destination Store ID:");
                int storeDestID = input.nextInt();

                // Take Transfer Quantity as input
                System.out.println("Enter Transfer Quantity:");
                int returnQuantity = input.nextInt();

                if (storeID == storeDestID)
                {
                    System.out.println("Source store and destination store cannot be same");
                    return;
                }

                String sqlselect1 = "Select * from productInfo where ProductID = '%d' AND StoreID = '%d'";
                sqlselect1= String.format(sqlselect1, productID, storeID);
                result = statement.executeQuery(sqlselect1);

                String sqlselect3 = "Select * from productInfo where ProductID = '%d' AND StoreID = '%d'";
                sqlselect3= String.format(sqlselect3, productID, storeDestID);
                ResultSet result2 = statement.executeQuery(sqlselect3);
                String salestartdate = "";
                String saleenddate ="";
                double discountinfo=0.0;
                int sellingprice = 0;
                int t =0;
                if(result2.next()!= false){

                    while(result.next()){
                        t = result.getInt("StoreQuantity");
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
                    }
                }else{
                    while(result.next()){
                        t = result.getInt("StoreQuantity");
                        salestartdate = result.getString("SaleStartDate");
                        saleenddate = result.getString("SaleEndDate");
                        discountinfo = result.getFloat("DiscountInfo");
                        sellingprice = result.getInt("SellingPrice");
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
                    
                    String sqlInsert1 = "INSERT INTO productInfo (StoreID, ProductID, WarehouseStaffID, ProductSource, ProductDest, SaleStartDate, SaleEndDate, DiscountInfo, SellingPrice, StoreQuantity) VALUES (%d,%d,%d,%d,%d,'%s','%s',%f,%d,%d)";
                    sqlInsert1 = String.format(sqlInsert1, storeDestID, productID, warehouseStaffID, storeID, storeDestID, salestartdate, saleenddate, discountinfo,sellingprice, returnQuantity );

                    int i = statement.executeUpdate(sqlInsert1);

                    System.out.format("%d Row inserted\n", i);


                }

                //End Transaction
                connection.commit();

                break;
            }
            case 4:
            {
                System.exit(0);
            }
            default:
            {
                System.out.println("Wrong choice.");
            }
        }
    }

    



        

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
                                        
