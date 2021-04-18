import java.sql.*;
import java.util.Scanner;
import java.util.Calendar;

public class generateRewardChecks {

// Update your user info alone here
private static String jdbcURL = "jdbc:mariadb://classdb2.csc.ncsu.edu:3306/";

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
		ResultSet resultCustomer = null;
		ResultSet resultCashback = null;
		int amount = 0;
		int year = Calendar.getInstance().get(Calendar.YEAR);

		Scanner input = new Scanner(System.in);
		System.out.println("Enter database name:");
		user = input.nextLine();
		System.out.println("Enter password:");
		password = input.nextLine();
		jdbcURL = jdbcURL + user;

		Class.forName("org.mariadb.jdbc.Driver");

		try {
			    // Get a connection instance from the first driver in the
			    // DriverManager list that recognizes the URL jdbcURL
			    connection = DriverManager.getConnection(jdbcURL, user, password);

			    statement = connection.createStatement();

			    String sqlSelect = "SELECT CustomerID FROM ClubMembers WHERE LevelID = 3;";
			    resultCustomer = statement.executeQuery(sqlSelect);

			    while(resultCustomer.next()) {
			    	int customerID = resultCustomer.getInt("CustomerID");
			    	System.out.println(year);
			    	String sqlSelect2 = "SELECT 0.02 * SUM(TotalAmount) as Cashback FROM Transaction WHERE CustomerID = %d AND YEAR(PurchaseDate) = '%d'";
			    	sqlSelect2 = String.format(sqlSelect2, customerID, year);

			    	resultCashback = statement.executeQuery(sqlSelect2);

			    	while(resultCashback.next()) {
			    		int cashback = resultCashback.getInt("Cashback");
			    		String sqlUpdate = "UPDATE ClubMembers SET Cashback = %d WHERE CustomerID = %d";
				    	sqlUpdate = String.format(sqlUpdate, cashback, customerID);
				    	statement.executeQuery(sqlUpdate);

				    	System.out.format("Generated cashback reward of %d for Customer ID %d", cashback, customerID);
			    	}

			    	

			    }

			    

		}
		finally {
    close(resultCustomer);
    close(resultCashback);
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
