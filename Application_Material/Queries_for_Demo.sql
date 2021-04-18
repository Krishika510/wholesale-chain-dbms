CREATE TABLE Store (
StoreID VARCHAR (16),
Address VARCHAR (1024) NOT NULL,
PhoneNumber VARCHAR (16) NOT NULL,
ManagerStaffID VARCHAR (16) NOT NULL,
PRIMARY KEY(StoreID)
);


CREATE TABLE Staff(
StaffID VARCHAR(16),
StoreID VARCHAR(16),
Name VARCHAR(256) NOT NULL,
DateOfBirth DATE NOT NULL,
JobTitle VARCHAR(256),
HomeAddress VARCHAR(1024) NOT NULL,
EmailAdd VARCHAR(512)  NOT NULL,
EmpStartDate DATE NOT NULL,
EmpEndDate DATE,
PhoneNumber VARCHAR(16) NOT NULL,
PRIMARY KEY(StaffID),
FOREIGN KEY(StoreID) REFERENCES Store(StoreID)
ON UPDATE CASCADE
);

CREATE TABLE Level(
LevelID VARCHAR(16),
LevelName VARCHAR(256) NOT NULL,
LevelDesc VARCHAR(1024) NOT NULL,
PRIMARY KEY(LevelID)
);

CREATE TABLE Supplier(
SupplierID VARCHAR(16),
SupplierName VARCHAR(256) NOT NULL,
Location VARCHAR(256) NOT NULL,
PhoneNumber VARCHAR(16) NOT NULL,
Email VARCHAR(512) NOT NULL,
PRIMARY KEY(SupplierID)
);

CREATE TABLE ClubMembers (
CustomerID VARCHAR (16),
LevelID VARCHAR (16) NOT NULL,
RegistrationStaffID VARCHAR (16),
BillingStaffID VARCHAR (16),
FirstName VARCHAR (256) NOT NULL,
LastName VARCHAR (256) NOT NULL,
HomeAddress VARCHAR (1024) NOT NULL,
EmailAdd VARCHAR (512) NOT NULL,
PhoneNumber VARCHAR (16) NOT NULL,
MembershipExpDate DATE NOT NULL,
ActiveStatus BOOLEAN NOT NULL,
Cashback DECIMAL (9,2),
PRIMARY KEY (CustomerID),
FOREIGN KEY(LevelID) REFERENCES Level (LevelID)
ON UPDATE CASCADE,
FOREIGN KEY(RegistrationStaffID) REFERENCES Staff (StaffID)
ON UPDATE CASCADE,
FOREIGN KEY(BillingStaffID) REFERENCES Staff (StaffID)
ON UPDATE CASCADE
);


CREATE TABLE Merchandise(
ProductID VARCHAR(16),
SupplierID VARCHAR(16),
WarehouseStaffID VARCHAR(16),
ProductName VARCHAR(256) NOT NULL,
ProductionDate Date NOT NULL,
ExpirationDate Date NOT NULL,
MarketPrice DECIMAL(9, 2) NOT NULL,
BuyPrice DECIMAL(9, 2) NOT NULL,
TotalQuantity INT NOT NULL,
PRIMARY KEY(ProductID, SupplierID),
FOREIGN KEY(SupplierID) REFERENCES Supplier(SupplierID)
ON UPDATE CASCADE,
FOREIGN KEY(WarehouseStaffID) REFERENCES Staff(StaffID)
ON UPDATE CASCADE
);

CREATE TABLE Transaction (
TransactionID VARCHAR (16),
StoreID VARCHAR (16) NOT NULL,
CustomerID VARCHAR (16) NOT NULL,
CashierStaffID VARCHAR (16) NOT NULL,
PurchaseDate Date NOT NULL,
TotalAmount DECIMAL (9, 2) NOT NULL,
PRIMARY KEY (TransactionID),
FOREIGN KEY(StoreID) REFERENCES Store (StoreID)
ON UPDATE CASCADE,
FOREIGN KEY(CustomerID) REFERENCES ClubMembers (CustomerID)
ON UPDATE CASCADE,
FOREIGN KEY(CashierStaffID) REFERENCES Staff (StaffID)
ON UPDATE CASCADE
);


CREATE TABLE SignUp (
SignUpDate Date NOT NULL,
CustomerID VARCHAR (16) NOT NULL,
RegistrationStaffID VARCHAR (16) NOT NULL,
StoreID VARCHAR (16) NOT NULL,
PRIMARY KEY (CustomerID, StoreID),
FOREIGN KEY(CustomerID) REFERENCES ClubMembers (CustomerID)
ON UPDATE CASCADE,
FOREIGN KEY(RegistrationStaffID) REFERENCES Staff (StaffID)
ON UPDATE CASCADE,
FOREIGN KEY(StoreID) REFERENCES Store (StoreID)
ON UPDATE CASCADE
);

