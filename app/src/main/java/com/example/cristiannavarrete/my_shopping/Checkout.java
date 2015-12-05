package com.example.cristiannavarrete.my_shopping;

import android.app.ActionBar;
import android.app.Notification;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.text.style.StyleSpan;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * This is the class that manages the checkout activity.
 * Allows the user to input their credit card info and "buy"
 * an item
 * Created by Cristian Navarrete on 11/1/2015.
 */
public class Checkout extends AppCompatActivity {

    private MyDBHandler db;
    private Singleton instance = Singleton.getInstance();
    private TextView priceTV;
    private EditText creditCardET;
    private Button checkoutButton;
    private Button goBack;

    /**
     * This function is called when a new activty of this class is created.
     * @param savedInstanceState Bundle containing data from a previous instance
     *                           of an activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.checkout);
        LinearLayout l = (LinearLayout) findViewById(R.id.checkOutScroll);
        double cartPrice = 0.00;

        priceTV = (TextView) findViewById(R.id.checkoutPriceTV);
        creditCardET = (EditText) findViewById(R.id.creditCardET);
        checkoutButton = (Button) findViewById(R.id.checkoutButton);
        goBack = (Button) findViewById(R.id.checkoutGoBack);

        final ArrayList<Product> products = instance.getCart();
        final HashMap<Integer, Integer> quantityMap = instance.getQuantityMap();

        db = new MyDBHandler(this, null, null, 4);

        for (int i = 0; i < products.size(); i++) {
            TextView nameTV = new TextView(this);
            TextView quantityTV = new TextView(this);
            TextView itemPriceTV = new TextView(this);
            TextView totalTV = new TextView(this);
            LinearLayout h = new LinearLayout(this);

            nameTV.setText(products.get(i).getName());
            nameTV.setTextColor(Color.BLACK);
            nameTV.setPadding(0, 35, 0, 0);
            nameTV.setTextSize(25);
            quantityTV.setText("Quantity: " + Integer.toString(quantityMap.get(products.get(i).getId())));
            //double roundOff = Math.round(products.get(i).getSellPrice() * 100.0) / 100.0;
            itemPriceTV.setText("Price:    " + "$ " + Double.toString(instance.getCart().get(i).getSellPrice()));
            totalTV.setText("Total:    " + "$" + Double.toString(instance.getCart().get(i).getSellPrice() * quantityMap.get(products.get(i).getId())));

            l.addView(nameTV);
            l.addView(quantityTV);
            l.addView(itemPriceTV);
            l.addView(totalTV);

        }
        cartPrice = Math.round(instance.computeCartPrice() * 100.0) / 100.0;
        priceTV.setText("$ " + Double.toString(cartPrice));

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(Checkout.this, ShoppingCart.class);
                startActivity(k);
            }
        });

        checkoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (creditCardET.getText().toString().length() == 16) {
                    boolean cardIsValid = true;
                    for (int i = 0; i < creditCardET.getText().toString().length()-1; i++) {
                        if (!Character.isDigit(creditCardET.getText().toString().charAt(i))) {
                            cardIsValid = false;
                        }
                    }
                    if (cardIsValid) {

                        //update database now that these items are taken
                        for (int i = 0; i < products.size(); i++) {
                            int idToModify = 0;
                            int quantity = 0;
                            int totalSold = 0;
                            double revenue = 0;
                            idToModify = products.get(i).getId();
                            quantity = db.getProductQuantityById(idToModify) - quantityMap.get(idToModify);
                            revenue = db.getProductRevenueById(idToModify);
                            //Revenue = sellPrice - invoicePrice * quantitySold
                            revenue += (products.get(i).getSellPrice() - products.get(i).getInvoiceprice()) * quantityMap.get(idToModify);
                            totalSold = db.getTotalAmountSoldById(idToModify) + quantityMap.get(idToModify);

                            db.updateProduct(idToModify, quantity, revenue, totalSold);
                        }
                        products.clear();
                        quantityMap.clear();
                        Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT).show();
                        Intent k = new Intent(Checkout.this, BuyerMainPage.class);
                        startActivity(k);
                    }
                    else
                        Toast.makeText(getApplicationContext(), "Credit Card Information is invalid. Please input valid credit card information. 16 digits", Toast.LENGTH_SHORT).show();
                }
                else {
                    //creditCardET.setText("");
                    Toast.makeText(getApplicationContext(), "Credit Card Information is invalid. Please input valid credit card information. 16 digits", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    /**
     * Used to override the back button of the android device
     * to do nothing.
     */
    @Override
    public void onBackPressed() {}

}
