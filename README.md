# wholesale-chain-dbms

The application contains a total of 5 files. There are 4 application files in Java and one SQL file which contains create statements and insert scripts for demo data.

The six Java files do the following - 

1. informationProcessing.java - This application handles insert/update/delete of basic information about Staff, Store, ClubMember and Supplier. It also handles promotion and sale of products. User will be provided with a main menu to choose from five options, namely, "Insert", "Update", "Delete", "Update Sale Information" and "Exit". When prompted, the user enters a number from 1-5 corresponsing to the action required. A submenu will appear for insert, update and delete operations asking further details. 

2. inventoryRecords.java - This application handles all inventory related tasks. User will be provided with a main menu to choose from four options, namely, "Create inventory for newly arrived products", "Update inventory with returns", "Manage product transfers between stores in the chain" and "Exit". When prompted, the user enters a number from 1-4 corresponding to the action required. User will be asked the necessary inputs to facilitate the completion of action. 

3. billingAndTransaction.java - This application handles billing and transaction related tasks. User will be provided with a main menu to choose from five options, namely, "Add a transaction", "Return a product", "Generate Supplier Bills", "Generate Reward Checks for Platinum Members" and "Exit". When prompted, the user enters a number from 1-5 corresponding to the action required. User will be asked the necessary inputs to facilitate the completion of action. 

4. generateReports.java - This application handles all the report generations. User will be provided with a main menu to choose from ten options, namely, "Total Sales Report by Day", "Total Sales Report by Month", "Total Sales Report by Year", "Sales Growth report for a specific store for a given time period", "Merchandise Stock report by store", "Merchandise Stock report by product", "Customer Growth report by Month", "Customer Growth report by Year", "Customer Activity report such as Total Purchase Amount for a given time period" and "Exit". When prompted, the user enters a number from 1-10 corresponding to the action required. User will be asked the necessary inputs to facilitate the completion of action. 