CREATE TABLE productInfo(
StoreID VARCHAR(16),
ProductID VARCHAR(16),
WarehouseStaffID VARCHAR(16),
ProductSource VARCHAR(256) NOT NULL,
ProductDest VARCHAR(256) NOT NULL,
SaleStartDate DATE,
SaleEndDate DATE,
DiscountInfo VARCHAR(1024),
SellingPrice DECIMAL(9,2) NOT NULL,
StoreQuantity INT NOT NULL,
PRIMARY KEY(StoreID, ProductID),
FOREIGN KEY(StoreID) REFERENCES Store(StoreID)
ON UPDATE CASCADE,
FOREIGN KEY(ProductID) REFERENCES Merchandise(ProductID)
ON UPDATE CASCADE,
FOREIGN KEY(WarehouseStaffID) REFERENCES Staff(StaffID)
ON UPDATE CASCADE
);

CREATE TABLE contains(
TransactionID VARCHAR(16),
ProductID VARCHAR(16),
CashierStaffID VARCHAR(16),
ReturnDate DATE,
ReturnQuantity INT,
ProdSellQty INT NOT NULL,
PRIMARY KEY(TransactionID, ProductID),
FOREIGN KEY(TransactionID) REFERENCES Transaction(TransactionID)
ON UPDATE CASCADE,
FOREIGN KEY(ProductID) REFERENCES Merchandise(ProductID)
ON UPDATE CASCADE,
FOREIGN KEY(CashierStaffID) REFERENCES Staff(StaffID)
ON UPDATE CASCADE
);

CREATE TABLE generateBills (
ProductID VARCHAR (16), 
SupplierID VARCHAR (16),
BillingStaffID VARCHAR (16),
SuppliedQuantity INT NOT NULL,
Amount DECIMAL (9,2) NOT NULL, 
IsBilled BOOLEAN NOT NULL,
PRIMARY KEY (ProductID, SupplierID),
FOREIGN KEY(ProductID) REFERENCES Merchandise (ProductID)
ON UPDATE CASCADE,
FOREIGN KEY(SupplierID) REFERENCES Supplier (SupplierID)
ON UPDATE CASCADE,
FOREIGN KEY(BillingStaffID) REFERENCES Staff (StaffID)
ON UPDATE CASCADE
);


INSERT INTO Store (StoreID, ManagerStaffID, Address, PhoneNumber) 
VALUES ('2001','1001', '2221, B Street, NC', '919-2222-123');

INSERT INTO Store (StoreID, ManagerStaffID, Address, PhoneNumber) 
VALUES ('2002','1002', '2222, C Street, NC', '919-2222-456');

INSERT INTO Staff (StaffID, StoreID, Name, DateOfBirth, JobTitle, HomeAddress, 
	EmailAdd, EmpStartDate, PhoneNumber) VALUES ('1001', '2001', 'John', '1989-01-01',
	'Manager', '1101, S Street, NC', 'john01@gmail.com', '2018-10-10',
	'919-1111-123');

INSERT INTO Staff (StaffID, StoreID, Name, DateOfBirth, JobTitle, HomeAddress, 
	EmailAdd, EmpStartDate, PhoneNumber) VALUES ('1002', '2002', 'Alex', '1979-01-01',
	'Manager', '1102, T Street, NC', 'alex12@gmail.com', '2015-07-19',
	'919-1111-456');

INSERT INTO Staff (StaffID, StoreID, Name, DateOfBirth, JobTitle, HomeAddress, 
	EmailAdd, EmpStartDate, PhoneNumber) VALUES ('1003', '2001', 'Mary', '1993-01-01',
	'Cashier', '1103, U Street, NC', 'mary34@gmail.com', '2019-07-19',
	'919-1111-789');

INSERT INTO Level (LevelID, LevelName, LevelDesc) VALUES ('1', 'Silver', '10% discount on every bill');
INSERT INTO Level (LevelID, LevelName, LevelDesc) VALUES ('2', 'Gold', '20% discount on every bill');
INSERT INTO Level (LevelID, LevelName, LevelDesc) VALUES ('3', 'Platinum', '25% discount on every bill');

INSERT INTO Supplier (SupplierID, SupplierName, Location, PhoneNumber, Email) VALUES 
('4001', 'A Food Wholesale', '4401, A Street, NC', '919-4444-123', 'afood@gmail.com');

INSERT INTO Supplier (SupplierID, SupplierName, Location, PhoneNumber, Email) VALUES 
('4002', 'US Foods', '4402, G Street, NC', '919-4444-456', 'usfoods@gmail.com');

INSERT INTO ClubMembers(CustomerID, LevelID, FirstName, LastName, HomeAddress, EmailAdd, PhoneNumber, MembershipExpDate, ActiveStatus)
VALUES ('5001', '2', 'James', 'Smith', '5500, E Street, NC', 'James5001@gmail.com','919-5555-123', '2022-01-01', True);

