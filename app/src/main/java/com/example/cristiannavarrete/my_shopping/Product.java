package com.example.cristiannavarrete.my_shopping;

/**
 * This is the main class that stores all info on one product.
 * The fields stored by this class includes, the item name, description,
 * quantity, sell price, invoice price, id, and the seller.
 * Created by Cristian Navarrete on 11/3/2015.
 */
public class Product {

    private int id;
    private String name;
    private double sellPrice;
    private double invoicePrice;
    private int quantity;
    private String description;
    private String seller;

    public Product(String aName, double anInvoicePrice, double sellPrice, int aQuantity, String aDescription, String aSeller) {
        this.name = aName;
        this.invoicePrice = anInvoicePrice;
        this.sellPrice = sellPrice;
        this.quantity = aQuantity;
        this.description = aDescription;
        this.seller = aSeller;
    }

    public Product() {}

    public int getId() {return this.id;}

    public String getName() {
        return this.name;
    }

    public double getSellPrice() { return this.sellPrice; }

    public double getInvoiceprice() { return this.invoicePrice; }

    public int getQuantity() { return this.quantity; }

    public String getDescription() { return this.description; }

    public String getSeller() { return this.seller; }

    public void setId(int id) {this.id = id;}

    public void setName(String aName) {
        this.name=aName;
    }

    public void setSellPrice(double aSellPrice) { this.sellPrice=aSellPrice; }

    public void setInvoiceprice(double anInvoicePrice) { this.invoicePrice=anInvoicePrice;}

    public void setQuantity(int aQuantity) { this.quantity=aQuantity; }

    public void setDescription(String aDescription) { this.description=aDescription; }

    public void setSeller(String aSeller) { this.seller=aSeller;}
}
