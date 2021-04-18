import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.io.Console;

public class generateReports {


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
        System.out.println("\n\t\tGenerate Reports");
        System.out.println("\t\t-----------------\n");
        System.out.println("1. Total Sales report by Day\n");
        System.out.println("2. Total Sales report by Month\n");
        System.out.println("3. Total Sales report by Year\n");
        System.out.println("4. Sales Growth report for a specific store for a given time period\n");
        System.out.println("5. Merchandise Stock report for each store or for a certain product\n");
        System.out.println("6. Customer Growth report by Month\n");
        System.out.println("7. Customer Growth report by Year\n");
        System.out.println("8. Customer Activity report such as Total Purchase Amount for a given time period\n");
        System.out.println("9. Exit menu\n\n");
        
        System.out.println("Which task do you want to perform?");
        int task = input.nextInt();
        

        switch(task){
            case 1: {
                input.nextLine();
                System.out.println("Enter Purchase date (YYYY-MM-DD): ");
                String purchaseDate = input.nextLine();

                String sqlSelect = "SELECT SUM(TotalAmount) AS Sales_by_Day FROM Transaction WHERE PurchaseDate =" + "'"+ purchaseDate+"'";
                result = statement.executeQuery(sqlSelect);

                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {
                    int salesByDay = result.getInt("Sales_by_Day");
                    System.out.format("Generated report for sales by day is: %d ", salesByDay);
                    input.nextLine();
                }

                }


                //End Transaction
                break;
            }
            case 2: {
                input.nextLine();
               
                System.out.println("Enter Report Year (YYYY): ");
                String report_year = input.nextLine();
                
                System.out.println("Enter Report Month (MM): ");
                String report_month = input.nextLine();

                String sqlSelect = "SELECT SUM(TotalAmount) AS Sales_by_Month FROM Transaction WHERE MONTH(PurchaseDate) ="  + "'"+ report_month  + "'"+ "AND YEAR(PurchaseDate) ="  + "'"+report_year + "'";
                result = statement.executeQuery(sqlSelect);

                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {
                    int salesByMonth = result.getInt("Sales_by_Month");
                    System.out.format("Generated report of sales by month for selection is : %d", salesByMonth);
                    input.nextLine();
                }

                }


                //End Transaction
                break;
            }
            case 3: {
                input.nextLine();
               
                System.out.println("Enter Report Year (YYYY): ");
                String report_year = input.nextLine();

                String sqlSelect = "SELECT SUM(TotalAmount) AS Sales_by_Year FROM Transaction WHERE YEAR(PurchaseDate) =" + "'"+ report_year+"'";
                result = statement.executeQuery(sqlSelect);
               
                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {
                    int salesByMonth = result.getInt("Sales_by_Year");
                    System.out.format("Generated report of sales by month for selection is : %d", salesByMonth);
                    input.nextLine();
                }

                }
                

                //End Transaction
                break;
            }  
            case 4: {
                input.nextLine();
                System.out.println("Enter Report Period (YYYY): ");
                String report_year = input.nextLine();

                String sqlSelect = "SELECT StoreID, SUM(TotalAmount) AS Total_Sales FROM Transaction WHERE YEAR(PurchaseDate) =" + "'"+ report_year+"' GROUP BY StoreID";
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

                }






                 

                //End Transaction
                break;
            }   
            case 5: {
                input.nextLine();
                System.out.println("Enter Product ID: ");
                String prod_id = input.nextLine();


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

                }




                //End Transaction
                break;
            }   
            case 6: {
                input.nextLine();
                
                System.out.println("Enter Growth Year (YYYY): ");
                String report_year = input.nextLine();
                
                System.out.println("Enter Growth Month (MM): ");
                String report_month = input.nextLine();

                String sqlSelect = " SELECT COUNT(*) AS Number_of_enrolled_customers FROM SignUp WHERE YEAR(SignUpDate) ="  + "'"+report_year + "'" +" AND MONTH(SignUpDate) =" + "'"+ report_month + "'";
                result = statement.executeQuery(sqlSelect);
                
                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {
                    int growthByMonth = result.getInt("Number_of_enrolled_customers");
                    System.out.format("Generated report of Customer Growth by month for selection is : %d", growthByMonth);
                    input.nextLine();
                }

                }
              
                


                //End Transaction
                break;
            }     
            case 7: {
                input.nextLine();
                
                System.out.println("Enter Growth Year (YYYY): ");
                String report_year = input.nextLine();

                String sqlSelect = " SELECT COUNT(*) AS Number_of_enrolled_customers FROM SignUp WHERE YEAR(SignUpDate) ="  + "'"+report_year + "'";
                result = statement.executeQuery(sqlSelect);
                

                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {
                    int growthByYear = result.getInt("Number_of_enrolled_customers");
                    System.out.format("Generated report of Customer Growth by year for selection is : %d", growthByYear);
                    input.nextLine();
                } 

                }
                


                //End Transaction
                break;
            }     
            case 8: {
                input.nextLine();
                
                System.out.println("Enter CustomerID: ");
                String cust_id = input.nextLine();

                System.out.println("Enter Growth Year (YYYY): ");
                String report_year = input.nextLine();

                String sqlSelect = " SELECT CustomerID, Year(PurchaseDate), SUM(TotalAmount) AS Total_transaction FROM Transaction WHERE CustomerID =" + "'" + cust_id + "'" + "AND YEAR(PurchaseDate) = " + "'" + report_year + "'";
                result = statement.executeQuery(sqlSelect);

                if(!result.next()){
                    System.out.println("No results found.");
                } else {
                    result.beforeFirst();
                    while(result.next()) {
                    int growthByMonth = result.getInt("Total_transaction");
                    System.out.format("Generated report of Customer Growth by month for selection is : %d", growthByMonth);
                    input.nextLine();
                } 

                }
              
                //End Transaction
                break;
            }    
            case 9:
            {
                System.out.println("Bye!");
                System.exit(0);
            }
            default:
            {
                System.out.println("Wrong choice.");
            }
        }
    }

    



        

} finally {
    // close(resultSelect);
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
    }