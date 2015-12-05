package com.example.cristiannavarrete.my_shopping;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

/**
 * This is the main activity class. This activity functions
 * as the log in activity. The user can create new accounts and log
 * in to known accounts.
 * Created by Cristian Navarrete on 10/3/2015.
 */
public class MainActivity extends AppCompatActivity {

    private Singleton instance = Singleton.getInstance();
    private MyDBHandler db;
    private Button logIn, addUser, clear;
    private EditText userField, passField;
    private RadioButton buyer;
    private RadioButton seller;

    /**
     * This function is called when a new activty of this class is created.
     * @param savedInstanceState Bundle containing data from a previous instance
     *                           of an activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        db = new MyDBHandler(this, null, null, 4);

        logIn = (Button) findViewById(R.id.button);
        addUser = (Button) findViewById(R.id.button2);
        //clear = (Button) findViewById(R.id.clear);
        userField = (EditText) findViewById(R.id.editText);
        passField = (EditText) findViewById(R.id.editText2);
        buyer = (RadioButton) findViewById(R.id.radioButton);
        seller = (RadioButton) findViewById(R.id.radioButton2);

        //clear.setEnabled(false);

        logIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userField.getText().toString().equals("") || passField.getText().toString().equals("") || (!seller.isChecked() && !buyer.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Please fill Username and Password fields and state your user account type.", Toast.LENGTH_SHORT).show();
                }
                else if (buyer.isChecked()) {
                    if (db.logInMatch(userField.getText().toString(), passField.getText().toString(), "buyer")) {
                        Toast.makeText(getApplicationContext(), "Log in successful", Toast.LENGTH_SHORT).show();
                        instance.setUserName(userField.getText().toString());
                        Intent k = new Intent(MainActivity.this, BuyerMainPage.class);
                        startActivity(k);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Log in failed", Toast.LENGTH_SHORT).show();
                }
                else if (seller.isChecked()) {
                    if (db.logInMatch(userField.getText().toString(), passField.getText().toString(), "seller")) {
                        Toast.makeText(getApplicationContext(), "Log in successful", Toast.LENGTH_SHORT).show();
                        instance.setUserName(userField.getText().toString());
                        Intent k = new Intent(MainActivity.this, SellerMainPage.class);
                        startActivity(k);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Log in failed", Toast.LENGTH_SHORT).show();
                }
            }
        });

        addUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userField.getText().toString().equals("") || passField.getText().toString().equals("") || (!seller.isChecked() && !buyer.isChecked())) {
                    Toast.makeText(getApplicationContext(), "Please fill Username and Password fields and declare user account type.", Toast.LENGTH_SHORT).show();
                }
                else {
                    if (buyer.isChecked()) {
                        if (db.hasUser(userField.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "That name has already been taken.", Toast.LENGTH_SHORT).show();
                        }
                        else {
                            User user = new User(userField.getText().toString(), passField.getText().toString(), "buyer");
                            user.setId(db.addUser(user));
                            Toast.makeText(getApplicationContext(), "BUYER: " + user.getName().toString() + " has been added with password " + user.getPass(), Toast.LENGTH_SHORT).show();
                        }
                    } else if (seller.isChecked()) {
                        if (db.hasUser(userField.getText().toString())) {
                            Toast.makeText(getApplicationContext(), "That name has already been taken.", Toast.LENGTH_SHORT).show();
                        } else {
                            User user = new User(userField.getText().toString(), passField.getText().toString(), "seller");
                            user.setId(db.addUser(user));
                            Toast.makeText(getApplicationContext(), "SELLER: " + user.getName().toString() + " has been added with password " + user.getPass(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            }
        });

        seller.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (seller.isChecked())
                    buyer.setChecked(false);
            }
        });

        buyer.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (buyer.isChecked())
                    seller.setChecked(false);
            }
        });

//        clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.deleteAllRows();
//            }
//        });



    }

    /**
     * Used to override the back button of the android device
     * to do nothing.
     */
    @Override
    public void onBackPressed() {}


}
