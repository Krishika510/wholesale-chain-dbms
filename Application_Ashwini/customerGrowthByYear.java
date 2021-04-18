import java.sql.*;
import java.util.Scanner;

public class customerGrowthByYear {

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

			    System.out.println("Enter Growth Year (YYYY): ");
			    String report_year = input.nextLine();

			    statement = connection.createStatement();

			    String sqlSelect = " SELECT COUNT(*) AS Number_of_enrolled_customers FROM SignUp WHERE YEAR(SignUpDate) ="  + "'"+report_year + "'";
			    resultSelect = statement.executeQuery(sqlSelect);

			    while(resultSelect.next()) {
			    	int growthByMonth = resultSelect.getInt("Number_of_enrolled_customers");
			    	System.out.format("Generated report of Customer Growth by month for selection is : %d", growthByMonth);
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
