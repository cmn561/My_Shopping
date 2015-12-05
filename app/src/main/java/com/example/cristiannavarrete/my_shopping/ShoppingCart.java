package com.example.cristiannavarrete.my_shopping;

import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.Gravity;
import android.view.LayoutInflater;
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
 * This is the shopping cart class. This activity functions
 * as the page which displays the user's current shopping cart status.
 * It has buttons to remove an object from the cart,
 * modify the total quantity of an item in the cart,
 * and proceed to checkout.
 *
 * Created by Cristian Navarrete on 11/27/2015.
 */
public class ShoppingCart extends AppCompatActivity {

    private Button goBack, checkout;
    private Singleton instance = Singleton.getInstance();
    private ArrayList<Product> products = instance.getCart();
    private HashMap<Integer, Integer> quantityMap = instance.getQuantityMap();
    private Double cartPrice = 0.00;
    private TextView priceTV;
    private ArrayList<EditText> etArray= new ArrayList<>();

    /**
     * This function is called when a new activty of this class is created.
     * @param savedInstanceState Bundle containing data from a previous instance
     *                           of an activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        LinearLayout l = (LinearLayout) findViewById(R.id.cartScroll);

        goBack = (Button) findViewById(R.id.button3);
        checkout = (Button) findViewById(R.id.button5);
        priceTV = (TextView) findViewById(R.id.shoppingCartPrice);

        for (int i = 0; i < products.size(); i++) {
            TextView tv = new TextView(this);
            TextView tv2 = new TextView(this);
            TextView tv3 = new TextView(this);
            EditText et = new EditText(this);
            LinearLayout h = new LinearLayout(this);
            h.setOrientation(LinearLayout.HORIZONTAL);
            h.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            Button modify = new Button(this);
            Button delete = new Button(this);
            modify.setId(i);
            delete.setId(-i);

            tv.setText(products.get(i).getName());
            tv.setTextColor(Color.BLACK);
            tv.setPadding(0, 35, 0, 0);
            tv.setTextSize(25);
            tv2.setText("Quantity: " + Integer.toString(quantityMap.get(products.get(i).getId())));
            tv3.setText("Price:    " + Double.toString(instance.getCart().get(i).getSellPrice()));
            modify.setText("Change Quantity");
            modify.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            modify.setOnClickListener(modifyClick);
            modify.setGravity(Gravity.CENTER);
            delete.setText("Delete Item");
            delete.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            delete.setOnClickListener(deleteClick);
            delete.setGravity(Gravity.CENTER);

            et.setHint("New Quantity");
            et.setId(i);
            et.setInputType(InputType.TYPE_CLASS_NUMBER);
            etArray.add(et);

            l.addView(tv);
            l.addView(tv2);
            l.addView(tv3);
            h.addView(modify);
            h.addView(et);
            h.addView(delete);
            l.addView(h);

        }

        priceTV.setText("$ " + Double.toString(Math.round(instance.computeCartPrice() * 100.0) / 100.0));

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent (ShoppingCart.this, Checkout.class);
                startActivity(k);
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(ShoppingCart.this, BuyerMainPage.class);
                startActivity(k);
            }
        });


    }

    View.OnClickListener modifyClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            MyDBHandler db = new MyDBHandler(getApplicationContext(), null, null, 4);
            if (etArray.get(view.getId()).getText().toString().equals("")) {
                Toast.makeText(getApplicationContext(), "Set a new quantity.", Toast.LENGTH_SHORT).show();
            }
            else if (Integer.parseInt(etArray.get(view.getId()).getText().toString()) == 0) {
                Toast.makeText(getApplicationContext(), "Cannot set quantity to zero. Use Delete option.", Toast.LENGTH_SHORT).show();
            }
            else if (Integer.parseInt(etArray.get(view.getId()).getText().toString()) > db.getProductQuantityById(products.get(view.getId()).getId())) {
                Toast.makeText(getApplicationContext(), "There is not enough items for that quantity.", Toast.LENGTH_SHORT).show();
            }
            else {
                quantityMap.put(products.get(view.getId()).getId(), Integer.parseInt(etArray.get(view.getId()).getText().toString()));
                finish();
                startActivity(getIntent());
            }
        }

    };

    View.OnClickListener deleteClick = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            int idToDelete;
            idToDelete = products.get(-view.getId()).getId();
            products.remove(-view.getId());

            quantityMap.remove(idToDelete);

            //reloads the activity
            finish();
            startActivity(getIntent());
        }


    };

    /**
     * Used to override the back button of the android device
     * to do nothing.
     */
    @Override
    public void onBackPressed() {
    }



}