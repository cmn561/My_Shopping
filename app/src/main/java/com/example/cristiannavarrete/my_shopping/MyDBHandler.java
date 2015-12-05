package com.example.cristiannavarrete.my_shopping;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * This is the main class that is used for storing the two
 * databases: the user database for login, and the product
 * database for buying and selling. Contains functions for
 * adding to database, deleting from database, and
 * modifying database.
 * Created by Cristian Navarrete on 11/6/2015.
 */
public class MyDBHandler extends SQLiteOpenHelper {

    private static final int TABLE_VERSION = 4;
    private static final String DATABASE_NAME = "ProductDB.db";
    public static final String TABLE_PRODUCTS = "products";
    public static final String COLUMN_ID = "_id";
    public static final String COLUMN_PRODUCTNAME = "product_name";
    public static final String COLUMN_SELLPRICE = "sell_price";
    public static final String COLUMN_INVOICEPRICE = "invoice_price";
    public static final String COLUMN_DESCRIPTION = "description";
    public static final String COLUMN_QUANTITY = "quantity";
    public static final String COLUMN_TOTALSOLD = "total_sold";
    public static final String COLUMN_SELLER = "seller_name";
    public static final String COLUMN_REVENUE = "revenue";

    public static final String TABLE_USERS = "users";
    public static final String COLUMN_USERNAME = "user_name";
    public static final String COLUMN_USERPASS = "user_pass";
    public static final String COLUMN_USERTYPE = "user_type";
    private static final String TAG = "Tag";

