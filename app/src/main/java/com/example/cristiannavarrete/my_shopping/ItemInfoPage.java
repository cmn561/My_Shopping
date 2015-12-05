package com.example.cristiannavarrete.my_shopping;

import android.content.ClipData;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

/**
 * This class is used to display the details of a certain
 * product on the screen and gives the option to add a
 * quantity to the cart.
 * Created by Cristian Navarrete on 11/1/2015.
 */
public class ItemInfoPage extends AppCompatActivity {

    Button add, goBack;
    EditText setQuantity;
    TextView itemNameTV;
    TextView descriptionTV;
    TextView priceTV;
    TextView sellerTV;
    TextView quantityTV;
    TextView idTV;
    int id;
    String name = "";
    String description = "";
    String seller = "";
    double sellPrice;
    int quantity;

    Singleton instance = Singleton.getInstance();

    /**
     * This function is called when a new activty of this class is created.
     * @param savedInstanceState Bundle containing data from a previous instance
     *                           of an activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.item_info_page);

        itemNameTV = (TextView) findViewById(R.id.nameTV);
        descriptionTV = (TextView) findViewById(R.id.descriptionTV);
        priceTV = (TextView) findViewById(R.id.priceTV);
        sellerTV = (TextView) findViewById(R.id.sellerTV);
        quantityTV = (TextView) findViewById(R.id.quantityTV);
        add = (Button) findViewById(R.id.addToCartButton);
        goBack = (Button) findViewById(R.id.infoGoBack);
        setQuantity = (EditText) findViewById(R.id.editText8);
        idTV = (TextView) findViewById(R.id.idTV);

        final Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getInt("id");
            name = extras.getString("name");
            description = extras.getString("description");
            sellPrice = extras.getDouble("sellPrice");
            seller = extras.getString("seller");
            quantity = extras.getInt("quantity");
        }

        idTV.setText(Integer.toString(id));
        itemNameTV.setText(name);
        descriptionTV.setText(description);
        priceTV.setText(Double.toString(sellPrice));
        sellerTV.setText(seller);
        quantityTV.setText(Integer.toString(quantity));

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (setQuantity.getText().toString().equals("")) {
                    Toast.makeText(getApplicationContext(), "Give the quantity to add to cart.", Toast.LENGTH_SHORT).show();
                }
                else if (instance.getQuantityMap().containsKey(id)) {
                    if (Integer.parseInt(setQuantity.getText().toString()) + instance.getQuantityMap().get(id) > quantity) {
                        Toast.makeText(getApplicationContext(), "Not enough items in stock to accommodate.", Toast.LENGTH_SHORT).show();
                    }
                    else if (Integer.parseInt(setQuantity.getText().toString()) > quantity) {
                        Toast.makeText(getApplicationContext(), "Not enough items in stock to accommodate.", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        instance.getQuantityMap().put(id, instance.getQuantityMap().get(id) + Integer.parseInt(setQuantity.getText().toString()));
                        Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_SHORT).show();
                        Intent k = new Intent(ItemInfoPage.this, BuyerMainPage.class);
                        startActivity(k);
                    }
                }
                else if (Integer.parseInt(setQuantity.getText().toString()) > quantity) {
                    Toast.makeText(getApplicationContext(), "Not enough items in stock to accommodate.", Toast.LENGTH_SHORT).show();
                }
                else {
                    Product p = new Product(name, extras.getDouble("invoicePrice"), sellPrice, quantity, description, seller);
                    p.setId(extras.getInt("id"));

                    instance.getCart().add(p);
                    instance.getQuantityMap().put(id, Integer.parseInt(setQuantity.getText().toString()));
                    Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_SHORT).show();
                    Toast.makeText(getApplicationContext(), "Success.", Toast.LENGTH_SHORT).show();
                    Intent k = new Intent(ItemInfoPage.this, BuyerMainPage.class);
                    startActivity(k);
                }
            }
        });

        goBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent k = new Intent(ItemInfoPage.this, BuyerMainPage.class);
                startActivity(k);

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
