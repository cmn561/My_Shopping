# My_Shopping

**Installation:**
Go to [http://developer.android.com/sdk/index.html](http://developer.android.com/sdk/index.html) to download the Android Studio IDE and SDK tools.

**IDE:**
Android Studio

**Languages:**
Java, XML

**Description:**
This code was developed as a final project for Intro to Object Oriented Design at Florida Atlantic University. The purpose of the  assignment was to create an application that could simulate a shopping application. There are two different types of users: buyers and sellers. The sellers, once they create an account and log in, can post new items that can be sold(with a name, description of the item, quantity of the item, and sell price). The buyers can browse through the list of items and can buy these items at their leisure. The program also mantains a shopping cart page for managing the buyer's current cart. They can then proceed to purchase these items by providing a 16 digit number (meant to simulate a credit card number). All databases are managed and stored in SQL format using the SQLiteDatabase class in java. This allows the program to store a username/password database, and a product database on the Android phone's memory.
The program contains a log in system (with username to password mapping) which separates log ins for buyers and sellers. After logging in, the buyer will be presented with a list of items which are fetched from the SQL database. They can add items to their cart, manage their cart, and proceed to checkout. 
When logging in as a seller, the user is able to see their current inventory list, change product properties (sell price, description, quantity, etc), and add new products to the database, and check on their finances and revenue. The revenue page list how much each item has made as a profit and how much the seller has made in total. 
**Note:** All purchases are properly reflected in the database. Ex: Buying out all of an items stock, will make it unavailable until the seller adds more to the inventory.

**Contributors:**
Cristian Navarrete


Upcoming Features:
UI overhaul, more payment options, more seller options (delete item from inventory, etc), ability to add thumbnail pictures for each product
