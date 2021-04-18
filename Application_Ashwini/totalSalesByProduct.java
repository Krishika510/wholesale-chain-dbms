import java.sql.*;
import java.util.Scanner;

public class totalSalesByProduct {

// Update your user info alone here
private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/aunayak";

// Update your user and password info here!

private static final String user = "aunayak";
private static final String password = "Amigo777";

public static void main(String[] args) {
	try {
		// Loading the driver. This creates an instance of the driver
		// and calls the registerDriver method to make MySql(MariaDB) Thin available to clients.


		Class.forName("org.mariadb.jdbc.Driver");

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSelect = null;

		Scanner input = new Scanner(System.in);

		Class.forName("org.mariadb.jdbc.Driver");

		try {
			    // Get a connection instance from the first driver in the
			    // DriverManager list that recognizes the URL jdbcURL
			    connection = DriverManager.getConnection(jdbcURL, user, password);
			    
			    System.out.println("Enter Product ID: ");
			    String prod_id = input.nextLine();

			    statement = connection.createStatement();

			    String sqlSelect = "SELECT p.StoreID, p.ProductID, m.ProductName, p.StoreQuantity FROM productInfo p, Merchandise m WHERE m.ProductID = p.ProductID AND p.ProductID =" + prod_id;
			    ResultSet rs = statement.executeQuery(sqlSelect);
  				ResultSetMetaData rsmd = rs.getMetaData();

  				int columnsNumber = rsmd.getColumnCount();
  			 	while (rs.next()) {
      				for (int i = 1; i <= columnsNumber; i++) {
          				 if (i > 1) System.out.print(",  ");
          				 String columnValue = rs.getString(i);
           				 System.out.print(columnValue + " " + rsmd.getColumnName(i));
      				 }
      				 System.out.println("");
   				}

    
			    

			    

		}
		finally {
    close(resultSelect);
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
