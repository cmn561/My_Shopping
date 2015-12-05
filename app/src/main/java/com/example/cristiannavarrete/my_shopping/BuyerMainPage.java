package com.example.cristiannavarrete.my_shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * This is the buyer main page class. This activity functions
 * as the main page for buyers. It list the items that they can buy on the page.
 * Created by Cristian Navarrete on 11/1/2015.
 */
public class
        BuyerMainPage extends AppCompatActivity {

    private MyDBHandler db;
    private Singleton instance = Singleton.getInstance();
    private ArrayList<Product> products = new ArrayList<>();
    private int rows;
    private TextView cartPrice;
    private Button goBack;
    private Button goToCart;

    /**
     * This function is called when a new activty of this class is created.
     * @param savedInstanceState Bundle containing data from a previous instance
     *                           of an activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.buyer_main_page);
        LinearLayout l = (LinearLayout) findViewById(R.id.linearScroll);

        goBack = (Button) findViewById(R.id.buyerGoBackButton);
        goToCart = (Button) findViewById(R.id.goToCartButton);
        cartPrice = (TextView) findViewById(R.id.price);

        db = new MyDBHandler(this, null, null, 4);
        rows = db.numRows();

        for (int i = 0; i <= rows - 1; i++) {
            Product productToAdd;
            productToAdd = db.getRowProductData(i);
            products.add(productToAdd);
        }


        for (int i = 0; i < products.size(); i++) {

            TextView tv = new TextView(this);
            Button newButton = new Button(this);
            newButton.setId(i);

            tv.setText(products.get(i).getName());
            tv.setPadding(0, 35, 0, 0);
            tv.setTextSize(25);
            newButton.setText("Price: " + Double.toString(products.get(i).getSellPrice()) + " $");
            newButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            newButton.setOnClickListener(btnclick);
            newButton.setGravity(Gravity.CENTER);

            l.addView(tv);
            l.addView(newButton);

        }

        //set the price of the shopping cart
        cartPrice.setText("$ " + Double.toString(Math.round(instance.computeCartPrice()*100.0)/100.0));

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.setUserName("");
                instance.getCart().clear();
                instance.getQuantityMap().clear();
                Intent k = new Intent(BuyerMainPage.this, MainActivity.class);
                startActivity(k);
            }
        });

        goToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(BuyerMainPage.this, ShoppingCart.class);
                startActivity(k);
            }
        });



    }

    View.OnClickListener btnclick = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
                Intent k = new Intent(BuyerMainPage.this, ItemInfoPage.class);
                Product product;
                product = db.getRowProductData(view.getId());

                k.putExtra("id", product.getId());
                k.putExtra("name", product.getName());
                k.putExtra("description", product.getDescription());
                k.putExtra("invoicePrice", product.getInvoiceprice());
                k.putExtra("sellPrice", product.getSellPrice());
                k.putExtra("quantity", product.getQuantity());
                k.putExtra("seller", product.getSeller());

                startActivity(k);
            }
    };

    /**
     * Used to override the back button of the android device
     * to do nothing.
     */
    @Override
    public void onBackPressed() {}



};



