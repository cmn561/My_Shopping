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
 * This is the seller main page class. This activity functions
 * as the main page for sellers. It list the items that belong to
 * the seller.
 *
 * Created by Cristian Navarrete on 11/3/2015.
 */
public class SellerMainPage extends AppCompatActivity {

    private Button addProduct;
    private Button checkFinances;
    private Button goBack;
    private int rows;
    private ArrayList<Product> products = new ArrayList<>();
    private Singleton instance = Singleton.getInstance();
    private MyDBHandler db;

    /**
     * This function is called when a new activty of this class is created.
     *
     * @param savedInstanceState Bundle containing data from a previous instance
     *                           of an activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_main_page);

        db = new MyDBHandler(this, null, null, 4);

        addProduct = (Button) findViewById(R.id.addProduct);
        checkFinances = (Button) findViewById(R.id.seeFinances);
        goBack = (Button) findViewById(R.id.sellerGoBackButton);

        rows = db.numRowsOfSeller(instance.getUserName());

        fetchProductList();

        addProduct.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(SellerMainPage.this, AddProductPage.class);
                startActivity(k);
            }
        });

        checkFinances.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(SellerMainPage.this, FinancesPage.class);
                startActivity(k);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                instance.setUserName("");
                Intent k = new Intent(SellerMainPage.this, MainActivity.class);
                startActivity(k);
            }
        });

    }

    /**
     * Fetches the list of products belonging to a seller
     * and displays them on the screen.
     */
    public void fetchProductList() {
        LinearLayout l = (LinearLayout) findViewById(R.id.sellerListScroll);
        for (int i = 0; i <= rows - 1; i++) {
            Product productToAdd;
            productToAdd = db.getRowProductDataOfSeller(i, instance.getUserName());
            products.add(productToAdd);
        }

        for (int i = 0; i < products.size(); i++) {

            TextView tv = new TextView(this);
            Button newButton = new Button(this);
            newButton.setId(i);

            tv.setText(products.get(i).getName());
            tv.setPadding(0, 35, 0, 0);
            tv.setTextSize(30);
            //double roundOff = Math.round(products.get(i).getSellPrice() * 100.0) / 100.0;
            newButton.setText("Price: " + Double.toString(products.get(i).getSellPrice()) + " $");
            newButton.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            newButton.setOnClickListener(btnclick);
            newButton.setGravity(Gravity.CENTER);

            l.addView(tv);
            l.addView(newButton);

        }
    }


    View.OnClickListener btnclick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Intent k = new Intent(SellerMainPage.this, SellerChangeAttributes.class);
            Product product;
            product = db.getRowProductDataOfSeller(view.getId(), instance.getUserName());

            k.putExtra("id", product.getId());
            k.putExtra("name", product.getName());
            k.putExtra("description", product.getDescription());
            k.putExtra("invoicePrice", product.getInvoiceprice());
            k.putExtra("sellPrice", product.getSellPrice());
            k.putExtra("quantity", product.getQuantity());

            startActivity(k);
        }
    };


    /**
     * Used to override the back button of the android device
     * to do nothing.
     */
    @Override
    public void onBackPressed() {
        Intent k = new Intent(SellerMainPage.this, MainActivity.class);
        startActivity(k);
    }

}
