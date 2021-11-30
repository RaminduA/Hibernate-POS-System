DROP DATABASE IF EXISTS WholesaleLayered;
CREATE DATABASE IF NOT EXISTS WholesaleLayered;
SHOW DATABASES ;
USE WholesaleLayered;
SHOW TABLES ;
#===================================================================================================
DROP TABLE IF EXISTS Customer;
CREATE TABLE IF NOT EXISTS Customer(
    customerId VARCHAR(8),
    name VARCHAR(30) NOT NULL DEFAULT 'Unknown',
    address VARCHAR(30),
    contact VARCHAR(20),
    nic VARCHAR(20),
    CONSTRAINT PRIMARY KEY (customerId)
);

#===================================================================================================
DROP TABLE IF EXISTS Item;
CREATE TABLE IF NOT EXISTS Item(
    itemCode VARCHAR(8),
    description VARCHAR(50) NOT NULL DEFAULT 'Empty',
    qtyOnHand INT NOT NULL DEFAULT 0,
    unitPrice DECIMAL(8,2) NOT NULL DEFAULT 0.00,
    discountPercent DECIMAL(8,2) DEFAULT 0.00,
    CONSTRAINT PRIMARY KEY (itemCode)
);
#===================================================================================================
DROP TABLE IF EXISTS `Order`;
CREATE TABLE IF NOT EXISTS `Order`(
    orderId VARCHAR(8),
    customerId VARCHAR(8),
    orderDate DATE,
    orderTime VARCHAR(15),
    cost DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (orderId),
    CONSTRAINT FOREIGN KEY (customerId) REFERENCES Customer(customerId) ON DELETE CASCADE ON UPDATE CASCADE
);
#===================================================================================================
DROP TABLE IF EXISTS `Order Detail`;
CREATE TABLE IF NOT EXISTS `Order Detail`(
    orderId VARCHAR(8),
    itemCode VARCHAR(8),
    orderQty INT NOT NULL DEFAULT 0,
    discount DECIMAL(8,2) DEFAULT 0.00,
    price DECIMAL(8,2),
    CONSTRAINT PRIMARY KEY (orderId, itemCode),
    CONSTRAINT FOREIGN KEY (orderId) REFERENCES `Order`(orderId) ON DELETE CASCADE ON UPDATE CASCADE,
    CONSTRAINT FOREIGN KEY (itemCode) REFERENCES Item(itemCode) ON DELETE CASCADE ON UPDATE CASCADE
);
#===================================================================================================
SHOW TABLES ;
DESCRIBE Customer;
DESCRIBE `Order`;
DESCRIBE Item;
DESCRIBE `Order Detail`;

