package com.example.cristiannavarrete.my_shopping;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is a Singleton class that is used to store
 * the ArrayList of products that is used by most
 * of the activities as well as the current user's
 * name.
 *
 * Created by Cristian Navarrete on 11/6/2015.
 */
public class Singleton {

    private static Singleton singleton = null;
    private String currentUserName = "";
    private ArrayList<Product> cart = new ArrayList<>();
    private HashMap<Integer, Integer> quantityMap = new HashMap<Integer, Integer>();

    public static Singleton getInstance() {
        if(singleton == null) {
            singleton = new Singleton();
        }
        return singleton;
    }

    public Singleton() {

    }

    public void setUserName(String userName) {
        this.currentUserName = userName;
    }

    public String getUserName() {
        return currentUserName;
    }

    public ArrayList<Product> getCart() {return this.cart;}

    public HashMap<Integer, Integer> getQuantityMap() {return this.quantityMap;}

    public double computeCartPrice() {
        double price = 0;

        for (int i = 0; i < getCart().size(); i++) {
            double productPrice = getCart().get(i).getSellPrice();
            productPrice *= getQuantityMap().get(getCart().get(i).getId());
            price += productPrice;
        }

        return price;
    }




}
