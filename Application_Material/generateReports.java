import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.io.Console;

public class generateReports {


// Initiating jdbc URL.
private static String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/"; // Using SERVICE_NAME

// Declaring variables for collecting database user id and .
private static String user;
private static String password;

public static void main(String[] args) {
try {

// Loading the driver. This creates an instance of the driver
// and calls the registerDriver method to make MySql(MariaDB) Thin available to clients.
Class.forName("org.mariadb.jdbc.Driver");

// Connection interface is used to create statement.
Connection connection = null;
// Used to implement simple SQL statements with no parameters.
Statement statement = null;
// ResultSet interface represents the result set of a database query.
// A ResultSet object maintains a cursor that points to the current row in the result set
ResultSet result = null;
// Creates a new Scanner instance which points to the input stream passed as argument.
Scanner input = new Scanner(System.in);
// Asks user to input database name.
System.out.println("Enter database name:");
user = input.nextLine();
// The Java Console class is be used to get input from console.
Console console = System.console();
// Asks user to input password to establish connection.
password = new String(console.readPassword("Enter Password:\n"));
jdbcURL = jdbcURL + user;

    try {
        // Get a connection instance from the first driver in the
        // DriverManager list that recognizes the URL jdbcURL
        connection = DriverManager.getConnection(jdbcURL, user, password);
        statement = connection.createStatement();
        while(true){
        // Prints a Menu for user to select the task to be performed.
        System.out.println("\n\t\tGenerate Reports");
        System.out.println("\t\t-----------------\n");
        System.out.println("1. Total Sales report by Day\n");
        System.out.println("2. Total Sales report by Month\n");
        System.out.println("3. Total Sales report by Year\n");
        System.out.println("4. Sales Growth report for a specific store for a given time period\n");
        System.out.println("5. Merchandise Stock report for each store\n");
        System.out.println("6. Merchandise Stock report for a certain product\n");
        System.out.println("7. Customer Growth report by Month\n");
        System.out.println("8. Customer Growth report by Year\n");
        System.out.println("9. Customer Activity report such as Total Purchase Amount for a given time period\n");
        System.out.println("10. Exit menu\n\n");
        
        // Asks user what task to perform.
        System.out.println("Which task do you want to perform?");
        int task = input.nextInt();
        
        switch(task){

            // Case 1. Total Sales report by Day
            case 1: {
                input.nextLine();
                
                // Takes Purchase date as input.
                System.out.println("Enter Purchase date (YYYY-MM-DD): ");
                String purchaseDate = input.nextLine();

                // SQL query that plugs in user inputted data to generate Total Sales report by Day report. 
                String sqlSelect = "SELECT SUM(TotalAmount) AS Sales_by_Day FROM Transaction WHERE PurchaseDate =" + "'"+ purchaseDate+"'";
                result = statement.executeQuery(sqlSelect);

                while(result.next()) {             
                    int salesByDay = result.getInt("Sales_by_Day");
                    boolean bool = result.wasNull();

                    // Boolean variable to check if result of query is NULL.
                    if (bool == true) {
                        System.out.println("No results found.");
                    } else{
                    // Displays report after required computation.
                    System.out.format("Generated report for sales by day is: %d ", salesByDay);
                    }

                    // To improve look and feel, this line helps the user hit enter when done before printing Menu again
                    input.nextLine();
                                }
                
                //End Transaction.
                break;
            }

            // Case 2. Total Sales report by Month
            case 2: {
                input.nextLine();
               
                // Takes Report Year as input.
                System.out.println("Enter Report Year (YYYY): ");
                String report_year = input.nextLine();
                
                // Takes Report Month as input.
                System.out.println("Enter Report Month (MM): ");
                String report_month = input.nextLine();

                // SQL query that plugs in user inputted data to generate Total Sales report by Month report. 
                String sqlSelect = "SELECT SUM(TotalAmount) AS Sales_by_Month FROM Transaction WHERE MONTH(PurchaseDate) ="  + "'"+ report_month  + "'"+ "AND YEAR(PurchaseDate) ="  + "'"+report_year + "'";
                result = statement.executeQuery(sqlSelect);

                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {
                    int salesByMonth = result.getInt("Sales_by_Month");
                    boolean bool = result.wasNull();

                    // Boolean variable to check if result of query is NULL.
                    if (bool == true) {
                        System.out.println("No results found.");
                        } else{
                    // Displays report after required computation.    
                    System.out.format("Generated report of sales by month for selection is : %d", salesByMonth);
                        }
                        // To improve look and feel, this line helps the user hit enter when done before printing Menu again
                        input.nextLine();
                        }
                    }

                    //End Transaction
                    break;
            }

            //Case 3. Total Sales report by Year
            case 3: {
                input.nextLine();

                // Takes Report Year as input.
                System.out.println("Enter Report Year (YYYY): ");
                String report_year = input.nextLine();

                // SQL query that plugs in user inputted data to generate Total Sales report by Year report.
                String sqlSelect = "SELECT SUM(TotalAmount) AS Sales_by_Year FROM Transaction WHERE YEAR(PurchaseDate) =" + "'"+ report_year+"'";
                result = statement.executeQuery(sqlSelect);
               
                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {

                    // Boolean variable to check if result of query is NULL.
                    int salesByMonth = result.getInt("Sales_by_Year");
                    boolean bool = result.wasNull();
                    if (bool == true) {
                        System.out.println("No results found.");
                        } else{
                            // Displays report after required computation.
                            System.out.format("Generated report of sales by month for selection is : %d", salesByMonth);
                            }
                        input.nextLine();
                        }
                    }
                
                //End Transaction.
                break;
            }  

            //Case 4. Sales Growth report for a specific store for a given time period.
            case 4: {
                input.nextLine();

                // Takes Report Period as input.
                System.out.println("Enter Report Period (YYYY): ");
                String report_year = input.nextLine();

                // SQL query that plugs in user inputted data to generate Sales Growth report for a specific store for a given time period.
                String sqlSelect = "SELECT StoreID, SUM(TotalAmount) AS Total_Sales FROM Transaction WHERE YEAR(PurchaseDate) =" + "'"+ report_year+"' GROUP BY StoreID";
                ResultSet rs = statement.executeQuery(sqlSelect);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                if(!rs.next()){
                    System.out.println("No results found.");
                } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                     // Writing a loop to display results as columns
                     for (int i = 1; i <= columnsNumber; i++) {
                         if (i > 1) System.out.print(",  ");
                         String columnValue = rs.getString(i);
                         System.out.print(columnValue + " " + rsmd.getColumnName(i));
                        }
                        System.out.println("");
                        }
                    }

                //End Transaction
                break;
            }  

             //Case 5. Merchandise Stock report for each store.
            case 5: {
                input.nextLine();

                // Takes Store ID as input.
                System.out.println("Enter Store ID: ");
                String store_id = input.nextLine();

                // SQL query that plugs in user inputted data to generateMerchandise Stock report for each store.
                String sqlSelect = "SELECT p.StoreID, p.ProductID, m.ProductName, p.StoreQuantity FROM productInfo p, Merchandise m WHERE m.ProductID = p.ProductID AND p.StoreID =" + store_id;
                
                ResultSet rs = statement.executeQuery(sqlSelect);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();
                if(!rs.next()){
                    System.out.println("No results found.");
                } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                         if (i > 1) System.out.print(",  ");
                         String columnValue = rs.getString(i);
                         System.out.print(columnValue + " " + rsmd.getColumnName(i));
                        }
                        System.out.println("");
                        }
                    input.nextLine();
                    }
                //End Transaction
                break;
            }   

            // Case 6. Merchandise Stock report for a certain product
            case 6: {
                input.nextLine();

                //Take Product ID as input.
                System.out.println("Enter Product ID: ");
                String prod_id = input.nextLine();

                // SQL query that plugs in user inputted data to generate Merchandise Stock report for each product.
                String sqlSelect = "SELECT p.StoreID, p.ProductID, m.ProductName, p.StoreQuantity FROM productInfo p, Merchandise m WHERE m.ProductID = p.ProductID AND p.ProductID =" + prod_id;
                
                ResultSet rs = statement.executeQuery(sqlSelect);
                ResultSetMetaData rsmd = rs.getMetaData();
                int columnsNumber = rsmd.getColumnCount();

                if(!rs.next()){
                    System.out.println("No results found.");
                } else {
                    rs.beforeFirst();
                    while (rs.next()) {
                    for (int i = 1; i <= columnsNumber; i++) {
                         if (i > 1) System.out.print(",  ");
                         String columnValue = rs.getString(i);
                         System.out.print(columnValue + " " + rsmd.getColumnName(i));
                        }
                        System.out.println("");
                        }
                    input.nextLine();
                    }

                //End Transaction
                break;
            }   

            // 7. Customer Growth report by Month.
            case 7: {
                input.nextLine();
                
                // Take Year as input.
                System.out.println("Enter Growth Year (YYYY): ");
                String report_year = input.nextLine();
                
                // Take Month as input.
                System.out.println("Enter Growth Month (MM): ");
                String report_month = input.nextLine();

                // SQL query that plugs in user inputted data to generate Customer Growth report by Month.
                String sqlSelect = " SELECT COUNT(*) AS Number_of_enrolled_customers FROM SignUp WHERE YEAR(SignUpDate) ="  + "'"+report_year + "'" +" AND MONTH(SignUpDate) =" + "'"+ report_month + "'";
                result = statement.executeQuery(sqlSelect);
                
                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {

                    // Storing result in a variable.
                    int growthByMonth = result.getInt("Number_of_enrolled_customers");

                    // Boolean variable to check if result of query is NULL.
                    boolean bool = result.wasNull();
                    if (bool == true) {
                        System.out.println("No results found.");
                        } else{
                        // Displays report after required computation.
                        System.out.format("Generated report of Customer Growth by month for selection is : %d", growthByMonth);
                        }
                        input.nextLine();
                        }
                    }
              
                //End Transaction
                break;
            }     

            // Case 8. Customer Growth report by Year.
            case 8: {
                input.nextLine();
                
                //  Take input Year.
                System.out.println("Enter Growth Year (YYYY): ");
                String report_year = input.nextLine();

                // SQL query that plugs in user inputted data to generate Customer Growth report by Year.
                String sqlSelect = " SELECT COUNT(*) AS Number_of_enrolled_customers FROM SignUp WHERE YEAR(SignUpDate) ="  + "'"+report_year + "'";
                result = statement.executeQuery(sqlSelect);
                

                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {
                    int growthByYear = result.getInt("Number_of_enrolled_customers");

                    // Boolean variable to check if result of query is NULL.
                    boolean bool = result.wasNull();
                    if (bool == true) {
                        System.out.println("No results found.");
                        } else{
                        // Displays report after required computation.
                        System.out.format("Generated report of Customer Growth by year for selection is : %d", growthByYear);
                        }
                        input.nextLine();
                        } 
                    }
                
                    //End Transaction
                    break;
            }   

            // Case 9. Customer Activity report such as Total Purchase Amount for a given time period 
            case 9: {
                input.nextLine();
                

                // Take input Customer ID.
                System.out.println("Enter Customer ID: ");
                String cust_id = input.nextLine();

                // Take input Year.
                System.out.println("Enter Growth Year (YYYY): ");
                String report_year = input.nextLine();

                 // SQL query that plugs in user inputted data to generate Customer Activity report such as Total Purchase Amount for a given time period.
                String sqlSelect = " SELECT CustomerID, Year(PurchaseDate), SUM(TotalAmount) AS Total_transaction FROM Transaction WHERE CustomerID =" + "'" + cust_id + "'" + "AND YEAR(PurchaseDate) = " + "'" + report_year + "'";
                result = statement.executeQuery(sqlSelect);

                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {
                    int growthByMonth = result.getInt("Total_transaction");

                    // Boolean variable to check if result of query is NULL.
                    boolean bool = result.wasNull();
                    if (bool == true) {
                        System.out.println("No results found.");
                    } else{
                    // Displays report after required computation.
                    System.out.format("Generated report of Customer Growth by month for selection is : %d", growthByMonth);
                    }
                    input.nextLine();
                } 

                }
              
                //End Transaction
                break;
            }    

            // Case: Exit Menu
            case 10:
            {
                // Displays exit message
                System.out.println("Bye!");
                System.exit(0);
            }

            // default case
            default:
            {
                System.out.println("Wrong choice.");
            }
        }
    } 

        
} finally {
    close(rsmd);
    close(rs);
    close(result);
    close(statement);
    close(connection);
    }
    }
    catch(Throwable oops) {
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
    static void close(ResultSetMetaData result) {
        if(result != null) {
            try {
            result.close();
            } catch(Throwable whatever) {}
        }
    }
    }