    /**
     * Constructor
     * @param context The view of the activity.
     * @param name The name of the database.
     * @param factory A new cursor factory object.
     * @param version The version number of the table.
     */
    public MyDBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, TABLE_VERSION);
    }

    /**
     * Called when a new database is instantiated. Used to create
     * the tables.
     * @param db A new database to create
     */
    @Override
    public void onCreate(SQLiteDatabase db) {
        String productQuery = "CREATE TABLE " + TABLE_PRODUCTS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_PRODUCTNAME + " TEXT, " +
                COLUMN_SELLPRICE + " TEXT, " +
                COLUMN_INVOICEPRICE + " TEXT, " +
                COLUMN_DESCRIPTION + " TEXT, " +
                COLUMN_QUANTITY + " INTEGER, " +
                COLUMN_TOTALSOLD + " INTEGER, " +
                COLUMN_SELLER + " TEXT," +
                COLUMN_REVENUE + " TEXT" +
                ");";
        db.execSQL(productQuery);

        String userQuery = "CREATE TABLE " + TABLE_USERS + "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COLUMN_USERNAME + " TEXT, " +
                COLUMN_USERPASS + " TEXT, " +
                COLUMN_USERTYPE + " TEXT" +
                ");";
        db.execSQL(userQuery);

    }

    /**
     * Used to upgrade a database to a new version.
     * @param db The database to upgrade.
     * @param oldVersion The old version of the current database.
     * @param newVersion The new version of the database.
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PRODUCTS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USERS);
        onCreate(db);
    }

    /**
     * Adds a new user to the user table.
     * @param user A user to add to the user table.
     * @return The unique id number of the new user.
     */
    public int addUser(User user)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_USERNAME, user.getName());
        cv.put(COLUMN_USERPASS, user.getPass());
        cv.put(COLUMN_USERTYPE, user.getType());
        SQLiteDatabase db = getWritableDatabase();
        long studentID = db.insert(TABLE_USERS, null, cv);
        //db.update(TABLE_USERS, cv, "_id " + "= " + user.getId(), null);
        db.close();
        return (int) studentID;
    }

    /**
     * Adds a new product to the product table.
     * @param product A product to add to the product table.
     * @return The unique id number of the new product.
     */
    public int addProduct(Product product)
    {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCTNAME, product.getName());
        cv.put(COLUMN_SELLPRICE, Double.toString(product.getSellPrice()));
        cv.put(COLUMN_INVOICEPRICE, Double.toString(product.getInvoiceprice()));
        cv.put(COLUMN_DESCRIPTION, product.getDescription());
        cv.put(COLUMN_QUANTITY, product.getQuantity());
        cv.put(COLUMN_TOTALSOLD, 0);
        cv.put(COLUMN_SELLER, product.getSeller());
        cv.put(COLUMN_REVENUE, 0.00);
        SQLiteDatabase db = getWritableDatabase();
        long studentID = db.insert(TABLE_PRODUCTS, null, cv);
        db.close();
        return (int) studentID;
    }

    /**
     * Called when a user has bought supplies. This function modifies the quantity, total items
     * sold, and total revenue for the item
     * @param idToModify The id number of the item to modify.
     * @param newQuantity The new quantity of the item.
     * @param newRevenue The new total revenue of the item.
     * @param newTotalSold The total amount of this item that have been sold.
     */
    public void updateProduct(int idToModify, int newQuantity, double newRevenue, int newTotalSold) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_QUANTITY, newQuantity);
        cv.put(COLUMN_TOTALSOLD, newTotalSold);
        cv.put(COLUMN_REVENUE, Double.toString(newRevenue));
        SQLiteDatabase db = getWritableDatabase();;
        db.update(TABLE_PRODUCTS, cv, "_id " + "= " + Integer.toString(idToModify), null);
        db.close();
    }

    /**
     * Used when the seller wants to update one or more fields in their
     * inventory.
     * @param idToModify The integer id of the product to modify
     * @param product The product with the new attributes
     */
    public void updateSellersProduct(int idToModify, Product product) {
        ContentValues cv = new ContentValues();
        cv.put(COLUMN_PRODUCTNAME, product.getName());
        cv.put(COLUMN_QUANTITY, product.getQuantity());
        cv.put(COLUMN_DESCRIPTION, product.getDescription());
        cv.put(COLUMN_INVOICEPRICE, Double.toString(product.getInvoiceprice()));
        cv.put(COLUMN_SELLPRICE, Double.toString(product.getSellPrice()));
        SQLiteDatabase db = getWritableDatabase();;
        db.update(TABLE_PRODUCTS, cv, "_id " + "= " + Integer.toString(idToModify), null);
        db.close();
    }

    public void deleteAllRows() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USERS, null, null);
        db.close();
    }

    public void deleteAllRowsProduct() {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_PRODUCTS, null, null);
        db.close();
    }

    /**
     * Used to tell whether a name has already been taken inside the database.
     * @param name The name to cross check against the database.
     * @return True of the table already has a user with that username; false otherwise.
     */
    public boolean hasUser(String name) {
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " =?";

        // Add the String you are searching by here.
        // Put it in an array to avoid an unrecognized token error
        Cursor cursor = db.rawQuery(selectString, new String[] {name});

        boolean hasObject = false;
        if(cursor.moveToFirst())
            hasObject = true;

        cursor.close();
        db.close();
        return hasObject;
    }

    /**
     * Used to determine the total amount of products in the database.
     * @return The total number of product in the database
     */
    public int numRows()
    {  int r;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        r = c.getCount();
        return r;
    }

    /**
     * Used to determine the total amount of products that the seller has
     * in the product database
     * @param seller The current seller username.
     * @return The total number of objects in the database belonging to that user.
     */
    public int numRowsOfSeller(String seller)
    {  int r;
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_SELLER + " =?";
        Cursor c = db.rawQuery(selectString, new String[] {seller});
        r = c.getCount();
        return r;
    }

    /**
     * Fetches the total quantity of a product.
     * @param id The id of the product to check.
     * @return The quantity of the product.
     */
    public int getProductQuantityById(int id) {
        int quantity = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + " = " + Integer.toString(id);
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            quantity = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_QUANTITY)));
        }
        return quantity;
    }

    /**
     * Fetches the total product revenue of a product.
     * @param id The id of the product to check.
     * @return The total revenue of the product.
     */
    public double getProductRevenueById(int id) {
        double revenue = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + " = " + Integer.toString(id);
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            revenue = Double.parseDouble(c.getString(c.getColumnIndex(COLUMN_REVENUE)));
        }
        return revenue;
    }

    /**
     * Fetches the total amount sold of a product.
     * @param id The id of the product to check.
     * @return The total amount of the product that has been sold.
     */
    public int getTotalAmountSoldById(int id) {
        int totalSold = 0;
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_ID + " = " + Integer.toString(id);
        Cursor c = db.rawQuery(query, null);
        if (c.moveToFirst()) {
            totalSold = Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_TOTALSOLD)));
        }
        return totalSold;
    }

    /**
     * Fetches all of the fields of a product in the database.
     * @param i The position of the product in the database.
     * @return A new product with fields equal to the product in the
     * database.
     */
    public Product getRowProductData(int i)
    {
        Product newProduct = new Product();
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE 1";
        Cursor c = db.rawQuery(query, null);
        c.moveToPosition(i);
        newProduct.setId(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID))));
        newProduct.setName(c.getString(c.getColumnIndex(COLUMN_PRODUCTNAME)));
        newProduct.setDescription(c.getString(c.getColumnIndex(COLUMN_DESCRIPTION)));
        newProduct.setInvoiceprice(Double.parseDouble(c.getString(c.getColumnIndex(COLUMN_INVOICEPRICE))));
        newProduct.setSellPrice(Double.parseDouble(c.getString(c.getColumnIndex(COLUMN_SELLPRICE))));
        newProduct.setQuantity(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_QUANTITY))));
        newProduct.setSeller(c.getString(c.getColumnIndex(COLUMN_SELLER)));


        return newProduct;
    }
    /**
     * Fetches all of the fields of a product in the database belonging
     * to a seller.
     * @param i The position of the product in the database.
     * @return A new product with fields equal to the product in the
     * database.
     */
    public Product getRowProductDataOfSeller(int i, String seller) {
        Product newProduct = new Product();
        SQLiteDatabase db = getWritableDatabase();
        String selectString = "SELECT * FROM " + TABLE_PRODUCTS + " WHERE " + COLUMN_SELLER + " =?";
        Cursor c = db.rawQuery(selectString, new String[] {seller});
        c.moveToPosition(i);
        newProduct.setId(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_ID))));
        newProduct.setName(c.getString(c.getColumnIndex(COLUMN_PRODUCTNAME)));
        newProduct.setDescription(c.getString(c.getColumnIndex(COLUMN_DESCRIPTION)));
        newProduct.setInvoiceprice(Double.parseDouble(c.getString(c.getColumnIndex(COLUMN_INVOICEPRICE))));
        newProduct.setSellPrice(Double.parseDouble(c.getString(c.getColumnIndex(COLUMN_SELLPRICE))));
        newProduct.setQuantity(Integer.parseInt(c.getString(c.getColumnIndex(COLUMN_QUANTITY))));
        newProduct.setSeller(c.getString(c.getColumnIndex(COLUMN_SELLER)));

        return newProduct;
    }

    /**
     * Used to determine if the user log in credentials are valid or not
     * @param name The username of the user to log in as.
     * @param pass The password supplied by the user.
     * @param buyer Used to determine if the account is a buyer or seller.
     * @return True if the log in was a success; false otherwise.
     */
    public boolean logInMatch(String name, String pass, String buyer) {
        String passMatch ="";
        String typeMatch = "";
        SQLiteDatabase db = getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_USERS + " WHERE " + COLUMN_USERNAME + " =?";
        Cursor c = db.rawQuery(query, new String[] { name });
        if (c.moveToFirst()) {
            passMatch += c.getString(c.getColumnIndex(COLUMN_USERPASS));
            typeMatch += c.getString(c.getColumnIndex(COLUMN_USERTYPE));
            if (pass.equals(passMatch) && buyer.equals(typeMatch))
            {
                c.close();
                return true;
            }
            else
                return false;
        }
        else
            c.close();
            return false;


    }

}