INSERT INTO ClubMembers(CustomerID, LevelID, FirstName, LastName, HomeAddress, EmailAdd, PhoneNumber, MembershipExpDate, ActiveStatus)
VALUES ('5002', '3', 'David', 'Smith', '5501, F Street, NC', 'David5002@gmail.com','919-5555-456', '2022-01-01', True);

INSERT INTO Merchandise(ProductID, SupplierID, ProductName, ProductionDate, ExpirationDate, MarketPrice, BuyPrice, 
	TotalQuantity)
VALUES ('3001', '4001', 'AAA Paper Towels', '2020-01-01', '2025-01-01', '20', '10', '0');

INSERT INTO Merchandise(ProductID, SupplierID, ProductName, ProductionDate, ExpirationDate, MarketPrice, BuyPrice, TotalQuantity)
VALUES ('3002', '4002', 'BBB Hand soap', '2020-01-01', '2022-01-01', '10', '5', '0');

INSERT INTO Merchandise(ProductID, SupplierID, ProductName, ProductionDate, ExpirationDate, MarketPrice, BuyPrice, TotalQuantity)
VALUES ('3003', '4002', 'CCC Red Wine', '2021-01-01', '2022-01-01', '30', '15', '0');

INSERT INTO Transaction(TransactionID, StoreID, CustomerID, CashierStaffID, PurchaseDate, TotalAmount)
VALUES ('6001', '2001', '5002', '1003', '2020-05-01', 100);

INSERT INTO Transaction(TransactionID, StoreID, CustomerID, CashierStaffID, PurchaseDate, TotalAmount)
VALUES ('6002', '2001', '5002', '1003', '2020-06-01', 100);

INSERT INTO Transaction(TransactionID, StoreID, CustomerID, CashierStaffID, PurchaseDate, TotalAmount)
VALUES ('6003', '2001', '5001', '1003', '2020-07-01', 160);

INSERT INTO SignUp (SignUpDate, CustomerID, RegistrationStaffID, StoreID) VALUES ('2019-08-01', '5001', '1003', '2001');
INSERT INTO SignUp (SignUpDate, CustomerID, RegistrationStaffID, StoreID) VALUES ('2018-01-01', '5002', '1003', '2001');

INSERT INTO productInfo (StoreID, ProductID, ProductSource, ProductDest, SellingPrice, StoreQuantity, SaleStartDate, SaleEndDate, DiscountInfo)
VALUES ('2001', '3001', 'Warehouse', '2001', 20, 100, '2020-01-01', '2021-05-01', 0.2);

INSERT INTO productInfo (StoreID, ProductID, ProductSource, ProductDest, SellingPrice, StoreQuantity)
VALUES ('2001', '3002', 'Warehouse', '2001', 10, 200);

INSERT INTO productInfo (StoreID, ProductID, ProductSource, ProductDest, SellingPrice, StoreQuantity, SaleStartDate, SaleEndDate, DiscountInfo)
VALUES ('2002', '3001', 'Warehouse', '2002', 20, 150, '2020-01-01', '2021-05-01', 0.2);

INSERT INTO productInfo (StoreID, ProductID, ProductSource, ProductDest, SellingPrice, StoreQuantity)
VALUES ('2002', '3002', 'Warehouse', '2002', 10, 0);

INSERT INTO productInfo (StoreID, ProductID, ProductSource, ProductDest, SellingPrice, StoreQuantity, SaleStartDate, SaleEndDate, DiscountInfo)
VALUES ('2001', '3003', 'Warehouse', '2001', 30, 100, '2020-01-01', '2021-05-01', 0.2);

INSERT INTO contains (TransactionID, ProductID, ProdSellQty) 
VALUES ('6001', '3001', 5);

INSERT INTO contains (TransactionID, ProductID, ProdSellQty)
VALUES ('6001', '3002', 2);

INSERT INTO contains (TransactionID, ProductID, ProdSellQty)
VALUES ('6002', '3002', 10);

INSERT INTO contains (TransactionID, ProductID, ProdSellQty)
VALUES ('6003', '3001', 10);

INSERT INTO generateBills (ProductID, SupplierID, BillingStaffID, SuppliedQuantity, Amount, IsBilled)
VALUES ('3001', '4001', '1003', 250, 2500, False);

INSERT INTO generateBills (ProductID, SupplierID, BillingStaffID, SuppliedQuantity, Amount, IsBilled)
VALUES ('3002', '4002', '1003', 200, 1000, False);

INSERT INTO generateBills (ProductID, SupplierID, BillingStaffID, SuppliedQuantity, Amount, IsBilled)
VALUES ('3003', '4002', '1003', 100, 1500, False);


