package com.example.cristiannavarrete.my_shopping;

/**
 * This class is used to store information on each
 * user, such as name, password, and account type
 * (Seller or Buyer). Used for the user
 * database login system.
 *
 * Created by Cristian Navarrete on 10/9/2015.
 */
public class User {

    int id;
    String name;
    String pass;
    String type;

    User(String name, String pass, String type) {
        this.name = name;
        this.pass = pass;
        this.type = type;
    }


    public void setId(Integer id) {this.id = id;}
    public void setName(String name) {this.name = name;}
    public void setPass(String pass) {this.pass = pass;}
    public void setType(String type) {this.type = type;}

    public int getId() {return this.id;}
    public String getName() {return this.name;}
    public String getPass() {return this.pass;}
    public String getType() {return this.type;}

}
