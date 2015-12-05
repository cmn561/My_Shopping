package com.example.cristiannavarrete.my_shopping;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * This class is used by seller to add a new
 * product to their inventory.
 *
 * Created by Cristian Navarrete on 11/14/2015.
 */
public class AddProductPage extends AppCompatActivity {

    Singleton instance = Singleton.getInstance();
    MyDBHandler db;
    double invoice, sell;
    EditText name;
    EditText description;
    EditText quantity;
    EditText invoicePrice;
    EditText sellPrice;
    Button add;
    Button goBack;
    Button clear;

    /**
     * This function is called when a new activty of this class is created.
     *
     * @param savedInstanceState Bundle containing data from a previous instance
     *                           of an activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_product);

        db = new MyDBHandler(this, null, null, 4);

        name = (EditText) findViewById(R.id.editText3);
        description = (EditText) findViewById(R.id.editText4);
        quantity = (EditText) findViewById(R.id.editText5);
        invoicePrice = (EditText) findViewById(R.id.editText6);
        sellPrice = (EditText) findViewById(R.id.editText7);
        add = (Button) findViewById(R.id.button4);
        goBack = (Button) findViewById(R.id.goBack);
        //clear = (Button) findViewById(R.id.clearProductDatabase);

        //clear.setEnabled(false);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((name.getText().toString().equals("")) || (description.getText().toString().equals(""))
                        || (quantity.getText().toString().equals("")) || (invoicePrice.getText().toString().equals(""))
                        || (sellPrice.getText().toString().equals(""))) {
                    Toast.makeText(getApplicationContext(), "Please Fill all Fields.", Toast.LENGTH_SHORT).show();
                }
                else {
                    try {
                        invoice = Double.parseDouble(invoicePrice.getText().toString());
                        sell = Double.parseDouble(sellPrice.getText().toString());
                        if ((invoice < 0) || (sell < 0)) {
                            throw new NumberFormatException();
                        }
                        invoice = round(invoice, 2);
                        sell = round(sell, 2);

                        Product product = new Product(name.getText().toString(), invoice, sell, Integer.parseInt(quantity.getText().toString()),
                                description.getText().toString(), instance.getUserName());

                        Toast.makeText(getApplicationContext(), Integer.toString(db.addProduct(product)), Toast.LENGTH_SHORT).show();

                        eraseAllFields();

                    } catch (NumberFormatException e) {
                        Toast.makeText(getApplicationContext(), "Make sure price fields are non negative decimal values", Toast.LENGTH_SHORT).show();
                    } catch (IllegalArgumentException e) {
                        Toast.makeText(getApplicationContext(), "Please Fill all Fields.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(AddProductPage.this, SellerMainPage.class);
                startActivity(k);
            }
        });

//        clear.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                db.deleteAllRowsProduct();
//            }
//        });
    }


    /**
     * This function will clear all of the TextViews
     * when a product has been added.
     */
    private void eraseAllFields() {
        name.setText("");
        description.setText("");
        quantity.setText("");
        invoicePrice.setText("");
        sellPrice.setText("");
    }

    /**
     * This function is used to round out a double
     * value to 2 decimal places.
     * @param value The double value to round.
     * @param places The number of decimal places to round to.
     * @return
     */
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
    /**
     * Used to override the back button of the android device
     * to do nothing.
     */
    @Override
    public void onBackPressed() {return;}

}
