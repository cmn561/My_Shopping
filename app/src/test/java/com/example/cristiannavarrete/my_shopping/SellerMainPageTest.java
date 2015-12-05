package com.example.cristiannavarrete.my_shopping;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.TextView;

import junit.framework.TestCase;

import org.w3c.dom.Text;

import java.util.ArrayList;

/**
 * Created by Cristian Navarrete on 12/1/2015.
 */
public class SellerMainPageTest extends ActivityInstrumentationTestCase2<SellerMainPage> {

    private SellerMainPage page;
    private TextView tv;

    public SellerMainPageTest() {
        super(SellerMainPage.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        page = getActivity();

        tv = (TextView) page.findViewById(R.id.textView9);

    }

    public void testPreconditions() {
        assertNotNull("page is null", page);
    }
//
//    public void testMyFirstTestTextView_labelText() {
//        final String expected =
//                page.getString(R.string.sellerInventory);
//        final String actual = tv.getText().toString();
//        assertEquals(expected, actual);
//    }





}