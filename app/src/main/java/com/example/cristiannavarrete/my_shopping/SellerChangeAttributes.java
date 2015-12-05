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
 * This is the main page for changing the attributes of a
 * seller's products.
 *
 * Created by Cristian Navarrete on 11/30/2015.
 */
public class SellerChangeAttributes extends AppCompatActivity {

    private MyDBHandler db;
    private EditText nameET;
    private EditText descriptionET;
    private EditText quantityET;
    private EditText invoicePriceET;
    private EditText sellPriceET;
    private Button submit;
    private Button goBack;
    private int id = 0;
    private double invoicePrice = 0.00;
    private String name = "";
    private String description = "";
    private Double sellPrice = 0.00;
    private String seller = "";
    private int quantity = 0;

    /**
     * This function is called when a new activty of this class is created.
     *
     * @param savedInstanceState Bundle containing data from a previous instance
     *                           of an activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.seller_change_attributes);

        db = new MyDBHandler(this, null, null, 4);

        nameET = (EditText) findViewById(R.id.changeProductName);
        descriptionET = (EditText) findViewById(R.id.changeDescription);
        quantityET = (EditText) findViewById(R.id.changeQuantity);
        invoicePriceET = (EditText) findViewById(R.id.changeInvoicePrice);
        sellPriceET = (EditText) findViewById(R.id.changeSellPrice);
        submit = (Button) findViewById(R.id.submit);
        goBack = (Button) findViewById(R.id.changeGoBack);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            name = extras.getString("name");
            description = extras.getString("description");
            sellPrice = extras.getDouble("sellPrice");
            seller = extras.getString("seller");
            quantity = extras.getInt("quantity");
            invoicePrice = extras.getDouble("invoicePrice");
        }

        nameET.setText(name);
        descriptionET.setText(description);
        sellPriceET.setText(Double.toString(sellPrice));
        invoicePriceET.setText(Double.toString(invoicePrice));
        quantityET.setText(Integer.toString(quantity));


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInventory();
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(SellerChangeAttributes.this, SellerMainPage.class);
                startActivity(k);
            }
        });

    }

    /**
     * This function will update the item that the
     * seller has selected with the new attributes,
     * if the info given was valid.
     */
    public void updateInventory() {
        if ((nameET.getText().toString().equals("")) || (descriptionET.getText().toString().equals(""))
                || (quantityET.getText().toString().equals("")) || (invoicePriceET.getText().toString().equals(""))
                || (sellPriceET.getText().toString().equals(""))) {
            Toast.makeText(getApplicationContext(), "Please Fill all Fields before submitting.", Toast.LENGTH_SHORT).show();
        }
        else {
            try {
                name = nameET.getText().toString();
                description = descriptionET.getText().toString();
                quantity = Integer.parseInt(quantityET.getText().toString());
                invoicePrice = Double.parseDouble(invoicePriceET.getText().toString());
                sellPrice = Double.parseDouble(sellPriceET.getText().toString());
                if ((invoicePrice < 0) || (sellPrice < 0)) {
                    throw new NumberFormatException();
                }
                invoicePrice = round(invoicePrice, 2);
                sellPrice = round(sellPrice, 2);

                Product p = new Product(name, invoicePrice, sellPrice, quantity, description, seller);
                p.setId(id);

                db.updateSellersProduct(p.getId(), p);

                Intent k = new Intent(SellerChangeAttributes.this, SellerMainPage.class);
                startActivity(k);
            } catch (NumberFormatException e) {
                Toast.makeText(getApplicationContext(), "Make sure price fields are non negative decimal values", Toast.LENGTH_SHORT).show();
            } catch (IllegalArgumentException e) {
                Toast.makeText(getApplicationContext(), "Please Fill all Fields.", Toast.LENGTH_SHORT).show();
            }
        }
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
