import java.sql.*;
import java.util.Scanner;
import java.time.LocalDate;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.Scanner;
import java.util.Calendar;
import java.io.Console;

public class informationProcessing {


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
            System.out.println("\n\t\tInformation Processing");
            System.out.println("\t\t-----------------------\n");
            System.out.println("1. Insert\n");
            System.out.println("2. Update\n");
            System.out.println("3. Delete\n");
            System.out.println("4. Update Sale Information\n");
            System.out.println("5. Exit menu\n\n");
            
            System.out.println("Which operation do you want to perform?");
            int operation = input.nextInt();

            switch(operation){
                case 1:
                {
                    System.out.println("\n\t\tTables");
                    System.out.println("\t\t------\n");
                    System.out.println("1. Store\n");
                    System.out.println("2. Staff\n");
                    System.out.println("3. Customer\n");
                    System.out.println("4. Supplier\n");
                    System.out.println("5. Exit menu\n\n");

                    System.out.println("Which table do you want to use the insert operation on?");
                    int table = input.nextInt();

                    switch(table){
                        case 1:
                        {
                            input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Store ID:");
                            String storeID = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Address:");
                            String address = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Phone Number:");
                            String phoneNumber = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Manager Staff ID:");
                            String managerStaffID = input.nextLine();
                            
                
                            String sqlInsert1 = "INSERT INTO Store (StoreID, Address, PhoneNumber, ManagerStaffID) VALUES ('%s','%s','%s','%s')";
                            sqlInsert1 = String.format(sqlInsert1, storeID, address, phoneNumber, managerStaffID);
                            int i = statement.executeUpdate(sqlInsert1);
                            System.out.format("%d Row inserted into Store table\n", i);
                            connection.commit();
                            return;
                        }
                        case 2:
                        {
                            input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Store ID:");
                            String storeID = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Staff ID:");
                            String staffID = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Name:");
                            String name = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Date of Birth:");
                            String dob = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Job title:");
                            String jobTitle = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Home Address:");
                            String homeAddress = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Email Address:");
                            String emailAddress = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Employee Start Date:");
                            String empStartDate = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Phone Number:");
                            String phoneNumber = input.nextLine();

                            String sqlInsert1 = "INSERT INTO Staff (StaffID, StoreID, Name, DateOfBirth, JobTitle, HomeAddress, EmailAdd, EmpStartDate, PhoneNumber) VALUES ('%s','%s','%s','%s','%s','%s','%s','%s','%s')";
                            sqlInsert1 = String.format(sqlInsert1, staffID, storeID, name, dob, jobTitle, homeAddress, emailAddress, empStartDate, phoneNumber );
                            int i = statement.executeUpdate(sqlInsert1);
                            System.out.format("%d Row inserted into Staff table\n", i);
                            connection.commit();
                            return;
                        }
                        case 3:
                        {
                            input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Customer ID:");
                            String custID = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Level ID:");
                            String levelID = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter First Name:");
                            String fName = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Last Name:");
                            String lName = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Home Address:");
                            String homeAddress = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Email Address:");
                            String emailAddress = input.nextLine();
                        
                            System.out.println("Enter Phone Number:");
                            String phoneNumber = input.nextLine();
                        
                            
                            String sqlInsert1 = "INSERT INTO ClubMembers (CustomerID, LevelID, FirstName, LastName, HomeAddress, EmailAdd, PhoneNumber, MembershipExpDate, ActiveStatus) VALUES ('%s','%s','%s','%s','%s','%s','%s',CURDATE()+ INTERVAL 1 YEAR,true)";
                            sqlInsert1 = String.format(sqlInsert1, custID, levelID, fName, lName, homeAddress, emailAddress, phoneNumber);
                            int i = statement.executeUpdate(sqlInsert1);
                            System.out.format("%d Row inserted into Customer table\n", i);
                            connection.commit();
                            return;
                        }
                        case 4:
                        {
                            input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Supplier ID:");
                            String suppID = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Supplier Name:");
                            String suppName = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Location:");
                            String location = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Phone Number:");
                            String phoneNumber = input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Email:");
                            String email = input.nextLine();

                            String sqlInsert1 = "INSERT INTO Supplier (SupplierID, SupplierName, Location, PhoneNumber, Email) VALUES ('%s','%s','%s','%s','%s')";
                            sqlInsert1 = String.format(sqlInsert1, suppID, suppName, location, phoneNumber, email);
                            int i = statement.executeUpdate(sqlInsert1);
                            System.out.format("%d Row inserted into Supplier table\n", i);
                            connection.commit();
                            return;
                        }
                        case 5:
                        {
                            return;
                        }
                        default:
                        {
                            System.out.println("Wrong choice:");
                        }
                    }
                }
                case 2:
                {
                    System.out.println("\n\t\tTables");
                    System.out.println("\t\t------\n");
                    System.out.println("1. Store\n");
                    System.out.println("2. Staff\n");
                    System.out.println("3. Customer\n");
                    System.out.println("4. Supplier\n");
                    System.out.println("5. Exit menu\n\n");

                    System.out.println("Which table do you want to use the update operation on?");
                    int table = input.nextInt();

                    switch(table){
                        case 1:
                        {
                            input.nextLine();
                            // Take Warehouse Staff ID as input.
                            System.out.println("Enter Store ID:");
                            String storeID = input.nextLine();

                            

                            String sqlselect2 = "Select * from Store where StoreID = '%s'";
                            sqlselect2= String.format(sqlselect2, storeID);
                            result = statement.executeQuery(sqlselect2);

                            while(result.next())
                            {
                                System.out.println("\n\t\tColumns");
                                System.out.println("\t\t-------\n");
                                System.out.println("1. Address\n");
                                System.out.println("2. Phone Number\n");
                                System.out.println("3. Manager Staff ID\n");
                                System.out.println("4. Exit menu\n\n");

                                System.out.println("Which attribute do you want to use the update operation on?");
                                int choice = input.nextInt();
                                switch(choice){
                                    case 1:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Address to update:");
                                        String address = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Store SET Address = '%s' WHERE StoreID = '%s'";
                                        sqlUpdate2 = String.format(sqlUpdate2, address, storeID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Store Table Updated Successfully");
                                        return;
                                    }
                                    case 2:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter phone number to update:");
                                        String phoneNumber = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Store SET PhoneNumber = '%s' WHERE StoreID = '%s'";
                                        sqlUpdate2 = String.format(sqlUpdate2, phoneNumber, storeID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Store Table Updated Successfully");
                                        return;
                                    }
                                    case 3:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Manager Staff ID to update:");
                                        String manStaffID = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Store SET ManagerStaffID = '%s' WHERE StoreID = '%s'";
                                        sqlUpdate2 = String.format(sqlUpdate2, manStaffID, storeID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Store Table Updated Successfully");
                                        return;
                                    }
                                    default:
                                    {
                                        System.out.println("Wrong Choice");
                                    }
                                }
                                
                            }
                            System.out.println("Wrong Store ID");
                            connection.commit();
                            return;
                        }
                        case 2:
                        {
                            System.out.println("Enter Staff ID:");
                            int staffID = input.nextInt();
                            

                            String sqlselect2 = "Select * from Staff where StaffID = '%d'";
                            sqlselect2= String.format(sqlselect2, staffID);
                            result = statement.executeQuery(sqlselect2);
                            
                            while(result.next())
                            {
                                System.out.println("\n\t\tColumns");
                                System.out.println("\t\t-------\n");
                                System.out.println("1. Name\n");
                                System.out.println("2. Date of Birth\n");
                                System.out.println("3. Job Title\n");
                                System.out.println("4. Home Address\n");
                                System.out.println("5. Email Address\n");
                                System.out.println("6. Employee Start Date\n");
                                System.out.println("7. Phone Number\n");
                                System.out.println("8. Store ID\n");
                                System.out.println("9. Exit menu\n\n");

                                System.out.println("Which attribute do you want to use the update operation on?");
                                int choice = input.nextInt();
                                switch(choice){
                                    case 1:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Staff Name to update:");
                                        String staffName = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Staff SET Name = '%s' WHERE StaffID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, staffName, staffID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Staff Table Updated Successfully");
                                        return;
                                    }
                                    case 2:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Date of Birth to update:");
                                        String dob = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Staff SET DateOfBirth = '%s' WHERE StaffID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, dob, staffID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Staff Table Updated Successfully");
                                        return;
                                    }
                                    case 3:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Job Title to update:");
                                        String jobTitle = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Staff SET JobTitle = '%s' WHERE StaffID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, jobTitle, staffID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Staff Table Updated Successfully");
                                        return;
                                    }
                                    case 4:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Home Address to update:");
                                        String homeAddress = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Staff SET HomeAddress = '%s' WHERE StaffID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, homeAddress, staffID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Staff Table Updated Successfully");
                                        return;
                                    }
                                    case 5:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Email Address to update:");
                                        String emailAddress = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Staff SET EmailAddress = '%s' WHERE StaffID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, emailAddress, staffID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Staff Table Updated Successfully");
                                        return;
                                    }
                                    case 6:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Employee Start Date to update:");
                                        String empStartDate = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Staff SET EmpStartDate = '%s' WHERE StaffID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, empStartDate, staffID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Staff Table Updated Successfully");
                                        return;
                                    }
                                    case 7:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Phone Number to update:");
                                        String phno = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Staff SET PhoneNumber = '%s' WHERE StaffID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, phno, staffID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Staff Table Updated Successfully");
                                        return;
                                    }
                                    case 8:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Store ID to update:");
                                        String storeID = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Staff SET StoreID = '%s' WHERE StaffID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, storeID, staffID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Staff Table Updated Successfully");
                                        return;
                                    }
                                    default:
                                    {
                                        System.out.println("Wrong choice");
                                    }
                                }
                            
                            }
                            System.out.println("Wrong Staff ID");
                            connection.commit();
                            return;
                        }
                        case 3:
                        {
                            System.out.println("Enter Customer ID:");
                            int custID = input.nextInt();
                            input.nextLine();
                            

                            String sqlselect2 = "Select * from ClubMembers where CustomerID = '%d'";
                            sqlselect2= String.format(sqlselect2, custID);
                            result = statement.executeQuery(sqlselect2);

                            while(result.next())
                            {   
                                System.out.println("\n\t\tColumns");
                                System.out.println("\t\t-------\n");
                                System.out.println("1. First Name\n");
                                System.out.println("2. Last Name\n");
                                System.out.println("3. Home Address\n");
                                System.out.println("4. Email Address\n");
                                System.out.println("5. Phone Number\n");
                                System.out.println("6. Membership Expiration Date\n");
                                System.out.println("7. Active Status\n");
                                System.out.println("8. Level ID\n");
                                System.out.println("9. Exit menu\n\n");

                                System.out.println("Which attribute do you want to use the update operation on?");
                                int choice = input.nextInt();
                                switch(choice){
                                    case 1:
                                    {
                                        input.nextLine();
                                        // Take Warehouse Staff ID as input.
                                        System.out.println("Enter First Name to update:");
                                        String fName = input.nextLine();
                                        String sqlUpdate2 = "UPDATE ClubMembers SET FirstName = '%s' WHERE CustomerID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, fName, custID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Customer Table Updated Successfully");
                                        return;
                                    }
                                    case 2:
                                    {
                                        input.nextLine();
                                        // Take Warehouse Staff ID as input.
                                        System.out.println("Enter Last Name to update:");
                                        String lName = input.nextLine();
                                        String sqlUpdate2 = "UPDATE ClubMembers SET LastName = '%s' WHERE CustomerID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, lName, custID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Customer Table Updated Successfully");
                                        return;
                                    }
                                    case 3:
                                    {
                                        input.nextLine();
                                        // Take Warehouse Staff ID as input.
                                        System.out.println("Enter Home Address to update:");
                                        String homeAddress = input.nextLine();
                                        String sqlUpdate2 = "UPDATE ClubMembers SET HomeAddress = '%s' WHERE CustomerID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, homeAddress, custID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Customer Table Updated Successfully");
                                        return;
                                    }
                                    case 4:
                                    {
                                        input.nextLine();
                                        // Take Warehouse Staff ID as input.
                                        System.out.println("Enter Email Address to update:");
                                        String emailAddress = input.nextLine();
                                        String sqlUpdate2 = "UPDATE ClubMembers SET EmailAdd = '%s' WHERE CustomerID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, emailAddress, custID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Customer Table Updated Successfully");
                                        return;
                                    }
                                    case 5:
                                    {
                                        input.nextLine();
                                        // Take Warehouse Staff ID as input.
                                        System.out.println("Enter Phone Number to update:");
                                        String phno = input.nextLine();
                                        String sqlUpdate2 = "UPDATE ClubMembers SET PhoneNumber = '%s' WHERE CustomerID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, phno, custID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Customer Table Updated Successfully");
                                        return;
                                    }
                                    case 6:
                                    {
                                        input.nextLine();
                                        // Take Warehouse Staff ID as input.
                                        System.out.println("Enter Membership expiration date to update:");
                                        String expDate = input.nextLine();
                                        String sqlUpdate2 = "UPDATE ClubMembers SET MembershipExpDate = '%s' WHERE CustomerID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, expDate, custID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Customer Table Updated Successfully");
                                        return;
                                    }
                                    case 7:
                                    {
                                        // Take Warehouse Staff ID as input.
                                        System.out.println("Enter Active Status to update:");
                                        boolean activeStatus = input.nextBoolean();
                                        String sqlUpdate2 = "UPDATE ClubMembers SET ActiveStatus = %b WHERE CustomerID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, activeStatus, custID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Customer Table Updated Successfully");
                                        return;
                                    }
                                    case 8:
                                    {
                                        // Take Warehouse Staff ID as input.
                                        System.out.println("Enter Level ID to update:");
                                        String levelID = input.nextLine();
                                        String sqlUpdate2 = "UPDATE ClubMembers SET LevelID = %b WHERE CustomerID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, levelID, custID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Customer Table Updated Successfully");
                                        return;
                                    }
                                    default:
                                    {
                                        System.out.println("Wrong choice");
                                    }
                                }
                                
                            }
                            System.out.println("Wrong Customer ID");
                            connection.commit();
                            return;
                        }
                        case 4:
                        {
                            System.out.println("Enter Supplier ID:");
                            int suppID = input.nextInt();
                            input.nextLine();
                            

                            String sqlselect2 = "Select * from Supplier where SupplierID = '%d'";
                            sqlselect2= String.format(sqlselect2, suppID);
                            result = statement.executeQuery(sqlselect2);

                            while(result.next())
                            {
                                System.out.println("\n\t\tColumns");
                                System.out.println("\t\t-------\n");
                                System.out.println("1. Supplier Name\n");
                                System.out.println("2. Location\n");
                                System.out.println("3. Phone Number\n");
                                System.out.println("4. Email\n");
                                System.out.println("5. Exit menu\n\n");

                                System.out.println("Which attribute do you want to use the update operation on?");
                                int choice = input.nextInt();
                                switch(choice){
                                    case 1:
                                    {
                                        input.nextLine();
                                        // Take Warehouse Staff ID as input.
                                        System.out.println("Enter Supplier Name to update:");
                                        String sName = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Supplier SET SupplierName = '%s' WHERE SupplierID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, sName, suppID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Supplier Table Updated Successfully");
                                        return;
                                    }
                                    case 2:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Location to update:");
                                        String location = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Supplier SET Location = '%s' WHERE SupplierID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, location, suppID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Supplier Table Updated Successfully");
                                        return;
                                    }
                                    case 3:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Phone Number to update:");
                                        String phno = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Supplier SET PhoneNumber = '%s' WHERE SupplierID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, phno, suppID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Supplier Table Updated Successfully");
                                        return;
                                    }
                                    case 4:
                                    {
                                        input.nextLine();
                                        System.out.println("Enter Email to update:");
                                        String email = input.nextLine();
                                        String sqlUpdate2 = "UPDATE Supplier SET Email = '%s' WHERE SupplierID = %d";
                                        sqlUpdate2 = String.format(sqlUpdate2, email, suppID);
                                        statement.executeQuery(sqlUpdate2);
                                        System.out.println("Supplier Table Updated Successfully");
                                        return;
                                    }
                                    default:
                                    {
                                        System.out.println("Wrong choice");
                                    }
                                }
                                
                            }
                            System.out.println("Wrong Supplier ID");
                            return;

                        }
                        case 5:
                        {
                            System.exit(0);
                        }
                        default:
                        {
                            System.out.println("Wrong choice:");
                        }

                    }
                }
                case 3:
                {
                    System.out.println("\n\t\tTables");
                    System.out.println("\t\t------\n");
                    System.out.println("1. Store\n");
                    System.out.println("2. Staff\n");
                    System.out.println("3. Customer\n");
                    System.out.println("4. Supplier\n");
                    System.out.println("5. Exit menu\n\n");

                    System.out.println("Which table do you want to use the delete operation on?");
                    int table = input.nextInt();

                    switch(table){
                        case 1:
                        {
                            System.out.println("Enter Store ID to delete:");
                            int storeID = input.nextInt();

                            String sqlselect2 = "Select * from Store where StoreID = '%d'";
                            sqlselect2= String.format(sqlselect2, storeID);
                            result = statement.executeQuery(sqlselect2);

                            while(result.next())
                            {

                            String sqldelete2 = "UPDATE Store SET isDelete = True WHERE StoreID = %d";
                            sqldelete2 = String.format(sqldelete2, storeID);
                            statement.executeQuery(sqldelete2);
                            System.out.println("Row deleted from Store table");
                            return;
                            }
                            System.out.println("Wrong Store ID");
                            connection.commit();
                            return;
                        }
                        case 2:
                        {
                            System.out.println("Enter Staff ID to delete:");
                            int staffID = input.nextInt();

                            String sqlselect2 = "Select * from Staff where StaffID = '%d'";
                            sqlselect2= String.format(sqlselect2, staffID);
                            result = statement.executeQuery(sqlselect2);

                            while(result.next())
                            {
                            String sqldelete2 = "UPDATE Staff SET isDelete = True WHERE StaffID = %d";
                            sqldelete2 = String.format(sqldelete2, staffID);
                            statement.executeQuery(sqldelete2);
                            System.out.println("Row deleted from Staff table");
                            return;
                            }
                            System.out.println("Wrong Staff ID");
                            connection.commit();
                            return;
                        }
                        case 3:
                        {
                            System.out.println("Enter Customer ID to delete:");
                            int custID = input.nextInt();

                            String sqlselect2 = "Select * from ClubMembers where CustomerID = '%d'";
                            sqlselect2= String.format(sqlselect2, custID);
                            result = statement.executeQuery(sqlselect2);

                            while(result.next())
                            {

                            String sqldelete2 = "UPDATE ClubMembers SET isDelete = True WHERE CustomerID = %d";
                            sqldelete2 = String.format(sqldelete2, custID);
                            statement.executeQuery(sqldelete2);
                            System.out.println("Row deleted from Club Members table");
                            return;
                            }
                            System.out.println("Wrong Customer ID");
                            connection.commit();
                            return;
                        }
                        case 4:
                        {
                            System.out.println("Enter Supplier ID to delete:");
                            int suppID = input.nextInt();

                            String sqlselect2 = "Select * from Supplier where SupplierID = '%d'";
                            sqlselect2= String.format(sqlselect2, suppID);
                            result = statement.executeQuery(sqlselect2);

                            while(result.next())
                            {
                            String sqldelete2 = "UPDATE Supplier SET isDelete = True WHERE SupplierID = %d";
                            sqldelete2 = String.format(sqldelete2, suppID);
                            statement.executeQuery(sqldelete2);
                            System.out.println("Row deleted from Supplier table");
                            return;
                            }
                            System.out.println("Wrong Supplier ID");
                            connection.commit();
                            return;
                        }
                        case 5:
                        {
                            System.exit(0);
                        }
                        default:
                        {
                            System.out.println("Wrong choice:");
                        }
                    }
                }
                case 4:
                {
                    System.out.println("Enter Product ID:");
                    int prodID = input.nextInt();
                    System.out.println("Enter Store ID:");
                    int storeID = input.nextInt();
                    System.out.println("Enter Discount Info to update:");
                    float dInfo = input.nextFloat();
                    input.nextLine();
                    // Take Warehouse Staff ID as input.
                    System.out.println("Enter Sale Start Date to update:");
                    String saleStartDate = input.nextLine();
                    // Take Warehouse Staff ID as input.
                    System.out.println("Enter Sale End Date to update:");
                    String saleEndDate = input.nextLine();

                    String sqlselect2 = "Select * from productInfo where StoreID = %d and ProductID = %d";
                    sqlselect2= String.format(sqlselect2, storeID, prodID);
                    result = statement.executeQuery(sqlselect2);

                    while(result.next())
                    {
                    String sqlUpdate2 = "UPDATE productInfo SET SaleStartDate = '%s', SaleEndDate = '%s', DiscountInfo= %f WHERE StoreID = %d AND ProductID = %d";
                    sqlUpdate2 = String.format(sqlUpdate2, saleStartDate, saleEndDate,dInfo, storeID, prodID);
                    statement.executeQuery(sqlUpdate2);
                    System.out.println("Sale Information Updated Successfully");
                    return;
                    }
                    System.out.println("Wrong Product or Store ID");
                    connection.commit();
                    return;
                }
                case 5:
                {
                    System.exit(0);
                    break;
                }
                default:
                {
                    System.out.println("Wrong choice:");
                    break;
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
                                        
