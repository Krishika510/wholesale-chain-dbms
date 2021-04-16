import java.sql.*;
import java.util.Scanner;

public class generateBills {

// Update your user info alone here
private static final String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/kmshivna";

// Update your user and password info here!

private static final String user = "kmshivna";
private static final String password = "200368095";

public static void main(String[] args) {
	try {
		// Loading the driver. This creates an instance of the driver
		// and calls the registerDriver method to make MySql(MariaDB) Thin available to clients.


		Class.forName("org.mariadb.jdbc.Driver");

		Connection connection = null;
		Statement statement = null;
		ResultSet resultSelect = null;
		int amount = 0;

		Scanner input = new Scanner(System.in);

		Class.forName("org.mariadb.jdbc.Driver");

		try {
			    // Get a connection instance from the first driver in the
			    // DriverManager list that recognizes the URL jdbcURL
			    connection = DriverManager.getConnection(jdbcURL, user, password);

			    System.out.println("Enter Supplier ID: ");
			    int supplierID = input.nextInt();

			    statement = connection.createStatement();

			    String sqlSelect = "Select Amount from generateBills where SupplierID =" + supplierID;
			    resultSelect = statement.executeQuery(sqlSelect);

			    while(resultSelect.next()) {
			    	amount = resultSelect.getInt("Amount");
			    }

			    System.out.format("Generated bill amount of %d for Supplier ID %d", amount, supplierID);

			    String sqlUpdate = "UPDATE generateBills set Amount = 0 and IsBilled = True where SupplierID = %d";
			    sqlUpdate = String.format(sqlUpdate, supplierID);
    			statement.executeQuery(sqlUpdate);
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
